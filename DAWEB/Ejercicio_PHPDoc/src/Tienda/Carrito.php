<?php

/**
 * Implements a shopping cart for an online store.
 *
 * @package App\Tienda
 * @author Rafael Vega Librero
 * @version 1.0
 */

declare(strict_types=1);

namespace App\Tienda;

use InvalidArgumentException;
use OutOfBoundsException;

/**
 * Represents a customer's shopping cart in an online store.
 *
 * The cart lines are stored using the product SKU as the key,
 * keeping its price and quantity.
 * The total amounts are calculated by applying the discount first
 * and then the VAT.
 *
 * @since 1.0
 * @see \App\Tienda\PedidoService
 */
class Carrito
{
    /**
     * VAT percentage applied to the products.
     *
     * @var float
     */
    public const IVA = 0.21;

    /**
     * Product lines stored in the shopping cart.
     *
     * @var array<string, array{precio: float, cantidad: int}>
     */
    private array $lineas = [];

    /**
     * Creates a new shopping cart associated with a customer.
     *
     * @param string $cliente Customer name.
     *
     * @throws InvalidArgumentException If the customer name is empty.
     */
    public function __construct(private readonly string $cliente)
    {
        if (trim($cliente) === '') {
            throw new InvalidArgumentException('Cliente requerido');
        }
    }

    /**
     * Returns the name of the customer associated with the cart.
     *
     * @return string Customer name.
     */
    public function getCliente(): string
    {
        return $this->cliente;
    }

    /**
     * Adds a product to the shopping cart.
     *
     * If the SKU already exists in the cart, a new line is not created.
     * Instead, the given quantity is added to the existing quantity.
     *
     * @param string $sku Product SKU identifier.
     * @param float $precio Product unit price.
     * @param int $cantidad Number of units to add.
     *
     * @return void
     *
     * @throws InvalidArgumentException If the price is negative
     *                                  or the quantity is less than or equal to zero.
     */
    public function agregar(
        string $sku,
        float $precio,
        int $cantidad = 1
    ): void {
        if ($precio < 0 || $cantidad <= 0) {
            throw new InvalidArgumentException('Precio o cantidad inválidos');
        }

        if (isset($this->lineas[$sku])) {
            $this->lineas[$sku]['cantidad'] += $cantidad;
        } else {
            $this->lineas[$sku] = [
                'precio' => $precio,
                'cantidad' => $cantidad
            ];
        }
    }

    /**
     * Removes a product from the shopping cart using its SKU.
     *
     * @param string $sku SKU of the product to remove.
     *
     * @return void
     *
     * @throws OutOfBoundsException If the product is not found in the cart.
     */
    public function eliminar(string $sku): void
    {
        if (!isset($this->lineas[$sku])) {
            throw new OutOfBoundsException(
                "El producto $sku no está en el carrito"
            );
        }

        unset($this->lineas[$sku]);
    }

    /**
     * Returns all product lines stored in the shopping cart.
     *
     * @return array<string, array{precio: float, cantidad: int}>
     *         Product lines stored in the cart.
     */
    public function getLineas(): array
    {
        return $this->lineas;
    }

    /**
     * Calculates the cart subtotal without applying discount or VAT.
     *
     * @return float Cart subtotal.
     */
    public function subtotal(): float
    {
        $total = 0.0;

        foreach ($this->lineas as $linea) {
            $total += $linea['precio'] * $linea['cantidad'];
        }

        return $total;
    }

    /**
     * Calculates the final total price of the shopping cart.
     *
     * First, the discount percentage is applied to the subtotal.
     * Then, VAT is applied to the resulting amount.
     *
     * Example:
     * $carrito->total(10);
     * Calculates the total with a 10% discount.
     *
     * @param float $descuento Discount percentage between 0 and 100.
     *
     * @return float Final cart price including discount and VAT.
     *
     * @throws InvalidArgumentException If the discount is not between 0 and 100.
     */
    public function total(float $descuento = 0.0): float
    {
        if ($descuento < 0 || $descuento > 100) {
            throw new InvalidArgumentException('Descuento fuera de rango');
        }

        $base = $this->subtotal() * (1 - $descuento / 100);

        return round($base * (1 + self::IVA), 2);
    }

    /**
     * Checks whether the shopping cart is empty.
     *
     * This method will be replaced by isEmpty() in version 2.0.
     *
     * @return bool True if the cart is empty, false otherwise.
     *
     * @deprecated 2.0 This method will be replaced by isEmpty().
     */
    public function estaVacio(): bool
    {
        return count($this->lineas) === 0;
    }
}
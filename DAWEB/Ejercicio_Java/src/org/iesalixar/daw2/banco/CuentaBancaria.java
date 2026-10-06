package org.iesalixar.daw2.banco;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 */
public class CuentaBancaria {
    private final String iban;
    private BigDecimal saldo;
    private boolean bloqueada = false;

    /**
     * Contructor
     * We enter the data and verify it.
     * @param iban
     * @param saldoInicial
     */
    public CuentaBancaria(String iban, BigDecimal saldoInicial) {
        if (iban == null || iban.isBlank()) throw new
                IllegalArgumentException("IBAN requerido");
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) <
                0)
            throw new IllegalArgumentException("Saldo no puede ser negativo");
        this.iban = iban;
        this.saldo = saldoInicial;
    }

    /**
     * Return the IBAN.
     * @return iban
     */
    public String getIban() {
        return iban;
    }
    /**
     * Return the IBAN.
     * @return saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }
    /**
     * Return the IBAN.
     */
    public void bloquear() {
        bloqueada = true;
    }
    /**
     * Return the information if the bloquead
     */
    public void desbloquear() {
        bloqueada = false;
    }
    /**
     * Return the information of the count
     * @return bloqueada
     */
    public boolean estaBloqueada() {
        return bloqueada;
    }

    /**
     *controls the money deposited into the account
     * @param importe
     */
    public void depositar(BigDecimal importe) {
        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Importe inválido");
        saldo = saldo.add(importe);
    }

    /**
     *Controls the withdrawal of funds.
     * @param importe
     */
    public void retirar(BigDecimal importe) {
        if (bloqueada) throw new IllegalStateException("Cuenta bloqueada");
        if (importe == null || importe.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Importe inválido");
        if (importe.compareTo(saldo) > 0) throw new
                IllegalArgumentException("Saldo insuficiente");
        saldo = saldo.subtract(importe);
    }

    /**
     * @param o   the reference object with which to compare.
     * @return iban
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBancaria that)) return false;
        return Objects.equals(iban, that.iban);
    }

    /**
     * @return iban
     */
    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    /**
     * Nos devuelve un String con la informacion de nuestra cuenta
     * @return saldo
     * @return iban
     * @return bloqueada
     */
    @Override
    public String toString() {
        return "CuentaBancaria{iban='" + iban + "', saldo=" + saldo +
                "bloqueada=" + bloqueada + '}';
    }
}
<?php

namespace App\Dto;

use Symfony\Component\Validator\Constraints as Assert;

class Contacto
{
    #[Assert\NotBlank(message: 'Escribe tu nombre.')]
    #[Assert\Length(min: 3, minMessage: 'El nombre debe tener al menos {{ limit }} caracteres.')]
    public ?string $nombre = null;

    #[Assert\NotBlank(message: 'Escribe tu correo electrónico.')]
    #[Assert\Email(message: 'Escribe un correo electrónico válido.')]
    public ?string $email = null;

    #[Assert\NotBlank(message: 'Escribe un mensaje.')]
    public ?string $mensaje = null;
}

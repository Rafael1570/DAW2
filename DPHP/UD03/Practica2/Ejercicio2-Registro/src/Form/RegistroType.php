<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Validator\Constraints as Assert;

class RegistroType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nombre', TextType::class, [
                'label' => 'Nombre',
                'attr' => ['autocomplete' => 'given-name'],
                'constraints' => [new Assert\NotBlank(message: 'Escribe tu nombre.')],
            ])
            ->add('apellidos', TextType::class, [
                'label' => 'Apellidos',
                'attr' => ['autocomplete' => 'family-name'],
                'constraints' => [new Assert\NotBlank(message: 'Escribe tus apellidos.')],
            ])
            ->add('email', EmailType::class, [
                'label' => 'Correo electrónico',
                'attr' => ['autocomplete' => 'email'],
                'constraints' => [
                    new Assert\NotBlank(message: 'Escribe tu correo electrónico.'),
                    new Assert\Email(message: 'Introduce un correo electrónico válido.'),
                ],
            ])
            ->add('usuario', TextType::class, [
                'label' => 'Usuario',
                'attr' => ['autocomplete' => 'username'],
                'constraints' => [new Assert\NotBlank(message: 'Escribe un nombre de usuario.')],
            ])
            ->add('password', RepeatedType::class, [
                'type' => PasswordType::class,
                'invalid_message' => 'Las contraseñas deben coincidir.',
                'options' => ['attr' => ['autocomplete' => 'new-password']],
                'first_options' => ['label' => 'Contraseña'],
                'second_options' => ['label' => 'Repite la contraseña'],
                'constraints' => [
                    new Assert\NotBlank(message: 'Escribe una contraseña.'),
                    new Assert\Length(max: 72, maxMessage: 'La contraseña supera los 72 bytes.', countUnit: 'bytes'),
                    new Assert\Regex(pattern: '/^[^\x00]*$/D', message: 'La contraseña contiene un carácter no válido.'),
                ],
            ])
            ->add('condiciones', CheckboxType::class, [
                'label' => 'Acepto las condiciones',
                'required' => false,
                'constraints' => [new Assert\IsTrue(message: 'Debes aceptar las condiciones para registrarte.')],
            ])
            ->add('registrar', SubmitType::class, ['label' => 'Crear cuenta']);
    }
}

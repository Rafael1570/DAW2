<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
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
                'label' => 'Nombre completo',
                'constraints' => [new Assert\NotBlank()],
            ])
            ->add('email', EmailType::class, [
                'label' => 'Correo electrónico',
                'constraints' => [new Assert\NotBlank(), new Assert\Email()],
            ])
            ->add('password', PasswordType::class, [
                'label' => 'Contraseña',
                'constraints' => [new Assert\NotBlank()],
                'attr' => ['autocomplete' => 'new-password'],
            ])
            ->add('edad', IntegerType::class, [
                'label' => 'Edad',
                'constraints' => [new Assert\NotNull(), new Assert\PositiveOrZero()],
            ])
            ->add('fecha_nacimiento', DateType::class, [
                'label' => 'Fecha de nacimiento',
                'widget' => 'single_text',
                'constraints' => [new Assert\NotNull()],
            ])
            ->add('genero', ChoiceType::class, [
                'label' => 'Género',
                'placeholder' => 'Selecciona una opción',
                'choices' => ['Mujer' => 'f', 'Hombre' => 'm', 'Otro' => 'o'],
                'constraints' => [new Assert\NotBlank()],
            ])
            ->add('acepta_condiciones', CheckboxType::class, [
                'label' => 'Acepto las condiciones',
                'mapped' => false,
                'required' => true,
                'constraints' => [new Assert\IsTrue(message: 'Debes aceptar las condiciones.')],
            ])
            ->add('enviar', SubmitType::class, ['label' => 'Enviar registro']);
    }
}

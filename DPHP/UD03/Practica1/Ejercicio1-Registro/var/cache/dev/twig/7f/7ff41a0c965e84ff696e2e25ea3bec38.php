<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Extension\CoreExtension;
use Twig\Extension\SandboxExtension;
use Twig\MacroNamespace;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Sandbox\SecurityNotAllowedTestError;
use Twig\Source;
use Twig\Template;
use Twig\TemplateWrapper;

/* datos.html.twig */
class __TwigTemplate_372704bca2d028d4b9977229ac3108a5 extends Template
{
    private Source $source;
    /**
     * @var array<string, MacroNamespace>
     */
    private array $macros = [];
    private \Twig\Runtime\EscaperRuntime $escaper;

    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->source = $this->getSourceContext();

        $this->escaper = $env->getRuntime('Twig\Runtime\EscaperRuntime');

        $this->blocks = [
            'titulo' => [$this, 'block_titulo'],
            'contenido' => [$this, 'block_contenido'],
        ];
    }

    protected function doGetParent(array $context): bool|string|Template|TemplateWrapper
    {
        // line 1
        return $this->parent ??= $this->load("base.html.twig", 1);
    }

    protected function doDisplay(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "datos.html.twig"));

        $this->parent = $this->load("base.html.twig", 1);
        yield from $this->parent->unwrap()->yield($context, array_merge($this->blocks, $blocks));
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

    }

    // line 3
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_titulo(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "titulo"));

        yield "Datos recibidos";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        return; yield;
    }

    // line 5
    /**
     * @return iterable<null|scalar|\Stringable>
     */
    public function block_contenido(array $context, array $blocks = []): iterable
    {
        $macros = $this->macros;
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f = $this->extensions["Symfony\\Bridge\\Twig\\Extension\\ProfilerExtension"];
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "block", "contenido"));

        // line 6
        yield "    <dl>
        <dt>Nombre</dt><dd>";
        // line 7
        yield (string) $this->escaper->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 7, $this->source); })()), "nombre", [], "any", false, false, false, 7), "html", null, true);
        yield "</dd>
        <dt>Correo electrónico</dt><dd>";
        // line 8
        yield (string) $this->escaper->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 8, $this->source); })()), "email", [], "any", false, false, false, 8), "html", null, true);
        yield "</dd>
        <dt>Contraseña</dt><dd>";
        // line 9
        yield (string) $this->escaper->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 9, $this->source); })()), "password", [], "any", false, false, false, 9), "html", null, true);
        yield "</dd>
        <dt>Edad</dt><dd>";
        // line 10
        yield (string) $this->escaper->escape(CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 10, $this->source); })()), "edad", [], "any", false, false, false, 10), "html", null, true);
        yield "</dd>
        <dt>Fecha de nacimiento</dt><dd>";
        // line 11
        yield (string) $this->escaper->escape($this->extensions['Twig\Extension\CoreExtension']->formatDate(CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 11, $this->source); })()), "fecha_nacimiento", [], "any", false, false, false, 11), "d/m/Y"), "html", null, true);
        yield "</dd>
        <dt>Género</dt><dd>";
        // line 12
        yield (string) $this->escaper->escape(CoreExtension::getAttribute($this->env, $this->source, ["f" => "Mujer", "m" => "Hombre", "o" => "Otro"], CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 12, $this->source); })()), "genero", [], "any", false, false, false, 12), [], "array", false, false, false, 12), "html", null, true);
        yield "</dd>
        <dt>Acepta las condiciones</dt><dd>";
        // line 13
        yield (string) (((($tmp = CoreExtension::getAttribute($this->env, $this->source, (isset($context["datos"]) || array_key_exists("datos", $context) ? $context["datos"] : (function () { throw new RuntimeError('Variable "datos" does not exist.', 13, $this->source); })()), "acepta_condiciones", [], "any", false, false, false, 13)) && $tmp instanceof Markup ? (string) $tmp : $tmp)) ? ("Sí") : ("No"));
        yield "</dd>
    </dl>
    <a href=\"";
        // line 15
        yield (string) $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("app_registro");
        yield "\">Volver al registro</a>
";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        return; yield;
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "datos.html.twig";
    }

    /**
     * @codeCoverageIgnore
     */
    public function isTraitable(): bool
    {
        return false;
    }

    /**
     * @codeCoverageIgnore
     */
    public function getDefaultEscapeStrategy(): string|false
    {
        return "html";
    }

    /**
     * @codeCoverageIgnore
     */
    public function getDebugInfo(): array
    {
        return array (  122 => 15,  117 => 13,  113 => 12,  109 => 11,  105 => 10,  101 => 9,  97 => 8,  93 => 7,  90 => 6,  80 => 5,  63 => 3,  46 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends \x27base.html.twig\x27 %}

{% block titulo %}Datos recibidos{% endblock %}

{% block contenido %}
    <dl>
        <dt>Nombre</dt><dd>{{ datos.nombre }}</dd>
        <dt>Correo electrónico</dt><dd>{{ datos.email }}</dd>
        <dt>Contraseña</dt><dd>{{ datos.password }}</dd>
        <dt>Edad</dt><dd>{{ datos.edad }}</dd>
        <dt>Fecha de nacimiento</dt><dd>{{ datos.fecha_nacimiento|date(\x27d/m/Y\x27) }}</dd>
        <dt>Género</dt><dd>{{ {\x27f\x27: \x27Mujer\x27, \x27m\x27: \x27Hombre\x27, \x27o\x27: \x27Otro\x27}[datos.genero] }}</dd>
        <dt>Acepta las condiciones</dt><dd>{{ datos.acepta_condiciones ? \x27Sí\x27 : \x27No\x27 }}</dd>
    </dl>
    <a href=\"{{ path(\x27app_registro\x27) }}\">Volver al registro</a>
{% endblock %}
", "datos.html.twig", "/home/usuario-m/Documentos/DAW2/DPHP/UD03/formularios/Practica1/Ejercicio1-Registro/templates/datos.html.twig");
    }
}

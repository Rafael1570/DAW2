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

/* carrito.html.twig */
class __TwigTemplate_0a7e537200821217b1f2f88210533d3a extends Template
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
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->enter($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof = new \Twig\Profiler\Profile($this->getTemplateName(), "template", "carrito.html.twig"));

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

        yield "Carrito de la compra";
        
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
        yield "    <p>Total de productos: <strong>";
        yield (string) $this->escaper->escape((isset($context["total"]) || array_key_exists("total", $context) ? $context["total"] : (function () { throw new RuntimeError('Variable "total" does not exist.', 6, $this->source); })()), "html", null, true);
        yield "</strong></p>
    ";
        // line 7
        if ((($tmp = (isset($context["carrito"]) || array_key_exists("carrito", $context) ? $context["carrito"] : (function () { throw new RuntimeError('Variable "carrito" does not exist.', 7, $this->source); })())) && $tmp instanceof Markup ? (string) $tmp : $tmp)) {
            // line 8
            yield "        <ul>
            ";
            // line 9
            $context['_parent'] = $context;
            $context['_seq'] = CoreExtension::ensureTraversable((isset($context["carrito"]) || array_key_exists("carrito", $context) ? $context["carrito"] : (function () { throw new RuntimeError('Variable "carrito" does not exist.', 9, $this->source); })()));
            foreach ($context['_seq'] as $context["_key"] => $context["producto"]) {
                // line 10
                yield "                <li>";
                yield (string) $this->escaper->escape($context["producto"], "html", null, true);
                yield "</li>
            ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_key'], $context['producto'], $context['_parent']);
            $context = array_intersect_key($context, $_parent);
            $context += $_parent;
            // line 12
            yield "        </ul>
        <a class=\"boton\" href=\"";
            // line 13
            yield (string) $this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("carrito_vaciar");
            yield "\">Vaciar carrito</a>
    ";
        } else {
            // line 15
            yield "        <p>El carrito está vacío.</p>
    ";
        }
        // line 17
        yield "
    <h2>Añadir un producto</h2>
    <div class=\"acciones\">
        ";
        // line 20
        $context['_parent'] = $context;
        $context['_seq'] = CoreExtension::ensureTraversable(["Pan", "Leche", "Manzana"]);
        foreach ($context['_seq'] as $context["_key"] => $context["producto"]) {
            // line 21
            yield "            <a href=\"";
            yield (string) $this->escaper->escape($this->extensions['Symfony\Bridge\Twig\Extension\RoutingExtension']->getPath("carrito_anadir", ["producto" => $context["producto"]]), "html", null, true);
            yield "\">";
            yield (string) $this->escaper->escape($context["producto"], "html", null, true);
            yield "</a>
        ";
        }
        $_parent = $context['_parent'];
        unset($context['_seq'], $context['_key'], $context['producto'], $context['_parent']);
        $context = array_intersect_key($context, $_parent);
        $context += $_parent;
        // line 23
        yield "    </div>
";
        
        $__internal_6f47bbe9983af81f1e7450e9a3e3768f->leave($__internal_6f47bbe9983af81f1e7450e9a3e3768f_prof);

        return; yield;
    }

    /**
     * @codeCoverageIgnore
     */
    public function getTemplateName(): string
    {
        return "carrito.html.twig";
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
        return array (  147 => 23,  135 => 21,  131 => 20,  126 => 17,  122 => 15,  117 => 13,  114 => 12,  104 => 10,  100 => 9,  97 => 8,  95 => 7,  90 => 6,  80 => 5,  63 => 3,  46 => 1,);
    }

    public function getSourceContext(): Source
    {
        return new Source("{% extends \x27base.html.twig\x27 %}

{% block titulo %}Carrito de la compra{% endblock %}

{% block contenido %}
    <p>Total de productos: <strong>{{ total }}</strong></p>
    {% if carrito %}
        <ul>
            {% for producto in carrito %}
                <li>{{ producto }}</li>
            {% endfor %}
        </ul>
        <a class=\"boton\" href=\"{{ path(\x27carrito_vaciar\x27) }}\">Vaciar carrito</a>
    {% else %}
        <p>El carrito está vacío.</p>
    {% endif %}

    <h2>Añadir un producto</h2>
    <div class=\"acciones\">
        {% for producto in [\x27Pan\x27, \x27Leche\x27, \x27Manzana\x27] %}
            <a href=\"{{ path(\x27carrito_anadir\x27, {producto: producto}) }}\">{{ producto }}</a>
        {% endfor %}
    </div>
{% endblock %}
", "carrito.html.twig", "/home/usuario-m/Documentos/DAW2/DPHP/UD03/formularios/Practica1/Ejercicio5-Carrito/templates/carrito.html.twig");
    }
}

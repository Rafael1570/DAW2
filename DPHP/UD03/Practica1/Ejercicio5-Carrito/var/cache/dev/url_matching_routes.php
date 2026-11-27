<?php

/**
 * This file has been auto-generated
 * by the Symfony Routing Component.
 */

return [
    false, // $matchHost
    [ // $staticRoutes
        '/carrito' => [[['_route' => 'carrito_ver', '_controller' => 'App\\Controller\\CarritoController::ver'], null, ['GET' => 0], null, false, false, null]],
        '/carrito/vaciar' => [[['_route' => 'carrito_vaciar', '_controller' => 'App\\Controller\\CarritoController::vaciar'], null, ['GET' => 0], null, false, false, null]],
    ],
    [ // $regexpList
        0 => '{^(?'
                .'|/carrito/anadir/([^/]++)(*:31)'
            .')/?$}sDu',
    ],
    [ // $dynamicRoutes
        31 => [
            [['_route' => 'carrito_anadir', '_controller' => 'App\\Controller\\CarritoController::anadir'], ['producto'], ['GET' => 0], null, false, true, null],
            [null, null, null, null, false, false, 0],
        ],
    ],
    null, // $checkCondition
];

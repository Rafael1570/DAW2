<?php

    declare(strict_types=1);

    date_default_timezone_set('Europe/Madrid');

    $cumples = [
        'María López'   => '1990-10-12',
        'Carlos Ruiz'   => '1985-02-28',
        'Laura García'  => '1992-06-05',
        'Pedro Martín'  => '1988-12-25',
        'Elena Sánchez' => '1996-02-29' // caso bisiesto
    ];

    $hoy = new DateTime('today');

    $resultados = [];

    foreach ($cumples as $nombre => $fechaNacStr) {
        $nac = DateTime::createFromFormat('Y-m-d', $fechaNacStr);
        if (!$nac) {
            $resultados[] = [
                'nombre' => $nombre,
                'nacimiento' => $fechaNacStr,
                'prox' => '—',
                'faltan' => 'Fecha inválida',
            ];
            continue;
        }

        $anioActual = (int)$hoy->format('Y');
        $mes = (int)$nac->format('m');
        $dia = (int)$nac->format('d');

        if ($mes === 2 && $dia === 29 && !checkdate(2, 29, $anioActual)) {
            $dia = 28;
        }

        $prox = DateTime::createFromFormat('Y-m-d', sprintf('%04d-%02d-%02d', $anioActual, $mes, $dia));

        if ($prox < $hoy) {
            $anioSiguiente = $anioActual + 1;
            if ($mes === 2 && $dia === 29 && !checkdate(2, 29, $anioSiguiente)) {
                $dia = 28;
            }
            $prox = DateTime::createFromFormat('Y-m-d', sprintf('%04d-%02d-%02d', $anioSiguiente, $mes, $dia));
        }

        // Diferencia en días
        $diff = $hoy->diff($prox);
        $diasFaltan = (int)$diff->format('%a');

        $resultados[] = [
            'nombre'     => $nombre,
            'nacimiento' => $nac->format('Y-m-d'),
            'prox'       => $prox->format('Y-m-d'),
            'faltan'     => $diasFaltan === 0 ? '¡Hoy!' : $diasFaltan . ' día(s)',
        ];
    }
?>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Días hasta el próximo cumpleaños</title>
    </head>
    <body>

    <h1>Próximos cumpleaños</h1>

    <table>
        <tr>
            <th>Nombre</th>
            <th>Fecha nacimiento</th>
            <th>Próximo cumpleaños</th>
            <th>Faltan</th>
        </tr>
        <?php foreach ($resultados as $r): ?>
            <tr class="<?= ($r['faltan'] === '¡Hoy!') ? 'hoy' : '' ?>">
                <td><?= $r['nombre'] ?></td>
                <td><?= $r['nacimiento'] ?></td>
                <td><?= $r['prox'] ?></td>
                <td><?= $r['faltan'] ?></td>
            </tr>
        <?php endforeach; ?>
    </table>

    </body>
</html>

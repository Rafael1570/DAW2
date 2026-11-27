-- Crear tabla para los jugadores (cuentas de Riot) de Valorant
-- Entidad JUGADOR del modelo E-R. La clave primaria es el PUUID que asigna Riot.
-- El atributo derivado PartidasRegistradas no se guarda: se calcula contando sus participaciones.
CREATE TABLE IF NOT EXISTS players (
    puuid VARCHAR(78) PRIMARY KEY,
    riot_name VARCHAR(16) NOT NULL,
    riot_tag VARCHAR(5) NOT NULL,
    region VARCHAR(5) NOT NULL,
    account_level INT NOT NULL,
    current_rank VARCHAR(30) NULL,
    UNIQUE (riot_name, riot_tag)
);

-- Crear tabla para las participaciones de cada jugador en una partida
-- Entidad PARTICIPACIÓN del modelo E-R (lado N de la relación JUGADOR 1:N PARTICIPACIÓN).
-- En el E-R la clave es compuesta (IdPartida, PUUID): aquí se usa un id autoincremental
-- y se mantiene esa pareja como UNIQUE para que un jugador no aparezca dos veces en la misma partida.
-- Los atributos derivados KDA, ACS y PorcentajeHS no se guardan: se calculan en el Mapper.
CREATE TABLE IF NOT EXISTS participations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    match_id VARCHAR(64) NOT NULL,
    player_puuid VARCHAR(78) NOT NULL,
    team VARCHAR(10) NOT NULL,
    kills INT NOT NULL,
    deaths INT NOT NULL,
    assists INT NOT NULL,
    total_score INT NOT NULL,
    rounds_played INT NOT NULL,
    headshots INT NOT NULL,
    total_hits INT NOT NULL,
    result VARCHAR(10) NOT NULL,
    UNIQUE (match_id, player_puuid),
    FOREIGN KEY (player_puuid) REFERENCES players(puuid)
);

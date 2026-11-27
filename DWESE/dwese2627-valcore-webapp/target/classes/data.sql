-- Inserts de los jugadores, ignora si se produce un error en la inserción (ya existen)
INSERT IGNORE INTO players (puuid, riot_name, riot_tag, region, account_level, current_rank) VALUES
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a01', 'NeonDrift', 'EUW', 'eu', 214, 'Diamond 2'),
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a02', 'SageMain', '1234', 'eu', 87, 'Gold 3'),
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a03', 'Vandalito', 'ESP', 'eu', 156, 'Platinum 1'),
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a04', 'OneTapJuan', 'SEV', 'eu', 301, 'Ascendant 1'),
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a05', 'SmokeyJett', 'NA1', 'na', 45, NULL),
('6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a06', 'ClutchKing', 'KR7', 'kr', 412, 'Immortal 2');

-- Inserts de las participaciones. El UNIQUE (match_id, player_puuid) evita duplicados al reiniciar.
INSERT IGNORE INTO participations
    (match_id, player_puuid, team, kills, deaths, assists, total_score, rounds_played, headshots, total_hits, result)
VALUES
-- Partida 1: 13-9 (22 rondas), gana el equipo rojo
('b7e2d1c0-0001-4a5b-8c9d-0e1f2a3b4c01', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a01', 'RED',  24, 14, 6, 6380, 22, 41, 152, 'VICTORY'),
('b7e2d1c0-0001-4a5b-8c9d-0e1f2a3b4c01', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a02', 'RED',  12, 15, 11, 3520, 22, 18, 109, 'VICTORY'),
('b7e2d1c0-0001-4a5b-8c9d-0e1f2a3b4c01', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a03', 'BLUE', 17, 18, 4, 4690, 22, 30, 131, 'DEFEAT'),
-- Partida 2: 11-13 (24 rondas), gana el equipo rojo
('b7e2d1c0-0002-4a5b-8c9d-0e1f2a3b4c02', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a01', 'BLUE', 19, 17, 5, 5300, 24, 33, 140, 'DEFEAT'),
('b7e2d1c0-0002-4a5b-8c9d-0e1f2a3b4c02', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a04', 'RED',  27, 13, 7, 7150, 24, 52, 168, 'VICTORY'),
('b7e2d1c0-0002-4a5b-8c9d-0e1f2a3b4c02', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a05', 'BLUE',  9, 19, 8, 2810, 24, 11, 98, 'DEFEAT'),
-- Partida 3: 13-5 (18 rondas), gana el equipo azul
('b7e2d1c0-0003-4a5b-8c9d-0e1f2a3b4c03', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a02', 'BLUE', 15, 8, 9, 4050, 18, 22, 101, 'VICTORY'),
('b7e2d1c0-0003-4a5b-8c9d-0e1f2a3b4c03', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a03', 'BLUE', 20, 9, 3, 5220, 18, 37, 118, 'VICTORY'),
('b7e2d1c0-0003-4a5b-8c9d-0e1f2a3b4c03', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a06', 'RED',  14, 16, 2, 3890, 18, 29, 96, 'DEFEAT'),
-- Partida 4: 13-7 (20 rondas), gana el equipo rojo
('b7e2d1c0-0004-4a5b-8c9d-0e1f2a3b4c04', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a01', 'RED',  22, 11, 4, 5960, 20, 39, 133, 'VICTORY'),
('b7e2d1c0-0004-4a5b-8c9d-0e1f2a3b4c04', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a05', 'RED',   8, 12, 13, 2740, 20, 9, 87, 'VICTORY'),
('b7e2d1c0-0004-4a5b-8c9d-0e1f2a3b4c04', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a04', 'BLUE', 18, 16, 3, 5010, 20, 35, 127, 'DEFEAT'),
('b7e2d1c0-0004-4a5b-8c9d-0e1f2a3b4c04', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a06', 'BLUE', 16, 17, 5, 4480, 20, 31, 119, 'DEFEAT'),
-- Partida 5: 10-13 (23 rondas), gana el equipo azul
('b7e2d1c0-0005-4a5b-8c9d-0e1f2a3b4c05', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a02', 'RED',  13, 17, 10, 3750, 23, 17, 112, 'DEFEAT'),
('b7e2d1c0-0005-4a5b-8c9d-0e1f2a3b4c05', '6f1c2a90-3b4d-4e8f-9a01-1c2d3e4f5a03', 'BLUE', 23, 14, 6, 6190, 23, 44, 147, 'VICTORY');

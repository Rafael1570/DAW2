# Valcore — CRUD de Participaciones (Jugador 1:N Participación)

Aplicación Spring Boot + Thymeleaf + JPA (MariaDB) con la misma estructura que `ticket-logger`.

- **Entidad principal (CRUD completo):** `Participation` → tabla `participations`.
- **Entidad relacionada (lado 1):** `Player` → tabla `players`. Se elige en el `<select>` del formulario
  y su Riot ID se ve en el listado; en el detalle aparecen todos sus datos.
- **Atributos derivados del E-R** (no se guardan, se calculan en `ParticipationMapper`):
  KDA, ACS, % HS de la participación y *Partidas registradas* del jugador.
- **Validaciones:** Bean Validation en los DTO y validaciones propias en `ParticipationController`
  (impactos en la cabeza ≤ impactos totales, máximo 5 bajas por ronda). La regla "un jugador solo una
  vez por partida" la aplica el servicio con `DuplicateResourceException`.

## Arrancar

```bash
cp .env.example .env   # y pon las contraseñas
docker compose up -d   # MariaDB en el puerto 3308
./mvnw spring-boot:run
```

Abre <http://localhost:8080/participations>. Idioma: selector de la cabecera o `?lang=en`.

`schema.sql` y `data.sql` se ejecutan en cada arranque (`spring.sql.init.mode=always`) con
`CREATE TABLE IF NOT EXISTS` e `INSERT IGNORE`, así que no duplican datos.

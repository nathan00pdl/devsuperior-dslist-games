# DSList — Game Lists API

[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.1-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/github/license/nathan00pdl/devsuperior-dslist-games)](LICENSE)

A REST API for browsing games grouped into lists and reordering the games inside a list.

Built during the [DevSuperior](https://devsuperior.com.br/) Java Spring immersion, taught by Nélio Alves. It was my first structured application with Spring Boot.

## Tech stack

- **Java 17**
- **Spring Boot 3.1.1** — Spring Web and Spring Data JPA
- **Hibernate** (JPA implementation)
- **H2** in-memory database for the default profile
- **PostgreSQL** for the `dev` and `prod` profiles
- **Maven**, through the Maven Wrapper (`./mvnw`)

## Architecture

Layered: `controllers` → `services` → `repositories` → `entities`.

- **DTOs** (`GameDTO`, `GameMinDTO`, `GameListDTO`, `ReplacementDTO`) keep entities out of the HTTP responses.
- **Projections** (`GameMinProjection`) read only the columns a list view needs.
- **Native queries** in the repositories search the games of a list and update a game's position when the list is reordered.
- A many-to-many relationship between games and lists is mapped by the `Belonging` entity, with a composite key (`BelongingPK`) and the game's `position` in the list.

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/games` | All games, summarized |
| `GET` | `/games/{id}` | One game, with full details |
| `GET` | `/lists` | All game lists |
| `GET` | `/lists/{listId}/games` | The games of a list, in order |
| `POST` | `/lists/{listId}/replacement` | Moves a game to another position in the list |

Moving the game at position 3 to position 1 of list 1:

```bash
curl -X POST http://localhost:8080/lists/1/replacement \
  -H "Content-Type: application/json" \
  -d '{ "sourceIndex": 3, "destinationIndex": 1 }'
```

## Running locally

Requirements: **Java 17**. Maven does not need to be installed.

```bash
git clone https://github.com/nathan00pdl/devsuperior-dslist-games.git
cd devsuperior-dslist-games
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080` with the `test` profile: an in-memory H2 database seeded from `src/main/resources/import.sql` with 2 lists and 10 games. The H2 console is at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, user `sa`, empty password).

### Profiles and environment variables

The profile is chosen with `APP_PROFILE` (default: `test`).

| Profile | Database | Variables |
|---|---|---|
| `test` | H2 in memory | none |
| `dev` | Local PostgreSQL | `DB_PASSWORD` (required), `DB_URL` (default `jdbc:postgresql://localhost:5432/dslist`), `DB_USERNAME` (default `postgres`) |
| `prod` | PostgreSQL | `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` |

`CORS_ORIGINS` sets the allowed origins in every profile (default `http://localhost:5173,http://localhost:3000`).

The `dev` and `prod` profiles do not create the schema (`ddl-auto=none`). Run `create.sql` first — it creates the tables and inserts the sample data:

```bash
psql -U postgres -d dslist -f create.sql
```

Then create your `.env` from the template, fill in the password, and load it before starting:

```bash
cp .env.example .env
set -a && source .env && set +a
./mvnw spring-boot:run
```

`set -a` exports everything read from the file as an environment variable, which is how Spring sees it — Java does not read `.env` on its own. `.env` is ignored by git and must never be committed. Passing the values inline also works:

```bash
APP_PROFILE=dev DB_PASSWORD=your_password ./mvnw spring-boot:run
```

### Tests

```bash
./mvnw test
```

## License

Licensed under the [MIT License](LICENSE).

## Contact

Nathan Paiva de Lacerda — [LinkedIn](https://www.linkedin.com/in/nathan-paiva-636336236)

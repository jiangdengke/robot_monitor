# Robot Monitor Admin

This repository now keeps a single active backend, `backend`, together with the Vue frontend.

## Structure

```text
backend/
  src/main/java/          Spring Boot monolith source
  src/main/resources/     application config, schema.sql, and platform templates
  build.gradle.kts
frontend/
  src/                    Vue 3 + Ant Design Vue source project
  legacy-dist/            Original built frontend assets
  package.json
  vite.config.js
docs/
  e2e-smoke.md
  e2e-smoke-report.md
```

## Current Status

- Active backend is `backend`.
- Backend compiles with:

```bash
cd backend
./gradlew compileJava
```

- Frontend is now based on `Vite + Vue 3 + Ant Design Vue`.
- Frontend build passes with:

```bash
cd frontend
npm run build
```

- Browser smoke test passes against the current retained routes and new backend API.
- Current API summary is documented in `backend/API_SUMMARY.md`.

## Important Boundary

- `backend` is the only maintained backend in this repository.
- `frontend/src` is maintainable reconstructed source adapted to the new monolith API.
- `frontend/legacy-dist` is preserved only as static historical reference.

## Backend Runtime

- Backend uses MySQL via `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DATABASE`, `MYSQL_USERNAME`, and `MYSQL_PASSWORD`.
- Startup no longer rebuilds the demo schema by default. Set `SPRING_SQL_INIT_MODE=always` only for local demo database rebuilds.
- JWT must be configured with `JWT_SECRET`; use a long random value outside local demos.
- Platform bootstrap reads an enabled row from `project.platform_bootstrap_config` first, then falls back to `PLATFORM_BOOTSTRAP_LOCATION` when no database config is available or parsing fails.
- `PLATFORM_BOOTSTRAP_LOCATION` defaults to `classpath:platform/templates/lounge-greeting/bootstrap.json`. Set it to another `classpath:` or `file:` location for per-project deployment templates.
- The `/system/platformBootstrap` page provides a minimal JSON editor for saving the database bootstrap config.

## Docker Deployment

This project can be delivered as a single Docker image. The image contains:

- Nginx serving the built frontend on port `7777`
- Spring Boot backend running inside the same container on port `8080`
- Nginx proxying `/api/*` to the backend without exposing backend port `8080`

Server deployment only needs `docker-compose.yml` and a local `.env` file:

```bash
docker compose pull
docker compose up -d
```

For local image builds from source:

```bash
docker compose -f docker-compose.yml -f docker-compose.build.yml up -d --build
```

Copy `.env.example` to `.env` on the server and fill in the database host and password. Do not commit `.env`.
Set `JAVA_OPTS` only when the server needs an explicit JVM memory limit.
Keep `SPRING_SQL_INIT_MODE=never` for existing or production databases. Use `always` only when intentionally rebuilding demo data.

Pushing to `main` triggers `.github/workflows/docker-image.yml`, which builds and publishes the single image to GHCR.

# Robot Monitor Admin

This repository now keeps a single active backend, `backend`, together with the Vue frontend.

## Structure

```text
backend/
  src/main/java/          Spring Boot monolith source
  src/main/resources/     application config and schema.sql
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
- Startup still runs `backend/src/main/resources/db/schema.sql`, which rebuilds the demo schema and seed data.

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

Pushing to `main` triggers `.github/workflows/docker-image.yml`, which builds and publishes the single image to GHCR.

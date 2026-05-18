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

- Backend now defaults to MySQL at `104.223.20.115:3306/project`.
- Set `SPRING_DATASOURCE_PASSWORD` before starting the backend.
- Startup still runs `backend/src/main/resources/db/schema.sql`, which rebuilds the demo schema and seed data.

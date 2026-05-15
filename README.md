# Robot Monitor Admin

This repository now keeps a single active backend, `moban_java`, together with the Vue frontend.

## Structure

```text
moban_java/
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

- Active backend is `moban_java`.
- Backend compiles with:

```bash
cd moban_java
./gradlew compileJava
```

- Frontend is now based on `Vite + Vue 3 + Ant Design Vue`.
- Frontend build passes with:

```bash
cd frontend
npm run build
```

- Browser smoke test passes against the current retained routes and new backend API.
- Current API summary is documented in `moban_java/API_SUMMARY.md`.

## Important Boundary

- `moban_java` is the only maintained backend in this repository.
- `frontend/src` is maintainable reconstructed source adapted to the new monolith API.
- `frontend/legacy-dist` is preserved only as static historical reference.

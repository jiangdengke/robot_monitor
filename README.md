# Robot Monitor Admin

This repository contains the reconstructed management backend and frontend.

## Structure

```text
backend/
  src/main/java/          Decompiled reference Java source
  src/overlay/java/       Active reconstructed source overrides
  src/main/resources/     Spring Boot resources
  lib/                    Runtime/compile dependencies from original jar
  sql/init.sql            Local bootstrap database schema and demo data
  runtime-config/         Local profile configuration templates
frontend/
  src/                    Reconstructed Vue 3 + Element Plus source project
  legacy-dist/            Original built frontend assets
  package.json
  vite.config.js
```

## Current Status

- Backend Java source has been merged into a normal Spring Boot layout.
- Internal modules such as `common`, `framework`, `system`, `config`, `flight`, `food`, `bot`, `ai`, `quartz`, and `generator` are now under `backend/src/main/java`.
- Backend business mappers now use explicit jOOQ implementations under `backend/src/overlay/java`.
- Backend compilation passes with:

```bash
cd backend
./gradlew compileJava
```

- Frontend now includes a `src/` source project based on `Vite + Vue 3 + Element Plus`.
- Common admin pages use a shared Element Plus CRUD implementation; digital twin, table model, form builder, code generator, profile/avatar, auth, AI statistics, and video stream pages have dedicated source implementations.
- Frontend build passes with:

```bash
cd frontend
npm run build
```

- The original deployment frontend build is preserved under `frontend/legacy-dist/`.

## Important Boundary

- The backend is reconstructed source and compile-ready.
- The frontend `src/` project is maintainable reconstructed `.vue` source, not a 1:1 restored original source-control repository.
- End-to-end runtime still depends on local MySQL, Redis, and external integrations such as Kafka, DeepGlint, AI services, and robot services.

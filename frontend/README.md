# Frontend

This directory now contains both the active Vue source project and the original built frontend assets.

## Layout

```text
frontend/
  src/          Vue 3 + Vite + Ant Design Vue source project
  legacy-dist/  original deployment build assets
```

## Current Status

- `src/` has been created so the frontend is no longer only a static bundle.
- The active routes cover users, profiles, sites, areas, points, devices, robots, tasks, and logs.
- Common admin pages use shared CRUD and data components adapted to Ant Design Vue.
- The frontend is aligned with the Kotlin backend and its `site`/`area`/`point` API contract.
- Swagger remains available through backend endpoints but is not exposed as a frontend menu or route.
- Removed lounge, statistics, digital-twin, media, complaint, and knowledge modules have no compatibility routes or API aliases.
- The frontend build has been verified with `npm run build`.
- The original built frontend remains preserved under `legacy-dist/` for reference only.

## Important Boundary

This is not the original frontend repository restored from source control.
It is maintainable `.vue` source adapted from the deployment bundle and aligned with the current Kotlin backend.

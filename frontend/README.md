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
- Main route paths kept in the new scope have been recreated under `src/views/`.
- Common admin pages use shared CRUD and data components adapted to Ant Design Vue.
- Current frontend routes are aligned to the `moban_java` monolith API.
- The frontend build has been verified with `npm run build`.
- The original built frontend remains preserved under `legacy-dist/` for reference only.

## Important Boundary

This is not the original frontend repository restored from source control.
It is maintainable `.vue` source adapted from the deployment bundle and aligned to the current monolith backend.

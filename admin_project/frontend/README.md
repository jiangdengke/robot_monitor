# Frontend

This directory now contains both the reconstructed Vue source project and the original built frontend assets.

## Layout

```text
frontend/
  src/          Vue 3 + Vite + Element Plus source project
  legacy-dist/  original deployment build assets
```

## Current Status

- `src/` has been created so the frontend is no longer only a static bundle.
- Main route paths found in the original built bundle have been recreated under `src/views/`.
- Common admin pages use a shared Element Plus CRUD page with query, pagination, add, edit, delete, detail dialog, batch delete, and upload dialog.
- Digital twin, table model, form builder, code generator, profile/avatar, auth, AI statistics, and video stream pages have dedicated `.vue` source implementations.
- The frontend build has been verified with `npm run build`.
- The original built frontend remains preserved under `legacy-dist/` for comparison and continued reverse reconstruction.

## Important Boundary

This is not the original frontend repository restored from source control.
It is maintainable reconstructed `.vue` source based on the deployment bundle and backend interfaces.

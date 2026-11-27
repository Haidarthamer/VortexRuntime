# VORTEX Architecture

## Current foundation

The first app milestone deliberately keeps the Android shell independent from any game engine.

- **app** — Jetpack Compose UI and Android lifecycle.
- **device profile** — reports Android-provided ABI, memory, OS, and Vulkan feature metadata.
- **runtime boundary** — future adapter contract for a separately supplied runtime.
- **graphics** — future runtime-specific graphics settings; no driver installer is included.
- **multiplayer** — future integration point only; no networking protocol is implemented.
- **tools** — future file validation, controller mapping, and diagnostics.

## Planned dependency direction

UI → domain interfaces → runtime adapter → verified native runtime component

The UI must not directly load arbitrary native libraries or assume that a renderer is present. Runtime discovery, compatibility checks, and lifecycle management belong in the adapter layer.

## Milestones

1. Android project foundation and CI.
2. Device profile and import/validation flow.
3. Runtime adapter contract and capability reporting.
4. Graphics configuration for a verified backend.
5. Controller mapping and diagnostics.
6. Multiplayer only if the selected runtime exposes compatible, documented networking hooks.

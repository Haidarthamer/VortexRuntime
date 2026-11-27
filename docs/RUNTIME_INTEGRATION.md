# Runtime Integration Boundary

VORTEX does not bundle a proprietary game engine or game data.

Before an adapter can be implemented, the selected runtime must have:

- a license that permits the intended integration and redistribution (or be installed separately by its owner);
- an Android-compatible binary for the target ABI and Android version;
- documented initialization, shutdown, and error-reporting interfaces;
- a way to validate required game files without copying them into this repository;
- a clear compatibility matrix for game version, renderer, and device requirements.

## Not implemented yet

The current application does not launch GTA V or any other commercial game. It does not contain DXVK, Turnip packages, game assets, or proprietary native libraries.

A native ARM64 Android port is not the same thing as emulating the PC x86-64 version. The runtime must match the actual game build and platform.

## Multiplayer requirement

A functional multiplayer implementation needs more than lobby UI. It requires a compatible protocol and engine-level state hooks for the game entities being synchronized. VORTEX will not advertise multiplayer as working until those conditions are met and tested on multiple physical devices.

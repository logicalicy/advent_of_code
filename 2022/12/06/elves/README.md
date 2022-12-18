# Elves

## Setup

This project was generated with:

```
cargo generate --git https://github.com/rustwasm/wasm-pack-template
```

The WASM app was generated with:

```
npm init wasm-app www
```

Add to `www/package.json`.

```
"dependencies": {
  "wasm-elves": "file:../pkg"
}
```

## Build

Always rebuild with each code change:

```
wasm-pack build
```

## Run

To run:

```
cd www
npm run start
open http://localhost:9292/
```

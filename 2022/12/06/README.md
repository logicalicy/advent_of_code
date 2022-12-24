# 2022/12/06

Sixth day of [advent of code](https://adventofcode.com/): an elf is signal-tuning.

## Background
### Setup

This project was generated with `cargo-generate` and is built with `wasm-pack`.
See https://rustwasm.github.io/docs/book/game-of-life

Note that the following lines were added to `elves/www/package.json`:

```
"dependencies": {
  "wasm-game-of-life": "file:../pkg"
}
```

### Part #1

Elves give you a handheld comms device! But it's broken.

* They want you to fix the malfunctioning device
* The device processes Elves' signals, series of characters
* Start of packet is indicated by sequence of four characters that are all different
* The device receives a datastream buffer (`input.txt`)
* Function identifies first position where four most recently received characters are all different
* Needs to report number of characters from beginning of buffer to end of the first such four-character marker.

_Question:_ How many characters need to be processed before the first start-of-packet marker is detected?

## Run

```
cd ./2022/12/06/elves
wasm-pack build
cd www
npm run start
open http://localhost:8080/
```

## Part #2

Start-of-message marker has 14 distinct chars, rather than four.

_Question:_ How many characters need to be processed before the first start-of-message marker is detected?

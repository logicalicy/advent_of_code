# 2022/12/07

Seventh day of [advent of code](https://adventofcode.com/): an elf is freeing up disk space.

## Background
### Part #1

Here's what we know:
* The filesystem is very UNIX-like, root is `/`
* Commands are bash-like (`cd`, `ls`)
* `ls` command outputs `dir abc` (folder `abc`) or `123 xyz` (file `xyz` with size `123`)
* `input.txt` shows commands run

_Question:_ What is the sum of the total sizes of directories with a total size of at most 100000?

### Part #2

Find the smallest directory that, if deleted, would free up enough space on the filesystem to run an update.
* Total available space: 70000000
* Update requires at least: 30000000

_Question:_ What is the total size of that directory?.

## Run

```
npm run build
npm run start
```

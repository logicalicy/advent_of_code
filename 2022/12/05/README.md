# 2022/12/05

Fifth day of [advent of code](https://adventofcode.com/): an elf managed supply stacks with cranes.

# Background

## Part #1

Expedition can depart as soon as final supplies have been unloaded from the ships.
* Supplies are stored in stacks of marked crates
* But needed supplies are buried under many other crates
* Crates need to be rearranged with a crane

The initial stacks look like this:

```
    [D]
[N] [C]
[Z] [M] [P]
 1   2   3
```

The crane instructions are like this:
```
move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
```

...which results in:
```
        [Z]
        [N]
        [D]
[C] [M] [P]
 1   2   3
```

_Question:_ After the rearrangement procedure, what crate ends up on top of each stack?

## Part #2

A new crane has the ability to pick up and move multiple crates at once.

* Multiple crates can be moved, in the order they're stacked

_Question:_ After the rearrangement procedure, what crate ends up on top of each stack?

# Run

```
./2022/12/05/part1.js
./2022/12/05/part2.js
```

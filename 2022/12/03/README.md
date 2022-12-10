# 2022/12/03

Third day of [advent of code](https://adventofcode.com/): an elf loaded rucksacks with Python.

## Background

### Part #1

Elves need to load rucksacks with supplies for a jungle journey:
- Each line represents items put in a rucksack
- For each line:
  - First half of characters represent items in the first compartment
  - Second half of the characters represent items in the second compartment
- Some items appear in both compartments of a rucksack!
- For each item, there are priorities:
  - a-z have priorities 1-26
  - A-Z have priorities 27-52

_Question:_ What is the sum of priorities of items that appear in both compartments of rucksacks?

### Part #2

- Every set of three lines corresponds to a single group of elves
- For each group:
  - There is one item type that is common between all three elves' backpacks
  - The item type common to all three elves' represents the group's "badge"


_Question:_ What is the sum of the priorities of each group's badge?

## Run

```
python 2022/12/03/part_1.py
python 2022/12/03/part_2.py
```

# 2022/12/08

Eighth day of [advent of code](https://adventofcode.com/): an elf looking at trees.

## Background

### Part #1

A grid might look like this:

```
111
121
111
```

* Each number represents the height of a tree
* A tree is visible if it's in the outer-most rows or columns, or is not obstructed from view by another tree in its row/column
* All the trees are visible above

_Question:_ How many trees are visible from outside the grid?

### Part #2

A tree's scenic score is found by multiplying together its viewing distance in each of the four directions:
* Viewing distance in any one direction is trees you can see from the tree, up to the tree blocking the view
* If tree is on the edge, at least one of its viewing distances will be zero

_Question:_ What is the highest scenic score possible for any tree?

## Run

For part #1:

```
cd src/
CLASSPATH=$(pwd) javac app/forest/Part1.java
java app.forest.Part1
```

For part #2:

```
cd src/
CLASSPATH=$(pwd) javac app/forest/Part2.java
java app.forest.Part2
```

## Test

```
cd src/
javac test/app/forest/ForestTest.java -cp $(pwd)":"$(pwd)"/../lib/junit-4.13.2.jar"
java -cp $(pwd)":"$(pwd)"/../lib/junit-4.13.2.jar:"$(pwd)"/../lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore test.app.forest.ForestTest
```

# 2022/12/02

Second day of [advent of code](https://adventofcode.com/): an elf that won Rock, Paper Scissors with Haskell.

## Background

### Part #1

It's a Rock-Paper-Scissors tournament, to settle which elf camps closest to snack storage!
* Each game has many rounds: each round, players choose one of Rock, Paper, or Scissors.
* One Elf gives you an encrypted strategy guide (input.txt) to help you win.
* First column is opponent's action: A = Rock, B = Paper, and C = Scissors.
* Second column is your response: X = Rock, Y = Paper, and Z = Scissors.
* Score for a round = Score for selected shape (1 = Rock, 2 = Paper, 3 = Scissors) + Score for outcome (0 = lost, 3 = draw, 6 = won).
* Total score is the sum of your scores for each round.
* We're not sure if Elf's encrypted strategy is a trick... so need to calculate score when guide is followed.

_Question:_ What would total score be if strategy guide is followed?

### Part #2

...second column says how round needs to end:
* X = need to lose
* Y = need to end the round in a draw
* Z = need to win

_Question:_ What would total score be if (new) strategy guide is followed?

## Run

```
runhaskell Part1
runhaskell Part2
```
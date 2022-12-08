module Part2 where

import System.IO
import Part1 (
  Shape(Rock, Paper, Scissors),
  toShape,
  getRounds,
  calcScoreForRound)

data Strategy = Win | Draw | Lose deriving Show
instance Eq Strategy where
  (==) Win Win = True
  (==) Draw Draw = True
  (==) Lose Lose = True
  (==) _ _ = False


toShapeStratTuple :: [String] -> (Shape, Strategy)
toShapeStratTuple [x, y]
  | y == "X" = (toShape(x), Lose)
  | y == "Y" = (toShape(x), Draw)
  | y == "Z" = (toShape(x), Win)

toShapeTuple' :: (Shape, Strategy) -> (Shape, Shape)
toShapeTuple' (shape, strat)
  | shape == Rock && strat == Win = (shape, Paper)
  | shape == Paper && strat == Win = (shape, Scissors)
  | shape == Scissors && strat == Win = (shape, Rock)
  | shape == Rock && strat == Lose = (shape, Scissors)
  | shape == Paper && strat == Lose = (shape, Rock)
  | shape == Scissors && strat == Lose = (shape, Paper)
  | otherwise = (shape, shape)

toShapeTuple :: [String] -> (Shape, Shape)
toShapeTuple ss = toShapeTuple'(toShapeStratTuple ss)

solveTotalScore' contents = sum (map calcScoreForRound ((getRounds toShapeTuple) contents))

solveTotalScore handle = do
  contents <- hGetContents handle
  print(solveTotalScore' contents)

main = do
  withFile "input.txt" ReadMode solveTotalScore

module Part1 where

import System.IO

data Shape = Rock | Paper | Scissors deriving Show

instance Eq Shape where
  (==) Rock Rock = True
  (==) Paper Paper = True
  (==) Scissors Scissors = True
  (==) _ _ = False

toShape :: String -> Shape
toShape x
  | x `elem` ["A", "X"] = Rock
  | x `elem` ["B", "Y"] = Paper
  | x `elem` ["C", "Z"] = Scissors

toShapeTuple :: [String] -> (Shape, Shape)
toShapeTuple ss = (toShape(ss!!0), toShape(ss!!1))

getRounds :: ([String] -> (Shape, Shape)) -> String -> [(Shape, Shape)]
getRounds toShapeTuple' contents = [toShapeTuple'(words x) | x <- lines contents]

calcScoreForOutcome :: (Shape, Shape) -> Integer
calcScoreForOutcome round
  | round == (Scissors, Paper) = 0
  | round == (Paper, Rock) = 0
  | round == (Rock, Scissors) = 0
  | round == (Paper, Scissors) = 6
  | round == (Rock, Paper) = 6
  | round == (Scissors, Rock) = 6
  | otherwise = 3

calcScoreForShape :: Shape -> Integer
calcScoreForShape shape
  | shape == Rock = 1
  | shape == Paper = 2
  | shape == Scissors = 3

calcScoreForRound :: (Shape, Shape) -> Integer
calcScoreForRound round = sum [calcScoreForOutcome round, calcScoreForShape (snd round)]

solveTotalScore' contents = sum (map calcScoreForRound ((getRounds toShapeTuple) contents))

solveTotalScore handle = do
  contents <- hGetContents handle
  print(solveTotalScore' contents)

main = do
  withFile "input.txt" ReadMode solveTotalScore


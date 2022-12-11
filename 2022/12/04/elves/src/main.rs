use std::fs;
use std::path::PathBuf;

#[derive(Debug)]
pub struct Range {
  lower: i32,
  upper: i32,
}

pub trait Containable {
  fn contains(&self, r: &Range) -> bool;
}

pub trait Overlappable {
  fn overlaps(&self, r: &Range) -> bool;
}

impl Containable for Range {
  fn contains(&self, r: &Range) -> bool {
    self.lower <= r.lower && r.upper <= self.upper
  }
}

impl Overlappable for Range {
  fn overlaps(&self, r: &Range) -> bool {
    self.lower <= r.upper && r.lower <= self.upper
    || r.contains(self)
    || self.contains(r)
  }
}

fn to_pair(line: &str) -> Vec<&str> {
  line.trim().split(",").collect()
}

fn to_range(boundaries: Vec<&str>) -> Range {
  let lower = boundaries[0].parse().expect("Must be a number.");
  let upper = boundaries[1].parse().expect("Must be a number.");
  Range {
    lower: lower,
    upper: upper,
  }
}

fn to_ranges<'a>(pair: &'a Vec<&'a str>) -> Vec<Range> {
  let range_boundaries: Vec<Vec<&str>> = pair.iter().map(|range| range.split("-").collect()).collect();
  range_boundaries.iter().map(|vec| to_range(vec.to_vec())).collect()
}

fn to_pair_ranges(lines: &Vec<&str>) -> Vec<Vec<Range>> {
  let pairs: Vec<Vec<&str>> = lines
    .iter()
    .map(|line| to_pair(line))
    .collect();
  let pair_ranges: Vec<Vec<Range>> = pairs
    .iter()
    .map(|pair| to_ranges(pair))
    .collect();
  return pair_ranges;
}

fn calculate_num_overlaps(lines: &Vec<&str>) -> i32 {
  let pair_ranges = to_pair_ranges(lines);
  let num_overlaps: i32 = pair_ranges
    .iter()
    .map(|pair_range| {
      let left_range = &pair_range[0];
      let right_range = &pair_range[1];
      if left_range.overlaps(right_range) {
        1
      } else {
        0
      }
    })
    .collect::<Vec<i32>>()
    .iter()
    .sum();
  return num_overlaps;
}

fn main() {
  let filepath = PathBuf::from("../input.txt");
  let contents = fs::read_to_string(filepath)
    .expect("Could not read from file");
  let lines: Vec<&str> = contents.trim().split("\n").collect();
  let pairs: Vec<Vec<&str>> = lines
    .iter()
    .map(|line| to_pair(line))
    .collect();
  let pair_ranges: Vec<Vec<Range>> = pairs
    .iter()
    .map(|pair| to_ranges(pair))
    .collect();
  let num_contained: i32 = pair_ranges
    .iter()
    .map(|pair_range| {
      let left_range = &pair_range[0];
      let right_range = &pair_range[1];
      if left_range.contains(right_range) || right_range.contains(left_range) {
        1
      } else {
        0
      }
    })
    .collect::<Vec<i32>>()
    .iter()
    .sum();
  println!("Assignment pairs where one range fully contains the other: {}", num_contained);

  let num_overlaps = calculate_num_overlaps(&lines);
  println!("Assignment pairs where one range overlaps with the other: {}", num_overlaps);
}

#[cfg(test)]
mod tests {
  use super::*;
  #[test]
  fn it_calculates_overlaps_correctly() {
    let lines = vec![
      "2-4,6-8",
      "2-3,4-5",
      "5-7,7-9", // Overlap!
      "2-8,3-7", // Overlap!
      "6-6,4-6", // Overlap!
      "2-6,4-8", // Overlap!
    ];
    let num_overlaps = calculate_num_overlaps(
      &lines
    );
    assert_eq!(num_overlaps, 4)
  }
}

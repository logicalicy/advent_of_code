package test.app.forest;
import junit.framework.TestCase;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.forest.Forest;
import app.forest.Tree;

public class ForestTest extends TestCase {
  Tree[] trees;
  Tree[][] smForestTrees;
  Tree[][] mdForestTrees;
  Tree[][] lgForestTrees;
  Tree[][] longForestTrees;
  protected void setUp() {
      trees = new Tree[] {
          new Tree(1),
          new Tree(2),
          new Tree(4),
          new Tree(4),
          new Tree(8),
          new Tree(2)
      };
      smForestTrees = new Tree[][]{
          new Tree[]{ new Tree(1), new Tree(1), new Tree(1) },
          new Tree[]{ new Tree(1), new Tree(2), new Tree(1) },
          new Tree[]{ new Tree(1), new Tree(1), new Tree(1) },
      };
      mdForestTrees = new Tree[][]{
          new Tree[]{ new Tree(9), new Tree(5), new Tree(6), new Tree(8) },
          new Tree[]{ new Tree(6), new Tree(2), new Tree(3), new Tree(7) },
          new Tree[]{ new Tree(5), new Tree(6), new Tree(9), new Tree(3) },
          new Tree[]{ new Tree(8), new Tree(3), new Tree(7), new Tree(5) },
      };
      lgForestTrees = new Tree[][]{
          new Tree[]{ new Tree(1), new Tree(8), new Tree(9), new Tree(4), new Tree(9) },
          new Tree[]{ new Tree(9), new Tree(9), new Tree(3), new Tree(9), new Tree(6) },
          new Tree[]{ new Tree(9), new Tree(2), new Tree(4), new Tree(3), new Tree(2) },
          new Tree[]{ new Tree(7), new Tree(1), new Tree(9), new Tree(8), new Tree(1) },
      };
      longForestTrees = new Tree[][]{
          new Tree[]{ new Tree(3), new Tree(0), new Tree(3), new Tree(7), new Tree(3) },
          new Tree[]{ new Tree(2), new Tree(5), new Tree(5), new Tree(1), new Tree(2) },
          new Tree[]{ new Tree(6), new Tree(5), new Tree(3), new Tree(3), new Tree(2) },
          new Tree[]{ new Tree(3), new Tree(3), new Tree(5), new Tree(4), new Tree(9) },
          new Tree[]{ new Tree(3), new Tree(5), new Tree(3), new Tree(9), new Tree(0) },
      };
  }
  public void testGetMaxHeights() {
      int[] actual = Forest.getMaxHeights(this.trees);
      int[] expected = new int[] {
        0,
        1,
        2,
        4,
        4,
        8,
      };
      assertArrayEquals(
        expected,
        actual
      );
      List<Tree> reversedTreeList = Arrays.asList(this.trees);
      Collections.reverse(reversedTreeList);
      actual = Forest.getMaxHeights(reversedTreeList.toArray(new Tree[0]));
      expected = new int[] {
        0,
        2,
        8,
        8,
        8,
        8,
      };
      assertArrayEquals(
        expected,
        actual
      );
  }
  public void testCalcVisibilities() {
      Boolean[] actual = Forest.calcVisibilities(this.trees);
      Boolean[] expected = new Boolean[] {
        true,
        true,
        true,
        false,
        true,
        true,
      };
      assertArrayEquals(
        expected,
        actual
      );
  }
  public void testGetVisibleTreeCount() {
      Forest smForest = new Forest(this.smForestTrees);
      int actual = smForest.getVisibleTreeCount();
      int expected = 9;
      assertEquals(
        expected,
        actual
      );

      Forest mdForest = new Forest(this.mdForestTrees);
      actual = mdForest.getVisibleTreeCount();
      expected = 14;
      assertEquals(
        expected,
        actual
      );

      Forest lgForest = new Forest(this.lgForestTrees);
      actual = lgForest.getVisibleTreeCount();
      expected = 19;
      assertEquals(
        expected,
        actual
      );

      Forest longForest = new Forest(this.longForestTrees);
      actual = longForest.getVisibleTreeCount();
      expected = 21;
      assertEquals(
        expected,
        actual
      );
  }
  public void testCalcVisibleTreesToLeft() {
    int actual = Forest.calcVisibleTreesToLeft(0, this.trees);
    int expected = 0;
    assertEquals(
      expected,
      actual
    );
    actual = Forest.calcVisibleTreesToLeft(2, this.trees);
    expected = 2;
    assertEquals(
      expected,
      actual
    );
    actual = Forest.calcVisibleTreesToLeft(3, this.trees);
    expected = 1;
    assertEquals(
      expected,
      actual
    );
    actual = Forest.calcVisibleTreesToLeft(4, this.trees);
    expected = 4;
    assertEquals(
      expected,
      actual
    );
    Tree[] recurringTrees = new Tree[] {
        new Tree(3),
        new Tree(5),
        new Tree(3),
        new Tree(5),
        new Tree(3),
    };
    actual = Forest.calcVisibleTreesToLeft(3, recurringTrees);
    expected = 2;
    assertEquals(
      expected,
      actual
    );
    actual = Forest.calcVisibleTreesToLeft(2, this.mdForestTrees[1]);
    expected = 2;
    assertEquals(
      expected,
      actual
    );
  }
  public void testCalcScenicScoresOneDirection() {
    int[] actual = Forest.calcScenicScoresOneDirection(this.trees);
    int[] expected = new int[] {
      0, 1, 2, 1, 4, 1
    };
    assertArrayEquals(
      expected,
      actual
    );
    List<Tree> reversedTreesList = Arrays.asList(this.trees.clone());
    Collections.reverse(reversedTreesList);
    Tree[] reversedTrees = reversedTreesList.toArray(new Tree[0]);
    actual = Forest.calcScenicScoresOneDirection(reversedTrees);
    expected = new int[] {
      0, 1, 1, 1, 1, 1
    };
    assertArrayEquals(
      expected,
      actual
    );
  }
  public void testCalcScenicScores() {
    int[] actual = Forest.calcScenicScores(this.trees);
    int[] expected = new int[] {
      0, 1, 2, 1, 4, 0
    };
    assertArrayEquals(
      expected,
      actual
    );
    // Row #1: [2, 5, 5, 1, 2].
    actual = Forest.calcScenicScores(this.longForestTrees[1]);
    expected = new int[] {
      0, 1, 2, 1, 0
    };
    assertArrayEquals(
      expected,
      actual
    );
    Tree[] recurringTrees = new Tree[] {
        new Tree(3),
        new Tree(5),
        new Tree(3),
        new Tree(5),
        new Tree(3),
    };
    actual = Forest.calcScenicScores(recurringTrees);
    expected = new int[] {
      0, 2, 1, 2, 0
    };
  }
  public void testGetScenicScores() {
    Forest forest = new Forest(this.smForestTrees);
    int[][] actual = forest.getScenicScores();
    int[][] expected = new int[][] {
      new int[] { 0, 0, 0, },
      new int[] { 0, 1, 0, },
      new int[] { 0, 0, 0, }
    };
    assertArrayEquals(
      expected,
      actual
    );
    forest = new Forest(this.mdForestTrees);
    actual = forest.getScenicScores();
    expected = new int[][] {
      new int[] { 0, 0, 0, 0 },
      new int[] { 0, 1, 2, 0 },
      new int[] { 0, 2, 4, 0 },
      new int[] { 0, 0, 0, 0 }
    };
    assertArrayEquals(
      expected,
      actual
    );
    forest = new Forest(this.longForestTrees);
    actual = forest.getScenicScores();
    expected = new int[][] {
      new int[] { 0, 0, 0, 0, 0 },
      new int[] { 0, 1, 4, 1, 0 },
      new int[] { 0, 6, 1, 2, 0 },
      new int[] { 0, 1, 8, 3, 0 },
      new int[] { 0, 0, 0, 0, 0 }
    };
    assertArrayEquals(
      expected,
      actual
    );
  }
}

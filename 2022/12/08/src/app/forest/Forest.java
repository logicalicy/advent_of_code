package app.forest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Forest {
    private Tree[][] trees;
    public Forest(Tree[][] trees) {
        this.trees = trees;
    }
    public static int[] getMaxHeights(Tree[] row) {
        int[] maxHeights = new int[row.length];
        if (row.length == 1) {
          return new int[] {0};
        }
        for (int i = 0; i < row.length; i += 1) {
            boolean firstCol = i == 0;
            if (firstCol) {
                maxHeights[i] = 0;
            } else {
                maxHeights[i] = Math.max(maxHeights[i - 1], row[i - 1].getHeight());
            }
        }
        return maxHeights;
    }
    public static Boolean[] calcVisibilities(Tree[] trees) {
        int numCols = trees.length;
        Boolean[] visibilities = new Boolean[numCols];
        int[] maxHeightsLeftToRight = Forest.getMaxHeights(trees);
        for (int i = 0; i < numCols; i += 1) {
          boolean firstCol = i == 0;
          Tree currentTree = trees[i];
          int currentHeight = currentTree.getHeight();
          Boolean isVisible = currentHeight > maxHeightsLeftToRight[i];
          visibilities[i] = firstCol || isVisible;
        }
        List<Tree> reversedTreeList = Arrays.asList(trees.clone());
        Collections.reverse(reversedTreeList);
        int[] maxHeightsRightToLeft = Forest.getMaxHeights(reversedTreeList.toArray(new Tree[0]));
        for (int i = 0; i < numCols; i += 1) {
            boolean firstCol = i == 0;
            Tree currentTree = reversedTreeList.get(i);
            int currentHeight = currentTree.getHeight();
            Boolean isVisible = currentHeight > maxHeightsRightToLeft[i];
            visibilities[numCols - i - 1] = firstCol || visibilities[numCols - i - 1] || isVisible;
        }
        return visibilities;
    }
    public int getVisibleTreeCount() {
        int numRows = this.trees.length;
        int numCols = this.trees[0].length;
        Boolean[][] visibilities = new Boolean[numRows][numCols];
        for (int i = 0; i < numRows; i += 1) {
            Tree[] treeRow = this.trees[i];
            visibilities[i] = Forest.calcVisibilities(treeRow);
        }
        Tree[][] transposed = new TreeMatrix(this.trees).transpose();
        numRows = transposed.length;
        numCols = transposed[0].length;
        for (int i = 0; i < numRows; i += 1) {
            Tree[] transposedRow = transposed[i];
            Boolean[] localVisibilities = Forest.calcVisibilities(transposedRow);
            for (int j = 0; j < numCols; j += 1) {
                visibilities[j][i] = visibilities[j][i] || localVisibilities[j];
            }
        }
        // System.out.println("Visibilities:");
        // for (int idx = 0; idx < visibilities.length; idx += 1) {
        //     System.out.print(Arrays.toString(visibilities[idx]));
        //     System.out.print("\n");
        // }
        numRows = this.trees.length;
        numCols = this.trees[0].length;
        int count = 0;
        for (int i = 0; i < numRows; i += 1) {
            for (int j = 0; j < numCols; j += 1) {
                count += visibilities[i][j] ? 1 : 0;
            }
        }
        return count;
    }
    public static int calcVisibleTreesToLeft(int idx, Tree[] trees) {
        int visibleCount = 0;
        int currentHeight = trees[idx].getHeight();
        for (int i = idx; i >= 0; i -= 1) {
            if (i == 0) {
              break;
            }
            int prevHeight = trees[i - 1].getHeight();
            visibleCount += 1;
            if (prevHeight >= currentHeight) {
              break;
            }
        }
        return visibleCount;
    }
    public static int[] calcScenicScoresOneDirection(Tree[] trees) {
      int numCols = trees.length;
      int[] visibleTreeCount = new int[numCols];
      for (int i = 0; i < numCols; i += 1) {
          boolean firstCol = i == 0;
          if (firstCol) {
              visibleTreeCount[i] = 0;
          } else {
              Tree prevTree = trees[i - 1];
              Tree currentTree = trees[i];
              int prevHeight = prevTree.getHeight();
              int currentHeight = currentTree.getHeight();
              int visibleTreesCount = Forest.calcVisibleTreesToLeft(i, trees);
              visibleTreeCount[i] = (prevHeight < currentHeight ? visibleTreesCount : 1);
          }
      }
      return visibleTreeCount;
    }
    public static int[] calcScenicScores(Tree[] trees) {
        int[] visibleTreeCountLeftToRight = Forest.calcScenicScoresOneDirection(trees);
        List<Tree> reversedTreeList = Arrays.asList(trees.clone());
        Collections.reverse(reversedTreeList);
        Tree[] reversedTrees = reversedTreeList.toArray(new Tree[0]);
        int[] visibleTreeCountRightToLeft = Forest.calcScenicScoresOneDirection(reversedTrees);
        int numCols = trees.length;
        int[] scores = new int[numCols];
        for (int i = 0; i < numCols; i += 1) {
            scores[i] = visibleTreeCountLeftToRight[i] * visibleTreeCountRightToLeft[numCols - i - 1];
        }
        return scores;
    }
    public int[][] getScenicScores() {
        int numRows = this.trees.length;
        int numCols = this.trees[0].length;
        int[][] rowScenicScores = new int[numRows][numCols];
        for (int i = 0; i < numRows; i += 1) {
            Tree[] treeRow = this.trees[i];
            rowScenicScores[i] = Forest.calcScenicScores(treeRow);
        }
        Tree[][] transposed = new TreeMatrix(this.trees.clone()).transpose();
        int[][] colScenicScores = new int[numCols][numRows];
        System.out.println("Transposed:");
        for (int j = 0; j < numCols; j += 1) {
          Tree[] treeCol = transposed[j];
          System.out.println(Arrays.toString(treeCol));
          colScenicScores[j] = Forest.calcScenicScores(treeCol);
        }

        int[][] scenicScores = new int[numRows][numCols];
        for (int i = 0; i < numRows; i += 1) {
            for (int j = 0; j < numCols; j += 1) {
                scenicScores[i][j] = rowScenicScores[i][j] * colScenicScores[j][i];
            }
        }
        System.out.println("Rows:");
        for (int i = 0; i < numRows; i += 1) {
            System.out.println(Arrays.toString(rowScenicScores[i]));
        }
        System.out.println("Cols:");
        for (int j = 0; j < numCols; j += 1) {
            System.out.println(Arrays.toString(colScenicScores[j]));
        }
        System.out.println("Scores:");
        for (int idx = 0; idx < scenicScores.length; idx += 1) {
            System.out.print(Arrays.toString(scenicScores[idx]));
            System.out.print("\n");
        }
        return scenicScores;
    }
    public int getMaxScenicScore() {
        int[][] scenicScores = this.getScenicScores();
        int maxScore = 0;
        for (int i = 0; i < scenicScores.length; i += 1) {
            for (int j = 0; j < scenicScores[0].length; j += 1) {
                maxScore = Math.max(maxScore, scenicScores[i][j]);
            }
        }
        return maxScore;
    }
}
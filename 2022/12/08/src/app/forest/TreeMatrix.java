package app.forest;
public class TreeMatrix {
  private Tree[][] matrix;
  public TreeMatrix(Tree[][] matrix) {
      this.matrix = matrix;
  }
  public Tree[][] transpose() {
      int numRows = this.matrix.length;
      int numCols = this.matrix[0].length;
      Tree[][] newMatrix = new Tree[numCols][numRows];
      for (int i = 0; i < numRows; i += 1) {
          for (int j = 0; j < numCols; j += 1) {
              newMatrix[j][i] = this.matrix[i][j];
          }
      }
      return newMatrix;
  }
}

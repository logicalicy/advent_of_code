package app.forest;
public class Tree {
    private int height;
    public Tree(int height) {
        this.height = height;
    }
    public int getHeight() {
        return this.height;
    }
    public String toString() {
      return " 🌲" + this.height + " ";
    }
}
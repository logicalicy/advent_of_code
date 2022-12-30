package app.forest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import app.utils.FileReader;

public class Part2 {
    public static void main(String args[]) {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath + "/src/app/resources/input.txt";

        FileReader reader = new FileReader(filePath);
        String content;
        try {
            content = reader.getContent();
            System.out.println("Read content...");
            ForestParser parser = new ForestParser(content);
            Tree[][] trees = parser.parse();
            System.out.println("Parsed content...");
            for (int treeRowIdx = 0; treeRowIdx < trees.length; treeRowIdx += 1) {
                System.out.print(Arrays.toString(trees[treeRowIdx]));
                System.out.print("\n");
            }
            Forest forest = new Forest(trees);
            Integer maxScenicScore = forest.getMaxScenicScore();
            System.out.print("Max scenic score:\n");
            System.out.println(maxScenicScore);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
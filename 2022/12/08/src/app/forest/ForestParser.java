package app.forest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ForestParser {
    private String content;
    public ForestParser(String content) {
        this.content = content;
    }
    private static Tree[] toTrees(List<String> line) {
        List<Tree> trees = line.stream()
          .filter(s -> {
            String str = s.trim();
            return str != null && str.length() > 0;
          })
          .map(s -> {
              return new Tree( Integer.parseInt(s));
          })
          .collect(Collectors.toList());
        Tree[] emptyArray = new Tree[0]; // Allocates zero-length array.
        return trees.toArray(emptyArray);
    }
    public Tree[][] parse() {
        List<String> lines = Arrays.asList(content.split("\n"));
        List<Tree[]> trees = lines.stream()
            .map(s -> Arrays.asList(s.split("")))
            .collect(Collectors.toList())
            .stream()
            .map(line -> ForestParser.toTrees(line))
            .collect(Collectors.toList());
        Tree[][] emptyMatrix = new Tree[0][0]; // Allocates zero-length matrix.
        return trees.toArray(emptyMatrix);
    }
}

package oreilly.fj;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class Shakespeare {

    public static void main(String[] args) {
        var lines = getLines();
        System.out.println("Total lines: "+ lines.size());

        var hasRose = Pattern.compile("rose", Pattern.CASE_INSENSITIVE).asPredicate();
        var start = System.nanoTime();
        var total = lines.parallelStream()
                        .filter(hasRose)
                        .count();
        var end = System.nanoTime();
        System.out.println("Lines that talk about roses: "+ total +" ; "+ (end - start));
    }

    private static List<String> getLines() {
        try {
            Path p = Path.of(Shakespeare.class.getResource("/complete_works_of_shakespeare.txt").toURI());
            return Files.readAllLines(p);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}

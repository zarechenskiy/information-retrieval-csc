package ru.csc.ir.index;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.index.impl.CoordinateIndex;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.impl.CoordinateDocument;
import ru.csc.ir.structure.impl.TermImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Indexer {

    private final Index<Term, CoordinateDocument> index;

    public Indexer(@NotNull Index<Term, CoordinateDocument> index) {
        this.index = index;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("There are not enough parameters: " + args.length);
            return;
        }

        File folder = new File(args[0]);
        if (!folder.isDirectory()) {
            System.out.println("First parameter is not path to the directory: " + args[0]);
        }

        indexDirectory(args[1], folder, new Indexer(new CoordinateIndex()));
    }

    private static void indexDirectory(@NotNull String resultIndex, @NotNull File folder, @NotNull Indexer indexer) {
        indexer.indexDirectory(folder);
        indexer.index.saveIndex(new File(resultIndex));
    }

    private void indexDirectory(@NotNull File folder) {
        //noinspection ConstantConditions
        Arrays.stream(folder.listFiles()).forEach(this::indexFile);
    }

    private void indexFile(@NotNull File file)  {
        try {
            Scanner scanner = new Scanner(file);
            int coordinate = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.replaceAll("\\?", "");

                String[] words = line.split("\\|");

                final int c = coordinate;
                Arrays.stream(words).forEach(w -> index.indexTerm(new TermImpl(w), new CoordinateDocument(file, c)));

                coordinate++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

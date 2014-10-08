package ru.csc.ir.search;

import org.jetbrains.annotations.Nullable;
import ru.csc.ir.index.impl.InvertedIndex;
import ru.csc.ir.structure.DocumentCollection;
import ru.csc.ir.structure.query.SearchQuery;
import ru.csc.ir.structure.query.parser.ErrorParsingException;
import ru.csc.ir.structure.query.parser.QueryParser;

import java.io.File;
import java.util.Scanner;

public class Searcher {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("There are not enough parameters: " + args.length);
            System.out.println("Single parameter should specify path to index");
            return;
        }

        SearchEngine searchEngine = buildSearchEngine(args[0]);
        if (searchEngine == null) {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                SearchQuery query = (QueryParser.INSTANCE).parse(scanner.nextLine());
                DocumentCollection result = searchEngine.search(query);
                System.out.println(result);
            } catch (ErrorParsingException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Nullable
    private static SearchEngine buildSearchEngine(String pathToIndex) {
        System.out.println("Loading data...");
        SearchEngine searchEngine = new SearchEngine(new InvertedIndex());
        File file = new File(pathToIndex);

        if (!file.exists()) {
            System.out.println("File " + pathToIndex + " not exists");
            return null;
        }

        searchEngine.loadIndex(file);
        System.out.println("Ready, enter queries:");

        return searchEngine;
    }
}

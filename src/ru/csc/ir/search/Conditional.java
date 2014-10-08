package ru.csc.ir.search;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Document;
import ru.csc.ir.structure.DocumentCollection;

import java.util.Set;

public interface Conditional {
    @NotNull
    default DocumentCollection and(@NotNull DocumentCollection doc1, @NotNull DocumentCollection doc2) {
        return doc1.intersect(doc2);
    }

    @NotNull
    default DocumentCollection or(@NotNull DocumentCollection doc1, @NotNull DocumentCollection doc2) {
        return doc1.unite(doc2);
    }
}

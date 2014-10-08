package ru.csc.ir.structure;

import org.jetbrains.annotations.NotNull;

public interface DocumentCollection extends Iterable<Document> {
    void add(@NotNull Document document);

    @NotNull
    DocumentCollection intersect(@NotNull DocumentCollection documentCollection);

    @NotNull
    DocumentCollection unite(@NotNull DocumentCollection documentCollection);

    int size();
}

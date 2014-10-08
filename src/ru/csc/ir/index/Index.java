package ru.csc.ir.index;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.DocumentCollection;
import ru.csc.ir.structure.Term;

import java.io.File;

public interface Index<T extends Term, R> {
    void indexTerm(@NotNull T term, @NotNull R declaringData);

    @NotNull
    DocumentCollection findDocuments(@NotNull T term);

    void saveIndex(@NotNull File file);

    void readIndex(@NotNull File file);
}

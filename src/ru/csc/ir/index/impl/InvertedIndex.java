package ru.csc.ir.index.impl;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.index.Index;
import ru.csc.ir.structure.Document;
import ru.csc.ir.structure.DocumentCollection;
import ru.csc.ir.structure.Term;
import ru.csc.ir.structure.impl.DocumentSet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InvertedIndex implements Index<Term, Document> {

    private final Map<Term, DocumentCollection> index = new HashMap<>();

    @Override
    public void indexTerm(@NotNull Term term, @NotNull Document declaringData) {
        if (!index.containsKey(term)) {
            index.put(term, new DocumentSet());
        }
        index.get(term).add(declaringData);
    }

    @NotNull
    @Override
    public DocumentCollection findDocuments(@NotNull Term term) {
        return index.containsKey(term) ? index.get(term) : new DocumentSet();
    }

    @Override
    public void saveIndex(@NotNull File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(index);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readIndex(@NotNull File file) {
        try {
            index.clear();

            FileInputStream inputStream = new FileInputStream(file);

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            @SuppressWarnings("unchecked")
            Map<Term, DocumentCollection> object = index.getClass().cast(objectInputStream.readObject());
            objectInputStream.close();

            assert object != null : "Obtained index object from file is null";
            index.putAll(object);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

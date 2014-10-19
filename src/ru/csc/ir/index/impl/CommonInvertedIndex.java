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

public class CommonInvertedIndex<T extends Term, D extends Document, C extends DocumentCollection> implements Index<T, D> {

    private final Map<T, C> index = new HashMap<>();

    @Override
    public void indexTerm(@NotNull T term, @NotNull D declaringData) {
        if (!index.containsKey(term)) {
            index.put(term, (C) new DocumentSet());
        }
        index.get(term).add(declaringData);
    }

    @NotNull
    @Override
    public DocumentCollection findDocuments(@NotNull T term) {
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
            Map<T, C> object = index.getClass().cast(objectInputStream.readObject());
            objectInputStream.close();

            assert object != null : "Obtained index object from file is null";
            index.putAll(object);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
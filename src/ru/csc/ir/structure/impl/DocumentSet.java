package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Document;
import ru.csc.ir.structure.DocumentCollection;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DocumentSet implements DocumentCollection, Serializable {

    private static final long serialVersionUID = 6672601782915135044L;

    private final Set<Document> documents;

    public DocumentSet() {
        documents = new HashSet<>();
    }

    public DocumentSet(@NotNull Set<Document> documents) {
        this();
        this.documents.addAll(documents);
    }

    @Override
    public void add(@NotNull Document document) {
        documents.add(document);
    }

    @NotNull
    @Override
    public DocumentCollection intersect(@NotNull DocumentCollection documentCollection) {
        if (documentCollection.size() < size()) {
            return documentCollection.intersect(this);
        }

        HashSet<Document> docs1 = new HashSet<>(documents);
        docs1.retainAll(((DocumentSet) documentCollection).documentsSet());

        return new DocumentSet(docs1);
    }

    @NotNull
    @Override
    public DocumentCollection unite(@NotNull DocumentCollection documentCollection) {
        if (size() < documentCollection.size()) {
            return documentCollection.unite(this);
        }

        HashSet<Document> docs1 = new HashSet<>(documents);
        docs1.addAll(((DocumentSet) documentCollection).documentsSet());

        return new DocumentSet(docs1);
    }

    @Override
    public int size() {
        return documents.size();
    }

    @NotNull
    public Set<Document> documentsSet() {
        return documents;
    }

    @Override
    public Iterator<Document> iterator() {
        return documents.iterator();
    }

    @Override
    public String toString() {
        if (documents.isEmpty()) {
            return "No documents found";
        }

        StringBuilder builder = new StringBuilder("found ");

        int i = 0;
        for (Document document : documents) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(document.getPath());
            i++;

            if (i > 1) {
                break;
            }
        }

        if (documents.size() > 2) {
            builder
                    .append(" and ")
                    .append(documents.size() - 2)
                    .append(" more");
        }

        return builder.toString();
    }
}

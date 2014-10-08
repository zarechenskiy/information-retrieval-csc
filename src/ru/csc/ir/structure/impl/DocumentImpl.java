package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Document;

import java.io.File;
import java.io.Serializable;

public class DocumentImpl implements Document, Serializable {

    private static final long serialVersionUID = 822801085949057570L;

    private final File file;

    public DocumentImpl(File file) {
        this.file = file;
    }

    @NotNull
    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof DocumentImpl)) return false;

        return ((DocumentImpl) obj).file.equals(file);
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}

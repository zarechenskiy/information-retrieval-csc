package ru.csc.ir.structure.impl;

import java.io.File;

public class CoordinateDocument extends DocumentImpl {
    private final int position;

    public CoordinateDocument(File file, int coordinate) {
        super(file);
        this.position = coordinate;
    }

    public int getPosition() {
        return position;
    }
}

package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;

public class CoordinateTerm extends TermImpl {
    private final int coordinate;

    public CoordinateTerm(@NotNull String term, int coordinate) {
        super(term);
        this.coordinate = coordinate;
    }

    public int getCoordinate() {
        return coordinate;
    }
}

package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.query.parser.ErrorParsingException;

public class CoordinateOpImpl extends OperatorImpl {
    private static final long serialVersionUID = -6178101539250895573L;

    public static enum DIRECTION {
        LEFT, RIGHT, ANY
    }

    private DIRECTION direction;
    private int distance;

    public CoordinateOpImpl(@NotNull String term) {
        super(term);
        assert term.startsWith("\\") : "Coordinate term should starts with \\";

        match(term);

    }

    @NotNull
    @Override
    public TYPE getType() {
        return TYPE.DIST;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }

    private void match(String t) {
        direction = matchDirection(t.charAt(1));
        distance = matchDistance(t.substring(1));
    }

    private int matchDistance(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch(Exception e) {
            throw new ErrorParsingException("Could not parse coordinate operator: " + getValue());
        }
    }

    private DIRECTION matchDirection(char d) {
        if (Character.isDigit(d)) {
            return DIRECTION.ANY;
        } else if (d == '+') {
            return DIRECTION.RIGHT;
        } else if (d == '-') {
            return DIRECTION.LEFT;
        }

        throw new ErrorParsingException("Could not parse coordinate operator: " + getValue());
    }
}

package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.csc.ir.structure.Operator;

import java.util.Arrays;

public class OperatorImpl extends TermImpl implements Operator {
    private static final long serialVersionUID = 1337188061492914710L;

    private final TYPE operator;

    public OperatorImpl(@NotNull String term) {
        super(term);
        operator = matchOperator(term);

        assert operator != null : "Operator can not match with: " + term;
    }

    @NotNull
    public TYPE getType() {
        return operator;
    }

    @Nullable
    private TYPE matchOperator(@NotNull String term) {
        for (TYPE element : TYPE.values()) {
            if (element.getName().equals(term)) {
                return element;
            }
        }

        return null;
    }
}

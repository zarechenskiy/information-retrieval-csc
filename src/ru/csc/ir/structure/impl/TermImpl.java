package ru.csc.ir.structure.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.csc.ir.structure.Operator;
import ru.csc.ir.structure.Term;

import java.io.Serializable;

public class TermImpl implements Term, Serializable {

    private static final long serialVersionUID = 8140137844855372789L;

    private final String term;

    public TermImpl(String term) {
        this.term = term;
    }

    @NotNull
    @Override
    public String getValue() {
        return term;
    }

    @NotNull
    public static Term create(@NotNull String term) {
        for (Operator.TYPE operator : Operator.TYPE.values()) {
            if (operator.getName().equals(term)) {
                return new OperatorImpl(term);
            }
        }

        return new TermImpl(term);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof TermImpl)) return false;

        return ((TermImpl) obj).getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return term.hashCode();
    }
}

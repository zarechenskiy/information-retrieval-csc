package ru.csc.ir.structure;

import org.jetbrains.annotations.NotNull;

public interface Operator extends Term {
    public static enum TYPE {
        AND("AND"), OR("OR");

        private final String operatorName;

        TYPE(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getName() {
            return operatorName;
        }
    }

    @NotNull
    TYPE getType();
}

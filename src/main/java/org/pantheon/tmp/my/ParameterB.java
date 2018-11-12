package org.pantheon.tmp.my;

public enum ParameterB {
    B_1("B_1"),
    B_2("B_2");

    private String value;

    ParameterB(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
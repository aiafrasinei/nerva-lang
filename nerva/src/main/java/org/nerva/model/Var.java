package org.nerva.model;

public class Var {
    public String name;
    public char type;
    public Object value;

    public Var(String name, char type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}

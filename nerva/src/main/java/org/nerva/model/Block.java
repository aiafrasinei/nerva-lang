package org.nerva.model;

import java.util.ArrayList;

public class Block {
    private final String name;
    private ArrayList<Object> statements;

    public Block(String name, ArrayList<Object> statements) {
        this.name = name;
        this.statements = statements;
    }

    public ArrayList<Object> getStatements() {
        return statements;
    }

    public boolean containsVar(String name) {
        boolean found = false;

        for(Object obj: statements) {
            if (obj instanceof Var) {
                if(((Var) obj).name.equals(name)) {
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    public Var getVar(String name) {
        Var ret = null;

        for(Object obj: statements) {
            if (obj instanceof Var) {
                if(((Var) obj).name.equals(name)) {
                    ret = (Var) obj;
                }
            }
        }

        return  ret;
    }
}

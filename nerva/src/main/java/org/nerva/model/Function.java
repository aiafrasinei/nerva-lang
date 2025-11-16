package org.nerva.model;

import java.util.ArrayList;

public class Function {
    public String name;
    public char returnType;
    public ArrayList<Var> params;
    public Block block;

    public Function(String name, char returnType, ArrayList<Var> params, Block block) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
        this.block = block;
    }
}

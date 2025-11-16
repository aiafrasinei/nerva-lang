package org.nerva;

import org.nerva.model.containers.Blocks;

import java.util.ArrayList;
import java.util.List;

public class StdLibrary {
    public static void handlePrint(Blocks blocks, List<String> tokens) {
        if(tokens.get(0).equals("println")) {
            System.out.println(tokens.get(1));
        } else if(tokens.get(0).equals("printf")) {
            String format = tokens.get(1);
            ArrayList<Object> args = new ArrayList<>();
            for(int i = 2; i < tokens.size(); i++) {
                args.add(tokens.get(i));
            }
            System.out.printf(format, args.toArray());
        }
    }
}

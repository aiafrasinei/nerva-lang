package org.nerva;

import org.nerva.model.Var;
import org.nerva.model.containers.Blocks;

import java.util.ArrayList;
import java.util.List;

public class Handlers {
    private static void handleDefaultVarAssignment(Blocks blocks, String input) {
        String varName = ParserUtils.getVarName(input);
        String annotationString = ParserUtils.getAnnotationString(input);

        if(blocks.getLast().containsVar(varName)) {
            System.out.println("ERR - Variable already defined , name: " + varName);
            return;
        }

        ArrayList<Object> statements = blocks.getLast().getStatements();

        if (annotationString.equals("i")) {
            statements.add(new Var(varName, 'i', 0));
        } else if (annotationString.equals("b")) {
            statements.add(new Var(varName, 'b', Boolean.FALSE));
        } else if (annotationString.equals("r")) {
            statements.add(new Var(varName, 'r', 0.0));
        } else if (annotationString.equals("s")) {
            statements.add(new Var(varName, 's', ""));
        } else if (annotationString.equals("t")) {
            statements.add(new Var(varName, 't', null));
        } else if (annotationString.equals("f")) {
            statements.add(new Var(varName, 'f', null));
        } else {
            System.out.println("ERR - Unknown type annotation , found: " + annotationString);
        }
    }

    private static void handleVarAssignment(Blocks blocks, String token, Object value) {
        String name = ParserUtils.getVarName(token);
        String annotationString = ParserUtils.getAnnotationString(token);

        if(annotationString.length() == 1) {
            if(ParserUtils.isStandardTypeAnnotation(annotationString.charAt(0))) {
                Var var = blocks.getLast().getVar(name);
                if(var != null) {
                    var.value = value;
                } else {
                    blocks.getLast().getStatements().add(new Var(name, annotationString.charAt(0), value));
                }
            }
        } else {
            System.out.println("ERR - Unknown type annotation , found: " + annotationString);
        }
    }

    public static void handleFunctDef(Blocks blocks, String input) {

    }

    public static void handleFunctCall(Blocks blocks, List<String> tokens) {
        StdLibrary.handlePrint(blocks, tokens);
    }

    public static void handleStatement(Blocks blocks, String input) {
        List<String> tokens = ParserUtils.splitSpecial(input);
        if(tokens.get(0).contains("_")) {
            if(tokens.size() == 1) {
                Handlers.handleDefaultVarAssignment(blocks, input);
            } else {
                String annotationString = ParserUtils.getAnnotationString(tokens.get(0));
                if(annotationString.equals("f")) {
                    Handlers.handleFunctDef(blocks, input);
                } else {
                    if(tokens.get(1).equals("=")) {
                        Handlers.handleVarAssignment(blocks, tokens.get(0), tokens.get(2));
                    }
                }
            }
        } else {
            Handlers.handleFunctCall(blocks, tokens);
        }
    }
}

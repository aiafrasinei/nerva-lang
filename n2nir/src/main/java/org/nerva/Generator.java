package org.nerva;

import java.io.PrintWriter;
import java.util.List;

import static org.nerva.ParserUtils.*;

public class Generator {
    public static int indentLevel = 0;

    public static void genProgramStart(PrintWriter outp, String inf) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\"\n" , indentString(indentLevel), inf);
        outp.printf("%s\"data\" : [\n", indentString(indentLevel));
        indentLevel++;
    }

    public static void genProgramEnd(PrintWriter outp) {
        indentLevel--;
        outp.printf("%s]\n", indentString(indentLevel));
        outp.println("}");
        outp.close();
    }

    /*{
        "name" : "name",
        "type" : "Var",
        "data" : {}
    }*/
    public static void genVar(PrintWriter outp, String name, String data, boolean last) {
        indentLevel++;
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\"\n" , indentString(indentLevel), name);
        outp.printf("%s\"type\" : \"%s\"\n" , indentString(indentLevel), "Var");
        outp.printf("%s\"data\" : \"%s\"\n" , indentString(indentLevel), data);
        indentLevel--;
        if(last)
            outp.printf("%s}\n", indentString(indentLevel));
        else
            outp.printf("%s},\n", indentString(indentLevel));
        indentLevel--;
    }

    /*{
        "name" : "",
        "type" : "Var",
        "data" : {}
    }*/
    public static void genFunctCallParam(PrintWriter outp, String data, boolean last) {
        genVar(outp, "", data, last);
    }

    /*{
        "name" : "",
        "type"  : "FunctDef",
        "inputs" : [],
        "outputs" : [],
        "code" : []
    }*/
    public static void genFunctDef(PrintWriter outp, N2nir.LineData data) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\"\n" , indentString(indentLevel), data.tokens.getFirst());
        outp.printf("%s\"type\" : \"%s\"\n" , indentString(indentLevel), "FunctDef");
        indentLevel--;
        outp.printf("%s}\n", indentString(indentLevel));
    }

    /*{
        "name" : "",
        "type"  : "FunctCall",
        "inputs" : [],
        "outputs" : []
    }*/
    public static void genFunctCall(PrintWriter outp, N2nir.LineData data, String name) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\"\n" , indentString(indentLevel), name);
        outp.printf("%s\"type\" : \"%s\"\n" , indentString(indentLevel), "FunctCall");
        outp.printf("%s\"inputs\" : [\n", indentString(indentLevel));

        for(int i=1;i<data.tokens.size(); i++) {
            genFunctCallParam(outp, data.tokens.get(i), i == data.tokens.size() - 1);
        }

        outp.printf("%s],\n", indentString(indentLevel));
        outp.printf("%s\"outputs\" : [\n", indentString(indentLevel));
        outp.printf("%s]\n", indentString(indentLevel));
        indentLevel--;
        outp.printf("%s}\n", indentString(indentLevel));
    }

    public static void genFunctCall(PrintWriter outp, N2nir.LineData data) {
        genFunctCall(outp, data, data.tokens.getFirst());
    }

    public static void genVarAssignment(PrintWriter outp, N2nir.LineData data) {
        genVar(outp, data.tokens.getFirst(), data.tokens.get(2), true);
    }

    public static void genIf(PrintWriter outp, N2nir.LineData data) {

    }

    public static void genFunctionDeclaration(PrintWriter outp, N2nir.LineData data) {

    }

    public static void genVar(PrintWriter outp, N2nir.LineData data, boolean last) {
        String firstToken = data.tokens.getFirst();
        int underscoreIndex = firstToken.indexOf('_');
        char annotationChar = getAnnotationChar(firstToken);
        if (isTypeAnnotation(annotationChar)) {
            genVar(outp, firstToken.substring(0, underscoreIndex), "0", last);
        } else {
            System.out.println("ERR - Wrong type annotation");
        }
    }

    private static void genVar(PrintWriter outp, List<N2nir.LineData> linesData, N2nir.LineData data, int counter) {
        String firstToken = data.tokens.getFirst();
        if (firstToken.contains("_")) {
            if(counter == linesData.size()-1) {
                genVar(outp, data, true);
            } else {
                genVar(outp, data, false);
            }
        }
    }

    static void genProgram(PrintWriter outp, List<N2nir.LineData> linesData) {
        int counter = -1;
        
        for(N2nir.LineData data : linesData) {
            counter++;
            String firstToken = data.tokens.getFirst();
            if(data.tokens.size() == 1) {
                genVar(outp, linesData, data, counter);
            } else {
                if(data.tokens.get(1).equals("=")) {
                    if(firstToken.contains("_")) {
                        char annotationChar = getAnnotationChar(firstToken);
                        if(annotationChar == 'f') {
                            genFunctionDeclaration(outp, data);
                        } else {
                            genVarAssignment(outp, data);
                        }
                    }
                } else if (firstToken.equals("if")) {
                    genIf(outp, data);
                } else {
                    genFunctCall(outp, data);
                }
            }
        }
    }
}

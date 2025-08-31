package org.nerva;

import java.io.PrintWriter;
import java.util.List;

import static org.nerva.ParserUtils.*;

public class Generator {
    public static int globalIndentLevel = 0;

    public static void genProgramStart(PrintWriter outp, String inf) {
        outp.printf("%s{\n", indentString(globalIndentLevel));
        globalIndentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(globalIndentLevel), inf);
        outp.printf("%s\"data\" : [\n", indentString(globalIndentLevel));
        globalIndentLevel++;
    }

    public static void genProgramEnd(PrintWriter outp) {
        globalIndentLevel--;
        outp.printf("%s]\n", indentString(globalIndentLevel));
        outp.println("}");
        outp.close();
    }

    public static void genBlockStart(PrintWriter outp, int indentLevel) {
        outp.printf("%s{\n", indentString(indentLevel));
    }

    public static void genBlockEnd(PrintWriter outp, int indentLevel) {
        outp.printf("%s}\n", indentString(indentLevel));
    }

    /*{
        "name" : "name",
        "blocktype" : "Var",
        "datatype" : "type"
        "data" : {}
    }*/
    public static void genVar(PrintWriter outp, String name, String data, int indentLevel) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(indentLevel), name);
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "Var");
        outp.printf("%s\"datatype\" : \"%s\",\n" , indentString(indentLevel), "Var");
        outp.printf("%s\"data\" : \"%s\"\n" , indentString(indentLevel), data);
        indentLevel--;
        outp.printf("%s}\n", indentString(indentLevel));
    }

    /*{
        "name" : "",
        "blocktype" : "Var",
        "data" : {}
    }*/
    public static void genFunctCallParam(PrintWriter outp, String data, int indentLevel) {
        genVar(outp, "", data, indentLevel);
    }

    /*{
        "name" : "",
        "blocktype" : "FunctDef",
        "inputs" : [],
        "outputs" : [],
        "code" : []
    }*/
    public static void genFunctDef(PrintWriter outp, N2nir.LineData data, int indentLevel) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(indentLevel), data.tokens.getFirst());
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "FunctDef");
        indentLevel--;
        outp.printf("%s},\n", indentString(indentLevel));
    }

    /*{
        "name" : "",
        "blocktype"  : "FunctCall",
        "inputs" : [],
        "outputs" : []
    }*/
    public static void genFunctCall(PrintWriter outp, N2nir.LineData data, String name, int indentLevel) {
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(indentLevel), name);
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "FunctCall");
        outp.printf("%s\"inputs\" : [\n", indentString(indentLevel));

        indentLevel++;
        for(int i=1;i<data.tokens.size(); i++) {
            genFunctCallParam(outp, data.tokens.get(i), indentLevel);
        }
        indentLevel--;

        outp.printf("%s],\n", indentString(indentLevel));
        outp.printf("%s\"outputs\" : [\n", indentString(indentLevel));
        outp.printf("%s]\n", indentString(indentLevel));
        indentLevel--;
        outp.printf("%s}\n", indentString(indentLevel));
    }

    public static void genFunctCall(PrintWriter outp, N2nir.LineData data, int indentLevel) {
        genFunctCall(outp, data, data.tokens.getFirst(), indentLevel);
    }

    public static void genVarAssignment(PrintWriter outp, N2nir.LineData data, int indentLevel) {
        genVar(outp, data.tokens.getFirst(), data.tokens.get(2), indentLevel);
    }

    public static void genIf(PrintWriter outp, N2nir.LineData data, int indentLevel) {

    }

    public static void genFunctionDeclaration(PrintWriter outp, N2nir.LineData data, int indentLevel) {
        String firstToken = data.tokens.getFirst();
        genBlockStart(outp, indentLevel);
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\"\n" , indentString(indentLevel), getVarName(firstToken));
        outp.printf("%s\"blocktype\" : \"%s\"\n" , indentString(indentLevel), "FunctDef");
        outp.printf("%s\"inputs\" : [\n", indentString(indentLevel));
        outp.printf("%s],\n", indentString(indentLevel));
        outp.printf("%s\"outputs\" : [\n", indentString(indentLevel));
        outp.printf("%s]\n", indentString(indentLevel));
        indentLevel--;
        outp.printf("%s},\n", indentString(indentLevel));
        genBlockEnd(outp, indentLevel);
    }

    /*public static void genFunctCallParamVar(PrintWriter outp, String data, int indentLevel) {
        if(data.contains("_")) {
            int underscoreIndex = data.indexOf('_');
            char annotationChar = getAnnotationChar(data);
            if (isTypeAnnotation(annotationChar)) {
                genVar(outp, data.substring(0, underscoreIndex), "0", indentLevel);
            } else {
                System.out.println("ERR - Wrong blocktype annotation");
            }
        } else {
            genVar(outp, "", data, indentLevel);
        }
    }*/

    static void genProgram(PrintWriter outp, List<N2nir.LineData> linesData) {
        int counter = -1;
        
        for(N2nir.LineData data : linesData) {
            counter++;
            String firstToken = data.tokens.getFirst();
            if(data.tokens.size() == 1) {
                genVar(outp, data.tokens.getFirst(), "", globalIndentLevel);
            } else {
                if(data.tokens.get(1).equals("=")) {
                    if(firstToken.contains("_")) {
                        char annotationChar = getAnnotationChar(firstToken);
                        if(annotationChar == 'f') {
                            genFunctionDeclaration(outp, data, globalIndentLevel);
                        } else {
                            genVarAssignment(outp, data, globalIndentLevel);
                        }
                    }
                } else if (firstToken.equals("if")) {
                    genIf(outp, data, globalIndentLevel);
                } else {
                    genFunctCall(outp, data,globalIndentLevel);
                }
            }
        }
    }
}

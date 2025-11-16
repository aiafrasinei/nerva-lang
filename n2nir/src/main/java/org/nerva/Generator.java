package org.nerva;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.nerva.ParserUtils.*;

public class Generator {
    public static int globalIndentLevel = 0;
    public static boolean globalLast = false;

    public static void genProgramStart(PrintWriter outp, String inf) {
        outp.printf("%s{\n", indentString(globalIndentLevel));
        globalIndentLevel++;
        Path infPath = Paths.get(inf);
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(globalIndentLevel), infPath.getFileName().toString());
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
        char annotationChar = '0';
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        if (name.contains("_")) {
            outp.printf("%s\"name\" : \"%s\",\n", indentString(indentLevel), ParserUtils.getVarName(name));
        } else {
            outp.printf("%s\"name\" : \"%s\",\n", indentString(indentLevel), name);
        }
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "Var");
        if(name.contains("_")) {
            annotationChar = getAnnotationChar(name);
            if (isStandardTypeAnnotation(annotationChar)) {
                outp.printf("%s\"datatype\" : \"%s\",\n" , indentString(indentLevel), annotationChar);
            } else {
                String annotation = getAnnotationString(name);
                outp.printf("%s\"datatype\" : \"%s\",\n" , indentString(indentLevel), "");
                System.out.println("ERR - Unknown datatype annotation: " + annotation);
                throw new RuntimeException("ERR - Unknown datatype annotation: " + annotation);
            }
        } else {
            outp.printf("%s\"datatype\" : \"%s\",\n" , indentString(indentLevel), "");
        }

        if(ParserUtils.isNumeric(data)) {
            if (annotationChar == 'i') {
                outp.printf("%s\"data\" : %d\n", indentString(indentLevel), Integer.parseInt(data));
            } else if (annotationChar == 'r') {
                outp.printf("%s\"data\" : %f\n", indentString(indentLevel), Double.parseDouble(data));
            } else {
                outp.printf("%s\"data\" : \"%s\"\n", indentString(indentLevel), data);
            }
        } else if(!data.isEmpty()) {
            //function
            outp.printf("%s\"data\" : \"%s\"\n", indentString(indentLevel), data);
        } else {
            //default init
            if(annotationChar == 'b' ) {
                if(!data.equals("true") && !data.equals("false") && !data.isEmpty()) {
                    throw new RuntimeException("ERR - Wrong boolean assignment must be true or false");
                }

                outp.printf("%s\"data\" : %b\n", indentString(indentLevel), false);
            } else if(annotationChar == 'i') {
                if(!data.isEmpty()) {
                    throw new RuntimeException("ERR - Wrong integer assignment");
                }
                outp.printf("%s\"data\" : %d\n", indentString(indentLevel), 0);
            } else if(annotationChar == 'r') {
                if(!data.isEmpty()) {
                    throw new RuntimeException("ERR - Wrong real assignment");
                }
                outp.printf("%s\"data\" : %f\n", indentString(indentLevel), 0.0);
            } else {
                outp.printf("%s\"data\" : \"%s\"\n", indentString(indentLevel), data);
            }
        }

        indentLevel--;
        if(globalLast) {
            outp.printf("%s}\n", indentString(indentLevel));
        } else {
            outp.printf("%s},\n", indentString(indentLevel));
        }
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
        "outputs" : []
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
    public static void genFunctCall(PrintWriter outp, N2nir.LineData lineData, String name, int indentLevel) {
        boolean localLast = globalLast;
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(indentLevel), name);
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "FunctCall");
        outp.printf("%s\"inputs\" : [\n", indentString(indentLevel));

        indentLevel++;
        for(int i=1;i<lineData.tokens.size(); i++) {
            if(i == lineData.tokens.size()-1) {
                globalLast = true;
            }
            genFunctCallParam(outp, lineData.tokens.get(i), indentLevel);
            globalLast = false;
        }
        indentLevel--;

        outp.printf("%s],\n", indentString(indentLevel));
        outp.printf("%s\"outputs\" : [\n", indentString(indentLevel));
        outp.printf("%s]\n", indentString(indentLevel));
        indentLevel--;

        if(localLast) {
            outp.printf("%s}\n", indentString(indentLevel));
        } else {
            outp.printf("%s},\n", indentString(indentLevel));
        }
    }

    public static void genFunctCall(PrintWriter outp, N2nir.LineData data, int indentLevel) {
        genFunctCall(outp, data, data.tokens.getFirst(), indentLevel);
    }

    public static void genVarAssignment(PrintWriter outp, N2nir.LineData lineData, int indentLevel) {
        genVar(outp, lineData.tokens.getFirst(), lineData.tokens.get(2), indentLevel);
    }

    public static void genVarDefaultAssignment(PrintWriter outp, N2nir.LineData lineData, int indentLevel) {
        genVar(outp, lineData.tokens.getFirst(), "", indentLevel);
    }

    public static void genIf(PrintWriter outp, N2nir.LineData data, int indentLevel) {

    }

    public static void genFunctionDeclaration(PrintWriter outp, N2nir.LineData lineData, int indentLevel) {
        String firstToken = lineData.tokens.getFirst();
        outp.printf("%s{\n", indentString(indentLevel));
        indentLevel++;
        outp.printf("%s\"name\" : \"%s\",\n" , indentString(indentLevel), getVarName(firstToken));
        outp.printf("%s\"blocktype\" : \"%s\",\n" , indentString(indentLevel), "FunctDef");
        outp.printf("%s\"inputs\" : [\n", indentString(indentLevel));

        indentLevel++;
        int countParams = 0;
        for(int i=1;i<lineData.tokens.size(); i++) {
            if(i == lineData.tokens.size()-2) {
                globalLast = true;
            }

            if (i == 1) {
                if(!lineData.tokens.get(i).equals(",")) {
                    throw new RuntimeException("ERR - Wrong syntax for function declaration");
                }
            }
            if(Collections.frequency(lineData.tokens, ",") != 3) {
                throw new RuntimeException("ERR - Wrong syntax for function declaration");
            }

            if(!lineData.tokens.get(i).equals(",")) {
                genFunctCallParam(outp, lineData.tokens.get(i), indentLevel);
                countParams++;
            }
            globalLast = false;
        }
        indentLevel--;

        outp.printf("%s],\n", indentString(indentLevel));
        outp.printf("%s\"outputs\" : [\n", indentString(indentLevel));
        outp.printf("%s]\n", indentString(indentLevel));
        indentLevel--;
        outp.printf("%s},\n", indentString(indentLevel));
    }

    static void genProgram(PrintWriter outp, List<N2nir.LineData> linesData) {
        int counter = -1;
        
        for(N2nir.LineData lineData : linesData) {
            counter++;
            if(counter == linesData.size()-1) {
                globalLast = true;
            }
            String firstToken = lineData.tokens.getFirst();
            if(lineData.tokens.size() == 1) {
                genVarDefaultAssignment(outp, lineData, globalIndentLevel);
            } else {
                if(firstToken.contains("_")) {
                    char annotationChar = getAnnotationChar(firstToken);
                    if(annotationChar == 'f') {
                        genFunctionDeclaration(outp, lineData, globalIndentLevel);
                    } else {
                        genVarAssignment(outp, lineData, globalIndentLevel);
                    }
                } else {
                    genFunctCall(outp, lineData, globalIndentLevel);
                }
            }
            globalLast = false;
        }
    }
}

package org.nerva;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.nerva.Generator.*;
import static org.nerva.ParserUtils.splitSpecial;


public class N2nir {
    public static class LineData {
        public String line;
        public List<String> tokens;

        LineData(String line, List<String> tokens) {
            this.line = line;
            this.tokens = tokens;
        }
    }

    private static boolean checkArgs(String[] args) {
        boolean ret = true;

        if (args.length == 0 || args.length == 1) {
            System.out.println("ERR - Missing parameters! N2nir [input file] [output file]");
            ret = false;
        }

        if (args.length > 2) {
            System.out.println("ERR - Too many parameters");
            ret = false;
        }

        return ret;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("n2nir\n");

        if (checkArgs(args)) {
            // initialize parameters
            String inf = args[0];
            Path imputPath = Paths.get(inf);
            List<String> lines = Files.readAllLines(imputPath);

            String outf = args[1];
            FileWriter outfw = new FileWriter(outf);
            PrintWriter outp = new PrintWriter(outfw);

            //initialize linesData
            List<LineData> linesData = new ArrayList<>();
            for(String line : lines) {
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                linesData.add(new LineData(line, splitSpecial(line)));
            }

            //generate nir representation
            genProgramStart(outp, inf);

            genProgram(outp, linesData);

            genProgramEnd(outp);
        } else {
            System.exit(1);
        }
    }
}
package org.nerva;

import org.nerva.model.Block;
import org.nerva.model.containers.Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.nerva.Handlers.handleStatement;

/**
 * Nerva repl
 */
public class Nerva {
    private static final String VERSION = "0.1.0";

    private static Blocks blocks;

    public static void init() {
        blocks = new Blocks();
        blocks.push(new Block("Global", new ArrayList<>()));

        String url = "jdbc:sqlite:Global.db";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void handle(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            handleStatement(blocks, input);
        }
    }

    public static void main(String[] args) {
        System.out.println("Nerva " + VERSION + "\n");

        init();

        Scanner scanner;
        if(args.length == 1) {
            File input = new File(args[0]);
            try {
                scanner = new Scanner(input);
                handle(scanner);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            scanner = new Scanner(System.in);
            handle(scanner);
        }
        scanner.close();
    }
}

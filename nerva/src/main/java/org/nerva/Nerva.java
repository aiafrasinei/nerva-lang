package org.nerva;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Nerva repl
 */
public class Nerva {
    private static String VERSION = "0.1.0";

    public static class Global {
    }

    public static void init() {
        String url = "jdbc:sqlite:Global.db";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Nerva " + VERSION);
        System.out.print("> ");

        init();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.print("> ");
            String input = scanner.nextLine();
        }
        scanner.close();
    }
}

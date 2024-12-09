import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class InitializeDatabase {
    public static void initializeDatabase(String url, String username, String password, String sqlFilePath) {
        try {
            // First Connect to the MySQL server (without specifying a database)
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {
                System.out.println("Connected to MySQL server!");

                // Next Create the database if it doesn't exist
                String createDbQuery = "CREATE DATABASE IF NOT EXISTS PC_Builder_DB";
                stmt.execute(createDbQuery);
//                System.out.println("Executed: " + createDbQuery);  // Debugging
            }

            // Connect to the newly created or existing database
            try (Connection conn = DriverManager.getConnection(url + "PC_Builder_DB", username, password)) {
                System.out.println("Connected to PC_Builder_DB!");

                // Read and execute SQL file to initialize the database
                String sqlCommands = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
                String[] commands = sqlCommands.split(";");

                // Execute all the non-INSERT commands (CREATE TABLE)
                try (Statement stmt = conn.createStatement()) {
                    for (String command : commands) {
                        if (!command.trim().isEmpty() && !command.trim().startsWith("INSERT")) {
                            stmt.execute(command.trim());
//                            System.out.println("Executed: " + command.trim()); // Debugging
                        }
                    }
                }

                // Check if data already exists in the tables
                String checkDataQuery = "SELECT COUNT(*) FROM USER_ACCOUNT";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(checkDataQuery)) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("Data already exists in the tables. Skipping initialization.");
                    } else {
                        // Execute all the INSERT commands since data doesn't exist
                        try (Statement stmt2 = conn.createStatement()) {
                            for (String command : commands) {
                                if (command.trim().startsWith("INSERT")) {
                                    stmt2.execute(command.trim());
//                                    System.out.println("Inserted data: " + command.trim()); // Debugging
                                }
                            }
                        }
                    }
                }
                System.out.println("Database initialized successfully!");
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error initializing the database: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java InitializeDatabase <url> <username> <password> <sqlFilePath>");
            return;
        }
        initializeDatabase(args[0], args[1], args[2], args[3]);
    }

    // TODO: either make new file for queries or add them in this file (to handle all database updates)
}

import java.sql.*;

public class UserInteraction {
    private DatabaseConfig dbConfig;

    public UserInteraction(DatabaseConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    /*
    * Validates the user login credentials.
     */
    public boolean validateLogin(String inputUsername, String inputPassword) {
        String query = "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, inputUsername);
            stmt.setString(2, inputPassword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Login successful!");
                    return true; // Valid user
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating login: " + e.getMessage());
        }
        return false; // Invalid user
    }

    /*
    * Get the computer ID associated with the user.
     */
    public int getUserComputerId(String inputUsername) {
    String query = """
        SELECT c.ComputerID
        FROM USER_ACCOUNT u
        JOIN COMPUTER c ON u.Username = c.AccUsername
        WHERE u.Username = ?
        """;
    try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, inputUsername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ComputerID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving computer ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    /*
     * Register a new user in the system.
     */
    public boolean registerUser(String inputUsername, String inputPassword, String inputComputerName) {
        // Before registering, check if the username is already taken
        String checkUsernameQuery = "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement checkStmt = conn.prepareStatement(checkUsernameQuery)) {

            checkStmt.setString(1, inputUsername);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Username is already taken. Please choose a different one.");
                    return false; // Username already exists
                }
            }

            // Insert the new user into the USER_ACCOUNT table
            String insertUserQuery = "INSERT INTO USER_ACCOUNT (Username, Password) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {
                insertStmt.setString(1, inputUsername);
                insertStmt.setString(2, inputPassword);
                insertStmt.executeUpdate();
            }

            // Insert the new computer associated with the user
            String insertComputerQuery = "INSERT INTO COMPUTER (AccUsername, ComputerName) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertComputerQuery)) {
                insertStmt.setString(1, inputUsername);
                insertStmt.setString(2, inputComputerName);
                insertStmt.executeUpdate();
            }

            System.out.println("User and computer successfully registered!");
            return true;

        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
        }
        return false; // Registration failed
    }



}

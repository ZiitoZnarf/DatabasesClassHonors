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
        String query = "SELECT computer_id FROM USER_ACCOUNT WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, inputUsername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    try {
                        return rs.getInt("computer_id");
                    } catch (SQLException e) {
                        System.err.println("Column 'computer_id' does not exist: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving computer ID: " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found or if column does not exist
    }
}

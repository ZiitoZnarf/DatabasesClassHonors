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


    /*
    * Get User details from the database. (Username, Password, ComputerName)
     */
    public String getUserDetail(String inputUsername, String detailType) {
        String query = "SELECT " + detailType + " FROM USER_ACCOUNT WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, inputUsername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(detailType);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving " + detailType + ": " + e.getMessage());
        }
        return null;
    }

    /*
    * Change the username of the user in the system.
     */
    public boolean changeUsername(String currentUsername, String newUsername) {
        // Check if the new username already exists
        if (isUsernameTaken(newUsername)) {
            System.out.println("Username is already taken. Please choose a different one.");
            return false; // Username already exists
        }

        // Update related computer entries first
        if (!updateComputerUsername(currentUsername, newUsername)) {
            return false; // If there was an issue updating the computer records
        }

        // Update the user account with the new username
        if (updateUserAccountUsername(currentUsername, newUsername)) {
            System.out.println("Username changed successfully!");
            return true; // Successfully updated username
        } else {
            System.out.println("Error changing username.");
            return false;
        }
    }

    /*
    * Check if the new username is already taken.
     */
    private boolean isUsernameTaken(String newUsername) {
        String checkUsernameQuery = "SELECT COUNT(*) FROM USER_ACCOUNT WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement checkStmt = conn.prepareStatement(checkUsernameQuery)) {

            checkStmt.setString(1, newUsername);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Username is already taken
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }
        return false; // Username is available
    }

    /*
    * Update the computer records with the new username.
     */
    private boolean updateComputerUsername(String currentUsername, String newUsername) {
        String updateComputerQuery = "UPDATE COMPUTER SET AccUsername = ? WHERE AccUsername = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement updateStmt = conn.prepareStatement(updateComputerQuery)) {

            updateStmt.setString(1, newUsername);
            updateStmt.setString(2, currentUsername);
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Successfully updated computer record
            } else {
                System.err.println("No computer records were updated.");
                return false; // No computer records were found with the current username
            }
        } catch (SQLException e) {
            System.err.println("Error updating computer record: " + e.getMessage());
        }
        return false;
    }

    /*
    * Update the user account with the new username.
     */
    private boolean updateUserAccountUsername(String currentUsername, String newUsername) {
        String updateUserQuery = "UPDATE USER_ACCOUNT SET username = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement stmt = conn.prepareStatement(updateUserQuery)) {

            stmt.setString(1, newUsername);
            stmt.setString(2, currentUsername);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Check if the update was successful
        } catch (SQLException e) {
            System.err.println("Error updating user account username: " + e.getMessage());
        }
        return false;
    }

    /*
    * Change the password of the user in the database.\
     */
    public boolean changePassword(String currentUsername, String oldPassword, String newPassword) {
        // Check if the current password matches the old password
        String checkPasswordQuery = "SELECT password FROM USER_ACCOUNT WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement checkStmt = conn.prepareStatement(checkPasswordQuery)) {

            checkStmt.setString(1, currentUsername);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    String currentPassword = rs.getString("password");

                    // Validate that the old password matches
                    if (!currentPassword.equals(oldPassword)) {
                        System.out.println("Old password is incorrect.");
                        return false; // Incorrect old password
                    }
                } else {
                    System.out.println("User not found.");
                    return false; // User not found
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking password: " + e.getMessage());
            return false;
        }

        //TODO: Add password complexity requirements
        // Make sure the new password is at least 4 characters long (arbitrary minimum length)
        if (newPassword == null || newPassword.length() < 4) {
            System.out.println("New password must be at least 4 characters long.");
            return false; // Password too short
        }

        // Update the password in the database
        String updatePasswordQuery = "UPDATE USER_ACCOUNT SET password = ? WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
             PreparedStatement updateStmt = conn.prepareStatement(updatePasswordQuery)) {

            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, currentUsername);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password updated successfully.");
                return true; // Successfully updated password
            } else {
                System.out.println("Error updating password.");
                return false; // Error updating password
            }
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }



}

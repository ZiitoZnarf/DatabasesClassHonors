public class DatabaseConfig {
    private final String baseUrl; // Base URL without the database name
    private final String username;
    private final String password;

    public DatabaseConfig(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    // Getters for jdbcUrl, username, and password

    // Get the base URL without the database name
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Get the full JDBC URL, this is the base URL + database name
    public String getJdbcUrl() {
        // Default database name
        String databaseName = "PC_Builder_DB";
        return baseUrl + databaseName;
    }
}

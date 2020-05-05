package Database;

public class DatabaseConnection {
    private static DatabaseConnection ourInstance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return ourInstance;
    }

    private DatabaseConnection() {
    }
}

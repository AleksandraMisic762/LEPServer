package rs.ac.bg.fon.ai.npserver.repository.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectionFactory {

	private String scope = "production";
    private Connection connection;
    private static DBConnectionFactory instance;

    private DBConnectionFactory() {
    }

    public static DBConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DBConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/dbconfig.properties"));
            String url = properties.getProperty(scope + "_url");
            String username = properties.getProperty(scope + "_username");
            String password = properties.getProperty(scope + "_password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
    
    public void setScope(String scope) {
		this.scope = scope;
	}
}

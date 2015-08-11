import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zcfrank1st on 8/11/15.
 */
public enum ConnectMan {
    INSTANCE;

    private static Properties config;
    static {
        InputStream in = ConnectMan.class.getResourceAsStream("connection.properties");
        config = new Properties();
        try {
            config.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JdbcObject getJdbcObject(String connectProperty) {
        String ip = config.getProperty(connectProperty +".ip");
        String port = config.getProperty(connectProperty + ".port");
        String username = config.getProperty(connectProperty + ".username");
        String password = config.getProperty(connectProperty + ".password");

        JdbcObject jdbcObject = new JdbcObject();
        jdbcObject.setIp(ip);
        jdbcObject.setPort(port);
        jdbcObject.setUsername(username);
        jdbcObject.setPassword(password);

        return jdbcObject;
    }

    private String getJdbcUri (String connectProperty, JdbcObject jdbcObject) throws ClassNotFoundException {
        if (connectProperty.contains("mysql")) {
            Class.forName("com.mysql.jdbc.Driver");
            return "jdbc:mysql://" + jdbcObject.getIp() + ":" + jdbcObject.getPort()
                    + "?useUnicode=true&characterEncoding=utf8";
        } else if (connectProperty.contains("sqlserver")) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return "jdbc:sqlserver://" + jdbcObject.getIp() + ":" + jdbcObject.getPort();
        } else {
            throw new RuntimeException("not support jdbc uri, current support mysql and sqlserver");
        }
    }

    public Connection getConnetion(String connectProperty) throws SQLException, ClassNotFoundException {
        JdbcObject jdbcObject = getJdbcObject(connectProperty);
        String jdbcUri = getJdbcUri(connectProperty, jdbcObject);

        return DriverManager.getConnection(jdbcUri, jdbcObject.getUsername(), jdbcObject.getPassword());
    }

    public List<String> getAllDatabases (String connectProperty) throws SQLException, ClassNotFoundException {
        Connection con = getConnetion(connectProperty);
        DatabaseMetaData meta = con.getMetaData();
        ResultSet rs = meta.getCatalogs();

        List<String> databases = new ArrayList<String>();
        while (rs.next()) {
            databases.add(rs.getString("TABLE_CAT"));
        }
        rs.close();
        return databases;
    }
}

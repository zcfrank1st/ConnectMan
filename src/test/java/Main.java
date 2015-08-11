import java.sql.SQLException;

/**
 * Created by zcfrank1st on 8/11/15.
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        for (String table : ConnectMan.INSTANCE.getAllDatabases("mysql_test")) {
            System.out.println(table);
        };
    }
}

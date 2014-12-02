package squui.gui.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import squui.log.Log;

public class ConnectionWrapper {

    public String user;
    public String host;
    public String pass;
    public Connection conn;
    public ArrayList<Schema> schemas;

    public ConnectionWrapper() {
        schemas = new ArrayList<Schema>();
        user = "root";
        pass = "cvscvs";
        host = "127.0.0.1";
    }

    public void connect() {
        if (conn == null) {
            try {
                String connStr = "jdbc:mysql://" + host + "?" +
                    "autoReconnect=true&user=" + user + "&password=" + pass;

                Log.error(connStr);
                conn = DriverManager.getConnection(connStr);
            } catch (SQLException ex) {
                conn = null;
                // handle any errors
                Log.error("SQLException: " + ex.getMessage());
                Log.error("SQLState: " + ex.getSQLState());
                Log.error("VendorError: " + ex.getErrorCode());
            }
        }
        if (conn == null)
            throw new RuntimeException("Could not connect to MySQL");
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Log.error(e);
            }
            conn = null;
        }
    }

}

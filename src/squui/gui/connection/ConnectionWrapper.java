package squui.gui.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    public ResultSet sql(String sql) throws SQLException {
        PreparedStatement st;
        st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        return rs;
    }
    
    public ArrayList<Schema> getSchemas() {
        schemas = new ArrayList<Schema>();
        try {
            PreparedStatement st = conn.prepareStatement("SHOW databases;");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Schema schema = new Schema();
                schema.name = rs.getString(1);
                schemas.add(schema);
            }
        } catch (SQLException e) {
            Log.error(e);
        }
        return schemas;
    }

}

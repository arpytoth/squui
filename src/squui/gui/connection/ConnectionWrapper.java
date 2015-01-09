package squui.gui.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import squui.log.Log;

public class ConnectionWrapper {

    private ConnSettings settings;
    public Connection conn;
    public ArrayList<Schema> schemas;

    public ConnectionWrapper(ConnSettings settings) {
        schemas = new ArrayList<Schema>();
        this.settings = settings;
    }

    public void connect() {
        if (conn == null) {
            try {
                String connStr = "jdbc:mysql://" + settings.host + "?" +
                    "autoReconnect=true&user=" + settings.user + "&password=" + 
                    settings.pass;

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
    
    public void checkForSelectedSchema() {
        try {
            ResultSet rs = sql("SELECT DATABASE()");
            while (rs.next()) {
                String name = rs.getString(1);
                if (name == null)
                    return;
                for (Schema s : schemas) {
                    if (name.equals(s.name)) 
                        s.isSelected = true;
                    else
                        s.isSelected = false;
                }
            }
        } catch (SQLException e) {
            Log.error(e);
        }
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement st;
        st = conn.prepareStatement(sql);
        return st;
    }
    
    public ResultSet sql(String sql) throws SQLException {
        PreparedStatement st;
        st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        return rs;
    }
    
    public int update(String sql) throws SQLException {
        PreparedStatement st;
        st = conn.prepareStatement(sql);
        int rs = st.executeUpdate();
        return rs;
    }
    
    public void getColumns(Table table) {
        Schema schema = table.schema;
        table.columns = new ArrayList<Column>();
        try {
            PreparedStatement s;
            s = conn.prepareStatement("SELECT COLUMN_NAME, DATA_TYPE, " +
            		"IS_NULLABLE, COLUMN_KEY FROM information_schema.columns " +
            		"WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ? ORDER BY " +
            		"ORDINAL_POSITION;");
            
            s.setString(1, table.name);
            s.setString(2, schema.name);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Column column = new Column();
                column.table = table;
                column.name = rs.getString(1);
                column.type = rs.getString(2);
                column.isNullable = rs.getBoolean(3);
                column.isColumnKey = rs.getString(4).equalsIgnoreCase("PRI");
                table.columns.add(column);
            }
        } catch (Exception e) {
            Log.error(e);
        }
      }
    
    
    public ArrayList<Table> getTables(Schema schema) {
        schema.tables = new ArrayList<Table>();
        try {
            PreparedStatement s;
            s = conn.prepareStatement("SELECT TABLE_NAME FROM " +
            		"information_schema.tables WHERE TABLE_SCHEMA = ?;");
            s.setString(1, schema.name);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.schema = schema;
                table.name = rs.getString(1);
                schema.tables.add(table);
                getColumns(table);
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return schema.tables;
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
                getTables(schema);
            }
            checkForSelectedSchema();
        } catch (SQLException e) {
            Log.error(e);
        }
        return schemas;
    }

    public Schema getSchema(String name) {
        ArrayList<Schema> schemas = getSchemas();
        Schema defaultSchema = null;
        for (Schema s : schemas) {
            if (s.name.equals(name))
                return s;
            
            if (s.isSelected) 
                defaultSchema = s;
        }
        return defaultSchema;
    }
}

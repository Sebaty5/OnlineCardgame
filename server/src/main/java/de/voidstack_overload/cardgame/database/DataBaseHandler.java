package de.voidstack_overload.cardgame.database;

import de.voidstack_overload.cardgame.objects.ExitCode;
import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DataBaseHandler {
    private static final StandardLogger LOGGER = new StandardLogger("Database Handler");
    private static final String path = "./server/database/database.db";
    private static final String dbUrl = "jdbc:sqlite:" + path;

    public static final DataBaseHandler INSTANCE = new DataBaseHandler();

    private final ArrayList<String> tableNames = new ArrayList<>();

    private DataBaseHandler()
    {
        File file = new File(path);
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        LOGGER.log("Initializing database...");
        createNewDatabase();
        Table.Builder tableBuilder = new Table.Builder("users");
        tableBuilder.addField("id", FieldType.INTEGER, true, true, true);
        tableBuilder.addField("username", FieldType.STRING, false, false, true);
        tableBuilder.addField("password", FieldType.STRING, false, false, true);
        Table table = tableBuilder.build();
        createNewTable(table);
        LOGGER.log("Database initialization complete.");
    }

    private void createNewDatabase() {
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.log("Can't locate driver Class.");
        }
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                LOGGER.log("The driver name is " + meta.getDriverName());
                LOGGER.log("A new database has been created.");
            } else {
                LOGGER.error("Database not available and failed to create.");
                System.exit(ExitCode.DATABASE_ERROR.getCode());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.exit(ExitCode.DATABASE_ERROR.getCode());
        }
    }

    public void init() {

    }

    public void createNewTable(Table table) {
        StringBuilder sqlString = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (");
        int i = 0;
        for (Field field : table.getFields()) {
            if(i > 0)  sqlString.append(",");
            sqlString.append("\n").append(field.getName()).append(" ").append(field.getType().toString());
            if(field.getPrimary()) {
                sqlString.append(" PRIMARY KEY");
                if(field.getType() == FieldType.INTEGER && field.getAutoIncrement()) {
                    sqlString.append(" AUTOINCREMENT");
                }
            }
            if(field.getNotNull()) sqlString.append(" NOT NULL");
            i++;
        }
        sqlString.append("\n);");
        if(executeSQL(sqlString.toString())) tableNames.add(table.getTableName());
    }

    public boolean executeSQL(String sqlString, Object... params)
    {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement(sqlString);
                // Bind parameters dynamically
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]); // Indexes start from 1 in JDBC
                }
            statement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.exit(ExitCode.DATABASE_ERROR.getCode());
        }
        return false;
    }

    public List<Map<String, Object>> executeQuerySQL(String sqlString, Object... params)
    {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement(sqlString);
            // Bind parameters dynamically
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]); // Indexes start from 1 in JDBC
            }
            ResultSet resultSet = statement.executeQuery();

            return resultSetToArrayList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            System.exit(ExitCode.DATABASE_ERROR.getCode());
        }
        return null;
    }

    public List<Map<String, Object>> resultSetToArrayList(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; ++i) {
                row.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    public boolean isRegisteredUser(String username) {
        return !executeQuerySQL("SELECT * FROM users WHERE username = ?", username).isEmpty();
    }

    public boolean isValidLogin(String username, String password) {
        return !executeQuerySQL("SELECT * FROM users WHERE username = ? AND password = ?", username, password).isEmpty();
    }


    public void registerUser(String username, String password) {
        executeSQL("INSERT INTO users (username, password) VALUES (?, ?)", username, password);
    }


}

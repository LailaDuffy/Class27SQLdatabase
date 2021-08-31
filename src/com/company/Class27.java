package com.company;

import java.sql.*;

public class Class27 {
    public static void main(String[] args) {

        try {
            String dataBasePath = "jdbc:sqlite:C:\\Users\\laild\\IdeaProjects\\Class27SQLdatabase\\Class27SQLdatabase.db"; // create a database on the disk
            Connection connection = DriverManager.getConnection(dataBasePath); // create a connection to the database from IntelliJ

            if (connection != null) { // check that there were no errors while connecting to the database (variable is not empty)
                DatabaseMetaData dbMetaData = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Connected to the " + dbMetaData.getDatabaseProductName() + " " + dbMetaData.getDatabaseProductVersion()); // print to which data base it's connected

                // Creating a table (migration script)
                Statement statement = connection.createStatement(); // statement will be the box to write SQL in Java console
                String sqlStatement =
                        "CREATE TABLE groceries " +
                                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "expiry_date TEXT NOT NULL," +
                                "FOREIGN KEY (shop_id) REFERENCES shops(id) )";
                statement.execute(sqlStatement);

                // Insert first row
                sqlStatement = "INSERT INTO groceries (name, expiry_date) " +
                        "values ('juice', '2021-09-09')";
                statement.execute(sqlStatement);
                // as many times as "run" is pressed, this line will be added to the table same amount of times

                sqlStatement = "SELECT * FROM groceries";
                ResultSet resultSet = statement.executeQuery(sqlStatement);

                while (resultSet.next()) {
                    String productName = resultSet.getString("name");
                    String expiryDate = resultSet.getString("expiry_date");
                    int productID = resultSet.getInt("id");

                    System.out.println("ID: " + productID + "Name: " + productName + "Expiry date: " + expiryDate);
                }

                sqlStatement = "DELETE FROM groceries " +
                        "WHERE id = 2";
                statement.execute(sqlStatement);

                sqlStatement =
                        "CREATE TABLE shop " +
                                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "address TEXT NOT NULL)";
                statement.execute(sqlStatement);

                sqlStatement = "SELECT shops.name as sname, groceries.name as gname, groceries.expiry_date " +
                        "FROM shops LEFT JOIN groceries " +
                        "ON groceries.shop_id = shops.id";

                resultSet = statement.executeQuery(sqlStatement);

                while (resultSet.next()) {
                    String shopName = resultSet.getString("sname");
                    String productName = resultSet.getString("gname");
                    String expiryDate = resultSet.getString("expiry_date");
                    int productID = resultSet.getInt("id");

                    System.out.println("Shop: " + shopName + "Name: " + productName + "Expiry date: " + expiryDate);
                }
            }

        } catch (SQLException exception) {
            System.out.println("An error has occurred " + exception.toString());
        }
    }
}

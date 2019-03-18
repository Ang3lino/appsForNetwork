/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import foro.networking.Pack;
import foro.networking.UtilFun;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Sigma
 */
public class DBHelper {

    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DB_NAME = "forum";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER); // no es necesario a partir de JDBC 4 
                connection = (Connection) DriverManager.getConnection(
                        URL + DB_NAME, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static ArrayList<Pack> listPost() throws SQLException, IOException {
        System.out.println("List Post method called "); 
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet set = statement.executeQuery("call get_list_post()");

        ArrayList<Pack> list = new ArrayList<>();
        while (set.next()) {
            Pack pack = new Pack();
            pack.setTitle(set.getString("title"));
            pack.setDate(set.getString("post_date"));
            pack.setPostId(set.getInt("id"));
            list.add(pack);
        }

        set.close();

        list.forEach(p -> { System.out.println(p); } );

        return list;
    }

    public static ArrayList<Pack> findByKeyword(String keyword) throws SQLException, IOException {
        System.out.println("findBykeyword method called "); 

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String query = String.format("call find_by_keyword(\"%s\")", keyword); 
        System.out.println(query); 
        ResultSet set = statement.executeQuery(query);

        ArrayList<Pack> list = new ArrayList<>();
        while (set.next()) {
            Pack pack = new Pack();
            pack.setTitle(set.getString("title"));
            pack.setDate(set.getString("post_date"));
            pack.setPostId(set.getInt("id"));
            list.add(pack);
        }

        set.close();

        list.forEach(p -> { System.out.println(p); } );

        return list;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import foro.networking.Pack;
import foro.networking.UtilFun;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigma
 */
public class DBHelper {

    // Database parameters, change them if necessary
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DB_NAME = "forum";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    /**
     * 
     * @return safe connection (not null).
     */
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
        ResultSet set = statement.executeQuery("call get_list_post3()");

        ArrayList<Pack> list = new ArrayList<>();
        while (set.next()) {
            Pack pack = new Pack();
            pack.setPostId(set.getInt("id"));
            pack.setTitle(set.getString("title"));
            pack.setDate(set.getString("post_date"));
            pack.setNick(set.getString("nick"));
            pack.setTopic(set.getString("category"));
         //   pack.setmComment(set.getString("comment"));
           
            list.add(pack);
        }

        set.close();

        list.forEach(p -> { System.out.println(p); } );

        return list;
    }
    
    public static ArrayList<Pack> getComments(final int postId) 
			throws SQLException  {

		Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String query = String.format("call get_comments(%d)", postId); 
        System.out.println(query); 
        ResultSet set = statement.executeQuery(query);

        ArrayList< Pack > comments = new ArrayList<>();
        while (set.next()) {
            Pack comment = new Pack();
			comment.setNick(set.getString("nick"));
			comment.setmComment(set.getString("comment"));
			comments.add(comment);
	    }

        set.close();

        comments.forEach(p -> { System.out.println(p); } );
		
		return comments;
	}
    
    public static void appendComment(Pack pack) {
		try {
			System.out.println("appendComment method called ");
			Connection conn = getConnection();
			Statement statement = conn.createStatement();
			String nick = pack.getNick(), comment = pack.getmComment();
			int postId = pack.getPostId();
			String query = String.format(
					"call add_comment(%d, \"%s\", \"%s\")", postId, nick, comment);
			System.out.println(query);
			statement.executeQuery(query);
		} catch (SQLException ex) {
			Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
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

    public static void appendPost(Pack pack) throws SQLException, IOException {
        System.out.println("appendPost method called "); 

        Connection conn = getConnection();
        Statement statement = conn.createStatement();
	
        File file = pack.getImage();
        boolean hasFile = file != null;
        String query = String.format(
                "call add_post(\"%s\", \"%s\", \"%s\", \"%s\", %s)", 
                pack.getNick(), 
                pack.getTopic(), 
                pack.getTitle(), 
                pack.getDescription(), 
                (hasFile) ? "\"" + file.getName() + "\"" : "null" ); 
        System.out.println(query); 
        statement.executeQuery(query);
    }

    public static Pack getPost(int postId) throws SQLException {
        System.out.println("getPost method called "); 
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        String query = String.format("call get_post(%d)", postId); 
        System.out.println(query); 
        ResultSet set = statement.executeQuery(query);

        set.next(); // expect only one result, hence no while loop

        Pack pack = new Pack();
        String imgUrl = set.getString("img_url");
        File file = null;
        if (imgUrl != null) {
            String path = Paths.get("img", imgUrl).toString();
            file = new File(path);
        }
        pack.addPost(set.getString("nick"), set.getString("category"), 
                set.getString("title"), set.getString("description"), 
                imgUrl);
        pack.setFile(file);
	System.out.println(pack);
        set.close();
        return pack;
    }

}

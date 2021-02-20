import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Enumeration;

import java.sql.*;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement Pstmt;
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "user";
    String PASS = "password";
    int LoginAttempts = 3;
    // URL to connect to database
    String DB_URL = "jdbc:mysql://db:3306/lottery";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get a session in the servlet
        HttpSession session = request.getSession();

        //Removal of all the session attributes
        Enumeration<String> attributes = session.getAttributeNames();
        while( attributes.hasMoreElements()){
            String attribute = (String) attributes.nextElement();
            session.removeAttribute(attribute);

            if(!attribute.equals("LoginAttempts")){
                session.removeAttribute(attribute);
            }
        }


        try {
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // query database and get results
            ResultSet rs = stmt.executeQuery("SELECT * FROM userAccounts");

            // create HTML table text
            String content = "<table border='1' cellspacing='2' cellpadding='2' width='100%' align='left'>" +
                    "<tr><th>First name</th><th>Last name</th><th>Email</th><th>Phone number</th><th>Username</th><th>Password</th></tr>";

            // add HTML table data using data from database
            while (rs.next()) {
                content += "<tr><td>"+ rs.getString("Firstname") + "</td>" +
                        "<td>" + rs.getString("Lastname") + "</td>" +
                        "<td>" + rs.getString("Email") + "</td>" +
                        "<td>" + rs.getString("Phone") + "</td>" +
                        "<td>" + rs.getString("Username") + "</td>" +
                        "<td>" + rs.getString("Pwd") + "</td></tr>";
            }
            // finish HTML table text
            content += "</table>";

            // close connection
            conn.close();

            // display output.jsp page with given content above if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/output.jsp");
            request.setAttribute("data", content);
            dispatcher.forward(request, response);


        } catch (Exception se) {
            se.printStackTrace();
            // display error.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Database Error, Please try again");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { ;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        HttpSession session = request.getSession();


    try{
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //Create hashcode for the passwords
            MessageDigest msgDigest = MessageDigest.getInstance("SHA-512");
            msgDigest.update(password.getBytes());
            String passwordHash = new String(msgDigest.digest());
            passwordHash = passwordHash.replaceAll("[^a-zA-Z0-9]", "");

            // Create sql query
            String query = "SELECT * FROM userAccounts WHERE Username=? AND PWD=? AND UserRole =?";

            // set values into SQL query statement
            Pstmt = conn.prepareStatement(query);
            Pstmt.setString(1,username);
            Pstmt.setString(2,passwordHash);
            Pstmt.setString(3,role);

            session.setAttribute("passwordHash", passwordHash);

            // execute query and close connection
            Pstmt.executeQuery();
            ResultSet rs = Pstmt.executeQuery();

            if(session.getAttribute("admin_login")!=null){
                request.getRequestDispatcher("/admin/admin_home.jsp").forward(request, response);
            }
            //Query successful and login attempts not 0
             if (rs.next() && LoginAttempts > 0) {
                 String firstname = rs.getString("Firstname");
                 String lastname = rs.getString("Lastname");
                 String email = rs.getString("Email");
                 String phone = rs.getString("Phone");


                 session.setAttribute("firstname", firstname);
                 session.setAttribute("lastname", lastname);
                 session.setAttribute("email", email);
                 session.setAttribute("phone", phone);
                 session.setAttribute("username", username);


                 String dbrole = rs.getString("UserRole");

                 if (dbrole.equals("admin")){
                     // display admin home page if successful
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_home.jsp");
                     request.setAttribute("message", username + ", you have successfully logged in");
                     dispatcher.forward(request, response);
                 }
                 else if (dbrole.equals("public")) {
                     // display public home page with given message if successful
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                     request.setAttribute("message", username + ", you have successfully logged in");
                     dispatcher.forward(request, response);

                 }
            }
             else{
                 if (LoginAttempts == 0){
                     // display error.jsp page with given message if unsuccessful
                     request.setAttribute("LoginAttempts",LoginAttempts );
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                     request.setAttribute("message",  username+", this username/password combination doesnt exist. No Login Attempts Available");
                     dispatcher.forward(request, response);

                }
                 else{
                     LoginAttempts--;
                     request.setAttribute("LoginAttempts",LoginAttempts );
                     // display error.jsp page with given message if unsuccessful
                     RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                     request.setAttribute("message",  username+", this username/password combination doesnt exist. Please try again");
                     request.setAttribute("message",  "Login Failed Now Only "+LoginAttempts+" Login Attempts Available");
                     dispatcher.forward(request, response);

                 }
             }

        } catch(Exception se){

            se.printStackTrace();

        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2){}
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


    }

}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;


@WebServlet("/AdminUserData")
public class AdminUserData extends HttpServlet {

    private Connection conn;
    private Statement stmt;
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "user";
    String PASS = "password";



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get a session in the servlet
        HttpSession session = request.getSession();

        // URL to connect to database
        String DB_URL = "jdbc:mysql://db:3306/lottery";


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
                        "<td>" + rs.getString("Username") + "</td>"+
                        "<td>" + rs.getString("UserRole") + "</td></tr>";;



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
}

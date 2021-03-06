import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.*;



@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {



    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get a session in the servlet
        HttpSession session = request.getSession();
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";


        // URL to connect to database
        String DB_URL = "jdbc:mysql://db:3306/lottery";



        // get parameter data that was submitted in HTML form (use form attributes 'name')
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // set user attributes as session objects
        session.setAttribute("firstname", firstname);
        session.setAttribute("lastname", lastname);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("username", username);
        session.setAttribute("password", password);




        try{
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            MessageDigest msgDigest = MessageDigest.getInstance("SHA-512");
            msgDigest.update(password.getBytes());
            String passwordHash = new String(msgDigest.digest());
            passwordHash = passwordHash.replaceAll("[^a-zA-Z0-9]", "");


            // Create sql query
            String query = "INSERT INTO userAccounts (Firstname, Lastname, Email, Phone, Username, Pwd,UserRole)"
                    + " VALUES (?, ?, ?, ?, ?, ?,?)";


            // set values into SQL query statement
            stmt = conn.prepareStatement(query);
            stmt.setString(1,firstname);
            stmt.setString(2,lastname);
            stmt.setString(3,email);
            stmt.setString(4,phone);
            stmt.setString(5,username);
            stmt.setString(6,passwordHash);
            session.setAttribute("passwordHash", passwordHash);

            stmt.setString(7,"public");

            // execute query and close connection
            stmt.execute();
            conn.close();

            // display account.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", firstname+", you have successfully created an account");
            dispatcher.forward(request, response);

        } catch(Exception se){
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", firstname+", this username/password combination already exists. Please try again");
            dispatcher.forward(request, response);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

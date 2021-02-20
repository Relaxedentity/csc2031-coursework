import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@WebServlet("/CompareUserNumbers")

public class CompareUserNumbers extends HttpServlet {

    private Connection conn;
    private PreparedStatement Pstmt;
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "user";
    String PASS = "password";
    // URL to connect to database
    String DB_URL = "jdbc:mysql://db:3306/lottery";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT * FROM WinningLottery";


            Pstmt = conn.prepareStatement(query);

            Pstmt.executeQuery();
            ResultSet rs = Pstmt.executeQuery();



            if (rs.next()){
                String LottoNum1 = rs.getString("LotteryNum1");
                String LottoNum2 = rs.getString("LotteryNum2");
                String LottoNum3 = rs.getString("LotteryNum3");
                String LottoNum4 = rs.getString("LotteryNum4");
                String LottoNum5 = rs.getString("LotteryNum5");
                String LottoNum6 = rs.getString("LotteryNum6");

                session.setAttribute("LottoNum1",LottoNum1);
                session.setAttribute("LottoNum2",LottoNum2);
                session.setAttribute("LottoNum3",LottoNum3);
                session.setAttribute("LottoNum4",LottoNum4);
                session.setAttribute("LottoNum5",LottoNum5);
                session.setAttribute("LottoNum6",LottoNum6);

                String WinningLotto = LottoNum1 + LottoNum2 + LottoNum3 +
                        LottoNum4 + LottoNum5 + LottoNum6;

                Object passwordHash = session.getAttribute("passwordHash");
                // Instantiate the Encrypted Storage class
                EncryptedStorage es = (EncryptedStorage) session.getAttribute("encryption");
                String filename = passwordHash.toString()+ ".txt";
                byte[] data = es.decryptData(es.bytesFileReader(filename));
                String sdata = new String(data, StandardCharsets.UTF_8);
                // Compare Winning Lotto string to user inputted string
                if(sdata.equals(WinningLotto)){
                    //Display message in account.jsp if won
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                    request.setAttribute("message", WinningLotto + ", you have won! ");
                    dispatcher.forward(request, response);
                }
                else{
                    //Display message in account.jsp if lost
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                    request.setAttribute("message",   " Try Again");
                    dispatcher.forward(request, response);
                }

            }
            Pstmt.close();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;




@WebServlet("/GetUserNumbers")

public class GetUserNumbers extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get a session in the servlet
        HttpSession session = request.getSession();

        Object passwordHash = session.getAttribute("passwordHash");
        //instantiate the EncryptedStorage class
        EncryptedStorage es = (EncryptedStorage) session.getAttribute("encryption");
        String filename = passwordHash.toString() + ".txt";
        //decrypt the data
        byte[] data = es.decryptData(es.bytesFileReader(filename));
        String sdata = new String(data, StandardCharsets.UTF_8);

        // display public home page with given message if successful
        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("draws", sdata);
        request.setAttribute("message", "Received Draws Successfully");
        dispatcher.forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
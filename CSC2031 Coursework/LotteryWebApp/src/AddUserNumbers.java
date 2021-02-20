import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;




@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Get a session in the servlet
        HttpSession session = request.getSession();

        String firstInt = request.getParameter("firstInt");
        String secondInt = request.getParameter("secondInt");
        String thirdInt = request.getParameter("thirdInt");
        String fourthInt = request.getParameter("fourthInt");
        String fifthInt = request.getParameter("fifthInt");
        String sixthInt = request.getParameter("sixthInt");
        Object passwordHash = session.getAttribute("passwordHash");
        String finalNumber = firstInt + secondInt + thirdInt + fourthInt + fifthInt + sixthInt;

        //instantiate the EncryptedStorage class
        EncryptedStorage es = new EncryptedStorage();
        String filename = passwordHash.toString()+ ".txt";
        es.bytesFileWriter(filename,es.encryptData(finalNumber));
        session.setAttribute("encryption", es);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("message", filename+", you have successfully created an account");
        dispatcher.forward(request, response);



    }
}


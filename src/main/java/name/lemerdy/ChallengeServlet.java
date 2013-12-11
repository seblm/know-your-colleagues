package name.lemerdy;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "challenge", value = "/challenge")
public class ChallengeServlet extends HttpServlet {
    private Challenge challenge;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        challenge = new Challenge(getServletContext().getResourcePaths("/images"), getQuestions());
        gson = new Gson();
    }

    private Questions getQuestions() {
        String questionsParameter = getServletConfig().getInitParameter("questions");
        if (questionsParameter != null) {
            return new PredictableQuestions(questionsParameter);
        }
        return new RandomQuestions(new Random());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            gson.toJson(challenge, out);
        }

        challenge.next();
    }
}

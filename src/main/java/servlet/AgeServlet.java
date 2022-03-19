package servlet;

import service.DragonService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ageServlet", value = "/age/*")
public class AgeServlet extends HttpServlet {
    DragonService dragonService = new DragonService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/sum":
                dragonService.getAgeSum(request, response);
                break;
            case "/avg":
                dragonService.getAgeAvg(request, response);
                break;
        }
    }
}

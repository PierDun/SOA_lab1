package servlet;

import service.DragonService;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "colorServlet", value = "/color")
public class ColorServlet extends HttpServlet {
    DragonService dragonService = new DragonService();

    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String color = (request.getParameter("color"));
        try {
            dragonService.getDragonsWithLesserColor(request, response, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {}
}
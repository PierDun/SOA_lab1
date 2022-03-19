package servlet;

import dao.DragonDAO;
import entity.Dragon;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/pages/*")
public class JspServlet extends HttpServlet {
    private final DragonDAO dragonDAO = new DragonDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/add-dragon-form":
                    showNewForm(request, response);
                    break;
                case "/edit-form":
                    showEditForm(request, response);
                    break;
                case "/get-by-id-form":
                    showGetByIdForm(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/dragon-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Optional<Dragon> existingDragon = dragonDAO.getDragonById(id);
        request.setAttribute("dragon", existingDragon.get());
        request.getRequestDispatcher("/jsp/dragon-form.jsp").forward(request, response);
    }

    private void showGetByIdForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }
}

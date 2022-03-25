package servlet;

import dao.DragonDAO;
import entity.Dragon;
import service.DragonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/dragons/*")
public class DragonServlet extends HttpServlet {
    private final DragonDAO dragonDAO = new DragonDAO();
    private final DragonService dragonService = new DragonService();

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String action = request.getRequestURI();
        try {
            switch (action) {
                case "/Lab1-1.0-SNAPSHOT/dragons/filter":
                    filterDragons(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons/sort":
                    sort(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons":
                    getDragons(request, response);
                    break;
                default:
                    String id = action.substring(action.indexOf("/dragons/") + 9);
                    getDragonById(request, response, id);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPut (HttpServletRequest request, HttpServletResponse response) {
        try {
            dragonService.updateDragon(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) {
        try {
            dragonService.createDragon(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete (HttpServletRequest request, HttpServletResponse response) {
        String action = request.getRequestURI();
        String id = action.substring(action.indexOf("/dragons/") + 9);
        dragonService.deleteDragon(request, response, id);
    }

    public void getDragons (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dragonService.getAllDragons(request, response);
    }

    private void getDragonById (HttpServletRequest request, HttpServletResponse response, String dragon_id) throws ServletException, IOException {
        int id = 0;
        try {
            id = Integer.parseInt(dragon_id);
        } catch (NumberFormatException nfe) {
            response.setStatus(400);
            request.setAttribute("msg", "The following value can't be a number");
            request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
        }
        Optional<Dragon> dragon = dragonDAO.getDragonById(id);
        if (dragon.isPresent()) {
            request.setAttribute("dragon", dragon.get());
        } else {
            response.setStatus(400);
            request.setAttribute("msg", "Not found dragon with id = " + id);
        }
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }

    private void filterDragons (HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.filterDragons(request, response);
    }

    private void sort(HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.sort(request, response);
    }
}
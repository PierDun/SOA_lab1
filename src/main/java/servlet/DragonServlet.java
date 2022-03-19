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

    public void init() {}

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String action = request.getRequestURI();
        try {
            switch (action) {
                case "/Lab1-1.0-SNAPSHOT/dragons/filter":
                    filterDragons(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons/avg":
                    getAgeAvg(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons/sum":
                    getAgeSum(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons/sort":
                    sort(request, response);
                    break;
                case "/Lab1-1.0-SNAPSHOT/dragons":
                    if (request.getParameterMap().isEmpty()) {
                        getDragons(request, response);
                    } else {
                        getDragonById(request, response);
                    }
                    break;
                default:
                    getDragons(request, response);
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
        dragonService.deleteDragon(request, response);
    }

    public void getDragons (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dragonService.getAllDragons(request, response);
    }

    private void getDragonById (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Dragon> dragon = dragonDAO.getDragonById(id);
        if (dragon.isPresent()) {
            request.setAttribute("dragon", dragon.get());
        } else {
            request.setAttribute("msg", "Not found dragon with id = " + id);
        }
        request.getRequestDispatcher("/jsp/get-by-id.jsp").forward(request, response);
    }

    private void filterDragons (HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.filterDragons(request, response);
    }

    private void getAgeAvg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.getAgeAvg(request, response);
    }

    private void getAgeSum(HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.getAgeSum(request, response);
    }

    private void sort(HttpServletRequest request, HttpServletResponse response) throws Exception {
        dragonService.sort(request, response);
    }
}
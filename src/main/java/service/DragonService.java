package service;

import dao.DragonDAO;
import entity.Dragon;
import entity.xml.Dragons;
import entity.xml.Errors;
import entity.xml.JaxBDragon;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import validator.DragonValidator;
import util.JaxB;
import validator.ValidateFieldsException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static util.ServletUtil.getBody;

public class DragonService {
    private final DragonDAO dragonDAO = new DragonDAO();
    private final Dragons dragonsList = new Dragons();
    private final Errors errorsList = new Errors();
    private final DragonValidator validator = new DragonValidator();

    public void createDragon(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String dragonBody = getBody(request);
            JaxBDragon dragon = JaxB.fromStr(dragonBody, JaxBDragon.class);
            dragon.setCreationDate(ZonedDateTime.now());
            validator.validate(dragon);
            dragonDAO.addDragon(dragon.toDragon());
            response.setStatus(200);
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void updateDragon (HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String dragonBody = getBody(request);

            String x = dragonBody.substring(dragonBody.indexOf("<x>") + 3, dragonBody.indexOf("</x>"));

            JaxBDragon dragon = JaxB.fromStr(dragonBody, JaxBDragon.class);
            validator.validate(dragon);
            Optional<Dragon> dragonFromBD = dragonDAO.getDragonById(dragon.getId());
            if (dragonFromBD.isPresent()) {
                Dragon updatingDragon = dragonFromBD.get();
                updatingDragon.update(dragon);
                dragonDAO.updateDragon(updatingDragon);
                response.setStatus(200);
            } else {
                throw new EntityNotFoundException("Cannot update dragon");
            }
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void deleteDragon (HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (dragonDAO.deleteDragon(id)) {
            response.setStatus(200);
        } else {
            throw new EntityNotFoundException("Cannot find dragon with id " + id);
        }
    }

    public void getAllDragons (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfRecordsPerPage = 10;
        int selectedPage = 1;
        List<Dragon> dragons = dragonDAO.getAllDragons();
        int dragonsQuality = (int)Math. ceil( (double) (dragons.size()+1) / numberOfRecordsPerPage);
        request.setAttribute("pagesQuality", IntStream.range(1, (int)Math. ceil( dragonsQuality + 1)).toArray());
        request.setAttribute("dragonsLength", dragons.size());
        int from = (selectedPage - 1) * numberOfRecordsPerPage;
        request.setAttribute("dragons", Arrays.copyOfRange(dragons.toArray(), from , from + numberOfRecordsPerPage) );
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/main-page.jsp");
        dispatcher.forward(request, response);
    }

    private void sendDragons (HttpServletRequest request, HttpServletResponse response, List<Dragon> filteredDragons) throws Exception {
        int numberOfRecordsPerPage = Integer.parseInt(request.getParameter("numberOfRecordsPerPage"));
        int selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
        int from = (selectedPage - 1) * numberOfRecordsPerPage;
        int to = Math.min(from + numberOfRecordsPerPage, filteredDragons.size());
        List<Dragon> dragons = new ArrayList<>(filteredDragons.subList(from , to));
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        dragonsList.setDragons(dragons);
        response.setStatus(200);
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(dragonsList, writer);
        String xml = writer.toString();
        response.getWriter().print(xml);
    }

    private void sendErrorList (HttpServletRequest request, HttpServletResponse response, ValidateFieldsException ex) throws Exception {
        response.setStatus(400);
        errorsList.setErrors(ex.getErrorMsg());
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(errorsList, writer);
        String xml = writer.toString();
        response.getWriter().print(xml);
    }

    public void sort (HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        List<Dragon> sortedDragons = dragonDAO.sort(sortBy, order);
        sendDragons(request, response, sortedDragons);
    }

    public void filterDragons(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String[]> queryMap = request.getParameterMap();
        List<Dragon> filteredDragons = dragonDAO.getFilteredDragons(queryMap);
        sendDragons(request, response, filteredDragons);
    }

    public void getAgeAvg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double avg = dragonDAO.getAgeAvg();
        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().print("The average value is " + avg);
    }

    public void getAgeSum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long sum = dragonDAO.getAgeSum();
        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().print("The sum is " + sum);
    }

    public void getDragonsWithLesserColor (HttpServletRequest request, HttpServletResponse response, String color) throws Exception {
        List<Dragon> dragons = dragonDAO.getDragonsWithLesserColor(color);
        sendDragons(request, response, dragons);
    }
}
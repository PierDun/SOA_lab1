package dao;

import entity.Color;
import entity.Dragon;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DateBuilder;
import util.HibernateUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class DragonDAO {
    public List<Dragon> getAllDragons() {
        Transaction transaction = null;
        List<Dragon> dragons = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            dragons = session.createQuery("from dragon ").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return dragons;
    }

    public Optional<Dragon> getDragonById(long id) {
        Transaction transaction = null;
        Dragon dragon = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            dragon = session.get(Dragon.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(dragon);
    }

    public void addDragon(Dragon dragon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(dragon);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean deleteDragon(long id) {
        Transaction transaction = null;
        boolean successful = false;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Dragon dragon = session.find(Dragon.class, id);
            if (dragon != null) {
                session.delete(dragon);
                session.flush();
                successful = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return successful;
    }

    public void updateDragon(Dragon dragon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(dragon);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public int getQuantity() {
        Transaction transaction = null;
        int quantity = 0;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            quantity = session.createQuery("SELECT count(*) FROM dragon ").getFirstResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return quantity;
    }

    public List<Dragon> sort(String sortBy, String order) {
        Transaction transaction = null;
        List<Dragon> dragons = null;
        List<Object[]> dragonsListWithExtraColumns = null;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            if (sortBy.equals("x") || sortBy.equals("y")) {
                dragonsListWithExtraColumns = session.createQuery("from dragon c join c.coordinates cor ORDER BY cor." + sortBy + " " + order).getResultList();
            }
            if (sortBy.equals("depth")) {
                dragonsListWithExtraColumns = session.createQuery("from dragon c join c.cave g ORDER BY g." + sortBy + " " + order).getResultList();
            }
            if (dragonsListWithExtraColumns != null && !dragonsListWithExtraColumns.isEmpty()) {
                dragons = new ArrayList<>();
                for (Object[] dragonWithExtraColumns : dragonsListWithExtraColumns) {
                    dragons.add((Dragon) dragonWithExtraColumns[0]);
                }
            } else {
                dragons = session.createQuery("from dragon ORDER BY " + sortBy + " " + order).getResultList();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return dragons;
    }

    public long getAgeSum() {
        Transaction transaction = null;
        long sum = 0;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            sum = (Long) session.createQuery("SELECT sum(d.age) FROM dragon d ").getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sum;
    }

    public double getAgeAvg() {
        Transaction transaction = null;
        double average = 0;
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            average = (Double) session.createQuery("SELECT avg (d.age) FROM dragon d ").getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return average;
    }

    public List<Dragon> getFilteredDragons(Map<String, String[]> params) {
        Transaction transaction = null;
        List<Dragon> dragons = null;
        boolean isJoin = false;
        StringBuilder queryStr = new StringBuilder("from dragon c");
        if (params.containsKey("x1") || params.containsKey("y1") || params.containsKey("depth1")) {
            isJoin = true;
            if (params.containsKey("x1") || params.containsKey("y1")) {
                queryStr.append(" join c.coordinates cor");
            }
            if (params.containsKey("depth1")) {
                queryStr.append(" join c.cave g");
            }
            queryStr.append(" where");
            if (params.containsKey("x1")) {
                queryStr.append(" cor.x BETWEEN '").append(params.get("x1")[0]).append("' AND '").append(params.get("x2")[0]).append("' AND");
            }
            if (params.containsKey("y1")) {
                queryStr.append(" cor.y BETWEEN '").append(params.get("y1")[0]).append("' AND '").append(params.get("y2")[0]).append("' AND");
            }
            if (params.containsKey("depth1")) {
                queryStr.append(" g.height BETWEEN '").append(params.get("depth1")[0]).append("' AND '").append(params.get("depth2")[0]).append("' AND");
            }
        }

        if (!queryStr.toString().contains("where")) {
            queryStr.append(" where");
        }
        if (params.containsKey("name")) {
            queryStr.append(" c.name LIKE '%").append(params.get("name")[0]).append("%' AND");
        }
        if (params.containsKey("start-creation-date")) {
            LocalDateTime startCreationDate = DateBuilder.getLocalDateFromDateAndTime(params.get("start-creation-date")[0], params.get("start-creation-time")[0]);
            LocalDateTime endCreationDate = DateBuilder.getLocalDateFromDateAndTime(params.get("end-creation-date")[0], params.get("end-creation-time")[0]);
            queryStr.append(" c.birth BETWEEN '").append(Timestamp.valueOf(startCreationDate)).append("' AND '").append(Timestamp.valueOf(endCreationDate)).append("' AND");
        }

        ArrayList<String> paramsNames = new ArrayList<>(Collections.singletonList("age"));
        for (String paramName : paramsNames) {
            if (params.containsKey(paramName + "1")) {
                queryStr.append(" c.").append(paramName).append(" BETWEEN '").append(params.get(paramName + "1")[0]).append("' AND '").append(params.get(paramName + "2")[0]).append("' AND");
            }
        }

        ArrayList<String> checkboxParamsNames = new ArrayList<>(Arrays.asList("color", "type"));
        for (String paramName : checkboxParamsNames) {
            if (params.containsKey(paramName)) {
                queryStr.append(" c.").append(paramName).append(" IN (");
                String[] checkboxValues = params.get(paramName);
                for (String checkboxValue : checkboxValues) {
                    switch (checkboxValue) {
                        case "WATER":
                        case "RED":
                            queryStr.append(0).append(", ");
                            break;
                        case "UNDERGROUND":
                        case "BLACK":
                            queryStr.append(1).append(", ");
                            break;
                        case "AIR":
                        case "YELLOW":
                            queryStr.append(2).append(", ");
                            break;
                        case "FIRE":
                        case "ORANGE":
                            queryStr.append(3).append(", ");
                            break;
                        case "WHITE":
                            queryStr.append(4).append(", ");
                            break;
                    }
                }
                queryStr = new StringBuilder(queryStr.substring(0, queryStr.length() - 2));
                queryStr.append(") AND");
            }
        }

        if (queryStr.toString().endsWith("AND")) {
            queryStr = new StringBuilder(queryStr.substring(0, queryStr.length() - 4));
        }

        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            if (isJoin) {
                List<Object[]> citiesListWithExtraColumns = session.createQuery(queryStr.toString()).getResultList();
                dragons = new ArrayList<>();
                for (Object[] cityWithExtraColumns : citiesListWithExtraColumns) {
                    dragons.add((Dragon) cityWithExtraColumns[0]);
                }
            } else {
                dragons = session.createQuery(queryStr.toString()).getResultList();
            }
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return dragons;
    }

    public List<Dragon> getDragonsWithLesserColor (String color) {
        List<Dragon> dragons = new ArrayList<>();
        Transaction transaction = null;
        int colorNumber = 0;
        switch (color) {
            case "RED":
                break;
            case "BLACK":
                colorNumber = 1;
                break;
            case "YELLOW":
                colorNumber = 2;
                break;
            case "ORANGE":
                colorNumber = 3;
                break;
            case "WHITE":
                colorNumber = 4;
                break;
        }
        try (Session session = HibernateUtil.getFactory().openSession()) {
            transaction = session.beginTransaction();
            dragons = session.createQuery("from dragon d where d.color < " + colorNumber).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return dragons;
    }
}
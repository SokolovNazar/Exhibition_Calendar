package ua.expo.model.service;

import ua.expo.model.dao.DaoFactory;
import ua.expo.model.dao.impl.JDBCExhibitionHallDao;
import ua.expo.model.dao.impl.JDBCExpositionDao;
import ua.expo.model.entity.ExhibitionHall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class. Verify data before pass it to JDBCExhibitionHallDao
 * @author andrii
 */
public class HallsService {

    private static volatile HallsService instance;
    private JDBCExhibitionHallDao hallDao = DaoFactory.getInstance().createExhibitionHallDao();
    private JDBCExpositionDao expoDao = DaoFactory.getInstance().createExpositionDao();
    private List<ExhibitionHall> halls;
    private Map<Integer, Integer> rowsByHall = new HashMap<>();

    private HallsService() {
        halls = hallDao.getAllOK();
        rowsByHall.put(-1, expoDao.getNumberRows());
    }

    public static HallsService getInstance() {
        if (instance == null) {
            synchronized (HallsService.class) {
                if (instance == null) {
                    instance = new HallsService();
                }
            }
        }
        return instance;
    }


    /**
     * @see JDBCExhibitionHallDao#insert(ExhibitionHall)
     */
    public void add(String name, String info) {
        hallDao.insert(new ExhibitionHall.Builder()
                .setName(name)
                .setInformation(info).build());
        updateList();
    }


    /**
     * @see JDBCExhibitionHallDao#saveDelete(ExhibitionHall)
     */
    public void delete(String idStr) {
        int id = Integer.parseInt(idStr);
        if (id < 0) return;
        hallDao.saveDelete(new ExhibitionHall.Builder().setId(id).build());
        updateList(id);
        rowsByHall.remove(id);
    }

    /**
     * @see JDBCExhibitionHallDao#update(ExhibitionHall)
     */
    public void update(String idStr, String name, String info) {
        ExhibitionHall hall = new ExhibitionHall.Builder()
                .setId(Integer.parseInt(idStr))
                .setName(name)
                .setInformation(info)
                .build();
        hallDao.update(hall);
        updateList(hall.getId());
    }

    /**
     * @return list of Exposition.
     */
    public List<ExhibitionHall> getHalls() {
        return halls;
    }

    /**
     * @return number of rows in exposition table specified by Exhibition Hall id
     */
    public int getNumberOfRows(int hallId) {
        if (rowsByHall.containsKey(hallId)) {
            return rowsByHall.get(hallId);
        }
        int rows = expoDao.getNumberRows(hallId);
        rowsByHall.put(hallId, rows);
        return rows;
    }

    /**
     *
     * @return number of all rows in exposition table
     */
    public int getNumberOfRows() {
        return rowsByHall.get(-1);
    }

    void updateList() {
        halls = hallDao.getAllOK();
    }

    public void updateList(int hallId) {
        this.updateList();
        rowsByHall.put(hallId, expoDao.getNumberRows(hallId));
        rowsByHall.put(-1, expoDao.getNumberRows());
    }

    public void setHallDao(JDBCExhibitionHallDao hallDao){
        this.hallDao = hallDao;
    }

    public void setExpoDao(JDBCExpositionDao expoDao){
        this.expoDao = expoDao;
    }
}

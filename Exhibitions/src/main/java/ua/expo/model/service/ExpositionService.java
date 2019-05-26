package ua.expo.model.service;

import org.apache.log4j.Logger;
import ua.expo.model.dao.DaoFactory;
import ua.expo.model.dao.impl.JDBCExpositionDao;
import ua.expo.model.entity.ExhibitionHall;
import ua.expo.model.entity.Exposition;
import ua.expo.model.service.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

/**
 * Service class. Verify data before pass it to JDBCExpositionDao
 * @author andrii
 */
public class ExpositionService {

    private static volatile ExpositionService instance;
    private static final String SETTINGS_PROP = "/settings.properties";
    private static final Logger LOGGER = Logger.getLogger(ExpositionService.class);

    private int postOnPage;
    private JDBCExpositionDao expoDao = DaoFactory.getInstance().createExpositionDao();
    private HallsService hallsService = HallsService.getInstance();

    private ExpositionService(){
        try(InputStream stream = ExpositionService.class.getResourceAsStream(SETTINGS_PROP)){
            Properties properties = new Properties();
            properties.load(stream);
            postOnPage = Integer.parseInt(properties.getProperty("post.on.page"));
            LOGGER.debug("Load count of post on page from property file. Value=" + postOnPage);
        } catch (IOException e){
            LOGGER.warn("Cannot load settings file or count value is invalid");
            postOnPage = 3;
        }
    }

    public static ExpositionService getInstance(){
        if(instance == null){
            synchronized (ExpositionService.class){
                if(instance == null){
                    instance = new ExpositionService();
                }
            }
        }
        return instance;
    }

    /**
     * @return list of Exposition in specified range
     * @see JDBCExpositionDao#getInRange(int, int)
     * @see JDBCExpositionDao#getInRangeHall(int, int, int)
     */
    public List<Exposition> getExpoList(String currentPage, String hallId) {
        if (Utils.isNumber(currentPage)) {
            int offset = Integer.parseInt(currentPage);
            offset = offset <= 0 ? 0 : (offset - 1) * postOnPage;

            if (Utils.isNumber(hallId)) {
                return expoDao.getInRangeHall(offset, postOnPage, Integer.parseInt(hallId));
            } else {
                return expoDao.getInRange(offset, postOnPage);
            }
        } else {
            int offset = 0;
            if (Utils.isNumber(hallId)) {
                return expoDao.getInRangeHall(offset, postOnPage, Integer.parseInt(hallId));
            } else {
                return expoDao.getInRange(offset, postOnPage);
            }
        }
    }

    /**
     * Calculate number of pages are needed to show all exposition
     * with specified hall id
     * @return number of pages
     */
    public int getNumberOfPages(String hallId) {
        int noOfRows = Utils.isNumber(hallId)
                ? hallsService.getNumberOfRows(Integer.parseInt(hallId))
                : hallsService.getNumberOfRows();
        int noOfPages = noOfRows / postOnPage;
        if ((noOfRows % postOnPage) != 0) {
            noOfPages++;
        }
        return noOfPages;
    }

    /**
     *Verify data, create instance of Exposition and write it to DB.
     * @see JDBCExpositionDao#insert(Exposition)
     */
    public void add(String theme, String shortDesc, String fullDesc, String priceStr,
                    String date, String date_to, String hallIdSrt) {
        int price = Integer.parseInt(priceStr);
        int hallId = Integer.parseInt(hallIdSrt);
        if (hallId < 0 || price < 0) {
            throw new IllegalArgumentException("Wrong value of price or wrong hallId!");
        }
        Date date_start = Date.valueOf(date);
        Date date_end = Date.valueOf(date_to);
        if(date_start.after(date_end)){
            throw new IllegalArgumentException("Wrong date order!");
        }
        Exposition.Builder builder = new Exposition.Builder();
        builder.setTheme(theme)
                .setShortDescription(shortDesc)
                .setFullDescription(fullDesc)
                .setPrice(price)
                .setDate(date_start)
                .setDateTo(date_end)
                .setHall(new ExhibitionHall.Builder().setId(hallId).build());
        expoDao.insert(builder.build());
        hallsService.updateList(hallId);
    }

    /**
     * @see JDBCExpositionDao#saveDelete(Exposition)
     */
    public void delete(String expoIdStr) {
        int expoId = Integer.parseInt(expoIdStr);
        if (expoId < 0) return;
        expoDao.saveDelete(new Exposition.Builder().setId(expoId).build());
        hallsService.updateList(expoDao.getById(expoId).getHall().getId());
    }

    /**
     * @see JDBCExpositionDao#update(Exposition)
     */
    public void update(String expoIdStr, String theme, String shortDesc,
                       String fullDesc, String priceStr, String date, String date_to, String hallIdSrt) {
        int price = Integer.parseInt(priceStr);
        int expoId = Integer.parseInt(expoIdStr);
        int hallId = Integer.parseInt(hallIdSrt);
        if (hallId < 0 || price < 0 || expoId < 0) {
            return;
        }
        Date date_start = Date.valueOf(date);
        Date date_end = Date.valueOf(date_to);
        if(date_start.after(date_end)){
            throw new IllegalArgumentException("Wrong date order!");
        }
        Exposition.Builder builder = new Exposition.Builder();
        builder.setId(expoId)
                .setDate(date_start)
                .setDateTo(date_end)
                .setTheme(theme)
                .setShortDescription(shortDesc)
                .setFullDescription(fullDesc)
                .setHall(new ExhibitionHall.Builder().setId(hallId).build())
                .setPrice(price);
        expoDao.update(builder.build());
    }

    /**
     * @see JDBCExpositionDao#getAllNotDeleted()
     */
    public List<Exposition> getExpositions() {
        return expoDao.getAllNotDeleted();
    }

    /**
     * @see JDBCExpositionDao#getById(int)
     */
    public Exposition getExposition(String expoId) {
        if (Utils.isNumber(expoId)) {
            return expoDao.getById(Integer.parseInt(expoId));
        }
        return null;
    }

    public void setExpoDao(JDBCExpositionDao expoDao) {
        this.expoDao = expoDao;
    }

    public void setHallsService(HallsService hallsService) {
        this.hallsService = hallsService;
    }

    public void setPostOnPage(int postOnPage) {
        this.postOnPage = postOnPage;
    }

    public int getPostOnPage() {
        return postOnPage;
    }
}

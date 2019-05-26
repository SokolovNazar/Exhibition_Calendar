package ua.expo.model.dao.impl;

import org.apache.log4j.Logger;
import ua.expo.model.dao.GenericDAO;
import ua.expo.model.dao.impl.sqlQueries.ExpoQueries;
import ua.expo.model.dao.mapper.ExhibitionHallMapper;
import ua.expo.model.dao.mapper.ExpositionMapper;
import ua.expo.model.entity.ExhibitionHall;
import ua.expo.model.entity.Exposition;
import ua.expo.model.entity.enums.ExpositionStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which introduce DAO to the exposition table.
 * Uses instance of Exposition classes
 * @author andrii
 */
public class JDBCExpositionDao implements GenericDAO<Exposition> {

    private final static Logger LOGGER = Logger.getLogger(JDBCExpositionDao.class);

    private DataSource dataSource;
    private ExpositionMapper expoMapper;
    private ExhibitionHallMapper hallMapper;

    JDBCExpositionDao(DataSource dataSource){
        this.dataSource = dataSource;
        this.expoMapper = ExpositionMapper.getInstance();
        this.hallMapper = ExhibitionHallMapper.getInstance();
        LOGGER.debug("Creating instance of " + this.getClass().getName());
    }

    /**
     * Insert data from Exposition instance to expositions table
     */
    @Override
    public void insert(Exposition expo) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_INSERT)){
            fillStatementCommon(expo, statement);
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("SQLException while insert Exposition instance with theme=" + expo.getTheme(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Inserted new exposition with theme=" + expo.getTheme());
    }

    /**Create instance of Exposition class with data
     * from DB with proper id
     * @return instance of Exposition class
     */
    @Override
    public Exposition getById(int id) {
        ResultSet resultSet;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_BY_ID)){
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            LOGGER.info("Successful execution of select query by exposition id=" + id);
            if (resultSet.next()){
                Exposition expo = expoMapper.extractFromResultSet(resultSet);
                expo.setHall(hallMapper.extractFromResultSet(resultSet));
                return expo;
            }
        } catch (SQLException e){
            LOGGER.error("SQLException while get ExhibitionHall instance by id=" + id, e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Update proper row in DB by specific id
     */
    @Override
    public void update(Exposition expo) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXSITION_UPDATE)){
            fillStatementCommon(expo, statement);
            statement.setInt(9, expo.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("SQLException while trying update exposition with id=" + expo.getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Update exposition with id=" + expo.getId() + ", theme=" + expo.getTheme());
    }

    /**
     * Mark proper row in DB as 'DELETED'.
     * Does not delete row from DB
     */
    public void saveDelete(Exposition expo){
        LOGGER.info("Request to save 'delete' of exposition with id=" + expo.getId());
        operationById(expo.getId(), ExpoQueries.EXPOSITION_SAVE_DELETE);
    }

    /**
     * Delete row from DB, which represent proper exposition instance
     */
    @Override
    public void delete(Exposition expo) {
        LOGGER.info("Request to delete of exposition with id=" + expo.getId());
        operationById(expo.getId(), ExpoQueries.EXPOSITION_DELETE);
    }

    /**
     *
     * @return list of Expositions from DB
     */
    @Override
    public List<Exposition> getAll() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_ALL)) {
            LOGGER.info("Trying to get all expositions");
            return getList(statement);
        }catch (SQLException e){
            LOGGER.error("SQLException while get all exposition", e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of Exposition from DB which are not mark as 'DELETED'
     */
    public List<Exposition> getAllNotDeleted(){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_ALL_NOT_DELETED)) {
            LOGGER.info("Trying to get all expositions where state are not 'deleted'");
            return getList(statement);
        }catch (SQLException e){
            LOGGER.error("SQLException while get all exposition where state ane not 'deleted'", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return list of Exposition which have specified status
     */
    public List<Exposition> getAllByStatus(ExpositionStatus status){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_ALL_BY_STATUS)) {
            statement.setString(1, status.name());
            LOGGER.info("Trying to get all expositions with status " + status.name());
            return getList(statement);
        }catch (SQLException e){
            LOGGER.error("SQLException while get all exposition with status "
                    + status.name(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return list of Exposition in specified range
     */
    public List<Exposition> getInRange(int offset, int length){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_IN_RANGE)){
            statement.setInt(1, offset);
            statement.setInt(2, length);
            LOGGER.info("Request to get exposition in range offset="
                    + offset + " length=" + length);
            return getList(statement);
        } catch (SQLException e){
            LOGGER.error("SQLException while get expositions in range offset="
                    + offset + " length=" + length, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return list of Exposition in specified range and ExhibitionHall id
     */
    public List<Exposition> getInRangeHall(int offset, int length, int hallId) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_IN_RANGE_HALL)){
            statement.setInt(1, hallId);
            statement.setInt(2, offset);
            statement.setInt(3, length);
            LOGGER.info("Request to get exposition in range offset="
                    + offset + " length=" + length + " hall id=" + hallId);
            return getList(statement);
        } catch (SQLException e){
            LOGGER.error("SQLException while get expositions in range offset="
                    + offset + " length=" + length + "hall id=" + hallId, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @return count of rows in exposition table specified by exhibition hall id
     */
    public int getNumberRows(int id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_NUMBER_ROWS_BY_HALL_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Performed request to get count of rows in expositions table where hall id=" + id);
            if (resultSet.next()){
                return resultSet.getInt("count");
            }

        } catch (SQLException e){
            LOGGER.error("SQLException while get rows count in expositions table where hall id=" + id, e);
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     *
     * @return count of rows in exposition table
     */
    public int getNumberRows() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ExpoQueries.EXPOSITION_GET_NUMBER_ROWS)){
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("Performed request to get count of rows in expositions table");
            if (resultSet.next()){
                return resultSet.getInt("count");
            }
        } catch (SQLException e){
            LOGGER.error("SQLException while get rows count in expositions table", e);
            throw new RuntimeException(e);
        }
        return 0;
    }

    private List<Exposition> getList(PreparedStatement statement) throws SQLException{
        Map<Integer, ExhibitionHall> hallsMap = new HashMap<>();
        List<Exposition> list = new ArrayList<>();
        ResultSet resultSet;
        ExhibitionHall tempHall;
        Exposition tempExpo;
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            tempHall = hallMapper.extractFromResultSet(resultSet);
            tempHall = hallMapper.makeUnique(hallsMap, tempHall);
            tempExpo = expoMapper.extractFromResultSet(resultSet);
            tempExpo.setHall(tempHall);
            list.add(tempExpo);
        }
        return list;
    }

    private void fillStatementCommon(Exposition expo, PreparedStatement statement) throws SQLException {
        statement.setString(1, expo.getTheme());
        statement.setString(2, expo.getShortDescription());
        statement.setString(3, expo.getFullDescription());
        statement.setInt(4, expo.getPrice());
        statement.setDate(5, expo.getDate());
        statement.setDate(6, expo.getDate_to());
        statement.setInt(7, expo.getHall().getId());
        statement.setString(8, expo.getExpositionStatus().name());
    }

    private void operationById(int expoId, String query){
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, expoId);
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("SQLException trying to perform operation under exposition with id=" + expoId, e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Performed query='"+query+"' for exposition with id=" + expoId);
    }

}

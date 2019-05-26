package ua.expo.model.dao.impl;

import org.apache.log4j.Logger;
import ua.expo.model.dao.GenericDAO;
import ua.expo.model.dao.impl.sqlQueries.ExpoQueries;
import ua.expo.model.dao.impl.sqlQueries.HallQueries;
import ua.expo.model.dao.mapper.ExhibitionHallMapper;
import ua.expo.model.entity.ExhibitionHall;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which introduce DAO to the exhibitionHalls table.
 * Uses instance of ExhibitionHall classes
 * @author andrii
 */
public class JDBCExhibitionHallDao implements GenericDAO<ExhibitionHall> {

    private final static Logger LOGGER = Logger.getLogger(JDBCExpositionDao.class);

    private DataSource dataSource;
    private ExhibitionHallMapper hallMapper;

    JDBCExhibitionHallDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.hallMapper = ExhibitionHallMapper.getInstance();
        LOGGER.debug("Creating instance of " + this.getClass().getName());
    }

    /**
     *Insert instance of ExhibitionHall class to the
     * exhibitionHalls table
     * @param hall instance of ExhibitionHall
     */
    @Override
    public void insert(ExhibitionHall hall) {
        String query =
                "insert into ExpositionProject.exhibitionHalls (name, information, state) values (?,?,?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hall.getName());
            statement.setString(2, hall.getInformation());
            statement.setString(3, hall.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException while insert ExhibitionHall instance with name=" + hall.getName(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Inserted new hall with name=" + hall.getName());
    }

    /**
     * @param id if ExhibitionHall
     * @return instance of ExhibitionHall class
     * filled with date proper to row in table
     */
    @Override
    public ExhibitionHall getById(int id) {
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(HallQueries.HALL_GET_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            LOGGER.info("Successful execution of select query by hall id=" + id);
            if (resultSet.next()) {
                return hallMapper.extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while get ExhibitionHall instance by id=" + id, e);
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * Update record in exhibitionHalls table.
     * As pointer to proper row used id of hall.
     * You can`t change id of hall
     */
    @Override
    public void update(ExhibitionHall hall) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(HallQueries.HALL_UPDATE)) {
            statement.setString(1, hall.getName());
            statement.setString(2, hall.getInformation());
            statement.setString(3, hall.getStatus().name());
            statement.setInt(4, hall.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException while trying update hall with id=" + hall.getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Update hall id=" + hall.getId() + ", name=" + hall.getName());
    }

    /**
     * Save delete means do not delete row from table. This method marks proper
     * row in table as 'DELETED'. As Many Exposition may be linked to specific hall
     * so it`s necessary to mark as 'DELETED' proper expositions.
     * Here this operation makes in one transaction.
     */
    public void saveDelete(ExhibitionHall hall) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement1 = connection.prepareStatement(HallQueries.HALL_SAVE_DELETE);
            PreparedStatement statement2 = connection.prepareStatement(ExpoQueries.EXPOSITION_SAVE_DELETE_BY_HALL_ID);
            statement2.setInt(1, hall.getId());
            statement2.executeUpdate();
            statement1.setInt(1, hall.getId());
            statement1.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("SQLException trying update exhibitionHalls and expositions tables " +
                    "by seting state 'DELETED' by hall with id=" + hall.getId(), e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException q) {
                LOGGER.error("SQLException trying to do rollback", e);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.error("SQLException trying to close connection", e);
            }
        }
        LOGGER.info("Performed save 'delete' in halls and expos tables by hall with id=" + hall.getId());
    }


    /**
     * Delete proper row in exhibitionHalls table.
     * As pointer to specific row used id of hall.
     */
    @Override
    public void delete(ExhibitionHall hall) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(HallQueries.HALL_DELETE)) {
            statement.setInt(1, hall.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException trying to delete hall with id=" + hall.getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.warn("Performed delete of hall with id=" + hall.getId());
    }

    /**
     * @return list of ExhibitionHall instance from table
     */
    @Override
    public List<ExhibitionHall> getAll() {
        LOGGER.info("Trying to get all halls");
        return getList(HallQueries.HALL_GET_ALL);
    }

    /**
     *
     * @return list of ExhibitionHall where state is OK
     */
    public List<ExhibitionHall> getAllOK() {
        LOGGER.info("Trying to getAll hall with 'OK' state");
        return getList(HallQueries.HALL_GET_ALL_OK);
    }

    private List<ExhibitionHall> getList(String query) {
        List<ExhibitionHall> list = new ArrayList<>();
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(hallMapper.extractFromResultSet(resultSet));
            }
            LOGGER.info("Performed select of hall by specified query");
            return list;
        } catch (SQLException e) {
            LOGGER.error("SQLException while select halls by query", e);
            throw new RuntimeException(e);
        }

    }
}

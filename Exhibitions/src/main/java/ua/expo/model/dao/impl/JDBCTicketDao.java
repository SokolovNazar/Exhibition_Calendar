package ua.expo.model.dao.impl;

import org.apache.log4j.Logger;
import ua.expo.model.dao.GenericDAO;
import ua.expo.model.dao.impl.sqlQueries.TicketsQueries;
import ua.expo.model.dao.mapper.ExhibitionHallMapper;
import ua.expo.model.dao.mapper.ExpositionMapper;
import ua.expo.model.dao.mapper.UserMapper;
import ua.expo.model.entity.ExhibitionHall;
import ua.expo.model.entity.Exposition;
import ua.expo.model.entity.Ticket;
import ua.expo.model.entity.User;

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
 * Class which introduce DAO to the tickets table.
 * Uses instance of Ticket classes
 * @author andrii
 */
public class JDBCTicketDao implements GenericDAO<Ticket> {

    private final static Logger LOGGER = Logger.getLogger(JDBCTicketDao.class);

    private DataSource dataSource;
    private UserMapper userMapper;
    private ExpositionMapper expoMapper;
    private ExhibitionHallMapper hallMapper;

    JDBCTicketDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.userMapper = UserMapper.getInstance();
        this.expoMapper = ExpositionMapper.getInstance();
        this.hallMapper = ExhibitionHallMapper.getInstance();
        LOGGER.debug("Creating instance of " + this.getClass().getName());
    }

    /**
     * White data from Ticket instance to
     * tickets table
     */
    @Override
    public void insert(Ticket ticket) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_INSERT)) {
            statement.setInt(1, ticket.getUser().getId());
            statement.setInt(2, ticket.getExposition().getId());
            statement.setInt(3, ticket.getCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException while insert ExhibitionHall instance: user id="
                    + ticket.getUser().getId() + " and exposition id=" + ticket.getExposition().getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Inserted new ticket user id=" + ticket.getUser().getId()
                + ", expo id=" + ticket.getExposition().getId()
                + ", count=" + ticket.getCount());
    }

    /**
     *
     * @return instance of Ticket class which introduce specified by id
     * row in tuckets table
     */
    @Override
    public Ticket getById(int id) {
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_GET_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            LOGGER.info("Successful execution of select query by ticket id=" + id);
            if (resultSet.next()) {
                User user = userMapper.extractFromResultSet(resultSet);
                Exposition expo = expoMapper.extractFromResultSet(resultSet);
                expo.setHall(hallMapper.extractFromResultSet(resultSet));
                return new Ticket.Builder()
                        .setId(resultSet.getInt("tickets.id"))
                        .setCount(resultSet.getInt("tickets.id"))
                        .setUser(user)
                        .setExposition(expo)
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException while get Ticket instance by id=" + id, e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Update proper row in tickets table.
     * Use ticket id as pointer
     */
    @Override
    public void update(Ticket ticket) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_UPDATE)) {
            statement.setInt(1, ticket.getUser().getId());
            statement.setInt(2, ticket.getExposition().getId());
            statement.setInt(3, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException while trying update ticket with id=" + ticket.getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Update ticket id=" + ticket.getId() + ", user id="
                + ticket.getUser().getId() + "expo id=" + ticket.getExposition().getId());
    }

    /**
     * Delete proper row in tickets table
     */
    @Override
    public void delete(Ticket ticket) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_DELETE)) {
            statement.setInt(1, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQLException trying to delete ticket with id=" + ticket.getId(), e);
            throw new RuntimeException(e);
        }
        LOGGER.warn("Performed delete of ticket with id=" + ticket.getId());
    }

    /**
     *
     * @return list of tickets which linked with
     * specified User
     */
    public List<Ticket> getUserTickets(User user) {
        List<Ticket> ticketList = new ArrayList<>();
        Map<Integer, Exposition> expoMap = new HashMap<>();
        Map<Integer, ExhibitionHall> hallMap = new HashMap<>();
        Exposition expoTemp;
        ExhibitionHall hallTemp;
        int count;
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_GET_USER_TICKETS)) {
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                expoTemp = expoMapper.extractFromResultSet(resultSet);
                hallTemp = hallMapper.extractFromResultSet(resultSet);
                hallTemp = hallMapper.makeUnique(hallMap, hallTemp);
                expoTemp = expoMapper.makeUnique(expoMap, expoTemp);
                expoTemp.setHall(hallTemp);
                count = resultSet.getInt("count");
                ticketList.add(new Ticket.Builder()
                        .setId(-1)
                        .setCount(count)
                        .setUser(user)
                        .setExposition(expoTemp)
                        .build());
            }
            LOGGER.info("Select tickets by user with id="
                    + user.getId() + " returned list with size" + ticketList.size());
            return ticketList;
        } catch (SQLException e) {
            LOGGER.error("SQLException truing get tickets of user with id=" + user.getId(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of all tickets in DB
     */
    @Override
    public List<Ticket> getAll() {
        List<Ticket> ticketList = new ArrayList<>();
        Map<Integer, User> usersMap = new HashMap<>();
        Map<Integer, Exposition> expoMap = new HashMap<>();
        Map<Integer, ExhibitionHall> hallMap = new HashMap<>();
        User user;
        Exposition expo;
        ExhibitionHall hall;
        ResultSet resultSet;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TicketsQueries.TICKET_GET_ALL)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = userMapper.extractFromResultSet(resultSet);
                expo = expoMapper.extractFromResultSet(resultSet);
                hall = hallMapper.extractFromResultSet(resultSet);

                user = userMapper.makeUnique(usersMap, user);
                hall = hallMapper.makeUnique(hallMap, hall);
                expo = expoMapper.makeUnique(expoMap, expo);
                expo.setHall(hall);
                Ticket.Builder builder = new Ticket.Builder();
                builder.setId(resultSet.getInt("tickets.id"))
                        .setCount(resultSet.getInt("tickets.count"))
                        .setUser(user)
                        .setExposition(expo);
                ticketList.add(builder.build());
            }
            LOGGER.info("Got all tickets, counts=" + ticketList.size());
            return ticketList;
        } catch (SQLException e) {
            LOGGER.error("SQLException while getting all tickets", e);
            throw new RuntimeException(e);
        }
    }
}

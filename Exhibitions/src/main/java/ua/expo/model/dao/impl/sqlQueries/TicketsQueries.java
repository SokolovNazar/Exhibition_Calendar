package ua.expo.model.dao.impl.sqlQueries;

public interface TicketsQueries {
    String TICKET_INSERT = "insert into ExpositionProject.tickets (user_id, exposition_id, count) values (?, ?, ?);";
    String TICKET_GET_BY_ID = "SELECT * FROM ExpositionProject.tickets " +
            "join users on tickets.user_id = users.id " +
            "join expositions on tickets.exposition_id = expositions.id " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where tickets.id = ?;";
    String TICKET_UPDATE =  "update ExpositionProject.tickets set user_id = ?, exposition_id = ? where id = ?;";
    String TICKET_DELETE = "delete from ExpositionProject.tickets where id = ?;";
    String TICKET_GET_USER_TICKETS = "SELECT  user_id, exposition_id, sum(count) as count, users.*, expositions.*, exhibitionHalls.* " +
            "FROM ExpositionProject.tickets join users on tickets.user_id = users.id " +
            "join expositions on tickets.exposition_id = expositions.id " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where tickets.user_id = ? " +
            "group by exposition_id ;";
    String TICKET_GET_ALL = "SELECT * FROM ExpositionProject.tickets " +
            "join users on tickets.user_id = users.id " +
            "join expositions on tickets.exposition_id = expositions.id " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id;";
}

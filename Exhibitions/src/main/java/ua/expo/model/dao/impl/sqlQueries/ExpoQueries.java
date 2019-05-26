package ua.expo.model.dao.impl.sqlQueries;

public interface ExpoQueries {
    String EXPOSITION_SAVE_DELETE_BY_HALL_ID = "update ExpositionProject.expositions  set state='DELETED' where hall_id = ?;";
    String EXPOSITION_INSERT = "insert into ExpositionProject.expositions (theme, shortDescription, fullDescription, " +
            "price, date, date_to, hall_id, state) values (?, ?, ?, ?, ?, ?, ?, ?);";
    String EXPOSITION_GET_BY_ID = "SELECT * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.id = ? order by expositions.date;";
    String EXSITION_UPDATE = "update ExpositionProject.expositions  set theme = ?, shortDescription = ?, " +
            "fullDescription = ?, price = ?,date = ?,date_to = ?, hall_id = ?, state=? where id = ?;";
    String EXPOSITION_SAVE_DELETE = "update ExpositionProject.expositions  set state='DELETED' where id = ?;";
    String EXPOSITION_DELETE = "delete from ExpositionProject.expositions where id = ?;";
    String EXPOSITION_GET_ALL = "SELECT * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id order by expositions.date;";
    String EXPOSITION_GET_ALL_NOT_DELETED = "SELECT * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.state != 'DELETED' order by expositions.date;";
    String EXPOSITION_GET_ALL_BY_STATUS = "SELECT * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.state=? order by expositions.date;";
    String EXPOSITION_GET_IN_RANGE = "SELECT * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.state != 'DELETED' order by expositions.date limit ?,?";
    String EXPOSITION_GET_IN_RANGE_HALL = "SELECT  * FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.hall_id = ? AND expositions.state != 'DELETED' " +
            "order by expositions.date limit ?,?;";
    String EXPOSITION_GET_NUMBER_ROWS_BY_HALL_ID = "SELECT count(expositions.id) as count FROM ExpositionProject.expositions " +
            "join exhibitionHalls on expositions.hall_id = exhibitionHalls.id " +
            "where expositions.hall_id = ? AND expositions.state != 'DELETED';";
    String EXPOSITION_GET_NUMBER_ROWS = "SELECT count(expositions.id) as count FROM ExpositionProject.expositions " +
            "WHERE expositions.state != 'DELETED';";

}

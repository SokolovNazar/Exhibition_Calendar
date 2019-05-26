package ua.expo.model.dao.impl.sqlQueries;

public interface HallQueries {
    String HALL_GET_BY_ID
            = "SELECT * FROM ExpositionProject.exhibitionHalls where id = ?;";
    String HALL_UPDATE
            = "update ExpositionProject.exhibitionHalls set name=?, information=?, state=? where id=?;";
    String HALL_SAVE_DELETE
            = "update ExpositionProject.exhibitionHalls set state='DELETED' where id=?;";
    String HALL_DELETE
            = "delete from ExpositionProject.exhibitionHalls where id = ?; ";
    String HALL_GET_ALL
            = "select * from ExpositionProject.exhibitionHalls;";
    String HALL_GET_ALL_OK
            = "select * from ExpositionProject.exhibitionHalls where exhibitionHalls.state='OK';";
}

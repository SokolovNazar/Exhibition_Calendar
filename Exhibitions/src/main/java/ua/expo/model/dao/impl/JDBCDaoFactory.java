package ua.expo.model.dao.impl;

import ua.expo.model.dao.DaoFactory;
import javax.sql.DataSource;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource;


    public JDBCDaoFactory(){
        dataSource = ConnectionPoolHolder.getDataSource();
    }


    @Override
    public JDBCUserDao createUserDao() {
        return new JDBCUserDao(dataSource);
    }

    @Override
    public JDBCExhibitionHallDao createExhibitionHallDao() {
        return new JDBCExhibitionHallDao(dataSource);
    }

    @Override
    public JDBCExpositionDao createExpositionDao() {
        return new JDBCExpositionDao(dataSource);
    }

    @Override
    public JDBCTicketDao createTicketDao() {
        return new JDBCTicketDao(dataSource);
    }
}

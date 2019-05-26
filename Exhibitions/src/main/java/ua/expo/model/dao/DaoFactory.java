package ua.expo.model.dao;

import ua.expo.model.dao.impl.*;

/**
 * @author andrii
 */
public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    /**
     * Create only one instance of JDBCDaoFactory
     * @return realisation of this abstract class
     */
    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

    public abstract JDBCUserDao createUserDao();
    public abstract JDBCExhibitionHallDao createExhibitionHallDao();
    public abstract JDBCExpositionDao createExpositionDao();
    public abstract JDBCTicketDao createTicketDao();

}

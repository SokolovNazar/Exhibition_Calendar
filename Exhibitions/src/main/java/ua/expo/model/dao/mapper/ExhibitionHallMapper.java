package ua.expo.model.dao.mapper;

import ua.expo.model.entity.ExhibitionHall;
import ua.expo.model.entity.enums.HallStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExhibitionHallMapper implements ObjectMapper<ExhibitionHall> {

    private static volatile ExhibitionHallMapper instance;

    private ExhibitionHallMapper(){}

    public static ExhibitionHallMapper getInstance(){
        if(instance == null){
            synchronized (ExhibitionHallMapper.class){
                if(instance == null){
                    instance = new ExhibitionHallMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public ExhibitionHall extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new ExhibitionHall.Builder()
                .setId(resultSet.getInt("exhibitionHalls.id"))
                .setName(resultSet.getString("exhibitionHalls.name"))
                .setInformation(resultSet.getString("exhibitionHalls.information"))
                .setHallStatus(HallStatus.valueOf(resultSet.getString("exhibitionHalls.state")))
                .build();
    }

    @Override
    public ExhibitionHall makeUnique(Map<Integer, ExhibitionHall> cache, ExhibitionHall hall) {
        cache.putIfAbsent(hall.getId(), hall);
        return cache.get(hall.getId());
    }
}

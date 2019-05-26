package ua.expo.model.dao.mapper;


import ua.expo.model.entity.Exposition;
import ua.expo.model.entity.enums.ExpositionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExpositionMapper implements ObjectMapper<Exposition> {
    private static volatile ExpositionMapper instance;

    private ExpositionMapper(){}

    public static ExpositionMapper getInstance(){
        if(instance == null){
            synchronized (ExpositionMapper.class){
                if(instance == null){
                    instance = new ExpositionMapper();
                }
            }
        }
        return instance;
    }

    /**
     * Extract Exposition instance from ResultSet,
     * but this instance do not contains link to the ExhibitionHall instance
     */
    @Override
    public Exposition extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Exposition.Builder()
                .setId(resultSet.getInt("expositions.id"))
                .setTheme(resultSet.getString("expositions.theme"))
                .setShortDescription(resultSet.getString("expositions.shortDescription"))
                .setFullDescription(resultSet.getString("expositions.fullDescription"))
                .setPrice(resultSet.getInt("expositions.price"))
                .setDate(resultSet.getDate("expositions.date"))
                .setDateTo(resultSet.getDate("expositions.date_to"))
                .setExpositionStatus(ExpositionStatus.valueOf(resultSet.getString("expositions.state")))
                .build();
    }

    @Override
    public Exposition makeUnique(Map<Integer, Exposition> cache, Exposition expo) {
        cache.putIfAbsent(expo.getId(), expo);
        return cache.get(expo.getId());
    }

}

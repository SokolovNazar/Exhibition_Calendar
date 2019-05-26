package ua.expo.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author andrii
 * @param <T>
 */
public interface ObjectMapper<T> {
    /**
     * Extract instance of T class from ResultSet.
     * Can throw SQLException of ResultSet is invalid.
     * @param resultSet
     * @return instance of T class
     * @throws SQLException
     */
    T extractFromResultSet(ResultSet resultSet) throws SQLException;

    /**
     * If cache does not contains id as obj,
     * then add this obj to cache and set it`s id as key value.
     * Else return instance from cache with the same id as in obj
     *
     * @param cache map which contains previous instance of T class as value and id as key
     * @param obj
     */
    T makeUnique(Map<Integer, T> cache, T obj);
}

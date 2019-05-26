package ua.expo.model.dao;

import java.util.List;

public interface GenericDAO<T> {
    public abstract void insert(T obj);
    public abstract T getById(int id);
    public abstract void update(T obj);
    public abstract void delete(T obj);
    public abstract List<T> getAll();
}

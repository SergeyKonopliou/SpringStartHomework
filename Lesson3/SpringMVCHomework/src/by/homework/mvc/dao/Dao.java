package by.homework.mvc.dao;

import java.util.List;

import by.homework.mvc.exception.DaoException;


public interface Dao<T> {

	public List<T> getAll() throws DaoException;
	
	public void add(T object) throws DaoException;
	
	public void update(T object) throws DaoException;
	
	public void delete(Long id) throws DaoException;
}

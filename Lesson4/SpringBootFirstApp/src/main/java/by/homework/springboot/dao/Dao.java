package by.homework.springboot.dao;

import java.util.List;

import by.homework.springboot.exception.DaoException;
import by.homework.springboot.model.Product;

public interface Dao {

	public List<Product> getAll() throws DaoException;
	
	public void add(Product product) throws DaoException;
	
	public void delete(Long id) throws DaoException;
}

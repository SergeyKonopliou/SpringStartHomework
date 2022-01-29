package by.homework.mvc.dao;

import java.util.List;

import by.homework.mvc.exception.DaoException;
import by.homework.mvc.model.Product;

public interface ProductDao extends Dao<Product> {

	public List<Product> findByName(String name) throws DaoException;
	
}

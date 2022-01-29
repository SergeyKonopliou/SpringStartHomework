package by.homework.mvc.service;

import java.util.List;

import by.homework.mvc.exception.ServiceException;
import by.homework.mvc.model.Product;


public interface ProductService {

	public List<Product> loadAll() throws ServiceException;
	
	public List<Product> loadFindProductByName(String name) throws ServiceException;
	
	public void add(Product object) throws ServiceException;
	
	public void delete(Long id) throws ServiceException;
	
	public void update(Product product) throws ServiceException;
}

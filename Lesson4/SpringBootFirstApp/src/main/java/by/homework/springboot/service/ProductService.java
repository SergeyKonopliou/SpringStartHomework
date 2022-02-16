package by.homework.springboot.service;

import java.util.List;

import by.homework.springboot.exception.ServiceException;
import by.homework.springboot.model.Product;


public interface ProductService {

	public List<Product> loadAll() throws ServiceException;
	
	public void add(Product object) throws ServiceException;
	
	public void delete(Long id) throws ServiceException;
	
	
}
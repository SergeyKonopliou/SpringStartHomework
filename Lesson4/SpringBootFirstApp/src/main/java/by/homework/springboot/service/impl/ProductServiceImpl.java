package by.homework.springboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import by.homework.springboot.dao.Dao;
import by.homework.springboot.exception.DaoException;
import by.homework.springboot.exception.ServiceException;
import by.homework.springboot.model.Product;
import by.homework.springboot.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier("daoImpl")
	private Dao dao;

	@Override
	public List<Product> loadAll() throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = dao.getAll();
		} catch (DaoException e) {
			throw new ServiceException("Problems with loading all products from DB service method " + e.getMessage(), e);
		}
		return products;
	}

	public void add(Product product) throws ServiceException {
		try {
			dao.add(product);
		} catch (DaoException e) {
			throw new ServiceException("Problems with adding new products to DB service method  " + e.getMessage(), e);
		}
	}

	public void delete(Long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Problems with deleting product from DB service method " + e.getMessage(), e);
		}
	}

}

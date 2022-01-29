package by.homework.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import by.homework.mvc.dao.ProductDao;
import by.homework.mvc.exception.DaoException;
import by.homework.mvc.exception.ServiceException;
import by.homework.mvc.model.Product;
import by.homework.mvc.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao dao;

	/**
	 * Метод загрузки всех товаров и базы данных
	 */
	public List<Product> loadAll() throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = dao.getAll();
		} catch (DaoException e) {
			throw new ServiceException("Problems with loading all products from DB service method " + e.getMessage(), e);
		}
		return products;
	}

	/**
	 * Метод загрузки товаров,найденных по названию
	 */
	public List<Product> loadFindProductByName(String name) throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = dao.findByName(name);
		} catch (DaoException e) {
			throw new ServiceException("Problems with serching product by name from DB service method " + e.getMessage(), e);
		}
		return products;
	}

	/**
	 * Метод добавления нового товара в базу данных
	 */
	public void add(Product product) throws ServiceException {
		try {
			dao.add(product);
		} catch (DaoException e) {
			throw new ServiceException("Problems with adding new products to DB service method  " + e.getMessage(), e);
		}
	}

	/**
	 * Метод удаления товара из базы данных
	 */
	public void delete(Long id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Problems with deleting product from DB service method " + e.getMessage(), e);
		}
	}

	/**
	 * Метод изменения товара в базе данных
	 */
	public void update(Product product) throws ServiceException {
		try {
			dao.update(product);
		} catch (DaoException e) {
			throw new ServiceException("Problems with updating product by name in DB service method " + e.getMessage(), e);
		}
	}
}


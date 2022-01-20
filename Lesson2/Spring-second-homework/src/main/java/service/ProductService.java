package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.protobuf.ServiceException;

import dao.ProductDao;
import exception.DaoException;
import model.Product;
import util.Timed;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;

	/**
	 * Метод загрузки всех товаров и базы данных
	 */
	public List<Product> loadAll() throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = dao.getAll();
		} catch (DaoException e) {
			throw new ServiceException("Проблемы с загрузкой товаров из БД " + e.getMessage(), e);
		}
		return products;
	}

	/**
	 * Метод загрузки товаров,найденных по названию
	 */
	@Timed
	public List<Product> loadFindProductByName(String name) throws ServiceException {
		List<Product> products = new ArrayList<>();
		try {
			products = dao.findByName(name);
		} catch (DaoException e) {
			throw new ServiceException("Проблемы с поиском товара в БД " + e.getMessage(), e);
		}
		return products;
	}

	/**
	 * Метод добавления нового товара в базу данных
	 */
	public void add(Product object) throws ServiceException {
		try {
			dao.add(object);
		} catch (DaoException e) {
			throw new ServiceException("Проблемы с добавлением товара в БД " + e.getMessage(), e);
		}
	}

	/**
	 * Метод удаления товара из базы данных
	 */
	public void delete(String name) throws ServiceException {
		try {
			dao.delete(name);
		} catch (DaoException e) {
			throw new ServiceException("Проблемы с удалением товара в БД " + e.getMessage(), e);
		}
	}

	/**
	 * Метод изменения товара в базе данных
	 */
	public void update(String nameNew, String priceNew) throws ServiceException {
		try {
			dao.update(nameNew, priceNew);
		} catch (DaoException e) {
			throw new ServiceException("Проблемы с изменением товара в БД " + e.getMessage(), e);
		}
	}
}

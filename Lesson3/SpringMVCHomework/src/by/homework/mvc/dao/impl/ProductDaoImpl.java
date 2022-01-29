package by.homework.mvc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.homework.mvc.dao.ProductDao;
import by.homework.mvc.dao.util.MysqlConfigConnectManager;
import by.homework.mvc.exception.DBConnectException;
import by.homework.mvc.exception.DaoException;
import by.homework.mvc.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private MysqlConfigConnectManager manager;

	private static final String SQL_DELETE_GOOD_QUERY = "DELETE FROM products WHERE id = ?";
	private static final String SQL_UPDATE_GOOD_QUERY = "UPDATE products SET name = ?,price = ? WHERE id = ?";
	private static final String SQL_ADD_GOOD_QUERY = "INSERT INTO products(name, price) VALUES (?,?)";
	private static final String SQL_FIND_GOOD_LIKE_QUERY = "SELECT * FROM products WHERE name LIKE ?";
	private static final String SQL_SELECT_ALL_GOOD_QUERY = "SELECT * FROM products";

	/**
	 * Метод получения всех товаров и базы данных
	 */
	public List<Product> getAll() throws DaoException {

		List<Product> newProducts = new ArrayList<>();
		try (Connection connection = manager.connection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GOOD_QUERY);
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");
				newProducts.add(new Product(id, name, price));
			}
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Error in getting all items dao method " + e.getMessage(), e);
		}

		return newProducts;
	}

	/**
	 * Метод поиска товара по названию
	 */
	public List<Product> findByName(String newName) throws DaoException {
		List<Product> list = new ArrayList<>();
		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_GOOD_LIKE_QUERY);) {
			statement.setString(1, "%" + newName + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				list.add(new Product(id, name, price));
			}
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Error in finding by name dao method " + e.getMessage(), e);
		}
		return list;

	}

	/**
	 * Метод добавления нового товара в базу данных
	 */
	public void add(Product product) throws DaoException {

		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_ADD_GOOD_QUERY);) {
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.executeUpdate();
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Error in adding new product to DB dao method " + e.getMessage(), e);
		}

	}

	/**
	 * Метод изменения информации о товаре
	 */
	public void update(Product product) throws DaoException {

		try (Connection connection = manager.connection();) {
			PreparedStatement statement;
			statement = connection.prepareStatement(SQL_UPDATE_GOOD_QUERY);
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.setLong(3, product.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException | DBConnectException | NullPointerException e) {
			throw new DaoException("Error in updating product by name dao method " + e.getMessage(), e);
		}
	}

	/**
	 * Метод удаления товара по id из базы данных
	 */
	public void delete(Long id) throws DaoException {

		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_GOOD_QUERY);) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Error in deleting product by name dao method " + e.getMessage(), e);
		}
	}
}

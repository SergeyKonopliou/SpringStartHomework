package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.util.MysqlConfigConnectManager;
import exception.DBConnectException;
import exception.DaoException;
import model.Product;

@Repository
public class ProductDao {
	
	@Autowired
	private MysqlConfigConnectManager manager;


	private static final String SQL_DELETE_GOOD_QUERY = "DELETE FROM products WHERE name = ?";
	private static final String SQL_UPDATE_GOOD_QUERY = "UPDATE products SET name = ?,price = ? WHERE name = ?";
	private static final String SQL_UPDATE_GOOD_QUERY_WITHOUT_PRICE = "UPDATE products SET name = ? WHERE name = ?";
	private static final String SQL_UPDATE_GOOD_QUERY_WITHOUT_NAME = "UPDATE products SET price = ? WHERE name = ?";
	private static final String SQL_ADD_GOOD_QUERY = "INSERT INTO products(name, price) VALUES (?,?)";
	private static final String SQL_FIND_GOOD_LIKE_QUERY = "SELECT * FROM products WHERE name LIKE ?";
	private static final String SQL_SELECT_ALL_GOOD_QUERY = "SELECT * FROM products";
	
	/**
	 * Метод получения всех товаров и базы данных
	 */
	public List<Product> getAll() throws DaoException {

		List<Product> newProducts = new ArrayList<>();
		try (Connection connection = manager.connection();
				Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GOOD_QUERY);
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");
				newProducts.add(new Product(id, name, price));
			}
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Ошибка в методе получения всех товаров " + e.getMessage(), e);
		}

		return newProducts;
	}

	/**
	 * Метод поиска товара по названию
	 */
	public List<Product> findByName(String nameProduct) throws DaoException {

		List<Product> foundGoods = new ArrayList<>();
		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_FIND_GOOD_LIKE_QUERY);) {
			statement.setString(1, "%" + nameProduct + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				foundGoods.add(new Product(id, name, price));
			}
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Возникла ошибка при поиске товара по наименованию " + e.getMessage(), e);
		}
		return foundGoods;
	}

	/**
	 * Метод добавления нового товара в базу данных
	 */
	public void add(Product product) throws DaoException {

		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_ADD_GOOD_QUERY);) {
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.executeUpdate();// executeQuery() здесь не работает!!!
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Возникла ошибка при добавлении нового товара в БД " + e.getMessage(), e);
		}

	}

	/**
	 * Метод изменения информации о товаре
	 */
	public void update(String newName, String newPrice) throws DaoException {

		try (Connection connection = manager.connection();) {
			PreparedStatement statement;

			if (!newPrice.isEmpty() && !newName.isEmpty()) {
				statement = connection.prepareStatement(SQL_UPDATE_GOOD_QUERY);
				statement.setString(1, newName);
				statement.setDouble(2, Double.parseDouble(newPrice));
			} else if (!newName.isEmpty()) {
				statement = connection.prepareStatement(SQL_UPDATE_GOOD_QUERY_WITHOUT_PRICE);
				statement.setString(1, newName);
			} else {
				statement = connection.prepareStatement(SQL_UPDATE_GOOD_QUERY_WITHOUT_NAME);
				statement.setDouble(1, Double.parseDouble(newPrice));
			}
			statement.executeUpdate();
			statement.close();
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Возникла ошибка в методе updateGood() " + e.getMessage(), e);
		}
	}

	/**
	 * Метод удаления товара по id из базы данных
	 */
	public void delete(String name) throws DaoException {

		try (Connection connection = manager.connection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_GOOD_QUERY);) {
			statement.setString(1, name);
			statement.executeUpdate();
		} catch (SQLException | DBConnectException e) {
			throw new DaoException("Возникла ошибка при удалении товара " + e.getMessage(), e);
		}
	}
}

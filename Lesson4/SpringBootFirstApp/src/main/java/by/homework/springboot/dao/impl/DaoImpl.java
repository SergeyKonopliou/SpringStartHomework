package by.homework.springboot.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.homework.springboot.dao.Dao;
import by.homework.springboot.exception.DaoException;
import by.homework.springboot.model.Product;

@Repository
public class DaoImpl implements Dao {

	@Autowired
	private DataSource source;
	private static final String SQL_SELECT_ALL_GOOD_QUERY = "SELECT * FROM products";
	private static final String SQL_ADD_GOOD_QUERY = "INSERT INTO products(name, price) VALUES (?,?)";
	private static final String SQL_DELETE_GOOD_QUERY = "DELETE FROM products WHERE id = ?";


	@Override
	public List<Product> getAll() throws DaoException {
		List<Product> newProducts = new ArrayList<>();
		try (Connection connection = source.getConnection(); Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_GOOD_QUERY);
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				Double price = resultSet.getDouble("price");
				newProducts.add(new Product(id, name, price));
			}
		} catch (Exception e) {
			throw new DaoException("Problems with reading database in Dao method", e);
		}
		return newProducts;
	}

	@Override
	public void add(Product product) throws DaoException {
		try (Connection connection = source.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_ADD_GOOD_QUERY);) {
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.executeUpdate();	
		} catch (Exception e) {
			throw new DaoException("Problems with adding new product in database in Dao method", e);
		}
		
	}

	@Override
	public void delete(Long id) throws DaoException {
		try (Connection connection = source.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_GOOD_QUERY);) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoException("Problems with deleteting product from database in Dao method", e);
		}
		
	}
}

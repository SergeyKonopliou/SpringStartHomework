package by.homework.mvc.dao.util;

import java.sql.Connection;

import by.homework.mvc.exception.DBConnectException;


public interface ConfigConnectManager {
	
	public Connection connection() throws DBConnectException;
	
}

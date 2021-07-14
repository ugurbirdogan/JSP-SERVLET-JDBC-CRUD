package com.ugur.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Result;
import com.ugur.model.Employee;

public class Db_Connection {

	private String jdbcUrl = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
	private String jdbcUserName = "root";
	private String jdbcPassword = "1234";
	
	private static final String INSERT_DATA_SQL   = "INSERT INTO employee" + " (name, surName, email, address, salary) VALUES ( ?, ?, ?, ?, ?)";
	private static final String SELECT_DATA_BY_ID = "SELECT id, name, surname, email, address, salary FROM employee WHERE id = ? ";
	private static final String SELECT_ALL_DATA   = "SELECT * FROM employee ";
	private static final String DELETE_DATA_SQL   = "DELETE FROM employee WHERE id = ?; ";
	private static final String UPDATE_DATA_SQL   = "UPDATE employee SET name = ?, surName = ?, email = ?, address = ?, salary = ? where id = ?; ";
	
	public Db_Connection() {

		//
	}
	
	protected Connection getConnection() {
		
		Connection connection = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		} catch(ClassNotFoundException e) {
			
			e.printStackTrace();
			
		}
		
		return connection;
	}
	
	public void insertData(Employee employee) {
		
		System.out.println(INSERT_DATA_SQL);
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DATA_SQL)){
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getSurName());
			preparedStatement.setString(3, employee.getEmail());
			preparedStatement.setString(4, employee.getAddress());
			preparedStatement.setInt(5, employee.getSalary());
			preparedStatement.executeUpdate();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public Employee selectData(int id) {
		Employee employee = null;
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DATA_BY_ID);){
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				String name = rs.getString("name");
				String surName = rs.getString("surName");
				String email = rs.getString("email");
				String address = rs.getString("address");
				int salary  = rs.getInt("salary");
				employee = new Employee(id, name, surName, email, address, salary);
				
			}
			
			
		} catch(SQLException e) {
			
			e.printStackTrace();
		}
		return employee;
		
	}
	
	public List<Employee> selectAllData(){
		
		List<Employee> employees = new ArrayList<>();
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DATA);){
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String surName = rs.getString("surName");
				String email = rs.getString("email");
				String address = rs.getString("address");
				int salary  = rs.getInt("salary");
				employees.add(new Employee(id, name, surName, email, address, salary));
			}
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return employees;
	}
	
	
	public boolean deleteData(int id) throws SQLException {
		
		boolean rowDeleted;
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DATA_SQL);){
			
			preparedStatement.setInt(1, id);
			rowDeleted = preparedStatement.executeUpdate() > 0 ;
				
		} 
		return rowDeleted;
	}
	
	public boolean updateData(Employee employee) throws SQLException {
		
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DATA_SQL);){
			
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setString(2, employee.getSurName());
			preparedStatement.setString(3, employee.getEmail());
			preparedStatement.setString(4, employee.getAddress());
			preparedStatement.setInt(5, employee.getSalary());
			preparedStatement.setInt(6, employee.getId());
			
			rowUpdated = preparedStatement.executeUpdate() > 0;
			
		}
		
		return rowUpdated;
		
	}
	
	private void printSQLException(SQLException ex) {
		
		for(Throwable e : ex) {
			
			if( e instanceof SQLException) {
				e.printStackTrace(System.err);
				
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());				
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				
				Throwable t = ex.getCause();
	
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
	
			}
		}
	}
}

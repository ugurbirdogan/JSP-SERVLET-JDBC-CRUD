package com.ugur.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ugur.dao.Db_Connection;
import com.ugur.model.Employee;

@WebServlet("/")
public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Db_Connection db_Connection;
	
	public void init() {
		db_Connection = new Db_Connection();
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getServletPath();
		
		try {
			
			switch(action) {
			
			case "/new" :
				showNewForm(req,resp);
				break;
			case "/insert" :
				insertData(req,resp);
				break;
			case "/delete" :
				deleteData(req,resp);
				break;
			case "/edit" :
				showEditForm(req,resp);
				break;
			case "/update" : 
				updateData(req,resp);
				break;
			default :
				listData(req,resp);
				break;
				
			}
			
			
		} catch (SQLException ex) {

			throw new ServletException(ex);
			
		}
		
	}
	
	
	private void listData(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Employee> listData = db_Connection.selectAllData();
		req.setAttribute("listData", listData);
		RequestDispatcher dispatcher = req.getRequestDispatcher("employee-list.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("employee-form.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		Employee existingEmployee = db_Connection.selectData(id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("employee-form.jsp");
		req.setAttribute("employee", existingEmployee);
		dispatcher.forward(req, resp);
		
	}
	
	private void insertData(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		
		String name = req.getParameter("name");
		String surName = req.getParameter("surName");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		int salary = Integer.parseInt(req.getParameter("salary"));
		Employee newEmployee = new Employee(name, surName, email, address, salary);
		db_Connection.insertData(newEmployee);
		resp.sendRedirect("list");
		
	}
	
	private void updateData(HttpServletRequest req, HttpServletResponse resp) 
			throws SQLException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String surName = req.getParameter("surName");
		String email = req.getParameter("email");
		String address = req.getParameter("address");
		int salary = Integer.parseInt(req.getParameter("salary"));
		
		Employee worker = new Employee(id, name, surName, email, address, salary);
		db_Connection.updateData(worker);
		resp.sendRedirect("list");
	}
	
	private void deleteData(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		db_Connection.deleteData(id);
		resp.sendRedirect("list");
	}
}

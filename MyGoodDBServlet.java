package com.mo;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyDBServlet
 */
@WebServlet("/MyGoodDBServlet")
public class MyGoodDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyGoodDBServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/html");
			String sid = request.getParameter("id");
			String spassword = request.getParameter("password");
			if (sid!=null && spassword!=null) {
			
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001");
				PreparedStatement s1 = c.prepareStatement("SET SCHEMA MO");
				s1.execute();
				PreparedStatement s2 = c.prepareStatement("Select * from user where id=? and password=?");
				s2.setString(1,sid );
				s2.setString(2, spassword);
				ResultSet rs = s2.executeQuery();
				if (rs.next()) {
					response.getWriter().append("Welcome " + rs.getString("id") + "<br/>");
				}
				else {
					response.getWriter().append("Either your id or password is wrong" + "<br/>");
				
				}
			}else {
				response.getWriter().append("No data received<br/>");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

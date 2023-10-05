package com.mo;

import java.sql.Statement;
import java.io.IOException;
import java.sql.CallableStatement;
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
@WebServlet("/MyInsertDBServlet")
public class MyInsertDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyInsertDBServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/html");
			String srow_number = request.getParameter("row_number");
			String sid = request.getParameter("id");
			String spassword = request.getParameter("password");
			if (sid!=null && spassword!=null && srow_number!=null) {
			
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001");
				PreparedStatement s1 = c.prepareStatement("SET SCHEMA MO");
				s1.execute();
				
				String callProcedure = "{call InsertUser(?, ?, ?)}";
	            CallableStatement cs = c.prepareCall(callProcedure);
	            
				cs.setInt(1,Integer.valueOf(srow_number));
				cs.setString(2, sid);
				cs.setString(3, spassword);
				cs.execute();
				response.getWriter().append(sid + " created<br/>");
				
			}else {
				response.getWriter().append("No data received<br/>");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().append("something is wrong, user not created<br/>");

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

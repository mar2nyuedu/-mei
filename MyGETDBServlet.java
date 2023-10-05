package com.mo;

import java.sql.Statement;
import java.sql.Types;
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
@WebServlet("/MyGETDBServlet")
public class MyGETDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyGETDBServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/html");
			String saccount_number = request.getParameter("account_number");
			if (saccount_number!=null) {
			
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001");
				PreparedStatement s1 = c.prepareStatement("SET SCHEMA MO");
				s1.execute();
				
				// Prepare the CallableStatement to call the stored procedure
	            String callProcedure = "{call GETUSER(?, ?)}";
	            CallableStatement callableStatement = c.prepareCall(callProcedure);

	            // Set the IN parameter
	            int inAccountNumber = Integer.valueOf(saccount_number); 
	            callableStatement.setInt(1, inAccountNumber);

         
	            callableStatement.setString(2, "");

	            // Execute the stored procedure
	            callableStatement.execute();

	            // Retrieve the INOUT parameter value (output value)
	            String userValue = callableStatement.getString(2);
				
				response.getWriter().append("Hello " + userValue +"<br/>");
				
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

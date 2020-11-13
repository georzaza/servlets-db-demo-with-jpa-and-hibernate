package no.jpa;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class AddProductNoJpa extends HttpServlet {

	private static final long serialVersionUID = -5264249364202880660L;

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String db_connect = "jdbc:mysql://localhost:3306/demo?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EET";
        String username = "temp";
        String password = "admin_pass";
        try {
        	// Make the connection.
            Connection conn = DriverManager.getConnection(db_connect, username, password);
            
            // Prepare the insert statement.
            String sqlStr = "insert into products (barcode, name, color, description) values (?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sqlStr);
            
            // Get the info we need from the request
            String barcode = request.getParameter("barcode");
            String name = request.getParameter("name");
            String color = request.getParameter("color");
            String description = request.getParameter("description");
            
            // Set the parameteres for the insert statement.
            pst.setString(1, barcode);
            pst.setString(2, name);
            pst.setString(3, color);
            pst.setString(4, description);
            
            // execute the insert statement.
            @SuppressWarnings("unused")
			int numRowsChanged  = pst.executeUpdate();
            
            // Inform user of the successful insertion
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<h2 style='color:red'>You succeffully added the product</h2>");
         // Stay on the same page as the user might want to insert more than one products in a row.
            RequestDispatcher rd=request.getRequestDispatcher("/add-product-no-jpa.html");  
            rd.include(request, response);
            out.close();
        }
        
        catch (SQLException ex){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red;'>Please confirm that the barcode you entered is unique.<br>");
            out.println("If the problem persists, contact the administrator.</h2>");
            
            // Stay on the same page as the user might want to insert more than one products in a row.
            RequestDispatcher rd=request.getRequestDispatcher("/add-product-no-jpa.html");  
            rd.include(request, response);
            out.close();
            
            //server log
            System.out.println("<p>Error: " + ex.getMessage() + "</p>");  // for debug
            ex.printStackTrace();
        } 
        
        catch (Exception ex){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red;'>We can not process your request at this time.<br></h2>");
            out.close();
            
            // Stay on the same page as the user might want to insert more than one products in a row.
            RequestDispatcher rd=request.getRequestDispatcher("/add-product-no-jpa.html");  
            rd.include(request, response);
            
            // server log
            System.out.println("<p>Error: " + ex.getMessage() + "</p>");  // for debug
            ex.printStackTrace();
        }        
    }
}

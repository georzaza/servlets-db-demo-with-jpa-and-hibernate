package with.jpa;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import org.hibernate.exception.ConstraintViolationException;

import dao.ProductDao;
import models.Product;
import welcome.Welcome;


public class AddProductWithJpa extends HttpServlet {

	private static final long serialVersionUID = -5264249364202880660L;

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	
            String barcode = request.getParameter("barcode");
            String name = request.getParameter("name");
            String color = request.getParameter("color");
            String description = request.getParameter("description");
            
            // get our dao
            ProductDao thisProductDao = Welcome.getProductDao();
            
            // save the product to database. All the magic happens in the next lines
            Product product = new Product(barcode, name, color, description);
            thisProductDao.saveProduct(product);

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            
            // Stay on the same page as the user might want to insert more than one products in a row.
        	RequestDispatcher rd=request.getRequestDispatcher("/add-product-with-jpa.html");
            rd.include(request, response);
            out.close();
              
        }
        
        catch (Exception ex){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red;'>We can not process your request at this time.<br></h2>");
            
            // Stay on the same page as the user might want to insert more than one products in a row.
        	RequestDispatcher rd=request.getRequestDispatcher("/add-product-with-jpa.html");
            rd.include(request, response);
            out.close();
            
            // server log
            System.out.println("<p>Error: " + ex.getMessage() + "</p>");  // for debug
            ex.printStackTrace();
        }
    }
}

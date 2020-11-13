package welcome;
import java.io.*;
import java.sql.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import dao.ProductDao;
import models.Product;

public class Welcome extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ProductDao productDao;
	
	// init
	public void init()	{
		productDao = new ProductDao();
	}
	
	/* I am not sure if we need to do this, but for any case
	 * I use this function to return the productDao that we initialized 
	 * when someone gets connected to our welcome page to AddProductWithJPA
	 * and AllProductsWithJPA. 
	 * If you need to know if it's possible the above-mentioned Servlets to 
	 * work without it, go ahead and test it.
	 */
	public static ProductDao getProductDao()	{
		return productDao;
	}
	
	// redirect to doGet
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
		doGet(request, response);
	}
	
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
   
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // for the NO-JPA db the database that we use is called demo, and has the table products.
        String db_connect = "jdbc:mysql://localhost:3306/demo?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EET";
        String username = "temp";
        String password = "admin_pass";
        int sizeOfJPAdb = 0;		// shows the number of the products in the    JPA db
        int sizeOfNoJPAdb = 0;		// shows the number of the products in the NO-JPA db
        try {
        	
        	/* Connect to the NO-JPA database and count the products there */
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(db_connect, username, password);
            Statement stmt = conn.createStatement(); 
            String sqlStr = "select * from products";
            ResultSet rset = stmt.executeQuery(sqlStr);
            while(rset.next()) { sizeOfNoJPAdb++; }
            
            /* Connect to the JPA database and count the products there */
            List<Product> listOfProducts = productDao.getAllProducts();
            sizeOfJPAdb = listOfProducts.size();
        }
        
        /* Usual case senario when the driver for jdbc is not ... stable */
        catch(ClassNotFoundException ex)	{
        	out.println("<h2> There is a problem while connecting to this database.");
        	out.println("</h2><h6> Contact the administrator at georzaza@gmail.com.</h6>");
        	ex.printStackTrace();
        }
        catch(Exception ex) {
        	// server log
            System.out.println("<p>Error: " + ex.getMessage() + "</p>");
            ex.printStackTrace();
        }
        out.println("<!DOCTYPE html>" 
        +"<html>" 
        +"<head>"
        +"<meta charset='UTF-8'>"
        +"<title>DB & Servlets Demo</title>"
        +"<link rel='stylesheet' href='index.css'>"
    +"</head>"
    +"<body>"
        +"<main>"

            /* PRINT A WELCOME MESSAGE */
            +"<section style='text-align:center;'>"
                +"<h1 style='font-size: 2.2rem;'margin-top: 20px;>"
                    +"Demonstration of using Servlets, Databases, JPA and Hibernate"
                +"</h1>"
                +"<br>"
            +"</section>"


            /*PRINT HOW MANY PRODUCTS THE NO-JPA DATABASE CONTAIN */
            +"<section style='text-align:center;'>"
                +"<h2>Currently the database WITHOUT JPA contains <strong>" +sizeOfNoJPAdb+ "</strong> products.</h2><br>"
            +"</section>"
			
			/* GIVE LINKS FOR THE NO-JPA DB */
            +"<section>"
                +"<h4>"
                    +"<div style='text-align:center;'>"
                        +"To see the products of the database without JPA: "
                        	+"<a href='/servlets-db-demo-with-jpa/all-products-no-jpa'><u>All products</u></a>"
                        +"<br>"
                        +"To add a new product to the database without JPA: "
                        	+"<a href='/servlets-db-demo-with-jpa/add-product-no-jpa.html'><u>Add product</u></a>"
                    +"</div"
                +"</h4>"
                +"</br><br><br>"
             +"</section>"

             
             /*PRINT HOW MANY PRODUCTS THE JPA DATABASE CONTAIN */
 			+"<section style='text-align:center;'>"
 				+"<h2>Currently the database WITH JPA contains <strong>" +sizeOfJPAdb+ "</strong> products.</h2><br>"
 			+"</section>"
 				
             /* GIVE LINKS FOR THE JPA DB */
             +"<section>"
             	  +"<h4>"
						+"<div style='text-align:center;'>"
							+"To see the products of the database with JPA: "
								+"<a href='/servlets-db-demo-with-jpa/all-products-with-jpa'><u>All products</u></a>"
								+"<br>"
								+"To add a new product to the database with JPA: "
								+"<a href='/servlets-db-demo-with-jpa/add-product-with-jpa.html'><u>Add product</u></a>"
						+"</div"
				  +"</h4>"
				  + "<br><br><br>"
			  +"</section>"
              
			  /* PRINT SOME MORE STUFF */
			+"<h6 style='text-align:center;'>" 
				+"Note: the databases are different for each segment!"
			+"</h6>"
        +"</main>"
    +"</body>"
    +"</html>'"
    );
    }
}

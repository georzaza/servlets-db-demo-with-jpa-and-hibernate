package with.jpa;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import models.Product;
import welcome.Welcome;


// Everything is self explained here.
public class AllProductsWithJpa extends HttpServlet {

	private static final long serialVersionUID = -5964689739431572205L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>DB Contents</title>");
        out.println("<style>");
        out.println("td { padding 2% 3%; border: 1px dotted rebeccapurple;}");
        out.println("th { color: gray;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");


        try  {
            List<Product> listOfProducts = Welcome.getProductDao().getAllProducts();
            out.println("<h3>The database contents so far.</h3>");
            out.println("<table style='border-spacing: 7px 13px;'>");
            out.println("<tr>"
            	+ "<th scope='col'>Barcode</th>"
            	+ "<th scope='col'>Name</th>"
            	+ "<th scope='col'>Color</th>"
            	+ "<th scope='col'>Description</th>"
            	+ "</tr>");
            
            listOfProducts.forEach((pr) -> {
            	out.println("<tr>");
                out.println(""
                    + "<td style='text-align:center;'>" + pr.getBarcode() + "</td>"
                    + "<td style='text-align:center;'>" + pr.getName()  + "</td>"
                    + "<td style='text-align:center;'>" + pr.getColor()  + "</td>"
                    + "<td style='text-align:center;'>" + pr.getDescription() + "</td>");
                out.println("</tr>");
            });
            
                        
            System.out.println("We cant print these products.----------");
            System.out.println("We cant print these products.----------");
            System.out.println("We cant print these products.----------");
            out.println("</table>");
            out.println("<h4>" + listOfProducts.size() + " records found.</h4>");
            out.println("<button type='submit'>"
                        +"<a style='text-decoration:none;color:rebeccapurple;'"
                        +"href='add-product-with-jpa.html'>Insert a new product</a</button>");
            
        }
        catch(Exception ex) {
            out.println("Oups. We can't deliver your request at this time. Try again later.");
            ex.getMessage();
            ex.printStackTrace();
        }
        out.println("</body></html>");
    out.close();
   }
}

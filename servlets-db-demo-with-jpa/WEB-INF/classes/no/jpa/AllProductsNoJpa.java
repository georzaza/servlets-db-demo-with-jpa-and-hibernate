package no.jpa;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AllProductsNoJpa extends HttpServlet {

	private static final long serialVersionUID = -5964689739431572205L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
		doGet(request, response);
	}
	
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
        
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

        String db_connect = "jdbc:mysql://localhost:3306/demo?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EET";
        String username = "temp";
        String password = "admin_pass";
        try {
        	// make the connectrion and create a statement
        	Connection conn = DriverManager.getConnection(db_connect, username, password);
            Statement stmt = conn.createStatement(); 
            
            // Execute the SQL statement
            String sqlStr = "select * from products order by id";
            out.println("<h3>The database contents so far.</h3>");
            ResultSet rset = stmt.executeQuery(sqlStr);

            
            int count = 0;		// counts the records
            out.println("<table style='border-spacing: 7px 13px;'>");
            out.println("<tr>"
                        +   "<th scope='col'>Barcode</th>"
                        +   "<th scope='col'>Name</th>"
                        +   "<th scope='col'>Color</th>"
                        +   "<th scope='col'>Description</th>"
                        +   "</tr>");
            while(rset.next()) {
                out.println("<tr>");
                out.println(""
                    + "<td style='text-align:center;'>" + rset.getString("barcode") + "</td>"
                    + "<td style='text-align:center;'>" + rset.getString("name")  + "</td>"
                    + "<td style='text-align:center;'>" + rset.getString("color")  + "</td>"
                    + "<td style='text-align:center;'>" + rset.getString("description") + "</td>");
                out.println("</tr>");
                count++;
            }
            out.println("</table>");
            out.println("<h4>" + count + " records found.</h4>");
            out.println("<button type='submit'>"
                        +"<a style='text-decoration:none;color:rebeccapurple;'"
                        +"href='add-product-no-jpa.html'>Insert a new product</a</button>");
            
        } catch(Exception ex) {
            out.println("Oups. We can't deliver your request at this time. Try again later.");
            System.out.println("<p>Error: " + ex.getMessage() + "</p>");  // for debug
            ex.printStackTrace();
        }
        out.println("</body></html>");
    out.close();
   }
}

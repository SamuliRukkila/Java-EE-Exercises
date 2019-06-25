/*
 * Testataan Connection Poolin ja sen käyttämiseksi
 * luodun Datasourcen toimintaa tietokantayhteyden luonnissa.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet(name = "ConnectionPoolTestServlet", urlPatterns = {"/ConnectionPoolTestServlet"})
public class ConnectionPoolTestServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
    //alustetaan yhteydenotossa ja kannan käytössä tarvittavia olioita
    Connection conn;
    Statement st;
    ResultSet rs;

  /*
   * Normaalisti yhteys kannattaa ottaa init-metodissa
   * joka siis suoritetaan aina kun servletti käynnistyy.
   */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);       
   try {
          
         /*Seuraavilla kolmella lauseella yhteys otetaan.
          * JDNI-nimi on "jdbc/sample" eli se viittaa jdbc/sample-
          * nimiseen resurssiin joka on yhdistetty connection pooliin. 
          */ 
          Context ctx = new InitialContext();
	  DataSource ds = (DataSource) ctx.lookup("jdbc/sample");
	  conn = ds.getConnection();         
                 
        }   
         catch (Exception ex) {} 
    
    }
    
   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
		
            //Haetaan vähän tietoa kannan asiakkaat-taulusta jotta nähdään toimiiko yhteys   
                try {
                    
                        st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM asiakkaat");
                        
                        out.println("<p>Asiakkaat-taulusta tulevaa tietoa näkyy tässä:</p>");

			while (rs.next()) {
				String nimi = rs.getString("nimi");
				String osoite = rs.getString("osoite");
				String email = rs.getString("email");                                    
                      
                      out.println("<p>Nimi :"+nimi+" Osoite :"+osoite+" Email :"+email+"</p>");
                                
			}
		} catch (Exception ex) {} 
                
                
                finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (st != null) st.close(); } catch (SQLException e) {}
			try { if (conn != null) conn.close(); } catch (SQLException e) {}
		        }
		
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
}

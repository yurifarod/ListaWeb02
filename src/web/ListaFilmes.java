package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "ListaFilmes", urlPatterns = {"/ListaFilmes"})


public class ListaFilmes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/cinemarketing";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
            Connection conn;
            out.println("<html>");
            out.println("<head>");
            out.println("<style type=\"text/css\">" +
                "body  {background-color:#0000000"+
                "font-family: verdana; color:white; font-size: 25; text-decoration:none;}"+
                "</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div align = \"center\">");
        Autenticador aut = new Autenticador();
	       try {
	            Class.forName(JDBC_DRIVER);
	            //ver newInstace();
	            conn = DriverManager.getConnection(DATABASE_URL, aut.getUser() , aut.getPass() );
	            Statement st = conn.createStatement();
	            ResultSet rec = st.executeQuery("SELECT * FROM filme");
                    out.println("<table border=1><tr>");
                    out.println("<td><b>CARTAZ<b></td><td><b>NOME DO FILME</b></td>"
                    		+ "<td><b>EM CARTAZ ATÉ:</b></td></tr>");
	            while(rec.next()) {
                        out.println("<tr><td><img src=\"Imagens/"+rec.getString(1)+".jpg\""
                        		+ " height=\"100\" weidth=\"100\"></td>"
                                +"<td>"+rec.getString(2)+"</td>"
                                +"<td>"+rec.getString(4)+"</td>"+ "</tr>");
	            }
                    out.println("</table>");
                   st.close();
	        } catch (SQLException s) {
	            out.println("SQL BUGOU AQUI: " + s.toString() + " "+ s.getErrorCode() + " " + s.getSQLState());
	        } catch (Exception e) {
	            out.println("JABU GRANDE AQUI: " + e.toString()+ e.getMessage());
	        }
	    out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
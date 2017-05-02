package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "CompraIngresso", urlPatterns = {"/CompraIngresso"})


public class CompraIngresso extends HttpServlet {
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
	            ResultSet rec = st.executeQuery("SELECT * FROM sessao ORDER BY numero");
                    out.println("<table border=1><tr>");
                    out.println("<td><b>CARTAZ<b></td>"
                    		+ "<td><b>SESSAO</b><td><b>DIA</b></td>"
                    		+ "<td><b>HORARIO</b></td>"
                    		+"<td><b>SALA</b></td>"
                    		+ "<td><b>COMPRA</b></td></tr>");
	            while(rec.next()) {
	            	int capac, vendas, resta;
	            	Statement st2 = conn.createStatement();
		            ResultSet rec2 = st2.executeQuery("SELECT capacidade FROM sala WHERE numero = '"+rec.getString(4) +"'");
		            rec2.next();
		            capac = Integer.parseInt(rec2.getString(1));
		            vendas = Integer.parseInt(rec.getString(6));
		            resta = capac - vendas;
                        out.println("<tr><td><img src=\"Imagens/"+rec.getString(2)+".jpg\""
                        		+ " height=\"100\" weidth=\"100\"></td>"
                                +"<td>"+rec.getString(1)+"</td>"
                                +"<td>"+rec.getString(3)+"</td>"
                                +"<td>"+rec.getString(5)+"</td>"
                                +"<td>"+rec.getString(4)+"</td>"
                                +"<td>");
                        if(resta > 0){
                        	out.println("<form action=\"Carrinho\" method = \"get\"	>");
                        	out.println("<select name=\"qntd\">");
                        	for(int i = 1; i < resta+1; i++){
                                out.println("<option value=\""+i+"\">"+i+"</option>");  	
                        	}         
                            out.println("</select>"
                            		+"<input type=\"text\" name=\"sessao\" style=\"display:none;\"  value="+rec.getString(1)+" >"
                            		+ "<input type=\"submit\" value=\"COMPRAR\" />");
                            out.println("</form></td></tr>");
                        }
                        else{
                        	out.println("ESGOTADO!");
                        }
                        
                        st2.close();
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
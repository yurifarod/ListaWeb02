package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Carrinho", urlPatterns = {"/Carrinho"})


public class Carrinho extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/cinemarketing";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int sessao = Integer.parseInt(request.getParameter("sessao"));
        int quant = Integer.parseInt(request.getParameter("qntd"));
        
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
	            ResultSet rec = st.executeQuery("SELECT * FROM sessao WHERE numero = '"+sessao+"';");
	            rec.next();
	            int totalVenda = quant + Integer.parseInt(rec.getString(6));
	            int totalRenda = totalVenda*10;
	            String filme = rec.getString(2);
	            	out.println("<br>SUA COMPRA FOI REALIZADA COM SUCESSO");
                    out.println("<br>Voce está comprando "+quant+" ingressos para a sessao: "+sessao);
                    out.println("<br>Imprima esta pagina como comprovante de compra!");
                    st.executeUpdate("UPDATE sessao SET venda = '"+totalVenda+"' WHERE numero = ' "+sessao+"';");
                    st.executeUpdate("UPDATE sessao SET renda = '"+totalRenda+"' WHERE numero = ' "+sessao+"';");
                rec = st.executeQuery("SELECT * FROM filme WHERE codigo = '"+filme+"';");
                rec.next();
                int filmeVenda = quant + Integer.parseInt(rec.getString(6));
                int filmeRenda = quant*10 + Integer.parseInt(rec.getString(7));
                st.executeUpdate("UPDATE filme SET inteiras = '"+filmeVenda+"' WHERE codigo = '"+filme+"';");
                st.executeUpdate("UPDATE filme SET renda = '"+filmeRenda+"' WHERE codigo = '"+filme+"';");
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
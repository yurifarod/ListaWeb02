package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "Login", urlPatterns = {"/Login"})


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/cinemarketing";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
            Connection conn;
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cinemarketing - FUNCIONARIOS</title>");
            out.println("<style type=\"text/css\">"
            		+"body  {background-image:url(\"Imagens/background.jpg\");"
            		+"font-family: verdana; color:white; font-size: 20;}"
            		+"ul {list-style-type: none;"
            		+"margin: 0; padding: 0; overflow: hidden;}"
            		+"li {float: left;}"
            		+"li a {display: block; color: white; text-align: center;"
            		+"padding: 16px; text-decoration: none; }"
            		+"li a:hover { background-color: #111111; }"
      				+"</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div align = \"center\">");
    	    out.println("<br><br>");
        Autenticador aut = new Autenticador();
	       try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DATABASE_URL, aut.getUser() , aut.getPass() );
	            Statement st = conn.createStatement();
	            ResultSet rec = st.executeQuery("SELECT senha FROM usuario WHERE login = '"+login+"';");
	            rec.next();
	            	if(rec.getString(1).equals(senha)){
	            		out.println("<iframe src=\"ListaLogin\" width=\"800\" height=\"500\"></iframe>");
	            	}
	            	else{
	            		out.println("USUÁRIO NÃO EXISTE!");
	            	}
                out.println("<br><a href=\"index.html\"><input type=\"button\" name=\"button1\" value=\"VOLTAR\"></a>");
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
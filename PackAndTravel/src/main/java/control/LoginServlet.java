package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.AccountUserDAO;
import model.HelperClass;
import model.AccountUser;
import java.util.logging.*;

/* Servlet implementation class LoginServlet*/

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getAnonymousLogger();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect("/common/Login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF=8");
		AccountUser user = null;
		String email= request.getParameter("email");

		String password= request.getParameter("password");



		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		AccountUserDAO tool=new AccountUserDAO(ds);
		


		try {
			user=tool.doRetrieveByKey(email);
		}catch (SQLException e) {
			logger.log(Level.WARNING, "Problema Parse/Sql!");
		}

		//controllo tra password inserita nel form e quella nel db
		boolean controlloPasswd=false;
		if(user.getEmail()!=null && user.getEmail().equals(email) ) {
			String passToCompare=user.getPassword();
			String passwdInserita=HelperClass.toHash(password);
			
			if(passToCompare.equals(passwdInserita)) {
				controlloPasswd=true;
			}
		}

		if(!controlloPasswd){ 
			String error="Login errato, riprova.";
			HttpSession session = request.getSession();
			session.setAttribute("isLogged", "false");
			request.setAttribute("error", error);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/Login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//significa che ho trovato l'utente
		HttpSession session = request.getSession();
		session.setAttribute("isLogged", "true"); 
		session.setAttribute("auth", user); //mi serve per recuperare le info dell'utente per account
		
		String admin;
		if(user.isAdmin()) {
			admin = "true";
		}
		else
			admin = "false";
		
			
		
		session.setAttribute("isAdmin", admin) ; 
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/Index.jsp");
		  dispatcher.forward(request, response);
		 


	}

}


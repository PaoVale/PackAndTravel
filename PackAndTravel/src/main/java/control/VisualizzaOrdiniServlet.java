package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.AccountUser;
import model.OrdineBean;
import model.OrdineDAO;

/**
 * Servlet implementation class VisualizzaOrdiniServlet
 */
@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public VisualizzaOrdiniServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Sono nella servlet visualizza ordini");
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		OrdineDAO oDAO = new OrdineDAO(ds);
		//Logger logger = Logger.getAnonymousLogger();
		AccountUser auth=(AccountUser) request.getSession().getAttribute("auth");
		String email=auth.getEmail();
		System.out.println(email);
		
		try {
			request.setAttribute("ordini", oDAO.doRetrieveByEmail(email));
		}catch (SQLException e) {
			//logger.log(Level.WARNING, "Problema accesso DB!");
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/VisualizzaOrdiniUtente.jsp");
		dispatcher.forward(request, response);
		
		
	}

}

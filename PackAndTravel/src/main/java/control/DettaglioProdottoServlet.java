package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import model.ProdottoDAO;

/**
 * Servlet implementation class VisualizzaProdottiServlet
 */
@WebServlet("/DettaglioProdottoServlet")
public class DettaglioProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettaglioProdottoServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO prodotto = new ProdottoDAO(ds);
		int codice=  (int) request.getAttribute("codice");
		try {
			request.setAttribute("prodotto", prodotto.doRetrieveByKey(codice));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

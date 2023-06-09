package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.ProdottoDAO;

/**
 * Servlet implementation class ModificaProdottoServlet
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ModificaProdottoServlet() {
        super();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		ProdottoDAO prodotto=new ProdottoDAO(ds);
		int codice=0;
		codice= Integer.parseInt(request.getParameter("codiceModifica"));
		String nome=null;
		String descrizione= null;
		String categoria=null;
		Double prezzo=0.0;
		
		System.out.println("nome: "+request.getParameter("nome")+ " descrizione: "+request.getParameter("descrizione")+ " categoria:"+request.getParameter("categoria")+" prezzo: "+request.getParameter("prezzo"));
		if (request.getParameter("nome")!="" && request.getParameter("nome")!=null && !request.getParameter("nome").isEmpty())
		{
			System.out.println("sono nella modifica del nome");
			nome = request.getParameter("nome");
			System.out.println("ho asegnato il nome");
			
			try {
				prodotto.doUpdate("nome", nome, codice);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("descrizione")!="" && request.getParameter("descrizione")!=null && !request.getParameter("descrizione").isEmpty())
		{
			System.out.println("sono nella modifica della descrizione");
			descrizione = request.getParameter("descrizione");
			System.out.println("ho assegnato la descrizione");
			
			try {
				prodotto.doUpdate("descrizione", descrizione, codice);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		
		if (request.getParameter("categoria")!= null)
		{
			categoria = request.getParameter("categoria");
			
			try {
				prodotto.doUpdate("categoria_nome", categoria, codice);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("prezzo")!="" && request.getParameter("prezzo") != null && !request.getParameter("prezzo").isEmpty())
		{
			prezzo = Double.parseDouble( request.getParameter("prezzo"));
			
			try {
				prodotto.doUpdatePrezzo(prezzo, codice);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		
		Part immaginePart = request.getPart("immagine");
		String nomeFile = immaginePart.getSubmittedFileName();

		if (nomeFile != null && !nomeFile.isEmpty()) { 
		 
	    	  request.setAttribute("codice", codice);
	      
	      RequestDispatcher dispatcher =
	      getServletContext().getRequestDispatcher("/UploadPhoto");
	      dispatcher.forward(request, response);
	      System.out.println("Sono dopo di dispatcher"); }
	      else {
	     
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/ProductView.jsp");
		dispatcher.forward(request, response); 
	      }
		
	}

}

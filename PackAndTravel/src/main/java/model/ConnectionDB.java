package model;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.logging.*;



public class ConnectionDB {
  

  
  static Connection conn = null;
  private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());
  
  private ConnectionDB() {
	    throw new IllegalStateException("Utility class");
	  }

  
  public static Connection getConnection()  {
    DataSource ds;
    try {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");

      ds = (DataSource) envCtx.lookup("jdbc/packandtravel");
      conn = ds.getConnection();
      logger.log(Level.INFO,"nel Database");

    } catch (NamingException e) {
      logger.log(Level.INFO,"non nel Database");
      logger.log(Level.SEVERE, e.getMessage());
      
    } catch (SQLException e) {
 
      e.printStackTrace();
    }
return conn;
  }
  
}
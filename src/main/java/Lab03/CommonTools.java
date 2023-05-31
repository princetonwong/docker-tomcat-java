package Lab03;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CommonTools {
	
	public static String RAXB310102519 = "RAXB310102519";
	public static String RAXB310103899 = "RAXB310103899";
	public static String RAXB31010612729 = "RAXB31010612729";
	public static String RAXB3101012771 = "RAXB3101012771";
	
	static int  count = 0;
	
	static Object dbLocker = new Object();
	
	static Object sessionFactoryLocker = new Object();
	
	static Object voucherSuffixLocker = new Object();
	
	static Connection conn = null;
	
	public static int voucherSuffix = 0;
	
	static SessionFactory sessionFactory = null;
	
	public static Connection getConnection() throws Throwable
	{
		synchronized(dbLocker)
		{
			if( conn == null || conn.isClosed() )
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jvc","root","P133233abc");	
			}
		}
		
		return conn;
	}
	
	public static SessionFactory getSessionFactory() throws Throwable
	{
		synchronized(sessionFactoryLocker)
		{
			if( sessionFactory == null || sessionFactory.isClosed() )
			{
				Configuration configuration = new Configuration();
				configuration.configure("hibernate.cfg.xml");
				sessionFactory = configuration.buildSessionFactory();
			}
		}
		
		return sessionFactory;
	}
	
	public static String getVoucherSuffix() throws Throwable
	{
		synchronized(voucherSuffixLocker)
		{
			voucherSuffix++;
			return String.format("%07d", voucherSuffix );
		}
	}
	
	public static String replaceAll(
		    String haystack,  
		    String needle,             
		    String replacement) {    

		    int i = haystack.lastIndexOf( needle );
		    if ( i != -1 ) {
		        StringBuffer buffer = new StringBuffer( haystack );
		        buffer.replace( i, i+needle.length(), replacement );
		        while( (i=haystack.lastIndexOf(needle, i-1)) != -1 ) {
		            buffer.replace( i, i+needle.length(), replacement );
		        }
		        haystack = buffer.toString();
		    }

		    return haystack;
		}
}

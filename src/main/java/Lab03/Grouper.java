package Lab03;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import jvg.generation;

public class Grouper 
{
	public static void process_Group_1( JV01 rule, SessionFactory sessionFactory ) throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		Session dbSession = null;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			String sql = "";
			
			sql = 		" SELECT p_hr_payment_date, p_hr_position, p_hr_department, sum(v_domAmt) as v_domAmt ";
			sql = sql + " FROM jv01 ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB3101012771 + "' ";
			sql = sql + " AND r_idx = ? ";
			sql = sql + " GROUP BY p_hr_payment_date, p_hr_position, p_hr_department ";
			
			Connection conn = CommonTools.getConnection();
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			pstmt1.setInt(++pstmtIdx1, rule.getR_idx());
			
			rs1 = pstmt1.executeQuery();
			
			while( rs1.next() )
			{
				Timestamp group_payment_date = rs1.getTimestamp("p_hr_payment_date");
				String group_position = rs1.getString("p_hr_position");
				String group_department = rs1.getString("p_hr_department");
				double group_amount = rs1.getDouble("v_domAmt");
				
				pstmtIdx1 = 0;
				
				sql = " SELECT * FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB3101012771 + "' AND "
						+ " p_hr_payment_date = :payment_date AND p_hr_position = :position AND p_hr_department = :department AND r_idx = :ruleIdx ";
				SQLQuery query = dbSession.createSQLQuery(sql);
				query.setTimestamp("payment_date", group_payment_date);
				query.setString("position", group_position);
				query.setString("department", group_department);
				query.setInteger("ruleIdx", rule.getR_idx());
				query.addEntity(JV01.class);
				List<JV01> payments = query.list();
			
				JV01 payment = payments.get(0).cloneMe();
				
				payment.setV_domAmt(group_amount);
				payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB31010612729 );
				dbSession.save(payment);
			}
				
			rs1.close();
				
			pstmt1.close();
				
			dbSession.close();
		}
		catch( Throwable thw) 
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			thw.printStackTrace(pw);
			String sStackTrace = sw.toString(); 
			
			generation.writer.write( sStackTrace ); PrintWriter writer;
			generation.writer.newLine(); 
			generation.writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			
		}
	}
	
	
	
	
	public static void process_Group_2( JV01 rule, SessionFactory sessionFactory ) throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		Session dbSession = null;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			String sql = "";
			
			sql = 		" SELECT p_hr_payment_date, p_hr_department, sum(v_domAmt) as v_domAmt ";
			sql = sql + " FROM jv01 ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB3101012771 + "' ";
			sql = sql + " AND r_idx = ? ";
			sql = sql + " GROUP BY p_hr_payment_date, p_hr_department ";
			
			Connection conn = CommonTools.getConnection();
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			pstmt1.setInt(++pstmtIdx1, rule.getR_idx());
			
			rs1 = pstmt1.executeQuery();
			
			while( rs1.next() )
			{
				Timestamp group_payment_date = rs1.getTimestamp("p_hr_payment_date");
				String group_department = rs1.getString("p_hr_department");
				double group_amount = rs1.getDouble("v_domAmt");
				
				sql = " SELECT * FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB3101012771 + "' AND "
						+ " p_hr_payment_date = :payment_date AND p_hr_department = :department AND r_idx = :ruleIdx ";
				SQLQuery query = dbSession.createSQLQuery(sql);
				query.setTimestamp("payment_date", group_payment_date);
				query.setString("department", group_department);
				query.setInteger("ruleIdx", rule.getR_idx());
				query.addEntity(JV01.class);
				List<JV01> payments = query.list();
			
				JV01 payment = payments.get(0).cloneMe();
				
				payment.setV_domAmt(group_amount);
				payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB31010612729 );
				dbSession.save(payment);
			}
				
			rs1.close();
				
			pstmt1.close();
				
			dbSession.close();
		}
		catch( Throwable thw) 
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			thw.printStackTrace(pw);
			String sStackTrace = sw.toString(); 
			
			generation.writer.write( sStackTrace ); PrintWriter writer;
			generation.writer.newLine(); 
			generation.writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			
		}
	}
	
	
	public static void process_Group_No( JV01 rule, SessionFactory sessionFactory ) throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		Session dbSession = null;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			String sql = " SELECT * FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB3101012771 + "' AND r_idx = :ruleIdx ";
			SQLQuery query = dbSession.createSQLQuery(sql);
			query.setInteger("ruleIdx", rule.getR_idx());
			query.addEntity(JV01.class);
			List<JV01> payments = query.list();
		
			for( JV01 payment : payments)
			{
				JV01 _payment = payment.cloneMe();
				_payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB31010612729 );
				dbSession.save(_payment);	
			}
							
			dbSession.close();
		}
		catch( Throwable thw) 
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			thw.printStackTrace(pw);
			String sStackTrace = sw.toString(); 
			
			generation.writer.write( sStackTrace ); PrintWriter writer;
			generation.writer.newLine(); 
			generation.writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			
		}
	}
}

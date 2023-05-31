package jvg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

import Lab03.CommonTools;
import Lab03.Employee;
import Lab03.Grouper;
import Lab03.JV01;
import Lab03.Mpf;
import Lab03.Translator;

/**
 * Servlet implementation class generation
 */
@WebServlet("/generation")
public class generation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static BufferedWriter writer = null;
       
	static String paymentDateString = "10/05/2047";
	static String accountingDateString = "30/04/2047";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public generation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		writer.println("Hello World!");
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    String _PaymentType = request.getParameter("PaymentType");
	    paymentDateString = request.getParameter("MpfPaymentDate");
	    accountingDateString = request.getParameter("MpfAccoutingDate");

	    try {
			
			CommonTools.voucherSuffix = 1;
			
			String dataFolder = System.getProperty("DataFolder");
			String dataFolderIn = dataFolder + "\\Inbox";
			String dataFolderOut = dataFolder + "\\Outbox";
			String option = "";
			String dateString = "";
			
			writer = new BufferedWriter(new FileWriter(dataFolderOut + "\\log.txt"));
			
			if( _PaymentType.equals("Normal"))
			{
				option = "TOA_Normal_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Normal_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_Normal_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Normal_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("Final"))
			{
				option = "TOA_Final_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Final_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_Final_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Final_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("MPF"))
			{
				LinkedList<Employee> employees = readEmployee( dataFolderIn + "\\hr_mpf_employee.csv" );
				LinkedList<Mpf> mpfs = readMpf( dataFolderIn + "\\hr_mpf_gneration.csv" );
				
				LinkedList<Employee> payments = crossCheck( employees, mpfs );
				
				generatePaymentFile( dataFolderOut + "\\mpfPayments.csv", payments );
				
				option = "TOA_MPF_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderOut + "\\mpfPayments.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_MPF_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderOut + "\\mpfPayments.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("DO"))
			{
				option = "TOA_Do_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Do_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_Do_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Do_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("AL"))
			{
				option = "TOA_AL_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_AL_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_AL_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_AL_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("Bonus"))
			{
				option = "TOA_Bonus_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Bonus_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_Bonus_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Bonus_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("Medical"))
			{
				option = "TOA_Medical_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Medical_AutoPay"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOA_Medical_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_Medical_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			else if( _PaymentType.equals("WorkInjury"))
			{
				option = "TOA_WorkInjury_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
				option = "TOAR_WorkInjury_Cheque"; gen( CommonTools.getSessionFactory(), dataFolderIn + "\\hr_payment.csv", option, dataFolderOut + "\\Import_to_M18_" + option + ".xlsx" );
			}
			

	    	
		} 
	    catch (Throwable thw) 
	    {
	    	try { 

					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
					
			} catch( Throwable thwa) {}
		}
	    finally
	    {
	    	try { writer.close(); } catch( Throwable thw) {}
	    }
	    
	    // Send a response back to the client
	    PrintWriter out = response.getWriter();
	    out.print("Completed and please check out the result in C:\\JVG\\Outbox");
	    out.flush();
	    
	}
	
	static void gen( SessionFactory sessionFactory, String dataFile, String option, String outputFile ) throws Throwable
	{
		clearTempTable();
		
		String settingFolder = System.getProperty("SettingFolder");
		
		JSONObject lists = readLists( settingFolder + "\\Settings\\Lists.xlsx" );
		
		JSONObject maps = readMaps( settingFolder + "\\Settings\\Maps.xlsx" );		
		
		LinkedList<JV01> rules = readRules( settingFolder + "\\Settings\\Rules.xlsx", sessionFactory );
		
		LinkedList<JV01> payments = readPaymentsFromCsv( dataFile, sessionFactory, lists, option, maps );

		joinPaymentsWithRules( sessionFactory, rules, payments, lists, maps );
		
		applyGrouping( sessionFactory, rules, payments, lists, maps );
		
		assignVoucherNumber();
		
		patchVoucherNumber();
				
		JSONObject voucherData = getDataForOutput();
		
		createVoucher( voucherData, outputFile );
	}
	
	
	static void clearTempTable() throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
						
		try
		{
			Connection conn = CommonTools.getConnection();
						
			String sql = "TRUNCATE jv01";
						
			pstmt1 = conn.prepareStatement(sql);
			
			pstmt1.executeUpdate();
			
			pstmt1.close();
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
		}
	}
	
	
	
	static void joinPaymentsWithRules( SessionFactory sessionFactory, LinkedList<JV01> rules, LinkedList<JV01> payments, JSONObject lists, JSONObject maps ) throws Throwable
	{
		Session dbSession = null;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			Connection conn = CommonTools.getConnection();
			
			
			for( JV01 payment : payments  )
			{
				try
				{
					for( JV01 rule : rules )
					{
						if( rule.getR_rule_id() <= 100 && payment.getP_hr_department().equals("TOAR Mangement") && payment.getP_hr_business_entity().equals("TOAR") )
						{
							continue;
						}
						
						if( isRuleApplyToThisPayment( payment, rule, lists ) )
						{
							JV01 paymentStep1 = rule.cloneDataOnly();
							
							JV01.assignHrPaymentData(paymentStep1, payment);
							
							paymentStep1.setV_tDate( Translator.get_tDate(payment, rule.getR_tDate() ) );
							paymentStep1.setV_beId( Translator.get_beId( payment , rule.getR_beId() ) );
							paymentStep1.setV_staffId( Translator.get_staffId( payment , rule.getR_staffId() ) );
							paymentStep1.setV_virDeptId( Translator.get_virDeptId( payment , rule.getR_virDeptId(), maps ) );
							paymentStep1.setV_AI1( Translator.get_AI1( payment , rule.getR_AI1(), maps ) );
							paymentStep1.setV_AI2( Translator.get_AI2( payment , rule.getR_AI2(), maps ) );
							paymentStep1.setV_AI3( Translator.get_AI3( payment , rule.getR_AI3() ) );
							paymentStep1.setV_AI4( Translator.get_AI4( payment , rule.getR_AI4() ) );
							paymentStep1.setV_cnDeptId( Translator.get_cnDeptId( payment , rule.getR_cnDeptId() ) );
							paymentStep1.setV_accId( Translator.get_accId( payment , rule.getR_accId(), maps ) );
							paymentStep1.setV_accDesc( Translator.get_accDesc( payment , rule.getR_accDesc(), maps ) );
							paymentStep1.setV_curId( Translator.get_curId( payment , rule.getR_curId() ) );
							paymentStep1.setV_domAmt( Translator.get_domAmt( payment , rule.getR_domAmt() ) );
							paymentStep1.setV_c_d( Translator.get_c_d( payment , rule.getR_c_d() ) );
							paymentStep1.setV_jlTypeId( Translator.get_jlTypeId( payment , rule.getR_jlTypeId() ) );
							paymentStep1.setV_payee( Translator.get_payee( payment , rule.getR_payee() ) );
							paymentStep1.setV_chequeDate( Translator.get_chequeDate(payment, rule.getR_chequeDate() ) );
							paymentStep1.setV_chequeNo( Translator.get_chequeNo( payment , rule.getR_chequeNo() ) );
							paymentStep1.setV_slmId( Translator.get_slmId( payment , rule.getR_slmId() ) );
							paymentStep1.setV_bankSlipNo( Translator.get_bankSlipNo( payment , rule.getR_bankSlipNo() ) );
							paymentStep1.setV_particular( Translator.get_particular( payment , rule.getR_particular() ) );
							paymentStep1.setV_expired( Translator.get_expired( payment , rule.getR_expired() ) );
							paymentStep1.setV_intercovoucherTranVirdeptId( CommonTools.RAXB3101012771 );
							
							dbSession.save(paymentStep1);
						}
					}	
				}
				catch( Throwable thw) 
				{	
					writer.write( payment.getP_idx() + " " + " " + payment.getP_hr_employee_name() + " " + payment.getP_hr_department() + " " + thw.toString() ); writer.newLine(); 
					writer.flush();
				}
			}
			

			dbSession.close();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
		}
	}
	

	
	static void applyGrouping( SessionFactory sessionFactory, LinkedList<JV01> rules, LinkedList<JV01> payments, JSONObject lists, JSONObject maps ) throws Throwable
	{
		Session dbSession = null;
		
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			Connection conn = CommonTools.getConnection();
			
			
			for( JV01 rule : rules )
			{
				if( rule.getR_rule_grouping().startsWith("Group 1") )
				{
					Grouper.process_Group_1( rule, sessionFactory );
				}
				else if( rule.getR_rule_grouping().startsWith("Group 2") )
				{
					Grouper.process_Group_2( rule, sessionFactory );
				}
				else if( rule.getR_rule_grouping().equals("No"))
				{
					Grouper.process_Group_No( rule, sessionFactory );
				}
				else
				{
					writer.write( "Unknow grouping " + rule.getR_rule_grouping() );
					writer.newLine();
				}
			}
			
			dbSession.close();
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
		}
	}
	
	
	
	static void assignVoucherNumber() throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int pstmtIdx2 = 0;
						
		try
		{
			Connection conn = CommonTools.getConnection();
						
			String sql = " SELECT DISTINCT r_rule_id FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' ORDER BY r_rule_id ";
						
			pstmt1 = conn.prepareStatement(sql);
			
			rs1 = pstmt1.executeQuery();
			
			LinkedList<Integer> ruleIds = new LinkedList<Integer>();
			
			while( rs1.next() )
			{
				ruleIds.add( rs1.getInt("r_rule_id") );
			}
			
			rs1.close();
			
			pstmt1.close();
			
			for( Integer ruleId : ruleIds  )
			{
				sql = " SELECT min(r_rule_sequence) as r_rule_sequence_min, max(r_rule_sequence) as r_rule_sequence_max FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' and r_rule_id = ? ";

				pstmt1 = conn.prepareStatement(sql);
				
				Integer r_rule_sequence_min = 0;
				Integer r_rule_sequence_max = 0;
				
				pstmtIdx1 = 0;
				
				pstmt1.setInt(++pstmtIdx1, ruleId);
				
				rs1 = pstmt1.executeQuery();
				
				rs1.next();
				
				r_rule_sequence_min = rs1.getInt("r_rule_sequence_min");
				r_rule_sequence_max = rs1.getInt("r_rule_sequence_max");
				
				rs1.close();
				
				pstmt1.close();
				
				
				
				sql = " SELECT distinct r_rule_link FROM jv01 WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' and r_rule_id = ? ";

				pstmt1 = conn.prepareStatement(sql);
				
				pstmtIdx1 = 0;
				
				pstmt1.setInt(++pstmtIdx1, ruleId);
				
				rs1 = pstmt1.executeQuery();
				
				rs1.next();
				
				String r_rule_link = rs1.getString("r_rule_link");
				
				rs1.close();
				
				pstmt1.close();
				
				
				if( r_rule_link.startsWith("Link 1"))
				{
					sql = 		" SELECT id, v_c_d, r_rule_sequence, r_rule_voucher_prefix ";
					sql = sql + " FROM jv01 ";
					sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND r_rule_id = ? ";
					sql = sql + " ORDER BY p_hr_payment_date, p_hr_department, r_rule_sequence, r_idx ";
				}
				else if( r_rule_link.equals("No"))
				{
					sql = 		" SELECT id, v_c_d, r_rule_sequence, r_rule_voucher_prefix ";
					sql = sql + " FROM jv01 ";
					sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND r_rule_id = ? ";
					sql = sql + " ORDER BY p_hr_employee_name, r_rule_sequence, r_idx, v_c_d ";
				}
				else
				{
					sql = "";
				}

				
				pstmt1 = conn.prepareStatement(sql);
				
				pstmtIdx1 = 0;
				
				pstmt1.setInt(++pstmtIdx1, ruleId);
				
				rs1 = pstmt1.executeQuery();
			
				Integer previousRuleSequence = -1;
				String previousCd = null;
				
				String voucherSuffix = "";
				
				while( rs1.next() )
				{
					Integer id = rs1.getInt("id");
					String v_c_d = rs1.getString("v_c_d");
					Integer r_rule_sequence = rs1.getInt("r_rule_sequence");
					String r_rule_voucher_prefix = rs1.getString("r_rule_voucher_prefix");
					
					if( r_rule_link.equals("No"))
					{
						if( v_c_d.equals("Debit") )
						{
							voucherSuffix = CommonTools.getVoucherSuffix();
						}
					}
					else
					{
						if( previousRuleSequence == -1 )
						{
							voucherSuffix = CommonTools.getVoucherSuffix();
							previousCd = v_c_d;
						}
						else if( r_rule_sequence == r_rule_sequence_min && previousRuleSequence == r_rule_sequence_max && r_rule_sequence_max > r_rule_sequence_min )
						{
							voucherSuffix = CommonTools.getVoucherSuffix();
						}
						else if (r_rule_sequence_max == 1 && r_rule_sequence_min == 1)
						{
							if( previousCd.equals("Credit") )
							{
								voucherSuffix = CommonTools.getVoucherSuffix();
							}
							
							previousCd = v_c_d;
						}
						
						previousRuleSequence = r_rule_sequence;
					}
					
					sql = " UPDATE jv01 SET v_maint_code = ? WHERE id = ? ";
										
					pstmt2 = conn.prepareStatement(sql);
					
					pstmtIdx2 = 0;
					
					String voucherNumber = r_rule_voucher_prefix + voucherSuffix;
					
					pstmt2.setString(++pstmtIdx2, voucherNumber);
					pstmt2.setInt(++pstmtIdx2, id);
									
					pstmt2.executeUpdate();
					
					pstmt2.close();
				}
				
				rs1.close();
				
				pstmt1.close();
			}
			

			
			
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			try { rs2.close(); } catch( Throwable thw) {}
			try { pstmt2.close(); } catch( Throwable thw) {}
		}
	}
	
	
	
	
	
	static void patchVoucherNumber() throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int pstmtIdx2 = 0;
						
		try
		{
			Connection conn = CommonTools.getConnection();
						
			String sql = "";
			
			
			sql = 		" SELECT distinct v_virDeptId ";
			sql = sql + " FROM jv01 ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND (   (r_rule_id = 1 AND r_rule_sequence = 3) OR (r_rule_id = 7 AND r_rule_sequence = 3)   )  AND r_rule_voucher_prefix = 'PV' ";
			sql = sql + " AND p_hr_payment_method = 'Auto Pay' ";
						
			pstmt1 = conn.prepareStatement(sql);
			
			rs1 = pstmt1.executeQuery();
			
			LinkedList<String> v_virDeptIds = new LinkedList<String>();
			
			while( rs1.next() )
			{
				v_virDeptIds.add( rs1.getString("v_virDeptId") );
			}
			
			rs1.close();
			
			pstmt1.close();
			
			for( String v_virDeptId : v_virDeptIds )
			{
				sql = 		" UPDATE jv01 ";
				sql = sql + " SET v_maint_code = ? ";
				sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND (   (r_rule_id = 1 AND r_rule_sequence = 3) OR (r_rule_id = 7 AND r_rule_sequence = 3)   )  AND r_rule_voucher_prefix = 'PV' AND v_virDeptId = ? ";
				sql = sql + " AND p_hr_payment_method = 'Auto Pay' ";
							
				pstmt1 = conn.prepareStatement(sql);
				
				pstmtIdx1 = 0;
				
				String voucherNumber = "PV" + CommonTools.getVoucherSuffix();
				
				pstmt1.setString(++pstmtIdx1, voucherNumber);
				pstmt1.setString(++pstmtIdx1, v_virDeptId);
				
				pstmt1.executeUpdate();
				
				pstmt1.close();
			}
			
			
			sql = 		" UPDATE jv01 SET v_maint_code = ? ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND (   (r_rule_id = 2 AND r_rule_sequence = 1) OR (r_rule_id = 8 AND r_rule_sequence = 1)   )  AND r_rule_voucher_prefix = 'JV' ";
			sql = sql + " AND p_hr_payment_method = 'Auto Pay' ";
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			String voucherNumber = "JV" + CommonTools.getVoucherSuffix();
			
			pstmt1.setString(++pstmtIdx1, voucherNumber);
			
			pstmt1.executeUpdate();
			
			pstmt1.close();
			
			
			sql = 		" UPDATE jv01 SET v_maint_code = ? ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND (   (r_rule_id = 2 AND r_rule_sequence = 2) OR (r_rule_id = 8 AND r_rule_sequence = 2)   ) AND r_rule_voucher_prefix = 'IC' ";
			sql = sql + " AND p_hr_payment_method = 'Auto Pay' ";
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			voucherNumber = "IC" + CommonTools.getVoucherSuffix();
			
			pstmt1.setString(++pstmtIdx1, voucherNumber);
			
			pstmt1.executeUpdate();
			
			pstmt1.close();

			
			
			sql = 		" UPDATE jv01 SET v_maint_code = ? ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' AND (   (r_rule_id = 2 AND r_rule_sequence = 3) OR (r_rule_id = 8 AND r_rule_sequence = 3)   ) AND r_rule_voucher_prefix = 'PV' ";
			sql = sql + " AND p_hr_payment_method = 'Auto Pay' ";
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			voucherNumber = "PV" + CommonTools.getVoucherSuffix();
			
			pstmt1.setString(++pstmtIdx1, voucherNumber);
			
			pstmt1.executeUpdate();
			
			pstmt1.close();
			
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			try { rs2.close(); } catch( Throwable thw) {}
			try { pstmt2.close(); } catch( Throwable thw) {}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	static JSONObject getDataForOutput( ) throws Throwable
	{
		JSONObject voucherData = new JSONObject();
		JSONArray voucherMasterList = new JSONArray();
		JSONArray voucherDetailList = new JSONArray();
		
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		int pstmtIdx2 = 0;
		
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		int pstmtIdx3 = 0;
		
		
		LinkedList<String> ruleIdList = new LinkedList<String>();
		LinkedList<String> maintCodeList = new LinkedList<String>();
		
		try
		{
			Connection conn = CommonTools.getConnection();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			
			String sql = "";
						
			sql = 		" SELECT p_hr_payment_date, p_hr_department, r_rule_sequence, r_idx, ";
			sql = sql + " v_maint_code,  ";
			sql = sql + " v_tDate,  ";
			sql = sql + " v_beId,  ";
			sql = sql + " v_staffId,  ";
			sql = sql + " v_cnDeptId,  ";
			sql = sql + " v_bankSlipNo,  ";
			sql = sql + " v_slmId,  ";
			sql = sql + " v_virDeptId, "; 
			sql = sql + " v_expired  ";
			sql = sql + " FROM jv01  ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' ";
			sql = sql + " order by p_hr_payment_date, p_hr_department, r_rule_sequence, r_idx ";
						
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			rs1 = pstmt1.executeQuery();
			
			HashMap<String, String> mainCodes = new HashMap<String, String>();
			
			while( rs1.next() )
			{
				String maint_code = rs1.getString("v_maint_code");
				Timestamp tDate = rs1.getTimestamp("v_tDate");
				String beId = rs1.getString("v_beId");
				String staffId = rs1.getString("v_staffId");
				String cnDeptId = rs1.getString("v_cnDeptId");
				String bankSlipNo = rs1.getString("v_bankSlipNo");
				String slmId = rs1.getString("v_slmId");
				String virDeptId = rs1.getString("v_virDeptId");
				String expired = rs1.getString("v_expired");
				
				if( !mainCodes.containsKey(maint_code) )
				{
					mainCodes.put(maint_code, "");
					
					JSONObject voucherMaster = new JSONObject();
					
					if( maint_code == null ) voucherMaster.put("code", "N/A"); else voucherMaster.put("code", maint_code);
					if( tDate == null ) voucherMaster.put("tDate", "N/A"); else voucherMaster.put("tDate", sdf.format( tDate )  );
					if( beId == null ) voucherMaster.put("beId", "N/A"); else voucherMaster.put("beId", beId);
					if( staffId == null ) voucherMaster.put("createUid", "N/A"); else voucherMaster.put("createUid", staffId);
					if( cnDeptId == null ) voucherMaster.put("cnDeptId", "N/A"); else voucherMaster.put("cnDeptId", cnDeptId);
					if( bankSlipNo == null ) voucherMaster.put("bankSlipNo", "N/A"); else voucherMaster.put("bankSlipNo", bankSlipNo);
					if( slmId == null ) voucherMaster.put("slmId", "N/A"); else voucherMaster.put("slmId", slmId);
					if( virDeptId == null ) voucherMaster.put("virDeptId", "N/A"); else voucherMaster.put("virDeptId", virDeptId);
					if( expired == null ) voucherMaster.put("expired", "N/A"); else voucherMaster.put("expired", expired);
					if( staffId == null ) voucherMaster.put("staffId", "N/A"); else voucherMaster.put("staffId", staffId);
					
					voucherMasterList.put( voucherMaster );
				}
			}
			
			rs1.close();
			
			pstmt1.close();
			
			voucherData.put("voucherMasterList", voucherMasterList);
			
			
			
			
			
			
			
			sql = 		" SELECT p_hr_payment_date, p_hr_department, r_rule_sequence, r_idx, ";
			sql = sql + " v_maint_code, ";
			sql = sql + " v_tDate, ";
			sql = sql + " v_staffId, ";
			sql = sql + " v_virDeptId, ";
			sql = sql + " v_AI1, ";
			sql = sql + " v_AI2, ";
			sql = sql + " v_AI3, ";
			sql = sql + " v_AI4, ";
			sql = sql + " v_cnDeptId, ";
			sql = sql + " v_accId, ";
			sql = sql + " v_accDesc, ";
			sql = sql + " v_curId, ";
			sql = sql + " v_domAmt, ";
			sql = sql + " v_c_d, ";
			sql = sql + " v_jlTypeId, ";
			sql = sql + " v_payee, ";
			sql = sql + " v_chequeDate, ";
			sql = sql + " v_chequeNo, ";
			sql = sql + " v_slmId, ";
			sql = sql + " v_bankSlipNo, ";
			sql = sql + " v_particular ";
			sql = sql + " FROM jv01  ";
			sql = sql + " WHERE v_intercovoucherTranVirdeptId = '" + CommonTools.RAXB31010612729  + "' ";
			sql = sql + " ORDER BY p_hr_payment_date, p_hr_department, r_rule_sequence, r_idx ";
			
			pstmt1 = conn.prepareStatement(sql);
			
			pstmtIdx1 = 0;
			
			rs1 = pstmt1.executeQuery();
						
			while( rs1.next() )
			{
				String v_maint_code = rs1.getString("v_maint_code");
				Timestamp v_tDate = rs1.getTimestamp("v_tDate");
				String v_staffId = rs1.getString("v_staffId");
				String v_virDeptId = rs1.getString("v_virDeptId");
				String v_AI1 = rs1.getString("v_AI1");
				String v_AI2 = rs1.getString("v_AI2");
				String v_AI3 = rs1.getString("v_AI3");
				String v_AI4 = rs1.getString("v_AI4");
				String v_cnDeptId = rs1.getString("v_cnDeptId");
				String v_accId = rs1.getString("v_accId");
				String v_accDesc = rs1.getString("v_accDesc");
				String v_curId = rs1.getString("v_curId");
				Double v_domAmt = rs1.getDouble("v_domAmt");
				String v_c_d = rs1.getString("v_c_d");
				String v_jlTypeId = rs1.getString("v_jlTypeId");
				String v_payee = rs1.getString("v_payee");
				Timestamp v_chequeDate = rs1.getTimestamp("v_chequeDate");
				String v_chequeNo = rs1.getString("v_chequeNo");
				String v_slmId = rs1.getString("v_slmId");
				String v_bankSlipNo = rs1.getString("v_bankSlipNo");
				String v_particular = rs1.getString("v_particular");
				
				JSONObject voucherDetail = new JSONObject();
				
				if( v_maint_code == null ) voucherDetail.put("maint.code", "N/A"); else voucherDetail.put("maint.code", v_maint_code);
				if( v_tDate == null ) voucherDetail.put("tDate", "N/A"); else voucherDetail.put("tDate", sdf.format( v_tDate ));
				if( v_staffId == null ) voucherDetail.put("staffId", "N/A"); else voucherDetail.put("staffId", v_staffId);
				if( v_virDeptId == null ) voucherDetail.put("virDeptId", "N/A"); else voucherDetail.put("virDeptId", v_virDeptId);
				if( v_AI1 == null ) voucherDetail.put("AI1", "N/A"); else voucherDetail.put("AI1", v_AI1);
				if( v_AI2 == null ) voucherDetail.put("AI2", "N/A"); else voucherDetail.put("AI2", v_AI2);
				if( v_AI3 == null ) voucherDetail.put("AI3", "N/A"); else voucherDetail.put("AI3", v_AI3);
				if( v_AI4 == null ) voucherDetail.put("AI4", "N/A"); else voucherDetail.put("AI4", v_AI4);
				if( v_cnDeptId == null ) voucherDetail.put("cnDeptId", "N/A"); else voucherDetail.put("cnDeptId", v_cnDeptId);
				if( v_accId == null ) voucherDetail.put("accId", "N/A"); else voucherDetail.put("accId", v_accId);
				if( v_accDesc == null ) voucherDetail.put("accDesc", "N/A"); else voucherDetail.put("accDesc", v_accDesc);
				if( v_curId == null ) voucherDetail.put("curId", "N/A"); else voucherDetail.put("curId", v_curId);
				if( v_domAmt == null ) voucherDetail.put("domAmt", "N/A"); else voucherDetail.put("domAmt", v_domAmt.toString() + "");
				if( v_c_d == null ) voucherDetail.put("c_d", "N/A"); else voucherDetail.put("c_d", v_c_d);
				if( v_jlTypeId == null ) voucherDetail.put("jlTypeId", "N/A"); else voucherDetail.put("jlTypeId", v_jlTypeId);
				if( v_payee == null ) voucherDetail.put("payee", "N/A"); else voucherDetail.put("payee", v_payee);
				if( v_chequeDate == null ) voucherDetail.put("chequeDate", "N/A"); else voucherDetail.put("chequeDate", sdf.format( v_chequeDate ) );
				if( v_chequeNo == null ) voucherDetail.put("chequeNo", "N/A"); else voucherDetail.put("chequeNo", v_chequeNo);
				if( v_slmId == null ) voucherDetail.put("slmId", "N/A"); else voucherDetail.put("slmId", v_slmId);
				if( v_bankSlipNo == null ) voucherDetail.put("bankSlipNo", "N/A"); else voucherDetail.put("bankSlipNo", v_bankSlipNo);
				if( v_particular == null ) voucherDetail.put("particular", "N/A"); else voucherDetail.put("particular", v_particular);
				voucherDetail.put("intercovoucherTranVirdeptId", "N/A");
				
				voucherDetailList.put( voucherDetail );
			}
			
			rs1.close();
			
			pstmt1.close();
			
			voucherData.put("voucherDetailList", voucherDetailList);
			
			
			
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
			
			try { rs2.close(); } catch( Throwable thw) {}
			try { pstmt2.close(); } catch( Throwable thw) {}
			
			try { rs3.close(); } catch( Throwable thw) {}
			try { pstmt3.close(); } catch( Throwable thw) {}
		}
		
		return voucherData;
	}
	
	
	
	
	static boolean isRuleApplyToThisPayment( JV01 payment, JV01 rule, JSONObject lists ) throws Throwable
	{		
		if( !isPatternMatched( payment.getP_hr_payment_no().trim(), rule.getR_hr_payment_no().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_payment_name().trim(), rule.getR_hr_payment_name().trim(), lists ) ) return false;
		//if( !isPatternMatched( payment.getP_hr_payment_date().trim(), rule.getR_hr_payment_date().trim(), lists ) ) return false;
		//if( !isPatternMatched( payment.getP_hr_accounting_date().trim(), rule.getR_hr_accounting_date().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_business_entity().trim(), rule.getR_hr_business_entity().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_department().trim(), rule.getR_hr_department().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_employee_name().trim(), rule.getR_hr_employee_name().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_position().trim(), rule.getR_hr_position().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_payroll_category().trim(), rule.getR_hr_payroll_category().trim(), lists ) ) return false;
		//if( !isPatternMatched( payment.getP_hr_amount().trim(), rule.getR_hr_amount().trim(), lists ) ) return false;
		if( !isPatternMatched( payment.getP_hr_payment_method().trim(), rule.getR_hr_payment_method().trim(), lists ) ) return false;
		
		return true;
	}
	
	
	
	static boolean isPatternMatched( String value, String pattern, JSONObject lists ) throws Throwable
	{
		boolean isMatched = true;
		
		if( !pattern.startsWith("All ") )
		{
			if(pattern.indexOf(" in ") != -1 )
			{
				if(pattern.indexOf(" list ") != -1 )
				{
					if(pattern.indexOf(" characters of ") != -1 )
					{
						String rangeStr = pattern.substring(0, pattern.indexOf(" characters of ") );
						
						StringTokenizer rangeTokenizer = new StringTokenizer( rangeStr, "-" );
						
						int startIdx = Integer.parseInt(rangeTokenizer.nextToken()) - 1;
						int endIdx = Integer.parseInt(rangeTokenizer.nextToken());
						
						String _value = value.substring(startIdx, endIdx).trim();
								
						String listName = pattern.substring( pattern.indexOf(" in list ") + " in list ".length(), pattern.length() );
												
						if( !lists.getJSONObject(listName).has(_value) )
						{
							isMatched = false;
						}
					}
					else
					{
						String listName = pattern.substring( pattern.indexOf(" in list ") + " in list ".length(), pattern.length() );
						
						if( !lists.getJSONObject(listName).has(value) )
						{
							isMatched = false;
						}
					}
				}
				else
				{
					if(pattern.indexOf(" characters of ") != -1 )
					{
						String rangeStr = pattern.substring(0, pattern.indexOf(" characters of ") );
						
						StringTokenizer rangeTokenizer = new StringTokenizer( rangeStr, "-" );
						
						int startIdx = Integer.parseInt(rangeTokenizer.nextToken()) - 1;
						int endIdx = Integer.parseInt(rangeTokenizer.nextToken());
						
						String _value = value.substring(startIdx, endIdx).trim();
								
						String _pattern = pattern.substring( pattern.indexOf(" in ") + " in ".length(), pattern.length() );
						
						StringTokenizer patternTokenizer = new StringTokenizer( _pattern, "," );
											
						boolean isNonMatched = true;
						
						while( patternTokenizer.hasMoreTokens() )
						{
							String token = patternTokenizer.nextToken().trim();
							
							if( token.equals(_value) )
							{
								isNonMatched = false;
								break;
							}
						}
						
						if( isNonMatched )
						{
							isMatched = false;
						}
					}
					else
					{
						String _pattern = pattern.substring( pattern.indexOf(" in ") + " in ".length(), pattern.length() );
						
						StringTokenizer patternTokenizer = new StringTokenizer( _pattern, "," );
						
						boolean isNonMatched = true;
						
						while( patternTokenizer.hasMoreTokens() )
						{
							String token = patternTokenizer.nextToken().trim();
							
							if( token.equals(value.trim()) )
							{
								isNonMatched = false;
								break;
							}
						}
						
						if( isNonMatched )
						{
							isMatched = false;
						}
						
					}
				}
			}
			else if(pattern.equals("N/A") )
			{
			}
			else
			{
				//Auto Pay
				if( !pattern.trim().equals(value.trim()) )
				{
					isMatched = false;
				}
			}
		}
		else
		{
			//All Payment No
		}
		
		return isMatched;
	}
	
	
	
	
	
	
	
	
	static LinkedList<JV01> readPaymentsFromCsv( String paymentFileLocation, SessionFactory sessionFactory, JSONObject lists, String option, JSONObject maps ) throws Throwable
	{
		Session dbSession = null;
		
		LinkedList<JV01> payments = new LinkedList<JV01>();
		
		FileReader fileReader = null;
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			int PAYMENT_IDX_hr_payment_no = 1;
			int PAYMENT_IDX_hr_payment_name = 2;
			int PAYMENT_IDX_hr_employee_code = 3;
			int PAYMENT_IDX_hr_payment_date = 4;
			int PAYMENT_IDX_hr_accounting_date = 5;
			int PAYMENT_IDX_hr_business_entity = 6;
			int PAYMENT_IDX_hr_department = 7;
			int PAYMENT_IDX_hr_employee_name = 8;
			int PAYMENT_IDX_hr_position = 9;
			int PAYMENT_IDX_hr_payroll_category = 10;
			int PAYMENT_IDX_hr_job_nature = 11;
			int PAYMENT_IDX_hr_amount = 12;
			int PAYMENT_IDX_hr_payment_method = 13;
			
			
			fileReader = new FileReader(paymentFileLocation);
			
		    CSVParser parser = CSVParser.parse(fileReader, CSVFormat.DEFAULT);
		    
		    List<CSVRecord> csvRecords = parser.getRecords();
		    
		    int columnNum = 0;
		    int rowNum = 0;
		    
		    int paymentIdx = -1;
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    
	        for( CSVRecord csvRecord : csvRecords )
	        {
	        	rowNum++;
	        	
	        	if( rowNum == 1 ) continue;
	        	
	        	JV01 payment = new JV01();
	        	paymentIdx++;
	        	
	            for (int j = 0; j < csvRecord.size(); j++)
	            {
	            	String value = csvRecord.get(j);
	            	
	            	columnNum = j + 1;
									
					if( columnNum == PAYMENT_IDX_hr_payment_no  ) payment.setP_hr_payment_no( value );
					if( columnNum == PAYMENT_IDX_hr_payment_name  ) payment.setP_hr_payment_name( value );
					
					if( columnNum == PAYMENT_IDX_hr_payment_date  ) 
					{
						if( value.trim().length() == 0 ) value = "01/01/2047";
						payment.setP_hr_payment_date( new Timestamp( sdf.parse(value).getTime() ) );
					}
											
					if( columnNum == PAYMENT_IDX_hr_accounting_date  ) 
					{
						if( value.trim().length() == 0 ) value = "01/01/2047";
						payment.setP_hr_accounting_date( new Timestamp( sdf.parse(value).getTime() ) );
					}
											
					if( columnNum == PAYMENT_IDX_hr_business_entity  ) 
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						payment.setP_hr_business_entity( value );
					}
											
					if( columnNum == PAYMENT_IDX_hr_department  ) 
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						payment.setP_hr_department( value );
					}
											
					if( columnNum == PAYMENT_IDX_hr_employee_name  ) 
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						payment.setP_hr_employee_name( value );
					}						
					
					if( columnNum == PAYMENT_IDX_hr_position  )
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						payment.setP_hr_position( value );
					}
											
					if( columnNum == PAYMENT_IDX_hr_payroll_category  )
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						payment.setP_hr_payroll_category( value );
					}
											
					if( columnNum == PAYMENT_IDX_hr_amount  ) 
					{
						if( value.trim().length() == 0 ) value = "0";
						payment.setP_hr_amount( Double.parseDouble( CommonTools.replaceAll(value, ",", "") ) );
					}
											
					if( columnNum == PAYMENT_IDX_hr_payment_method  ) 
					{
						if( value.trim().length() == 0 ) value = "NA_" + paymentIdx;
						
						if( value.equals("By Cheque") ) value = "Cheque";
						
						if( value.equals("cheque") ) value = "Cheque";
						
						
						payment.setP_hr_payment_method( value );
					}
	            }
	            
	            JSONObject iList = lists.getJSONObject("Ignore_List");
	            
	            JSONObject pMap = maps.getJSONObject( "Position_Transfer" );
	            
	            if( payment.getP_hr_amount() > 0 )
	            {
	            	for( String pMapKey : pMap.keySet() )
	            	{
	            		StringTokenizer fpMapTokenizer = new StringTokenizer( pMapKey, "@" );
	            		String fpDept = fpMapTokenizer.nextToken();
	            		String fpPos = fpMapTokenizer.nextToken();
	            		
	            		StringTokenizer tpMapTokenizer = new StringTokenizer( pMap.getString(pMapKey) , "@" );
	            		String tpDept = tpMapTokenizer.nextToken();
	            		String tpPos = tpMapTokenizer.nextToken();
	            		
	            		if( payment.getP_hr_department().equals(fpDept) && payment.getP_hr_position().equals( fpPos ) )
	            		{
	            			payment.setP_hr_department( tpDept );
	            			payment.setP_hr_position( tpPos );
	            		}
	            	}
	            	
	            	if( option.equals("TOA_MPF_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Employer Mandatory Contribution") || payment.getP_hr_payroll_category().equals("Employee Mandatory Contribution") ) && 
		            			payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_MPF_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Employer Mandatory Contribution") || payment.getP_hr_payroll_category().equals("Employee Mandatory Contribution") ) && 
		            			payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Normal_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Normal Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Normal_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Normal Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Normal_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Normal Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Normal_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Normal Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Final_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Back Payment") || payment.getP_hr_payroll_category().equals("Final Payment") ) && 
		            			payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Final_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Back Payment") || payment.getP_hr_payroll_category().equals("Final Payment") ) && 
		            			payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Final_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Back Payment") || payment.getP_hr_payroll_category().equals("Final Payment") ) && 
		            			payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Final_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && 
		            			( payment.getP_hr_payroll_category().equals("Back Payment") || payment.getP_hr_payroll_category().equals("Final Payment") ) && 
		            			payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Do_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("DO Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Do_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("DO Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Do_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("DO Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Do_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("DO Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Bonus_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Bonus") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Bonus_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Bonus") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Bonus_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Bonus") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Bonus_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Bonus") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_WorkInjury_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Work Injury") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_WorkInjury_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Work Injury") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Medical_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Medical Claim") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Medical_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Medical Claim") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_Medical_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Medical Claim") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_Medical_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("Medical Claim") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_AL_AutoPay") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("AL Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_AL_AutoPay") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("AL Payment") && payment.getP_hr_payment_method().equals("Auto Pay") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOA_AL_Cheque") )
	            	{
		            	if( !payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("AL Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	else if( option.equals("TOAR_AL_Cheque") )
	            	{
		            	if( payment.getP_hr_business_entity().equals("TOAR") && payment.getP_hr_payroll_category().equals("AL Payment") && payment.getP_hr_payment_method().equals("Cheque") )
		            	{
			            	payment.setP_idx(paymentIdx);
							payment.setV_intercovoucherTranVirdeptId( CommonTools.RAXB310103899 );
							dbSession.save(payment);
							payments.add(payment);	
		            	}
	            	}
	            	
	            	
	            }
	        }
	        
			dbSession.close();
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
			try { fileReader.close(); } catch ( Throwable thw ) {}
		}
		        
        return payments;
	}
	
	
	
	static LinkedList<JV01> readRules( String ruleFileLocation, SessionFactory sessionFactory ) throws Throwable
	{
		Session dbSession = null;
		
		LinkedList<JV01> rules = new LinkedList<JV01>();
		
		try
		{
			dbSession = sessionFactory.openSession();
			
			int IDX_rule_id = 1;
			int IDX_rule_sequence = 2;
			int IDX_rule_link = 3;
			int IDX_rule_grouping = 4;
			int IDX_rule_voucher_prefix = 5;
			int IDX_hr_payment_no = 6;
			int IDX_hr_payment_name = 7;
			int IDX_hr_payment_date = 8;
			int IDX_hr_accounting_date = 9;
			int IDX_hr_business_entity = 10;
			int IDX_hr_department = 11;
			int IDX_hr_employee_name = 12;
			int IDX_hr_position = 13;
			int IDX_hr_payroll_category = 14;
			int IDX_hr_amount = 15;
			int IDX_hr_payment_method = 16;
			int IDX_maint_code = 17;
			int IDX_tDate = 18;
			int IDX_beId = 19;
			int IDX_staffId = 20;
			int IDX_virDeptId = 21;
			int IDX_AI1 = 22;
			int IDX_AI2 = 23;
			int IDX_AI3 = 24;
			int IDX_AI4 = 25;
			int IDX_cnDeptId = 26;
			int IDX_accId = 27;
			int IDX_accDesc = 28;
			int IDX_curId = 29;
			int IDX_domAmt = 30;
			int IDX_c_d = 31;
			int IDX_jlTypeId = 32;
			int IDX_payee = 33;
			int IDX_chequeDate = 34;
			int IDX_chequeNo = 35;
			int IDX_slmId = 36;
			int IDX_bankSlipNo = 37;
			int IDX_particular = 38;
			int IDX_expired = 39;
			int IDX_intercovoucherTranVirdeptId = 40;
			
			FileInputStream file = new FileInputStream(new File(ruleFileLocation));

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			XSSFSheet sheet = workbook.getSheet("Details");

			Iterator<Row> rowIterator = sheet.iterator();
	        		
			int rowNum = 0;
			int columnNum = 0;
			
			int ruleIdx = -1;
	       
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
	           
				rowNum++;

				if( rowNum == 1 ) continue; 
				if( rowNum == 2 ) continue; 
				
				JV01 rule = new JV01();
				ruleIdx++;
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) 
				{
					Cell cell = cellIterator.next();
					
					columnNum++;
	           
					if( cell.getCellTypeEnum() == CellType.valueOf("STRING") )
					{
						String value = cell.getStringCellValue();
										
						if( columnNum == IDX_rule_id  ) rule.setR_rule_id( Integer.parseInt(value) );
						if( columnNum == IDX_rule_sequence  ) rule.setR_rule_sequence( Integer.parseInt(value) );
						if( columnNum == IDX_rule_link  ) rule.setR_rule_link(value);
						if( columnNum == IDX_rule_grouping  ) rule.setR_rule_grouping(value);
						if( columnNum == IDX_rule_voucher_prefix  ) rule.setR_rule_voucher_prefix(value);
						if( columnNum == IDX_hr_payment_no  ) rule.setR_hr_payment_no(value);
						if( columnNum == IDX_hr_payment_name  ) rule.setR_hr_payment_name(value);
						if( columnNum == IDX_hr_payment_date  ) rule.setR_hr_payment_date(value);
						if( columnNum == IDX_hr_accounting_date  ) rule.setR_hr_accounting_date(value);
						if( columnNum == IDX_hr_business_entity  ) rule.setR_hr_business_entity(value);
						if( columnNum == IDX_hr_department  ) rule.setR_hr_department(value);
						if( columnNum == IDX_hr_employee_name  ) rule.setR_hr_employee_name(value);
						if( columnNum == IDX_hr_position  ) rule.setR_hr_position(value);
						if( columnNum == IDX_hr_payroll_category  ) rule.setR_hr_payroll_category(value);
						if( columnNum == IDX_hr_amount  ) rule.setR_hr_amount(value);
						if( columnNum == IDX_hr_payment_method  ) rule.setR_hr_payment_method(value);
						if( columnNum == IDX_maint_code  ) rule.setR_maint_code(value);
						if( columnNum == IDX_tDate  ) rule.setR_tDate(value);
						if( columnNum == IDX_beId  ) rule.setR_beId(value);
						if( columnNum == IDX_staffId  ) rule.setR_staffId(value);
						if( columnNum == IDX_virDeptId  ) rule.setR_virDeptId(value);
						if( columnNum == IDX_AI1  ) rule.setR_AI1(value);
						if( columnNum == IDX_AI2  ) rule.setR_AI2(value);
						if( columnNum == IDX_AI3  ) rule.setR_AI3(value);
						if( columnNum == IDX_AI4  ) rule.setR_AI4(value);
						if( columnNum == IDX_cnDeptId  ) rule.setR_cnDeptId(value);
						if( columnNum == IDX_accId  ) rule.setR_accId(value);
						if( columnNum == IDX_accDesc  ) rule.setR_accDesc(value);
						if( columnNum == IDX_curId  ) rule.setR_curId(value);
						if( columnNum == IDX_domAmt  ) rule.setR_domAmt(value);
						if( columnNum == IDX_c_d  ) rule.setR_c_d(value);
						if( columnNum == IDX_jlTypeId  ) rule.setR_jlTypeId(value);
						if( columnNum == IDX_payee  ) rule.setR_payee(value);
						if( columnNum == IDX_chequeDate  ) rule.setR_chequeDate(value);
						if( columnNum == IDX_chequeNo  ) rule.setR_chequeNo(value);
						if( columnNum == IDX_slmId  ) rule.setR_slmId(value);
						if( columnNum == IDX_bankSlipNo  ) rule.setR_bankSlipNo(value);
						if( columnNum == IDX_particular  ) rule.setR_particular(value);
						if( columnNum == IDX_expired  ) rule.setR_expired(value);
						if( columnNum == IDX_intercovoucherTranVirdeptId  ) rule.setV_intercovoucherTranVirdeptId(value);
					}
					else if( cell.getCellTypeEnum() == CellType.NUMERIC )
					{
						String value = new Double(cell.getNumericCellValue()).toString();
						
						DataFormatter dataFormatter = new DataFormatter();
	               	
						try
						{
							value = dataFormatter.formatCellValue( cell );
						}
						catch(Throwable thw)
						{
						}
						
						if( columnNum == IDX_rule_id  ) rule.setR_rule_id( Integer.parseInt(value) );
						if( columnNum == IDX_rule_sequence  ) rule.setR_rule_sequence( Integer.parseInt(value) );
						if( columnNum == IDX_rule_link  ) rule.setR_rule_link(value);
						if( columnNum == IDX_rule_grouping  ) rule.setR_rule_grouping(value);
						if( columnNum == IDX_rule_voucher_prefix  ) rule.setR_rule_voucher_prefix(value);
						if( columnNum == IDX_hr_payment_no  ) rule.setR_hr_payment_no(value);
						if( columnNum == IDX_hr_payment_name  ) rule.setR_hr_payment_name(value);
						if( columnNum == IDX_hr_payment_date  ) rule.setR_hr_payment_date(value);
						if( columnNum == IDX_hr_accounting_date  ) rule.setR_hr_accounting_date(value);
						if( columnNum == IDX_hr_business_entity  ) rule.setR_hr_business_entity(value);
						if( columnNum == IDX_hr_department  ) rule.setR_hr_department(value);
						if( columnNum == IDX_hr_employee_name  ) rule.setR_hr_employee_name(value);
						if( columnNum == IDX_hr_position  ) rule.setR_hr_position(value);
						if( columnNum == IDX_hr_payroll_category  ) rule.setR_hr_payroll_category(value);
						if( columnNum == IDX_hr_amount  ) rule.setR_hr_amount(value);
						if( columnNum == IDX_hr_payment_method  ) rule.setR_hr_payment_method(value);
						if( columnNum == IDX_maint_code  ) rule.setR_maint_code(value);
						if( columnNum == IDX_tDate  ) rule.setR_tDate(value);
						if( columnNum == IDX_beId  ) rule.setR_beId(value);
						if( columnNum == IDX_staffId  ) rule.setR_staffId(value);
						if( columnNum == IDX_virDeptId  ) rule.setR_virDeptId(value);
						if( columnNum == IDX_AI1  ) rule.setR_AI1(value);
						if( columnNum == IDX_AI2  ) rule.setR_AI2(value);
						if( columnNum == IDX_AI3  ) rule.setR_AI3(value);
						if( columnNum == IDX_AI4  ) rule.setR_AI4(value);
						if( columnNum == IDX_cnDeptId  ) rule.setR_cnDeptId(value);
						if( columnNum == IDX_accId  ) rule.setR_accId(value);
						if( columnNum == IDX_accDesc  ) rule.setR_accDesc(value);
						if( columnNum == IDX_curId  ) rule.setR_curId(value);
						if( columnNum == IDX_domAmt  ) rule.setR_domAmt(value);
						if( columnNum == IDX_c_d  ) rule.setR_c_d(value);
						if( columnNum == IDX_jlTypeId  ) rule.setR_jlTypeId(value);
						if( columnNum == IDX_payee  ) rule.setR_payee(value);
						if( columnNum == IDX_chequeDate  ) rule.setR_chequeDate(value);
						if( columnNum == IDX_chequeNo  ) rule.setR_chequeNo(value);
						if( columnNum == IDX_slmId  ) rule.setR_slmId(value);
						if( columnNum == IDX_bankSlipNo  ) rule.setR_bankSlipNo(value);
						if( columnNum == IDX_particular  ) rule.setR_particular(value);
						if( columnNum == IDX_expired  ) rule.setR_expired(value);
						if( columnNum == IDX_intercovoucherTranVirdeptId  ) rule.setV_intercovoucherTranVirdeptId(value);
					}
					else
					{
						generation.writer.write("readRules unknow cell type " + cell.getCellTypeEnum() + " at " + rowNum + ":" + columnNum); generation.writer.newLine();
					}
				}
	           
				columnNum = 0;
				
				rule.setR_idx(ruleIdx);
				rule.setV_intercovoucherTranVirdeptId(CommonTools.RAXB310102519);
				dbSession.save(rule);
				rules.add(rule);
			}
			
			dbSession.close();
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { dbSession.close(); } catch ( Throwable thw ) {}
		}
		
		return rules;
	}
	
	

	
	
	
	
	static JSONObject readLists( String listFileLocation ) throws Throwable
	{
		JSONObject lists = new JSONObject();
		
		FileInputStream file = new FileInputStream(new File(listFileLocation));

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		for (int workSheetIdx = 0; workSheetIdx < workbook.getNumberOfSheets(); workSheetIdx++)
		{
			XSSFSheet sheet = workbook.getSheetAt(workSheetIdx);
		    
		    String workSheetName = sheet.getSheetName();
		   
		    JSONObject list = new JSONObject();
		    
		    Iterator<Row> rowIterator = sheet.iterator();
    		
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
	           
				list.put( row.getCell(0).getStringCellValue(), "");
			}
			
			lists.put(workSheetName, list);
		}
		
		return lists;
	}
	
	
	static JSONObject readMaps( String listFileLocation ) throws Throwable
	{
		JSONObject maps = new JSONObject();
		
		FileInputStream file = new FileInputStream(new File(listFileLocation));

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		for (int workSheetIdx = 0; workSheetIdx < workbook.getNumberOfSheets(); workSheetIdx++)
		{
			XSSFSheet sheet = workbook.getSheetAt(workSheetIdx);
		    
		    String workSheetName = sheet.getSheetName();
		   
		    JSONObject map = new JSONObject();
		    
		    Iterator<Row> rowIterator = sheet.iterator();
    		
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
	           
				String key = null;
				String value = null;
				
				if( row.getCell(0).getCellTypeEnum() == CellType.STRING )
				{
					key = row.getCell(0).getStringCellValue();
				}
				else
				{
					key = new Double(row.getCell(0).getNumericCellValue()) + "";
					key = key.substring( 0, key.indexOf(".") );
				}
				
				if( row.getCell(1).getCellTypeEnum() == CellType.STRING )
				{
					value = row.getCell(1).getStringCellValue();
				}
				else
				{
					value = new Double(row.getCell(1).getNumericCellValue()) + "";
					value = value.substring( 0, value.indexOf(".") );
				}
				
				map.put( key, value);
			}
			
			maps.put(workSheetName, map);
		}
		
		return maps;
	}
	
	
	
	
	
	
	
	public static void createVoucher( JSONObject voucherData, String fileLocation ) throws Throwable
	{
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		
		createJournalVoucher( workbook, voucherData.getJSONArray("voucherMasterList") );
		createJournalVoucherAccount( workbook, voucherData.getJSONArray("voucherDetailList") );
		createInterCompanyEntry( workbook, new JSONArray() );
		
        FileOutputStream out = new FileOutputStream(new File( fileLocation ));
        workbook.write(out);
        out.close();
	}

	
	
	public static void createJournalVoucher( XSSFWorkbook workbook, JSONArray dataList ) throws Throwable
	{
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		int pstmtIdx1 = 0;
						
		try
		{
			Connection conn = CommonTools.getConnection();
						
	        XSSFSheet sheet = workbook.createSheet("Journal Voucher");
	        
	        int rownum = 0;
	        int cellnum = 0;
	        
	        Row row = sheet.createRow(rownum++); 
	        Cell cell = row.createCell(cellnum++); cell.setCellValue( "code" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "tDate" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "beId" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "createUid" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "cnDeptId" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "bankSlipNo" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "slmId" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "virDeptId" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "expired" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "staffId" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "remt.remarks" );
	        
	        cellnum = 0;
	        
	        row = sheet.createRow(rownum++); 
	        cell = row.createCell(cellnum++); cell.setCellValue( "Voucher No." );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Date" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Business Entity" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Created By" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Department" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Bank Slip No." );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Payment Method" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Business Unit" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Void" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "Staff" );
	        cell = row.createCell(cellnum++); cell.setCellValue( "System Remarks" );
	        
	        cellnum = 0;
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmssSSS");
	        
	        String remarkTag = sdf.format( new Date() );
	        
	        String sql = " Update payment1 Set payment1_rule_intercovoucherTranVirdeptId = ? Where payment1_rule_maint_code = ? ";
	        
	        for( int idx = 0; idx < dataList.length(); idx++ )
	        {
	        	JSONObject voucherMaster = dataList.getJSONObject(idx);
	        		        	
	            cellnum = 0;
	            
	            String remark = "Remark : " + remarkTag + " : " + voucherMaster.getString("code");
	            
	            row = sheet.createRow(rownum++); 
	            if( !voucherMaster.getString("code").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("code") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("tDate").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("tDate") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("beId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("beId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("createUid").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("createUid") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("cnDeptId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("cnDeptId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("bankSlipNo").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("bankSlipNo") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("slmId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("slmId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("virDeptId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("virDeptId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("expired").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("expired") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            if( !voucherMaster.getString("staffId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherMaster.getString("staffId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
	            cell = row.createCell(cellnum++); cell.setCellValue( remark );
	            
	            
	        }	
	        
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
			try { rs1.close(); } catch( Throwable thw) {}
			try { pstmt1.close(); } catch( Throwable thw) {}
		}
	}
	
	
	
	public static void createJournalVoucherAccount(XSSFWorkbook workbook, JSONArray dataList ) throws Throwable
	{
		BufferedReader reader = null;

		HashMap<Integer, String> requiredLines = new HashMap<Integer, String>();
		
		try {
			
			String settingFolder = System.getProperty("SettingFolder");
			
			reader = new BufferedReader(new FileReader(settingFolder + "\\Settings\\Filter.txt"));
			
			String dataLine = "";
			
			while ((dataLine = reader.readLine()) != null)
			{
				requiredLines.put( Integer.parseInt(dataLine.trim())  , "");
		    }   
			 
			reader.close();

		}
		finally
		{
			try { reader.close(); } catch( Throwable thw ) {}
		}
		
		
        XSSFSheet sheet = workbook.createSheet("Journal Voucher (Account)");
     
        int rownum = 0;
        int cellnum = 0;
        
        Row row = sheet.createRow(rownum++); 
        Cell cell = row.createCell(cellnum++); cell.setCellValue( "maint.code" );
        cell = row.createCell(cellnum++); cell.setCellValue( "tDate" );
        cell = row.createCell(cellnum++); cell.setCellValue( "staffId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "virDeptId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "AI1" );
        cell = row.createCell(cellnum++); cell.setCellValue( "AI2" );
        cell = row.createCell(cellnum++); cell.setCellValue( "AI3" );
        cell = row.createCell(cellnum++); cell.setCellValue( "AI4" );
        cell = row.createCell(cellnum++); cell.setCellValue( "cnDeptId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "accId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "accDesc" );
        cell = row.createCell(cellnum++); cell.setCellValue( "curId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "domAmt" );
        cell = row.createCell(cellnum++); cell.setCellValue( "c_d" );
        cell = row.createCell(cellnum++); cell.setCellValue( "jlTypeId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "payee" );
        cell = row.createCell(cellnum++); cell.setCellValue( "chequeDate" );
        cell = row.createCell(cellnum++); cell.setCellValue( "chequeNo" );
        cell = row.createCell(cellnum++); cell.setCellValue( "slmId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "bankSlipNo" );
        cell = row.createCell(cellnum++); cell.setCellValue( "particular" );
        cell = row.createCell(cellnum++); cell.setCellValue( "intercovoucherTranVirdeptId" );

        
        cellnum = 0;
        
        row = sheet.createRow(rownum++);  
        cell = row.createCell(cellnum++); cell.setCellValue( "Journal Voucher_Voucher No." );
        cell = row.createCell(cellnum++); cell.setCellValue( "Date" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Staff" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Business Unit" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Brand" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Company" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Shop" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Engineering" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Department" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Account Code" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Brief Description" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Currency" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Amount (Entity Currency)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Credit / Debit" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Document Type" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Payee" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Cheque Date" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Cheque No." );
        cell = row.createCell(cellnum++); cell.setCellValue( "Payment Method" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Bank Slip No." );
        cell = row.createCell(cellnum++); cell.setCellValue( "Transaction Description" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Transaction BU" );

        
        for( int idx = 0; idx < dataList.length(); idx++ )
        {
        	JSONObject voucherDetail = dataList.getJSONObject(idx);
        
            cellnum = 0;
        	
            row = sheet.createRow(rownum++); 
            if( !voucherDetail.getString("maint.code").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("maint.code") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("tDate").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("tDate") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("staffId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("staffId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("virDeptId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("virDeptId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("AI1").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("AI1") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("AI2").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("AI2") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("AI3").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("AI3") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("AI4").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("AI4") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("cnDeptId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("cnDeptId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("accId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("accId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("accDesc").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("accDesc") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("curId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("curId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("domAmt").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( Double.parseDouble(voucherDetail.getString("domAmt")) ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("c_d").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("c_d") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("jlTypeId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("jlTypeId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("payee").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("payee") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("chequeDate").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("chequeDate") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("chequeNo").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("chequeNo") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("slmId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("slmId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("bankSlipNo").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("bankSlipNo") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("particular").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("particular") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            if( !voucherDetail.getString("intercovoucherTranVirdeptId").equals("N/A") ) { cell = row.createCell(cellnum++); cell.setCellValue( voucherDetail.getString("intercovoucherTranVirdeptId") ); } else { cell = row.createCell(cellnum++); cell.setCellValue( "" ); }
            
        }
        
	}
	
	
	
	public static void createInterCompanyEntry(XSSFWorkbook workbook, JSONArray dataList ) throws Throwable
	{
        XSSFSheet sheet = workbook.createSheet(" (Intercompany Entry)");
     
        int rownum = 0;
        int cellnum = 0;
        
        Row row = sheet.createRow(rownum++); 
        Cell cell = row.createCell(cellnum++); cell.setCellValue( "maint.code" );
        cell = row.createCell(cellnum++); cell.setCellValue( "virdeptId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "accId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "amt" );
        cell = row.createCell(cellnum++); cell.setCellValue( "curId" );
        cell = row.createCell(cellnum++); cell.setCellValue( "rate" );
        cell = row.createCell(cellnum++); cell.setCellValue( "bdesc" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy1Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy2Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy3Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy4Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy5Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy6Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy7Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy8Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy9Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy10Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy11Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy12Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy13Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy14Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy15Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy16Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy17Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy18Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy19Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy20Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy21Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy22Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy23Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy24Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy25Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy26Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy27Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy28Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy29Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "analy30Id" );
        cell = row.createCell(cellnum++); cell.setCellValue( "drAmt" );
        cell = row.createCell(cellnum++); cell.setCellValue( "crAmt" );
        
        cellnum = 0;
        
        row = sheet.createRow(rownum++); 
        cell = row.createCell(cellnum++); cell.setCellValue( "Journal Voucher_Voucher No." );
        cell = row.createCell(cellnum++); cell.setCellValue( "Business Unit" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Account" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Amount" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Currency" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Current Ex. Rate" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Brief Description" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Brand (Analysis Code 1)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Company (Analysis Code 2)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Shop (Analysis Code 3)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Engineering (Analysis Code 4)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 5" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 6" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 7" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 8" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 9" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 10" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 11" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 12" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 13" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 14" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 15" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 16" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 17" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 18" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 19" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 20" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 21" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 22" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 23" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 24" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 25" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Company Related (Analysis Code 26)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Division (Analysis Code 27)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Employee Name (Analysis Code 28)" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 29" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Analysis Code 30" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Debit" );
        cell = row.createCell(cellnum++); cell.setCellValue( "Credit" );


        
        cellnum = 0;
        
	}

	
	
	static void generatePaymentFile( String mpfPaymentFileLocaiton, LinkedList<Employee> payments ) throws Throwable
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		BufferedWriter pWriter = new BufferedWriter(new FileWriter( mpfPaymentFileLocaiton ));
		 
		pWriter.write( "Payment No.,Payment Name,MPF1,Payment Date,Accounting Date,Historical Business Entity Code,Historical Department Description,Employee Name(繁體中文),Position Description,Payroll Category,MPF2,Net Payment Amount,Employee Payment Method Filter");
		pWriter.newLine();
			
		 for( Employee payment : payments )
		 {			
			pWriter.write( payment.payment_no + "," );
			pWriter.write( payment.payment_name + "," );
			pWriter.write( "00318223," );
			pWriter.write( sdf.format( payment.payment_date ) + "," );
			pWriter.write( sdf.format( payment.accounting_date ) + "," );
			pWriter.write( payment.business_entity + "," );
			pWriter.write( payment.department + "," );
			pWriter.write( payment.employee_name + "," );
			pWriter.write( payment.position + "," );
			pWriter.write( payment.payroll_category + "," );
			pWriter.write( payment.business_entity + "," );
			pWriter.write( payment.amount + "," );
			pWriter.write( payment.payment_method);
			pWriter.newLine();
		 
		 }
		 
		 pWriter.close();
	}
	
	
	
	static LinkedList<Employee> crossCheck( LinkedList<Employee> employees, LinkedList<Mpf> mpfs ) throws Throwable
	{
		LinkedList<Employee> payments = new LinkedList<Employee>();
		
		for( Mpf mpf: mpfs )
		{
			boolean isRecordFound = false;
			
			for( Employee employee: employees )
			{
				if( employee.employee_code.equals( mpf.empId ) )
				{
					Employee paymentERMC = employee.cloneMe();
					paymentERMC.payroll_category = "Employer Mandatory Contribution";
					paymentERMC.amount = mpf.ERMC;
					paymentERMC.payment_method = "Auto Pay";
					payments.add(paymentERMC);
					
					Employee paymentEEMC = employee.cloneMe();
					paymentEEMC.payroll_category = "Employee Mandatory Contribution";
					paymentEEMC.amount = mpf.EEMC;
					paymentEEMC.payment_method = "Auto Pay";
					payments.add(paymentEEMC);
					
					isRecordFound = true;
					break;
				}
			}
			
			if( !isRecordFound )
			{
				writer.write( "No found : " + mpf.idx + " " + mpf.id + " " + mpf.empId);
				writer.newLine();
			}
		}
		
		return payments;
	}
	
	
	
	static LinkedList<Mpf> readMpf( String mpfFileLocationy ) throws Throwable
	{
		LinkedList<Mpf> mpfs = new LinkedList<Mpf>();
		
		try
		{
			int IDX_id = 1;
			int IDX_empId = 4;
			int IDX_ERMC = 42;
			int IDX_EEMC = 43;
			
		    CSVParser parser = CSVParser.parse(new FileReader(mpfFileLocationy), CSVFormat.DEFAULT);
		    
		    List<CSVRecord> csvRecords = parser.getRecords();
		    
		    int columnNum = 0;
		    int rowNum = 0;
		    
		    int mpfIdx = 0;
		    
	        for( CSVRecord csvRecord : csvRecords )
	        {
	        	rowNum++;
	        	
	        	if( rowNum <= 2 ) continue;
	        	
	        	Mpf mpf = new Mpf();
	        	
	        	mpfIdx++;
	        	
	        	mpf.idx = mpfIdx;
	        	
	            for (int j = 0; j < csvRecord.size(); j++)
	            {
	            	String value = csvRecord.get(j);
	            	
	            	columnNum = j + 1;
									
					if( columnNum == IDX_id  ) mpf.id = value;
					if( columnNum == IDX_empId  ) mpf.empId = value;
					if( columnNum == IDX_ERMC  ) mpf.ERMC = Double.parseDouble( CommonTools.replaceAll(value, ",", "") );
					if( columnNum == IDX_EEMC  ) mpf.EEMC = Double.parseDouble( CommonTools.replaceAll(value, ",", "") );

	            }
	            
	            mpfs.add(mpf);	
	        }
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
		}
		        
        return mpfs;
	}
	
	
	
	static LinkedList<Employee> readEmployee( String employeeFileLocation) throws Throwable
	{
		LinkedList<Employee> employees = new LinkedList<Employee>();
		
		try
		{
			int PAYMENT_IDX_hr_payment_no = 1;
			int PAYMENT_IDX_hr_payment_name = 2;
			int PAYMENT_IDX_hr_employee_code = 3;
			int PAYMENT_IDX_hr_payment_date = 4;
			int PAYMENT_IDX_hr_accounting_date = 5;
			int PAYMENT_IDX_hr_business_entity = 6;
			int PAYMENT_IDX_hr_department = 7;
			int PAYMENT_IDX_hr_employee_name = 8;
			int PAYMENT_IDX_hr_position = 9;
			int PAYMENT_IDX_hr_payroll_category = 10;
			int PAYMENT_IDX_hr_job_nature = 11;
			int PAYMENT_IDX_hr_amount = 12;
			int PAYMENT_IDX_hr_payment_method = 13;
			
		    CSVParser parser = CSVParser.parse(new FileReader(employeeFileLocation), CSVFormat.DEFAULT);
		    
		    List<CSVRecord> csvRecords = parser.getRecords();
		    
		    int columnNum = 0;
		    int rowNum = 0;
		    
		    int employeeIdx = -1;
		    
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    
	        for( CSVRecord csvRecord : csvRecords )
	        {
	        	rowNum++;
	        	
	        	if( rowNum == 1 ) continue;
	        	
	        	Employee employee = new Employee();
	        	
	        	employeeIdx++;
	        	
	        	employee.idx = employeeIdx;
	        	
	            for (int j = 0; j < csvRecord.size(); j++)
	            {
	            	String value = csvRecord.get(j);
	            	
	            	columnNum = j + 1;
									
					if( columnNum == PAYMENT_IDX_hr_payment_no  )
					{
						value = "AC";
						
						employee.payment_no = value;
					}
						
						
					if( columnNum == PAYMENT_IDX_hr_payment_name  ) 
					{
						value = "AC";
						
						employee.payment_name = value;
					}
					
					
					
					
					if( columnNum == PAYMENT_IDX_hr_employee_code  )
					{
						employee.employee_code = value;
					}
					
					
					
					
					if( columnNum == PAYMENT_IDX_hr_payment_date  )
					{
						value = paymentDateString;
						
						employee.payment_date = new Timestamp( sdf.parse(value).getTime() );
					}
					
					
					
					
					if( columnNum == PAYMENT_IDX_hr_accounting_date  )					
					{
						value = accountingDateString;
												
						employee.accounting_date = new Timestamp( sdf.parse(value).getTime() );
					}
					
					
					
					
					if( columnNum == PAYMENT_IDX_hr_business_entity  )
					{
						employee.business_entity = value;
					}
					
					
					
					
					if( columnNum == PAYMENT_IDX_hr_department  ) 
					{
						employee.department = value;
					}
					
						
					
					
					if( columnNum == PAYMENT_IDX_hr_employee_name  )
					{
						employee.employee_name = value;
					}
						
					
					
					if( columnNum == PAYMENT_IDX_hr_position  )
					{
						employee.position = value;
					}
						
					
					
					if( columnNum == PAYMENT_IDX_hr_payroll_category  )
					{
						employee.payroll_category = "Auto Pay";
					}
						
					
					
					if( columnNum == PAYMENT_IDX_hr_job_nature  )
					{
						employee.job_nature = value;
					}
						
					
					
					if( columnNum == PAYMENT_IDX_hr_amount  ) 
					{
						
						employee.amount = 0d;
					}
						
					
					
					if( columnNum == PAYMENT_IDX_hr_payment_method  )
					{
						employee.payment_method = value;
					}
						

	            }
	            
	            employees.add(employee);	
	        }
		}
		catch( Throwable thw) 
		{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					thw.printStackTrace(pw);
					String sStackTrace = sw.toString(); 
					
					writer.write( sStackTrace ); writer.newLine(); 
					writer.flush();
		}
		finally
		{
		}
		        
        return employees;
	}
}

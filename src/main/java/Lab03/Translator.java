package Lab03;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

import org.json.JSONObject;

import com.google.gson.Gson;

public class Translator
{
	public static Timestamp get_tDate( JV01 payment, String rulePattern ) throws Exception
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
		
		if( rulePattern.equals("Account Date") )
		{
			return payment.getP_hr_accounting_date();
		}
		else if( rulePattern.equals("Payment Date") )
		{
			return payment.getP_hr_payment_date();
		}
		else
		{
			throw new Exception( "Error at get_tDate" );
		}
	}
	
	
	public static String get_beId( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("Business Entity") )
		{
			return payment.getP_hr_business_entity();
		}
		else
		{
			return rulePattern;
		}
		
	}
	
	
	public static String get_staffId( JV01 payment, String rulePattern ) throws Exception
	{
		return rulePattern;
	}
	
	
	public static String get_virDeptId( JV01 payment, String rulePattern, JSONObject maps ) throws Exception
	{
		if( rulePattern.startsWith("Pattern 1"))
        {
        	String deptChar6To7_9to10 = payment.getP_hr_department().substring(5, 7) + payment.getP_hr_department().substring(8, 10);
		
        	return deptChar6To7_9to10;
        }
		else if( rulePattern.startsWith("Pattern 2"))
        {
			String value = maps.getJSONObject("BE to BU").getString( payment.getP_hr_business_entity() );
		
        	return value;
        }
		else if( rulePattern.equals("Business Entity"))
        {
			return payment.getP_hr_business_entity();
        }
		else
		{
			throw new Exception( "No handling logic for get_virDeptId " + rulePattern );
		}
	}
			

	public static String get_AI1( JV01 payment, String rulePattern, JSONObject maps ) throws Exception
	{
		if( rulePattern.startsWith("Pattern 1"))
        {
			String deptChar6To7 = payment.getP_hr_department().substring(5, 7);
    		
        	return deptChar6To7;
        }
        else if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_AI1" );
		}
	}
	
	
	public static String get_AI2( JV01 payment, String rulePattern, JSONObject maps ) throws Exception
	{
		if( rulePattern.equals("Business Entity"))
        {
        	String value = payment.getP_hr_business_entity();
		
        	return value;
        }
		else if( rulePattern.startsWith("Pattern 1"))
        {
        	String deptChar6To10 = payment.getP_hr_department().substring(5, 10);
		
        	String value = maps.getJSONObject("Shop To Company").getString( deptChar6To10 );
        	
        	return value;
        }
        else if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_AI2" );
		}
	}
	
	
	public static String get_AI3( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.startsWith("Pattern 1"))
        {
        	String value = payment.getP_hr_department().substring(5, 10);
		
        	return value;
        }
        else if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_AI3" );
		}
	}
	
	
	public static String get_AI4( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_AI4" );
		}
	}
	
	
	public static String get_cnDeptId( JV01 payment, String rulePattern ) throws Exception
	{
		return rulePattern;
	}
	
	
	public static String get_accId( JV01 payment, String rulePattern, JSONObject maps ) throws Exception
	{
        if( rulePattern.startsWith("Pattern 1"))
        {
        	String value = maps.getJSONObject("Position to AC 1").getString( payment.getP_hr_position() );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 2"))
        {
        	String value = maps.getJSONObject("Position to AC 2").getString( payment.getP_hr_position() );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 3"))
        {
        	String value = maps.getJSONObject("Position to AC 3").getString( payment.getP_hr_position() );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 4"))
        {
        	String value = maps.getJSONObject("Position to AC 4").getString( payment.getP_hr_position() );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 5"))
        {
        	String value = maps.getJSONObject("BE to AC 1").getString( payment.getP_hr_business_entity() );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 6"))
        {
        	String deptChar6To10 = payment.getP_hr_department().substring(5, 10);
    		
        	String value = maps.getJSONObject("Department to AC 1").getString(deptChar6To10);
    	
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 7"))
        {
        	String value = maps.getJSONObject("Position to AC 5").getString( payment.getP_hr_position() );
    		
        	return value;
        }
		else
		{
			return rulePattern;
		}
	}
	
	
	public static String get_accDesc( JV01 payment, String rulePattern, JSONObject maps ) throws Exception
	{		
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/yy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
        
        if( rulePattern.startsWith("Pattern 1"))
        {
        	String deptChar6To10 = payment.getP_hr_department().substring(5, 10);
		
        	String value = deptChar6To10 + "-" + payment.getP_hr_payment_method() + "-" + payment.getP_hr_payroll_category() + "-" + sdf1.format( new Date( payment.getP_hr_accounting_date().getTime() ) );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 2"))
        {
        	String deptChar6To10 = payment.getP_hr_department().substring(5, 10);
		
        	String value = deptChar6To10 + "-" + payment.getP_hr_payment_method() + "-" + payment.getP_hr_payroll_category() + "-" + payment.getP_hr_employee_name() + "-" + sdf1.format( new Date( payment.getP_hr_accounting_date().getTime() ) );
		
        	return value;
        }
        else if( rulePattern.startsWith("Pattern 3"))
        {
        	String deptChar6To10 = payment.getP_hr_department().substring(5, 10);
		
        	String value = deptChar6To10 + "-" + payment.getP_hr_payment_method() + "-MPF-" + sdf1.format( new Date( payment.getP_hr_accounting_date().getTime() ) );
		
        	return value;
        }
		else
		{
			throw new Exception( "Error at get_accDesc" );
		}
	}
	
	
	public static String get_curId( JV01 payment, String rulePattern ) throws Exception
	{
		return rulePattern;
	}
	
	
	public static Double get_domAmt( JV01 payment, String rulePattern ) throws Exception
	{
		return payment.getP_hr_amount();
	}
	
	
	public static String get_c_d( JV01 payment, String rulePattern ) throws Exception
	{
		return rulePattern;
	}
	
	
	public static String get_jlTypeId( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_jlTypeId" );
		}
	}
	
	
	public static String get_payee( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_payee" );
		}
	}
	
	
	public static Timestamp get_chequeDate( JV01 payment, String rulePattern ) throws Exception
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
		
		if( rulePattern.equals("Account Date") )
		{
			return payment.getP_hr_accounting_date();
		}
		else if( rulePattern.equals("Payment Date") )
		{
			return payment.getP_hr_payment_date();
		}
		else if( rulePattern.equals("N/A") )
		{
			return null;
		}
		else
		{
			throw new Exception( "Error at get_chequeDate" );
		}
	}
	
	
	public static String get_chequeNo( JV01 payment, String rulePattern ) throws Exception
	{
        if( rulePattern.startsWith("Pattern 1"))
        {
        	return payment.getP_hr_payment_method();
        }
        else if( rulePattern.startsWith("Pattern 2"))
        {
        	String value = payment.getP_hr_payment_method();
		
        	return value;
        }
        else if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_chequeNo" );
		}
	}
	
	
	public static String get_slmId( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_slmId" );
		}
	}
	
	
	public static String get_bankSlipNo( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_bankSlipNo" );
		}
	}
	
	
	public static String get_particular( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_particular" );
		}
	}
	
	
	public static String get_expired( JV01 payment, String rulePattern ) throws Exception
	{
		return rulePattern;
	}
	
	
	public static String get_intercovoucherTranVirdeptId( JV01 payment, String rulePattern ) throws Exception
	{
		if( rulePattern.equals("N/A"))
        {
        	return null;
        }
		else
		{
			throw new Exception( "Error at get_intercovoucherTranVirdeptId" );
		}
	}
	
}

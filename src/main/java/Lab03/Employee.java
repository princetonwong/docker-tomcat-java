package Lab03;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Employee 
{
	public int idx = -1;
	public String payment_no;
	public String payment_name;
	public String employee_code;    
	public Timestamp payment_date;
	public Timestamp accounting_date;
	public String business_entity;
	public String department;
	public String employee_name;
	public String position;
	public String payroll_category;
	public String job_nature;
	public Double amount;
	public String payment_method;
	
	public Employee cloneMe()
	{
		Employee employee = new Employee();
		
		employee.payment_no = payment_no;
		employee.payment_name = payment_name;
		employee.employee_code = employee_code;
		employee.payment_date = payment_date;
		employee.accounting_date = accounting_date;
		employee.business_entity = business_entity;
		employee.department = department;
		employee.employee_name = employee_name;
		employee.position = position;
		employee.payroll_category = payroll_category;
		employee.job_nature = job_nature;
		employee.amount = amount;
		employee.payment_method = payment_method;
		
		return employee;
	}

}

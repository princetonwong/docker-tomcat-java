package Lab03;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "jv01")
public class JV01 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "r_idx")
    private Integer r_idx;
    
    @Column(name = "p_idx")
    private Integer p_idx;
    
    @Column(name = "r_rule_id")
    private Integer r_rule_id;
    
    @Column(name = "r_rule_sequence")
    private Integer r_rule_sequence;
    
    @Column(name = "r_rule_grouping")
    private String r_rule_grouping;

    @Column(name = "r_rule_link")
    private String r_rule_link;
    
    @Column(name = "r_rule_voucher_prefix")
    private String r_rule_voucher_prefix;

    @Column(name = "r_hr_payment_no")
    private String r_hr_payment_no;

    @Column(name = "r_hr_payment_name")
    private String r_hr_payment_name;

    @Column(name = "r_hr_payment_date")
    private String r_hr_payment_date;

    @Column(name = "r_hr_accounting_date")
    private String r_hr_accounting_date;

    @Column(name = "r_hr_business_entity")
    private String r_hr_business_entity;

    @Column(name = "r_hr_department")
    private String r_hr_department;

    @Column(name = "r_hr_employee_name")
    private String r_hr_employee_name;

    @Column(name = "r_hr_position")
    private String r_hr_position;

    @Column(name = "r_hr_payroll_category")
    private String r_hr_payroll_category;

    @Column(name = "r_hr_amount")
    private String r_hr_amount;

    @Column(name = "r_hr_payment_method")
    private String r_hr_payment_method;
	
    @Column(name = "r_maint_code")
    private String r_maint_code;
    
    @Column(name = "r_tDate")
    private String r_tDate;

    @Column(name = "r_beId")
    private String r_beId;

    @Column(name = "r_staffId")
    private String r_staffId;

    @Column(name = "r_virDeptId")
    private String r_virDeptId;

    @Column(name = "r_AI1")
    private String r_AI1;

    @Column(name = "r_AI2")
    private String r_AI2;

    @Column(name = "r_AI3")
    private String r_AI3;

    @Column(name = "r_AI4")
    private String r_AI4;

    @Column(name = "r_cnDeptId")
    private String r_cnDeptId;	

    @Column(name = "r_accId")
    private String r_accId;

    @Column(name = "r_accDesc")
    private String r_accDesc;

    @Column(name = "r_curId")
    private String r_curId;

    @Column(name = "r_domAmt")
    private String r_domAmt;
    
    @Column(name = "r_c_d")
    private String r_c_d;

    @Column(name = "r_jlTypeId")
    private String r_jlTypeId;

    @Column(name = "r_payee")
    private String r_payee;

    @Column(name = "r_chequeDate")
    private String r_chequeDate;

    @Column(name = "r_chequeNo")
    private String r_chequeNo;

    @Column(name = "r_slmId")
    private String r_slmId;

    @Column(name = "r_bankSlipNo")
    private String r_bankSlipNo;

    @Column(name = "r_particular")
    private String r_particular;

    @Column(name = "r_expired")
    private String r_expired;

    @Column(name = "p_hr_payment_no")
    private String p_hr_payment_no;

    @Column(name = "p_hr_payment_name")
    private String p_hr_payment_name;

    @Column(name = "p_hr_payment_date")
    private Timestamp p_hr_payment_date;

    @Column(name = "p_hr_accounting_date")
    private Timestamp p_hr_accounting_date;

    @Column(name = "p_hr_business_entity")
    private String p_hr_business_entity;

    @Column(name = "p_hr_department")
    private String p_hr_department;

    @Column(name = "p_hr_employee_name")
    private String p_hr_employee_name;

    @Column(name = "p_hr_position")
    private String p_hr_position;

    @Column(name = "p_hr_payroll_category")
    private String p_hr_payroll_category;

    @Column(name = "p_hr_amount")
    private Double p_hr_amount;

    @Column(name = "p_hr_payment_method")
    private String p_hr_payment_method;

    @Column(name = "v_maint_code")
    private String v_maint_code;

	@Column(name = "v_maint_code_prefix")
    private String v_maint_code_prefix;
    
    @Column(name = "v_maint_code_suffix")
    private String v_maint_code_suffix;
    
    @Column(name = "v_tDate")
    private Timestamp v_tDate;

    @Column(name = "v_beId")
    private String v_beId;

    @Column(name = "v_staffId")
    private String v_staffId;

    @Column(name = "v_virDeptId")
    private String v_virDeptId;

    @Column(name = "v_AI1")
    private String v_AI1;

    @Column(name = "v_AI2")
    private String v_AI2;

    @Column(name = "v_AI3")
    private String v_AI3;

    @Column(name = "v_AI4")
    private String v_AI4;

    @Column(name = "v_cnDeptId")
    private String v_cnDeptId;

    @Column(name = "v_accId")
    private String v_accId;

    @Column(name = "v_accDesc")
    private String v_accDesc;

    @Column(name = "v_curId")
    private String v_curId;

    @Column(name = "v_domAmt")
    private Double v_domAmt;

    @Column(name = "v_c_d")
    private String v_c_d;

    @Column(name = "v_jlTypeId")
    private String v_jlTypeId;

    @Column(name = "v_payee")
    private String v_payee;

    @Column(name = "v_chequeDate")
    private Timestamp v_chequeDate;	
	
	@Column(name = "v_chequeNo")
    private String v_chequeNo;	
	
    @Column(name = "v_slmId")
    private String v_slmId;	
	
    @Column(name = "v_bankSlipNo")
    private String v_bankSlipNo;	

    @Column(name = "v_particular")
    private String v_particular;	

    @Column(name = "v_expired")
    private String v_expired;	

    @Column(name = "v_intercovoucherTranVirdeptId")
    private String v_intercovoucherTranVirdeptId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getR_idx() {
		return r_idx;
	}

	public void setR_idx(Integer r_idx) {
		this.r_idx = r_idx;
	}

	public Integer getP_idx() {
		return p_idx;
	}

	public void setP_idx(Integer p_idx) {
		this.p_idx = p_idx;
	}

	public Integer getR_rule_id() {
		return r_rule_id;
	}

	public void setR_rule_id(Integer r_rule_id) {
		this.r_rule_id = r_rule_id;
	}

	public Integer getR_rule_sequence() {
		return r_rule_sequence;
	}

	public void setR_rule_sequence(Integer r_rule_sequence) {
		this.r_rule_sequence = r_rule_sequence;
	}

	public String getR_rule_link() {
		return r_rule_link;
	}

	public void setR_rule_link(String r_rule_link) {
		this.r_rule_link = r_rule_link;
	}
	
	public String getR_rule_grouping() {
		return r_rule_grouping;
	}

	public void setR_rule_grouping(String r_rule_grouping) {
		this.r_rule_grouping = r_rule_grouping;
	}

	public String getR_rule_voucher_prefix() {
		return r_rule_voucher_prefix;
	}

	public void setR_rule_voucher_prefix(String r_rule_voucher_prefix) {
		this.r_rule_voucher_prefix = r_rule_voucher_prefix;
	}

	public String getR_hr_payment_no() {
		return r_hr_payment_no;
	}

	public void setR_hr_payment_no(String r_hr_payment_no) {
		this.r_hr_payment_no = r_hr_payment_no;
	}

	public String getR_hr_payment_name() {
		return r_hr_payment_name;
	}

	public void setR_hr_payment_name(String r_hr_payment_name) {
		this.r_hr_payment_name = r_hr_payment_name;
	}

	public String getR_hr_payment_date() {
		return r_hr_payment_date;
	}

	public void setR_hr_payment_date(String r_hr_payment_date) {
		this.r_hr_payment_date = r_hr_payment_date;
	}

	public String getR_hr_accounting_date() {
		return r_hr_accounting_date;
	}

	public void setR_hr_accounting_date(String r_hr_accounting_date) {
		this.r_hr_accounting_date = r_hr_accounting_date;
	}

	public String getR_hr_business_entity() {
		return r_hr_business_entity;
	}

	public void setR_hr_business_entity(String r_hr_business_entity) {
		this.r_hr_business_entity = r_hr_business_entity;
	}

	public String getR_hr_department() {
		return r_hr_department;
	}

	public void setR_hr_department(String r_hr_department) {
		this.r_hr_department = r_hr_department;
	}

	public String getR_hr_employee_name() {
		return r_hr_employee_name;
	}

	public void setR_hr_employee_name(String r_hr_employee_name) {
		this.r_hr_employee_name = r_hr_employee_name;
	}

	public String getR_hr_position() {
		return r_hr_position;
	}

	public void setR_hr_position(String r_hr_position) {
		this.r_hr_position = r_hr_position;
	}

	public String getR_hr_payroll_category() {
		return r_hr_payroll_category;
	}

	public void setR_hr_payroll_category(String r_hr_payroll_category) {
		this.r_hr_payroll_category = r_hr_payroll_category;
	}

	public String getR_hr_amount() {
		return r_hr_amount;
	}

	public void setR_hr_amount(String r_hr_amount) {
		this.r_hr_amount = r_hr_amount;
	}

	public String getR_hr_payment_method() {
		return r_hr_payment_method;
	}

	public void setR_hr_payment_method(String r_hr_payment_method) {
		this.r_hr_payment_method = r_hr_payment_method;
	}

	public String getR_maint_code() {
		return r_maint_code;
	}

	public void setR_maint_code(String r_maint_code) {
		this.r_maint_code = r_maint_code;
	}

	public String getR_tDate() {
		return r_tDate;
	}

	public void setR_tDate(String r_tDate) {
		this.r_tDate = r_tDate;
	}

	public String getR_beId() {
		return r_beId;
	}

	public void setR_beId(String r_beId) {
		this.r_beId = r_beId;
	}

	public String getR_staffId() {
		return r_staffId;
	}

	public void setR_staffId(String r_staffId) {
		this.r_staffId = r_staffId;
	}

	public String getR_virDeptId() {
		return r_virDeptId;
	}

	public void setR_virDeptId(String r_virDeptId) {
		this.r_virDeptId = r_virDeptId;
	}

	public String getR_AI1() {
		return r_AI1;
	}

	public void setR_AI1(String r_AI1) {
		this.r_AI1 = r_AI1;
	}

	public String getR_AI2() {
		return r_AI2;
	}

	public void setR_AI2(String r_AI2) {
		this.r_AI2 = r_AI2;
	}

	public String getR_AI3() {
		return r_AI3;
	}

	public void setR_AI3(String r_AI3) {
		this.r_AI3 = r_AI3;
	}

	public String getR_AI4() {
		return r_AI4;
	}

	public void setR_AI4(String r_AI4) {
		this.r_AI4 = r_AI4;
	}

	public String getR_cnDeptId() {
		return r_cnDeptId;
	}

	public void setR_cnDeptId(String r_cnDeptId) {
		this.r_cnDeptId = r_cnDeptId;
	}

	public String getR_accId() {
		return r_accId;
	}

	public void setR_accId(String r_accId) {
		this.r_accId = r_accId;
	}

	public String getR_accDesc() {
		return r_accDesc;
	}

	public void setR_accDesc(String r_accDesc) {
		this.r_accDesc = r_accDesc;
	}

	public String getR_curId() {
		return r_curId;
	}

	public void setR_curId(String r_curId) {
		this.r_curId = r_curId;
	}

	public String getR_domAmt() {
		return r_domAmt;
	}

	public void setR_domAmt(String r_domAmt) {
		this.r_domAmt = r_domAmt;
	}

	public String getR_c_d() {
		return r_c_d;
	}

	public void setR_c_d(String r_c_d) {
		this.r_c_d = r_c_d;
	}

	public String getR_jlTypeId() {
		return r_jlTypeId;
	}

	public void setR_jlTypeId(String r_jlTypeId) {
		this.r_jlTypeId = r_jlTypeId;
	}

	public String getR_payee() {
		return r_payee;
	}

	public void setR_payee(String r_payee) {
		this.r_payee = r_payee;
	}

	public String getR_chequeDate() {
		return r_chequeDate;
	}

	public void setR_chequeDate(String r_chequeDate) {
		this.r_chequeDate = r_chequeDate;
	}

	public String getR_chequeNo() {
		return r_chequeNo;
	}

	public void setR_chequeNo(String r_chequeNo) {
		this.r_chequeNo = r_chequeNo;
	}

	public String getR_slmId() {
		return r_slmId;
	}

	public void setR_slmId(String r_slmId) {
		this.r_slmId = r_slmId;
	}

	public String getR_bankSlipNo() {
		return r_bankSlipNo;
	}

	public void setR_bankSlipNo(String r_bankSlipNo) {
		this.r_bankSlipNo = r_bankSlipNo;
	}

	public String getR_particular() {
		return r_particular;
	}

	public void setR_particular(String r_particular) {
		this.r_particular = r_particular;
	}

	public String getR_expired() {
		return r_expired;
	}

	public void setR_expired(String r_expired) {
		this.r_expired = r_expired;
	}

	public String getP_hr_payment_no() {
		return p_hr_payment_no;
	}

	public void setP_hr_payment_no(String p_hr_payment_no) {
		this.p_hr_payment_no = p_hr_payment_no;
	}

	public String getP_hr_payment_name() {
		return p_hr_payment_name;
	}

	public void setP_hr_payment_name(String p_hr_payment_name) {
		this.p_hr_payment_name = p_hr_payment_name;
	}

	public Timestamp getP_hr_payment_date() {
		return p_hr_payment_date;
	}

	public void setP_hr_payment_date(Timestamp p_hr_payment_date) {
		this.p_hr_payment_date = p_hr_payment_date;
	}

	public Timestamp getP_hr_accounting_date() {
		return p_hr_accounting_date;
	}

	public void setP_hr_accounting_date(Timestamp p_hr_accounting_date) {
		this.p_hr_accounting_date = p_hr_accounting_date;
	}

	public String getP_hr_business_entity() {
		return p_hr_business_entity;
	}

	public void setP_hr_business_entity(String p_hr_business_entity) {
		this.p_hr_business_entity = p_hr_business_entity;
	}

	public String getP_hr_department() {
		return p_hr_department;
	}

	public void setP_hr_department(String p_hr_department) {
		this.p_hr_department = p_hr_department;
	}

	public String getP_hr_employee_name() {
		return p_hr_employee_name;
	}

	public void setP_hr_employee_name(String p_hr_employee_name) {
		this.p_hr_employee_name = p_hr_employee_name;
	}

	public String getP_hr_position() {
		return p_hr_position;
	}

	public void setP_hr_position(String p_hr_position) {
		this.p_hr_position = p_hr_position;
	}

	public String getP_hr_payroll_category() {
		return p_hr_payroll_category;
	}

	public void setP_hr_payroll_category(String p_hr_payroll_category) {
		this.p_hr_payroll_category = p_hr_payroll_category;
	}

	public Double getP_hr_amount() {
		return p_hr_amount;
	}

	public void setP_hr_amount(Double p_hr_amount) {
		this.p_hr_amount = p_hr_amount;
	}

	public String getP_hr_payment_method() {
		return p_hr_payment_method;
	}

	public void setP_hr_payment_method(String p_hr_payment_method) {
		this.p_hr_payment_method = p_hr_payment_method;
	}

	public String getV_maint_code() {
		return v_maint_code;
	}

	public void setV_maint_code(String v_maint_code) {
		this.v_maint_code = v_maint_code;
	}

    public String getV_maint_code_prefix() {
		return v_maint_code_prefix;
	}

	public void setV_maint_code_prefix(String v_maint_code_prefix) {
		this.v_maint_code_prefix = v_maint_code_prefix;
	}

	public String getV_maint_code_suffix() {
		return v_maint_code_suffix;
	}

	public void setV_maint_code_suffix(String v_maint_code_suffix) {
		this.v_maint_code_suffix = v_maint_code_suffix;
	}
	
	public Timestamp getV_tDate() {
		return v_tDate;
	}

	public void setV_tDate(Timestamp v_tDate) {
		this.v_tDate = v_tDate;
	}

	public String getV_beId() {
		return v_beId;
	}

	public void setV_beId(String v_beId) {
		this.v_beId = v_beId;
	}

	public String getV_staffId() {
		return v_staffId;
	}

	public void setV_staffId(String v_staffId) {
		this.v_staffId = v_staffId;
	}

	public String getV_virDeptId() {
		return v_virDeptId;
	}

	public void setV_virDeptId(String v_virDeptId) {
		this.v_virDeptId = v_virDeptId;
	}

	public String getV_AI1() {
		return v_AI1;
	}

	public void setV_AI1(String v_AI1) {
		this.v_AI1 = v_AI1;
	}

	public String getV_AI2() {
		return v_AI2;
	}

	public void setV_AI2(String v_AI2) {
		this.v_AI2 = v_AI2;
	}

	public String getV_AI3() {
		return v_AI3;
	}

	public void setV_AI3(String v_AI3) {
		this.v_AI3 = v_AI3;
	}

	public String getV_AI4() {
		return v_AI4;
	}

	public void setV_AI4(String v_AI4) {
		this.v_AI4 = v_AI4;
	}

	public String getV_cnDeptId() {
		return v_cnDeptId;
	}

	public void setV_cnDeptId(String v_cnDeptId) {
		this.v_cnDeptId = v_cnDeptId;
	}

	public String getV_accId() {
		return v_accId;
	}

	public void setV_accId(String v_accId) {
		this.v_accId = v_accId;
	}

	public String getV_accDesc() {
		return v_accDesc;
	}

	public void setV_accDesc(String v_accDesc) {
		this.v_accDesc = v_accDesc;
	}

	public String getV_curId() {
		return v_curId;
	}

	public void setV_curId(String v_curId) {
		this.v_curId = v_curId;
	}

	public Double getV_domAmt() {
		return v_domAmt;
	}

	public void setV_domAmt(Double v_domAmt) {
		this.v_domAmt = v_domAmt;
	}

	public String getV_c_d() {
		return v_c_d;
	}

	public void setV_c_d(String v_c_d) {
		this.v_c_d = v_c_d;
	}

	public String getV_jlTypeId() {
		return v_jlTypeId;
	}

	public void setV_jlTypeId(String v_jlTypeId) {
		this.v_jlTypeId = v_jlTypeId;
	}

	public String getV_payee() {
		return v_payee;
	}

	public void setV_payee(String v_payee) {
		this.v_payee = v_payee;
	}

	public Timestamp getV_chequeDate() {
		return v_chequeDate;
	}

	public void setV_chequeDate(Timestamp v_chequeDate) {
		this.v_chequeDate = v_chequeDate;
	}

	public String getV_chequeNo() {
		return v_chequeNo;
	}

	public void setV_chequeNo(String v_chequeNo) {
		this.v_chequeNo = v_chequeNo;
	}

	public String getV_slmId() {
		return v_slmId;
	}

	public void setV_slmId(String v_slmId) {
		this.v_slmId = v_slmId;
	}

	public String getV_bankSlipNo() {
		return v_bankSlipNo;
	}

	public void setV_bankSlipNo(String v_bankSlipNo) {
		this.v_bankSlipNo = v_bankSlipNo;
	}

	public String getV_particular() {
		return v_particular;
	}

	public void setV_particular(String v_particular) {
		this.v_particular = v_particular;
	}

	public String getV_expired() {
		return v_expired;
	}

	public void setV_expired(String v_expired) {
		this.v_expired = v_expired;
	}

	public String getV_intercovoucherTranVirdeptId() {
		return v_intercovoucherTranVirdeptId;
	}

	public void setV_intercovoucherTranVirdeptId(String v_intercovoucherTranVirdeptId) {
		this.v_intercovoucherTranVirdeptId = v_intercovoucherTranVirdeptId;
	}		

	
	public JV01 cloneDataOnly() 
	{
		JV01 jv01 = new JV01();
		
		jv01.setR_idx(r_idx);
		jv01.setP_idx(p_idx);
		jv01.setR_rule_id(r_rule_id);
		jv01.setR_rule_sequence(r_rule_sequence);
		jv01.setR_rule_link(r_rule_link);
		jv01.setR_rule_grouping(r_rule_grouping);
		jv01.setR_rule_voucher_prefix(r_rule_voucher_prefix);
		jv01.setR_hr_payment_no(r_hr_payment_no);
		jv01.setR_hr_payment_name(r_hr_payment_name);
		jv01.setR_hr_payment_date(r_hr_payment_date);
		jv01.setR_hr_accounting_date(r_hr_accounting_date);
		jv01.setR_hr_business_entity(r_hr_business_entity);
		jv01.setR_hr_department(r_hr_department);
		jv01.setR_hr_employee_name(r_hr_employee_name);
		jv01.setR_hr_position(r_hr_position);
		jv01.setR_hr_payroll_category(r_hr_payroll_category);
		jv01.setR_hr_amount(r_hr_amount);
		jv01.setR_hr_payment_method(r_hr_payment_method);
		jv01.setR_maint_code(r_maint_code);
		jv01.setR_tDate(r_tDate);
		jv01.setR_beId(r_beId);
		jv01.setR_staffId(r_staffId);
		jv01.setR_virDeptId(r_virDeptId);
		jv01.setR_AI1(r_AI1);
		jv01.setR_AI2(r_AI2);
		jv01.setR_AI3(r_AI3);
		jv01.setR_AI4(r_AI4);
		jv01.setR_cnDeptId(r_cnDeptId);
		jv01.setR_accId(r_accId);
		jv01.setR_accDesc(r_accDesc);
		jv01.setR_curId(r_curId);
		jv01.setR_domAmt(r_domAmt);
		jv01.setR_c_d(r_c_d);
		jv01.setR_jlTypeId(r_jlTypeId);
		jv01.setR_payee(r_payee);
		jv01.setR_chequeDate(r_chequeDate);
		jv01.setR_chequeNo(r_chequeNo);
		jv01.setR_slmId(r_slmId);
		jv01.setR_bankSlipNo(r_bankSlipNo);
		jv01.setR_particular(r_particular);
		jv01.setR_expired(r_expired);
		jv01.setP_hr_payment_no(p_hr_payment_no);
		jv01.setP_hr_payment_name(p_hr_payment_name);
		jv01.setP_hr_payment_date(p_hr_payment_date);
		jv01.setP_hr_accounting_date(p_hr_accounting_date);
		jv01.setP_hr_business_entity(p_hr_business_entity);
		jv01.setP_hr_department(p_hr_department);
		jv01.setP_hr_employee_name(p_hr_employee_name);
		jv01.setP_hr_position(p_hr_position);
		jv01.setP_hr_payroll_category(p_hr_payroll_category);
		jv01.setP_hr_amount(p_hr_amount);
		jv01.setP_hr_payment_method(p_hr_payment_method);
		jv01.setV_maint_code(v_maint_code);
		jv01.setV_maint_code_prefix(v_maint_code_prefix);
		jv01.setV_maint_code_suffix(v_maint_code_suffix);
		jv01.setV_tDate(v_tDate);
		jv01.setV_beId(v_beId);
		jv01.setV_staffId(v_staffId);
		jv01.setV_virDeptId(v_virDeptId);
		jv01.setV_AI1(v_AI1);
		jv01.setV_AI2(v_AI2);
		jv01.setV_AI3(v_AI3);
		jv01.setV_AI4(v_AI4);
		jv01.setV_cnDeptId(v_cnDeptId);
		jv01.setV_accId(v_accId);
		jv01.setV_accDesc(v_accDesc);
		jv01.setV_curId(v_curId);
		jv01.setV_domAmt(v_domAmt);
		jv01.setV_c_d(v_c_d);
		jv01.setV_jlTypeId(v_jlTypeId);
		jv01.setV_payee(v_payee);
		jv01.setV_chequeDate(v_chequeDate);
		jv01.setV_chequeNo(v_chequeNo);
		jv01.setV_slmId(v_slmId);
		jv01.setV_bankSlipNo(v_bankSlipNo);
		jv01.setV_particular(v_particular);
		jv01.setV_expired(v_expired);
		
		return jv01;
	}
	
	public JV01 cloneMe() 
	{
		JV01 jv01 = new JV01();
		
		jv01.setId(id);
		jv01.setR_idx(r_idx);
		jv01.setP_idx(p_idx);
		jv01.setR_rule_id(r_rule_id);
		jv01.setR_rule_sequence(r_rule_sequence);
		jv01.setR_rule_link(r_rule_link);
		jv01.setR_rule_grouping(r_rule_grouping);
		jv01.setR_rule_voucher_prefix(r_rule_voucher_prefix);
		jv01.setR_hr_payment_no(r_hr_payment_no);
		jv01.setR_hr_payment_name(r_hr_payment_name);
		jv01.setR_hr_payment_date(r_hr_payment_date);
		jv01.setR_hr_accounting_date(r_hr_accounting_date);
		jv01.setR_hr_business_entity(r_hr_business_entity);
		jv01.setR_hr_department(r_hr_department);
		jv01.setR_hr_employee_name(r_hr_employee_name);
		jv01.setR_hr_position(r_hr_position);
		jv01.setR_hr_payroll_category(r_hr_payroll_category);
		jv01.setR_hr_amount(r_hr_amount);
		jv01.setR_hr_payment_method(r_hr_payment_method);
		jv01.setR_maint_code(r_maint_code);
		jv01.setR_tDate(r_tDate);
		jv01.setR_beId(r_beId);
		jv01.setR_staffId(r_staffId);
		jv01.setR_virDeptId(r_virDeptId);
		jv01.setR_AI1(r_AI1);
		jv01.setR_AI2(r_AI2);
		jv01.setR_AI3(r_AI3);
		jv01.setR_AI4(r_AI4);
		jv01.setR_cnDeptId(r_cnDeptId);
		jv01.setR_accId(r_accId);
		jv01.setR_accDesc(r_accDesc);
		jv01.setR_curId(r_curId);
		jv01.setR_domAmt(r_domAmt);
		jv01.setR_c_d(r_c_d);
		jv01.setR_jlTypeId(r_jlTypeId);
		jv01.setR_payee(r_payee);
		jv01.setR_chequeDate(r_chequeDate);
		jv01.setR_chequeNo(r_chequeNo);
		jv01.setR_slmId(r_slmId);
		jv01.setR_bankSlipNo(r_bankSlipNo);
		jv01.setR_particular(r_particular);
		jv01.setR_expired(r_expired);
		jv01.setP_hr_payment_no(p_hr_payment_no);
		jv01.setP_hr_payment_name(p_hr_payment_name);
		jv01.setP_hr_payment_date(p_hr_payment_date);
		jv01.setP_hr_accounting_date(p_hr_accounting_date);
		jv01.setP_hr_business_entity(p_hr_business_entity);
		jv01.setP_hr_department(p_hr_department);
		jv01.setP_hr_employee_name(p_hr_employee_name);
		jv01.setP_hr_position(p_hr_position);
		jv01.setP_hr_payroll_category(p_hr_payroll_category);
		jv01.setP_hr_amount(p_hr_amount);
		jv01.setP_hr_payment_method(p_hr_payment_method);
		jv01.setV_maint_code(v_maint_code);
		jv01.setV_tDate(v_tDate);
		jv01.setV_beId(v_beId);
		jv01.setV_staffId(v_staffId);
		jv01.setV_virDeptId(v_virDeptId);
		jv01.setV_AI1(v_AI1);
		jv01.setV_AI2(v_AI2);
		jv01.setV_AI3(v_AI3);
		jv01.setV_AI4(v_AI4);
		jv01.setV_cnDeptId(v_cnDeptId);
		jv01.setV_accId(v_accId);
		jv01.setV_accDesc(v_accDesc);
		jv01.setV_curId(v_curId);
		jv01.setV_domAmt(v_domAmt);
		jv01.setV_c_d(v_c_d);
		jv01.setV_jlTypeId(v_jlTypeId);
		jv01.setV_payee(v_payee);
		jv01.setV_chequeDate(v_chequeDate);
		jv01.setV_chequeNo(v_chequeNo);
		jv01.setV_slmId(v_slmId);
		jv01.setV_bankSlipNo(v_bankSlipNo);
		jv01.setV_particular(v_particular);
		jv01.setV_expired(v_expired);
		
		return jv01;
	}
	
	
	public static void assignHrPaymentData(JV01 target, JV01 reference ) 
	{
		target.setP_idx( reference.getP_idx());
		target.setP_hr_payment_no( reference.getP_hr_payment_no());
		target.setP_hr_payment_name( reference.getP_hr_payment_name());
		target.setP_hr_payment_date( reference.getP_hr_payment_date());
		target.setP_hr_accounting_date( reference.getP_hr_accounting_date());
		target.setP_hr_business_entity( reference.getP_hr_business_entity());
		target.setP_hr_department( reference.getP_hr_department());
		target.setP_hr_employee_name( reference.getP_hr_employee_name());
		target.setP_hr_position( reference.getP_hr_position());
		target.setP_hr_payroll_category( reference.getP_hr_payroll_category());
		target.setP_hr_amount( reference.getP_hr_amount());
		target.setP_hr_payment_method( reference.getP_hr_payment_method());
	}
	
	public static void assignRuleData(JV01 target, JV01 reference ) 
	{
		target.setR_idx( reference.getR_idx());
		target.setR_rule_id( reference.getR_rule_id());
		target.setR_rule_sequence( reference.getR_rule_sequence());
		target.setR_rule_link( reference.getR_rule_link());
		target.setR_rule_grouping( reference.getR_rule_grouping());
		target.setR_rule_voucher_prefix( reference.getR_rule_voucher_prefix());
		target.setR_hr_payment_no( reference.getR_hr_payment_no());
		target.setR_hr_payment_name( reference.getR_hr_payment_name());
		target.setR_hr_payment_date( reference.getR_hr_payment_date());
		target.setR_hr_accounting_date( reference.getR_hr_accounting_date());
		target.setR_hr_business_entity( reference.getR_hr_business_entity());
		target.setR_hr_department( reference.getR_hr_department());
		target.setR_hr_employee_name( reference.getR_hr_employee_name());
		target.setR_hr_position( reference.getR_hr_position());
		target.setR_hr_payroll_category( reference.getR_hr_payroll_category());
		target.setR_hr_amount( reference.getR_hr_amount());
		target.setR_hr_payment_method( reference.getR_hr_payment_method());
		target.setR_maint_code( reference.getR_maint_code());
		target.setR_tDate( reference.getR_tDate());
		target.setR_beId( reference.getR_beId());
		target.setR_staffId( reference.getR_staffId());
		target.setR_virDeptId( reference.getR_virDeptId());
		target.setR_AI1( reference.getR_AI1());
		target.setR_AI2( reference.getR_AI2());
		target.setR_AI3( reference.getR_AI3());
		target.setR_AI4( reference.getR_AI4());
		target.setR_cnDeptId( reference.getR_cnDeptId());
		target.setR_accId( reference.getR_accId());
		target.setR_accDesc( reference.getR_accDesc());
		target.setR_curId( reference.getR_curId());
		target.setR_domAmt( reference.getR_domAmt());
		target.setR_c_d( reference.getR_c_d());
		target.setR_jlTypeId( reference.getR_jlTypeId());
		target.setR_payee( reference.getR_payee());
		target.setR_chequeDate( reference.getR_chequeDate());
		target.setR_chequeNo( reference.getR_chequeNo());
		target.setR_slmId( reference.getR_slmId());
		target.setR_bankSlipNo( reference.getR_bankSlipNo());
		target.setR_particular( reference.getR_particular());
		target.setR_expired( reference.getR_expired());
	}
	
	public static void assignVocuherData(JV01 target, JV01 reference ) 
	{
		target.setV_maint_code( reference.getV_maint_code());
		target.setV_tDate( reference.getV_tDate());
		target.setV_beId( reference.getV_beId());
		target.setV_staffId( reference.getV_staffId());
		target.setV_virDeptId( reference.getV_virDeptId());
		target.setV_AI1( reference.getV_AI1());
		target.setV_AI2( reference.getV_AI2());
		target.setV_AI3( reference.getV_AI3());
		target.setV_AI4( reference.getV_AI4());
		target.setV_cnDeptId( reference.getV_cnDeptId());
		target.setV_accId( reference.getV_accId());
		target.setV_accDesc( reference.getV_accDesc());
		target.setV_curId( reference.getV_curId());
		target.setV_domAmt( reference.getV_domAmt());
		target.setV_c_d( reference.getV_c_d());
		target.setV_jlTypeId( reference.getV_jlTypeId());
		target.setV_payee( reference.getV_payee());
		target.setV_chequeDate( reference.getV_chequeDate());
		target.setV_chequeNo( reference.getV_chequeNo());
		target.setV_slmId( reference.getV_slmId());
		target.setV_bankSlipNo( reference.getV_bankSlipNo());
		target.setV_particular( reference.getV_particular());
		target.setV_expired( reference.getV_expired());
	}
}

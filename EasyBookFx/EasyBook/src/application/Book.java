package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class Book {
	private int b_id;
	private SimpleStringProperty  code;
	private SimpleStringProperty check_in;
	private SimpleStringProperty check_out;
	private SimpleStringProperty  name;
	private SimpleStringProperty  sname;
	private SimpleStringProperty  tel;
	private SimpleStringProperty  email;
	private SimpleStringProperty  idnum;
	private SimpleStringProperty  payment_method;
	private float total_cost;
	private SimpleStringProperty  paid;
	private float money_received;
	private SimpleStringProperty  status;
	

	public Book( 
			int b_id, 
			String  code, 
			String  check_in, 
			String  check_out, 
			String  name, 
			String  sname, 
			String  tel, 
			String  email, 
			String  idnum, 
			String  payment_method, 
			float total_cost, 
			String  paid, 
			float money_received, 
			String  status) throws ParseException {
		this.b_id = b_id;
		this.code = new SimpleStringProperty(code);
		this.check_in = new SimpleStringProperty(check_in); 
		this.check_out = new SimpleStringProperty(check_out); 
		this.name = new SimpleStringProperty(name); 
		this.sname = new SimpleStringProperty(sname);
		this.tel = new SimpleStringProperty(tel); 
		this.email = new SimpleStringProperty(email); 
		this.idnum = new SimpleStringProperty(idnum); 
		this.payment_method = new SimpleStringProperty(payment_method); 
		this.total_cost = total_cost; 
		this.paid = new SimpleStringProperty(paid); 
		this.money_received = money_received; 
		this.status = new SimpleStringProperty(status);
	}

	public void setB_id (int s) {
		b_id = s;
	}

	public void setCode (String  s) {
		code.set(s);
	}

	public void setCheck_in (String s) {
		check_in.set(s);
	}

	public void setCheck_out (String s) {
		check_out.set(s);
	}

	public void setname (String s) {
		name.set(s);
	}

	public void setSname (String s) {
		sname.set(s);
	}

	public void setTel (String s) {
		tel.set(s);
	}

	public void setEmail (String s) {
		email.set(s);
	}

	public void setIdnum (String s) {
		idnum.set(s);
	}

	public void setPayment_method (String s) {
		payment_method.set(s);
	}

	public void setTotal_cost (float s) {
		total_cost = s;
	}

	public void setPaid (String s) {
		paid.set(s);
	}

	public void setMoney_received (float s) {
		money_received = s;
	}

	public void setStatus (String s) {
		status.set(s);
	}
	
	public int getB_id() {
		return b_id;
	}

	public String getCode () {
		return code.get();
	}

	public String getCheck_in () throws ParseException {
		String origDate = check_in.getValueSafe();
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(origDate);
		String newDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
		
		return newDate;
	}

	public String getCheck_out () throws ParseException {
		String origDate = check_out.getValueSafe();
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(origDate);
		String newstring = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
		
		return newstring;
	}

	public String getName () {
		return name.get();
	}

	public String getSname () {
		return sname.get();
	}

	public String getTel () {
		return tel.get();
	}

	public String getEmail () {
		return email.get();
	}

	public String getIdnum () {
		return idnum.get();
	}

	public String getPayment_method () {
		return payment_method.get();
	}

	public float getTotal_cost () {
		return total_cost;
	}

	public String getPaid () {
		return paid.get();
	}

	public float getMoney_received () {
		return money_received;
	}

	public String getStatus () {
		return status.get();
	}

}
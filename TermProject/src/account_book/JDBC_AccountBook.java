package account_book;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// MYSQL�� �����ϱ� ���� Ŭ����
public class JDBC_AccountBook {
	
	// CONNECTION �� SATEMENT ����
	Connection conn = null;
	Statement stmt = null;
	
	// �ܾ� ���� �� �ʱ�ȭ
	int balance = 0;
	
	// DB����
	public void connectDB(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// ROOT�� �����Ͽ� ACCOUNT_BOOK �����ͺ��̽� ���
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "root", "apmsetup");
				stmt = conn.createStatement();
				
			}catch (ClassNotFoundException cnfe) {
				System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
			}catch (SQLException se) {
				System.out.println(se.getMessage());
			}
	}
	
	// DB�� �ִ� ���� ��� ��ü ������ �ҷ��ͼ� 2���� OBJECT�� ��ȯ
	public Object[][] selectIncomeAll() {
		// DB�� �����ϴ� �����͵��� �ҷ��ͼ� �����ϱ� ���� ARRAYLIST ���� - arrayList�� ������ ���� ��� �߰��� ���� �� �ս��� �Ϸ���
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			// WHILE ���� ������ �ݺ��� ���� �����ϱ� ���� COUNT ���� ����
			int count = 0;
			// SLQ ��
			ResultSet sql = stmt.executeQuery("select * from income;");
			
			// ���ǿ� �ش��ϴ� DATA�� �����ϸ� WHILE�� ����
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				// ���ǿ� �´� �����͵��� LIST�� ��ü�� ����ֱ�
				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// ��ȯ��ų ���� ���� �� �ʱ�ȭ
		Object[][] obj = new Object[listObj.size()][];
		
		// WHILE���� ���� ������� 2���� ARRAYLIST�� ����ִ� �����͵� �� ��ŭ ��ȯ�� �� ����ֱ�
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// DB�� �ִ� ���� ��� ��ü ������ �ҷ��ͼ� 2���� OBJECT�� ��ȯ
	public Object[][] selectOutcomeAll() {
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			ResultSet sql = stmt.executeQuery("select * from outcome;");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] obj = new Object[listObj.size()][];
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// �ܾ��� ��ȯ�ϴ� �Լ�
	public int getBalance() {
		try {
			balance = 0;
			
			// ���� ���̺� �� �ݾ��� ���� �ҷ��´�
			ResultSet sql = stmt.executeQuery("select money from income;");
			
			// ���� �����ϸ�,
			while(sql.next()) {
				int money = sql.getInt("money");
				// �ܾ� ������ �� ��ŭ�� money�� �߰��ϰ�
				balance += money;
			}
			
			// ���� ���̺� �� �ݾ��� ���� �ҷ��´�.
			sql = stmt.executeQuery("select money from outcome;");
			
			// ���� �����ϸ�,
			while(sql.next()) {
				int money = sql.getInt("money");
				// �ܾ� ������ �� ��ũ�� �ݾ��� ����.
				balance -= money;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// �ռ� ��� �Ϸ�� �ݾ��� ��ȯ�Ѵ�.
		return balance;
	}
	
	// ���� �κ� �����ͺ��̽��� �߰��ϱ�
	public void insertIncome(String date, String money, String memo, String category) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "account", "1234");
			stmt = conn.createStatement();
			
			String sql = "insert into income(date, money, memo, category) values('" + date + "', " +money + ", '" 
						+ memo + "', '" + category + "');";
			
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "������ �Է��Ͽ����ϴ�!", "���� �Է�", JOptionPane.INFORMATION_MESSAGE);
			
		}catch (ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
		}catch (SQLException se) {
			System.out.println(se.getMessage());
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// �����ͺ��̽��� �߰��� ���� �ٽ� �ҷ��ͼ� ���̺� ������ �ϱ� ���� Object �������� �����͸� ��ȯ��Ų��.
	public Object[] selectInsertIn(String date, String money, String memo, String category) {
		Object []obj = new Object[6];
		try {
			ResultSet objQuery = stmt.executeQuery("select date, money, memo, category from income " +
								"where date='"+date+"' and money='"+money+"' and memo='"+memo+"' and category='"+category+"';");
			
			while(objQuery.next()) 
			{
				String objDate = objQuery.getString("date");
				int objMoney = objQuery.getInt("money");
				String objMemo = objQuery.getString("memo");
				String objCategory = objQuery.getString("category");

				Object[] obj2 = {objDate, objMoney, objMemo, objCategory, "����", "����"};
				// ���� ��ȯ�ϱ� ���� ������ ����ֱ�. �̰��� �Է��� �� �ϳ��� �����ϱ� ������ 2���� �迭�� �ƴ� 1���� �迭�̴�.
				obj = obj2;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
		return obj;
	}
	
	// ���� �κ� �����ͺ��̽��� �߰��ϱ�
	public void insertOutcome(String date, String money, String memo, String category) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "account", "1234");
			stmt = conn.createStatement();
			String sql = "insert into outcome(date, money, memo, category) values('" + date + "', " +money + ", '" 
						+ memo + "', '" + category + "');";
			
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "������ �Է��Ͽ����ϴ�!", "���� �Է�", JOptionPane.INFORMATION_MESSAGE);
			
		}catch (ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
		}catch (SQLException se) {
			System.out.println(se.getMessage());
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// �����ͺ��̽��� �߰��� ���� �ٽ� �ҷ��ͼ� ���̺� ������ �ϱ� ���� Object �������� �����͸� ��ȯ��Ų��.
	public Object[] selectInsertOut(String date, String money, String memo, String category) {
		Object []obj = new Object[6];
		try {

			ResultSet objQuery = stmt.executeQuery("select date, money, memo, category from outcome " +
								"where date='"+date+"' and money='"+money+"' and memo='"+memo+"' and category='"+category+"';");
			
			while(objQuery.next()) 
			{
				String objDate = objQuery.getString("date");
				int objMoney = objQuery.getInt("money");
				String objMemo = objQuery.getString("memo");
				String objCategory = objQuery.getString("category");

				Object[] obj2 = {objDate, objMoney, objMemo, objCategory, "����", "����"};
				obj = obj2;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
		return obj;
	}
	
	// ���� ���̺� �ش�� �����͸� db���� �����ϱ� ���� ���� �Լ�
	public void deleteInObject(String date, String money, String memo, String category) {
		Object []obj = new Object[6];
		try {
			String sql = "delete from income " +
					"where date='"+date+"' and money='"+money+"' and memo='"+memo+"' and category='"+category+"';";
			stmt.executeUpdate(sql);
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// ���� ���̺� �ش�� �����͸� db���� �����ϱ� ���� ���� �Լ�
	public void deleteOutObject(String date, String money, String memo, String category) {
		Object []obj = new Object[6];
		try {
			String sql = "delete from outcome " +
					"where date='"+date+"' and money='"+money+"' and memo='"+memo+"' and category='"+category+"';";
			stmt.executeUpdate(sql);
			
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// �ش� �Ϸ� ��¥�� �´� ���� �����͸� �˻��ϰ� �װ��� 2���� Object�迭�� ��ȯ�����ִ� �Լ�
	public Object[][] SearchIn(String _date) {
		// �̵� ���� �����͸� �߰��ϱ� ���� 2���� ARRAYLIST�� ����Ѵ�.
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			ResultSet sql = stmt.executeQuery("select * from income where date='"+_date+"';");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// �Լ����� ��ȯ�ϱ� ���� 2���� OBJECT��, ARRAYLISTũ�� ��ŭ ũ�⸦ �Ҵ��Ѵ�.
		Object[][] obj = new Object[listObj.size()][];
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// �ش� �Ϸ� ��¥�� �´� ���� �����͸� �˻��ϰ� �װ��� 2���� Object�迭�� ��ȯ�����ִ� �Լ�
	public Object[][] SearchOut(String _date) {
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			ResultSet sql = stmt.executeQuery("select * from outcome where date='"+_date+"';");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] obj = new Object[listObj.size()][];
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// �Է��� ��¥ ���� �ȿ� �����ϴ� ���� �����͸� �˻��ϰ� �װ��� 2���� Object�迭�� ��ȯ�����ִ� �Լ�
	public Object[][] SearchIn(String _date1, String _date2) {
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			// ���� ����� ���� MYSQL�� BETWEEN - AND �����ڸ� ����Ͽ���.
			ResultSet sql = stmt.executeQuery("select * from income where date between '"+_date1+"' and '"+_date2+"';");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] obj = new Object[listObj.size()][];
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// �Է��� ��¥ ���� �ȿ� �����ϴ� ���� �����͸� �˻��ϰ� �װ��� 2���� Object�迭�� ��ȯ�����ִ� �Լ�
	public Object[][] SearchOut(String _date1, String _date2) {
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			ResultSet sql = stmt.executeQuery("select * from outcome where date between '"+_date1+"' and '"+_date2+"';");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "����", "����"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Object[][] obj = new Object[listObj.size()][];
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// ���� ���̺� ���� �����͸� �ҷ��� ������Ʈ �ϴ� �����ͷ� ������Ʈ �ϴ� �Լ�
	public void updateIn(String p_date, String p_money, String p_memo, String category, 
			String u_date, String u_money, String u_memo, String u_category) {
		try {
			String sql = "Update income set date='"+u_date+"', money="+u_money+", memo='" + u_memo+ "', category='" + u_category + 
					"' where date='"+p_date+"' and money="+p_money+" and memo='" + p_memo +"' and category ='"+category+"';";

			stmt.executeUpdate(sql);

			}catch (SQLException e) {
			e.printStackTrace();
			}
	}
	
	// ���� ���̺� ���� �����͸� �ҷ��� ������Ʈ �ϴ� �����ͷ� ������Ʈ �ϴ� �Լ�
	public void updateOut(String p_date, String p_money, String p_memo, String category, 
						String u_date, String u_money, String u_memo, String u_category) {
		try {
			String sql = "Update outcome set date='"+u_date+"', money="+u_money+", memo='" + u_memo+ "', category='" + u_category + 
					"' where date='"+p_date+"' and money="+p_money+" and memo='" + p_memo +"' and category ='"+category+"';";
			
			stmt.executeUpdate(sql);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// DB���� ����
	public void closeDB() 
	{
		try {
			stmt.close();
		} 
		catch (Exception ignored) {
		}
		try {
			conn.close();
		} catch (Exception ignored) {
		}
	}

}

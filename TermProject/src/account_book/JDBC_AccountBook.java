package account_book;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// MYSQL과 연결하기 위한 클래스
public class JDBC_AccountBook {
	
	// CONNECTION 및 SATEMENT 선언
	Connection conn = null;
	Statement stmt = null;
	
	// 잔액 선언 및 초기화
	int balance = 0;
	
	// DB연결
	public void connectDB(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// ROOT에 접속하여 ACCOUNT_BOOK 데이터베이스 사용
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "root", "apmsetup");
				stmt = conn.createStatement();
				
			}catch (ClassNotFoundException cnfe) {
				System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
			}catch (SQLException se) {
				System.out.println(se.getMessage());
			}
	}
	
	// DB에 있는 수입 목록 전체 데이터 불러와서 2차원 OBJECT에 반환
	public Object[][] selectIncomeAll() {
		// DB에 존재하는 데이터들을 불러와서 저장하기 위한 ARRAYLIST 선언 - arrayList로 선언한 것은 노드 추가를 조금 더 손쉽게 하려고
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			// WHILE 문을 돌려서 반복된 수를 저장하기 위한 COUNT 변수 선언
			int count = 0;
			// SLQ 문
			ResultSet sql = stmt.executeQuery("select * from income;");
			
			// 조건에 해당하는 DATA가 존재하면 WHILE문 실행
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				// 조건에 맞는 데이터들을 LIST와 객체에 집어넣기
				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// 반환시킬 변수 선언 및 초기화
		Object[][] obj = new Object[listObj.size()][];
		
		// WHILE문을 통해 집어넣은 2차원 ARRAYLIST에 들어있는 데이터들 수 만큼 반환할 값 집어넣기
		for(int i = 0; i < listObj.size(); i++) {
			Object[] row = listObj.get(i);
			obj[i] = new Object[row.length];
			for(int j=0; j<row.length; j++) {
				obj[i][j] = row[j];
			}
		}
		return obj;
	}
	
	// DB에 있는 지출 목록 전체 데이터 불러와서 2차원 OBJECT에 반환
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

				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
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
	
	// 잔액을 반환하는 함수
	public int getBalance() {
		try {
			balance = 0;
			
			// 수입 테이블 속 금액을 전부 불러온다
			ResultSet sql = stmt.executeQuery("select money from income;");
			
			// 값이 존재하면,
			while(sql.next()) {
				int money = sql.getInt("money");
				// 잔액 변수에 그 만큼의 money를 추가하고
				balance += money;
			}
			
			// 지출 테이블 속 금액을 전부 불러온다.
			sql = stmt.executeQuery("select money from outcome;");
			
			// 값이 존재하면,
			while(sql.next()) {
				int money = sql.getInt("money");
				// 잔액 변수에 그 만크의 금액을 뺀다.
				balance -= money;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// 앞서 계산 완료된 금액을 반환한다.
		return balance;
	}
	
	// 수입 부분 데이터베이스에 추가하기
	public void insertIncome(String date, String money, String memo, String category) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "account", "1234");
			stmt = conn.createStatement();
			
			String sql = "insert into income(date, money, memo, category) values('" + date + "', " +money + ", '" 
						+ memo + "', '" + category + "');";
			
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "수입을 입력하였습니다!", "수입 입력", JOptionPane.INFORMATION_MESSAGE);
			
		}catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}catch (SQLException se) {
			System.out.println(se.getMessage());
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// 데이터베이스에 추가한 값을 다시 불러와서 테이블 갱신을 하기 위해 Object 형식으로 데이터를 반환시킨다.
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

				Object[] obj2 = {objDate, objMoney, objMemo, objCategory, "삭제", "수정"};
				// 값을 반환하기 위해 데이터 집어넣기. 이것은 입력한 값 하나만 존재하기 때문에 2차원 배열이 아닌 1차원 배열이다.
				obj = obj2;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
		return obj;
	}
	
	// 지출 부분 데이터베이스에 추가하기
	public void insertOutcome(String date, String money, String memo, String category) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/account_book", "account", "1234");
			stmt = conn.createStatement();
			String sql = "insert into outcome(date, money, memo, category) values('" + date + "', " +money + ", '" 
						+ memo + "', '" + category + "');";
			
			stmt.executeUpdate(sql);
			
			JOptionPane.showMessageDialog(null, "지출을 입력하였습니다!", "지출 입력", JOptionPane.INFORMATION_MESSAGE);
			
		}catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}catch (SQLException se) {
			System.out.println(se.getMessage());
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
	}
	
	// 데이터베이스에 추가한 값을 다시 불러와서 테이블 갱신을 하기 위해 Object 형식으로 데이터를 반환시킨다.
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

				Object[] obj2 = {objDate, objMoney, objMemo, objCategory, "삭제", "수정"};
				obj = obj2;
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(NumberFormatException nf) {
			nf.printStackTrace();
		}
		return obj;
	}
	
	// 수입 테이블에 해당된 데이터를 db에서 삭제하기 위한 쿼리 함수
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
	
	// 지출 테이블에 해당된 데이터를 db에서 삭제하기 위한 쿼리 함수
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
	
	// 해당 하루 날짜에 맞는 수입 데이터를 검색하고 그것을 2차원 Object배열로 반환시켜주는 함수
	public Object[][] SearchIn(String _date) {
		// 이도 쉽게 데이터를 추가하기 위해 2차원 ARRAYLIST를 사용한다.
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

				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
				listObj.add(count, obj2);
				count++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		// 함수에서 반환하기 위한 2차원 OBJECT를, ARRAYLIST크기 만큼 크기를 할당한다.
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
	
	// 해당 하루 날짜에 맞는 지출 데이터를 검색하고 그것을 2차원 Object배열로 반환시켜주는 함수
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

				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
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
	
	// 입력한 날짜 범위 안에 존재하는 수입 데이터를 검색하고 그것을 2차원 Object배열로 반환시켜주는 함수
	public Object[][] SearchIn(String _date1, String _date2) {
		ArrayList<Object[]> listObj = new ArrayList<Object[]>();
		try {
			int count = 0;
			// 범위 계산을 위해 MYSQL의 BETWEEN - AND 연산자를 사용하였다.
			ResultSet sql = stmt.executeQuery("select * from income where date between '"+_date1+"' and '"+_date2+"';");
			
			while(sql.next()) 
			{
				String date = sql.getString("date");
				int money = sql.getInt("money");
				String memo = sql.getString("memo");
				String category = sql.getString("category");

				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
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
	
	// 입력한 날짜 범위 안에 존재하는 지출 데이터를 검색하고 그것을 2차원 Object배열로 반환시켜주는 함수
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

				Object[] obj2 = {date, money, memo, category, "삭제", "수정"};
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
	
	// 수입 테이블에 이전 데이터를 불러와 업데이트 하는 데이터로 업데이트 하는 함수
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
	
	// 지출 테이블에 이전 데이터를 불러와 업데이트 하는 데이터로 업데이트 하는 함수
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
	
	// DB연결 해제
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

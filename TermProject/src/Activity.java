import java.util.GregorianCalendar;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Data Value + 생성자 접근자
public class Activity {
	
	/** 변수 선언 */
	GregorianCalendar cal = new GregorianCalendar();	// 날짜 객체
	private GregorianCalendar date;
	private int money;				// 삽입용 금액
	private String memo;			// 삽입용 메모
	private int totalOutcom;		// 월 전체 지출
	private int totalIncome;		// 월 전체 수입
	private int budget;				// 예산
	private int tag;				// 항목
	private int num;				// 각 항목별 인덱스 번호
	
	// 데이터 쓰기 위한 스트림
	DataOutputStream out = null;
	
	// 데이터 쓰고 읽기 위한 스트림 - 테스트용 
	DataOutputStream outTest = null;	
	// 데이터 읽기 위한 스트림 - 테스트용
	DataInputStream in = null;
	
	/** 접근자 생성자 */	
	// 기본생성자 
	public Activity() {}
	
	// 수입, 지출 객체 생성자
	public Activity(GregorianCalendar cal, int money, String memo) {
		this.cal = cal;
		this.money = money;
		this.memo = memo;
	}
	
	/** GregorianCalendar 출력 하는 함수 */
	public void toStringCal() {
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy.MM.dd");
	}
	
	/** 날짜 객체 접근자 
	 * @return the cal
	 */
	public GregorianCalendar getCal() {
		return cal;
	}
	
	/** 날짜 객체 생성자
	 * @param cal the cal to set
	 */
	public void setCal(GregorianCalendar cal) {
		this.cal = cal;
	}
	
	/** 삽입용 금액 접근자
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}
	
	/** 삽입용 금액 생성자
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	
	/** 삽입용 메모 접근자
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	
	/** 삽입용 메모 생성자
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/** 지출 총액 접근자
	 * @return the totalSpend
	 */
	public int getTotalSpend() {
		return totalOutcom;
	}

	/** 지출 총액 생성자
	 * @param totalSpend the totalSpend to set
	 */
	public void setTotalSpend(int totalOutcom) {
		this.totalOutcom += totalOutcom;
	}

	/** 수입 총액 접근자
	 * @return the totalIncome
	 */
	public int getTotalIncome() {
		return totalIncome;
	}

	/** 수입 총액 생성자
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(int totalIncome) {
		this.totalIncome += totalIncome;
	}

	/** 예산 접근자
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}
	
	/** 예산 생성자
	 * @param budget the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	/** 항목 접근자
	 * @return the category
	 */
	public int getTag() {
		return tag;
	}
	
	/** 항목 생성자
	 * @param category the category to set
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	/** 날짜 접근자 
	 */
	public GregorianCalendar GetDate() {
		return date;
	}
	
	/** 날짜 생성자
	 * @param date
	 */
	public void SetDate(GregorianCalendar date) {
		this.date = date;
	}
	
	/** 각 객체별 가지고 있는 고유 번호 접근자
	 * @return
	 */
	public int GetNum() {
		return num;
	}
	
	/** 각 객체별 가지고 있는 고유 번호 생성자
	 * @param num
	 */
	public void SetNum(int num) {
		this.num = num;
	}
	
	// FileOutputStream 이용
	public void WriteFile(boolean isInOut) {
		try {
			// out은 이어쓰기를 위한 스트림 파일
			out = new DataOutputStream(new FileOutputStream("output.txt", true));
			// outTest는 읽고 콘솔에 보여주기 위한 스트림 파일
			outTest = new DataOutputStream(new FileOutputStream("input.txt", false));
			
			String inOut = null;
			
			String date = "<< Date : " + cal.get(GregorianCalendar.YEAR) + "." 
					+ cal.get((GregorianCalendar.MONTH)+1) + "."
					+ cal.get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
			
			String mm = "<< Memo : " + memo + " >>\n";
			
			String mn = "<< Money : " + money + " >> \n\n";
			
			// 아규먼트 타입이 true이면 수입
			if(isInOut == true) {
				inOut = "<< " + (num+1) + ". 수입 >>\n";
			}else {	// 아규먼트 타입이 false 이면 지출
				inOut = "<< " + (num+1) + ". 지출 >>\n";
			}
			
			// 이어쓰기 값 집어넣기
			out.writeUTF(inOut);
			out.writeUTF(date);
			out.writeUTF(mm);
			out.writeUTF(mn);
			
			// input test 용 (계속해서 파일이 갱신된다)
			outTest.writeUTF(inOut);
			outTest.writeUTF(date);
			outTest.writeUTF(mm);
			outTest.writeUTF(mn);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.getMessage();
		}catch(Exception e) {
			e.getMessage();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ReadFile() {
		try{
			in = new DataInputStream(new FileInputStream("input.txt")); 
			
			System.out.print(in.readUTF()); 
			System.out.print(in.readUTF()); 
			System.out.print(in.readUTF());
			System.out.print(in.readUTF());
			
		}catch(FileNotFoundException fife){
			System.out.println("파일이 존재하지 않습니다.");
		}catch(EOFException e){
			System.out.println("끝");
		}catch(IOException e){
			System.out.println("파일을 읽을 수 없습니다");
		}finally {
			try{
				in.close();
			}catch(Exception e){
				
			}
		}
	}
}

import java.util.GregorianCalendar;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Data Value + ������ ������
public class Activity {
	
	/** ���� ���� */
	GregorianCalendar cal = new GregorianCalendar();	// ��¥ ��ü
	private GregorianCalendar date;
	private int money;				// ���Կ� �ݾ�
	private String memo;			// ���Կ� �޸�
	private int totalOutcom;		// �� ��ü ����
	private int totalIncome;		// �� ��ü ����
	private int budget;				// ����
	private int tag;				// �׸�
	private int num;				// �� �׸� �ε��� ��ȣ
	
	// ������ ���� ���� ��Ʈ��
	DataOutputStream out = null;
	
	// ������ ���� �б� ���� ��Ʈ�� - �׽�Ʈ�� 
	DataOutputStream outTest = null;	
	// ������ �б� ���� ��Ʈ�� - �׽�Ʈ��
	DataInputStream in = null;
	
	/** ������ ������ */	
	// �⺻������ 
	public Activity() {}
	
	// ����, ���� ��ü ������
	public Activity(GregorianCalendar cal, int money, String memo) {
		this.cal = cal;
		this.money = money;
		this.memo = memo;
	}
	
	/** GregorianCalendar ��� �ϴ� �Լ� */
	public void toStringCal() {
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy.MM.dd");
	}
	
	/** ��¥ ��ü ������ 
	 * @return the cal
	 */
	public GregorianCalendar getCal() {
		return cal;
	}
	
	/** ��¥ ��ü ������
	 * @param cal the cal to set
	 */
	public void setCal(GregorianCalendar cal) {
		this.cal = cal;
	}
	
	/** ���Կ� �ݾ� ������
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}
	
	/** ���Կ� �ݾ� ������
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}
	
	/** ���Կ� �޸� ������
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	
	/** ���Կ� �޸� ������
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/** ���� �Ѿ� ������
	 * @return the totalSpend
	 */
	public int getTotalSpend() {
		return totalOutcom;
	}

	/** ���� �Ѿ� ������
	 * @param totalSpend the totalSpend to set
	 */
	public void setTotalSpend(int totalOutcom) {
		this.totalOutcom += totalOutcom;
	}

	/** ���� �Ѿ� ������
	 * @return the totalIncome
	 */
	public int getTotalIncome() {
		return totalIncome;
	}

	/** ���� �Ѿ� ������
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(int totalIncome) {
		this.totalIncome += totalIncome;
	}

	/** ���� ������
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}
	
	/** ���� ������
	 * @param budget the budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}
	
	/** �׸� ������
	 * @return the category
	 */
	public int getTag() {
		return tag;
	}
	
	/** �׸� ������
	 * @param category the category to set
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
	
	/** ��¥ ������ 
	 */
	public GregorianCalendar GetDate() {
		return date;
	}
	
	/** ��¥ ������
	 * @param date
	 */
	public void SetDate(GregorianCalendar date) {
		this.date = date;
	}
	
	/** �� ��ü�� ������ �ִ� ���� ��ȣ ������
	 * @return
	 */
	public int GetNum() {
		return num;
	}
	
	/** �� ��ü�� ������ �ִ� ���� ��ȣ ������
	 * @param num
	 */
	public void SetNum(int num) {
		this.num = num;
	}
	
	// FileOutputStream �̿�
	public void WriteFile(boolean isInOut) {
		try {
			// out�� �̾�⸦ ���� ��Ʈ�� ����
			out = new DataOutputStream(new FileOutputStream("output.txt", true));
			// outTest�� �а� �ֿܼ� �����ֱ� ���� ��Ʈ�� ����
			outTest = new DataOutputStream(new FileOutputStream("input.txt", false));
			
			String inOut = null;
			
			String date = "<< Date : " + cal.get(GregorianCalendar.YEAR) + "." 
					+ cal.get((GregorianCalendar.MONTH)+1) + "."
					+ cal.get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
			
			String mm = "<< Memo : " + memo + " >>\n";
			
			String mn = "<< Money : " + money + " >> \n\n";
			
			// �ƱԸ�Ʈ Ÿ���� true�̸� ����
			if(isInOut == true) {
				inOut = "<< " + (num+1) + ". ���� >>\n";
			}else {	// �ƱԸ�Ʈ Ÿ���� false �̸� ����
				inOut = "<< " + (num+1) + ". ���� >>\n";
			}
			
			// �̾�� �� ����ֱ�
			out.writeUTF(inOut);
			out.writeUTF(date);
			out.writeUTF(mm);
			out.writeUTF(mn);
			
			// input test �� (����ؼ� ������ ���ŵȴ�)
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
			System.out.println("������ �������� �ʽ��ϴ�.");
		}catch(EOFException e){
			System.out.println("��");
		}catch(IOException e){
			System.out.println("������ ���� �� �����ϴ�");
		}finally {
			try{
				in.close();
			}catch(Exception e){
				
			}
		}
	}
}

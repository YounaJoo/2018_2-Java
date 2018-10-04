/** ��� �Լ��� ����
 * @author ������
 */
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class PrevManagement{
	Activity infoIncome[] = new Activity[50];	// ���� ��ü���� ���� �迭 ����
	Activity infoOutcome[] = new Activity[50];	// ���� ��ü���� ���� �迭 ����
	
	private int balance;						// �ܾ� ����
	
	private int inIndex = 0;					// ���� �迭�� �����ϴ� �ε��� ����
	private int outIndex = 0;					// ���� �迭�� �����ϴ� �ε��� ����
	
	// �ܾװ����� ���� ������, ������
	public int GetBalance() {
		return balance;
	}
	
	public void SetBalace(int balance) {
		this.balance = balance;
	}
	
	// ����, ����ó���� �����Է��� �ѹ��� �ϴ� �Լ�
	public void InsertIn(Activity act){
		// try-catch�� �����ϱ� - index ���� �Ѿ�� ���� ����ó�� �߰�-IndexOutOfBoundsException
		// ���� ��ü ��� �迭�� �� á�� ��� 
		try{
			// �Է� ����
			infoIncome[inIndex] = act;	// �迭�� ������ ��ü �־��ֱ�
			act.SetNum(inIndex);		// �� ��ü�� �Ҵ�Ǵ� ������ �� �־��ֱ�
			inIndex++;					// �迭�� �ε��� ����
			
			balance += act.getMoney();	// �ܾ� �����ϱ�
		}catch(IndexOutOfBoundsException ie){
			System.out.print("�迭�� ũ�Ⱑ ���� �ʽ��ϴ�.");
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
	}
	
	// ����, ����ó���� �����Է��� �ѹ��� �ϴ� �Լ�
	public void InsertOut(Activity act) throws Exception{
		// try-catch�� �����ϱ� - index ���� �Ѿ�� ���� ����ó�� �߰�-IndexOutOfBoundsException
		// ���� ��ü ��� �迭�� �� á�� ���
		try{
			// �Է� ����
			infoOutcome[outIndex] = act;	// �迭�� ������ ��ü �־��ֱ�
			act.SetNum(outIndex);			// �� ��ü�� �Ҵ�Ǵ� ������ �� �־��ֱ�
			outIndex++;						// �迭�� �ε��� �� ����
			
			balance -= act.getMoney();		// ���� �����ϱ�
		}catch(IndexOutOfBoundsException ie){
			System.out.print("�迭�� ũ�Ⱑ ���� �ʽ��ϴ�.");
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
	}
	
	// ���� ���� ����ϴ� �Լ�
	public String ToStringIn(int i) throws Exception{
		
		// ��ü�� �������� ���� �� ����ó��
		if(infoIncome == null) {
			throw new Exception("�迭�� ���� �������� �ʽ��ϴ�.");
		}
		
		String date = "<< ��¥ : " + infoIncome[i].GetDate().get(GregorianCalendar.YEAR) + "." 
					+ infoIncome[i].GetDate().get(GregorianCalendar.MONTH) + "."
					+ infoIncome[i].GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
		
		String memo = "<< �޸� : " + infoIncome[i].getMemo() + " >> \n";
		
		String money = "<< �ݾ� : " + infoIncome[i].getMoney() + " >> \n";
		
		// ���ڿ� �������� ��ȯ
		return date + memo + money + "<< �ܾ� : " + balance + "\n";
	}
	
	// ���� ���� ����ϴ� �Լ�
	public String ToStringOut(int i) throws Exception {
		// �ش� ��ü�� �������� ���� �� ����ó��
		if(infoOutcome == null) {
			throw new Exception("�迭�� ���� �������� �ʽ��ϴ�.");
		}
		
		String date = "<< ��¥ : " + infoOutcome[i].GetDate().get(GregorianCalendar.YEAR) + "." 
				+ (infoOutcome[i].GetDate().get(GregorianCalendar.MONTH)+1) + "."
				+ infoOutcome[i].GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
	
		String memo = "<< �޸� : " + infoOutcome[i].getMemo() + " >> \n";
	
		String money = "<< �ݾ� : " + infoOutcome[i].getMoney() + " >> \n";
	
		// ���ڿ� �������� ��ȯ
		return date + memo + money + "<< �ܾ� : " + balance + "\n";
	}
	
	// ���� �˻�
	public Activity[] searchIn(GregorianCalendar cal) throws Exception{
		// ���ϰ��� �ϴ� �˻���ü���� ���� ��ü
		Activity[] searchArr = new Activity[inIndex];
		
		int count=0;

		// ���ϴ� ���� �������� ���� �� ����ó�� �ϱ�
		// ��ü ���
		for(int i=0; i<inIndex; i++) {
			if(infoIncome[i].getCal().equals(cal)){
				searchArr[count] = infoIncome[i];
				count++;
			}
		}

		// ã�����Ѱ� ���ٸ�
		if(searchArr[count] == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
	
	// ���� ���� �Ⱓ �˻� ������
	public Activity[] searchIn(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		// ���ϰ��� �ϴ� �˻� ��ü���� ���� ��ü
		Activity[] searchArr = new Activity[inIndex];
			
		int count =0;
			
		// ��ü ���
		for(int i=0; i<inIndex; i++) {
			if((infoIncome[i].getCal().compareTo(fromDay)>=0)&&(toDay.compareTo(infoIncome[i].getCal())>=0)) { 
				searchArr[count] = infoIncome[i];
				count++;
			}
		}
		
		// ã�����Ѱ� ���ٸ�
		if(searchArr[count] == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
		
	// ���� �˻�
	public Activity[] searchOut(GregorianCalendar cal) throws Exception{
		// ���ϰ��� �ϴ� �˻� ��ü���� ���� ��ü
		Activity[] searchArr = new Activity[outIndex];
		
		int count=0;
		
		// ��ü ���
		for(int i=0; i<outIndex; i++) {
			if(infoOutcome[i].getCal().equals(cal)) {
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}
		
		// ã�����Ѱ� ���ٸ�
		if(searchArr[count] == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
	
	// ���� ���� �Ⱓ �˻� ������
	public Activity[] searchOut(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		Activity[] searchArr = new Activity[outIndex];
			
		int count =0;
			
		for(int i=0; i<outIndex; i++) {
			if((infoOutcome[i].getCal().compareTo(fromDay)>=0)&&(toDay.compareTo(infoOutcome[i].GetDate())>=0)) { 
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}
		
		// ã�����Ѱ� ���ٸ�
		if(searchArr[count] == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
	
	// ���� �׸� ��, ��, �� �޾Ƽ� ���� ����
	public void deleteIn(GregorianCalendar cal, int num)throws Exception {
		
		// ��ü�� �������� ���� �� ����ó��
		if(infoIncome == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}
		
 		for(int i = 0; i < inIndex; i++) {
 			// �ݺ����� �ε��� ��ȣ�ϰ� �����ϰ��� �� ������ �Է��� ���� �����ϴٸ�
 			if(infoIncome[i] == infoIncome[num]) {
 				// �� ���� ����ŭ �ܾ׿��� ���ְ�
 				balance -= infoIncome[num].getMoney();
 				// �ݺ����� ���� ������ �ε����� ��ġ ��ŭ �迭 ��ġ ��ȯ
 				for(int k = num; k < inIndex; k++) {
					infoIncome[k] = infoIncome[k+1];
				}
 				// ���� ������ ���� �ε��� 1�ٿ��ֱ�
				inIndex--;
				// ��ü�� �� ������ ���� ���Ľ����ֱ�
				updateActivityNum(true);
				break;
 				
 			}
 		}
 	}
	
	// ���� �׸�, ��, ��, �� �޾Ƽ� ���� ����
	public void deleteOut(GregorianCalendar cal, int num) throws Exception{
		// ��ü�� �������� ���� �� ����ó��
		if(infoOutcome == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}
		
		// ���� ����� ����
		for(int i = 0; i < outIndex; i++) {
 			if(infoOutcome[i] == infoOutcome[num]) {
 				balance += infoOutcome[num].getMoney();
 				for(int k = num; k < outIndex; k++) {
 					infoOutcome[k] = infoOutcome[k+1];
				}
				inIndex--;
				updateActivityNum(false);
				break;
 				
 			}
 		}
	}
	
	// ���� �׸� ����
	public void updateIn(GregorianCalendar cal, int num, int money,String memo) throws Exception{
		// ��ü�� �������� ���� �� ����ó��
		if(infoIncome == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}
		
		// ���� �ε��� ũ�⸸ŭ �ݺ�
		for(int i = 0; i< inIndex; i++) {
			// �����ϰ��� �ϴ� ������ �ش� �ε��� ������ ������ ��
 			if(infoIncome[i] == infoIncome[num]) {
 				// ���� ���� ���Ա� ��ŭ ���ܾ׿� ���ְ�
 				balance -= infoIncome[num].getMoney();
 				// �Է��� ���Ա� ��ŭ ��ü�� �����ִ� ���Ա� ����
 				infoIncome[num].setMoney(money);
 				// ���� ���� ���Ա� ��ŭ �ܾ׿� �����ְ�
 				balance += infoIncome[num].getMoney();
 				// ������ �޸��� ��ü�� ���� ����
 				infoIncome[num].setMemo(memo);
 				break;
 			}
 		}

 	}

	// ���� �׸� ����
	public void updateOut(GregorianCalendar cal, int num, int money,String memo) throws Exception{
		if(infoOutcome == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}
		
		// ���� �׸� ������ ����
		for(int i = 0; i< outIndex; i++) {
 			if(infoOutcome[i].getCal().equals(cal)) {
 				balance += infoOutcome[num].getMoney();
 				infoOutcome[num].setMoney(money);
 				balance -= infoOutcome[num].getMoney();
 				infoOutcome[num].setMemo(memo);
 				break;
 			}
 		}
 	}
	
	// Activity ��ü�� ��ȣ�� �ùٸ��� �����ϱ� ���� ��
	public void updateActivityNum(Boolean isIn) {
		if(isIn) { // ���� ����
			for(int i = 0; i < inIndex; i++) {
				infoIncome[i].SetNum(i);
			}
		}else {	// ���� ����
			for(int i = 0; i < outIndex; i++) {
				infoOutcome[i].SetNum(i);
			}
		}
	}
	
	// 0918 DataOutputStream�� �̿��Ͽ� ���� ����
	public void saveAll() {
		for(int i = 0; i < inIndex; i++) {
			infoIncome[i].WriteFile(true);
			infoIncome[i].ReadFile();
		}
		
		for(int i = 0; i < outIndex; i++) {
			infoOutcome[i].WriteFile(false);
			infoOutcome[i].ReadFile();
		}
	}

}

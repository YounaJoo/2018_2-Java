/** ��� �Լ��� ����
 * @author ������
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;

public class Management {
	//Activity infoIncome[] = new Activity[50];	// ���� ��ü���� ���� �迭 ����
	//Activity infoOutcome[] = new Activity[50];	// ���� ��ü���� ���� �迭 ����
	
	ArrayList<Activity> infoIncome = new ArrayList<Activity>(); 
	ArrayList<Activity> infoOutcome = new ArrayList<Activity>();
	
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
			//infoIncome[inIndex] = act;	// �迭�� ������ ��ü �־��ֱ�
			infoIncome.add(act);
			act.SetNum(inIndex);		// �� ��ü�� �Ҵ�Ǵ� ������ �� �־��ֱ�
			inIndex++;					// �迭�� �ε��� ����
			
			balance += act.getMoney();	// �ܾ� �����ϱ�
		}catch(IndexOutOfBoundsException ie){ // ����ó��
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
			//infoOutcome[outIndex] = act;	// �迭�� ������ ��ü �־��ֱ�
			infoOutcome.add(act);
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
	public String ToStringIn(int i){
		String date = null;
		String memo = null;
		String money = null;
		try {
			date = "<< ��¥ : " + infoIncome.get(i).GetDate().get(GregorianCalendar.YEAR) + "." 
					+ infoIncome.get(i).GetDate().get(GregorianCalendar.MONTH) + "."
					+ infoIncome.get(i).GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
		
			memo = "<< �޸� : " + infoIncome.get(i).getMemo() + " >> \n";
		
			money = "<< �ݾ� : " + infoIncome.get(i).getMoney() + " >> \n";
					
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		// ���ڿ� �������� ��ȯ
		return date + memo + money + "<< �ܾ� : " + balance + "\n";
	}
	
	// ���� ���� ����ϴ� �Լ�
	public String ToStringOut(int i){
		
		String date = null;
		String memo = null;
		String money = null;
		try {
			date = "<< ��¥ : " + infoOutcome.get(i).GetDate().get(GregorianCalendar.YEAR) + "." 
					+ infoOutcome.get(i).GetDate().get(GregorianCalendar.MONTH) + "."
					+ infoOutcome.get(i).GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
		
			memo = "<< �޸� : " + infoOutcome.get(i).getMemo() + " >> \n";
		
			money = "<< �ݾ� : " + infoOutcome.get(i).getMoney() + " >> \n";
					
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		// ���ڿ� �������� ��ȯ
		return date + memo + money + "<< �ܾ� : " + balance + "\n";
	}
	
	// ���� �˻�
	@SuppressWarnings("unused")
	public ArrayList<Activity> searchIn(GregorianCalendar cal)throws Exception{
		/*// ���ϰ��� �ϴ� �˻���ü���� ���� ��ü
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
		}*/
		ArrayList<Activity> searchArr = new ArrayList<Activity>();
		try {
			for(Activity ac : infoIncome) {
				if(ac.GetDate().equals(cal)) {
					searchArr.add(ac);
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(searchArr == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else {
			return searchArr;
		}
	}
	
	// ���� ���� �Ⱓ �˻� ������
	@SuppressWarnings("unused")
	public ArrayList<Activity> searchIn(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		// ���ϰ��� �ϴ� �˻� ��ü���� ���� ��ü
		ArrayList<Activity> searchArr = new ArrayList<Activity>();
			
		/*int count =0;
			
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
		}*/
		try {
			for(Activity ac : infoIncome) {
				if((ac.getCal().compareTo(fromDay)>=0) &&
						(toDay.compareTo(ac.getCal())>=0)){
					searchArr.add(ac);
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(searchArr == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else {
			return searchArr;
		}
	}
		
	// ���� �˻�
	@SuppressWarnings("unused")
	public ArrayList<Activity> searchOut(GregorianCalendar cal) throws Exception{
		/*// ���ϰ��� �ϴ� �˻� ��ü���� ���� ��ü
		Activity[] searchArr = new Activity[outIndex];
		
		int count=0;
		
		// ��ü ���
		for(int i=0; i<outIndex; i++) {
			if(infoOutcome[i].getCal().equals(cal)) {
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}*/
		
		ArrayList<Activity> searchArr = new ArrayList<Activity>();
		try {
			for(Activity ac : infoOutcome) {
				if(ac.GetDate().equals(cal)) {
					searchArr.add(ac);
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		// ã�����Ѱ� ���ٸ�
		if(searchArr == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
	
	// ���� ���� �Ⱓ �˻� ������
	@SuppressWarnings("unused")
	public ArrayList<Activity> searchOut(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		/*Activity[] searchArr = new Activity[outIndex];
			
		int count =0;
			
		for(int i=0; i<outIndex; i++) {
			if((infoOutcome[i].getCal().compareTo(fromDay)>=0)&&(toDay.compareTo(infoOutcome[i].GetDate())>=0)) { 
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}*/
		ArrayList<Activity> searchArr = new ArrayList<Activity>();
		
		try {
			for(Activity ac : infoOutcome) {
				if((ac.getCal().compareTo(fromDay)>=0) &&
						(toDay.compareTo(ac.getCal())>=0)){
					searchArr.add(ac);
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		// ã�����Ѱ� ���ٸ�
		if(searchArr == null) {
			throw new Exception("���� �������� �ʽ��ϴ�.");
		}else{
			// ã������ ������� ���� ��ü ��ȯ
			return searchArr;
		}
	}
	
	// ���� �׸� ��, ��, �� �޾Ƽ� ���� ����
	public void deleteIn(GregorianCalendar cal, int num){
		try {
 		/*for(int i = 0; i < inIndex; i++) {
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
 		}*/
			for(Activity ac : infoIncome) {
				if(ac.GetDate().equals(cal)) {
					infoIncome.remove(ac);
					balance -= ac.getMoney();
					updateActivityNum(true);
				}
			}
 		}catch(IndexOutOfBoundsException io){
 			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
 	}
	
	// ���� �׸�, ��, ��, �� �޾Ƽ� ���� ����
	public void deleteOut(GregorianCalendar cal, int num){
		/*// ���� ����� ����
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
 		}*/
		try {
			for(Activity ac : infoOutcome) {
				if(ac.GetDate().equals(cal)) {
					infoOutcome.remove(ac);
					balance += ac.getMoney();
					updateActivityNum(false);
				}
			}
		}catch(IndexOutOfBoundsException io){
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	// ���� �׸� ����
	public void updateIn(GregorianCalendar cal, int num, int money,String memo) throws Exception{
		/*// ��ü�� �������� ���� �� ����ó��
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
 		}*/
		try {
			for(int i = 0; i < infoIncome.size(); i++) {
				if(infoIncome.get(i) == infoIncome.get(num)) {
					balance -= infoIncome.get(i).getMoney();
					infoIncome.get(i).setMoney(money);
					balance += infoIncome.get(i).getMoney();
					infoIncome.get(i).setMemo(memo);
					break;
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
 	}

	// ���� �׸� ����
	public void updateOut(GregorianCalendar cal, int num, int money,String memo) throws Exception{
/*		if(infoOutcome == null) {
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
 		}*/
		try {
			for(int i = 0; i < infoOutcome.size(); i++) {
				if(infoOutcome.get(i) == infoOutcome.get(num)) {
					balance += infoOutcome.get(i).getMoney();
					infoOutcome.get(i).setMoney(money);
					balance -= infoOutcome.get(i).getMoney();
					infoOutcome.get(i).setMemo(memo);
					break;
				}
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
 	}
	
	// Activity ��ü�� ��ȣ�� �ùٸ��� �����ϱ� ���� ��
	public void updateActivityNum(Boolean isIn) {
		if(isIn) { // ���� ����
			for(int i = 0; i < infoIncome.size(); i++) {
				infoIncome.get(i).SetNum(i);
			}
		}else {	// ���� ����
			for(int i = 0; i < infoOutcome.size(); i++) {
				infoOutcome.get(i).SetNum(i);
			}
		}
	}
	
	// 0918 DataOutputStream�� �̿��Ͽ� ���� ����
	public void saveAll() {
		/*for(int i = 0; i < infoIncome; i++) {
			infoIncome.WriteFile(true);
			infoIncome[i].ReadFile();
		}
	
		for(int i = 0; i < outIndex; i++) {
			infoOutcome[i].WriteFile(false);
			infoOutcome[i].ReadFile();
		}*/
		try {
			for(Activity ac : infoIncome) {
				ac.WriteFile(true);
				ac.ReadFile();
			}
			for(Activity ac : infoOutcome) {
				ac.WriteFile(false);
				ac.ReadFile();
			}
		}catch(IndexOutOfBoundsException io) {
			System.out.println(io.getMessage());
		}catch(ConcurrentModificationException co) {
			System.out.println(co.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}

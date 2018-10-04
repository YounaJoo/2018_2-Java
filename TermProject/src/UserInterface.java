import java.util.*;
import java.util.GregorianCalendar;

public class UserInterface extends Management{

	/** ī�װ� ���� �迭 */
	public String tagIn[] = {"�ּ���", "�μ���", "�뵷", "��Ÿ"};
	public String tagOut[] = {"�ĺ�", "�����", "����ǰ", "����", 
								"������", "�ְ�/���", "��ȭ/���", "��Ÿ"};

	// �ð����� ī�װ� �����ϱ�
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	// ��ĳ�� ��ü ����
		
		Menu menu = new Menu();					// �޴� ��ü ����
		Management mag = new Management();		// Management ��ü ����
		
		int num = 0;							// �޴� ��ȣ �Է� 
		int year = 0, month = 0, 
				day = 0, money = 0; 			// ��¥ �Է¹��� ����
		String memo = null;						// ���λ��� �Է� ���� ����
		
		while(true) {
			menu.menu();						// �޴� �Լ� �ѷ��ֱ�
			println("");
			println("** 1~9 �� ���ϴ� ��ȣ�� �Է��� �ּ���. **");
			
			// ���� �Է� ����ó��
			try{
				num = sc.nextInt();				// �޴��� ���� ��ȣ�Է�
			}catch(InputMismatchException e) {
				sc = new Scanner(System.in);
				System.out.println("�߸� �Է��ϼ̽��ϴ�. ������ �Է��� �ּ���."); 
			}catch(Exception e) {}
			
			switch(num) {
			case 1 :
				// ���� �Է�
				try{
					Activity actIn = new Activity();	// ��ü ����
					println("���� : �⵵�� �Է����ּ���.");
					year = sc.nextInt();
					println("���� : ���� �Է����ּ���.");
					month = sc.nextInt();
					println("���� : ���� �Է����ּ���.");
					day = sc.nextInt();
					println("���� : �ݾ��� �Է����ּ���.");
					money = sc.nextInt();
					println("���� : ���λ����� �Է����ּ���.");
					memo = sc.next();
					
					// ��ü�� �� �Է�
					GregorianCalendar cal = new GregorianCalendar(year, month-1, day);					
					actIn.SetDate(cal);
					actIn.setCal(cal);
					actIn.setMoney(money);
					actIn.setMemo(memo);
					
					// �迭�� �Է��� ��ü ����ֱ�
					mag.InsertIn(actIn);
					
					// �ʱ�ȭ
					year = 0; month = 0; day = 0;
					memo = null;
					
				}catch(InputMismatchException e) {
					sc = new Scanner(System.in);
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					System.out.println("���� : �⵵, ��, ��, �ݾ�");
					System.out.println("���ڿ� : ���λ���");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 2 :
				// ���� �Է�
				try{
					Activity actOut = new Activity();		// Activity ��ü ����

					// �� ���
					println("���� : �⵵�� �Է����ּ���.");
					year = sc.nextInt();
					println("���� : ���� �Է����ּ���.");
					month = sc.nextInt();
					println("���� : ���� �Է����ּ���.");
					day = sc.nextInt();
					println("���� : �ݾ��� �Է����ּ���.");
					money = sc.nextInt();
					println("���� : ���λ����� �Է����ּ���.");
					memo = sc.next();
					
					// �Է��� �� ��ü�� ����ֱ�
					GregorianCalendar cal = new GregorianCalendar(year, month-1, day);					
					actOut.SetDate(cal);
					actOut.setCal(cal);
					actOut.setMoney(money);
					actOut.setMemo(memo);
					
					// �迭�� ��ü ����
					mag.InsertOut(actOut);
					
					// �ʱ�ȭ
					year = 0; month = 0; day = 0;
					memo = null;
					
				}catch(InputMismatchException e) {
					sc = new Scanner(System.in);
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					System.out.println("���� : �⵵, ��, ��, �ݾ�");
					System.out.println("���ڿ� : ���λ���");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 3 :
				// ��ü ���� ���
				try {
					println("----<< ���� ���� >>----");
					for(int i = 0; i < mag.infoIncome.size(); i++)
					{
						if(mag.infoIncome.get(i) == null) {
							break;
						}else
						{
							System.out.println(mag.ToStringIn(i));
						}
					}
					
					println("----<< ���� ���� >>----");
					// ���⳻���� �������� ������ �� �ݺ����� Ż���Ѵ�.
					for(int i = 0; i < mag.infoOutcome.size(); i++)
					{
						if(mag.infoOutcome.get(i) == null) {
							break;
						}else
						{
							System.out.println(mag.ToStringOut(i));
						}
					}
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 4 :
				// ���� ���� ����
				println("---<< ���� ���� ���� >>---");
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				try {
					// �˻��� ��ü �����ϱ�
					//Activity[] findIncome = new Activity[50];
					ArrayList<Activity> findIncome = new ArrayList<Activity>();
					
					// �� �Է�
					println("���� : �˻��� �⵵�� �Է����ּ���.");
					year = sc.nextInt();
				
					println("���� : �˻��� ���� �Է����ּ���.");
					month = sc.nextInt();
				
					println("���� : �˻��� ������ �Է����ּ���.");
					day = sc.nextInt();
				
					GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
					
					println("--<< �˻� ��� >>--");
					// �Է��� ���� ���� ���� ã��
					findIncome = mag.searchIn(cal1);
					
					// ã���� �ϴ� ������ �Է��� ���� �������� ���ϱ� ���� �迭 ����
					int[] sele = new int[50];
					
					for(int i = 0; i < findIncome.size(); i++) {
						if(findIncome.get(i) != null) {
							sele[i] = findIncome.get(i).GetNum();
							System.out.println("��¥: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
									+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
									+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
									+ "\t���Աݾ�: " +findIncome.get(i).getMoney() 
									+ "\t���γ���: " +findIncome.get(i).getMemo()
									+ "\t��ȣ : " + findIncome.get(i).GetNum());
						}
					}
					if(findIncome != null) {
						println("������ ������ ��µ� ��ȣ�� �Է��ϼ���.");
						int selectDelet = sc.nextInt();
						
						// ã���� �ϴ� ������ �Է��� ���� �������� ���ϱ�
						for(int i = 0; i < sele.length; i++) {
							if(sele[i] == selectDelet) {
								// ���� �����ϸ� �� ���� �ϳ����� �����ϰ� �ݺ����� Ż��
								mag.deleteIn(cal1, selectDelet);
							}
							break;
						}
						System.out.println("�ܾ� : " + mag.GetBalance());
					}
				}catch(InputMismatchException e) {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 5 :
				// ���� ���� ����
				println("---<< ���� ���� ���� >>---");
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				try {
					// �˻��� ��ü ����
					//Activity[] findOutcome = new Activity[50];
					ArrayList<Activity> findOutcome = new ArrayList<Activity>();
					
					println("���� : �˻��� �⵵�� �Է����ּ���.");
					year = sc.nextInt();
				
					println("���� : �˻��� ���� �Է����ּ���.");
					month = sc.nextInt();
				
					println("���� : �˻��� ������ �Է����ּ���.");
					day = sc.nextInt();
				
					GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
					
					println("--<< �˻� ��� >>--");
					// �Է��� ���� ���� ��ü�� �˻��ϱ�
					findOutcome = mag.searchOut(cal1);
					
					// ã���� �ϴ� ������ �Է��� ���� �������� ���ϱ� ���� �迭 ����
					int[] sele = new int[50];
					
					for(int i = 0; i < findOutcome.size(); i++) {
						if(findOutcome.get(i) != null) {
							sele[i] = findOutcome.get(i).GetNum();
							System.out.println("��¥: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
									+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
									+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
									+ "\t����ݾ�: " +findOutcome.get(i).getMoney() 
									+ "\t���γ���: " +findOutcome.get(i).getMemo()
									+ "\t��ȣ : " + findOutcome.get(i).GetNum());
						}
					}
					if(findOutcome != null) {
						println("������ ������ ��µ� ��ȣ�� �Է��ϼ���.");
						int selectDelet = sc.nextInt();
						
						// ã���� �ϴ� ������ �Է��� ���� �������� ���ϱ�
						for(int i = 0; i < sele.length; i++) {
							if(sele[i] == selectDelet) {
								// ���� �����ϸ� �� ���� �ϳ����� �����ϰ� �ݺ����� Ż��
								mag.deleteOut(cal1, selectDelet);
							}
							break;
						}
						System.out.println("�ܾ� : " + mag.GetBalance());
					}
				}catch(InputMismatchException e) {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 6 :
				// ���� ã��
				int select = 0;
				println("---<< ���� ã�� >>---");
				try {
					// �������� �������� �� �� �ϳ� �����ϰ� �ϱ�
					println("1. ���� 		2. ����");
					println("��ȣ�� �Է��� �ּ���.");
					select = sc.nextInt();
					
				// ����ó��
				}catch(InputMismatchException e) {	
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				switch(select) {
				case 1 : // ���� �˻�
					if(!(year == 0 || month == 0 || day == 0)) {
						year = 0; month = 0; day = 0;
					}
					try { 
						// �˻��� ���� ��ü ����
						//Activity[] findIncome = new Activity[50];
						ArrayList<Activity> findIncome = new ArrayList<Activity>();
						
						println("1. ������ ��¥		2. ������ ��¥���� ����");
						int selectIn = sc.nextInt();
						// ������ ��¥�� �˻��ϱ�
						if(selectIn == 1) {
							println("���� : �˻��� �⵵�� �Է����ּ���.");
							year = sc.nextInt();
						
							println("���� : �˻��� ���� �Է����ּ���.");
							month = sc.nextInt();
						
							println("���� : �˻��� ������ �Է����ּ���.");
							day = sc.nextInt();
						
							GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("--<< �˻� ��� >>--");
							
							// ��ü ������
							//findIncome = new Activity[50];
							// ��ü�� ã���� �ϴ� ���� ����
							findIncome = mag.searchIn(cal1);
							
							// ã���� �ϴ� ������ ���� ��¹�
							for(int i = 0; i < findIncome.size(); i++) {
								if(findIncome.get(i) != null) {
									System.out.println("��¥: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
											+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
											+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
											+ "\t���Աݾ�: " +findIncome.get(i).getMoney() 
											+ "\t���γ���: " +findIncome.get(i).getMemo());
								}
							}
							System.out.println("�ܾ� : " + mag.GetBalance());
						}else if(selectIn == 2){	// ������ ��¥���� ���۵��� ã��
							println("���� : �˻��� ���۵� �⵵�� �Է����ּ���.");
							year = sc.nextInt();
						
							println("���� : �˻��� ���۵� ���� �Է����ּ���.");
							month = sc.nextInt();
						
							println("���� : �˻��� ���۵� ������ �Է����ּ���.");
							day = sc.nextInt();
							
							GregorianCalendar fromcal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("���� : �˻��� ���� �⵵�� �Է��� �ּ���. ");
							year = sc.nextInt();
							
							println("���� : �˻��� ���� ���� �Է��� �ּ���. ");
							month = sc.nextInt();
							
							println("���� : �˻��� ���� ������ �Է��� �ּ���. ");
							day = sc.nextInt();
							
							GregorianCalendar tocal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							// ��ü ������
							//findIncome = new Activity[50];
							findIncome = mag.searchIn(fromcal, tocal);

							println("--<< �˻� ��� >>--");
							for(int i = 0; i < findIncome.size(); i++) {
								if(findIncome.get(i) != null) {
									System.out.println("��¥: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
											+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
											+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
											+ "\t���Աݾ�: " +findIncome.get(i).getMoney() 
											+ "\t���γ���: " +findIncome.get(i).getMemo());
								}
							}
							System.out.println("�ܾ� : " + mag.GetBalance());
						}
						else {
							println("�߸� �Է��ϼ̽��ϴ�.");
						}
					}catch(InputMismatchException e){
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2 : // ���� �˻�
					if(!(year == 0 || month == 0 || day == 0)) {
						year = 0; month = 0; day = 0;
					}
					try {
						// �˻��� ���� ��ü ����
						//Activity[] findOutcome = new Activity[50];
						ArrayList<Activity> findOutcome = new ArrayList<Activity>();
						
						println("1. ������ ��¥		2. ������ ��¥���� ����");
						int selectIn = sc.nextInt();
						// ������ ��¥�� �˻��ϱ�
						if(selectIn == 1) {
							println("���� : �˻��� �⵵�� �Է����ּ���.");
							year = sc.nextInt();
						
							println("���� : �˻��� ���� �Է����ּ���.");
							month = sc.nextInt();
						
							println("���� : �˻��� ������ �Է����ּ���.");
							day = sc.nextInt();
						
							GregorianCalendar cal2 = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;

							// ��ü ������
							//findOutcome = new Activity[50];
							// ã�� ���� ���� ��ü�� ����
							findOutcome = mag.searchOut(cal2);
							
							// ��¹�
							println("--<< �˻� ��� >>--");
							for(int i = 0; i < findOutcome.size(); i++) {
								if(findOutcome.get(i) != null) {
									System.out.println("��¥: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "�� "
											+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "�� " 
											+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
											+ "\t���Աݾ� : " +findOutcome.get(i).getMoney() 
											+ "\t���γ��� : " +findOutcome.get(i).getMemo());
								}
							}
							System.out.println("�ܾ� : " + mag.GetBalance());
							
						}else if(selectIn == 2) {	// ������ ��¥���� ���۵��� ã��
							println("���� : �˻��� ���۵� �⵵�� �Է����ּ���.");
							year = sc.nextInt();
						
							println("���� : �˻��� ���۵� ���� �Է����ּ���.");
							month = sc.nextInt();
						
							println("���� : �˻��� ���۵� ������ �Է����ּ���.");
							day = sc.nextInt();
							
							GregorianCalendar fromcal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("���� : �˻��� ���� �⵵�� �Է��� �ּ���. ");
							year = sc.nextInt();
							
							println("���� : �˻��� ���� ���� �Է��� �ּ���. ");
							month = sc.nextInt();
							
							println("���� : �˻��� ���� ������ �Է��� �ּ���. ");
							day = sc.nextInt();
							
							GregorianCalendar tocal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							// ��ü ������
							//findOutcome = new Activity[50];
							findOutcome = mag.searchOut(fromcal, tocal);

							println("--<< �˻� ��� >>--");
							for(int i = 0; i < findOutcome.size(); i++) {
								if(findOutcome.get(i) != null) {
									System.out.println("��¥: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "�� "
											+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "�� " 
											+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
											+ "\t���Աݾ� : " +findOutcome.get(i).getMoney() 
											+ "\t���γ��� : " +findOutcome.get(i).getMemo());
								}
							}
							System.out.println("�ܾ� : " + mag.GetBalance());
						}else {
							println("�߸� �Է��ϼ̽��ϴ�.");
						}
					}catch(InputMismatchException e){
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				default :
					System.out.println("�߸� �Է��ϼ̽��ϴ�. ���� �޴��� ���ư��ϴ�.");
				}
				exitEnter();
				break;
			case 7 :
				// ���� ����
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				int select1 = 0; 
				println("---<< ���� ���� >>---");
				try {
					println("1. ���� 		2. ����");
					println("��ȣ�� �Է��� �ּ���.");
					select1 = sc.nextInt();
					
				// ����ó��
				}catch(InputMismatchException e) {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				switch(select1) {
				case 1: // ����
					try {
						println("���� : �˻��� �⵵�� �Է����ּ���.");
						year = sc.nextInt();
				
						println("���� : �˻��� ���� �Է����ּ���.");
						month = sc.nextInt();
				
						println("���� : �˻��� ������ �Է����ּ���.");
						day = sc.nextInt();
				
						GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
						year = 0; month = 0; day = 0;
					
						// �˻��� ���� ��ü ����
						//Activity[] updateIncome = new Activity[50];
						ArrayList<Activity> updateIncome = new ArrayList<Activity>();
						
						// �˻��ϰ��� �ϴ� ������ ��ü�� ����
						updateIncome = mag.searchIn(cal1);
					
						// ��¹�
						for(int i = 0; i < updateIncome.size(); i++) {
							if(updateIncome.get(i) != null) {
								System.out.println("��¥: " + updateIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
									+ updateIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
									+ updateIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
									+ "\t���Աݾ�: " +updateIncome.get(i).getMoney() 
									+ "\t���γ���: " +updateIncome.get(i).getMemo()
									+ "\t��ȣ :" + updateIncome.get(i).GetNum());
							}
						}
						
						// ����� �κ� �߿� �����ϰ��� �ϴ� �κ� ���� �����ϱ� 
						if(updateIncome != null) {
							System.out.println("������ �׸��� ��ȣ�� �Է��ϼ���.");
							int nIn = sc.nextInt();
							System.out.println("�ݾ��� �Է��ϼ���.");
							int uMoney = sc.nextInt();
							System.out.println("���γ����� �Է��ϼ���.");
							String uMemo= sc.next();
							mag.updateIn(cal1, nIn, uMoney, uMemo);
						}
						System.out.println("\n�ܾ�: " + mag.GetBalance());
					}catch(InputMismatchException e){
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2 : // ����
					try {
						println("���� : �˻��� �⵵�� �Է����ּ���.");
						year = sc.nextInt();
				
						println("���� : �˻��� ���� �Է����ּ���.");
						month = sc.nextInt();
				
						println("���� : �˻��� ������ �Է����ּ���.");
						day = sc.nextInt();
				
						GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
						year = 0; month = 0; day = 0;
					
						// �˻��� ���� ��ü ����
						//Activity[] updateOutcome = new Activity[50];
						ArrayList<Activity> updateOutcome = new ArrayList<Activity>();
						
						updateOutcome = mag.searchOut(cal1);
					
						// ��¹�
						for(int i = 0; i < updateOutcome.size(); i++) {
							if(updateOutcome.get(i) != null) {
								System.out.println("��¥: " + updateOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "��"
									+ updateOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "��" 
									+ updateOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "��"
									+ "\t����ݾ�: " +updateOutcome.get(i).getMoney() 
									+ "\t���γ���: " +updateOutcome.get(i).getMemo()
									+ "\t��ȣ :" + updateOutcome.get(i).GetNum());
							}
						}
						
						// ����� �κ� �߿� �����ϰ��� �ϴ� �κ� ���� �����ϱ� 
						if(updateOutcome != null) {
							System.out.println("������ �׸��� ��ȣ�� �Է��ϼ���.");
							int nIn = sc.nextInt();
							System.out.println("�ݾ��� �Է��ϼ���.");
							int uMoney = sc.nextInt();
							System.out.println("���γ����� �Է��ϼ���.");
							String uMemo= sc.next();
							mag.updateOut(cal1, nIn, uMoney, uMemo);
						}
						System.out.println("\n�ܾ�: " + mag.GetBalance());
					}catch(InputMismatchException e){
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				default :
					println("�߸� �Է��ϼ̽��ϴ�.");
				}
				exitEnter();
				break;
			case 8 :
				// ������ ���� ����
				try {
					System.out.println("--<< �Է��Ͻ� �����͸� ���Ͽ� ���ϴ� >>--");
					mag.saveAll();
				}catch(InputMismatchException e){
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 9 :
				// ���α׷� ����
				println("** ���α׷��� �����մϴ�. **");
				System.exit(0);
			default :
				System.out.println("�߸� �Է��ϼ̽��ϴ�. 1~9 ������ �Է����ּ���");
			}
		}
	}
	
	// System.out.println �Լ�
	public static void println(String txt) {
		System.out.println(txt);
	}
	
	// �޴� ���� �� ���� ���α׷��� ��������, ������� �����
	public static void exitEnter() {
		println("���α׷��� ��� �Ͻðڽ��ϱ�?");
		println("1. ��		2. �ƴϿ�");
		
		Scanner sc = new Scanner(System.in);	// ��ĳ�� ��ü ����
		int num = 0;
		
		try{
			num = sc.nextInt();
		}catch(InputMismatchException e) {
			sc = new Scanner(System.in);
			System.out.println("�߸� �Է��ϼ̽��ϴ�. ������ �Է��� �ּ���.");
		}catch(Exception e) {}
		
		switch(num) {
		case 1 :
			break;
		case 2 :
			println("** ���α׷��� �����մϴ�. **");
			System.exit(0);
			break;
		default :
			System.out.println("�߸� �Է��ϼ̽��ϴ�. 1�� 2�� �Է����ּ���");
		}
	}

}

import java.util.*;
import java.util.GregorianCalendar;

public class UserInterface extends Management{

	/** 카테고리 내용 배열 */
	public String tagIn[] = {"주수입", "부수입", "용돈", "기타"};
	public String tagOut[] = {"식비", "교통비", "생필품", "쇼핑", 
								"병원비", "주거/통신", "문화/취미", "기타"};

	// 시간나면 카테고리 수정하기
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);	// 스캐너 객체 생성
		
		Menu menu = new Menu();					// 메뉴 객체 생성
		Management mag = new Management();		// Management 객체 생성
		
		int num = 0;							// 메뉴 번호 입력 
		int year = 0, month = 0, 
				day = 0, money = 0; 			// 날짜 입력받을 변수
		String memo = null;						// 세부사항 입력 받을 변수
		
		while(true) {
			menu.menu();						// 메뉴 함수 뿌려주기
			println("");
			println("** 1~9 중 원하는 번호를 입력해 주세요. **");
			
			// 정수 입력 예외처리
			try{
				num = sc.nextInt();				// 메뉴에 따른 번호입력
			}catch(InputMismatchException e) {
				sc = new Scanner(System.in);
				System.out.println("잘못 입력하셨습니다. 정수만 입력해 주세요."); 
			}catch(Exception e) {}
			
			switch(num) {
			case 1 :
				// 수입 입력
				try{
					Activity actIn = new Activity();	// 객체 생성
					println("수입 : 년도를 입력해주세요.");
					year = sc.nextInt();
					println("수입 : 월을 입력해주세요.");
					month = sc.nextInt();
					println("수입 : 일을 입력해주세요.");
					day = sc.nextInt();
					println("수입 : 금액을 입력해주세요.");
					money = sc.nextInt();
					println("수입 : 세부사항을 입력해주세요.");
					memo = sc.next();
					
					// 객체에 값 입력
					GregorianCalendar cal = new GregorianCalendar(year, month-1, day);					
					actIn.SetDate(cal);
					actIn.setCal(cal);
					actIn.setMoney(money);
					actIn.setMemo(memo);
					
					// 배열에 입력한 객체 집어넣기
					mag.InsertIn(actIn);
					
					// 초기화
					year = 0; month = 0; day = 0;
					memo = null;
					
				}catch(InputMismatchException e) {
					sc = new Scanner(System.in);
					System.out.println("잘못 입력하셨습니다.");
					System.out.println("정수 : 년도, 월, 일, 금액");
					System.out.println("문자열 : 세부사항");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 2 :
				// 지출 입력
				try{
					Activity actOut = new Activity();		// Activity 객체 생성

					// 값 출력
					println("지출 : 년도를 입력해주세요.");
					year = sc.nextInt();
					println("지출 : 월을 입력해주세요.");
					month = sc.nextInt();
					println("지출 : 일을 입력해주세요.");
					day = sc.nextInt();
					println("지출 : 금액을 입력해주세요.");
					money = sc.nextInt();
					println("지출 : 세부사항을 입력해주세요.");
					memo = sc.next();
					
					// 입력한 값 객체에 집어넣기
					GregorianCalendar cal = new GregorianCalendar(year, month-1, day);					
					actOut.SetDate(cal);
					actOut.setCal(cal);
					actOut.setMoney(money);
					actOut.setMemo(memo);
					
					// 배열에 객체 생성
					mag.InsertOut(actOut);
					
					// 초기화
					year = 0; month = 0; day = 0;
					memo = null;
					
				}catch(InputMismatchException e) {
					sc = new Scanner(System.in);
					System.out.println("잘못 입력하셨습니다.");
					System.out.println("정수 : 년도, 월, 일, 금액");
					System.out.println("문자열 : 세부사항");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 3 :
				// 전체 내역 출력
				try {
					println("----<< 수입 내역 >>----");
					for(int i = 0; i < mag.infoIncome.size(); i++)
					{
						if(mag.infoIncome.get(i) == null) {
							break;
						}else
						{
							System.out.println(mag.ToStringIn(i));
						}
					}
					
					println("----<< 지출 내역 >>----");
					// 지출내역이 존재하지 않으면 이 반복문을 탈출한다.
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
				// 수입 내용 삭제
				println("---<< 수입 내역 삭제 >>---");
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				try {
					// 검색할 객체 생성하기
					//Activity[] findIncome = new Activity[50];
					ArrayList<Activity> findIncome = new ArrayList<Activity>();
					
					// 값 입력
					println("수입 : 검색할 년도를 입력해주세요.");
					year = sc.nextInt();
				
					println("수입 : 검색할 월을 입력해주세요.");
					month = sc.nextInt();
				
					println("수입 : 검색할 요일을 입력해주세요.");
					day = sc.nextInt();
				
					GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
					
					println("--<< 검색 결과 >>--");
					// 입력한 값에 따른 내역 찾기
					findIncome = mag.searchIn(cal1);
					
					// 찾고자 하는 내역과 입력한 값이 동일한지 비교하기 위한 배열 생성
					int[] sele = new int[50];
					
					for(int i = 0; i < findIncome.size(); i++) {
						if(findIncome.get(i) != null) {
							sele[i] = findIncome.get(i).GetNum();
							System.out.println("날짜: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
									+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
									+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
									+ "\t수입금액: " +findIncome.get(i).getMoney() 
									+ "\t세부내역: " +findIncome.get(i).getMemo()
									+ "\t번호 : " + findIncome.get(i).GetNum());
						}
					}
					if(findIncome != null) {
						println("삭제할 내역의 출력된 번호를 입력하세요.");
						int selectDelet = sc.nextInt();
						
						// 찾고자 하는 내역과 입력한 값이 동일한지 비교하기
						for(int i = 0; i < sele.length; i++) {
							if(sele[i] == selectDelet) {
								// 값이 동일하면 그 내용 하나만을 삭제하고 반복문을 탈출
								mag.deleteIn(cal1, selectDelet);
							}
							break;
						}
						System.out.println("잔액 : " + mag.GetBalance());
					}
				}catch(InputMismatchException e) {
					System.out.println("잘못 입력하셨습니다.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 5 :
				// 지출 내용 삭제
				println("---<< 지출 내역 삭제 >>---");
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				try {
					// 검색할 객체 생성
					//Activity[] findOutcome = new Activity[50];
					ArrayList<Activity> findOutcome = new ArrayList<Activity>();
					
					println("지출 : 검색할 년도를 입력해주세요.");
					year = sc.nextInt();
				
					println("지출 : 검색할 월을 입력해주세요.");
					month = sc.nextInt();
				
					println("지출 : 검색할 요일을 입력해주세요.");
					day = sc.nextInt();
				
					GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
					
					println("--<< 검색 결과 >>--");
					// 입력한 값에 따른 객체들 검색하기
					findOutcome = mag.searchOut(cal1);
					
					// 찾고자 하는 내역과 입력한 값이 동일한지 비교하기 위한 배열 생성
					int[] sele = new int[50];
					
					for(int i = 0; i < findOutcome.size(); i++) {
						if(findOutcome.get(i) != null) {
							sele[i] = findOutcome.get(i).GetNum();
							System.out.println("날짜: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
									+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
									+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
									+ "\t지출금액: " +findOutcome.get(i).getMoney() 
									+ "\t세부내역: " +findOutcome.get(i).getMemo()
									+ "\t번호 : " + findOutcome.get(i).GetNum());
						}
					}
					if(findOutcome != null) {
						println("삭제할 내역의 출력된 번호를 입력하세요.");
						int selectDelet = sc.nextInt();
						
						// 찾고자 하는 내역과 입력한 값이 동일한지 비교하기
						for(int i = 0; i < sele.length; i++) {
							if(sele[i] == selectDelet) {
								// 값이 동일하면 그 내용 하나만을 삭제하고 반복문을 탈출
								mag.deleteOut(cal1, selectDelet);
							}
							break;
						}
						System.out.println("잔액 : " + mag.GetBalance());
					}
				}catch(InputMismatchException e) {
					System.out.println("잘못 입력하셨습니다.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 6 :
				// 내용 찾기
				int select = 0;
				println("---<< 내역 찾기 >>---");
				try {
					// 수입인지 지출인지 둘 중 하나 선택하게 하기
					println("1. 수입 		2. 지출");
					println("번호를 입력해 주세요.");
					select = sc.nextInt();
					
				// 예외처리
				}catch(InputMismatchException e) {	
					System.out.println("잘못 입력하셨습니다.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				switch(select) {
				case 1 : // 수입 검색
					if(!(year == 0 || month == 0 || day == 0)) {
						year = 0; month = 0; day = 0;
					}
					try { 
						// 검색을 위한 객체 생성
						//Activity[] findIncome = new Activity[50];
						ArrayList<Activity> findIncome = new ArrayList<Activity>();
						
						println("1. 기입한 날짜		2. 기입한 날짜부터 시작");
						int selectIn = sc.nextInt();
						// 기입한 날짜로 검색하기
						if(selectIn == 1) {
							println("수입 : 검색할 년도를 입력해주세요.");
							year = sc.nextInt();
						
							println("수입 : 검색할 월을 입력해주세요.");
							month = sc.nextInt();
						
							println("수입 : 검색할 요일을 입력해주세요.");
							day = sc.nextInt();
						
							GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("--<< 검색 결과 >>--");
							
							// 객체 재정의
							//findIncome = new Activity[50];
							// 객체에 찾고자 하는 정보 기입
							findIncome = mag.searchIn(cal1);
							
							// 찾고자 하는 정보에 따른 출력문
							for(int i = 0; i < findIncome.size(); i++) {
								if(findIncome.get(i) != null) {
									System.out.println("날짜: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
											+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
											+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
											+ "\t수입금액: " +findIncome.get(i).getMoney() 
											+ "\t세부내역: " +findIncome.get(i).getMemo());
								}
							}
							System.out.println("잔액 : " + mag.GetBalance());
						}else if(selectIn == 2){	// 기입한 날짜부터 시작됨을 찾기
							println("수입 : 검색할 시작될 년도를 입력해주세요.");
							year = sc.nextInt();
						
							println("수입 : 검색할 시작될 월을 입력해주세요.");
							month = sc.nextInt();
						
							println("수입 : 검색할 시작될 요일을 입력해주세요.");
							day = sc.nextInt();
							
							GregorianCalendar fromcal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("수입 : 검색할 종료 년도를 입력해 주세요. ");
							year = sc.nextInt();
							
							println("수입 : 검색할 종료 월을 입력해 주세요. ");
							month = sc.nextInt();
							
							println("수입 : 검색할 종료 요일을 입력해 주세요. ");
							day = sc.nextInt();
							
							GregorianCalendar tocal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							// 객체 재정의
							//findIncome = new Activity[50];
							findIncome = mag.searchIn(fromcal, tocal);

							println("--<< 검색 결과 >>--");
							for(int i = 0; i < findIncome.size(); i++) {
								if(findIncome.get(i) != null) {
									System.out.println("날짜: " + findIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
											+ findIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
											+ findIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
											+ "\t수입금액: " +findIncome.get(i).getMoney() 
											+ "\t세부내역: " +findIncome.get(i).getMemo());
								}
							}
							System.out.println("잔액 : " + mag.GetBalance());
						}
						else {
							println("잘못 입력하셨습니다.");
						}
					}catch(InputMismatchException e){
						System.out.println("잘못 입력하셨습니다.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2 : // 지출 검색
					if(!(year == 0 || month == 0 || day == 0)) {
						year = 0; month = 0; day = 0;
					}
					try {
						// 검색을 위한 객체 생성
						//Activity[] findOutcome = new Activity[50];
						ArrayList<Activity> findOutcome = new ArrayList<Activity>();
						
						println("1. 기입한 날짜		2. 기입한 날짜부터 시작");
						int selectIn = sc.nextInt();
						// 기입한 날짜로 검색하기
						if(selectIn == 1) {
							println("지출 : 검색할 년도를 입력해주세요.");
							year = sc.nextInt();
						
							println("지출 : 검색할 월을 입력해주세요.");
							month = sc.nextInt();
						
							println("지출 : 검색할 요일을 입력해주세요.");
							day = sc.nextInt();
						
							GregorianCalendar cal2 = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;

							// 객체 재정의
							//findOutcome = new Activity[50];
							// 찾고 싶은 정보 객체에 기입
							findOutcome = mag.searchOut(cal2);
							
							// 출력문
							println("--<< 검색 결과 >>--");
							for(int i = 0; i < findOutcome.size(); i++) {
								if(findOutcome.get(i) != null) {
									System.out.println("날짜: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "년 "
											+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월 " 
											+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
											+ "\t수입금액 : " +findOutcome.get(i).getMoney() 
											+ "\t세부내역 : " +findOutcome.get(i).getMemo());
								}
							}
							System.out.println("잔액 : " + mag.GetBalance());
							
						}else if(selectIn == 2) {	// 기입한 날짜부터 시작됨을 찾기
							println("지출 : 검색할 시작될 년도를 입력해주세요.");
							year = sc.nextInt();
						
							println("지출 : 검색할 시작될 월을 입력해주세요.");
							month = sc.nextInt();
						
							println("지출 : 검색할 시작될 요일을 입력해주세요.");
							day = sc.nextInt();
							
							GregorianCalendar fromcal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							println("지출 : 검색할 종료 년도를 입력해 주세요. ");
							year = sc.nextInt();
							
							println("지출 : 검색할 종료 월을 입력해 주세요. ");
							month = sc.nextInt();
							
							println("지출 : 검색할 종료 요일을 입력해 주세요. ");
							day = sc.nextInt();
							
							GregorianCalendar tocal = new GregorianCalendar(year, month-1, day);
							year = 0; month = 0; day = 0;
							
							// 객체 재정의
							//findOutcome = new Activity[50];
							findOutcome = mag.searchOut(fromcal, tocal);

							println("--<< 검색 결과 >>--");
							for(int i = 0; i < findOutcome.size(); i++) {
								if(findOutcome.get(i) != null) {
									System.out.println("날짜: " + findOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "년 "
											+ findOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월 " 
											+ findOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
											+ "\t수입금액 : " +findOutcome.get(i).getMoney() 
											+ "\t세부내역 : " +findOutcome.get(i).getMemo());
								}
							}
							System.out.println("잔액 : " + mag.GetBalance());
						}else {
							println("잘못 입력하셨습니다.");
						}
					}catch(InputMismatchException e){
						System.out.println("잘못 입력하셨습니다.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				default :
					System.out.println("잘못 입력하셨습니다. 메인 메뉴로 돌아갑니다.");
				}
				exitEnter();
				break;
			case 7 :
				// 내용 수정
				if(!(year == 0 || month == 0 || day == 0)) {
					year = 0; month = 0; day = 0;
				}
				int select1 = 0; 
				println("---<< 내역 수정 >>---");
				try {
					println("1. 수입 		2. 지출");
					println("번호를 입력해 주세요.");
					select1 = sc.nextInt();
					
				// 예외처리
				}catch(InputMismatchException e) {
					System.out.println("잘못 입력하셨습니다.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				switch(select1) {
				case 1: // 수입
					try {
						println("수입 : 검색할 년도를 입력해주세요.");
						year = sc.nextInt();
				
						println("수입 : 검색할 월을 입력해주세요.");
						month = sc.nextInt();
				
						println("수입 : 검색할 요일을 입력해주세요.");
						day = sc.nextInt();
				
						GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
						year = 0; month = 0; day = 0;
					
						// 검색을 위한 객체 생성
						//Activity[] updateIncome = new Activity[50];
						ArrayList<Activity> updateIncome = new ArrayList<Activity>();
						
						// 검색하고자 하는 정보를 객체에 기입
						updateIncome = mag.searchIn(cal1);
					
						// 출력문
						for(int i = 0; i < updateIncome.size(); i++) {
							if(updateIncome.get(i) != null) {
								System.out.println("날짜: " + updateIncome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
									+ updateIncome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
									+ updateIncome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
									+ "\t수입금액: " +updateIncome.get(i).getMoney() 
									+ "\t세부내역: " +updateIncome.get(i).getMemo()
									+ "\t번호 :" + updateIncome.get(i).GetNum());
							}
						}
						
						// 출력한 부분 중에 수정하고자 하는 부분 고르고 수정하기 
						if(updateIncome != null) {
							System.out.println("수정할 항목의 번호를 입력하세요.");
							int nIn = sc.nextInt();
							System.out.println("금액을 입력하세요.");
							int uMoney = sc.nextInt();
							System.out.println("세부내역을 입력하세요.");
							String uMemo= sc.next();
							mag.updateIn(cal1, nIn, uMoney, uMemo);
						}
						System.out.println("\n잔액: " + mag.GetBalance());
					}catch(InputMismatchException e){
						System.out.println("잘못 입력하셨습니다.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2 : // 지출
					try {
						println("지출 : 검색할 년도를 입력해주세요.");
						year = sc.nextInt();
				
						println("지출 : 검색할 월을 입력해주세요.");
						month = sc.nextInt();
				
						println("지출 : 검색할 요일을 입력해주세요.");
						day = sc.nextInt();
				
						GregorianCalendar cal1 = new GregorianCalendar(year, month-1, day);
						year = 0; month = 0; day = 0;
					
						// 검색을 위한 객체 생성
						//Activity[] updateOutcome = new Activity[50];
						ArrayList<Activity> updateOutcome = new ArrayList<Activity>();
						
						updateOutcome = mag.searchOut(cal1);
					
						// 출력문
						for(int i = 0; i < updateOutcome.size(); i++) {
							if(updateOutcome.get(i) != null) {
								System.out.println("날짜: " + updateOutcome.get(i).getCal().get(GregorianCalendar.YEAR) + "년"
									+ updateOutcome.get(i).getCal().get((GregorianCalendar.MONTH)-1) + "월" 
									+ updateOutcome.get(i).getCal().get(GregorianCalendar.DAY_OF_MONTH) + "일"
									+ "\t지출금액: " +updateOutcome.get(i).getMoney() 
									+ "\t세부내역: " +updateOutcome.get(i).getMemo()
									+ "\t번호 :" + updateOutcome.get(i).GetNum());
							}
						}
						
						// 출력한 부분 중에 수정하고자 하는 부분 고르고 수정하기 
						if(updateOutcome != null) {
							System.out.println("수정할 항목의 번호를 입력하세요.");
							int nIn = sc.nextInt();
							System.out.println("금액을 입력하세요.");
							int uMoney = sc.nextInt();
							System.out.println("세부내역을 입력하세요.");
							String uMemo= sc.next();
							mag.updateOut(cal1, nIn, uMoney, uMemo);
						}
						System.out.println("\n잔액: " + mag.GetBalance());
					}catch(InputMismatchException e){
						System.out.println("잘못 입력하셨습니다.");
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				default :
					println("잘못 입력하셨습니다.");
				}
				exitEnter();
				break;
			case 8 :
				// 데이터 파일 저장
				try {
					System.out.println("--<< 입력하신 데이터를 파일에 씁니다 >>--");
					mag.saveAll();
				}catch(InputMismatchException e){
					System.out.println("잘못 입력하셨습니다.");
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				exitEnter();
				break;
			case 9 :
				// 프로그램 종료
				println("** 프로그램을 종료합니다. **");
				System.exit(0);
			default :
				System.out.println("잘못 입력하셨습니다. 1~9 까지만 입력해주세요");
			}
		}
	}
	
	// System.out.println 함수
	public static void println(String txt) {
		System.out.println(txt);
	}
	
	// 메뉴 끝날 때 마다 프로그램을 종류할지, 계속할지 물어보기
	public static void exitEnter() {
		println("프로그램을 계속 하시겠습니까?");
		println("1. 예		2. 아니요");
		
		Scanner sc = new Scanner(System.in);	// 스캐너 객체 생성
		int num = 0;
		
		try{
			num = sc.nextInt();
		}catch(InputMismatchException e) {
			sc = new Scanner(System.in);
			System.out.println("잘못 입력하셨습니다. 정수만 입력해 주세요.");
		}catch(Exception e) {}
		
		switch(num) {
		case 1 :
			break;
		case 2 :
			println("** 프로그램을 종료합니다. **");
			System.exit(0);
			break;
		default :
			System.out.println("잘못 입력하셨습니다. 1과 2만 입력해주세요");
		}
	}

}

/** 기능 함수들 존재
 * @author 주윤아
 */
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class PrevManagement{
	Activity infoIncome[] = new Activity[50];	// 수입 객체들을 담을 배열 선언
	Activity infoOutcome[] = new Activity[50];	// 지출 객체들을 담을 배열 선언
	
	private int balance;						// 잔액 관리
	
	private int inIndex = 0;					// 수입 배열을 구분하는 인덱스 변수
	private int outIndex = 0;					// 지출 배열을 구분하는 인덱스 변수
	
	// 잔액관리에 대한 접근자, 생성자
	public int GetBalance() {
		return balance;
	}
	
	public void SetBalace(int balance) {
		this.balance = balance;
	}
	
	// 수입, 예외처리와 수입입력을 한번에 하는 함수
	public void InsertIn(Activity act){
		// try-catch로 변경하기 - index 범위 넘어갔을 때의 예외처리 추가-IndexOutOfBoundsException
		// 수입 객체 담는 배열이 꽉 찼을 경우 
		try{
			// 입력 구문
			infoIncome[inIndex] = act;	// 배열에 생성된 객체 넣어주기
			act.SetNum(inIndex);		// 각 객체에 할당되는 고유의 값 넣어주기
			inIndex++;					// 배열의 인덱스 증가
			
			balance += act.getMoney();	// 잔액 관리하기
		}catch(IndexOutOfBoundsException ie){
			System.out.print("배열의 크기가 맞지 않습니다.");
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
	}
	
	// 지출, 예외처리와 수입입력을 한번에 하는 함수
	public void InsertOut(Activity act) throws Exception{
		// try-catch로 변경하기 - index 범위 넘어갔을 때의 예외처리 추가-IndexOutOfBoundsException
		// 지출 객체 담는 배열이 꽉 찼을 경우
		try{
			// 입력 구문
			infoOutcome[outIndex] = act;	// 배열에 생성된 객체 넣어주기
			act.SetNum(outIndex);			// 각 객체에 할당되는 고유의 값 넣어주기
			outIndex++;						// 배열의 인덱스 값 증가
			
			balance -= act.getMoney();		// 진엑 관리하기
		}catch(IndexOutOfBoundsException ie){
			System.out.print("배열의 크기가 맞지 않습니다.");
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
	}
	
	// 수입 내역 출력하는 함수
	public String ToStringIn(int i) throws Exception{
		
		// 객체가 존재하지 않을 때 예외처리
		if(infoIncome == null) {
			throw new Exception("배열에 값이 존재하지 않습니다.");
		}
		
		String date = "<< 날짜 : " + infoIncome[i].GetDate().get(GregorianCalendar.YEAR) + "." 
					+ infoIncome[i].GetDate().get(GregorianCalendar.MONTH) + "."
					+ infoIncome[i].GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
		
		String memo = "<< 메모 : " + infoIncome[i].getMemo() + " >> \n";
		
		String money = "<< 금액 : " + infoIncome[i].getMoney() + " >> \n";
		
		// 문자열 형식으로 반환
		return date + memo + money + "<< 잔액 : " + balance + "\n";
	}
	
	// 지출 내역 출력하는 함수
	public String ToStringOut(int i) throws Exception {
		// 해당 객체가 존재하지 않을 때 예외처리
		if(infoOutcome == null) {
			throw new Exception("배열에 값이 존재하지 않습니다.");
		}
		
		String date = "<< 날짜 : " + infoOutcome[i].GetDate().get(GregorianCalendar.YEAR) + "." 
				+ (infoOutcome[i].GetDate().get(GregorianCalendar.MONTH)+1) + "."
				+ infoOutcome[i].GetDate().get(GregorianCalendar.DAY_OF_MONTH) + " >> \n";
	
		String memo = "<< 메모 : " + infoOutcome[i].getMemo() + " >> \n";
	
		String money = "<< 금액 : " + infoOutcome[i].getMoney() + " >> \n";
	
		// 문자열 형식으로 반환
		return date + memo + money + "<< 잔액 : " + balance + "\n";
	}
	
	// 수입 검색
	public Activity[] searchIn(GregorianCalendar cal) throws Exception{
		// 원하고자 하는 검색객체들을 담을 객체
		Activity[] searchArr = new Activity[inIndex];
		
		int count=0;

		// 원하는 값이 존재하지 않을 때 예외처리 하기
		// 객체 담기
		for(int i=0; i<inIndex; i++) {
			if(infoIncome[i].getCal().equals(cal)){
				searchArr[count] = infoIncome[i];
				count++;
			}
		}

		// 찾고자한게 없다면
		if(searchArr[count] == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}else{
			// 찾고자한 내용들을 담은 객체 반환
			return searchArr;
		}
	}
	
	// 수입 일정 기간 검색 재정의
	public Activity[] searchIn(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		// 원하고자 하는 검색 객체들을 담을 객체
		Activity[] searchArr = new Activity[inIndex];
			
		int count =0;
			
		// 객체 담기
		for(int i=0; i<inIndex; i++) {
			if((infoIncome[i].getCal().compareTo(fromDay)>=0)&&(toDay.compareTo(infoIncome[i].getCal())>=0)) { 
				searchArr[count] = infoIncome[i];
				count++;
			}
		}
		
		// 찾고자한게 없다면
		if(searchArr[count] == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}else{
			// 찾고자한 내용들을 담은 객체 반환
			return searchArr;
		}
	}
		
	// 지출 검색
	public Activity[] searchOut(GregorianCalendar cal) throws Exception{
		// 원하고자 하는 검색 객체들을 담을 객체
		Activity[] searchArr = new Activity[outIndex];
		
		int count=0;
		
		// 객체 담기
		for(int i=0; i<outIndex; i++) {
			if(infoOutcome[i].getCal().equals(cal)) {
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}
		
		// 찾고자한게 없다면
		if(searchArr[count] == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}else{
			// 찾고자한 내용들을 담은 객체 반환
			return searchArr;
		}
	}
	
	// 지출 일정 기간 검색 재정의
	public Activity[] searchOut(GregorianCalendar fromDay, GregorianCalendar toDay) throws Exception{
		Activity[] searchArr = new Activity[outIndex];
			
		int count =0;
			
		for(int i=0; i<outIndex; i++) {
			if((infoOutcome[i].getCal().compareTo(fromDay)>=0)&&(toDay.compareTo(infoOutcome[i].GetDate())>=0)) { 
				searchArr[count] = infoOutcome[i];
				count++;
			}
		}
		
		// 찾고자한게 없다면
		if(searchArr[count] == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}else{
			// 찾고자한 내용들을 담은 객체 반환
			return searchArr;
		}
	}
	
	// 수입 항목 년, 월, 일 받아서 내역 삭제
	public void deleteIn(GregorianCalendar cal, int num)throws Exception {
		
		// 객체가 존재하지 않을 떄 예외처리
		if(infoIncome == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}
		
 		for(int i = 0; i < inIndex; i++) {
 			// 반복중인 인덱스 번호하고 삭제하고자 한 내역을 입력한 값과 동일하다면
 			if(infoIncome[i] == infoIncome[num]) {
 				// 그 값의 돈만큼 잔액에서 빼주고
 				balance -= infoIncome[num].getMoney();
 				// 반복문을 통해 삭제된 인덱스의 위치 만큼 배열 위치 변환
 				for(int k = num; k < inIndex; k++) {
					infoIncome[k] = infoIncome[k+1];
				}
 				// 일이 끝나면 수입 인덱스 1줄여주기
				inIndex--;
				// 객체별 각 고유의 값을 정렬시켜주기
				updateActivityNum(true);
				break;
 				
 			}
 		}
 	}
	
	// 지출 항목, 년, 월, 일 받아서 내역 삭제
	public void deleteOut(GregorianCalendar cal, int num) throws Exception{
		// 객체가 존재하지 않을 떄 예외처리
		if(infoOutcome == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}
		
		// 수입 내용과 동일
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
	
	// 수입 항목 수정
	public void updateIn(GregorianCalendar cal, int num, int money,String memo) throws Exception{
		// 객체가 존재하지 않을 떄 예외처리
		if(infoIncome == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}
		
		// 수입 인덱스 크기만큼 반복
		for(int i = 0; i< inIndex; i++) {
			// 수정하고자 하는 내역과 해당 인덱스 내역과 동일할 때
 			if(infoIncome[i] == infoIncome[num]) {
 				// 수정 전의 수입금 만큼 ㅏ잔액에 빼주고
 				balance -= infoIncome[num].getMoney();
 				// 입력한 수입금 만큼 객체가 갖고있는 수입금 변경
 				infoIncome[num].setMoney(money);
 				// 수정 후의 수입금 만큼 잔액에 더해주고
 				balance += infoIncome[num].getMoney();
 				// 수정한 메모대로 객체의 내역 수정
 				infoIncome[num].setMemo(memo);
 				break;
 			}
 		}

 	}

	// 지출 항목 수정
	public void updateOut(GregorianCalendar cal, int num, int money,String memo) throws Exception{
		if(infoOutcome == null) {
			throw new Exception("값이 존재하지 않습니다.");
		}
		
		// 수익 항목 수정과 동일
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
	
	// Activity 객체의 번호를 올바르게 저장하기 위한 것
	public void updateActivityNum(Boolean isIn) {
		if(isIn) { // 수입 정렬
			for(int i = 0; i < inIndex; i++) {
				infoIncome[i].SetNum(i);
			}
		}else {	// 지출 정렬
			for(int i = 0; i < outIndex; i++) {
				infoOutcome[i].SetNum(i);
			}
		}
	}
	
	// 0918 DataOutputStream을 이용하여 파일 저장
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

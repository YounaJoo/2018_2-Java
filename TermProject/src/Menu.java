import java.util.GregorianCalendar;

/** 메뉴 인터페이스 */
public class Menu {

	public static void menu() {
		println("--------**가게부 프로그래밍**--------");
		println("1. 수입 내용 입력");
		println("2. 지출 내용 입력");
		println("3. 전체 내역 출력");
		println("4. 수입 내용 삭제");
		println("5. 지출 내용 삭제");
		println("6. 내용 찾기");
		println("7. 내용 수정");
		println("8. 데이터 파일에 저장");
		println("9. 프로그램 종료");
	}
	
	// System.out.println 함수
	private static void println(String txt) {
		System.out.println(txt);
	}
}

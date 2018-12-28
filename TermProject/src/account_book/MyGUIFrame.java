package account_book;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import account_book.MyGUIFrame.ClientsTableButton;
import account_book.MyGUIFrame.ClientsTableRenderer;
import account_book.MyGUIFrame.ClientsTableRendererUpdate;

public class MyGUIFrame extends JFrame implements Cloneable{
	
	// 데이터베이스 연결을 위한 JDBC 클래스 생성
	static JDBC_AccountBook jdbc = null;
	
	// 삽입, 검색, 저장 부분을 구현할 판넬
	private JPanel panel1;
	// 가계부 내역들을 구현할 판넬
	private JPanel panel2;
	
	// 테이블에 사용할 테스트 오브젝트 배열 생성
	private ArrayList<Object[]> objectIn = new ArrayList<Object[]>();
	private ArrayList<Object[]> objectOut = new ArrayList<Object[]>();
	
	// Label
	private Label inLabel;
	private Label outLabel;
	
	// JText
	private Label balance;
	
	// Button
	private JButton search;
	private JButton insertIn;
	private JButton insertOut;
	private JButton save;

	// 테이블 변수
	private JTable inTable = null;
	private JTable outTable = null;
	
	/*private int indexIn = 0;
	private int indexOut = 0;*/
	
	/*public void setTabel(JTable t) {
		this.inTable = t;
	}*/
	
	/** 생성자 함수 */
	public MyGUIFrame() {
		// 프레임 크기, 타이틀, 종료버튼 만들기
		this.setSize(600, 680);
		this.setTitle("가계부");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 종료 버튼을 눌렀을 시,
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
				// 데이터베이스 연결 끊기
		       jdbc.closeDB();
		    }
		});
		
		// 판넬 선언, 크기 설정, 레이아웃 설정
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(500, 200));
		panel1.setLayout(new FlowLayout());
		
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(500, 480));
		panel2.setLayout(new FlowLayout());
		
		// 해당하는 판넬 속 GUI 생성
		createPanel1();
		createPanel2();
	}
	
	// 수입, 지출, 검색 버튼이 있는 판넬 GUI 생성
	private void createPanel1() {
		// 절대값으로 위치 설정하기 위해  null 값 넣어주기
		panel1.setLayout(null);
		// 테스트를 위해 판넬 백그라운드의 컬러를 변경해준다.
		panel1.setBackground(Color.pink);
		
		// 검색 버튼 초기화
		search = new JButton("검색");
		search.setBounds(370, 60, 100, 50);
		// 리스너 연결
		search.addActionListener(new MyListener());
		panel1.add(search);
		
		// 수입 버튼 초기화
		insertIn = new JButton("수입 입력");
		insertIn.setBounds(90, 60, 100, 50);
		// 리스너 연결
		insertIn.addActionListener(new MyListener());
		panel1.add(insertIn);
		
		// 지출 버튼 초기화
		insertOut = new JButton("지출 입력");
		insertOut.setBounds(230, 60, 100, 50);
		// 리스너 연결
		insertOut.addActionListener(new MyListener());
		panel1.add(insertOut);
		
		// 해당 메인 프레임에 판넬 추가
		this.add(panel1, BorderLayout.NORTH);
	}
	
	// 수입, 지출 테이블을 보여주는 판넬 GUI 생성
	private void createPanel2() {
		panel2.setBackground(Color.gray);
		panel2.setLayout(null);
		
		// 수입, 지출 데이터에 현재 존재하는 데이터 초기화 하여 화면에 뿌려주기
        initTable();
		
		// JScrollPane를 추가, 크기 설정
        // 수입 테이블
		Label income = new Label("  수입");
		income.setBounds(10, 10, 40, 30);
		income.setBackground(Color.WHITE);
		panel2.add(income);
		
        JScrollPane sc = new JScrollPane(inTable);
        sc.setPreferredSize(new Dimension());
        sc.setBounds(10, 45, 560, 150);

        // 지출 테이블
        Label outcome = new Label("  지출");
        outcome.setBounds(10, 205, 40, 30);
        outcome.setBackground(Color.WHITE);
		panel2.add(outcome);
        
		JScrollPane sc2 = new JScrollPane(outTable);
        sc2.setPreferredSize(new Dimension());
        sc2.setBounds(10, 240, 560, 150);
        
        // 잔액 부분 추기하기
        int getBalance = jdbc.getBalance();
        balance = new Label("   잔액 :  " + getBalance);
        balance.setBackground(Color.WHITE);
        balance.setBounds(10, 400, 150, 30);
                
        //컴포넌트에  Table 추가
        panel2.add(sc);
        panel2.add(sc2);
        panel2.add(balance);
        
        // 해당 프레임에 PANEL2 추가
		this.add(panel2);
	}
	
	// 처음 프레임 생성 시, DB에 있는 데이터 불러다가 초기화 하는 함수
	public void initTable() {
		// 2차원 OBJECT 변수 선언
		Object[][] objin = jdbc.selectIncomeAll();
		Object[][] objout = jdbc.selectOutcomeAll();
		
		// JTABLE 열 이름 배열 생성
		String []textIn = {"년 | ", "수입금 | ", "메모 | ", "카테고리| ", "삭제 | ", "수정 |"};
		String []textOut = {"년 | ", "지출금 | ", "메모 | ", "카테고리|" ,"삭제 | ", "수정 |"};
		
		//모델과 데이터를 연결
        DefaultTableModel model1 = new DefaultTableModel(objin, textIn){
        	public boolean isCellEditable(int rowIndex, int mColIndex) {
        		// 4번째 열(삭제)과 5번째 열(수정)을 제외하고 테이블 내 값 변경되지 않도록 막아두기
        		return (mColIndex == 4 || mColIndex == 5);
        	}
        };
        DefaultTableModel model2 = new DefaultTableModel(objout, textOut){
        	public boolean isCellEditable(int rowIndex, int mColIndex) {
        		return (mColIndex == 4 || mColIndex == 5);
        	}
        };
        
        // 생성한 데이터보델에 따른 JTABLE 객체 생성
        inTable = new JTable(model1);
        outTable = new JTable(model2);
        
        // JTABLE HEADER 초기화
        inTable.getTableHeader().setReorderingAllowed(false); inTable.getTableHeader().setResizingAllowed(false);
        outTable.getTableHeader().setReorderingAllowed(false); outTable.getTableHeader().setResizingAllowed(false);
        
        // 입력 테이블의 4번째(삭제), 5번째(수정) 데이터를 버튼으로 변경해 주고, 버튼 클릭 시 새로운 클래스 호출하여 원하는 형태로 수정할 수 있도록 에디터 호출
        inTable.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
        inTable.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("1")));
        inTable.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
        inTable.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("1")));
        // 지출 테이블의 4번째(삭제), 5번째(수정) 데이터를 버튼으로 변경해 주고, 버튼 클릭 시 새로운 클래스 호출하여 원하는 형태로 수정할 수 있도록 에디터 호출
        outTable.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
        outTable.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("2")));
        outTable.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
        outTable.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("2")));
	}
	
	// 수입, 지출 데이터 추가 시 테이블 업데이트 하는 함수
	public void updateTable(int index, Object []obj) {
		try {
			if(index == 0) { // 수입 부분이라면
				// 수입 테이블의 모델을 불러와 새로운 모델 초기화
				DefaultTableModel model = (DefaultTableModel) inTable.getModel();
				
				// 해당하는 모델에 집어넣은 값을 넣어준다.
				model.addRow(obj);
				
				// 새로운 모델로 바꿔주고 UI 재뿌려주기
				inTable.setModel(model);
				inTable.repaint();
				
			}else if(index == 1) { // 지출 부분이라면
				DefaultTableModel model = (DefaultTableModel) outTable.getModel();
				
				model.addRow(obj);
				
				outTable.setModel(model);
				outTable.repaint();
			}
		// 잔액 부분 재구성
		int getBalance = jdbc.getBalance();
	    balance.setText("   잔액 :  " + getBalance);
	    
		}catch(IndexOutOfBoundsException ino) {
			ino.printStackTrace();
		}catch(NullPointerException np) {
			np.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// 전체 테이블 업데이트 하는 함수
	public void updateTable() {
		if(inTable.getRowCount() == 0) {	// 입력 테이블의 행이 0개라면
			DefaultTableModel model = (DefaultTableModel) inTable.getModel();
			// JDBC을 통해 수입 DB 값들을 전부 불러 온다.
			Object [][]obj = jdbc.selectIncomeAll();
			
			// 불러온 데이터의 양만큼 테이블에 ROW를 추가한다.
			for(int i = 0; i < obj.length; i++) {
				model.insertRow(i, obj[i]);
			}
            
			inTable.setModel(model);
			inTable.repaint();
			inTable.updateUI();
		}
		if(outTable.getRowCount() == 0) { // 지출 테이블의 행이 0개라면
			DefaultTableModel model = (DefaultTableModel) outTable.getModel();
			Object [][]obj = jdbc.selectOutcomeAll();
			
			for(int i = 0; i < obj.length; i++) {
				model.insertRow(i, obj[i]);
			}
            
			outTable.setModel(model);
			outTable.repaint();
			outTable.updateUI();
		}
		// 잔액 재초기화
		int getBalance = jdbc.getBalance();
        balance.setText("   잔액 :  " + getBalance);
	}

	// 수입, 지출 테이블 내 데이터 전부 삭제하기
	public void deleteTable() {
		if(!(inTable.getRowCount() == 0)) { // 수입 테이블의 ROW가 0개가 아니라면
			// 수입테이블의 모델을 불러와 새로운 모델 초기화
			DefaultTableModel model = (DefaultTableModel) inTable.getModel();

			// 불러온 모델에 있는 모든 데이터 값을 삭제하고
			model.getDataVector().removeAllElements();
			
			// 삭제한 모습을 다시 재뿌려준다.
			inTable.setModel(model);
			inTable.repaint();
			inTable.updateUI();
		}
		if(!(outTable.getRowCount() == 0)) { // 지출 테이블의 ROW가 0개가 아니라면
			DefaultTableModel model = (DefaultTableModel) outTable.getModel();

			model.getDataVector().removeAllElements();
			
			outTable.setModel(model);
			outTable.repaint();
			outTable.updateUI();
		}
	}
	
	// 검색, 수입삽입, 지출삽입, 저장 버튼에 따른 리스너 생성
	private class MyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == search){ // 검색 버튼이면
				try {
					// 검색 내부 클래스 생성
					SearchFrame searchF = new SearchFrame();
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == insertIn){ // 수입 버튼 이라면
				try {
					// 입력 내부 클래스 생성
					InsertFrame insertIn = new InsertFrame(true);
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == insertOut) { // 지출 버튼이라면
				try {
					// 지출 내부 클래스 생성
					InsertFrame insertIn = new InsertFrame(false);
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	// 검색 내부 클래스 생성
	public class SearchFrame extends JFrame{
		// JSWING 변수 선언
		private JButton search;
		
		private JPanel panel1;
		private JPanel panel2;
		
		private JTextField year_t1;
		private JTextField month_t1;
		private JTextField day_t1;

		private JTextField year_t2;
		private JTextField month_t2;
		private JTextField day_t2;
		
		private JCheckBox isSearch;
		
		private JPanel panel;
		
		private JTable selectInTable;
		private JTable selectOutTable;
		
		// 생성자 함수를 통해 새로운 프레임을 생성한다.
		public SearchFrame(){
			// 크기, 타이틀, 종료 버튼 생성
			this.setSize(300, 300);
			this.setTitle("검색");
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					// 종료 버튼 시, 해당 프레임의 모습을 보이지 않게 하고 프레임 파괴
					setVisible(false); 
					dispose();
	            }
			});
			
			// 판넬 객체 생성
			panel1 = new JPanel();
			panel2 = new JPanel();
			
			// 검색 형태를 변경하기 위한 체크 박스 생성
			isSearch = new JCheckBox("검색 형태 변형");
			isSearch.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					// 만약 CHEKBOX가 체크되었을 경우,
					if(e.getStateChange() == 1) {
						// 날짜 범위로 검색하는 판넬로 변경
						createPanel2();
					}else { // 체크가 되어있지 않으면
						// 하루 날짜로 검색하는 판넬로 변경, 현재 이것이 디폴트이다.
						createPanel1();
					}
				}
			});
			// 디폴트 -> 하루 날짜로 검색하는 판넬
			createPanel1();
			
			this.add(isSearch, BorderLayout.NORTH);
			this.setVisible(true);
		}
		
		// 하루 날짜로 검색하는 판넬
		public void createPanel1() {
			// 만약 범위 날짜 검색 판넬이 보여지는 상태라면
			if(panel2.isVisible()) {
				// 범위 검색 판넬을 지운다.
				this.remove(panel2);
				this.repaint();
			}
			// 판넬 값 설정
			panel1.setLayout(new FlowLayout());
			panel1.setBackground(Color.pink);
			panel1.setLayout(null);
			
			// 년도를 입력할 텍스트 상자를 보여주기 위한 라벨
			Label year = new Label("년 | ");
			year.setBounds(10, 70, 30, 50);
			panel1.add(year);
			// 년도를 입력할 텍스트 상자
			year_t1 = new JTextField();
			year_t1.setBounds(45, 70, 50, 35);
			panel1.add(year_t1);
			
			// 월을 입력할 텍스트 상자를 보여주기 위한 라벨
			Label month = new Label("월 | ");
			month.setBounds(100, 70, 30, 50);
			panel1.add(month);
			month_t1 = new JTextField();
			month_t1.setBounds(135, 70, 50, 35);
			panel1.add(month_t1);
			
			// 날을 입력할 텍스트 상자를 보여주기 위한 라벨
			Label day = new Label("일 | ");
			day.setBounds(190, 70, 30, 50);
			panel1.add(day);
			day_t1 = new JTextField();
			day_t1.setBounds(225, 70, 50, 35);
			panel1.add(day_t1);
			
			// 검색 버튼
			search = new JButton("검색");
			search.setBounds(110, 180, 60, 50);
			search.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) { // 검색 버튼을 눌렀을 떄 호출
					// 만약 년, 월, 일 중 하나의 텍스트에도 입력하지 않았을 경우 날짜를 입력하라는 경고창 뜨기
					if(year_t1.getText().isEmpty() || month_t1.getText().isEmpty()
							|| day_t1.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "날짜를 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else{ // 제대로 입력했을 경우
						String _date = year_t1.getText() +"." + month_t1.getText() + "." + day_t1.getText();
						// 입력한 값에 대한 데이터 불러오는 함수 호출
						searchTable(_date);
					}
				}
			});
			panel1.add(search);
			
			this.add(panel1);
			this.revalidate();
		}
		
		// 날짜 범위 판넬
		public void createPanel2() {
			if(panel1.isVisible()) { // 만약 하루만 검색하는 판넬이 보여지는 경우 그 판넬을 지운다.
				this.remove(panel1);
				this.repaint();
			}
			panel2.setLayout(new FlowLayout());
			panel2.setBackground(Color.WHITE);
			panel2.setLayout(null);
			
			// 범위 1 년도를 입력할 텍스트 상자를 보여주기 위한 라벨
			Label year = new Label("년 | ");
			year.setBounds(10, 30, 30, 50);
			panel2.add(year);
			// 범위 1 년도를 입력할 텍스트 상자
			year_t1 = new JTextField();
			year_t1.setBounds(45, 30, 50, 35);
			panel2.add(year_t1);
					
			Label month = new Label("월 | ");
			month.setBounds(100, 30, 30, 50);
			panel2.add(month);
			month_t1 = new JTextField();
			month_t1.setBounds(135, 30, 50, 35);
			panel2.add(month_t1);
			
			Label day = new Label("일 | ");
			day.setBounds(190, 30, 30, 50);
			panel2.add(day);
			day_t1 = new JTextField();
			day_t1.setBounds(225, 30, 50, 35);
			panel2.add(day_t1);
			
			Label label = new Label("~");
			label.setBounds(145, 80, 10, 20);
			panel2.add(label);
			
			// 범위 2 년도를 입력할 텍스트 상자를 보여주기 위한 라벨
			Label year2 = new Label("년 | ");
			year2.setBounds(10, 110, 30, 50);
			panel2.add(year2);
			
			// 범위 2 년도를 입력할 텍스트 상자
			year_t2 = new JTextField();
			year_t2.setBounds(45, 110, 50, 35);
			panel2.add(year_t2);
							
			Label month2 = new Label("월 | ");
			month2.setBounds(100, 110, 30, 50);
			panel2.add(month2);
			month_t2 = new JTextField();
			month_t2.setBounds(135,110, 50, 35);
			panel2.add(month_t2);
					
			Label day2 = new Label("일 | ");
			day2.setBounds(190, 110, 30, 50);
			panel2.add(day2);
			day_t2 = new JTextField();
			day_t2.setBounds(225, 110, 50, 35);
			panel2.add(day_t2);
				
			// 검색 버튼 생성
			search = new JButton("검색");
			search.setBounds(110, 180, 60, 50);
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { // 버튼 클릭 시
					// 범위 1과 2 중 한 곳이라도 년, 월, 일이 제대로 입력되지 않았을 경우 다시 입력하라는 경고 팝업창 띄우기
					if(year_t1.getText().isEmpty() || month_t1.getText().isEmpty()
							|| day_t1.getText().isEmpty() || year_t2.getText().isEmpty() 
							|| month_t2.getText().isEmpty() || day_t2.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "날짜를 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else{ // 제대로 값이 집어넣었을 경우
						String _date1 = year_t1.getText() +"." + month_t1.getText() + "." + day_t1.getText();
						String _date2 = year_t2.getText() +"." + month_t2.getText() + "." + day_t2.getText();
						// 해당 값들을 아규먼트로 넘겨받는 함수 호출
						SearchTable(true, _date1, _date2);
					}
				}
			});
			panel2.add(search);
			
			this.add(panel2);
			this.revalidate();
		}
		
		// 검색된 데이터들을 가지고 있는 테이블 프레임을 생성하기 위한 함수
		void CreateSelectFrame(JTable t1, JTable t2) {
			// 새로운 프레임 생성 및 초기화
			JFrame f = new JFrame();
			f.setSize(600, 400);	
			f.setTitle("가계부 검색");
			
			// 검색 되었을 시, 검색하기 위한 텍스트 필드 값 넣지 못하게 닫기
			year_t1.setEnabled(false);
			month_t1.setEnabled(false);
			day_t1.setEnabled(false);
			
			if(year_t2 != null && month_t2 != null && day_t2 == null)
			{
				year_t2.setEnabled(false);
				month_t2.setEnabled(false);
				day_t2.setEnabled(false);
			}
			
			panel = new JPanel();
			f.add(panel);
			
			panel.setBackground(Color.YELLOW);
			panel.setLayout(null);
			
			// 테이블 생성 및 초기화
			JScrollPane sc = new JScrollPane(selectInTable);
	        sc.setPreferredSize(new Dimension());
	        sc.setBounds(10, 10, 560, 150);
	        
	        JScrollPane sc2 = new JScrollPane(selectOutTable);
	        sc2.setPreferredSize(new Dimension());
	        sc2.setBounds(10, 180, 560, 150);
	        
	        panel.add(sc);
	        panel.add(sc2);
	        try {
	        	// 삭제, 변경을 하기 위해 4번, 5번 열값들을 버튼으로 바꿔주고 해당하는 에디터를 호출한다.
	            t1.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
	            t1.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("3")));
	            t1.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
	            t1.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("3")));
	            
	            t2.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
	            t2.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("4")));
	            t2.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
	            t2.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("4")));
	            
	        }catch(ArrayIndexOutOfBoundsException ioe) {
	        	
	        }catch(Exception e) {
	        	
	        }
	        // 위 프레임을 닫았을 시,
	        f.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	            	// 년, 월, 일 텍스트 필드 활성화
	        		year_t1.setEnabled(true);
					month_t1.setEnabled(true);
					day_t1.setEnabled(true);
					if(year_t2 != null && month_t2 != null && day_t2 == null) {
						year_t2.setEnabled(true);
						month_t2.setEnabled(true);
						day_t2.setEnabled(true);
					}
	            }
	        });
	        
	        f.setVisible(true);
		}
		
		// 하루에 해당하는 데이터를 불러오기 위한 함수
		public void searchTable(String _date) {			
	        try {
	        	// 데이터 db로부터 호출
	        	Object[][] objin = jdbc.SearchIn(_date);
	        	Object[][] objout = jdbc.SearchOut(_date);
	        	
	    		String []textIn = {"년 | ", "수입금 | ", "메모 | ", "카테고리| ", "삭제 | ", "수정 |"};
	    		String []textOut = {"년 | ", "지출금 | ", "메모 | ", "카테고리|" ,"삭제 | ", "수정 |"};
	    		
	    		//모델과 데이터를 연결
	    		DefaultTableModel model1 = new DefaultTableModel(objin, textIn){
	            	public boolean isCellEditable(int rowIndex, int mColIndex) {
	            		return (mColIndex == 4 || mColIndex == 5);
	            	}
	            };
	            DefaultTableModel model2 = new DefaultTableModel(objout, textOut){
	            	public boolean isCellEditable(int rowIndex, int mColIndex) {
	            		return (mColIndex == 4 || mColIndex == 5);
	            	}
	            };
	            
	            selectInTable = new JTable(model1);
	            selectOutTable = new JTable(model2);
	            
	            selectInTable.getTableHeader().setReorderingAllowed(false); selectInTable.getTableHeader().setResizingAllowed(false);
	            selectOutTable.getTableHeader().setReorderingAllowed(false); selectOutTable.getTableHeader().setResizingAllowed(false);
	            
	            // 프레임 생성 함수 호출
	            CreateSelectFrame(selectInTable, selectOutTable);
	            
	        }catch (NullPointerException np) {
	        	np.printStackTrace();
			}catch(IndexOutOfBoundsException io) {
				io.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 범위 날짜 검색을 하기 위해 데이터를 불러오는 함수
		public void SearchTable(boolean type, String _date1, String _date2) {
			if(type) {
				Object[][] objin = jdbc.SearchIn(_date1, _date2);
				Object[][] objout = jdbc.SearchOut(_date1, _date2);
				
				String []textIn = {"년 | ", "수입금 | ", "메모 | ", "카테고리| ", "삭제 | ", "수정 |"};
				String []textOut = {"년 | ", "지출금 | ", "메모 | ", "카테고리|" ,"삭제 | ", "수정 |"};
				
				//모델과 데이터를 연결
				DefaultTableModel model1 = new DefaultTableModel(objin, textIn){
		        	public boolean isCellEditable(int rowIndex, int mColIndex) {
		        		return (mColIndex == 4 || mColIndex == 5);
		        	}
		        };
		        DefaultTableModel model2 = new DefaultTableModel(objout, textOut){
		        	public boolean isCellEditable(int rowIndex, int mColIndex) {
		        		return (mColIndex == 4 || mColIndex == 5);
		        		
		        	}
		        }; 

		        selectInTable = new JTable(model1);
		        selectOutTable = new JTable(model2);
			       
		        selectInTable.getTableHeader().setReorderingAllowed(false); selectInTable.getTableHeader().setResizingAllowed(false);
		        selectOutTable.getTableHeader().setReorderingAllowed(false); selectOutTable.getTableHeader().setResizingAllowed(false);
		        
		        CreateSelectFrame(selectInTable, selectOutTable);
			}
		}
	}

	// 수입, 지출 부분을 담당하는 프레임 객체
	public class InsertFrame extends JFrame {
		// jswing 변수 선언 및 초기화
		private JButton ok;
		
		private JPanel panel;
		
		private JComboBox<String> tagList = null;
		
		private JTextField year_t;
		private JTextField month_t;
		private JTextField day_t;
		private JTextField memo_t;
		private JTextField money_t;
		
		public String tagIn[] = {"weekly revenue", "extra income", "pocket money", "etc."};
		public String tagOut[] = {"food expenses", "transportation expenses", "necessaries", "shopping", "hospital bills", "housing expenses", "postage", "culture/hobby", "etc."};
			
		// 생성자 함수
		public InsertFrame(Boolean inOut) {
			// 프레임 사이즈, 타이틀, 종료 버튼 초기화
			this.setSize(300, 300);
			this.setTitle("입력");
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					setVisible(false); 
					dispose();
	            }
			});
			
			// 프레임 속 데이터들 초기화
			panel = new JPanel();
			
			panel.setLayout(new FlowLayout());
			panel.setBackground(Color.pink);
			panel.setLayout(null);
			
			// 년도를 입력할 텍스트 상자를 보여주기 위한 라벨
			Label year = new Label("년 | ");
			year.setBounds(10, 30, 30, 50);
			panel.add(year);
			// 년도를 입력할 텍스트 상자
			year_t = new JTextField();
			year_t.setBounds(45, 30, 50, 35);
			panel.add(year_t);
					
			Label month = new Label("월 | ");
			month.setBounds(100, 30, 30, 50);
			panel.add(month);
			month_t = new JTextField();
			month_t.setBounds(135, 30, 50, 35);
			panel.add(month_t);
					
			Label day = new Label("일 | ");
			day.setBounds(190, 30, 30, 50);
			panel.add(day);
			day_t = new JTextField();
			day_t.setBounds(225, 30, 50, 35);
			panel.add(day_t);
			
			Label money = new Label("금액 | ");
			money.setBounds(10, 80, 35, 50);
			panel.add(money);
			money_t = new JTextField();
			money_t.setBounds(45, 80, 80, 35);
			panel.add(money_t);
			
			Label memo = new Label("메모| ");
			memo.setBounds(130, 80, 30, 50);
			panel.add(memo);
			memo_t = new JTextField();
			memo_t.setBounds(175, 80, 80, 35);
			panel.add(memo_t);
			
			Label category = new Label("카테고리| ");
			category.setBounds(10, 130, 50, 50);
			panel.add(category);

			if(inOut) { // 만약 수입 부분이라면
				tagList = new JComboBox<>(tagIn);
			}else { // 만약 지출 부분이라면
				tagList = new JComboBox<>(tagOut);
			}
			tagList.setBounds(70, 135, 150, 30);
			panel.add(tagList);
			
			// 입력 버튼 생성
			ok = new JButton("입력");
			ok.setBounds(110, 180, 60, 50);
			ok.addActionListener(new ActionListener() { // 입력 버튼 클릭 시 리스너 호출
				@Override
				public void actionPerformed(ActionEvent e) {
					String date = null;
					String money = null;
					String memo = null; 
					String category = null;
					
					String []textIn = {"년 | ", "수입금 | ", "메모 | ", "카테고리| ", "삭제 | ", "수정 |"};
					JTable table = null;
					
					try {
						// 날짜와 금액이 입력되지 않았다면 다시 입력하라는 경고 팝업창 생성
						if(year_t.getText().isEmpty() || month_t.getText().isEmpty()
							|| day_t.getText().isEmpty() || money_t.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "날짜와 금액을 입력해주세요!", "경고", JOptionPane.WARNING_MESSAGE);
						return;
					} else { // 제대로 입력했었으면 해당하는 값들을 String 데이터 타입으로 변환한다.
						date = year_t.getText() +"-" + month_t.getText() + "-" + day_t.getText();
						money = money_t.getText();
						category = tagList.getSelectedItem().toString();
						
						if(memo_t.getText().isEmpty()) {
							memo = null;
						}else {
							memo = memo_t.getText();
						}
						if(inOut) { //만약 입력 부분이라면
							// jdbc에 연결하여 입력한 데이터들을 db에 추가한다.
							jdbc.insertIncome(date, money, memo, category);
					        
							// 집어넣은 데이터를 다시 받아와서
							Object []obj = jdbc.selectInsertIn(date, money, memo, category);
							// 테이블 GUI에 업데이트 하기
							if(obj != null) {
								updateTable(0, obj);
							}else {
								return;
							}
						}else { // 지출 부분
							jdbc.insertOutcome(date, money, memo, category);
							
							Object []obj = jdbc.selectInsertOut(date, money, memo, category);
							if(obj != null)
								updateTable(1, obj);
							else
								return;
						}

						setVisible(false); 
						dispose();
					}
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}
				}
			});
			panel.add(ok);
			
			this.add(panel);
			this.setVisible(true);
		}
	}
	
	// 칼럼의 5번째 인덱스의 값을 버튼으로 변경 함수 
	public class ClientsTableButton extends JButton implements TableCellRenderer{
		// 생성자 함수
		public ClientsTableButton() {
			// TODO Auto-generated constructor stub
			setOpaque(true);
		}
		// JButton 상속받은 것의 오버로드
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			setForeground(Color.black);
			setBackground(UIManager.getColor("Button.background"));
			// 텍스트 값이 널이면 공백을 출력, 그게 아니라면 해당 열의 문자열을 받아온다
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
	
	// 칼럼의 5번째 인덱스 값을 클릭 시 이벤트(체크박스) 실행 함수
	public class ClientsTableRenderer extends DefaultCellEditor
	  {
	    private JButton button;
	    private String label;
	    private boolean clicked;
	    private int row, col;
	    private JTable table;
	    private String tt;

	    // 체크박스를 아규먼트로 받는 생성자 함수
	    public ClientsTableRenderer(JCheckBox checkBox)
	    {
	      super(checkBox);
	      tt = checkBox.getText();
	      button = new JButton();
	      button.setOpaque(true);
	      // 버튼의 새로운 리스너 생성
	      button.addActionListener(new ActionListener()
	      {
	        public void actionPerformed(ActionEvent e)
	        {
	        	try {
	        		fireEditingStopped();
	        	}catch(IndexOutOfBoundsException io) {
	        		
	        	}catch(Exception ef) {
	        		ef.printStackTrace();
	        	}
	        }
	      });
	    }
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	    {
	      this.table = table;
	      this.row = row;
	      this.col = column;
	      
	      button.setForeground(Color.black);
	      button.setBackground(UIManager.getColor("Button.background"));
	      label = (value == null) ? "" : value.toString();
	      button.setText(label);
	      clicked = true;
	      return button;
	    }
	    public Object getCellEditorValue()
	    {
	      if (clicked)
	      {
	    	int dialogResult = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제", JOptionPane.WARNING_MESSAGE);
	    	if(dialogResult == JOptionPane.YES_OPTION){
		    	try {
		    		Object dateValue = table.getValueAt(row, 0);
			    	String money = table.getValueAt(row, 1).toString();
			    	String memo = table.getValueAt(row, 2).toString();
			    	String date = dateValue.toString();
			    	String category = table.getValueAt(row, 3).toString();
			    	
			    	if(tt == "1") { // 수입 부분이라면
			    		jdbc.deleteInObject(date, money, memo, category);
			    	}else if(tt == "2"){ // 지출 부분이라면
			    		jdbc.deleteOutObject(date, money, memo, category);
			    	}else if(tt == "3") { // 검색 한 상태에서 수입 부분이라면
			    		jdbc.deleteInObject(date, money, memo, category);
			    		
			    		// 메인 프레임 속 ui 변경
			    		deleteTable();
			    		updateTable();
			    	}else { // 검색한 상태에서 지출 부분이라면
			    		jdbc.deleteOutObject(date, money, memo, category);
			    		
			    		deleteTable();
			    		updateTable();
			    	}
		    		
			    	// 버튼을 누른 테이블의 모델을 불러와 새로운 모델 데이터 형식 초기화하기
		    		DefaultTableModel m = (DefaultTableModel)table.getModel();
		    		// 버튼을 누른 누른 row를 삭제
		    		m.removeRow(table.getSelectedRow());
		    		
		    		// 잔액 재초기화
		    		int getBalance = jdbc.getBalance();
		            balance.setText("   잔액 :  " + getBalance);
		            
		            // 팝업창
		    		JOptionPane.showMessageDialog(null, "삭제에 성공하셨습니다!", "삭제", JOptionPane.QUESTION_MESSAGE);
		    		
		    		// ui 변경
		    		table.repaint();
		    	}catch(IndexOutOfBoundsException io) {
	    			io.printStackTrace();
	    		}catch(ConcurrentModificationException cc) {
	    			cc.printStackTrace();
	    		}catch(NullPointerException np) {
	    			np.printStackTrace();
	    		}catch(Exception e) {
	    			e.printStackTrace();
	    		}
		    }
	      }
	      clicked = false;
	      return new String(label);
	    }

	    public boolean stopCellEditing()
	    {
	      clicked = false;
	      return super.stopCellEditing();
	    }

	    protected void fireEditingStopped()
	    {
	      super.fireEditingStopped();
	    }
	  }

	public class ClientsTableRendererUpdate extends DefaultCellEditor
	  {
	    private JButton button;
	    private String label;
	    private boolean clicked;
	    private int row, col;
	    private JTable table;
	    private String tt;

	    // 체크박스를 아규먼트로 받는 생성자 함수
	    public ClientsTableRendererUpdate(JCheckBox checkBox)
	    {
	      super(checkBox);
	      tt = checkBox.getText();
	      button = new JButton();
	      button.setOpaque(true);
	      // 버튼의 새로운 리스너 생성
	      button.addActionListener(new ActionListener()
	      {
	        public void actionPerformed(ActionEvent e)
	        {
	        	try {
	        		fireEditingStopped();
	        	}catch(IndexOutOfBoundsException io) {
	        		
	        	}catch(Exception ef) {
	        		ef.printStackTrace();
	        	}
	        }
	      });
	    }
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	    {
	      this.table = table;
	      this.row = row;
	      this.col = column;

	      button.setForeground(Color.black);
	      button.setBackground(UIManager.getColor("Button.background"));
	      label = (value == null) ? "" : value.toString();
	      button.setText(label);
	      clicked = true;
	      return button;
	    }
	    public void SetEditorUpdate(String p_date, String p_money, String p_memo, String p_category) {
	    	JFrame f = new JFrame();
	    	f.setSize(300, 300);
	    	f.setTitle("수정 폼");
	    	
	    	f.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					// 수정 프레임 닫았을 때 해당하는 프레임만 닫도록 하기
					f.setVisible(false); 
					f.dispose();
	            }
			});
	    	JPanel panel = new JPanel();
			
	    	// 년, 월, 일으 '-'을 기준으로 텍스트 자르기
	    	String[] d = p_date.trim().split("-");
	    	
	    	panel.setLayout(new FlowLayout());
			panel.setBackground(Color.pink);
			panel.setLayout(null);
			
			// 년, 월, 일, 메모, 금액 라벨 및 데이터 필드 초기화 생성
			
			// 년도를 입력할 텍스트 상자를 보여주기 위한 라벨
			Label year = new Label("년 | ");
			year.setBounds(10, 30, 30, 50);
			panel.add(year);
			// 년도를 입력할 텍스트 상자
			JTextField year_t = new JTextField();
			year_t.setText(d[0]);
			year_t.setBounds(45, 30, 50, 35);
			panel.add(year_t);
					
			Label month = new Label("월 | ");
			month.setBounds(100, 30, 30, 50);
			panel.add(month);
			
			JTextField month_t = new JTextField();
			month_t.setText(d[1]);
			month_t.setBounds(135, 30, 50, 35);
			panel.add(month_t);
					
			Label day = new Label("일 | ");
			day.setBounds(190, 30, 30, 50);
			panel.add(day);
			
			JTextField day_t = new JTextField();
			day_t.setText(d[2]);
			day_t.setBounds(225, 30, 50, 35);
			panel.add(day_t);
			
			Label money = new Label("금액 | ");
			money.setBounds(10, 80, 35, 50);
			panel.add(money);
			
			JTextField money_t = new JTextField();
			money_t.setText(p_money);
			money_t.setBounds(45, 80, 80, 35);
			panel.add(money_t);
			
			Label memo = new Label("메모| ");
			memo.setBounds(130, 80, 30, 50);
			panel.add(memo);
			
			JTextField memo_t = new JTextField();
			memo_t.setText(p_memo);
			memo_t.setBounds(175, 80, 80, 35);
			panel.add(memo_t);
			
			Label category = new Label("카테고리| ");
			category.setBounds(10, 130, 50, 50);
			panel.add(category);

			// 카테고리 문자열 생성
	    	String tagIn[] = {"weekly revenue", "extra income", "pocket money", "etc."};
	    	String tagOut[] = {"food expenses", "transportation expenses", "necessaries", "shopping", 
	    			"hospital bills", "housing expenses", "postage", "culture/hobby", "etc."};
	    	// 카테고리를 넣을 콤보 박스 생성
	    	JComboBox<String> tagList = null;
	    	
	    	if(tt == "1") { // 수입 부분이라면
	    		// 수입 카테고리 문자열을 콤보박스에 연결
				tagList = new JComboBox<>(tagIn); 
			}else { // 지출
				tagList = new JComboBox<>(tagOut);
			}
	    	// 콤보박스 생성
			tagList.setBounds(70, 135, 150, 30);
			panel.add(tagList);
			
			try {
				// 해당하는 카테고리 값 문자열 변수 선언 후 집어넣기
    	    	String u_category = tagList.getSelectedItem().toString();
    	    	
    	    	// 입력 버튼 생성
    	    	JButton ok = new JButton("입력");
    			ok.setBounds(110, 180, 60, 50);
    			ok.addActionListener(new ActionListener() { // 입력 버튼 클릭 시 호출
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// yes or no 팝업 창 실행
						int dialogResult = JOptionPane.showConfirmDialog(null, "정말 이렇게 수정하시겠습니까?", "수정", JOptionPane.WARNING_MESSAGE);
						if(dialogResult == JOptionPane.YES_OPTION){ // yes 눌렀 을 시
							// 입력 한 변경 값들을 문자열 형태로 변경해준다.
			    	    	String u_date = year_t.getText() + "-" + month_t.getText() + "-" + day_t.getText();
			    	    	String u_money = money_t.getText();
			    	    	String u_memo = null;
			    	    	if(!memo_t.getText().isEmpty()) {
			    	    		u_memo = memo_t.getText();
			    	    	}
			    	    	
			    			if(tt == "1") { // 수입 부분이라면
			    				// 수입 부분 update하기
			        			jdbc.updateIn(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        		}else if(tt == "2") { // 지출 부분이라면
			        			jdbc.updateOut(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        		}else if(tt == "3") { // 검색한 상황에서 수입 부분이라면
			        			jdbc.updateIn(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        			
			        			// 검색 한 테이블의 모델을 불러온다.
			        			DefaultTableModel m = (DefaultTableModel)table.getModel();
			        			// 클릭한 row 삭제
			        			m.removeRow(table.getSelectedRow());
			        			
			        			// 변경 한 값들을 db에서 꺼내서
			        			Object[] obj = jdbc.selectInsertIn(u_date, u_money, u_memo, u_category);
			        			// 모델에 추가한다 -> gui 갱신
			        			m.addRow(obj);
			        			table.repaint();
			        		}else { // 검색한 상황에서 지출 부분이라면
			        			jdbc.updateOut(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        			
			        			DefaultTableModel m = (DefaultTableModel)table.getModel();
			        			m.removeRow(table.getSelectedRow());
			        			
			        			Object[] obj = jdbc.selectInsertOut(u_date, u_money, u_memo, u_category);
			        			m.addRow(obj);
			        			table.repaint();
			        		}
			    			// 수입 gui 테이블이 비어있지 않다면
			    			if(inTable != null) {
			    				// gui 갱신
			    				deleteTable();
			    				updateTable();
			    			}
			    			// 지출 gui 테이블이 비어있지 않으면
			    			if(outTable != null) {
			    				deleteTable();
			    				updateTable();
			    			}
							f.setVisible(false); 
							f.dispose();
						}
					}
    			});
    			panel.add(ok);
    			
    			f.add(panel);
    			f.setVisible(true);
                
	    	}catch(IndexOutOfBoundsException io) {
	    		io.printStackTrace();
	    	}catch(ConcurrentModificationException cc) {
	    		cc.printStackTrace();
	    	}catch(NullPointerException np) {
	    		np.printStackTrace();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    public Object getCellEditorValue()
	    {
	      if (clicked)
	      {
		    String p_date = table.getValueAt(row, 0).toString();
		    String p_money = table.getValueAt(row, 1).toString();
		    String p_memo = null;
		    String p_category = null;
		    if(!(table.getValueAt(row, 2) == null)) {
		    	p_memo = table.getValueAt(row, 2).toString();
		    }
		    if(!(table.getValueAt(row, 3) == null)) {
		    	p_category = table.getValueAt(row, 3).toString();
		    }
		    
		    SetEditorUpdate(p_date, p_money, p_memo, p_category);
		    
	      }
	      clicked = false;
	      return new String(label);
	    }

	    public boolean stopCellEditing()
	    {
	      clicked = false;
	      return super.stopCellEditing();
	    }

	    protected void fireEditingStopped()
	    {
	      super.fireEditingStopped();
	    }
	  }
	
	/** 메인함수 */
	public static void main(String[] args) {
		// jdbc 객체 생성
		jdbc = new JDBC_AccountBook();
		
		// db연결
		jdbc.connectDB();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGUIFrame frame = new MyGUIFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}


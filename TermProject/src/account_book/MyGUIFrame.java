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
	
	// �����ͺ��̽� ������ ���� JDBC Ŭ���� ����
	static JDBC_AccountBook jdbc = null;
	
	// ����, �˻�, ���� �κ��� ������ �ǳ�
	private JPanel panel1;
	// ����� �������� ������ �ǳ�
	private JPanel panel2;
	
	// ���̺� ����� �׽�Ʈ ������Ʈ �迭 ����
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

	// ���̺� ����
	private JTable inTable = null;
	private JTable outTable = null;
	
	/*private int indexIn = 0;
	private int indexOut = 0;*/
	
	/*public void setTabel(JTable t) {
		this.inTable = t;
	}*/
	
	/** ������ �Լ� */
	public MyGUIFrame() {
		// ������ ũ��, Ÿ��Ʋ, �����ư �����
		this.setSize(600, 680);
		this.setTitle("�����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���� ��ư�� ������ ��,
		this.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
				// �����ͺ��̽� ���� ����
		       jdbc.closeDB();
		    }
		});
		
		// �ǳ� ����, ũ�� ����, ���̾ƿ� ����
		panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(500, 200));
		panel1.setLayout(new FlowLayout());
		
		panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(500, 480));
		panel2.setLayout(new FlowLayout());
		
		// �ش��ϴ� �ǳ� �� GUI ����
		createPanel1();
		createPanel2();
	}
	
	// ����, ����, �˻� ��ư�� �ִ� �ǳ� GUI ����
	private void createPanel1() {
		// ���밪���� ��ġ �����ϱ� ����  null �� �־��ֱ�
		panel1.setLayout(null);
		// �׽�Ʈ�� ���� �ǳ� ��׶����� �÷��� �������ش�.
		panel1.setBackground(Color.pink);
		
		// �˻� ��ư �ʱ�ȭ
		search = new JButton("�˻�");
		search.setBounds(370, 60, 100, 50);
		// ������ ����
		search.addActionListener(new MyListener());
		panel1.add(search);
		
		// ���� ��ư �ʱ�ȭ
		insertIn = new JButton("���� �Է�");
		insertIn.setBounds(90, 60, 100, 50);
		// ������ ����
		insertIn.addActionListener(new MyListener());
		panel1.add(insertIn);
		
		// ���� ��ư �ʱ�ȭ
		insertOut = new JButton("���� �Է�");
		insertOut.setBounds(230, 60, 100, 50);
		// ������ ����
		insertOut.addActionListener(new MyListener());
		panel1.add(insertOut);
		
		// �ش� ���� �����ӿ� �ǳ� �߰�
		this.add(panel1, BorderLayout.NORTH);
	}
	
	// ����, ���� ���̺��� �����ִ� �ǳ� GUI ����
	private void createPanel2() {
		panel2.setBackground(Color.gray);
		panel2.setLayout(null);
		
		// ����, ���� �����Ϳ� ���� �����ϴ� ������ �ʱ�ȭ �Ͽ� ȭ�鿡 �ѷ��ֱ�
        initTable();
		
		// JScrollPane�� �߰�, ũ�� ����
        // ���� ���̺�
		Label income = new Label("  ����");
		income.setBounds(10, 10, 40, 30);
		income.setBackground(Color.WHITE);
		panel2.add(income);
		
        JScrollPane sc = new JScrollPane(inTable);
        sc.setPreferredSize(new Dimension());
        sc.setBounds(10, 45, 560, 150);

        // ���� ���̺�
        Label outcome = new Label("  ����");
        outcome.setBounds(10, 205, 40, 30);
        outcome.setBackground(Color.WHITE);
		panel2.add(outcome);
        
		JScrollPane sc2 = new JScrollPane(outTable);
        sc2.setPreferredSize(new Dimension());
        sc2.setBounds(10, 240, 560, 150);
        
        // �ܾ� �κ� �߱��ϱ�
        int getBalance = jdbc.getBalance();
        balance = new Label("   �ܾ� :  " + getBalance);
        balance.setBackground(Color.WHITE);
        balance.setBounds(10, 400, 150, 30);
                
        //������Ʈ��  Table �߰�
        panel2.add(sc);
        panel2.add(sc2);
        panel2.add(balance);
        
        // �ش� �����ӿ� PANEL2 �߰�
		this.add(panel2);
	}
	
	// ó�� ������ ���� ��, DB�� �ִ� ������ �ҷ��ٰ� �ʱ�ȭ �ϴ� �Լ�
	public void initTable() {
		// 2���� OBJECT ���� ����
		Object[][] objin = jdbc.selectIncomeAll();
		Object[][] objout = jdbc.selectOutcomeAll();
		
		// JTABLE �� �̸� �迭 ����
		String []textIn = {"�� | ", "���Ա� | ", "�޸� | ", "ī�װ�| ", "���� | ", "���� |"};
		String []textOut = {"�� | ", "����� | ", "�޸� | ", "ī�װ�|" ,"���� | ", "���� |"};
		
		//�𵨰� �����͸� ����
        DefaultTableModel model1 = new DefaultTableModel(objin, textIn){
        	public boolean isCellEditable(int rowIndex, int mColIndex) {
        		// 4��° ��(����)�� 5��° ��(����)�� �����ϰ� ���̺� �� �� ������� �ʵ��� ���Ƶα�
        		return (mColIndex == 4 || mColIndex == 5);
        	}
        };
        DefaultTableModel model2 = new DefaultTableModel(objout, textOut){
        	public boolean isCellEditable(int rowIndex, int mColIndex) {
        		return (mColIndex == 4 || mColIndex == 5);
        	}
        };
        
        // ������ �����ͺ����� ���� JTABLE ��ü ����
        inTable = new JTable(model1);
        outTable = new JTable(model2);
        
        // JTABLE HEADER �ʱ�ȭ
        inTable.getTableHeader().setReorderingAllowed(false); inTable.getTableHeader().setResizingAllowed(false);
        outTable.getTableHeader().setReorderingAllowed(false); outTable.getTableHeader().setResizingAllowed(false);
        
        // �Է� ���̺��� 4��°(����), 5��°(����) �����͸� ��ư���� ������ �ְ�, ��ư Ŭ�� �� ���ο� Ŭ���� ȣ���Ͽ� ���ϴ� ���·� ������ �� �ֵ��� ������ ȣ��
        inTable.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
        inTable.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("1")));
        inTable.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
        inTable.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("1")));
        // ���� ���̺��� 4��°(����), 5��°(����) �����͸� ��ư���� ������ �ְ�, ��ư Ŭ�� �� ���ο� Ŭ���� ȣ���Ͽ� ���ϴ� ���·� ������ �� �ֵ��� ������ ȣ��
        outTable.getColumnModel().getColumn(4).setCellRenderer(new ClientsTableButton());
        outTable.getColumnModel().getColumn(4).setCellEditor(new ClientsTableRenderer(new JCheckBox("2")));
        outTable.getColumnModel().getColumn(5).setCellRenderer(new ClientsTableButton());
        outTable.getColumnModel().getColumn(5).setCellEditor(new ClientsTableRendererUpdate(new JCheckBox("2")));
	}
	
	// ����, ���� ������ �߰� �� ���̺� ������Ʈ �ϴ� �Լ�
	public void updateTable(int index, Object []obj) {
		try {
			if(index == 0) { // ���� �κ��̶��
				// ���� ���̺��� ���� �ҷ��� ���ο� �� �ʱ�ȭ
				DefaultTableModel model = (DefaultTableModel) inTable.getModel();
				
				// �ش��ϴ� �𵨿� ������� ���� �־��ش�.
				model.addRow(obj);
				
				// ���ο� �𵨷� �ٲ��ְ� UI ��ѷ��ֱ�
				inTable.setModel(model);
				inTable.repaint();
				
			}else if(index == 1) { // ���� �κ��̶��
				DefaultTableModel model = (DefaultTableModel) outTable.getModel();
				
				model.addRow(obj);
				
				outTable.setModel(model);
				outTable.repaint();
			}
		// �ܾ� �κ� �籸��
		int getBalance = jdbc.getBalance();
	    balance.setText("   �ܾ� :  " + getBalance);
	    
		}catch(IndexOutOfBoundsException ino) {
			ino.printStackTrace();
		}catch(NullPointerException np) {
			np.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// ��ü ���̺� ������Ʈ �ϴ� �Լ�
	public void updateTable() {
		if(inTable.getRowCount() == 0) {	// �Է� ���̺��� ���� 0�����
			DefaultTableModel model = (DefaultTableModel) inTable.getModel();
			// JDBC�� ���� ���� DB ������ ���� �ҷ� �´�.
			Object [][]obj = jdbc.selectIncomeAll();
			
			// �ҷ��� �������� �縸ŭ ���̺� ROW�� �߰��Ѵ�.
			for(int i = 0; i < obj.length; i++) {
				model.insertRow(i, obj[i]);
			}
            
			inTable.setModel(model);
			inTable.repaint();
			inTable.updateUI();
		}
		if(outTable.getRowCount() == 0) { // ���� ���̺��� ���� 0�����
			DefaultTableModel model = (DefaultTableModel) outTable.getModel();
			Object [][]obj = jdbc.selectOutcomeAll();
			
			for(int i = 0; i < obj.length; i++) {
				model.insertRow(i, obj[i]);
			}
            
			outTable.setModel(model);
			outTable.repaint();
			outTable.updateUI();
		}
		// �ܾ� ���ʱ�ȭ
		int getBalance = jdbc.getBalance();
        balance.setText("   �ܾ� :  " + getBalance);
	}

	// ����, ���� ���̺� �� ������ ���� �����ϱ�
	public void deleteTable() {
		if(!(inTable.getRowCount() == 0)) { // ���� ���̺��� ROW�� 0���� �ƴ϶��
			// �������̺��� ���� �ҷ��� ���ο� �� �ʱ�ȭ
			DefaultTableModel model = (DefaultTableModel) inTable.getModel();

			// �ҷ��� �𵨿� �ִ� ��� ������ ���� �����ϰ�
			model.getDataVector().removeAllElements();
			
			// ������ ����� �ٽ� ��ѷ��ش�.
			inTable.setModel(model);
			inTable.repaint();
			inTable.updateUI();
		}
		if(!(outTable.getRowCount() == 0)) { // ���� ���̺��� ROW�� 0���� �ƴ϶��
			DefaultTableModel model = (DefaultTableModel) outTable.getModel();

			model.getDataVector().removeAllElements();
			
			outTable.setModel(model);
			outTable.repaint();
			outTable.updateUI();
		}
	}
	
	// �˻�, ���Ի���, �������, ���� ��ư�� ���� ������ ����
	private class MyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == search){ // �˻� ��ư�̸�
				try {
					// �˻� ���� Ŭ���� ����
					SearchFrame searchF = new SearchFrame();
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == insertIn){ // ���� ��ư �̶��
				try {
					// �Է� ���� Ŭ���� ����
					InsertFrame insertIn = new InsertFrame(true);
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource() == insertOut) { // ���� ��ư�̶��
				try {
					// ���� ���� Ŭ���� ����
					InsertFrame insertIn = new InsertFrame(false);
				}catch(NumberFormatException nf) {
					nf.printStackTrace();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	// �˻� ���� Ŭ���� ����
	public class SearchFrame extends JFrame{
		// JSWING ���� ����
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
		
		// ������ �Լ��� ���� ���ο� �������� �����Ѵ�.
		public SearchFrame(){
			// ũ��, Ÿ��Ʋ, ���� ��ư ����
			this.setSize(300, 300);
			this.setTitle("�˻�");
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					// ���� ��ư ��, �ش� �������� ����� ������ �ʰ� �ϰ� ������ �ı�
					setVisible(false); 
					dispose();
	            }
			});
			
			// �ǳ� ��ü ����
			panel1 = new JPanel();
			panel2 = new JPanel();
			
			// �˻� ���¸� �����ϱ� ���� üũ �ڽ� ����
			isSearch = new JCheckBox("�˻� ���� ����");
			isSearch.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					// ���� CHEKBOX�� üũ�Ǿ��� ���,
					if(e.getStateChange() == 1) {
						// ��¥ ������ �˻��ϴ� �ǳڷ� ����
						createPanel2();
					}else { // üũ�� �Ǿ����� ������
						// �Ϸ� ��¥�� �˻��ϴ� �ǳڷ� ����, ���� �̰��� ����Ʈ�̴�.
						createPanel1();
					}
				}
			});
			// ����Ʈ -> �Ϸ� ��¥�� �˻��ϴ� �ǳ�
			createPanel1();
			
			this.add(isSearch, BorderLayout.NORTH);
			this.setVisible(true);
		}
		
		// �Ϸ� ��¥�� �˻��ϴ� �ǳ�
		public void createPanel1() {
			// ���� ���� ��¥ �˻� �ǳ��� �������� ���¶��
			if(panel2.isVisible()) {
				// ���� �˻� �ǳ��� �����.
				this.remove(panel2);
				this.repaint();
			}
			// �ǳ� �� ����
			panel1.setLayout(new FlowLayout());
			panel1.setBackground(Color.pink);
			panel1.setLayout(null);
			
			// �⵵�� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label year = new Label("�� | ");
			year.setBounds(10, 70, 30, 50);
			panel1.add(year);
			// �⵵�� �Է��� �ؽ�Ʈ ����
			year_t1 = new JTextField();
			year_t1.setBounds(45, 70, 50, 35);
			panel1.add(year_t1);
			
			// ���� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label month = new Label("�� | ");
			month.setBounds(100, 70, 30, 50);
			panel1.add(month);
			month_t1 = new JTextField();
			month_t1.setBounds(135, 70, 50, 35);
			panel1.add(month_t1);
			
			// ���� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label day = new Label("�� | ");
			day.setBounds(190, 70, 30, 50);
			panel1.add(day);
			day_t1 = new JTextField();
			day_t1.setBounds(225, 70, 50, 35);
			panel1.add(day_t1);
			
			// �˻� ��ư
			search = new JButton("�˻�");
			search.setBounds(110, 180, 60, 50);
			search.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) { // �˻� ��ư�� ������ �� ȣ��
					// ���� ��, ��, �� �� �ϳ��� �ؽ�Ʈ���� �Է����� �ʾ��� ��� ��¥�� �Է��϶�� ���â �߱�
					if(year_t1.getText().isEmpty() || month_t1.getText().isEmpty()
							|| day_t1.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "��¥�� �Է����ּ���!", "���", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else{ // ����� �Է����� ���
						String _date = year_t1.getText() +"." + month_t1.getText() + "." + day_t1.getText();
						// �Է��� ���� ���� ������ �ҷ����� �Լ� ȣ��
						searchTable(_date);
					}
				}
			});
			panel1.add(search);
			
			this.add(panel1);
			this.revalidate();
		}
		
		// ��¥ ���� �ǳ�
		public void createPanel2() {
			if(panel1.isVisible()) { // ���� �Ϸ縸 �˻��ϴ� �ǳ��� �������� ��� �� �ǳ��� �����.
				this.remove(panel1);
				this.repaint();
			}
			panel2.setLayout(new FlowLayout());
			panel2.setBackground(Color.WHITE);
			panel2.setLayout(null);
			
			// ���� 1 �⵵�� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label year = new Label("�� | ");
			year.setBounds(10, 30, 30, 50);
			panel2.add(year);
			// ���� 1 �⵵�� �Է��� �ؽ�Ʈ ����
			year_t1 = new JTextField();
			year_t1.setBounds(45, 30, 50, 35);
			panel2.add(year_t1);
					
			Label month = new Label("�� | ");
			month.setBounds(100, 30, 30, 50);
			panel2.add(month);
			month_t1 = new JTextField();
			month_t1.setBounds(135, 30, 50, 35);
			panel2.add(month_t1);
			
			Label day = new Label("�� | ");
			day.setBounds(190, 30, 30, 50);
			panel2.add(day);
			day_t1 = new JTextField();
			day_t1.setBounds(225, 30, 50, 35);
			panel2.add(day_t1);
			
			Label label = new Label("~");
			label.setBounds(145, 80, 10, 20);
			panel2.add(label);
			
			// ���� 2 �⵵�� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label year2 = new Label("�� | ");
			year2.setBounds(10, 110, 30, 50);
			panel2.add(year2);
			
			// ���� 2 �⵵�� �Է��� �ؽ�Ʈ ����
			year_t2 = new JTextField();
			year_t2.setBounds(45, 110, 50, 35);
			panel2.add(year_t2);
							
			Label month2 = new Label("�� | ");
			month2.setBounds(100, 110, 30, 50);
			panel2.add(month2);
			month_t2 = new JTextField();
			month_t2.setBounds(135,110, 50, 35);
			panel2.add(month_t2);
					
			Label day2 = new Label("�� | ");
			day2.setBounds(190, 110, 30, 50);
			panel2.add(day2);
			day_t2 = new JTextField();
			day_t2.setBounds(225, 110, 50, 35);
			panel2.add(day_t2);
				
			// �˻� ��ư ����
			search = new JButton("�˻�");
			search.setBounds(110, 180, 60, 50);
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { // ��ư Ŭ�� ��
					// ���� 1�� 2 �� �� ���̶� ��, ��, ���� ����� �Էµ��� �ʾ��� ��� �ٽ� �Է��϶�� ��� �˾�â ����
					if(year_t1.getText().isEmpty() || month_t1.getText().isEmpty()
							|| day_t1.getText().isEmpty() || year_t2.getText().isEmpty() 
							|| month_t2.getText().isEmpty() || day_t2.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "��¥�� �Է����ּ���!", "���", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else{ // ����� ���� ����־��� ���
						String _date1 = year_t1.getText() +"." + month_t1.getText() + "." + day_t1.getText();
						String _date2 = year_t2.getText() +"." + month_t2.getText() + "." + day_t2.getText();
						// �ش� ������ �ƱԸ�Ʈ�� �Ѱܹ޴� �Լ� ȣ��
						SearchTable(true, _date1, _date2);
					}
				}
			});
			panel2.add(search);
			
			this.add(panel2);
			this.revalidate();
		}
		
		// �˻��� �����͵��� ������ �ִ� ���̺� �������� �����ϱ� ���� �Լ�
		void CreateSelectFrame(JTable t1, JTable t2) {
			// ���ο� ������ ���� �� �ʱ�ȭ
			JFrame f = new JFrame();
			f.setSize(600, 400);	
			f.setTitle("����� �˻�");
			
			// �˻� �Ǿ��� ��, �˻��ϱ� ���� �ؽ�Ʈ �ʵ� �� ���� ���ϰ� �ݱ�
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
			
			// ���̺� ���� �� �ʱ�ȭ
			JScrollPane sc = new JScrollPane(selectInTable);
	        sc.setPreferredSize(new Dimension());
	        sc.setBounds(10, 10, 560, 150);
	        
	        JScrollPane sc2 = new JScrollPane(selectOutTable);
	        sc2.setPreferredSize(new Dimension());
	        sc2.setBounds(10, 180, 560, 150);
	        
	        panel.add(sc);
	        panel.add(sc2);
	        try {
	        	// ����, ������ �ϱ� ���� 4��, 5�� �������� ��ư���� �ٲ��ְ� �ش��ϴ� �����͸� ȣ���Ѵ�.
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
	        // �� �������� �ݾ��� ��,
	        f.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	            	// ��, ��, �� �ؽ�Ʈ �ʵ� Ȱ��ȭ
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
		
		// �Ϸ翡 �ش��ϴ� �����͸� �ҷ����� ���� �Լ�
		public void searchTable(String _date) {			
	        try {
	        	// ������ db�κ��� ȣ��
	        	Object[][] objin = jdbc.SearchIn(_date);
	        	Object[][] objout = jdbc.SearchOut(_date);
	        	
	    		String []textIn = {"�� | ", "���Ա� | ", "�޸� | ", "ī�װ�| ", "���� | ", "���� |"};
	    		String []textOut = {"�� | ", "����� | ", "�޸� | ", "ī�װ�|" ,"���� | ", "���� |"};
	    		
	    		//�𵨰� �����͸� ����
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
	            
	            // ������ ���� �Լ� ȣ��
	            CreateSelectFrame(selectInTable, selectOutTable);
	            
	        }catch (NullPointerException np) {
	        	np.printStackTrace();
			}catch(IndexOutOfBoundsException io) {
				io.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// ���� ��¥ �˻��� �ϱ� ���� �����͸� �ҷ����� �Լ�
		public void SearchTable(boolean type, String _date1, String _date2) {
			if(type) {
				Object[][] objin = jdbc.SearchIn(_date1, _date2);
				Object[][] objout = jdbc.SearchOut(_date1, _date2);
				
				String []textIn = {"�� | ", "���Ա� | ", "�޸� | ", "ī�װ�| ", "���� | ", "���� |"};
				String []textOut = {"�� | ", "����� | ", "�޸� | ", "ī�װ�|" ,"���� | ", "���� |"};
				
				//�𵨰� �����͸� ����
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

	// ����, ���� �κ��� ����ϴ� ������ ��ü
	public class InsertFrame extends JFrame {
		// jswing ���� ���� �� �ʱ�ȭ
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
			
		// ������ �Լ�
		public InsertFrame(Boolean inOut) {
			// ������ ������, Ÿ��Ʋ, ���� ��ư �ʱ�ȭ
			this.setSize(300, 300);
			this.setTitle("�Է�");
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					setVisible(false); 
					dispose();
	            }
			});
			
			// ������ �� �����͵� �ʱ�ȭ
			panel = new JPanel();
			
			panel.setLayout(new FlowLayout());
			panel.setBackground(Color.pink);
			panel.setLayout(null);
			
			// �⵵�� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label year = new Label("�� | ");
			year.setBounds(10, 30, 30, 50);
			panel.add(year);
			// �⵵�� �Է��� �ؽ�Ʈ ����
			year_t = new JTextField();
			year_t.setBounds(45, 30, 50, 35);
			panel.add(year_t);
					
			Label month = new Label("�� | ");
			month.setBounds(100, 30, 30, 50);
			panel.add(month);
			month_t = new JTextField();
			month_t.setBounds(135, 30, 50, 35);
			panel.add(month_t);
					
			Label day = new Label("�� | ");
			day.setBounds(190, 30, 30, 50);
			panel.add(day);
			day_t = new JTextField();
			day_t.setBounds(225, 30, 50, 35);
			panel.add(day_t);
			
			Label money = new Label("�ݾ� | ");
			money.setBounds(10, 80, 35, 50);
			panel.add(money);
			money_t = new JTextField();
			money_t.setBounds(45, 80, 80, 35);
			panel.add(money_t);
			
			Label memo = new Label("�޸�| ");
			memo.setBounds(130, 80, 30, 50);
			panel.add(memo);
			memo_t = new JTextField();
			memo_t.setBounds(175, 80, 80, 35);
			panel.add(memo_t);
			
			Label category = new Label("ī�װ�| ");
			category.setBounds(10, 130, 50, 50);
			panel.add(category);

			if(inOut) { // ���� ���� �κ��̶��
				tagList = new JComboBox<>(tagIn);
			}else { // ���� ���� �κ��̶��
				tagList = new JComboBox<>(tagOut);
			}
			tagList.setBounds(70, 135, 150, 30);
			panel.add(tagList);
			
			// �Է� ��ư ����
			ok = new JButton("�Է�");
			ok.setBounds(110, 180, 60, 50);
			ok.addActionListener(new ActionListener() { // �Է� ��ư Ŭ�� �� ������ ȣ��
				@Override
				public void actionPerformed(ActionEvent e) {
					String date = null;
					String money = null;
					String memo = null; 
					String category = null;
					
					String []textIn = {"�� | ", "���Ա� | ", "�޸� | ", "ī�װ�| ", "���� | ", "���� |"};
					JTable table = null;
					
					try {
						// ��¥�� �ݾ��� �Էµ��� �ʾҴٸ� �ٽ� �Է��϶�� ��� �˾�â ����
						if(year_t.getText().isEmpty() || month_t.getText().isEmpty()
							|| day_t.getText().isEmpty() || money_t.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "��¥�� �ݾ��� �Է����ּ���!", "���", JOptionPane.WARNING_MESSAGE);
						return;
					} else { // ����� �Է��߾����� �ش��ϴ� ������ String ������ Ÿ������ ��ȯ�Ѵ�.
						date = year_t.getText() +"-" + month_t.getText() + "-" + day_t.getText();
						money = money_t.getText();
						category = tagList.getSelectedItem().toString();
						
						if(memo_t.getText().isEmpty()) {
							memo = null;
						}else {
							memo = memo_t.getText();
						}
						if(inOut) { //���� �Է� �κ��̶��
							// jdbc�� �����Ͽ� �Է��� �����͵��� db�� �߰��Ѵ�.
							jdbc.insertIncome(date, money, memo, category);
					        
							// ������� �����͸� �ٽ� �޾ƿͼ�
							Object []obj = jdbc.selectInsertIn(date, money, memo, category);
							// ���̺� GUI�� ������Ʈ �ϱ�
							if(obj != null) {
								updateTable(0, obj);
							}else {
								return;
							}
						}else { // ���� �κ�
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
	
	// Į���� 5��° �ε����� ���� ��ư���� ���� �Լ� 
	public class ClientsTableButton extends JButton implements TableCellRenderer{
		// ������ �Լ�
		public ClientsTableButton() {
			// TODO Auto-generated constructor stub
			setOpaque(true);
		}
		// JButton ��ӹ��� ���� �����ε�
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			setForeground(Color.black);
			setBackground(UIManager.getColor("Button.background"));
			// �ؽ�Ʈ ���� ���̸� ������ ���, �װ� �ƴ϶�� �ش� ���� ���ڿ��� �޾ƿ´�
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
	
	// Į���� 5��° �ε��� ���� Ŭ�� �� �̺�Ʈ(üũ�ڽ�) ���� �Լ�
	public class ClientsTableRenderer extends DefaultCellEditor
	  {
	    private JButton button;
	    private String label;
	    private boolean clicked;
	    private int row, col;
	    private JTable table;
	    private String tt;

	    // üũ�ڽ��� �ƱԸ�Ʈ�� �޴� ������ �Լ�
	    public ClientsTableRenderer(JCheckBox checkBox)
	    {
	      super(checkBox);
	      tt = checkBox.getText();
	      button = new JButton();
	      button.setOpaque(true);
	      // ��ư�� ���ο� ������ ����
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
	    	int dialogResult = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.WARNING_MESSAGE);
	    	if(dialogResult == JOptionPane.YES_OPTION){
		    	try {
		    		Object dateValue = table.getValueAt(row, 0);
			    	String money = table.getValueAt(row, 1).toString();
			    	String memo = table.getValueAt(row, 2).toString();
			    	String date = dateValue.toString();
			    	String category = table.getValueAt(row, 3).toString();
			    	
			    	if(tt == "1") { // ���� �κ��̶��
			    		jdbc.deleteInObject(date, money, memo, category);
			    	}else if(tt == "2"){ // ���� �κ��̶��
			    		jdbc.deleteOutObject(date, money, memo, category);
			    	}else if(tt == "3") { // �˻� �� ���¿��� ���� �κ��̶��
			    		jdbc.deleteInObject(date, money, memo, category);
			    		
			    		// ���� ������ �� ui ����
			    		deleteTable();
			    		updateTable();
			    	}else { // �˻��� ���¿��� ���� �κ��̶��
			    		jdbc.deleteOutObject(date, money, memo, category);
			    		
			    		deleteTable();
			    		updateTable();
			    	}
		    		
			    	// ��ư�� ���� ���̺��� ���� �ҷ��� ���ο� �� ������ ���� �ʱ�ȭ�ϱ�
		    		DefaultTableModel m = (DefaultTableModel)table.getModel();
		    		// ��ư�� ���� ���� row�� ����
		    		m.removeRow(table.getSelectedRow());
		    		
		    		// �ܾ� ���ʱ�ȭ
		    		int getBalance = jdbc.getBalance();
		            balance.setText("   �ܾ� :  " + getBalance);
		            
		            // �˾�â
		    		JOptionPane.showMessageDialog(null, "������ �����ϼ̽��ϴ�!", "����", JOptionPane.QUESTION_MESSAGE);
		    		
		    		// ui ����
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

	    // üũ�ڽ��� �ƱԸ�Ʈ�� �޴� ������ �Լ�
	    public ClientsTableRendererUpdate(JCheckBox checkBox)
	    {
	      super(checkBox);
	      tt = checkBox.getText();
	      button = new JButton();
	      button.setOpaque(true);
	      // ��ư�� ���ο� ������ ����
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
	    	f.setTitle("���� ��");
	    	
	    	f.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					// ���� ������ �ݾ��� �� �ش��ϴ� �����Ӹ� �ݵ��� �ϱ�
					f.setVisible(false); 
					f.dispose();
	            }
			});
	    	JPanel panel = new JPanel();
			
	    	// ��, ��, ���� '-'�� �������� �ؽ�Ʈ �ڸ���
	    	String[] d = p_date.trim().split("-");
	    	
	    	panel.setLayout(new FlowLayout());
			panel.setBackground(Color.pink);
			panel.setLayout(null);
			
			// ��, ��, ��, �޸�, �ݾ� �� �� ������ �ʵ� �ʱ�ȭ ����
			
			// �⵵�� �Է��� �ؽ�Ʈ ���ڸ� �����ֱ� ���� ��
			Label year = new Label("�� | ");
			year.setBounds(10, 30, 30, 50);
			panel.add(year);
			// �⵵�� �Է��� �ؽ�Ʈ ����
			JTextField year_t = new JTextField();
			year_t.setText(d[0]);
			year_t.setBounds(45, 30, 50, 35);
			panel.add(year_t);
					
			Label month = new Label("�� | ");
			month.setBounds(100, 30, 30, 50);
			panel.add(month);
			
			JTextField month_t = new JTextField();
			month_t.setText(d[1]);
			month_t.setBounds(135, 30, 50, 35);
			panel.add(month_t);
					
			Label day = new Label("�� | ");
			day.setBounds(190, 30, 30, 50);
			panel.add(day);
			
			JTextField day_t = new JTextField();
			day_t.setText(d[2]);
			day_t.setBounds(225, 30, 50, 35);
			panel.add(day_t);
			
			Label money = new Label("�ݾ� | ");
			money.setBounds(10, 80, 35, 50);
			panel.add(money);
			
			JTextField money_t = new JTextField();
			money_t.setText(p_money);
			money_t.setBounds(45, 80, 80, 35);
			panel.add(money_t);
			
			Label memo = new Label("�޸�| ");
			memo.setBounds(130, 80, 30, 50);
			panel.add(memo);
			
			JTextField memo_t = new JTextField();
			memo_t.setText(p_memo);
			memo_t.setBounds(175, 80, 80, 35);
			panel.add(memo_t);
			
			Label category = new Label("ī�װ�| ");
			category.setBounds(10, 130, 50, 50);
			panel.add(category);

			// ī�װ� ���ڿ� ����
	    	String tagIn[] = {"weekly revenue", "extra income", "pocket money", "etc."};
	    	String tagOut[] = {"food expenses", "transportation expenses", "necessaries", "shopping", 
	    			"hospital bills", "housing expenses", "postage", "culture/hobby", "etc."};
	    	// ī�װ��� ���� �޺� �ڽ� ����
	    	JComboBox<String> tagList = null;
	    	
	    	if(tt == "1") { // ���� �κ��̶��
	    		// ���� ī�װ� ���ڿ��� �޺��ڽ��� ����
				tagList = new JComboBox<>(tagIn); 
			}else { // ����
				tagList = new JComboBox<>(tagOut);
			}
	    	// �޺��ڽ� ����
			tagList.setBounds(70, 135, 150, 30);
			panel.add(tagList);
			
			try {
				// �ش��ϴ� ī�װ� �� ���ڿ� ���� ���� �� ����ֱ�
    	    	String u_category = tagList.getSelectedItem().toString();
    	    	
    	    	// �Է� ��ư ����
    	    	JButton ok = new JButton("�Է�");
    			ok.setBounds(110, 180, 60, 50);
    			ok.addActionListener(new ActionListener() { // �Է� ��ư Ŭ�� �� ȣ��
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// yes or no �˾� â ����
						int dialogResult = JOptionPane.showConfirmDialog(null, "���� �̷��� �����Ͻðڽ��ϱ�?", "����", JOptionPane.WARNING_MESSAGE);
						if(dialogResult == JOptionPane.YES_OPTION){ // yes ���� �� ��
							// �Է� �� ���� ������ ���ڿ� ���·� �������ش�.
			    	    	String u_date = year_t.getText() + "-" + month_t.getText() + "-" + day_t.getText();
			    	    	String u_money = money_t.getText();
			    	    	String u_memo = null;
			    	    	if(!memo_t.getText().isEmpty()) {
			    	    		u_memo = memo_t.getText();
			    	    	}
			    	    	
			    			if(tt == "1") { // ���� �κ��̶��
			    				// ���� �κ� update�ϱ�
			        			jdbc.updateIn(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        		}else if(tt == "2") { // ���� �κ��̶��
			        			jdbc.updateOut(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        		}else if(tt == "3") { // �˻��� ��Ȳ���� ���� �κ��̶��
			        			jdbc.updateIn(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        			
			        			// �˻� �� ���̺��� ���� �ҷ��´�.
			        			DefaultTableModel m = (DefaultTableModel)table.getModel();
			        			// Ŭ���� row ����
			        			m.removeRow(table.getSelectedRow());
			        			
			        			// ���� �� ������ db���� ������
			        			Object[] obj = jdbc.selectInsertIn(u_date, u_money, u_memo, u_category);
			        			// �𵨿� �߰��Ѵ� -> gui ����
			        			m.addRow(obj);
			        			table.repaint();
			        		}else { // �˻��� ��Ȳ���� ���� �κ��̶��
			        			jdbc.updateOut(p_date, p_money, p_memo, p_category, u_date, u_money, u_memo, u_category);
			        			
			        			DefaultTableModel m = (DefaultTableModel)table.getModel();
			        			m.removeRow(table.getSelectedRow());
			        			
			        			Object[] obj = jdbc.selectInsertOut(u_date, u_money, u_memo, u_category);
			        			m.addRow(obj);
			        			table.repaint();
			        		}
			    			// ���� gui ���̺��� ������� �ʴٸ�
			    			if(inTable != null) {
			    				// gui ����
			    				deleteTable();
			    				updateTable();
			    			}
			    			// ���� gui ���̺��� ������� ������
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
	
	/** �����Լ� */
	public static void main(String[] args) {
		// jdbc ��ü ����
		jdbc = new JDBC_AccountBook();
		
		// db����
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


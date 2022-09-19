package Bank;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class BankWindow extends JFrame implements ActionListener {

	// Main Place on Form where All Child Forms will Shown.
	private JDesktopPane desktop = new JDesktopPane();

	// For Program's MenuBar.
	private JMenuBar bar;

	// All the Main Menu of the Program.
	private JMenu mnuFile;
	private JMenu mnuEdit;
	private JMenu mnuView;
	private JMenu mnuHelp;

	// All the MenuIems of the programs
	private JMenuItem addNew;
	private JMenuItem deposit;
	private JMenuItem printRec;
	private JMenuItem withdraw;
	private JMenuItem delRec;
	private JMenuItem allCustomer;
	private JMenuItem about;

	// PopupMenu of Program.
	private JPopupMenu popMenu = new JPopupMenu();

	// MenuItems for PopupMenu of the Program.
	private JMenuItem open;
	private JMenuItem report;
	private JMenuItem dep;
	private JMenuItem with;
	private JMenuItem del;
	private JMenuItem all;

	// Main Form StatusBar where Program's Name & Welcome Message Display.
	private JPanel statusBar = new JPanel();

	// Labels for Displaying Program's Name & saying Welcome to Current User on
	// StatusBar.
	private JLabel welcome;
	private JLabel author;

	// Getting the Current System Date.
	private java.util.Date currDate = new java.util.Date();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
	private String d = sdf.format(currDate);

	// Following all Variables are use in BankSystem.

	// Variable use in Reading the BankSystem Records File & Store it in an Array.

	private int rows = 0;
	private int total = 0;

	// String Type Array use to Load Records From File.
	private String records[][] = new String[500][6];

	// Variable for Reading the BankSystem Records File.
	private FileInputStream fis;
	private DataInputStream dis;

	// Constructor of The Bank Program.

	public BankWindow() {
		// Setting Program's Title.
		super("OOP Bank System");
		Container content = getContentPane();
		content.add(desktop);
		setVisible(true);
		setBounds(300, 300, 700, 700);
		

		// The Style Apply
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Can't set look and feel");
		}

		// Creating the MenuBar.
		bar = new JMenuBar();

		// Setting the Main Window of Program.
		setSize(700, 550);
		setJMenuBar(bar);

		// Creating the MenuBar Items.

		mnuFile = new JMenu("File");
		mnuFile.setMnemonic((int) 'F');
		mnuEdit = new JMenu("Edit");
		mnuEdit.setMnemonic((int) 'E');
		mnuView = new JMenu("View");
		mnuView.setMnemonic((int) 'V');
		mnuHelp = new JMenu("Help");
		mnuHelp.setMnemonic((int) 'H');

		// Creating the MenuItems of Program.

		// MenuItems for FileMenu.

		addNew = new JMenuItem("Open New Account");
		addNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		addNew.setMnemonic((int) 'O');
		addNew.addActionListener(this);
		printRec = new JMenuItem("Print Customer Balance");
		printRec.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		printRec.setMnemonic((int) 'P');
		printRec.addActionListener(this);

		// MenuItems for EditMenu.
		deposit = new JMenuItem("Deposit Money");
		deposit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK));
		deposit.setMnemonic((int) 'M');
		deposit.addActionListener(this);
		withdraw = new JMenuItem("Withdraw Money");
		withdraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		withdraw.setMnemonic((int) 'W');
		withdraw.addActionListener(this);
		delRec = new JMenuItem("Delete Customer");
		delRec.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		delRec.setMnemonic((int) 'D');
		delRec.addActionListener(this);

		// MenuItems for ViewMenu.
		allCustomer = new JMenuItem("View All Customer");
		allCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		allCustomer.setMnemonic((int) 'A');
		allCustomer.addActionListener(this);

		// MenuItems for HelpMenu.

		about = new JMenuItem("About OOP BankSystem");
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
		about.setMnemonic((int) 'B');
		about.addActionListener(this);

		// Adding MenuItems to Menu.

		// File Menu Items.
		mnuFile.add(addNew);
		mnuFile.addSeparator();
		mnuFile.add(printRec);
		mnuFile.addSeparator();

		// Edit Menu Items.
		mnuEdit.add(deposit);
		mnuEdit.add(withdraw);
		mnuEdit.addSeparator();
		mnuEdit.add(delRec);
		// mnuEdit.addSeparator ();

		// View Menu Items.
		mnuView.add(allCustomer);

		// Help Menu Items.
		mnuHelp.add(about);

		// Adding Menus to Bar.
		bar.add(mnuFile);
		bar.add(mnuEdit);
		bar.add(mnuView);
		bar.add(mnuHelp);

		// MenuItems for PopupMenu.
		open = new JMenuItem("Open New Account");
		open.addActionListener(this);
		report = new JMenuItem("Print BankSystem Report");
		report.addActionListener(this);
		dep = new JMenuItem("Deposit Money");
		dep.addActionListener(this);
		with = new JMenuItem("Withdraw Money");
		with.addActionListener(this);
		del = new JMenuItem("Delete Customer");
		del.addActionListener(this);
		all = new JMenuItem("View All Customer");
		all.addActionListener(this);

		// Adding Menus to PopupMenu.
		popMenu.add(open);
		popMenu.add(report);
		popMenu.add(dep);
		popMenu.add(with);
		popMenu.add(del);
		popMenu.add(all);

		// Following Procedure display the PopupMenu of Program.
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				checkMouseTrigger(me);
			}

			public void mouseReleased(MouseEvent me) {
				checkMouseTrigger(me);
			}

			private void checkMouseTrigger(MouseEvent me) {
				if (me.isPopupTrigger())
					popMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});

		// Creating the StatusBar of Program.

		author = new JLabel(" " + "Mamadou Mbengue", Label.LEFT);
		author.setForeground(Color.black);
		author.setToolTipText("Program's Title");
		welcome = new JLabel("Welcome Today is " + d + " ", JLabel.RIGHT);
		welcome.setForeground(Color.black);
		welcome.setToolTipText("Welcoming the User & System Current Date");
		statusBar.setLayout(new BorderLayout());
		statusBar.add(author, BorderLayout.WEST);
		statusBar.add(welcome, BorderLayout.EAST);

		// For Making the Dragging Speed of Internal Frames Faster.
		desktop.putClientProperty("JDesktopPane.dragMode", "outline");

		// Setting the Contents of Programs.

		getContentPane().add(desktop, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Showing The Main Form of Application.
		setVisible(true);

	}

	// Function For Performing different Actions By Menus of Program.

	public void actionPerformed(ActionEvent ae) {

		Object obj = ae.getSource();

		if (obj == addNew || obj == open) {

			boolean b = openChildWindow("Create New Account");
			if (b == false) {
				NewAccount newAcc = new NewAccount();
				desktop.add(newAcc);
				newAcc.show();
			}

		} else if (obj == printRec) {

			getAccountNo();

		} else if (obj == deposit || obj == dep) {

			boolean b = openChildWindow("Deposit Money");
			if (b == false) {
				DepositMoney depMon = new DepositMoney();
				desktop.add(depMon);
				depMon.show();
			}

		} else if (obj == withdraw || obj == with) {

			boolean b = openChildWindow("Withdraw Money");
			if (b == false) {
				WithdrawMoney withMon = new WithdrawMoney();
				desktop.add(withMon);
				withMon.show();
			}

		} else if (obj == delRec || obj == del) {

			boolean b = openChildWindow("Delete Account Holder");
			if (b == false) {
				DeleteCustomer delCus = new DeleteCustomer();
				desktop.add(delCus);
				delCus.show();
			}

		} else if (obj == allCustomer || obj == all) {

			boolean b = openChildWindow("View All Account Holders");
			if (b == false) {
				ViewCustomer viewCus = new ViewCustomer();
				desktop.add(viewCus);
				viewCus.show();
			}

		}

		else if (obj == about) {

			String msg = "Mbengue's BankSystem .\n\n" + "Created & Designed By:\n" + "Mamadou Mbengue \n\n"
					+ "DAUST Student";
			JOptionPane.showMessageDialog(this, msg, "About Daust BankSystem", JOptionPane.PLAIN_MESSAGE);

		}

	}

	// Loop Through All the Opened JInternalFrame to Perform the Task.

	private boolean openChildWindow(String title) {

		JInternalFrame[] childs = desktop.getAllFrames();
		for (int i = 0; i < childs.length; i++) {
			if (childs[i].getTitle().equalsIgnoreCase(title)) {
				childs[i].show();
				return true;
			}
		}
		return false;

	}

	// Following Functions use for Printing Records & Report of BankSystem.

	void getAccountNo() {

		String printing;
		rows = 0;
		boolean b = populateArray();
		if (b == false) {
		} else {
			try {
				printing = JOptionPane.showInputDialog(this,
						"Enter Account No. to Print Customer Balance.\n" + "(Tip: Account No. Contains only Digits)",
						"BankSystem - PrintRecord", JOptionPane.PLAIN_MESSAGE);
				if (printing == null) {
				}
				if (printing.equals("")) {
					JOptionPane.showMessageDialog(this, "Provide Account No. to Print.", "BankSystem - EmptyField",
							JOptionPane.PLAIN_MESSAGE);
					getAccountNo();
				} else {
					findRec(printing);
				}
			} catch (Exception e) {
			}
		}

	}

	// Function use to load all Records from File when Application Execute.

	boolean populateArray() {

		boolean b = false;
		try {
			fis = new FileInputStream("Bank.dat");
			dis = new DataInputStream(fis);
			// Loop to Populate the Array.
			while (true) {
				for (int i = 0; i < 6; i++) {
					records[rows][i] = dis.readUTF();
				}
				rows++;
			}
		} catch (Exception ex) {
			total = rows;
			if (total == 0) {
				JOptionPane.showMessageDialog(null, "Records File is Empty.\nEnter Records First to Display.",
						"BankSystem - EmptyFile", JOptionPane.PLAIN_MESSAGE);
				b = false;
			} else {
				b = true;
				try {
					dis.close();
					fis.close();
				} catch (Exception exp) {
				}
			}
		}
		return b;

	}

	// Function use to Find Record by Matching the Contents of Records Array with
	// InputBox.

	void findRec(String rec) {

		boolean found = false;
		for (int x = 0; x < total; x++) {
			if (records[x][0].equals(rec)) {
				found = true;
				printRecord(makeRecordPrint(x));
				break;
			}
		}
		if (found == false) {
			JOptionPane.showMessageDialog(this, "Account No. " + rec + " doesn't Exist.", "BankSystem - WrongNo",
					JOptionPane.PLAIN_MESSAGE);
			getAccountNo();
		}

	}

	// Function use to make Current Record ready for Print.

	String makeRecordPrint(int rec) {

		String data;
		String data0 = "               OOP BankSystem.               \n"; // Page Title.
		String data1 = "               Customer Balance Report.              \n\n"; // Page Header.
		String data2 = "  Account No.:       " + records[rec][0] + "\n";
		String data3 = "  Customer Name:     " + records[rec][1] + "\n";
		String data4 = "  Last Transaction:  " + records[rec][2] + ", " + records[rec][3] + ", " + records[rec][4]
				+ "\n";
		String data5 = "  Current Balance:   " + records[rec][5] + "\n\n";
		String data6 = "          Thank you for choosing OOP BankSystem.\n"; // Page Footer.
		String sep0 = " -----------------------------------------------------------\n";
		String sep1 = " -----------------------------------------------------------\n";
		String sep2 = " -----------------------------------------------------------\n";
		String sep3 = " -----------------------------------------------------------\n";
		String sep4 = " -----------------------------------------------------------\n\n";

		data = data0 + sep0 + data1 + data2 + sep1 + data3 + sep2 + data4 + sep3 + data5 + sep4 + data6;
		return data;

	}
	// Function use to Print the Current Record.

	void printRecord(String rec) {

		StringReader sr = new StringReader(rec);
		LineNumberReader lnr = new LineNumberReader(sr);
		Font typeface = new Font("Times New Roman", Font.PLAIN, 12);
		Properties p = new Properties();
		PrintJob pJob = getToolkit().getPrintJob(this, "Print Customer Balance Report", p);

		if (pJob != null) {
			Graphics gr = pJob.getGraphics();
			if (gr != null) {
				FontMetrics fm = gr.getFontMetrics(typeface);
				int margin = 20;
				int pageHeight = pJob.getPageDimension().height - margin;
				int fontHeight = fm.getHeight();
				int fontDescent = fm.getDescent();
				int curHeight = margin;
				String nextLine;
				gr.setFont(typeface);

				try {
					do {
						nextLine = lnr.readLine();
						if (nextLine != null) {
							if ((curHeight + fontHeight) > pageHeight) { // New Page.
								gr.dispose();
								gr = pJob.getGraphics();
								curHeight = margin;
							}
							curHeight += fontHeight;
							if (gr != null) {
								gr.setFont(typeface);
								gr.drawString(nextLine, margin, curHeight - fontDescent);
							}
						}
					} while (nextLine != null);
				} catch (EOFException eof) {
				} catch (Throwable t) {
				}
			}
			gr.dispose();
		}
		if (pJob != null)
			pJob.end();

	}
}

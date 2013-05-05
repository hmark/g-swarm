package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import java.lang.Runnable;
import java.io.File;

import utils.DateUtils;
import utils.FileUtils;

import gui.commands.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.EventQueue;
import java.awt.Font;

import pso.Setup;
import javax.swing.border.TitledBorder;

/**
 * Application with GUI
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class App extends JFrame {

	private static final long serialVersionUID = -7884482561510373900L;

	private static App instance;

	private JPanel contentPane;
	
	private DefaultTableModel testsTableModel;
	private ListSelectionModel testSelectionModel;
	
	private DefaultTableModel itersTableModel;
	private ListSelectionModel itersSelectionModel;
	
	private DefaultTableModel particlesTableModel;
	private ListSelectionModel particlesSelectionModel;
	
	private int testSelectedIndex = -1;
	private int iterationSelectedIndex = -1;
	private int particleSelectedIndex = -1;
	
	JTextArea sourceTextArea;
	JTextArea propsTextArea;
	JTextArea scoresTextArea;
	private JTextField swarmSizeField;
	private JTextField dimsField;
	private JTextField xmaxField;
	private JTextField speedMinField;
	private JTextField speedMaxField;
	private JTextField itersField;
	private JTextField c1Field;
	private JTextField c2Field;
	private JTextField minWeightField;
	private JTextField maxWeightField;
	private JTable testsTable;
	private JTable itersTable;
	private JTable particlesTable;
	private JTextField robotTemplate;
	private JTextField initExpression;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
					frame.findTests();
					instance = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Get singleton instance of application to update GUI.
	 * @return
	 */
	public static App getInstance() {
        return instance;
    }

	/**
	 * Create the frame.
	 */
	public App() {
		setTitle("Marek Hlav\u00E1\u010D, STU FIIT, 2013, Testovacia aplik\u00E1cia k diplomovej pr\u00E1ce");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new ExitCommand();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1088, 699);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vlastnosti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_4.setBounds(10, 369, 480, 145);
		contentPane.add(scrollPane_4);
		
		propsTextArea = new JTextArea();
		scrollPane_4.setViewportView(propsTextArea);
		propsTextArea.setLineWrap(true);
		propsTextArea.setBorder(null);
		propsTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		propsTextArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1023, 25, 2, 2);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "V\u00FDsledky s\u00FAbojov", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_5.setBounds(10, 525, 480, 127);
		contentPane.add(scrollPane_5);
		
		scoresTextArea = new JTextArea();
		scrollPane_5.setViewportView(scoresTextArea);
		scoresTextArea.setLineWrap(true);
		scoresTextArea.setBorder(null);
		scoresTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		scoresTextArea.setEditable(false);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Program robota", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_6.setBounds(500, 369, 567, 283);
		contentPane.add(scrollPane_6);
		
		sourceTextArea = new JTextArea();
		scrollPane_6.setViewportView(sourceTextArea);
		sourceTextArea.setBorder(null);
		sourceTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		sourceTextArea.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.setBounds(310, 30, 320, 300);
		contentPane.add(scrollPane_1);
		
		testsTable = new JTable(){
			private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		scrollPane_1.setViewportView(testsTable);
		testsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		testsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testsTable.setBorder(null);
		
		testSelectionModel = testsTable.getSelectionModel();
		testSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	    	  int index = testsTable.getSelectedRow();
				if (!e.getValueIsAdjusting() && index != -1)
					App.getInstance().openTest(index);
	      }

	    });
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(640, 30, 192, 328);
		contentPane.add(scrollPane_2);
		
		itersTable = new JTable(){
			private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		scrollPane_2.setViewportView(itersTable);
		itersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		itersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itersTable.setBorder(null);
		
		itersSelectionModel = itersTable.getSelectionModel();
		itersSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	    	  int index = itersTable.getSelectedRow();
	    	  if (!e.getValueIsAdjusting() && index != -1)
					App.getInstance().openIteration(index);
	      }

	    });
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(842, 30, 225, 328);
		contentPane.add(scrollPane_3);
		
		particlesTable = new JTable(){
			private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		particlesTable.setBorder(null);
		particlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(particlesTable);
		particlesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JLabel lblTests = new JLabel("Zoznam testov");
		lblTests.setFont(new Font("Arial", Font.BOLD, 14));
		lblTests.setBounds(310, 10, 122, 14);
		contentPane.add(lblTests);
		
		JLabel lblIterations = new JLabel("Zoznam iter\u00E1ci\u00ED");
		lblIterations.setFont(new Font("Arial", Font.BOLD, 14));
		lblIterations.setBounds(640, 10, 192, 14);
		contentPane.add(lblIterations);
		
		JLabel lblParticles = new JLabel("Zoznam \u010Dast\u00EDc");
		lblParticles.setFont(new Font("Arial", Font.BOLD, 14));
		lblParticles.setBounds(842, 10, 134, 14);
		contentPane.add(lblParticles);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Vytvor nov\u00FD test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 290, 358);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblParticles_1 = new JLabel("Ve\u013Ekos\u0165 roja");
		lblParticles_1.setBounds(10, 24, 91, 14);
		panel.add(lblParticles_1);
		lblParticles_1.setToolTipText("Po\u010Det \u010Dast\u00EDc v roji");
		lblParticles_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		swarmSizeField = new JTextField();
		swarmSizeField.setBounds(111, 21, 169, 20);
		panel.add(swarmSizeField);
		swarmSizeField.setColumns(10);
		swarmSizeField.setText(Integer.toString(Setup.PARTICLES));
		
		JLabel lblNewLabel = new JLabel("Po\u010Det dimenzi\u00ED");
		lblNewLabel.setBounds(10, 49, 91, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setToolTipText("Ve\u013Ekos\u0165 vektora \u010Dastice");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JButton runTestBtn = new JButton("\u0160tart");
		runTestBtn.setToolTipText("\u0160tart");
		runTestBtn.setBounds(10, 324, 270, 23);
		panel.add(runTestBtn);
		
		dimsField = new JTextField();
		dimsField.setBounds(111, 46, 169, 20);
		panel.add(dimsField);
		dimsField.setColumns(10);
		dimsField.setText(Integer.toString(Setup.DIMENSIONS));
		
		JLabel lblNewLabel_1 = new JLabel("Max. Poz\u00EDcia");
		lblNewLabel_1.setBounds(10, 74, 72, 14);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setToolTipText("Maxim\u00E1lna hodnota \u010Dastice v jednej dimenzii");
		
		xmaxField = new JTextField();
		xmaxField.setBounds(111, 71, 169, 20);
		panel.add(xmaxField);
		xmaxField.setColumns(10);
		xmaxField.setText(Integer.toString(Setup.XMAX));
		
		JLabel lblNewLabel_3 = new JLabel("Min. R\u00FDchlos\u0165");
		lblNewLabel_3.setBounds(10, 98, 91, 14);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setToolTipText("Minim\u00E1lna r\u00FDchlos\u0165 \u010Dastice");
		
		JLabel lblNewLabel_4 = new JLabel("Max. R\u00FDchlos\u0165");
		lblNewLabel_4.setBounds(10, 123, 91, 14);
		panel.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setToolTipText("Maxim\u00E1lna r\u00FDchlos\u0165 \u010Dastice");
		
		speedMaxField = new JTextField();
		speedMaxField.setBounds(111, 120, 169, 20);
		panel.add(speedMaxField);
		speedMaxField.setColumns(10);
		speedMaxField.setText(Integer.toString(Setup.SPEED_MAX));
		
		JLabel lblNewLabel_5 = new JLabel("Po\u010Det iter\u00E1ci\u00ED");
		lblNewLabel_5.setBounds(10, 148, 91, 14);
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setToolTipText("Itera\u010Dn\u00FD limit algoritmu");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		itersField = new JTextField();
		itersField.setBounds(111, 145, 169, 20);
		panel.add(itersField);
		itersField.setColumns(10);
		itersField.setText(Integer.toString(Setup.ITERATIONS));
		
		JLabel lblNewLabel_6 = new JLabel("Kognit\u00EDvny koef.");
		lblNewLabel_6.setBounds(10, 173, 91, 14);
		panel.add(lblNewLabel_6);
		lblNewLabel_6.setToolTipText("Akcelera\u010Dn\u00FD koeficien pre kognit\u00EDvny komponent");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		speedMinField = new JTextField();
		speedMinField.setBounds(111, 95, 169, 20);
		panel.add(speedMinField);
		speedMinField.setColumns(10);
		speedMinField.setText(Integer.toString(Setup.SPEED_MIN));
		
		c1Field = new JTextField();
		c1Field.setBounds(111, 170, 169, 20);
		panel.add(c1Field);
		c1Field.setColumns(10);
		c1Field.setText(Double.toString(Setup.C1));
		
		JLabel lblNewLabel_7 = new JLabel("Soci\u00E1lny koef.");
		lblNewLabel_7.setBounds(11, 198, 90, 14);
		panel.add(lblNewLabel_7);
		lblNewLabel_7.setToolTipText("Akcelera\u010Dn\u00FD koeficient pre soci\u00E1lny komponent");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		c2Field = new JTextField();
		c2Field.setBounds(111, 195, 169, 20);
		panel.add(c2Field);
		c2Field.setColumns(10);
		c2Field.setText(Double.toString(Setup.C2));
		
		JLabel lblNewLabel_8 = new JLabel("Min. V\u00E1ha");
		lblNewLabel_8.setBounds(10, 223, 63, 14);
		panel.add(lblNewLabel_8);
		lblNewLabel_8.setToolTipText("Minim\u00E1lna v\u00E1ha zotrva\u010Dnosti");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		minWeightField = new JTextField();
		minWeightField.setBounds(111, 220, 169, 20);
		panel.add(minWeightField);
		minWeightField.setColumns(10);
		minWeightField.setText(Double.toString(Setup.WMIN));
		
		JLabel lblNewLabel_9 = new JLabel("Max. V\u00E1ha");
		lblNewLabel_9.setBounds(10, 248, 67, 14);
		panel.add(lblNewLabel_9);
		lblNewLabel_9.setToolTipText("Po\u010Diato\u010Dn\u00E1 v\u00E1ha zotrva\u010Dnosti");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		maxWeightField = new JTextField();
		maxWeightField.setBounds(111, 245, 169, 20);
		panel.add(maxWeightField);
		maxWeightField.setColumns(10);
		maxWeightField.setText(Double.toString(Setup.WMAX));
		
		JLabel lblRobotTemplate = new JLabel("\u0160abl\u00F3na robota");
		lblRobotTemplate.setToolTipText("Cesta k \u0161abl\u00F3ne robota");
		lblRobotTemplate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRobotTemplate.setBounds(10, 273, 102, 14);
		panel.add(lblRobotTemplate);
		
		JLabel lblStartingExpression = new JLabel("Po\u010Diato\u010Dn\u00FD v\u00FDraz");
		lblStartingExpression.setToolTipText("Po\u010Diato\u010Dn\u00FD v\u00FDraz pre gramatiku");
		lblStartingExpression.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStartingExpression.setBounds(10, 298, 102, 14);
		panel.add(lblStartingExpression);
		
		robotTemplate = new JTextField();
		robotTemplate.setText("templates/bot_micro.tmpl");
		robotTemplate.setBounds(111, 270, 169, 20);
		panel.add(robotTemplate);
		robotTemplate.setColumns(10);
		
		initExpression = new JTextField();
		initExpression.setText("<!EXP>");
		initExpression.setBounds(111, 295, 169, 20);
		panel.add(initExpression);
		initExpression.setColumns(10);
		JButton refreshBtn = new JButton("Obnov testy");
		refreshBtn.setToolTipText("Obnov testy");
		refreshBtn.setBounds(310, 335, 320, 23);
		contentPane.add(refreshBtn);
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.getInstance().update();
			}
		});
		runTestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				if (btn.getText() == "ätart"){
					new RunTestCommand();
					btn.setText("Stop");
				}
				else if (btn.getText() == "Stop"){
					new StopTestCommand();
					btn.setText("ätart");
				}
			}
		});
		
		particlesSelectionModel = particlesTable.getSelectionModel();
		particlesSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	    	  int index = particlesTable.getSelectedRow();
	    	  if (!e.getValueIsAdjusting() && index != -1)
	    		  App.getInstance().openParticle(index);
	      }

	    });
		
	}
	
	/**
	 * Update frame.
	 */
	public void update(){
		findTests();
	}
	
	/**
	 * Find test directories and prepare output.
	 */
	public void findTests(){
		File folder = new File("test");
		File[] listOfFiles = folder.listFiles();
		
		testsTableModel = new DefaultTableModel();
		testsTableModel.addColumn("Id");
		testsTableModel.addColumn("DateTime");
		testsTableModel.addColumn("Directory");
		String name;
				
		int id = 0;
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isDirectory()){
				name = listOfFiles[i].getName();
				testsTableModel.addRow(new Object[]{++id, transKeyToDate(name), name});
			}
		}
		
		testsTable.setModel(testsTableModel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		testsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		testsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		testsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		testsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		testsTable.getColumnModel().getColumn(2).setPreferredWidth(165);
		testsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	}
	
	private String transKeyToDate(String name){
		name = name.substring(4); // remove 'test' prefix
		return DateUtils.decodeDate(name);
	}
	
	/**
	 * Open test and prepare iterations output.
	 */
	public void openTest(int index){
		testSelectedIndex = index;
		
		// clear particles table
		DefaultTableModel model = (DefaultTableModel) particlesTable.getModel();
		model.setRowCount(0);
		
		// clear iterations table
		model = (DefaultTableModel) itersTable.getModel();
		model.setRowCount(0);
		
		particlesTable.clearSelection();
		itersTable.clearSelection();

		String folderName = "test/" + testsTableModel.getValueAt(index, 2).toString();
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		itersTableModel = new DefaultTableModel();
		itersTableModel.addColumn("Id");
		itersTableModel.addColumn("Directory");

		String name;
		int iteration = 0;
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isDirectory()){
				name = listOfFiles[i].getName();
				itersTableModel.addRow(new Object[]{++iteration, name});
			}
		}
		
		itersTable.setModel(itersTableModel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		itersTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		itersTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		itersTable.getColumnModel().getColumn(1).setPreferredWidth(145);
		itersTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		propsTextArea.setText("");
		propsTextArea.setCaretPosition(0);
		scoresTextArea.setText("");
		scoresTextArea.setCaretPosition(0);
		sourceTextArea.setText("");
		sourceTextArea.setCaretPosition(0);
	}
	
	/**
	 * Open iteration and prepare particles output with iterations log output.
	 */
	public void openIteration(int index){
		iterationSelectedIndex = index;
		
		// clear particles table
		DefaultTableModel model = (DefaultTableModel) particlesTable.getModel();
		model.setRowCount(0);
		particlesTable.clearSelection();
		
		String folderName = "test/" + testsTableModel.getValueAt(testSelectedIndex, 2).toString() + "/" + itersTableModel.getValueAt(iterationSelectedIndex, 1);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		particlesTableModel = new DefaultTableModel();
		particlesTableModel.addColumn("Id");
		particlesTableModel.addColumn("Modul");
		particlesTableModel.addColumn("Directory");
		
		String name;
		String[] splits;
		int iteration = 0;
		for (int i = 0; i < listOfFiles.length; i++){
			if (listOfFiles[i].isDirectory() && !listOfFiles[i].getName().equals("logs")){
				name = listOfFiles[i].getName();
				splits = name.split("_");
				particlesTableModel.addRow(new Object[]{++iteration, splits[0], name});
			}
		}
		
		particlesTable.setModel(particlesTableModel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		
		particlesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		particlesTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		particlesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		particlesTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		particlesTable.getColumnModel().getColumn(2).setPreferredWidth(127);
		particlesTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		String propText = FileUtils.convertFileToString(folderName + "/logs/ahead_data.log");
		propText += "\n";
		propText += FileUtils.convertFileToString(folderName + "/logs/gun_data.log");
		propText += "\n";
		propText += FileUtils.convertFileToString(folderName + "/logs/turn_data.log");
		propText += "\n";
		propText += FileUtils.convertFileToString(folderName + "/logs/fire_data.log");
		propText += "\n";
		
		propText = propText.replaceAll("null\n", "");
		
		propsTextArea.setText(propText);
		propsTextArea.setCaretPosition(0);
		scoresTextArea.setText("");
		scoresTextArea.setCaretPosition(0);
		sourceTextArea.setText("");
		sourceTextArea.setCaretPosition(0);
	}
	
	/**
	 * Open particle and prepare proper outputs.
	 */
	public void openParticle(int index){
		particleSelectedIndex = index;
		String particleId = particlesTableModel.getValueAt(particleSelectedIndex, 2).toString();
		particleId = particleId.substring(particleId.length() - 5, particleId.length());
		
		String folderName = "test/" + testsTableModel.getValueAt(testSelectedIndex, 2).toString() + "/" + itersTableModel.getValueAt(iterationSelectedIndex, 1) + "/" + particlesTableModel.getValueAt(particleSelectedIndex, 2);
		
		sourceTextArea.removeAll();
		
		String propText = FileUtils.convertFileToString(folderName + "/data.log");
		if (propText != null){
			propsTextArea.setText(propText);
			propsTextArea.setCaretPosition(0);
		}
		
		String scoresText = FileUtils.convertFileToString(folderName + "/battle.log");
		if (scoresText != null){
			scoresTextArea.setText(scoresText.replaceAll("%\\)", "%\\)\n").replaceAll("\t", " "));
			scoresTextArea.setCaretPosition(0);
		}
		
		String sourceText = FileUtils.convertFileToString(folderName + "/GSwarmRobot" + particleId + ".java");
		if (sourceText != null){
			sourceTextArea.setText(sourceText);
			sourceTextArea.setCaretPosition(0);
		}
	}
	
	public int getParticlesNum(){
		return Integer.parseInt(swarmSizeField.getText());
	}
	
	public int getDimensionsNum(){
		return Integer.parseInt(dimsField.getText());
	}
	
	public int getXMaxNum(){
		return Integer.parseInt(xmaxField.getText());
	}
	
	public int getSpeedMinNum(){
		return Integer.parseInt(speedMinField.getText());
	}
	
	public int getSpeedMaxNum(){
		return Integer.parseInt(speedMaxField.getText());
	}
	
	public int getIterationsNum(){
		return Integer.parseInt(itersField.getText());
	}
	
	public Double getC1Num(){
		return Double.parseDouble(c1Field.getText());
	}
	
	public Double getC2Num(){
		return Double.parseDouble(c2Field.getText());
	}
	
	public Double getMinWeightNum(){
		return Double.parseDouble(minWeightField.getText());
	}
	
	public Double getMaxWeightNum(){
		return Double.parseDouble(maxWeightField.getText());
	}
	
	public String getRobotTemplate(){
		return robotTemplate.getText();
	}
	
	public String getInitExpression(){
		return initExpression.getText();
	}
}

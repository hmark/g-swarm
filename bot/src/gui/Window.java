package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.*;
import javax.swing.*;

import java.lang.Runnable;
import java.io.File;
import utils.FileUtils;

import gui.commands.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.border.LineBorder;

import pso.Setup;

import java.awt.Color;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JFrame {
	
	private static Window instance;

	private JPanel contentPane;
	
	private JLabel testsLabel;
	private JLabel itersLabel;
	private JLabel particlesLabel;
	
	private DefaultListModel testsListModel = new DefaultListModel();
	private DefaultListModel iterationsListModel = new DefaultListModel();
	private DefaultListModel particlesListModel = new DefaultListModel();
	
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
					frame.findTests();
					instance = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Window getInstance() {
        return instance;
    }

	/**
	 * Create the frame.
	 */
	public Window() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new ExitCommand();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1196, 722);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblStats = new JLabel("Properties");
		lblStats.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblScoringTable = new JLabel("Results");
		lblScoringTable.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblSource = new JLabel("Source");
		lblSource.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane();
		
		testsLabel = new JLabel("Tests");
		testsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		itersLabel = new JLabel("Iterations");
		itersLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		particlesLabel = new JLabel("Particles");
		particlesLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		
		JScrollPane scrollPane_5 = new JScrollPane();
		
		JScrollPane scrollPane_6 = new JScrollPane();
		
		JLabel lblParticles_1 = new JLabel("Swarm Size");
		lblParticles_1.setToolTipText("Number of particles in swarm");
		lblParticles_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel = new JLabel("Dimensions");
		lblNewLabel.setToolTipText("Size of particle vector");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_1 = new JLabel("Max.Position");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setToolTipText("Maximum value of particle in one dimension");
		
		JLabel lblNewLabel_3 = new JLabel("Min.Velocity");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setToolTipText("Minimal velocity of particle");
		
		JLabel lblNewLabel_4 = new JLabel("Max.Velocity");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setToolTipText("Maximal velocity of particle");
		
		JLabel lblNewLabel_5 = new JLabel("Iterations");
		lblNewLabel_5.setToolTipText("Iteration limit of algorithm");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		swarmSizeField = new JTextField();
		swarmSizeField.setColumns(10);
		swarmSizeField.setText(Integer.toString(Setup.PARTICLES));
		
		dimsField = new JTextField();
		dimsField.setColumns(10);
		dimsField.setText(Integer.toString(Setup.DIMENSIONS));
		
		xmaxField = new JTextField();
		xmaxField.setColumns(10);
		xmaxField.setText(Integer.toString(Setup.XMAX));
		
		speedMinField = new JTextField();
		speedMinField.setColumns(10);
		speedMinField.setText(Integer.toString(Setup.SPEED_MIN));
		
		speedMaxField = new JTextField();
		speedMaxField.setColumns(10);
		speedMaxField.setText(Integer.toString(Setup.SPEED_MAX));
		
		JLabel lblNewLabel_6 = new JLabel("Cognitive Coef.");
		lblNewLabel_6.setToolTipText(" Accelerator coefficient of cognitive component.");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_7 = new JLabel("Social Coef.");
		lblNewLabel_7.setToolTipText("Accelerator coefficient of social component.");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_8 = new JLabel("Min.Weight");
		lblNewLabel_8.setToolTipText("Minimum inertia weight coefficient.");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_9 = new JLabel("Max.Weight");
		lblNewLabel_9.setToolTipText("Starting inertia weight coefficient.");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		itersField = new JTextField();
		itersField.setColumns(10);
		itersField.setText(Integer.toString(Setup.ITERATIONS));
		
		c1Field = new JTextField();
		c1Field.setColumns(10);
		c1Field.setText(Double.toString(Setup.C1));
		
		c2Field = new JTextField();
		c2Field.setColumns(10);
		c2Field.setText(Double.toString(Setup.C2));
		
		minWeightField = new JTextField();
		minWeightField.setColumns(10);
		minWeightField.setText(Double.toString(Setup.WMIN));
		
		maxWeightField = new JTextField();
		maxWeightField.setColumns(10);
		maxWeightField.setText(Double.toString(Setup.WMAX));
		
		JButton runTestBtn = new JButton("Run");
		runTestBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				if (btn.getText() == "Run"){
					new RunTestCommand();
					btn.setText("Stop");
				}
				else if (btn.getText() == "Stop"){
					new StopTestCommand();
					btn.setText("Run");
				}
			}
		});
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window.getInstance().update();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
								.addComponent(testsLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addComponent(itersLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
								.addComponent(particlesLabel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStats)
								.addComponent(lblSource)
								.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 559, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblScoringTable)
										.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 563, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 566, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(100))))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblParticles_1)
												.addGap(18)
												.addComponent(swarmSizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(lblNewLabel)
													.addComponent(lblNewLabel_1)
													.addComponent(lblNewLabel_3))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addComponent(xmaxField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(dimsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(speedMinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
										.addGap(18)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_6)
											.addComponent(lblNewLabel_5)
											.addComponent(lblNewLabel_8)
											.addComponent(lblNewLabel_7)))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblNewLabel_4)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(speedMaxField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(lblNewLabel_9)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(maxWeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(minWeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(c2Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(c1Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(itersField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addComponent(runTestBtn, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addComponent(refreshBtn, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(testsLabel)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(itersLabel)
							.addComponent(particlesLabel)
							.addComponent(lblStats)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(11)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(lblScoringTable)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblSource)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblParticles_1)
						.addComponent(swarmSizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5)
						.addComponent(itersField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(dimsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_6)
						.addComponent(c1Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(xmaxField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_7)
						.addComponent(c2Field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(speedMinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_8)
						.addComponent(minWeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(speedMaxField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_9)
						.addComponent(maxWeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addComponent(runTestBtn)
					.addComponent(refreshBtn)
					.addContainerGap())
		);
		
		sourceTextArea = new JTextArea();
		scrollPane_6.setViewportView(sourceTextArea);
		sourceTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		sourceTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		sourceTextArea.setEditable(false);
		
		scoresTextArea = new JTextArea();
		scrollPane_5.setViewportView(scoresTextArea);
		scoresTextArea.setLineWrap(true);
		scoresTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		scoresTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		scoresTextArea.setEditable(false);
		
		propsTextArea = new JTextArea();
		propsTextArea.setLineWrap(true);
		scrollPane_4.setViewportView(propsTextArea);
		propsTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		propsTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		propsTextArea.setEditable(false);
		
		JList ParticlesList = new JList(particlesListModel);
		scrollPane_3.setViewportView(ParticlesList);
		ParticlesList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ParticlesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ParticlesList.setAutoscrolls(false);
		ParticlesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList lsm = (JList)e.getSource();
				int index = lsm.getSelectedIndex();
				if (index != -1)
					Window.getInstance().openParticle(index);
			}
		});
		
		JList IterationsList = new JList(iterationsListModel);
		scrollPane_2.setViewportView(IterationsList);
		IterationsList.setBorder(new LineBorder(new Color(0, 0, 0)));
		IterationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		IterationsList.setAutoscrolls(false);
		IterationsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList lsm = (JList)e.getSource();
				int index = lsm.getSelectedIndex();
				if (index != -1)
					Window.getInstance().openIteration(index);
			}
		});
		
		JList TestsList = new JList(testsListModel);
		scrollPane_1.setViewportView(TestsList);
		TestsList.setBorder(new LineBorder(new Color(0, 0, 0)));
		TestsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TestsList.setAutoscrolls(false);
		TestsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList lsm = (JList)e.getSource();
				int index = lsm.getSelectedIndex();
				if (index != -1)
					Window.getInstance().openTest(index);
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
	
	public void update(){
		findTests();
	}
	
	public void findTests(){
		File folder = new File("test");
		File[] listOfFiles = folder.listFiles();
		
		testsListModel.removeAllElements();
		
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isDirectory())
				testsListModel.addElement(listOfFiles[i].getName());
	}
	
	public void openTest(int index){
		testSelectedIndex = index;
		
		particlesListModel.removeAllElements();
		iterationsListModel.removeAllElements();

		String folderName = "test/" + testsListModel.get(index);
		//System.out.println("Opening test with name: " + folderName);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isDirectory())
				iterationsListModel.addElement(listOfFiles[i].getName());
		
		propsTextArea.setText("");
		propsTextArea.setCaretPosition(0);
		scoresTextArea.setText("");
		scoresTextArea.setCaretPosition(0);
		sourceTextArea.setText("");
		sourceTextArea.setCaretPosition(0);
	}
	
	public void openIteration(int index){
		iterationSelectedIndex = index;
		
		particlesListModel.removeAllElements();
		
		String folderName = "test/" + testsListModel.get(testSelectedIndex) + "/" + iterationsListModel.get(index);
		//System.out.println("Opening iteration with name: " + folderName);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isDirectory() && !listOfFiles[i].getName().equals("logs"))
				particlesListModel.addElement(listOfFiles[i].getName());
		
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
	
	public void openParticle(int index){
		particleSelectedIndex = index;
		String particleId = particlesListModel.get(index).toString();
		particleId = particleId.substring(particleId.length() - 5, particleId.length());
		
		String folderName = "test/" + testsListModel.get(testSelectedIndex) + "/" + iterationsListModel.get(iterationSelectedIndex) + "/" + particlesListModel.get(index);
		//System.out.println("Opening particle with name: " + folderName);
		
		sourceTextArea.removeAll();
		
		String propText = FileUtils.convertFileToString(folderName + "/data.log");
		propsTextArea.setText(propText);
		propsTextArea.setCaretPosition(0);
		
		String scoresText = FileUtils.convertFileToString(folderName + "/battle.log");
		scoresTextArea.setText(scoresText);
		scoresTextArea.setCaretPosition(0);
		
		String sourceText = FileUtils.convertFileToString(folderName + "/GSwarmRobot" + particleId + ".java");
		sourceTextArea.setText(sourceText);
		sourceTextArea.setCaretPosition(0);
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
}

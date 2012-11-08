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

public class Window extends JFrame {
	
	private static Window instance;

	private JPanel contentPane;
	
	private DefaultListModel testsListModel = new DefaultListModel();
	private DefaultListModel iterationsListModel = new DefaultListModel();
	private DefaultListModel particlesListModel = new DefaultListModel();
	
	private int testSelectedIndex = -1;
	private int iterationSelectedIndex = -1;
	private int particleSelectedIndex = -1;
	
	JTextArea propsTextArea;
	JTextArea scoresTextArea;
	JTextArea sourceTextArea;

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
		setBounds(100, 100, 1196, 722);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTest = new JMenu("Menu");
		menuBar.add(mnTest);
		
		JMenuItem mntmRunTest = new JMenuItem("Run test");
		mntmRunTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RunTestCommand();
			}
		});
		mnTest.add(mntmRunTest);
		
		JMenuItem mntmOpenTest = new JMenuItem("Open test");
		mnTest.add(mntmOpenTest);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnTest.add(mntmAbout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ExitCommand();
			}
		});
		mnTest.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JList TestsList = new JList(testsListModel);
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
		
		JList IterationsList = new JList(iterationsListModel);
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
		
		JList ParticlesList = new JList(particlesListModel);
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
		
		JLabel lblStats = new JLabel("Properties");
		lblStats.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		propsTextArea = new JTextArea();
		propsTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		propsTextArea.setEditable(false);
		
		JLabel lblScoringTable = new JLabel("Results");
		lblScoringTable.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		scoresTextArea = new JTextArea();
		scoresTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		scoresTextArea.setEditable(false);
		
		JLabel lblSource = new JLabel("Source");
		lblSource.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		sourceTextArea = new JTextArea();
		sourceTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
		sourceTextArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblTests = new JLabel("Tests");
		lblTests.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblIterations = new JLabel("Iterations");
		lblIterations.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblParticles = new JLabel("Particles");
		lblParticles.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(TestsList, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTests))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(IterationsList, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIterations))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(ParticlesList, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblParticles))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(sourceTextArea)
						.addComponent(scoresTextArea)
						.addComponent(propsTextArea, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
					.addGap(39)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblStats))
						.addComponent(lblSource)
						.addComponent(lblScoringTable))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTests)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblIterations)
							.addComponent(lblParticles)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(11)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(220)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(scoresTextArea, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblScoringTable))))
							.addGap(59)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(sourceTextArea, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSource)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(TestsList, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
								.addComponent(IterationsList, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
								.addComponent(ParticlesList, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
								.addComponent(propsTextArea, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStats))))
					.addContainerGap(79, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
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
		System.out.println("Opening test with name: " + folderName);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isDirectory())
				iterationsListModel.addElement(listOfFiles[i].getName());
	}
	
	public void openIteration(int index){
		iterationSelectedIndex = index;
		
		particlesListModel.removeAllElements();
		
		String folderName = "test/" + testsListModel.get(testSelectedIndex) + "/" + iterationsListModel.get(index);
		System.out.println("Opening iteration with name: " + folderName);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++)
			if (listOfFiles[i].isDirectory() && !listOfFiles[i].getName().equals("logs"))
				particlesListModel.addElement(listOfFiles[i].getName());
	}
	
	public void openParticle(int index){
		particleSelectedIndex = index;
		String particleId = particlesListModel.get(index).toString();
		particleId = particleId.substring(particleId.length() - 5, particleId.length());
		
		String folderName = "test/" + testsListModel.get(testSelectedIndex) + "/" + iterationsListModel.get(iterationSelectedIndex) + "/" + particlesListModel.get(index);
		System.out.println("Opening particle with name: " + folderName);
		
		propsTextArea.removeAll();
		
		String propText = "NAME: " + folderName + "\n"; 
		propText += "ITERATION: " + iterationsListModel.get(iterationSelectedIndex) + "\n";
		propsTextArea.setText(propText);
		
		String scoresText = FileUtils.convertFileToString(folderName + "/result.rsl");
		scoresTextArea.setText(scoresText);
		
		String sourceText = FileUtils.convertFileToString(folderName + "/GSwarmRobot" + particleId + ".java");
		sourceTextArea.setText(sourceText);
	}
}

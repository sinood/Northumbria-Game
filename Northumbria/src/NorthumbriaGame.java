import java.awt.EventQueue;

//import java.io.File;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Graphics;
//import java.awt.Component;
import java.awt.Color;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;

public class NorthumbriaGame {

	private Graphics g;
	
	private JFrame frame;
	private JLabel lbl1;
	private JTextField txt1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NorthumbriaGame window = new NorthumbriaGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * THE APPLICATION
	 */
	public NorthumbriaGame() {
		initialize();
	}

	/**
	 * INITIAL FRAME CONTENTS
	 */
	private void initialize() {
		//Creates the initial application window frame
		frame = new JFrame();
		frame.setBounds(500, 500, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//temporary button in the center of the window
		JButton mainButton1 = new JButton("Button 1");
		mainButton1.setBounds(349, 261, 100, 50);
		mainButton1.setBackground(new Color(100,0,0));
		mainButton1.setFont(new Font("Times New Roman", 10, 24));
		mainButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt1.setText("Testing Button 1");
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(mainButton1);
		
		//temporary text box at the bottom of the window
		txt1 = new JTextField();
		txt1.setBounds(0,525, 800, 50);
		txt1.setBackground(new Color(255,255,255));
		txt1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		txt1.setColumns(10);
		frame.getContentPane().add(txt1);
	}
}

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class selection_window {
	 int difficulty  = 1;
	JFrame frmSelectDifficulty;
	public static void main(String[] args) {
		
	}
	/**
	 * Launch the application.
	 */
	

	
	public selection_window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSelectDifficulty = new JFrame();
		frmSelectDifficulty.setTitle("Select Difficulty");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("CharaSShift.png");
		frmSelectDifficulty.setIconImage(img);

		frmSelectDifficulty.getContentPane().setBackground(Color.WHITE);
		frmSelectDifficulty.getContentPane().setForeground(Color.BLACK);
		frmSelectDifficulty.getContentPane().setLayout(null);
		JList list = new JList();
		list.setBounds(28, 83, 90, -28);
		frmSelectDifficulty.getContentPane().add(list);
		
		JRadioButton rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setBounds(24, 23, 137, 32);
		frmSelectDifficulty.getContentPane().add(rdbtnEasy);
		rdbtnEasy.setSelected(true);
		
		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setBounds(24, 84, 137, 32);
		frmSelectDifficulty.getContentPane().add(rdbtnNormal);
		
		JRadioButton rdbtnHard = new JRadioButton("Hard");
		rdbtnHard.setBounds(24, 144, 137, 32);
		frmSelectDifficulty.getContentPane().add(rdbtnHard);
		ButtonGroup grup = new ButtonGroup();
		grup.add(rdbtnEasy);
		grup.add(rdbtnNormal);
		grup.add(rdbtnHard);
		JButton btnNewButton = new JButton("Select");
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (rdbtnEasy.isSelected()) {//easy
					
					Trio_sub trio = new Trio_sub(3);
					frmSelectDifficulty.dispose();
				}
				else if (rdbtnNormal.isSelected()) {//normal
					
					Trio_sub trio = new Trio_sub(2);
					frmSelectDifficulty.dispose();
				}
				else {//hard
					
					Trio_sub trio = new Trio_sub(1);
					frmSelectDifficulty.dispose();
					
				}
				
			}
		});
		btnNewButton.setBounds(219, 157, 175, 41);
		frmSelectDifficulty.getContentPane().add(btnNewButton);
		btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		
		
		frmSelectDifficulty.setForeground(Color.BLACK);
		frmSelectDifficulty.setBackground(Color.WHITE);
		frmSelectDifficulty.setAlwaysOnTop(true);
		frmSelectDifficulty.setBounds(100, 100, 450, 300);
		frmSelectDifficulty.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

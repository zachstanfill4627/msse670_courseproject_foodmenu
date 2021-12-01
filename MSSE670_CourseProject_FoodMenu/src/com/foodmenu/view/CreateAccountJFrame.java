package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.view.LoginJFrame.loginButtonListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CreateAccountJFrame extends JFrame {
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confirmPassField;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField recPhraseField;
	private JSpinner ageSpinner = new JSpinner();
	private JComboBox userLevelDropdown; 

	public CreateAccountJFrame() throws IOException {
		super("Food Menu Create Account");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(479, 569);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Create Food Menu Account");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLabel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLabel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		emailLabel.setBounds(10, 148, 222, 26);
		getContentPane().add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Calibri", Font.BOLD, 16));
		emailField.setColumns(10);
		emailField.setBounds(10, 180, 443, 26);
		getContentPane().add(emailField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.BOLD, 16));
		passwordField.setBounds(10, 248, 222, 26);
		getContentPane().add(passwordField);
		
		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		confirmPasswordLabel.setBounds(238, 224, 215, 18);
		getContentPane().add(confirmPasswordLabel);
		
		confirmPassField = new JPasswordField();
		confirmPassField.setFont(new Font("Calibri", Font.BOLD, 16));
		confirmPassField.setBounds(238, 248, 215, 26);
		getContentPane().add(confirmPassField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		passwordLabel.setBounds(10, 224, 222, 18);
		getContentPane().add(passwordLabel);
		
		JButton createButton = new JButton("Create Account");
		createButton.addActionListener(new createButtonListener());
		createButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createButton.setBounds(286, 490, 167, 29);
		getContentPane().add(createButton);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		firstNameLabel.setBounds(10, 90, 222, 18);
		getContentPane().add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		lastNameLabel.setBounds(238, 90, 215, 18);
		getContentPane().add(lastNameLabel);
		
		fNameField = new JTextField();
		fNameField.setFont(new Font("Calibri", Font.BOLD, 14));
		fNameField.setColumns(10);
		fNameField.setBounds(10, 113, 222, 24);
		getContentPane().add(fNameField);
		
		lNameField = new JTextField();
		lNameField.setFont(new Font("Calibri", Font.BOLD, 14));
		lNameField.setColumns(10);
		lNameField.setBounds(238, 113, 215, 24);
		getContentPane().add(lNameField);
		
		JLabel recoveryPhraseLabel = new JLabel("Password Recovery Question");
		recoveryPhraseLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		recoveryPhraseLabel.setBounds(10, 293, 443, 18);
		getContentPane().add(recoveryPhraseLabel);
		
		recPhraseField = new JTextField();
		recPhraseField.setFont(new Font("Calibri", Font.BOLD, 16));
		recPhraseField.setColumns(10);
		recPhraseField.setBounds(10, 338, 443, 26);
		getContentPane().add(recPhraseField);
		
		JLabel recoveryPhraseTextLabel = new JLabel("What do you want to be when you grow up?");
		recoveryPhraseTextLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		recoveryPhraseTextLabel.setBounds(10, 317, 443, 15);
		getContentPane().add(recoveryPhraseTextLabel);
		
		userLevelDropdown = new JComboBox<Object>();
		userLevelDropdown.setModel(new DefaultComboBoxModel(new String[] {"user", "admin"}));
		userLevelDropdown.setSelectedIndex(0);
		userLevelDropdown.setFont(new Font("Calibri", Font.BOLD, 14));
		userLevelDropdown.setBackground(Color.WHITE);
		userLevelDropdown.setBounds(10, 492, 150, 24);
		getContentPane().add(userLevelDropdown);
		
		JLabel permLevelLabel = new JLabel("Permissions Level");
		permLevelLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		permLevelLabel.setBounds(10, 461, 222, 18);
		getContentPane().add(permLevelLabel);
		
		JLabel AgeLabel = new JLabel("Age");
		AgeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		AgeLabel.setBounds(10, 389, 443, 18);
		getContentPane().add(AgeLabel);
		
		ageSpinner = new JSpinner();
		ageSpinner.setModel(new SpinnerNumberModel(21, 0, 100, 1));
		ageSpinner.setBounds(49, 386, 58, 20);
		getContentPane().add(ageSpinner);
	}
	
	class createButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String fName = fNameField.getText();
			String lName = lNameField.getText();
			String email = emailField.getText();
			String recPhrase = recPhraseField.getText();
			String password = "";
			int age = (int) ageSpinner.getValue();
			String role = userLevelDropdown.getSelectedItem().toString();
			
			if(passwordField.getText().equals(confirmPassField.getText())) {
				password = passwordField.getText();
			} else {
				JOptionPane.showMessageDialog(null, "Passwords do not match!");
				return;
			}
			
			User user = new User(fName, lName, email, password, recPhrase, age, role);
			
			UserManager userManager = new UserManager();
			
			try {
				if(userManager.addNewUser(user)) {
					try {
						JOptionPane.showMessageDialog(null, "User Account Created!\nUsername = " + email);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					setVisible(false);
					dispose();
				} else {
					if(!userManager.isValidPassword(password)) {
						JOptionPane.showMessageDialog(null, "Password not Strong Enough!\n\nSee Administrator for password complexity requirements!");
					} else {
						JOptionPane.showMessageDialog(null, "Problem with User Account Creation!");
					}
					return;
				}
			} catch (ServiceLoadException | UserServiceException | HeadlessException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

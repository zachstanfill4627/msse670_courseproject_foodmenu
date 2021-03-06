package com.foodmenu.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;

public class LoginJFrame extends JFrame {
	private JTextField unameField;
	private JPasswordField passwordField;
	
	public LoginJFrame() throws IOException {
		super("Food Menu Login");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(479, 350);
		getContentPane().setLayout(null);
		
		JLabel titleLablel = new JLabel("Food Menu Login");
		titleLablel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLablel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLablel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLablel);
		
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new createAccountButtonListener());
		createAccountButton.setFont(new Font("Calibri", Font.BOLD, 12));
		createAccountButton.setBounds(325, 46, 128, 23);
		getContentPane().add(createAccountButton);
		
		JLabel unameLabel = new JLabel("Username");
		unameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		unameLabel.setBounds(10, 94, 222, 24);
		getContentPane().add(unameLabel);
		
		unameField = new JTextField();
		unameField.setFont(new Font("Calibri", Font.BOLD, 16));
		unameField.setColumns(10);
		unameField.setBounds(10, 121, 443, 26);
		getContentPane().add(unameField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		passwordLabel.setBounds(10, 153, 222, 24);
		getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.BOLD, 16));
		passwordField.setBounds(10, 180, 443, 26);
		getContentPane().add(passwordField);
		
		JButton rstPasswordButton = new JButton("Reset Password");
		rstPasswordButton.addActionListener(new resetPWButtonListener());
		rstPasswordButton.setFont(new Font("Calibri", Font.BOLD, 16));
		rstPasswordButton.setBounds(10, 271, 178, 29);
		getContentPane().add(rstPasswordButton);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new loginButtonListener());
		loginButton.setFont(new Font("Calibri", Font.BOLD, 16));
		loginButton.setBounds(335, 271, 118, 29);
		getContentPane().add(loginButton);
	}
	
	class createAccountButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			CreateAccountJFrame createAccountJFrame;
			try {
				createAccountJFrame = new CreateAccountJFrame();
				createAccountJFrame.setVisible(true);
				createAccountJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosed(java.awt.event.WindowEvent windowEvent) {
						setVisible(true);						
					}
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
	}
	
	class loginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String username = unameField.getText();
			String password = passwordField.getText();
			
			UserManager userManager = new UserManager();
			
			try {
				if(!userManager.authenticateUser(username, password)) {
					JOptionPane.showMessageDialog(null, "Supplied Username or Password is incorrect!");
				} else {
					JOptionPane.showMessageDialog(null, "Authentication Succeeded!");
					User user = new User();
					user = userManager.retrieveUser(username);
					setVisible(false);
					FoodMenuJFrame foodMenuJFrame = new FoodMenuJFrame();
					foodMenuJFrame.setUser(user);
					foodMenuJFrame.setVisible(true);
				}
			} catch (ServiceLoadException | UserServiceException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class resetPWButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String email = JOptionPane.showInputDialog("Resetting Password!\n\nWhat is your email address?");
			int age = 0;
			try {	
				age = Integer.parseInt(JOptionPane.showInputDialog("Resetting Password!\n\nHow old were you when this account was created?"));
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid age input...\n\n Number Characters Only!\n\nPlease Try Again!");
				return;
			}
				
			String recPhrase = JOptionPane.showInputDialog("Resetting Password!\n\nRecovery Question\nWhat do you want to be when you grown up?");
			
			if (email == null || age == 0 || recPhrase == null) {
				JOptionPane.showMessageDialog(null, "User Identity could not be verified because the Username, Age, or Recovery Phrase were incorrect!\n\nPlease try again!");
				return;
			}
			
			String password1 = "";
			String password2 = "";
			
			UserManager userManager = new UserManager();
			
			try {
				if(userManager.resetUserPassword(email, age, recPhrase)) {
					try {
						do {
							if(password1.equals(password2) && !password1.equals("")) {
								JOptionPane.showMessageDialog(null, "Password doesn't meet Password Policy Standards!\n\n Please try again!");
							} else if (!password1.equals("")) {
								JOptionPane.showMessageDialog(null, "Passwords didn't match!\n\n Please try again!");
							}
							password1 = JOptionPane.showInputDialog("Resetting Password!\n\nIdentity Confirmed\nPlease enter your new password:");
							password2 = JOptionPane.showInputDialog("Resetting Password!\n\nIdentity Confirmed\nPlease Confirm your new password:");
						} while (!userManager.resetUserPassword(email, password1) || !password1.equals(password2));
						JOptionPane.showMessageDialog(null, "User Password reset successfully");
					} catch (HeadlessException | UserServiceException | ServiceLoadException | IOException e1) {
						e1.printStackTrace();
					} catch (NullPointerException e2) {
						JOptionPane.showMessageDialog(null, "User Exited Password Reset!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "User Identity could not be verified because the Username, Age, or Recovery Phrase were incorrect!\n\nPlease try again!");			
				}
			} catch (HeadlessException | ServiceLoadException | UserServiceException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}
}

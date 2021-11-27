package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;
import com.foodmenu.view.CreateAccountJFrame.createButtonListener;

import javax.swing.JTable;
import java.awt.Component;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

public class FoodMenuJFrame extends JFrame {

	private User user = null;
	private User selectedUser = null;
	private UsersTableModel usersModel = new UsersTableModel();
	private int selectedRow = -1;
	
	private UserManager userManager = new UserManager(); 
	
	private JTable usersTable;
	
    public void setUser(User user) {
        this.user = user;
        
        try {
			usersModel.setUsers(userManager.retrieveAllUsers());
		} catch (ServiceLoadException | UserServiceException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("serial")
	public FoodMenuJFrame() {
		super("Food Menu");
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setSize(850, 679);
		getContentPane().setLayout(null);
		
		//create Box containers with BoxLayout
		Box dayMenus = Box.createHorizontalBox();
		Box menuItems = Box.createHorizontalBox();
		Box foodItems = Box.createHorizontalBox();
		Box userAccounts = Box.createHorizontalBox();
				
	   // create a JTabbedPane
	   JTabbedPane tabs = new JTabbedPane(
			   JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	   tabs.setBounds(0, 0, 834, 640);
	   
	   //place each container on tabbed pane
	   tabs.addTab("Day Menus", dayMenus);
	   tabs.addTab("Menu Items", menuItems);
	   tabs.addTab("Food Items", foodItems);
	   tabs.addTab("User Accounts", userAccounts);
	   
	   JPanel panel = new JPanel();
	   userAccounts.add(panel);
	   panel.setLayout(null);
	   
	   JScrollPane scrollPane = new JScrollPane();
	   scrollPane.setBounds(10, 0, 809, 548);
	   panel.add(scrollPane);
	      
	   usersTable = new JTable();
	   usersTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
	   usersTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   usersTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null, null},
	   		{null, null, null, null, null},
	   		{null, null, null, null, null},
	   		{null, null, null, null, null},
	   		{null, null, null, null, null},
	   	},
	   	new String[] {
	   		"First Name", "Last Name", "Email", "Age", "Role"
	   	}
	   ) {
	   	@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
	   		String.class, String.class, String.class, Integer.class, String.class
	   	};
	   	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
	   		return columnTypes[columnIndex];
	   	}
	   });
	   scrollPane.setViewportView(usersTable);
	   
	   usersTable.setModel(usersModel);  
       usersTable.addMouseListener(new selectUserMouseClickListener());
	   
	   JButton rstPasswordButton = new JButton("Reset Password");
	   rstPasswordButton.addActionListener(new rstPasswordButtonListener());
	   rstPasswordButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   rstPasswordButton.setBounds(678, 578, 141, 23);
	   panel.add(rstPasswordButton);
	   
	   JButton deleteUserButton = new JButton("Delete User");
	   deleteUserButton.addActionListener(new deleteUserButtonListener());
	   deleteUserButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteUserButton.setBounds(10, 576, 143, 23);
	   panel.add(deleteUserButton);
	   getContentPane().add(tabs); // place tabbed pane on frame
		
	}
	
	class rstPasswordButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newPassword = JOptionPane.showInputDialog("Resetting Password for user \n" + selectedUser.getEmailAddress());
			
			selectedUser.setPassword(newPassword);
			try {
				if(userManager.resetUserPassword(selectedUser)) {
					JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was successfully set!");
				} else {
					JOptionPane.showMessageDialog(null, "Error in setting " + selectedUser.getEmailAddress() + " password!");
				}
			} catch (ServiceLoadException | UserServiceException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class deleteUserButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this User?\n" + selectedUser.getEmailAddress(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(userManager.deleteUser(selectedUser)) {
						JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was successfully deleted!");
						usersModel.setUsers(userManager.retrieveAllUsers());
						usersModel.fireTableDataChanged();
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete user \n" + selectedUser.getEmailAddress());
					}
				} catch (ServiceLoadException | UserServiceException e1) {
					e1.printStackTrace();
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was not deleted!");
			}
			
		}
	}	
	
	class selectUserMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int row = usersTable.getSelectedRow();
			String email = usersTable.getModel().getValueAt(row, 2).toString();
			
			try {
				selectedUser = userManager.retrieveUser(email);
			} catch (ServiceLoadException | UserServiceException e1) {
				e1.printStackTrace();
			}
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
	}
	

}

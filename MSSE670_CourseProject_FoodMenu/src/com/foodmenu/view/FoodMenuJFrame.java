package com.foodmenu.view;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.*;
import com.foodmenu.model.domain.*;
import com.foodmenu.model.services.exceptions.*;
import com.foodmenu.view.tableModels.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class FoodMenuJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private User user = null;
	private User selectedUser = null;
	private FoodItem selectedFoodItem = null;
	
	private UsersTableModel usersModel = new UsersTableModel();
	private FoodItemsTableModel foodItemsModel = new FoodItemsTableModel();
	private IngredientsTableModel ingredientsModel = new IngredientsTableModel();
	private RecipeTableModel recipeModel = new RecipeTableModel();
	
	private int selectedRow = -1;
	
	private UserManager userManager = new UserManager();
	private FoodItemManager foodItemManager = new FoodItemManager();
	
	private JTable usersTable;
	private JTable foodItemsTable;
	private JTable ingredientsTable;
	private JTable recipeTable;
	private JTable menuItemTable;
	private JTable menuFoodItemsTable;
	private JTable menuIngredientsTable;
	
    public void setUser(User user) {
        this.user = user;
        
        try {
			usersModel.setUsers(userManager.retrieveAllUsers());
		} catch (ServiceLoadException | UserServiceException e) {
			e.printStackTrace();
		}
        
        try {
			foodItemsModel.setFoodItems(foodItemManager.retrieveAllFoodItems());
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("serial")
	public FoodMenuJFrame() {
		super("Food Menu");
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setSize(850, 679);
		
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
	   
	   JPanel panel = new JPanel();
	   menuItems.add(panel);
	   panel.setLayout(null);
	   
	   JScrollPane menuItemScrollPane = new JScrollPane();
	   menuItemScrollPane.setBounds(10, 5, 809, 177);
	   panel.add(menuItemScrollPane);
	   
	   menuItemTable = new JTable();
	   menuItemTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   menuItemScrollPane.setViewportView(menuItemTable);
	   menuItemTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Meal Name", "Complexity Value", "HealthValue"
	   	}
	   ));
	   
	   JScrollPane menuFoodItemScrollPane = new JScrollPane();
	   menuFoodItemScrollPane.setBounds(10, 193, 522, 334);
	   panel.add(menuFoodItemScrollPane);
	   
	   menuFoodItemsTable = new JTable();
	   menuFoodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   menuFoodItemScrollPane.setViewportView(menuFoodItemsTable);
	   
	   JScrollPane menuIngredientsScrollPane = new JScrollPane();
	   menuIngredientsScrollPane.setBounds(539, 193, 280, 338);
	   panel.add(menuIngredientsScrollPane);
	   
	   menuIngredientsTable = new JTable();
	   menuIngredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   menuIngredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   menuIngredientsScrollPane.setViewportView(menuIngredientsTable);
	   tabs.addTab("Food Items", foodItems);
	   
	   JPanel foodItemsPanel = new JPanel();
	   foodItems.add(foodItemsPanel);
	   foodItemsPanel.setBounds(10, 0, 809, 548);
	   foodItemsPanel.setLayout(null);
	   
	   JScrollPane foodItemsScrollPane = new JScrollPane();
	   foodItemsScrollPane.setBounds(10, 5, 809, 262);
	   foodItemsPanel.add(foodItemsScrollPane);
	   
	   foodItemsTable = new JTable();
	   foodItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   foodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   foodItemsScrollPane.setViewportView(foodItemsTable);
	   foodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   
	   foodItemsTable.setModel(foodItemsModel);
	   foodItemsTable.addMouseListener(new selectFoodItemMouseClickListener());
	   
	   JScrollPane ingredientsScrollPane = new JScrollPane();
	   ingredientsScrollPane.setBounds(10, 274, 285, 262);
	   foodItemsPanel.add(ingredientsScrollPane);
	   
	   ingredientsTable = new JTable();
	   ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   ingredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   ingredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   ingredientsScrollPane.setViewportView(ingredientsTable);
	   
	   ingredientsTable.setModel(ingredientsModel);
	   
	   JScrollPane recipeScrollPane = new JScrollPane();
	   recipeScrollPane.setBounds(303, 274, 516, 262);
	   foodItemsPanel.add(recipeScrollPane);
	   
	   recipeTable = new JTable();
	   recipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   recipeTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   recipeTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Steps"
	   	}
	   ));
	   recipeScrollPane.setViewportView(recipeTable);
	   
	   recipeTable.setModel(recipeModel);
	   
	   tabs.addTab("User Accounts", userAccounts);
	   
	   JPanel usersPanel = new JPanel();
	   userAccounts.add(usersPanel);
	   usersPanel.setLayout(null);
	   
	   JScrollPane usersScrollPane = new JScrollPane();
	   usersScrollPane.setBounds(10, 0, 809, 548);
	   usersPanel.add(usersScrollPane);
	      
	   usersTable = new JTable();
	   usersTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
	   usersTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   usersTable.setModel(new DefaultTableModel(
	   	new Object[][] {
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
	   usersScrollPane.setViewportView(usersTable);
	   
	   usersTable.setModel(usersModel);  
       usersTable.addMouseListener(new selectUserMouseClickListener());
	   
	   JButton rstPasswordButton = new JButton("Reset Password");
	   rstPasswordButton.addActionListener(new rstPasswordButtonListener());
	   rstPasswordButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   rstPasswordButton.setBounds(678, 578, 141, 23);
	   usersPanel.add(rstPasswordButton);
	   
	   JButton deleteUserButton = new JButton("Delete User");
	   deleteUserButton.addActionListener(new deleteUserButtonListener());
	   getContentPane().setLayout(null);
	   deleteUserButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteUserButton.setBounds(10, 576, 143, 23);
	   usersPanel.add(deleteUserButton);
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
	
	class selectFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int row = foodItemsTable.getSelectedRow();
			String foodName = foodItemsTable.getModel().getValueAt(row, 0).toString();

			try {
				selectedFoodItem = foodItemManager.retrieveFoodItem(foodName);
				ingredientsModel.setFoodItem(selectedFoodItem);
				ingredientsModel.fireTableDataChanged();
				recipeModel.setFoodItem(selectedFoodItem);
				recipeModel.fireTableDataChanged();
			} catch (ServiceLoadException | FoodItemServiceException e1) {
				e1.printStackTrace();
			}

		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
	}
}

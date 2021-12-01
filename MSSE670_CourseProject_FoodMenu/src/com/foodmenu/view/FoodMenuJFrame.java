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
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class FoodMenuJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private User user = null;
	
	private User selectedUser = null;
	private FoodItem selectedFoodItem = null;
	private MenuItem selectedMenuItem = null;
	private DayMenu selectedDayMenu = null;
	
	private UsersTableModel usersModel = new UsersTableModel();
	private FoodItemsTableModel foodItemsModel = new FoodItemsTableModel();
	private FoodItemsTableModel foodItemsMenuItemModel = new FoodItemsTableModel();
	private FoodItemsTableModel foodItemsDayMenuModel = new FoodItemsTableModel();
	private IngredientsTableModel ingredientsFoodItemModel = new IngredientsTableModel();
	private IngredientsTableModel ingredientsMenuItemModel = new IngredientsTableModel();
	private IngredientsTableModel ingredientsDayMenuModel = new IngredientsTableModel();
	private MenuItemsTableModel menuItemsModel = new MenuItemsTableModel();
	private MenuItemsTableModel menuItemsDayMenuModel = new MenuItemsTableModel();
	private DayMenuTableModel dayMenuModel = new DayMenuTableModel();
	private RecipeTableModel recipeModel = new RecipeTableModel();
	
	private java.util.Date date; 
	
	private UserManager userManager = new UserManager();
	private FoodItemManager foodItemManager = new FoodItemManager();
	private MenuItemManager menuItemManager = new MenuItemManager();
	private DayMenuManager	dayMenuManager = new DayMenuManager();
	
	private JTable usersTable;
	private JTable foodItemsTable;
	private JTable ingredientsTable;
	private JTable recipeTable;
	private JTable menuItemsTable;
	private JTable menuFoodItemsTable;
	private JTable menuIngredientsTable;
	private JTable dayMenusTable;
	private JTable dayMenuMenuItemsTable;
	private JTable dayMenuFoodItemsTable;
	private JTable dayMenuIngredientsTable;
	
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
        
        try {
			menuItemsModel.setMenuItems(menuItemManager.retrieveAllMenuItems());
		} catch (ServiceLoadException | MenuItemServiceException | FoodItemServiceException e) {
			e.printStackTrace();
		}
        
        try {
			dayMenuModel.setDayMenus(dayMenuManager.retrieveAllDayMenus());
		} catch (ServiceLoadException | DayMenuServiceException | MenuItemServiceException
				| FoodItemServiceException e) {
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("serial")
	public FoodMenuJFrame() throws IOException {
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
	   
	   JPanel dayMenusPanel = new JPanel();
	   dayMenus.add(dayMenusPanel);
	   dayMenusPanel.setLayout(null);
	   
	   JScrollPane dayMenuScrollPane = new JScrollPane();
	   dayMenuScrollPane.setBounds(10, 11, 809, 218);
	   dayMenusPanel.add(dayMenuScrollPane);
	   
	   dayMenusTable = new JTable();
	   dayMenusTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   dayMenusTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenusTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Date", "Complexity Value", "Health Value"
	   	}
	   ));
	   dayMenuScrollPane.setViewportView(dayMenusTable);
	   
	   dayMenusTable.setModel(dayMenuModel);
	   dayMenusTable.addMouseListener(new selectDayMenuMouseClickListener());
	   
	   JScrollPane dayMenuMenuItemsScrollPane = new JScrollPane();
	   dayMenuMenuItemsScrollPane.setBounds(10, 240, 405, 281);
	   dayMenusPanel.add(dayMenuMenuItemsScrollPane);
	   
	   dayMenuMenuItemsTable = new JTable();
	   dayMenuMenuItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Meal Name", "Complexity Value", "Health Value"
	   	}
	   ));
	   dayMenuMenuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuMenuItemsScrollPane.setViewportView(dayMenuMenuItemsTable);
	   
	   JScrollPane dayMenuFoodItemsScrollPane = new JScrollPane();
	   dayMenuFoodItemsScrollPane.setBounds(426, 240, 393, 141);
	   dayMenusPanel.add(dayMenuFoodItemsScrollPane);
	   
	   dayMenuMenuItemsTable.setModel(menuItemsDayMenuModel);
	   
	   dayMenuFoodItemsTable = new JTable();
	   dayMenuFoodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{"", "", null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   dayMenuFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuFoodItemsScrollPane.setViewportView(dayMenuFoodItemsTable);
	   
	   dayMenuFoodItemsTable.setModel(foodItemsDayMenuModel);
	   
	   JScrollPane dayMenuIngredientsScrollPane = new JScrollPane();
	   dayMenuIngredientsScrollPane.setBounds(425, 392, 394, 129);
	   dayMenusPanel.add(dayMenuIngredientsScrollPane);
	   
	   dayMenuIngredientsTable = new JTable();
	   dayMenuIngredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   dayMenuIngredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuIngredientsScrollPane.setViewportView(dayMenuIngredientsTable);
	   
	   dayMenuIngredientsTable.setModel(ingredientsDayMenuModel);
	   
	   JButton createDayMenuButton = new JButton("Create New Day Menu");
	   createDayMenuButton.addActionListener(new createDayMenuButtonListener());
	   createDayMenuButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createDayMenuButton.setBounds(621, 578, 198, 23);
	   dayMenusPanel.add(createDayMenuButton);
	   
	   JButton deleteDayMenuButton = new JButton("Delete Day Menu");
	   deleteDayMenuButton.addActionListener(new deleteDayMenuButtonListener());
	   deleteDayMenuButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteDayMenuButton.setBounds(10, 576, 164, 23);
	   dayMenusPanel.add(deleteDayMenuButton);
	   
	   tabs.addTab("Menu Items", menuItems);
	   
	   JPanel menuItemsPanel = new JPanel();
	   menuItems.add(menuItemsPanel);
	   menuItemsPanel.setLayout(null);
	   
	   JScrollPane menuItemScrollPane = new JScrollPane();
	   menuItemScrollPane.setBounds(10, 5, 809, 177);
	   menuItemsPanel.add(menuItemScrollPane);
	   
	   menuItemsTable = new JTable();
	   menuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   menuItemScrollPane.setViewportView(menuItemsTable);
	   menuItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Meal Name", "Complexity Value", "HealthValue"
	   	}
	   ));
	   
	   JScrollPane menuFoodItemScrollPane = new JScrollPane();
	   menuFoodItemScrollPane.setBounds(10, 193, 522, 334);
	   menuItemsPanel.add(menuFoodItemScrollPane);
	   
	   menuItemsTable.setModel(menuItemsModel);
	   menuItemsTable.addMouseListener(new selectMenuItemMouseClickListener());
	   
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
	   
	   menuFoodItemsTable.setModel(foodItemsMenuItemModel);
	   
	   JScrollPane menuIngredientsScrollPane = new JScrollPane();
	   menuIngredientsScrollPane.setBounds(539, 193, 280, 338);
	   menuItemsPanel.add(menuIngredientsScrollPane);
	   
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
	   
	   menuIngredientsTable.setModel(ingredientsMenuItemModel);
	   
	   JButton createMenuItemButton = new JButton("Create New Menu Item");
	   createMenuItemButton.addActionListener(new createMenuItemButtonListener());
	   createMenuItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createMenuItemButton.setBounds(621, 578, 198, 23);
	   menuItemsPanel.add(createMenuItemButton);
	   
	   JButton deleteMenuItemButton = new JButton("Delete Menu Item");
	   deleteMenuItemButton.addActionListener(new deleteMenuItemButtonListener());
	   deleteMenuItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteMenuItemButton.setBounds(10, 576, 164, 23);
	   menuItemsPanel.add(deleteMenuItemButton);
	   
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
	   ingredientsTable.setRowSelectionAllowed(false);
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
	   
	   ingredientsTable.setModel(ingredientsFoodItemModel);
	   
	   JScrollPane recipeScrollPane = new JScrollPane();
	   recipeScrollPane.setBounds(303, 274, 516, 262);
	   foodItemsPanel.add(recipeScrollPane);
	   
	   recipeTable = new JTable();
	   recipeTable.setRowSelectionAllowed(false);
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
	   
	   JButton createFoodItemButton = new JButton("Create New Food Item");
	   createFoodItemButton.addActionListener(new createFoodItemButtonListener());
	   createFoodItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createFoodItemButton.setBounds(621, 578, 198, 23);
	   foodItemsPanel.add(createFoodItemButton);
	   
	   JButton deleteFoodItemButton = new JButton("Delete Food Item");
	   deleteFoodItemButton.addActionListener(new deleteFoodItemButtonListener());
	   deleteFoodItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteFoodItemButton.setBounds(10, 576, 164, 23);
	   foodItemsPanel.add(deleteFoodItemButton);
	   
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
			} catch (ServiceLoadException | UserServiceException | HeadlessException | IOException e1) {
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
	
	class createFoodItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CreateFoodItemJFrame createFoodItemJFrame;
			
			createFoodItemJFrame = new CreateFoodItemJFrame();
			createFoodItemJFrame.setVisible(true);
			
			createFoodItemJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
					JOptionPane.showMessageDialog(null, "Refreshing Food Items Table!!");
			        try {
						foodItemsModel.setFoodItems(foodItemManager.retrieveAllFoodItems());
						foodItemsModel.fireTableDataChanged();
					} catch (ServiceLoadException | FoodItemServiceException e) {
						e.printStackTrace();
					}
			        
					String foodName = foodItemsTable.getModel().getValueAt(0, 0).toString();

					try {
						selectedFoodItem = foodItemManager.retrieveFoodItem(foodName);
						ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
						ingredientsFoodItemModel.fireTableDataChanged();
						recipeModel.setFoodItem(selectedFoodItem);
						recipeModel.fireTableDataChanged();
					} catch (ServiceLoadException | FoodItemServiceException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}
	
	class deleteFoodItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Food Item?\n" + selectedFoodItem.getFoodName(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(foodItemManager.deleteFoodItem(selectedFoodItem)) {
						JOptionPane.showMessageDialog(null, "Food Item \"" + selectedFoodItem.getFoodName() + "\" was successfully deleted!");

						ArrayList<FoodItem> foodItems = foodItemManager.retrieveAllFoodItems(); 
						
						foodItemsModel.setFoodItems(foodItems);
						foodItemsModel.fireTableDataChanged();
						
						if(foodItems.size() == 0) {
							ingredientsFoodItemModel.setFoodItem(new ArrayList<String>());
							ingredientsFoodItemModel.fireTableDataChanged();
							recipeModel.setFoodItem(new FoodItem());
							recipeModel.fireTableDataChanged();
							return;
						}
						
						String foodName = foodItemsTable.getModel().getValueAt(0, 0).toString();

						try {
							selectedFoodItem = foodItemManager.retrieveFoodItem(foodName);
							ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
							ingredientsFoodItemModel.fireTableDataChanged();
							recipeModel.setFoodItem(selectedFoodItem);
							recipeModel.fireTableDataChanged();
						} catch (ServiceLoadException | FoodItemServiceException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete food item \"" + selectedFoodItem.getFoodName() + "\"");
					}
				} catch (ServiceLoadException | FoodItemServiceException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Food Item \"" + selectedFoodItem.getFoodName() + "\" was not deleted!");
			}
		}
	}
	
	class createMenuItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CreateMenuItemJFrame createMenuItemJFrame;
			
			createMenuItemJFrame = new CreateMenuItemJFrame();
			createMenuItemJFrame.setVisible(true);
			
			createMenuItemJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
					JOptionPane.showMessageDialog(null, "Refreshing Menu Items Table!!");
					try {
						menuItemsModel.setMenuItems(menuItemManager.retrieveAllMenuItems());
						menuItemsModel.fireTableDataChanged();
					} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e) {
						e.printStackTrace();
					}
			        
					String mealName = menuItemsTable.getModel().getValueAt(0, 0).toString();
					ArrayList<String> ingredients = new ArrayList<String>();

					try {
						foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
						foodItemsMenuItemModel.fireTableDataChanged();
						menuItemManager.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
							ingredients.addAll(food.getIngredients());
						}});
						ingredientsMenuItemModel.setFoodItem(ingredients);
						ingredientsMenuItemModel.fireTableDataChanged();
					} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}
	
	class deleteMenuItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Menu Item?\n" + selectedMenuItem.getMealName(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(menuItemManager.deleteMenuItem(selectedMenuItem)) {
						JOptionPane.showMessageDialog(null, "Menu Item \"" + selectedMenuItem.getMealName() + "\" was successfully deleted!");
						
						ArrayList<MenuItem> menuItems = menuItemManager.retrieveAllMenuItems(); 
						
						menuItemsModel.setMenuItems(menuItems);
						menuItemsModel.fireTableDataChanged();
											
						ArrayList<String> ingredients = new ArrayList<String>();
						
						if(menuItems.size() == 0) {
							foodItemsMenuItemModel.setFoodItems(new ArrayList<FoodItem>());
							foodItemsMenuItemModel.fireTableDataChanged();
							ingredientsMenuItemModel.setFoodItem(ingredients);
							ingredientsMenuItemModel.fireTableDataChanged();														
							return;
						}
						
						String mealName = menuItemsTable.getModel().getValueAt(0, 0).toString();
						
						try {
							foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
							foodItemsMenuItemModel.fireTableDataChanged();
							menuItemManager.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
								ingredients.addAll(food.getIngredients());
							}});
							ingredientsMenuItemModel.setFoodItem(ingredients);
							ingredientsMenuItemModel.fireTableDataChanged();
						} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete menu item \"" + selectedMenuItem.getMealName() + "\"");
					}
				} catch (ServiceLoadException | FoodItemServiceException | HeadlessException | MenuItemServiceException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Menu Item \"" + selectedMenuItem.getMealName() + "\" was not deleted!");
			}	
		}
	}
	
	class createDayMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CreateDayMenuJFrame createDayMenuJFrame;
			
			createDayMenuJFrame = new CreateDayMenuJFrame();
			createDayMenuJFrame.setVisible(true);
			
			createDayMenuJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
					JOptionPane.showMessageDialog(null, "Refreshing Day Menu Table!!");
					
					try {
						dayMenuModel.setDayMenus(dayMenuManager.retrieveAllDayMenus());
						dayMenuModel.fireTableDataChanged();
					} catch (ServiceLoadException | DayMenuServiceException | MenuItemServiceException
							| FoodItemServiceException e) {
						e.printStackTrace();
					}
					
				}
			});
		}
	}
	
	class deleteDayMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Day Menu?\n" + selectedDayMenu.getDateString(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(dayMenuManager.deleteDayMenu(selectedDayMenu)) {
						JOptionPane.showMessageDialog(null, "Day Menu \"" + selectedDayMenu.getDateString() + "\" was successfully deleted!");
						
						ArrayList<DayMenu> dayMenus = dayMenuManager.retrieveAllDayMenus();
						
						dayMenuModel.setDayMenus(dayMenus);
						dayMenuModel.fireTableDataChanged();
							
			    		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
						ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
						ArrayList<String> ingredients = new ArrayList<String>();
						
						if(dayMenus.size() == 0) {
							menuItemsDayMenuModel.setMenuItems(menuItems);
							menuItemsDayMenuModel.fireTableDataChanged();
							foodItemsDayMenuModel.setFoodItems(foodItems);
							foodItemsDayMenuModel.fireTableDataChanged();
							ingredientsDayMenuModel.setFoodItem(ingredients);
							ingredientsDayMenuModel.fireTableDataChanged();
							
							return;
						}						
						
						SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-d-yyyy");
						date = sdf1.parse(dayMenusTable.getModel().getValueAt(0, 0).toString());
						
						Calendar cal = Calendar.getInstance();
						
			    		cal.setTime(date);
						
						try {
							selectedDayMenu = dayMenuManager.retrieveDayMenu(cal);
							menuItemsDayMenuModel.setMenuItems(selectedDayMenu.getMenuList());
							menuItemsDayMenuModel.fireTableDataChanged();
							dayMenuManager.retrieveDayMenu(cal).getMenuList().forEach(menu -> {{
								foodItems.addAll(menu.getFoodList());
							}});
							foodItemsDayMenuModel.setFoodItems(foodItems);
							foodItemsDayMenuModel.fireTableDataChanged();
							foodItems.forEach(item -> {{
								try {
									ingredients.addAll(foodItemManager.retrieveFoodItem(item.getFoodName()).getIngredients());
								} catch (ServiceLoadException | FoodItemServiceException e1) {
									e1.printStackTrace();
								}
							}});
							ingredientsDayMenuModel.setFoodItem(ingredients);
							ingredientsDayMenuModel.fireTableDataChanged();
						} catch (ServiceLoadException | DayMenuServiceException | MenuItemServiceException
								| FoodItemServiceException e1) {
							e1.printStackTrace();
						} 
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete day Menu " + selectedDayMenu.getDateString());
					}
				} catch (ServiceLoadException | HeadlessException | DayMenuServiceException | MenuItemServiceException | FoodItemServiceException | ParseException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Day Menu " + selectedDayMenu.getDateString() + " was not deleted!");
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
				ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
				ingredientsFoodItemModel.fireTableDataChanged();
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
	
	class selectMenuItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int row = menuItemsTable.getSelectedRow();
			String mealName = menuItemsTable.getModel().getValueAt(row, 0).toString();
			ArrayList<String> ingredients = new ArrayList<String>();

			try {
				selectedMenuItem = menuItemManager.retrieveMenuItem(mealName);
				foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
				foodItemsMenuItemModel.fireTableDataChanged();
				menuItemManager.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
					ingredients.addAll(food.getIngredients());
				}});
				ingredientsMenuItemModel.setFoodItem(ingredients);
				ingredientsMenuItemModel.fireTableDataChanged();
			} catch (ServiceLoadException | MenuItemServiceException | FoodItemServiceException e1) {
				e1.printStackTrace();
			}

		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectDayMenuMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int row = dayMenusTable.getSelectedRow();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-d-yyyy");
			Calendar cal = Calendar.getInstance();
			
			
			try {
				date = sdf1.parse(dayMenusTable.getModel().getValueAt(row, 0).toString());
				cal.setTime(date);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
    		
    		ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
			ArrayList<String> ingredients = new ArrayList<String>();
			
			try {
				selectedDayMenu = dayMenuManager.retrieveDayMenu(cal);
				menuItemsDayMenuModel.setMenuItems(selectedDayMenu.getMenuList());
				menuItemsDayMenuModel.fireTableDataChanged();
				dayMenuManager.retrieveDayMenu(cal).getMenuList().forEach(menu -> {{
					foodItems.addAll(menu.getFoodList());
				}});
				foodItemsDayMenuModel.setFoodItems(foodItems);
				foodItemsDayMenuModel.fireTableDataChanged();
				foodItems.forEach(item -> {{
					try {
						ingredients.addAll(foodItemManager.retrieveFoodItem(item.getFoodName()).getIngredients());
					} catch (ServiceLoadException | FoodItemServiceException e1) {
						e1.printStackTrace();
					}
				}});
				ingredientsDayMenuModel.setFoodItem(ingredients);
				ingredientsDayMenuModel.fireTableDataChanged();
			} catch (ServiceLoadException | DayMenuServiceException | MenuItemServiceException
					| FoodItemServiceException e1) {
				e1.printStackTrace();
			} 
			
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}
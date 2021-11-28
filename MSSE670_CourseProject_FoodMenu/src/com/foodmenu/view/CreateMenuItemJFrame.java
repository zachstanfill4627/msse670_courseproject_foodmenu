package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.view.FoodMenuJFrame.deleteUserButtonListener;
import com.foodmenu.view.FoodMenuJFrame.selectUserMouseClickListener;
import com.foodmenu.view.tableModels.FoodItemsTableModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.ListSelectionModel;

public class CreateMenuItemJFrame extends JFrame {
	
	private FoodItemsTableModel allFoodItemsModel = new FoodItemsTableModel();
	private FoodItemsTableModel selectedFoodItemsModel = new FoodItemsTableModel();
	
	private FoodItemManager foodItemManager = new FoodItemManager();
	private MenuItemManager menuItemManager = new MenuItemManager();
	
	private ArrayList<FoodItem> allFoodItems = new ArrayList<FoodItem>();
	private ArrayList<FoodItem> selectedFoodItems = new ArrayList<FoodItem>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	private JTextField mealNameField;
	private JSpinner complexityValueSpinner;
	private JTable allFoodItemsTable;
	private JTable selectedFoodItemsTable;
	private JTextField healthValueField;
	
	private int[] allFoodItemsRows = null;
	private int[] selectedFoodItemsRows = null;
	private double healthValue = 0.0;

	public CreateMenuItemJFrame() {
		super("Create Menu Item");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(850, 572);
		getContentPane().setLayout(null);
		
		try {
			allFoodItems = foodItemManager.retrieveAllFoodItems();
		} catch (ServiceLoadException | FoodItemServiceException e) {
			e.printStackTrace();
		};
        
		allFoodItemsModel.setFoodItems(allFoodItems);
		selectedFoodItemsModel.setFoodItems(selectedFoodItems);
		
		JLabel lblCreateMenuItem = new JLabel("Create Menu Item");
		lblCreateMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateMenuItem.setFont(new Font("Calibri", Font.BOLD, 18));
		lblCreateMenuItem.setBounds(10, 11, 814, 32);
		getContentPane().add(lblCreateMenuItem);
		
		JLabel mealNameLabel = new JLabel("Meal Name");
		mealNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		mealNameLabel.setBounds(10, 83, 126, 18);
		getContentPane().add(mealNameLabel);
		
		mealNameField = new JTextField();
		mealNameField.setFont(new Font("Calibri", Font.BOLD, 12));
		mealNameField.setColumns(10);
		mealNameField.setBounds(136, 80, 688, 20);
		getContentPane().add(mealNameField);
		
		JLabel complexityValueLabel = new JLabel("Complexity Value");
		complexityValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		complexityValueLabel.setBounds(10, 115, 126, 18);
		getContentPane().add(complexityValueLabel);
		
		complexityValueSpinner = new JSpinner();
		complexityValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		complexityValueSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		complexityValueSpinner.setBounds(136, 112, 688, 20);
		getContentPane().add(complexityValueSpinner);
		
		JScrollPane allFoodItemsScrollPane = new JScrollPane();
		allFoodItemsScrollPane.setBounds(10, 144, 364, 284);
		getContentPane().add(allFoodItemsScrollPane);
		
		allFoodItemsTable = new JTable();
		allFoodItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allFoodItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"All Food Items"
			}
		));
		allFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		allFoodItemsScrollPane.setViewportView(allFoodItemsTable);
		
		allFoodItemsTable.setModel(allFoodItemsModel);
		allFoodItemsTable.addMouseListener(new selectAllFoodItemMouseClickListener());
		
		JScrollPane selectedFoodItemsScrollPane = new JScrollPane();
		selectedFoodItemsScrollPane.setBounds(461, 144, 363, 284);
		getContentPane().add(selectedFoodItemsScrollPane);
		
		selectedFoodItemsTable = new JTable();
		selectedFoodItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectedFoodItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Selected Food Items"
			}
		));
		selectedFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		selectedFoodItemsScrollPane.setViewportView(selectedFoodItemsTable);
		
		selectedFoodItemsTable.setModel(selectedFoodItemsModel);
		selectedFoodItemsTable.addMouseListener(new selectSelectedFoodItemMouseClickListener());
		
		JLabel healthValueLabel = new JLabel("Calculated Health Value");
		healthValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		healthValueLabel.setBounds(10, 461, 182, 18);
		getContentPane().add(healthValueLabel);
		
		healthValueField = new JTextField();
		healthValueField.setHorizontalAlignment(SwingConstants.RIGHT);
		healthValueField.setText("0");
		healthValueField.setBackground(Color.WHITE);
		healthValueField.setEditable(false);
		healthValueField.setFont(new Font("Calibri", Font.BOLD, 12));
		healthValueField.setColumns(10);
		healthValueField.setBounds(202, 458, 622, 20);
		getContentPane().add(healthValueField);
		
		JButton createMenuButton = new JButton("Create Item");
		createMenuButton.addActionListener(new createMenuButtonListener());
		createMenuButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createMenuButton.setBounds(657, 489, 167, 29);
		getContentPane().add(createMenuButton);
		
		JButton addFoodButton = new JButton(">>");
		addFoodButton.addActionListener(new addFoodButtonListener());
		addFoodButton.setBounds(384, 232, 67, 23);
		getContentPane().add(addFoodButton);
		
		JButton removeFoodButton = new JButton("<<");
		removeFoodButton.addActionListener(new removeFoodButtonListener());
		removeFoodButton.setBounds(384, 266, 67, 23);
		getContentPane().add(removeFoodButton);
	}
	
	class createMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String mealName = mealNameField.getText();
			int complexityValue = (int) complexityValueSpinner.getValue();
			
			MenuItem menuItem = new MenuItem(mealName, selectedFoodItems, complexityValue);
			
			if(!menuItem.validate()) {
				JOptionPane.showMessageDialog(null, "Menu Item did not validate, please ensure all fields have values!");
				return;
			}
			
			MenuItemManager menuItemManager = new MenuItemManager();
			
			try {
				if(!menuItemManager.addNewMenuItem(menuItem)) {
					JOptionPane.showMessageDialog(null, "System Failed to Create Menu Item!");
				}
			} catch (ServiceLoadException | MenuItemServiceException e1) {
				e1.printStackTrace();
			}
			
			setVisible(false);
			dispose();
		}
	}
	
	class addFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String foodName = "";
			FoodItem foodItem = new FoodItem();
			
			if(allFoodItemsRows == null) {
				return;
			}
						
			for(int i : allFoodItemsRows ) {
				foodName = allFoodItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					foodItem = foodItemManager.retrieveFoodItem(foodName);
					selectedFoodItems.add(foodItem);
					
					selectedFoodItemsModel.setFoodItems(selectedFoodItems);
					selectedFoodItemsModel.fireTableDataChanged();
				} catch (ServiceLoadException | FoodItemServiceException e1) {
					e1.printStackTrace();
				}
			}

			for(int i = (allFoodItemsRows.length-1); i > -1 ; i--){
				allFoodItems.remove(allFoodItemsRows[i]);
				
				allFoodItemsModel.setFoodItems(allFoodItems);
				allFoodItemsModel.fireTableDataChanged();	
			}
			
			for(FoodItem item : selectedFoodItems) {
				healthValue+=item.getHealthValue();
			}
			healthValue = healthValue / selectedFoodItems.size();
			
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			
			allFoodItemsRows = null;
		}
	}
	
	class removeFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String foodName = "";
			FoodItem foodItem = new FoodItem();
			
			if(selectedFoodItemsRows == null) {
				return;
			}
						
			for(int i : selectedFoodItemsRows ) {
				foodName = selectedFoodItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					foodItem = foodItemManager.retrieveFoodItem(foodName);
					
					allFoodItems.add(foodItem);
					
					allFoodItemsModel.setFoodItems(allFoodItems);
					allFoodItemsModel.fireTableDataChanged();
				} catch (ServiceLoadException | FoodItemServiceException e1) {
					e1.printStackTrace();
				}
			}

			for(int i = (selectedFoodItemsRows.length-1); i > -1 ; i--){
				selectedFoodItems.remove(selectedFoodItemsRows[i]);
				
				selectedFoodItemsModel.setFoodItems(selectedFoodItems);
				selectedFoodItemsModel.fireTableDataChanged();	
			}
			
			if(selectedFoodItems.size() > 0) {
				for(FoodItem item : selectedFoodItems) {
					healthValue+=item.getHealthValue();
				}
				healthValue = healthValue / selectedFoodItems.size();
			} else {
				healthValue = 0;
			}
			
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			
			selectedFoodItemsRows = null;
		}
	}
	
	class selectAllFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			allFoodItemsRows = allFoodItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectSelectedFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			selectedFoodItemsRows = selectedFoodItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}


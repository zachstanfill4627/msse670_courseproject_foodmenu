package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.foodmenu.model.business.managers.DayMenuManager;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.business.managers.MenuItemManager;
import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.services.exceptions.DayMenuServiceException;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;
import com.foodmenu.model.services.exceptions.MenuItemServiceException;
import com.foodmenu.view.FoodMenuJFrame.deleteUserButtonListener;
import com.foodmenu.view.FoodMenuJFrame.selectUserMouseClickListener;
import com.foodmenu.view.tableModels.FoodItemsTableModel;
import com.foodmenu.view.tableModels.MenuItemsTableModel;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JComboBox;

public class CreateDayMenuJFrame extends JFrame {
	
	private MenuItemsTableModel allMenuItemsModel = new MenuItemsTableModel();
	private MenuItemsTableModel selectedMenuItemsModel = new MenuItemsTableModel();
	
	private MenuItemManager menuItemManager = new MenuItemManager();
	
	private ArrayList<MenuItem> allMenuItems = new ArrayList<MenuItem>();
	private ArrayList<MenuItem> selectedMenuItems = new ArrayList<MenuItem>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	private JTable allMenuItemsTable;
	private JTable selectedMenuItemsTable;
	private JTextField healthValueField;
	private JTextField complexityValueField;
	private JSpinner spinner;
	
	private int[] allMenuItemsRows = null;
	private int[] selectedMenuItemsRows = null;
	private int complexityValue = 0;
	private double healthValue = 0.0;
	
	public CreateDayMenuJFrame() {
		super("Create Menu Item");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(850, 572);
		getContentPane().setLayout(null);
		
		try {
			allMenuItems = menuItemManager.retrieveAllMenuItems();
		} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e) {
			e.printStackTrace();
		};
        
		allMenuItemsModel.setMenuItems(allMenuItems);
		selectedMenuItemsModel.setMenuItems(selectedMenuItems);
		
		JLabel createDayMenuLabel = new JLabel("Create Day Menu");
		createDayMenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		createDayMenuLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		createDayMenuLabel.setBounds(10, 11, 814, 32);
		getContentPane().add(createDayMenuLabel);
		
		JLabel dayMenuDateLabel = new JLabel("Day Menu Date");
		dayMenuDateLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		dayMenuDateLabel.setBounds(10, 83, 126, 18);
		getContentPane().add(dayMenuDateLabel);
		
		JScrollPane allMenuItemsScrollPane = new JScrollPane();
		allMenuItemsScrollPane.setBounds(10, 112, 364, 284);
		getContentPane().add(allMenuItemsScrollPane);
		
		allMenuItemsTable = new JTable();
		allMenuItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allMenuItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Food Name", "Category", "Health Value"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		allMenuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		allMenuItemsScrollPane.setViewportView(allMenuItemsTable);
		
		allMenuItemsTable.setModel(allMenuItemsModel);
		allMenuItemsTable.addMouseListener(new selectAllMenuItemMouseClickListener());
		
		JScrollPane selectedMenuItemsScrollPane = new JScrollPane();
		selectedMenuItemsScrollPane.setBounds(461, 112, 363, 284);
		getContentPane().add(selectedMenuItemsScrollPane);
		
		selectedMenuItemsTable = new JTable();
		selectedMenuItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectedMenuItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Selected Food Items"
			}
		));
		selectedMenuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		selectedMenuItemsScrollPane.setViewportView(selectedMenuItemsTable);
		
		selectedMenuItemsTable.setModel(selectedMenuItemsModel);
		selectedMenuItemsTable.addMouseListener(new selectSelectedMenuItemMouseClickListener());
		
		JButton createDayButton = new JButton("Create Item");
		createDayButton.addActionListener(new createDayButtonListener());
		
		JLabel complexityValueLabel = new JLabel("Calculated Complexity Value");
		complexityValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		complexityValueLabel.setBounds(10, 423, 182, 18);
		getContentPane().add(complexityValueLabel);
		
		complexityValueField = new JTextField();
		complexityValueField.setText("0");
		complexityValueField.setHorizontalAlignment(SwingConstants.RIGHT);
		complexityValueField.setFont(new Font("Calibri", Font.BOLD, 12));
		complexityValueField.setEditable(false);
		complexityValueField.setColumns(10);
		complexityValueField.setBackground(Color.WHITE);
		complexityValueField.setBounds(202, 420, 622, 20);
		getContentPane().add(complexityValueField);
		
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
		createDayButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createDayButton.setBounds(657, 489, 167, 29);
		getContentPane().add(createDayButton);
		
		JButton addMenuButton = new JButton(">>");
		addMenuButton.addActionListener(new addMenuButtonListener());
		addMenuButton.setBounds(384, 200, 67, 23);
		getContentPane().add(addMenuButton);
		
		JButton removeMenuButton = new JButton("<<");
		removeMenuButton.addActionListener(new removeMenuButtonListener());
		removeMenuButton.setBounds(384, 234, 67, 23);
		getContentPane().add(removeMenuButton);
		
		Date today = new Date();
	    spinner = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.YEAR));
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MMM-dd-yyyy");
	    spinner.setEditor(editor);
		spinner.setBounds(132, 80, 158, 20);
		getContentPane().add(spinner);
	}
	
	class createDayButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MM-dd-yyyy");
			Calendar date = editor.getFormat().getCalendar();
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");

			DayMenu dayMenu = new DayMenu(date, selectedMenuItems);
			
			if(!dayMenu.validate()) {
				JOptionPane.showMessageDialog(null, "Day Menu did not validate, please ensure all fields have values!");
				return;
			}
			
			DayMenuManager dayMenuManager = new DayMenuManager();
			
			try {
				if(!dayMenuManager.addNewDayMenu(dayMenu)) {
					JOptionPane.showMessageDialog(null, "System Failed to Create Day Menu!");
				}
			} catch (ServiceLoadException | HeadlessException | DayMenuServiceException e1) {
				e1.printStackTrace();
			}
			
			setVisible(false);
			dispose();
		}
	}
	
	class addMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String menuName = "";
			MenuItem menuItem = new MenuItem();
			
			if(allMenuItemsRows == null) {
				return;
			}
						
			for(int i : allMenuItemsRows ) {
				menuName = allMenuItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					menuItem = menuItemManager.retrieveMenuItem(menuName);
					selectedMenuItems.add(menuItem);
					
					selectedMenuItemsModel.setMenuItems(selectedMenuItems);
					selectedMenuItemsModel.fireTableDataChanged();
				} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e1) {
					e1.printStackTrace();
				}
			}

			for(int i = (allMenuItemsRows.length-1); i > -1 ; i--){
				allMenuItems.remove(allMenuItemsRows[i]);
				
				allMenuItemsModel.setMenuItems(allMenuItems);
				allMenuItemsModel.fireTableDataChanged();	
			}
			
			for(MenuItem item : selectedMenuItems) {
				complexityValue+=item.getComplexityValue();
				healthValue+=item.getHealthValue();
			}
			complexityValue = complexityValue / selectedMenuItems.size();
			healthValue = healthValue / selectedMenuItems.size();
			
			complexityValueField.setText(String.valueOf(complexityValue));
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			
			allMenuItemsRows = null;
		}
	}
	
	class removeMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String menuName = "";
			MenuItem menuItem = new MenuItem();
			
			if(selectedMenuItemsRows == null) {
				return;
			}
						
			for(int i : selectedMenuItemsRows ) {
				menuName = selectedMenuItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					menuItem = menuItemManager.retrieveMenuItem(menuName);
					
					allMenuItems.add(menuItem);
					
					allMenuItemsModel.setMenuItems(allMenuItems);
					allMenuItemsModel.fireTableDataChanged();
				} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e1) {
					e1.printStackTrace();
				}
			}

			for(int i = (selectedMenuItemsRows.length-1); i > -1 ; i--){
				selectedMenuItems.remove(selectedMenuItemsRows[i]);
				
				selectedMenuItemsModel.setMenuItems(selectedMenuItems);
				selectedMenuItemsModel.fireTableDataChanged();	
			}
			
			if(selectedMenuItems.size() > 0) {
				for(MenuItem item : selectedMenuItems) {
					healthValue+=item.getHealthValue();
				}
				healthValue = healthValue / selectedMenuItems.size();
			} else {
				healthValue = 0;
			}
			
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			
			selectedMenuItemsRows = null;
		}
	}
	
	class selectAllMenuItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			allMenuItemsRows = allMenuItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectSelectedMenuItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			selectedMenuItemsRows = selectedMenuItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}


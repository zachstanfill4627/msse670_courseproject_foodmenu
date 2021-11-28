package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.SwingConstants;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.FoodItemManager;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.services.exceptions.FoodItemServiceException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

public class CreateFoodItemJFrame extends JFrame {
	
	private JTextField foodNameField;
	private JComboBox categoryComboBox;
	private JSpinner healthValueSpinner;
	private JSpinner prepTimeSpinner;
	private JScrollPane recipeScrollPane;
	private JTextArea recipeTextArea;
	private JScrollPane ingredientsScrollPane;
	private JTextArea ingredientsTextArea;

	public CreateFoodItemJFrame() {
		super("Create Food Item");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(479, 569);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Create Food Item");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLabel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLabel);
		
		JButton createFoodButton = new JButton("Create Item");
		createFoodButton.addActionListener(new createFoodButtonListener());
		createFoodButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createFoodButton.setBounds(286, 490, 167, 29);
		getContentPane().add(createFoodButton);
		
		JLabel foodNameLabel = new JLabel("Food Name");
		foodNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		foodNameLabel.setBounds(10, 67, 126, 18);
		getContentPane().add(foodNameLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		categoryLabel.setBounds(10, 96, 126, 18);
		getContentPane().add(categoryLabel);
		
		JLabel healthValueLabel = new JLabel("Health Value");
		healthValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		healthValueLabel.setBounds(10, 125, 126, 18);
		getContentPane().add(healthValueLabel);
		
		JLabel prepTimeLabel = new JLabel("PrepTime (Minutes)");
		prepTimeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		prepTimeLabel.setBounds(10, 154, 126, 18);
		getContentPane().add(prepTimeLabel);
		
		JLabel recipeLabel = new JLabel("Recipe (One Step Per Line)");
		recipeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		recipeLabel.setBounds(10, 183, 443, 18);
		getContentPane().add(recipeLabel);
		
		JLabel ingredientsLabel = new JLabel("Ingredients (One Ingredient Per Line)");
		ingredientsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		ingredientsLabel.setBounds(10, 327, 443, 18);
		getContentPane().add(ingredientsLabel);
		
		foodNameField = new JTextField();
		foodNameField.setFont(new Font("Calibri", Font.BOLD, 12));
		foodNameField.setBounds(136, 64, 317, 20);
		getContentPane().add(foodNameField);
		foodNameField.setColumns(10);
		
		categoryComboBox = new JComboBox();
		categoryComboBox.setFont(new Font("Calibri", Font.BOLD, 12));
		categoryComboBox.setModel(new DefaultComboBoxModel(new String[] {"American", "Mexican", "Italian", "Japanese", "Chinese", "Other"}));
		categoryComboBox.setBackground(Color.WHITE);
		categoryComboBox.setBounds(136, 92, 317, 22);
		getContentPane().add(categoryComboBox);
		
		healthValueSpinner = new JSpinner();
		healthValueSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		healthValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		healthValueSpinner.setBounds(136, 122, 317, 20);
		getContentPane().add(healthValueSpinner);
		
		prepTimeSpinner = new JSpinner();
		prepTimeSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		prepTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		prepTimeSpinner.setBounds(136, 151, 317, 20);
		getContentPane().add(prepTimeSpinner);
		
		recipeScrollPane = new JScrollPane();
		recipeScrollPane.setBounds(10, 200, 443, 116);
		getContentPane().add(recipeScrollPane);
		
		recipeTextArea = new JTextArea();
		recipeTextArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		recipeScrollPane.setViewportView(recipeTextArea);
		
		ingredientsScrollPane = new JScrollPane();
		ingredientsScrollPane.setBounds(10, 352, 443, 92);
		getContentPane().add(ingredientsScrollPane);
		
		ingredientsTextArea = new JTextArea();
		ingredientsTextArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		ingredientsScrollPane.setViewportView(ingredientsTextArea);
	}
	
	class createFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String foodName = foodNameField.getText();
			String category = categoryComboBox.getSelectedItem().toString();
			int healthValue = (int) healthValueSpinner.getValue();
			int prepTime = (int) prepTimeSpinner.getValue();
			ArrayList<String> recipe = new ArrayList<String>(Arrays.asList(recipeTextArea.getText().split("\\r?\\n")));
			ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientsTextArea.getText().split("\\r?\\n")));
			
			FoodItem foodItem = new FoodItem(foodName, category, healthValue, prepTime, recipe, ingredients);
			
			if(!foodItem.validate()) {
				JOptionPane.showMessageDialog(null, "Food Item did not validate, please ensure all fields have values!");
				return;
			}
			
			FoodItemManager foodItemManager = new FoodItemManager();
			
			try {
				if(!foodItemManager.addNewFoodItem(foodItem)) {
					JOptionPane.showMessageDialog(null, "System Failed to Create Food Item!");
				}
			} catch (ServiceLoadException | FoodItemServiceException e1) {
				e1.printStackTrace();
			}
			
			setVisible(false);
			dispose();
		}
	}
}

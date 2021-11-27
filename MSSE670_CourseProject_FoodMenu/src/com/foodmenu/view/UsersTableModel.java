package com.foodmenu.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.foodmenu.model.domain.User;

public class UsersTableModel extends AbstractTableModel {
	private String [] columnNames = {"First Name", "Last Name", "Email", "Age",
			"Role"};
	
	private ArrayList<User> users = new ArrayList<User>();
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public int getRowCount() {
		return users.size();
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		User user = users.get(row);
		switch (column) {
			case 0:
				return user.getFirstName();
			case 1:
				return user.getLastName();
			case 2:
				return user.getEmailAddress();
			case 3:
				return user.getAge();
			case 4:
				return user.getRole();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}

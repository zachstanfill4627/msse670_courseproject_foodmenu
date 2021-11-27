package com.foodmenu.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainUI {

	public static void main(String[] args) {
		MainUI callingMainUI = new MainUI();
		MainUI.view();
	}

	// calls ElectronicPedigreeFrame in view
	public static void view() {
		LoginJFrame loginJFrame = new LoginJFrame();
		loginJFrame.setVisible(true);
	}

}

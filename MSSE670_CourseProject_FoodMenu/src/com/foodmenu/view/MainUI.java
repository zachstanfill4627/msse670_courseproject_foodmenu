package com.foodmenu.view;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class MainUI {

	public static void main(String[] args) throws IOException {
		MainUI callingMainUI = new MainUI();
		MainUI.view();
	}

	// calls ElectronicPedigreeFrame in view
	public static void view() throws IOException {
		LoginJFrame loginJFrame = new LoginJFrame();
		loginJFrame.setVisible(true);
	}

}

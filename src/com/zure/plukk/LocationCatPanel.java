package com.zure.plukk;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zure.ztools.components.ZButton;
import com.zure.ztools.components.ZPanel;

public class LocationCatPanel extends ZPanel {
	private static final long serialVersionUID = 1L;
	
	private List<String> locCat = new ArrayList<String>();
	
	public LocationCatPanel(OrderPanel parent) {
		File file = new File("res/loccat.txt");
		if (!file.exists()) {
			locCat.add("Standard");
		} else {
			loadCategories(file);
		}
		addButtons(parent);
	}
	
	private void loadCategories(File file) {
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				locCat.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void addButtons(OrderPanel parent) {
		c.fill = GridBagConstraints.BOTH; c.weightx = 1;
		for (int i = 0; i < locCat.size(); i++) {
			if (i % 2 == 0) {
				c.gridy++; c.gridx = 0;
			}
			ZButton b = new ZButton(locCat.get(i));
			action(b, parent);
			add(b, c);
			c.gridx++;
		}
	}
	
	private void action(ZButton b, OrderPanel parent) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.swapAddLocationPanelView(b.getText().toLowerCase());
			}
		});
	}
	
}

package com.zure.plukk;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingConstants;

import com.zure.ztools.Statics;
import com.zure.ztools.components.ZButton;
import com.zure.ztools.components.ZLabel;
import com.zure.ztools.components.ZPanel;
import com.zure.ztools.components.ZScroll;
import com.zure.ztools.components.ZTextField;

public class OrderPanel extends ZPanel {
	private static final long serialVersionUID = 1L;
	
	private Order order;
	private ZPanel locPanel = new ZPanel();
	private AddLocationPanel addLocationPanel;
	private LocationCatPanel locCatPanel;
	private boolean locationIsUp;

	private ZLabel head = new ZLabel();
	private ZLabel space = new ZLabel("DELER LIGGER I:");
	private ZLabel space2 = new ZLabel("");

	private ZButton back = new ZButton("TILBAKE");
	private ZButton addLoc = new ZButton("LEGG TIL LOKASJON");
	
	private int locCatPanelY;

	public OrderPanel(Main main, Order order) {
		this.order = order;
		head.setText(Integer.toString(order.getOrderNumber()));
		addLocationPanel = new AddLocationPanel("plukk");
		locCatPanel = new LocationCatPanel(this);
		
		head.setHorizontalAlignment(SwingConstants.CENTER);
		head.setFont(Main.font);

		space.setHorizontalAlignment(SwingConstants.CENTER);
		space.setFont(new Font(Font.MONOSPACED, 2, 20));
		
		

		back.setFont(Main.font);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.setView(new SearchPanel(main));
				locationIsUp = false;
			}
		});
		
		addLoc.setFont(new Font(Font.MONOSPACED, 2, 20));
		addLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locationIsUp = true;
				removeAll();
				setupFrame();
				addAddLocPanel();
			}
		});

		//GBC and adding objects
		setupFrame();
		
		updateLocPanel();
	}
	
	private void setupFrame() {
		c.gridwidth = 1; c.fill = GridBagConstraints.BOTH; c.weightx = 1; c.weighty = 0;
		addVertical(head);
		addVertical(space);
		addVertical(locPanel);
		c.weighty = 1;
		addVertical(space2);
		c.gridwidth = 1; c.weighty = 0;
		addVertical(back);
		if (!locationIsUp) {
			addVertical(addLoc);
		}
		locCatPanelY = c.gridy;
	}
	
	private void addAddLocPanel() {
		remove(space2);
		c.gridy = locCatPanelY;
		addVertical(locCatPanel);
		c.weighty = 1;
		add(addLocationPanel, c);
		updateUI();
	}
	
	private void removeAddLocPanel() {
		removeAll();
		setupFrame();
		updateUI();
	}
	
	public void swapAddLocationPanelView(String subCategory) {
		removeAddLocPanel();
		addLocationPanel = new AddLocationPanel(subCategory);
		addAddLocPanel();
	}
	
	private void updateLocPanel() {
		locPanel.removeAll();
		locPanel.setBackground(Statics.ELEVATED_PANEL_COL);
		if (order.getLocations().size() == 0) {
			space.setText("");
			ZLabel label = new ZLabel("INGEN DELER ER REGISTRERT");
			ZLabel label2 = new ZLabel("PÅ DENNE ORDRE");
			label.setFont(new Font(Font.MONOSPACED, 1, 20));
			label2.setFont(new Font(Font.MONOSPACED, 1, 20));
			locPanel.addVertical(label);
			locPanel.addVertical(label2);
		} else {
			space.setText("DELER LIGGER I:");
			locPanel.c.fill = GridBagConstraints.BOTH;
			int count = 0;
			for (String s : order.getLocations()) {
				if (count % 8 == 0 && count > 0) {
					locPanel.c.gridx = 0;
					locPanel.c.gridy++;
				}
				ZTextField field = new ZTextField(s);
				field.setEditable(false);
				field.setFont(new Font(Font.MONOSPACED, 1, 20));
				field.setHorizontalAlignment(SwingConstants.CENTER);
				locPanel.addHorizontal(field);
				count++;
			}
		}
		locPanel.updateUI();
	}
	
	private class AddLocationPanel extends ZPanel {
		private static final long serialVersionUID = 1L;
	
		private ZScroll scroll = new ZScroll();
		
		public AddLocationPanel(String subCategory) {
			setBackground(Statics.ELEVATED_PANEL_COL);
			scroll.getPanel().setBackground(Statics.ELEVATED_PANEL_COL);
			for (String s : locationLines(subCategory)) {
				String[] split = s.split("_");
				for (String loc : split) {
					ZButton button = new ZButton(loc);
					button.setFont(new Font(Font.MONOSPACED, 2, 16));
					scroll.addHorizontal(button);
					
					for (String orderLoc : order.getLocations()) {
						if (orderLoc.equals(button.getText())) {
							button.setBackground(Statics.ELEVATED_PANEL_COL);
							break;
						}
					}
					action(button, loc);
				}
				scroll.getGBC().gridx = 0; scroll.getGBC().gridy++;
			}
			c.weighty = 1; c.fill = GridBagConstraints.BOTH; c.weightx = 1;
			addVertical(scroll);
		}
		
		private void action(ZButton b, String location) {
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean wasChecked = false;
					for (String s : order.getLocations()) {
						if (s.equals(location)) {
							b.setBackground(Statics.BUTTON_COL);
							wasChecked = true;
							order.removeLocation(location);
							break;
						}
					}
					if (!wasChecked) {
						order.addLocation(location);
						b.setBackground(Statics.ELEVATED_PANEL_COL);
					}
					updateLocPanel();
				}
			});
		}
		
		private List<String> locationLines(String subCategory) {
			List<String> list = new ArrayList<>();
			try {
				Scanner scan = new Scanner(new File("res/loc/" + subCategory + ".txt"));
				while (scan.hasNextLine()) {
					list.add(scan.nextLine());
				}
				scan.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			return list;
		}
		
	}
	
}

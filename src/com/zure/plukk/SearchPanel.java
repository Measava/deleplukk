package com.zure.plukk;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import com.zure.ztools.components.ZButton;
import com.zure.ztools.components.ZPanel;
import com.zure.ztools.components.ZTextField;

public class SearchPanel extends ZPanel {
	private static final long serialVersionUID = 1L;
	
	private ZTextField search = new ZTextField();
	private ZButton searchButton = new ZButton("     SØK     ", false);
	
	public SearchPanel(Main main) {
		c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
		
		search.setFont(Main.font);
		search.setHorizontalAlignment(SwingConstants.CENTER);
		searchButton.setFont(Main.font);
		
		addVertical(search);
		addVertical(searchButton);
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Order order = Order.loadOrder(Integer.parseInt(search.getText()));
					main.setView(new OrderPanel(main, order));
				} catch (Exception ee) {
					ee.printStackTrace();
					System.err.println("I am not a number! ... I am a free man!");
				}
			}
		};
		
		search.addActionListener(action);
		searchButton.addActionListener(action);
	}

}

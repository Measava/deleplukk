package com.zure.plukk;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.zure.ztools.components.ZButton;
import com.zure.ztools.components.ZFrame;
import com.zure.ztools.components.ZTextField;

public class SetupFrame extends ZFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SetupFrame() {
		super("SETUP", true, 400, 400, true);
		
		getContentPanel().c.fill = GridBagConstraints.HORIZONTAL;
		
		ZTextField field = new ZTextField();
		field.setFont(Main.font);
		addComp(field, getContentPanel().c);
		
		
		ZButton button = new ZButton(" - - Submit - - ");
		getContentPanel().c.gridy++;
		addComp(button, getContentPanel().c);
		
		ZFrame setup = this;
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (field.getText().equals("XX0F193119")) {
					File res = new File("res");
					if (!res.exists()) {
						res.mkdirs();
					}
					File orderDir = new File("res/orders");
					if (!orderDir.exists()) {
						orderDir.mkdirs();
					}
					File file = new File("res/XX0F193119.txt");
					try {
						PrintWriter writer = new PrintWriter(file);
						writer.print("Product not lisenced for distribution");
						writer.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					setup.dispose();
					new Main("DelePlukk", true, 400, 600, true);
				}
			}
		};
		
		field.addActionListener(action);
		button.addActionListener(action);
		
	}

}

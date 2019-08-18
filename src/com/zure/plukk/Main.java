package com.zure.plukk;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.io.File;

import com.zure.ztools.components.ZFrame;
import com.zure.ztools.components.ZPanel;

public class Main extends ZFrame {
	private static final long serialVersionUID = 1L;
	
	public static Font font = new Font(Font.MONOSPACED, 1, 40);
	
	public Main(String title, boolean bordered, int width, int height, boolean exit) {
		super(title, bordered, width, height, exit);

		setView(new SearchPanel(this));
	}
	
	public void setView(ZPanel panel) {
		getContentPanel().removeAll();
		getContentPanel().c.weightx = 1; getContentPanel().c.weighty = 1; getContentPanel().c.fill = GridBagConstraints.BOTH;
		addComp(panel, getContentPanel().c);
		getContentPanel().updateUI();
	}

	public static void main(String[] args) {
		loadVersion();
	}
	
	private static void loadVersion() {
		File file = new File("res/XX0F193119.txt");
		if (!file.exists()) {
			new SetupFrame();
			return;
		} else {
			new Main("DelePlukk", true, 400, 600, true);
		}
		
	}
	
}

/*
 * Copyright 2021 Marc Liberatore.
 */

package visualizer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class BusVisualizer extends JFrame {
	
	public BusVisualizer() throws IOException {
		add(new BusPanel());
		pack();
		setTitle("Bus Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
		
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                BusVisualizer visualizer;
				try {
					visualizer = new BusVisualizer();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
                visualizer.setVisible(true);
            }
        });
	}
}

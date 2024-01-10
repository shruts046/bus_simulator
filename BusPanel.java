/*
 * Copyright 2021 Marc Liberatore.
 */

package visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.Action;

import simulator.Bus;
import simulator.RoadMap;

@SuppressWarnings("serial")
public class BusPanel extends JPanel {
	static RoadMap roadMap;
	static Bus[] buses;
	static int busCount;

	private final int WIDTH;
	private final int HEIGHT;
    private static final String MOVE = "MOVE";
	private static final String REMOVE = "REMOVE";
	
	private Action move; 
	private Action remove;

	public BusPanel() {
		roadMap = new RoadMap(10, 10); // you could replace this with a call to
										// RoadMap.fromString(); see the tests for an example.
		buses = new Bus[5];
		busCount = 0;


		WIDTH = roadMap.xSize * 20;
		HEIGHT = roadMap.ySize * 20;

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("clicked " + e.getButton());
				final int x = e.getX() / 20;
				final int y = e.getY() / 20;
	
				if (e.getButton() == MouseEvent.BUTTON1) {
					toggleRoad(x, y);
				}
				else if (e.getButton() == MouseEvent.BUTTON3) {
					addBus(x, y);
				}
			}
		});

		move = new AbstractAction(MOVE) {
			public void actionPerformed(ActionEvent e) {
				System.out.println("calling move() on all buses");
				for (int i = 0; i < busCount; i++) {
					buses[i].move();
				}
				repaint();
			}
		};
		getActionMap().put(MOVE, move);
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), MOVE);

		remove =  new AbstractAction(REMOVE) {
			public void actionPerformed(ActionEvent e) {
				if (busCount > 0) {
					busCount--;
					System.out.println("removing bus " + busCount);
					buses[busCount] = null;
				}	
				repaint();
			}
		};
		getActionMap().put(REMOVE, remove);
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), REMOVE);
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), REMOVE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);		

		// first draw the road/non-road
		for (int y = 0; y < roadMap.ySize; y++) {
			for (int x = 0; x < roadMap.xSize; x++) {
				if (roadMap.isRoad(x, y)) {
					g.setColor(Color.BLACK);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fill3DRect(x*20, y*20, 20, 20, true);	
			}
		}

		// now draw the buses (numbers)
		g.setColor(Color.WHITE);
		for (int i = 0; i < busCount; i++) {
			Bus b = buses[i];
			g.drawString(Integer.toString(b.number), b.getX() * 20, b.getY() * 20 + 20);
		}
	}

	private void toggleRoad(int x, int y) {
		boolean isClear = true;
		for (int i = 0; i < busCount; i++) {
			Bus b = buses[i];
			if (b.getX() == x && b.getY() == y) {
				isClear = false;
				break;
			}
		}
		if (isClear) {
			System.out.println("toggle " + x + ", " + y);
			roadMap.setRoad(x, y, !roadMap.isRoad(x, y));
			repaint();
		}
	}

	private void addBus(int x, int y) {
		// is there space for a new bus and is the cell a road?
		if ((busCount < buses.length) && (roadMap.isRoad(x, y))) {
			// and, is it clear (no bus)?
			boolean isClear = true;
			for (int i = 0; i < busCount; i++) {
				Bus b = buses[i];
				if (b.getX() == x && b.getY() == y) {
					isClear = false;
					break;
				}
			}
			if (isClear) {
				System.out.println("add bus at " + x + ", " + y);
				buses[busCount] = new Bus(busCount, roadMap, x, y);				
				busCount++;
				repaint();
			}
		}
	}
}

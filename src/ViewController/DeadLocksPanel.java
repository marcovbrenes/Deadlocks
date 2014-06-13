package ViewController;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Fork;
import Model.Philosopher;
import ViewObjects.Circle;
import ViewObjects.Rectangle;

public class DeadLocksPanel extends JPanel implements Observer {
	private ExecutorService executorService = null;
	private Philosopher[] philosophers = null;
	private Fork[] forks = null;
	private Timer timer = null;


	private JButton button;
	Circle circle1, circle2, circle3, circle4, circle5;
	Rectangle rect1, rect2, rect3, rect4, rect5;

	public DeadLocksPanel() {
		philosophers = new Philosopher[5];
		forks = new Fork[5];

		for (int i = 0; i < 5; i++)
			forks[i] = new Fork(i);

		executorService = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) {
			philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5]);
			philosophers[i].addObserver(this);
		}

		button = new JButton("Start");
		ButtonListener listener = new ButtonListener();

		button.addActionListener(listener);

		circle1 = new Circle(80, 210, 100);
		circle2 = new Circle(80, 340, 230);
		circle3 = new Circle(80, 295, 380);
		circle4 = new Circle(80, 125, 380);
		circle5 = new Circle(80, 80, 230);
		
		int[] x1 = {145, 135, 185, 195};
		int[] y1 = {140, 150, 200, 190};
		rect1 = new Rectangle(x1, y1);
		
		int[] x2 = {350, 360, 310, 300};
		int[] y2 = {140, 150, 200, 190};
		rect2 = new Rectangle(x2, y2);
		
		int[] x3 = {330, 320, 370, 380};
		int[] y3 = {320, 330, 380, 370};
		rect3 = new Rectangle(x3, y3);
		
		int[] x4 = {243, 257, 257, 243};
		int[] y4 = {370, 370, 451, 451};
		rect4 = new Rectangle(x4, y4);
		
		int[] x5 = {165, 175, 125, 115};
		int[] y5 = {320, 330, 380, 370};
		rect5 = new Rectangle(x5, y5);
		
		
		
		setPreferredSize(new Dimension(500, 500));
		setBackground(Color.cyan);

		add(button);

	}

	/*
	 * Paint the Canvas
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		circle1.draw(g);
		circle2.draw(g);
		circle3.draw(g);
		circle4.draw(g);
		circle5.draw(g);
		rect1.draw(g);
		rect2.draw(g);
		rect3.draw(g);
		rect4.draw(g);
		rect5.draw(g);
		
		
	}

	public void update(Observable o, Object arg) {
		Philosopher philosopher = (Philosopher) o;

		switch (philosopher.ID) {
		case 0:
			if (philosopher.full) {
				circle1.setColor(Color.blue);
				break;
			}
			
			circle1.setColor(((philosopher.eating) ? Color.yellow : Color.blue));
			
			if (philosopher.eating) {
				rect1.setColor(Color.cyan);
				rect2.setColor(Color.cyan);
			} else {
				rect1.setColor(((philosophers[4].eating) ? Color.cyan : Color.lightGray));
				rect2.setColor(((philosophers[1].eating) ? Color.cyan : Color.lightGray));
			}
			break;
			
		case 1:
			if (philosopher.full) {
				circle2.setColor(Color.blue);
				break;
			}
			
			circle2.setColor(((philosopher.eating) ? Color.yellow : Color.blue));
			
			if (philosopher.eating) {
				rect2.setColor(Color.cyan);
				rect3.setColor(Color.cyan);
			} else {
			rect2.setColor(((philosophers[0].eating) ? Color.cyan : Color.lightGray));
			rect3.setColor(((philosophers[2].eating) ? Color.cyan : Color.lightGray));
			}
			break;
			
		case 2:
			if (philosopher.full) {
				circle3.setColor(Color.blue);
				break;
			}
			
			circle3.setColor(((philosopher.eating) ? Color.yellow : Color.blue));
			
			if (philosopher.eating) {
				rect3.setColor(Color.cyan);
				rect4.setColor(Color.cyan);
				
			} else {
				rect3.setColor(((philosophers[1].eating) ? Color.cyan : Color.lightGray));
				rect4.setColor(((philosophers[3].eating) ? Color.cyan : Color.lightGray));
			}
			
			break;
			
		case 3:
			if (philosopher.full) {
				circle4.setColor(Color.blue);
				break;
			}
			
			circle4.setColor(((philosopher.eating) ? Color.yellow : Color.blue));
			
			if (philosopher.eating) {
				rect4.setColor(Color.cyan);
				rect5.setColor(Color.cyan);
				
			} else {
				rect4.setColor(((philosophers[2].eating) ? Color.cyan : Color.lightGray));
				rect5.setColor(((philosophers[4].eating) ? Color.cyan : Color.lightGray));
			}
			break;
			
		case 4:
			if (philosopher.full) {
				circle5.setColor(Color.blue);
				break;
			}
			
			circle5.setColor(((philosopher.eating) ? Color.yellow : Color.blue));
			
			if (philosopher.eating) {
				rect5.setColor(Color.cyan);
				rect1.setColor(Color.cyan);
			}
			else {
				rect5.setColor(((philosophers[3].eating) ? Color.cyan : Color.lightGray));
				rect1.setColor(((philosophers[0].eating) ? Color.cyan : Color.lightGray));
			}
			break;
		}
		
		repaint();
	}
	
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			button.setEnabled(false);
			for (int i = 0; i < 5; i++)
				executorService.execute(philosophers[i]);
			timer = new Timer();
			timer.schedule(new endingTimerTask(), 20 * 1000);

		}

	}
	private class endingTimerTask extends TimerTask {

		@Override
		public void run() {

			for (Philosopher philosopher : philosophers)
				philosopher.full = true;
			
			// Close everything down.
			executorService.shutdown();

			// Wait for all thread to finish
			while (!executorService.isTerminated()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			System.out.println("-----------------------------------------\n");

			for (Philosopher philosopher : philosophers) {
				System.out.println(philosopher + " ate this many times:  "
						+ philosopher.getTimesEaten());
			}
			
			rect1.setColor(Color.lightGray);
			rect2.setColor(Color.lightGray);
			rect3.setColor(Color.lightGray);
			rect4.setColor(Color.lightGray);
			rect5.setColor(Color.lightGray);
			
			
			repaint();

		}

	}

}

package ViewController;
import javax.swing.*;
/*
 * Program simulates the Dining Philosophers problem for 20 seconds
 * In that time the philosophers will randomly get hungry and try to eat.
 * Each philosopher is simulated in its own thread and the forks are used to lock up the threads
 * 
 * Its takes a set amount of time for the philosophers to think or eat
 * 
 * The console will display the times at which the philosophers think, eat, and pickforks up or down.
 * 
 * At the end of the simulation the amount of times each philosopher was able to eat is displayed.
 * 
 */
public class Deadlocks {
	public static void main(String[] args) {
		JFrame frame = new JFrame("DeadLocks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new DeadLocksPanel());
		
		frame.pack();
		frame.setVisible(true);
		
	}
}

package Model;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Philosopher extends Observable implements Runnable {

	public final int ID;
	private final Fork leftFork;
	private final Fork rightFork;
	private Random random = new Random();
	public volatile boolean full = false;
	private int timesEaten = 0;
	
	public boolean rightForkUp	= false,
				   leftForkUp	= false,
				   eating		= false,
				   thinking		= false;

	public int getTimesEaten() {
		return timesEaten;
	}

	public Philosopher(int id, Fork left, Fork right) {
		this.ID = id;
		this.leftFork = left;
		this.rightFork = right;
	}

	public void run() {

		try {
			while (!full) {
				think();
				
				if (leftFork.pickUp(this, "left")) {
					if (rightFork.pickUp(this, "right")) {

						eat();
						
						rightFork.putDown(this, "right");
					}
		
					leftFork.putDown(this, "left");
				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void think() throws InterruptedException {
		thinking = true;
		eating = false;
		
		setChanged();
		notifyObservers();
		Thread.sleep(random.nextInt(4) * 1000 + 3);
	}

	private void eat() throws InterruptedException {
		eating = true;
		thinking = false;
		leftForkUp = true;
		rightForkUp = true;
		timesEaten++;
		
		setChanged();
		notifyObservers();
		
		Thread.sleep(random.nextInt(4) * 1000 + 3);
		
		leftForkUp = false;
		rightForkUp = false;
		
		setChanged();
		notifyObservers();
	}

	@Override
	public String toString() {
		return "Philosopher-" + ID;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
	}
}
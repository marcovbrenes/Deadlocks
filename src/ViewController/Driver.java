package ViewController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Fork;
import Model.Philosopher;

public class Driver {

	private static final int NO_OF_PHILOSOPHER = 5;
	private static final int SIMULATION_MILLIS = 1000 * 5;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = null;

		Philosopher[] philosophers = null;
		try {

			philosophers = new Philosopher[NO_OF_PHILOSOPHER];

			// As many forks as Philosophers
			Fork[] Forks = new Fork[NO_OF_PHILOSOPHER];
		
			for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
				Forks[i] = new Fork(i);
			}

			executorService = Executors.newFixedThreadPool(NO_OF_PHILOSOPHER);

			for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
				philosophers[i] = new Philosopher(i, Forks[i], Forks[(i + 1)
						% NO_OF_PHILOSOPHER]);
				executorService.execute(philosophers[i]);
			}
			// Main thread sleeps till time of simulation
			Thread.sleep(SIMULATION_MILLIS);
			// Stop all philosophers.
			for (Philosopher philosopher : philosophers) {
				philosopher.full = true;
			}

		} finally {
			// Close everything down.
			executorService.shutdown();

			// Wait for all thread to finish
			while (!executorService.isTerminated()) {
				Thread.sleep(1000);
			}

			// Time for check
			for (Philosopher philosopher : philosophers) {
				System.out.println(philosopher + " => No of Turns to Eat ="
						+ philosopher.getTimesEaten());

			}
		}
	}
}
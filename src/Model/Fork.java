package Model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	ReentrantLock lock = new ReentrantLock();
	private final int id;

	public Fork(int id) {
		this.id = id;
	}

	public boolean pickUp(Philosopher p, String rightORLeft) throws InterruptedException {

		if(lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(p + " picked up" + rightORLeft + " " + this);
			return true;
		}
		return false;
	}

	public void putDown(Philosopher p, String n) {
		lock.unlock();
		System.out.println(p + " put down " + n + this);
	}

	@Override
	public String toString() {
		return "Fork-" + id;
	}
}
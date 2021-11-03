package threads.warehouse;

public class Producer extends Worker {

	public Producer(Warehouse warehouse) {
		super(warehouse);
	}

	@Override
	protected void work() throws InterruptedException {
		/*
		 * synchronized naredba
		 */
		synchronized (warehouse) {
			int val = (int)(Math.random() * 10);
			warehouse.put(val);
			System.out.println(getWorkerName() + " put " + val);
		}
	}

	@Override
	protected char type() {
		return 'P';
	}
}

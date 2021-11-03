package threads.warehouse;

public class Consumer extends Worker {

	public Consumer(Warehouse warehouse) {
		super(warehouse);
	}

	@Override
	protected void work() throws InterruptedException {
		synchronized (warehouse) {
			int val = warehouse.get();
			System.out.println(getWorkerName() + " got " + val);
		}
	}

	@Override
	protected char type() {
		return 'C';
	}
}

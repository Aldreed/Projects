package threads.warehouse;

public abstract class Worker extends Thread {

	private static int sid;
	private int id = ++sid;
	protected Warehouse warehouse;
	
	public Worker(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	protected abstract void work() throws InterruptedException;
	protected abstract char type();
	
	public String getWorkerName() {
		return type() + "" + id;
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted()) {
			try {
				work();
				sleep((long) Math.random() * 500 + 250);
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
}

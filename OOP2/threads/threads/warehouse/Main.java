package threads.warehouse;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		Warehouse warehouse = new Warehouse(5);
		Worker workers[] = new Worker[3];
		
		for(int i = 0; i < workers.length; i++) {
			if(i % 2 == 0)
				workers[i] = new Producer(warehouse);
			else workers[i] = new Consumer(warehouse);
		}
		
		for(int i = 0; i < workers.length; i++)
			workers[i].start();
		
		Thread.sleep(1000);
		
		for(int i = 0; i < workers.length; i++) {
			workers[i].interrupt();
			workers[i].join();
		}
		
		System.out.println("Warehouse: " + warehouse);
	}
}

package threads.boss_workers;

public class Boss extends Thread {

	private static int sid;
	private int id = ++sid;
	private Worker workers[];
	
	public Boss(Worker[] workers) {
		this.workers = workers;
	}
	
	private void job() {
		System.out.println(getWorkerName() + " doin' some stuff");
	}
	
	public String getWorkerName() {
		return "Boss " + id;
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted()) {
			try {
				
				for(int i = 0; i < workers.length; i++) {
					//Obavesti radnika da treba da krene sa poslom
					workers[i].setWorks(true);
				}
				
				for(int i = 0; i < workers.length; i++) {
					synchronized (workers[i]) {
						//Cekaj da te radnik obavesti da je zavrsio sa poslom
						while(workers[i].getWorks())
							workers[i].wait();
					}
				}
				
				//Sada samo sef radi posao, a radnici ne rade posao.
				job();
				sleep((long) Math.random() * 500 + 500);
				System.out.println(getWorkerName() + " is done");
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
}

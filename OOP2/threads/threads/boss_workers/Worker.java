package threads.boss_workers;

public class Worker extends Thread {

	private static int sid;
	private int id = ++sid;
	private boolean works = false;
	
	private void job() {
		System.out.println(getWorkerName() + " doin' some stuff");
	}
	
	public String getWorkerName() {
		return "Worker " + id;
	}
	
	public synchronized void setWorks(boolean works) {
		this.works = works;
		notify();
	}
	
	public synchronized boolean getWorks() {
		return works;
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted()) {
			try {
				synchronized (this) {
					while(!works) {
						wait();
					}
				}
				job();
				/*
				 * Simulira se vreme odradjivanja posla
				 */
				sleep((long) Math.random() * 500 + 250);
				System.out.println(getWorkerName() + " is done");
				synchronized (this) {
					//Radnik je zavrsio posao
					works = false;
					//i obavestava sefa (koji ceka na tekucem objektu radnika) da je zavrsio posao
					notify();
				}
			} catch (InterruptedException e) {
				interrupt();
			}
		}
	}
}

package threads.warehouse;

public class Warehouse {

	private int cnt, front, rear;
	private int data[];
	
	public Warehouse(int cap) {
		data = new int [cap];
	}

	/*
	 * wait - nit koja pozove ovaj metod prelazi u stanje cekanja (WAITING), 
	 * ceka na monitoru objekta za koji je metod wait pozvan 
	 * i nece se probuditi sve dok je neka druga nit ne probudi 
	 * pozivom metoda notify, notifyAll ili interrupt.
	 * U slucaju da je nit bila suspendovana pozivom wait, 
	 * a zatim prekinuta pozivom metode interrupt 
	 * emituje se izuzetak InterruptedException.
	 * 
	 * notify, notifyAll - bude jednu (sve) niti koje cekaju na monitoru objekta 
	 * za koji je ovaj metod pozvan.
	 * Ukoliko su probudjene sve niti one se utrkuju za vlasnistvo nad monitorom tog objekta 
	 * i samo jedna ce uspeti da postane vlasnik i ona prelazi u stanje RUNNABLE, 
	 * dok ostale niti prelaze u stanje BLOCKED 
	 * i cekaju da vlasnik monitora objekta prepusti vlasnistvo 
	 * (napusti synchronized metod ili naredbu)
	 * nakon cega se ponovo utrkuju za vlasnistvo nad bravom objekta.
	 * 
	 * Pozivi metoda notify, notifyAll i wait klase Object moraju se vrsiti iz sinhronizovanog koda 
	 * za odgovarajuci objekat nad kojim se sinhronizacija vrsi.
	 * U ovom slucaju se vrsi sinhronizacija nad tekucim objektom klase Warehouse,
	 * (iz koda u redefinicijama metoda work u klasama Producer i Consumer) 
	 * sto znaci da je dozvoljeno uraditi this.wait() ili this.notify().
	 * 
	 * Ukoliko se pokusa otherObject.wait() ili otherObject.notify()
	 * nad kojima se ne vrsi sinhronizacija 
	 * desava se IllegalMonitorStateException. 
	 */
	
	public /*synchronized*/ void put(int val) throws InterruptedException {
		
		while(cnt >= data.length)
			wait();
		
		data[rear] = val;
		rear = (rear + 1) % data.length;
		cnt++;
		notifyAll();
	}
	
	public /*synchronized*/ int get() throws InterruptedException {
		
		while(cnt <= 0)
			wait();
		
		int val = data[front];
		front = (front + 1) % data.length;
		cnt--;
		
		notifyAll();
		
		return val;
	}
	
	@Override
	public synchronized String toString() {
		
		StringBuffer sb = new StringBuffer("[");
		
		for(int i = 0; i < cnt; i++) {
			sb.append(data[(front + i) % data.length]);
			if(i < cnt - 1)
				sb.append(" ");
		}
		
		return sb.append("]").toString();
	}
}

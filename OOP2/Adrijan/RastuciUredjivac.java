package paket;

import java.io.FileWriter;
import java.io.IOException;

public class RastuciUredjivac implements Uredjivac {

	@Override
	public void uredi(Uporediv[] niz) {
		FileWriter writer = null;
		try {
			writer = new FileWriter("output.txt");
			int brojzamena = 0;
			for(int i = 0; i < niz.length - 1; i++) {
				for(int j = i; j < niz.length; j++) {
					if(niz[i].veca(niz[j])) {
						brojzamena++;
						Uporediv tmp = niz[i];
						niz[i] = niz[j];
						niz[j] = tmp;
					}
				}
			}
			if(brojzamena == 0) throw new NeproverenIzuzetak(); // 0
			writer.write("Broj zamena je : " + brojzamena);
			// 1
		} catch(GreskaNeuporedivi | IOException g) {
			System.err.println(g);
			// 2
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		// 1 i 2
	}

}

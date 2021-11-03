package tekici;

import java.awt.Color;

public class Trava extends Polje {

	public Trava(Mreza mr) {
		super(mr);
		this.setBackground(Color.green);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return true;
	}

}

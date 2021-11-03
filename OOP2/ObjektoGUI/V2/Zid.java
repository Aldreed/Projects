package tekici;

import java.awt.Color;

public class Zid extends Polje {

	public Zid(Mreza mr) {
		super(mr);
		this.setBackground(Color.lightGray);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return false;
	}

}

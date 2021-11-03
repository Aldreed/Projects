package tekici;

import java.awt.Canvas;

public abstract class Polje extends Canvas {
	private Mreza mojaMreza;
	
	
	public Polje(Mreza mr) {
		mojaMreza=mr;
	}
	
	public int[] pozicija() {
		
			for (int i = 0; i < mojaMreza.dim; i++) {
				for (int j = 0; j < mojaMreza.dim; j++) {
					if(mojaMreza.mat[i][j]==this) {
						return new int[] {i,j};
					}
				}
			}
			return null;
	}

	public Polje pomeraj(int dn , int dm) {
		int[] temp = this.pozicija();
		int n = temp[0];
		int m = temp[1];
		int tn=dn+n;
		int tm= m+dm;
		if(tn<0||tn>mojaMreza.dim-1||tm<0||tm>mojaMreza.dim-1) {
			return null;
		}
		return mojaMreza.mat[tn][tm];
	}
	
	public abstract boolean dozvoljeno(Figura f);

	public Mreza getMojaMreza() {
		return mojaMreza;
	}

	
}

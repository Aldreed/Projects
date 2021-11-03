package tekici;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mreza extends Panel implements Runnable {
	int dim;
	Polje[][]mat;
	
	private List<Figura>igraci;
	private List<Figura>novcici;
	private List<Figura>tenkici;
	private Igra mojaIgra;
	
	Thread t = null;
	
	public Mreza(Igra g,int i) {
		super(new GridLayout(i, i));
		igraci=new ArrayList<Figura>();
		novcici=new ArrayList<Figura>();
		tenkici=new ArrayList<Figura>();
		dim =i;
		mat= new Polje[i][i];
		mojaIgra = g;
		Random r = new Random(System.nanoTime());
		for (int j = 0; j < dim; j++) {
			for (int j2 = 0; j2 < dim; j2++) {
				Polje temp;
				if(r.nextDouble()<=0.8) {
					temp = new Trava(this);
					mat[j][j2]=temp;
					this.add(temp);
				}
				else {
					temp = new Zid(this);
					mat[j][j2]=temp;
					this.add(temp);
				}
				temp.addMouseListener(new MouseAdapter() {

					@Override
					public void mousePressed(MouseEvent e) {
						if(!mojaIgra.rezimIgranja) {
							Polje p =	(Polje) e.getSource();
							int[] cord = p.pozicija();
							Polje temp = null;
							for (int i = 0; i <p.getMojaMreza().mojaIgra.editSetings.length; i++) {
								if(p.getMojaMreza().mojaIgra.editSetings[i].getState()){
									String s=p.getMojaMreza().mojaIgra.editSetings[i].getLabel();
									switch (s) {
									case "Trava":
										temp = new Trava(p.getMojaMreza());
										break;
									case "Zid":
										temp = new Zid(p.getMojaMreza());
										break;
									}
									p.getMojaMreza().remove(p);
									p.getMojaMreza().add(temp,cord[0]*p.getMojaMreza().dim+cord[1]);
									p.getMojaMreza().revalidate();
									mat[cord[0]][cord[1]]=temp;
									break;
								}
							}
						}
					}
				});
				
			}
			
		}
		
		
	}
	
	public Mreza(Igra g) {
		this(g,17);
	}
	
	

	@Override
	public void run() {
		while(!t.interrupted()) {
			try {
				t.sleep(40);
				azuriraj();
				iscrtajFigure();
			} catch (InterruptedException e) {
				t.interrupt();
			}
		}
		iscrtajFigure();
	}
	
	private synchronized void azuriraj() {
		Figura term=null;
		for (Figura figura : igraci) {
			for (Figura figura2 : novcici) {
				if(figura.getMojePolje()==figura2.getMojePolje()) {
					mojaIgra.osvojeni_poeni++;
					mojaIgra.poeni.setText("Poena: " +mojaIgra.osvojeni_poeni+" ");
					term=figura2;
				}
			}
		}
		if (term!=null) {
			novcici.remove(term);
			term.getMojePolje().repaint();
		}
		if(novcici.isEmpty()) {
			this.zaustavi();
		}
		term = null;
		for (Figura figura : igraci) {
			for (Figura figura2 : tenkici) {
				if(figura.getMojePolje()==figura2.getMojePolje()) {
					term=figura;
					this.zaustavi();
				}
			}
		}
		if(term!=null) {
			igraci.remove(term);
		}
	}
	

	private synchronized void iscrtajFigure() {
		
		for (Figura figura : novcici) {
			figura.iscrtaj();
		}
		for (Figura figura : tenkici) {
			figura.iscrtaj();
		}
		for (Figura figura : igraci) {
			figura.iscrtaj();
		}
	}
	
	public void inicijalizuj() {
		if(mojaIgra.rezimIgranja) {
			novcici.clear();
			tenkici.clear();
			igraci.clear();
			//iscrtajFigure();
			for (int i = 0; i <dim; i++) {
				for (int j = 0; j <dim; j++) {
					mat[i][j].repaint();
				}
				
			}
			Random r = new Random(System.nanoTime());
			int num = Integer.parseInt(mojaIgra.novcici.getText());//todo overflow
			//inicializacija Novcica
			for (int i = 0; i < num; i++) {
				Novcic temp = null;
				int n,m;
				do {
					do {
						 n = r.nextInt(dim);
						 m = r.nextInt(dim);
						temp = new Novcic(mat[n][m]);
					} while (!mat[n][m].dozvoljeno(temp));
				} while (novcici.contains(temp));
				novcici.add(temp);
			}
			//inicijalizacija Tenkica
			for (int i = 0; i < num/3; i++) {
				Tenk temp = null;
				int n,m;
				do {
					do {
						 n = r.nextInt(dim);
						 m = r.nextInt(dim);
						temp = new Tenk(mat[n][m]);
					} while (!mat[n][m].dozvoljeno(temp));
				} while (false);
				tenkici.add(temp);
			}
			//inicijalizacija Igraca
			for (int i = 0; i <1; i++) {//ako se trazi veci broj igraca iz nekog razloga na modifikaciji
				Igrac temp = null;
				int n,m;
				boolean moze =true;
				do {
					do {
						 n = r.nextInt(dim);
						 m = r.nextInt(dim);
						temp = new Igrac(mat[n][m]);
					} while (!mat[n][m].dozvoljeno(temp));
					
					moze =true;
					for (Figura figura : novcici) {
						if(figura.getMojePolje()==temp.getMojePolje()) {
							moze=false;
							break;
						}
					}
					for (Figura figura : tenkici) {
						if(figura.getMojePolje()==temp.getMojePolje()) {
							moze=false;
							break;
						}
					}
				} while (!moze||igraci.contains(temp));
				igraci.add(temp);
			}
			this.iscrtajFigure();
			this.pokreni();
		}
	}
	public synchronized void pokreni() {
		t=new Thread(this);
		for (Figura figura : tenkici) {
			((Tenk) figura).pokreni();
		}
		t.start();
	}
	
	public synchronized void zaustavi() {
		for (Figura figura : tenkici) {
			((Tenk) figura).zaustavi();
		}
		if(t!=null)t.interrupt();
	}
	void pomeriIgraca(int dn,int dm) {
		for (Figura figura : igraci) {
			figura.pomeri(figura.getMojePolje().pomeraj(dn, dm));
		}
	}

	public List<Figura> getIgraci() {
		return igraci;
	}

	public List<Figura> getNovcici() {
		return novcici;
	}

	public List<Figura> getTenkici() {
		return tenkici;
	}
	
}

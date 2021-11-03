#include "Gradska_linija.h"

Gradska_linija::Gradska_linija(string naziv, Lista<Stajaliste> ml):oznaka(naziv),lista(ml)
{
}

int operator&(const Gradska_linija & g1, const Gradska_linija & g2)
{
	int temp = 0;
	g1.lista.setPrvi();
	g2.lista.setPrvi();
	while (g1.lista.posTek()) {
		while (g2.lista.posTek())
		{
			if (g1.lista.dohvati() == g2.lista.dohvati())temp++;
			g2.lista.sledeci();
		}
		g2.lista.setPrvi();
		g1.lista.sledeci();
	}
	return temp;

}

ostream & operator<<(ostream & os, const Gradska_linija & gl)
{
	os << gl.oznaka << endl << gl.lista;
	return os;
}

#include "Ruta.h"

Ruta::Ruta(Lista<Deonica> l):duz(0),lista(l)
{
	for (lista.setPrvi();lista.posTek();lista.sledeci())
	{
		duz += lista.dohvati().operator~();
	}
	
}

double Ruta::operator()(double d) const
{
	return d*duz;
}

double Ruta::operator()(Deonica::katvoz voz) const
{
	return duz*(voz?11:22);
}

ostream & operator<<(ostream & os, const Ruta & r)
{
	for (r.lista.setPrvi();r.lista.posTek();r.lista.sledeci())
	{
		os << r.lista.dohvati() << endl;
	}
	return os;
}

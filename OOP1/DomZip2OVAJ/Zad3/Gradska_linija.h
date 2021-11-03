#pragma once
#include "Lista.h"
#include "Stajaliste.h"
class Gradska_linija
{
	string oznaka;
	Lista<Stajaliste> lista;
public:
	Gradska_linija(string naziv, Lista<Stajaliste> ml);
	string getoznaka()const {
		return oznaka;
	}
	Lista<Stajaliste>& getLista() {
		return lista;
	}
	const Lista<Stajaliste>& getLista() const {
		return lista;
	}
	
	friend int operator&(const Gradska_linija& g1, const Gradska_linija& g2);
	friend ostream& operator<< (ostream& os, const Gradska_linija& gl);
};


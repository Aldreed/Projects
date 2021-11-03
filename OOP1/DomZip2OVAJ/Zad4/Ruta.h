#pragma once
#include "Deonica.h"
#include "Lista.h"
class Ruta
{
	double duz;
	Lista<Deonica> lista;
public:
	Ruta(Lista<Deonica> l);
	double operator()(double d)const;
	double operator()(Deonica::katvoz voz)const;
	friend ostream& operator<<(ostream& os, const Ruta& r);
};


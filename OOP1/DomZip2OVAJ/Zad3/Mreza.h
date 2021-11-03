#pragma once
#include "Lista.h"
#include "Gradska_linija.h"

class Mreza
{
	Lista<Gradska_linija> linije;
public:
	Mreza& operator+=( const Gradska_linija& gl);
	friend ostream& operator<<(ostream& os, const Mreza& mr);
};


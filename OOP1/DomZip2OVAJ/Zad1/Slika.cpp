#include "Slika.h"
#include <iostream>

ostream & Slika::opis(ostream& os) const
{
	os << "[" << sirina << " x " << visina << " -> " << velicina() << "B]";
	return os;
}

Slika::Slika(string ime,int a, int b, int c):Multimedia(ime),sirina(a),visina(b),bajt(c)
{
	if (a<0 ||b<0||c<0)
	{
		std::cout << "pogresno uneti parametri";
		exit(1);
	}
}

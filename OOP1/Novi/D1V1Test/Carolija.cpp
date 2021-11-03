#include "Carolija.h"



Carolija::Carolija(int mag,string ime):Karta(mag,ime)
{
	kat = CAROLIJA;
}

Karta * Carolija::newInstance() const
{
	Carolija* p = new Carolija(this->GetMag(), this->GetIme());
	
	return p;
	
}

void Carolija::Upotrebi(Igrac&, Igrac&)
{
}




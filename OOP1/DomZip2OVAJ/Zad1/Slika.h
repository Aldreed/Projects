#pragma once
#include "Multimedia.h"
class Slika :
	public Multimedia
{
	unsigned int sirina;
	unsigned int visina;
	unsigned int bajt;
	unsigned int velicina()const override {
		return bajt * sirina*visina;
	}
	ostream& opis(ostream& os)const override;
public:
	Slika(string ime,int a, int b, int c);

	
};


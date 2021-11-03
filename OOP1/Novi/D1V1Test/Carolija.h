#pragma once
#include "Karta.h"
#include "Igrac.h"
class Carolija :
	public Karta
{
	Karta* newInstance()const override;
public:
	Carolija(int,string);
	
	void Upotrebi(Igrac&, Igrac&)override;
	
};


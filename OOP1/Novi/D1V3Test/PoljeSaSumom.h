#pragma once
#include "Polje.h"
class PoljeSaSumom :
	public Polje
{
	Polje* kopija()const override;
	unsigned int gustina;
public:
	PoljeSaSumom(int gus, int nep=100);
	std::ostream& ispis(std::ostream& os)const override;
	
};


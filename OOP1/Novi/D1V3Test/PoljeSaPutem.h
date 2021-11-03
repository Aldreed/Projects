#pragma once
#include "Polje.h"
class PoljeSaPutem :
	public Polje
{	public:
	enum tip { ZEMLJANI, KAMENI };
private:
	tip kat;
	Polje* kopija()const override;
public:
	PoljeSaPutem(tip t, int nep=100);
	std::ostream& ispis(std::ostream& os)const override;
};


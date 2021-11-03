#pragma once
#include "Borac.h"
class Carobnjak :
	public Borac
{
	Karta* newInstance() const;
	void Efekat(Igrac&, Igrac&)override;
public:
	Carobnjak(int i, string k, int h);
	
	std::ostream& ispis(std::ostream& os)const override;
};


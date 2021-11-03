#pragma once
#include "Karta.h"
#include "Igrac.h"
class Borac :
	public Karta
{
	int Snaga;
protected:
	bool Upotrebljen;
	Karta* newInstance() const = 0;
	virtual void Efekat(Igrac&, Igrac&) = 0;
public:
	Borac(int, string, int);
	
	int GetSnaga()const;
	void Upotrebi(Igrac&, Igrac&)override;
	
	bool operator>(Borac&)const;
	bool operator<(Borac&)const;
	std::ostream& ispis(std::ostream& os)const override;
};


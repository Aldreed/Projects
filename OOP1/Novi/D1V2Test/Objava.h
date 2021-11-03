#pragma once
#include "Obavestenje.h"
#include "Korisnik.h"
class Objava :public Obavestenje
{ 
	string tekst;
	Objava* kopija()const override;
public:
	Objava(string, Korisnik&, bool);
	std::ostream& ispis(std::ostream&) const override;
	
	~Objava(){}


};


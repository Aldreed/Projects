#pragma once
#include <iostream>
#include <string>
#include "Obavestenje.h"


using std::string;
class ListaObavestenja;
class Korisnik
{
	string ime;

	ListaObavestenja* moja;
	

public:

	Korisnik(string);
	Korisnik(Korisnik& const) = delete;
	Korisnik(Korisnik&&) = delete;
	Korisnik& operator=(Korisnik&&) = delete;
	Korisnik& operator=(Korisnik &const) = delete;
	friend std::ostream& operator<<(std::ostream& os, Korisnik&);
	string GetIme() const;
	void Primi(Obavestenje&);
	void Posalji(Obavestenje&, Korisnik&) const;
	virtual std::ostream& ispis(std::ostream&) const ;
	virtual ~Korisnik();
};


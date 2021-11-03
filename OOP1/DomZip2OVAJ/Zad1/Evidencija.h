#pragma once
#include "Lista.h"
#include "Korisnik.h"
#include "gres.h"
class Evidencija
{
	Lista<Korisnik*> lista;

public:
	Evidencija(){}
	Evidencija(const Evidencija&) = delete;
	Evidencija(Evidencija&&) = delete;
	Evidencija& operator=(const Evidencija&) = delete;
	Evidencija& operator=(Evidencija&&) = delete;

	Evidencija& operator += ( Korisnik&);
	 Korisnik operator[](string);
	const Korisnik operator[](string)const;
	void operator()(string ime);

	int promeniSifru(string ime, string old, string nova);
};


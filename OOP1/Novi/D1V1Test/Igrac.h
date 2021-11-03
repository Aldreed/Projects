#pragma once
#include "Karta.h"
#include "Zbirka.h"

class Igrac
{
	int MEn;
	int LEn;
	string Ime;
	Zbirka* Spil;
	Zbirka* Aktivirane;
	Zbirka* Ruka;
	Zbirka* Grav;
	void premesti(Igrac&const);
	void brisi();
	void kopiraj(Igrac& const);
	
	
public:
	Igrac(int, int, string, Zbirka&);
	Igrac(Igrac& const);
	Igrac(Igrac&&);
	
	void Aktiviraj(int i);
	void SetMEn(int);
	void SetLen(int);
	int GetMEn();
	int GetLen();
	Zbirka& GetSpil();
	Zbirka& GetAktivirane();
	Zbirka& GetRuka();
	Zbirka& GetGrav();
	
	Igrac& operator=(Igrac& const);
	Igrac&  operator=(Igrac&&);
	friend class Zbirka;
	~Igrac();
};


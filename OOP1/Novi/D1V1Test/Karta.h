#pragma once

#include<iostream>
#include<string>
using std::string;
class Igrac;
class Borac;
class Carobnjak;
class Zbirka;
class Karta {
	
	friend class Zbirka;
	int MagiE;
	string Ime;
	static int PosIdKarte;
	int idKarte;
	
protected:
	
	
	virtual Karta* newInstance()const = 0;
	enum Kategorija { CAROLIJA, BORAC, ERROR };
	Kategorija kat;
public:
	
	virtual ~Karta() {};
	Karta(Karta&) = delete;
	Karta(Karta&&) = delete;
	Karta& operator=(Karta&const) = delete;
	Karta& operator=(Karta&&) = delete;
	Karta(int,string);
	int GetMag() const;
	string GetIme()const;
	int GetID()const;
	Kategorija GetKat()const;

	
	virtual void Upotrebi(Igrac&, Igrac&) = 0;
	friend std::ostream& operator<<(std::ostream&, Karta&);
	virtual std::ostream& ispis(std::ostream&)const;
	

	
};
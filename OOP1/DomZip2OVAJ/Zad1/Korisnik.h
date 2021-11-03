#pragma once
#include <iostream>
#include  <string>
#include "gres.h"
using std::string;
class Korisnik
{
	void checkLoz(string loz);
	string ime;
	string lozinka;
public:
	Korisnik(string im,string loz);
	void promeniLozinku(string old,string nova);
	string getIme()const;
	string getLoz()const;
	friend bool operator==(const Korisnik& k1, const Korisnik& k2) {
		if (k1.getIme() == k2.getIme()) {
			return true;
		}
		return false;
	}
	friend std::ostream& operator<<(std::ostream& os, const Korisnik& k);

};


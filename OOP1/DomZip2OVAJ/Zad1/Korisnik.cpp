#include "Korisnik.h"

void Korisnik::checkLoz(string loz)
{
	if (loz.length() < 8 || (loz.find('0') == -1 && loz.find('1') == -1 && loz.find('2') == -1 && loz.find('3') == -1 && loz.find('4') == -1 && loz.find('5') == -1 && loz.find('6') == -1 && loz.find('7') == -1 && loz.find('8') == -1 && loz.find('9') == -1)) {
		throw gloz();
	}
	int temo = -1;
	for (int i = 0; i < loz.length(); i++) {
		if (isalpha(loz[i]))temo++;
	}
	if (temo==-1)
	{
		throw gloz();
	}
}


Korisnik::Korisnik(string im, string loz): ime(im),lozinka(loz)
{
	checkLoz(loz);
}

void Korisnik::promeniLozinku(string old,string nova)
{
	if (old != lozinka) {
		throw glozinc();
}
	else
	{
		checkLoz(nova);
		lozinka = nova;
	}
}

string Korisnik::getIme() const
{
	return ime;
}

string Korisnik::getLoz() const
{
	return lozinka;
}

std::ostream & operator<<(std::ostream& os, const Korisnik & k) 
{
	os << k.getIme();
	return os;
	
}

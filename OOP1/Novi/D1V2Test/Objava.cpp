#include "Objava.h"
#include "Obavestenje.h"



Objava::Objava(string t, Korisnik & k, bool g=false) :Obavestenje(k, g),tekst(t)
{
}

std::ostream & Objava::ispis(std::ostream & os)const
{
	Obavestenje::ispis(os);
	os << tekst;
	return os;
}

Objava * Objava::kopija()const
{
	return new Objava(*this);
}

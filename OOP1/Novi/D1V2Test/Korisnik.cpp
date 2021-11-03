#include "Korisnik.h"
#include "ListaObavestenja.h"

Korisnik::Korisnik(string s) :ime(s), moja(new ListaObavestenja())
{

}

string Korisnik::GetIme() const
{
	return ime ;
}

void Korisnik::Primi(Obavestenje & o)
{
	*moja += o;
}

void Korisnik::Posalji(Obavestenje &o, Korisnik &k)const
{
	k.Primi(o);
}

std::ostream & Korisnik::ispis(std::ostream & os) const
{
	os << ime;
	return os;
}

Korisnik::~Korisnik()
{
	delete moja;
	moja = nullptr;
}

std::ostream &operator<<(std::ostream & os, Korisnik & k)
{
	
	k.ispis(os);
	return os;
}

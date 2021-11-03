#include "Obavestenje.h"
#include "Korisnik.h"
int Obavestenje::idCount = 0;
Obavestenje::Obavestenje(Korisnik& k, bool b =false):mojKorisnik(&k),seen(b),id(idCount++),timeO(time(0))
{
}
bool Obavestenje::GetSeen() const
{
	return seen;
}

int Obavestenje::GetID() const
{
	return id;
}

Korisnik & Obavestenje::GetKorisnik() const
{
	return *mojKorisnik;
}

void Obavestenje::See()
{
	seen = true;
}

std::ostream & Obavestenje::ispis(std::ostream & os) const
{
	os << id << "|" << mojKorisnik->GetIme() << "-" << (timeO / 3600) % 24 + 1 << ":" << (timeO / 60) % 60 << ":" << timeO % 60 << std::endl;
	return os;
}


std::ostream & operator<<(std::ostream & os, Obavestenje & k)
{
	
	k.ispis(os);
	return os;
}

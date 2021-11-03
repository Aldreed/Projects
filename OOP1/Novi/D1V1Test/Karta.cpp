#include "Karta.h"
int Karta::PosIdKarte = 0;

Karta::Karta(int mag, string ime):MagiE(mag),Ime(ime),idKarte(PosIdKarte++),kat(ERROR)
{
}

int Karta::GetMag()const
{
	return MagiE;
}

string Karta::GetIme() const
{
	return Ime;
}

int Karta::GetID() const
{
	return idKarte;
}

Karta::Kategorija Karta::GetKat() const
{
	return kat;
}

std::ostream & Karta::ispis(std::ostream& os)const
{
	os << "[" << GetID() << "] " << '"' << GetIme() << '"'<<" " << "(cena: " << GetMag() << ")";
	return os;
}

std::ostream & operator<<(std::ostream & os, Karta & card)
{
	return card.ispis(os);
}

#include "Igrac.h"

Igrac::Igrac(int m, int l, string im, Zbirka& z) :MEn(m), LEn(l), Ime(im), Spil(new Zbirka(z)),Ruka(new Zbirka()),Grav(new Zbirka()),Aktivirane(new Zbirka())
{
	if (m < 0 || l < 0) {
		exit(1);
	}
}

Igrac::Igrac(Igrac & const k)
{
	kopiraj(k);
}

Igrac::Igrac(Igrac &&k)
{
	
	premesti(k);
}

void Igrac::premesti(Igrac & const k)
{
	MEn = k.MEn;
	LEn = k.LEn;
	Spil = k.Spil;
	Aktivirane = k.Aktivirane;
	Ruka = k.Ruka;
	Grav = k.Grav;
	k.Spil = nullptr;
	k.Aktivirane = nullptr;
	k.Ruka = nullptr;
	k.Grav = nullptr;
}

void Igrac::brisi()
{
	delete Spil;
	delete Aktivirane;
	delete Ruka;
	delete Grav;
	Spil = nullptr;
	Aktivirane = nullptr;
	Ruka = nullptr;
	Grav = nullptr;
}

void Igrac::kopiraj(Igrac & const k)
{
	MEn = k.MEn;
	LEn = k.LEn;
	Ime = k.Ime;
	Spil = new Zbirka(k.GetSpil());
	Aktivirane = new Zbirka(k.GetAktivirane());
	Ruka = new Zbirka(k.GetRuka());
	Grav = new Zbirka(k.GetGrav());

}

void Igrac::Aktiviraj(int i)
{
	int t =(*Ruka)[i].GetMag();
	if (MEn >= t) {
		MEn -= t;
		(*Aktivirane).DodajKartu((*Ruka)(i));
		
	}
	else(std::cout << "Nema dovoljno MEn");
}

void Igrac::SetMEn(int i)
{
	MEn += i;
}

void Igrac::SetLen(int i)
{
	LEn += i;
}

int Igrac::GetMEn()
{
	return MEn;
}

int Igrac::GetLen()
{
	return LEn;
}

Zbirka& Igrac::GetSpil()
{
	return *Spil;
}

Zbirka& Igrac::GetAktivirane()
{
	return *Aktivirane;
}

Zbirka&Igrac::GetRuka()
{
	return *Ruka;
}

Zbirka& Igrac::GetGrav()
{
	return *Grav;
}



Igrac & Igrac::operator=(Igrac & const k)
{
	if (this != &k) {
		brisi();
		kopiraj(k);
	}
	return *this;
}

Igrac & Igrac::operator=(Igrac && k)
{
	if (this != &k) {
		brisi();
		premesti(k);
	}
	return *this;
}

Igrac::~Igrac()
{
	delete Spil;
	delete Aktivirane;
	delete Ruka;
	delete Grav;
	Spil = nullptr;
	Aktivirane = nullptr;
	Ruka = nullptr;
	Grav = nullptr;
}

#include "Evidencija.h"

Evidencija & Evidencija::operator+=( Korisnik &k)
{
	for (int i = 1; i <= lista.getBroj(); i++) {
		if (*lista[i] == k)throw gdup();
	}
	lista += &k;
}

Korisnik Evidencija::operator[](string ime)
{
	for (int i = 1; i <= lista.getBroj(); i++) {
		if (lista[i]->getIme()==ime) {
			return *lista[i];
		}
	}
	throw gnf();
}

const Korisnik Evidencija::operator[](string ime) const
{
	for (int i = 1; i <= lista.getBroj(); i++) {
		if (lista[i]->getIme() == ime) {
			return *lista[i];
		}
	}
	throw gnf();
}

void Evidencija::operator()(string ime)
{
	int t = 1;
	for (int i = 1; i < lista.getBroj(); i++) {
		if (lista[i]->getIme() == ime) {
			lista(i);
		}
		else {
			t++;
		}
		
	}
	if (t == lista.getBroj()) {
		throw gnf();
	}
	
}

int Evidencija::promeniSifru(string ime, string old, string nova)
{
	for (int i = 1; i <= lista.getBroj(); i++) {
		if (lista[i]->getIme() == ime) {
			 lista[i]->promeniLozinku(old,nova);
			 return 1;
		}
	}
	throw gnf();
}

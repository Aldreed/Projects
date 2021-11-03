#include <iostream>
#include "Obavestenje.h"
#include "Objava.h"
#include "Korisnik.h"
#include "ListaObavestenja.h"
int main(){
	int st;
	std::cin >> st;
	Korisnik k("des");
	Objava ko("desdasd", k, false);
	ListaObavestenja lista;
	lista += ko;
	
	lista += Objava("Novi", k, true);
	ListaObavestenja* nova = new ListaObavestenja();
	nova->operator+=(ko);
	nova->operator+=(Objava("Novi2", k, true));
	delete nova;


	lista();
	!lista;
	lista();
	Obavestenje* kos = &lista[ko.GetID()];
	
	Korisnik pos("pos");
	pos.Posalji(ko, k);
	Korisnik* koj = new Korisnik("dadsss");
	pos.Posalji(ko, *koj);
	pos.Posalji(ko, *koj);
	pos.Posalji(ko, *koj);
	pos.Posalji(ko, *koj);
	delete koj;
	std::cout << pos;
	
	int p;
	std::cin >> p;
	
	
}
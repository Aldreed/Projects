#include<iostream>
#include<string>
#include "Karta.h"
#include "Carolija.h"
#include "Zbirka.h"
#include "Igrac.h"
#include "Carobnjak.h"
using std::string;
int main() {
	Carolija car(5, "MAGICNE MUDROLIJE");
	Carolija car2(16, "EKSTRA MAGICNI SENF");
	Carolija car3(20, "BAUKOVE TROLUMBE");
	Carobnjak car4(10, "MAGICNI MOMA", 400);
	Carobnjak car5(10, "SLABASNI SLOBODAN", 0);
	
	Zbirka z;
	z.DodajKartu(car);
	z.DodajKartu(car2);
	z.DodajKartu(car3);
	z.DodajKartu(car4);
	
	//std::cout << z;
	Igrac M(500, 1000, "JUSUF", z);
	M.GetRuka().DodajKartu(*new Carolija(50, "ELEMENTARNE NEPOGODE"));
	M.Aktiviraj(0);
	M.GetRuka().DodajKartu(car4);
	M.Aktiviraj(0);
	Igrac* novi = new Igrac(M);
	novi->GetRuka().DodajKartu(*new Carolija(50, "ELEMENTARNE NEPOGODE"));
	novi->GetRuka().DodajKartu(car5);
	novi->Aktiviraj(0);
	novi->Aktiviraj(0);

	std::cout << novi->GetAktivirane();
	std::cout << M.GetAktivirane();
	M.GetAktivirane()[1].Upotrebi(M, *novi);
	M.GetAktivirane()[1].Upotrebi(M, *novi);
	
	std::cout << std::endl<<novi->GetGrav();
	std::cout << std::endl << novi->GetLen();
	std::cout << std::endl << novi->GetMEn();
	
	delete novi;
	novi = nullptr;
	std::cout << M.GetAktivirane();
	int p;
	std::cin >> p;
}
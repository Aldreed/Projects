#include <iostream>
#include "Lista.h"
#include "Mesto.h"//duzina pa sirina;krece se od 0 i ide do 360
#include "Deonica.h"
#include "Ruta.h"
#include <string>
//Ako treba da se menjaju mesta po deonicama promeni: Return za dohvati u deonicama kao i same vrednosti u Deonici
//Ako treba da semenjaju deonice u ruti treba promeniti: Konstruktor i samu vrednost
using std::ostream;
using std::string;
using std::endl;
using std::cout;
int main() {
	try
	{
		Lista<float> lf;
		lf.dodaj(12.23);
		lf.setPrvi();
		lf.dodaj(13.f);
		lf.dodaj(3.21);
		lf.izbaci();
		lf.sledeci();
		lf.izbaci();

		cout << lf<<endl;
		Mesto m1("Drenor", 58.6523, 65.42342423);
		Mesto m2("Azeroth", 123124.2312, -23123.4);
		cout << (m1 - m2) << endl;
		cout << m2<<endl;

		Deonica d1(m1, m2, Deonica::AUTOPUT);
		cout << ~d1 << endl;
		cout << d1(Deonica::LAKO)<<endl;
		cout << d1 << endl;
		Mesto m3("Kazmodan", 66213.2132, 21321.0231); Mesto m4("Ogrimar", 213123145612.21, -2131.231);
		Mesto m5("Ice Crown", 66231425.5, 647865.5464); Mesto m6("Pandaria", 213.2, -23131212.321);
		Mesto m7("Common", 66213.2132, 21321.0231); Mesto m8("Sense", 9999994533.4234, -1312.2);
		Deonica d2(m3,m4, Deonica::MAGISTRALNI);
		Deonica d3(m5,m6, Deonica::AUTOPUT);
		Deonica d4(m7,m8, Deonica::AUTOPUT);
		Mesto bg("test", 140.52, 38.16);
		Mesto sof("test2", 43.985, 21.232);
		Deonica d5(bg,sof,Deonica::AUTOPUT);
		cout << "ovo  " << d5 << endl;

		Lista<Deonica> ld;
		ld.dodaj(d1);
		ld.dodaj(d2);
		ld.dodaj(d3);
		ld.dodaj(d4);

		Ruta r(ld);
		cout << r(2) << endl << r(Deonica::LAKO) << endl;
		cout << r << endl;

	}
	catch (gtek err)
	{
		cout << err;
	}
	system("pause");
}
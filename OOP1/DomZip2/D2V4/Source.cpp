#include <iostream>
#include "Lista.h"
#include "Mesto.h"
#include "Deonica.h"
#include "Ruta.h"
#include <string>
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

		Deonica d2(Mesto("Kazmodan", 66213.2132, 21321.0231), Mesto("Ogrimar", 213123145612.21, -2131.231), Deonica::MAGISTRALNI);
		Deonica d3(Mesto("Ice Crown", 66231425.5, 647865.5464), Mesto("Pandaria", 213.2, -23131212.321), Deonica::AUTOPUT);
		Deonica d4(Mesto("Common", 66213.2132, 21321.0231), Mesto("Sense", 9999994533.4234, -1312.2), Deonica::AUTOPUT);
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
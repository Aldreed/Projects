#include <iostream>
#include "Lista.h"
#include "Stajaliste.h"
#include "Gradska_linija.h"
#include "Mreza.h"
using std::ostream;
using std::string;
using std::cout;
using std::endl;
int main() {
	try
	{

		Stajaliste s1(5, "Prvo", 4);
		Stajaliste s2(3, "Drugo", 4);
		Stajaliste s3(2, "Trece", 4);
		Stajaliste s4(7, "Cetvrto", 4);
		Lista<Stajaliste> ls;
		ls.dodaj(s1);
		ls.dodaj(s2);
		ls.dodaj(s3);
		ls.dodaj(s4);
		ls.setPrvi();
		ls.sledeci();
		ls.izbaci();
		Gradska_linija gl1("oznaka1", ls);
		Gradska_linija gl2("oznaka2", ls);
		cout << (gl1 & gl2)<<endl;
		cout << gl1;
		cout << ls.dohvati() << endl;
		cout << ls << endl;
		cout << endl;
		cout << endl;
		Mreza m;
		m += gl1;
		m += gl2;
		cout << m << endl;
		ls.sledeci();
		ls.sledeci();
		ls.sledeci();
	}
	catch (gtek err)
	{
		cout << err << endl;
	}
}
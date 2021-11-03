#include <iostream>
#include "Lista.h"
#include "Datum.h"
#include "Vozilo.h"
#include "Automobil.h"

using namespace std;
int main(){
	try {
		
		const int t = 5;
		Lista<  Vozilo*> lv;
		Datum d1(12, 3, 1999);
		Automobil a1("Babi", d1, 250, Automobil::tip::LIMUZINA, 40);
		cout << a1.brojPut() << " " << a1.cenaDan(Datum(), true) << " " << a1;
		Datum dat(2, 3, 2019);
		Datum datdef;
		Lista<float> lf;
		float f1 = 1;
		float f2 = 2.251;
		float f3 = 3551.215;
		lf.dodaj(f1);
		lf.dodaj(f2);
		lf.dodaj(f3);
		//lf.izbaciTek();
		lf.setPrvi();
		lf.sledeci();
		lf.izbaciTek();
		cout << lf.getTek() << " " << lf.getBroj() << endl;
		cout << dat << " " << datdef << " " << endl;
		Automobil* pa1 = &a1;
		lv.dodaj(pa1);
		lv.dodaj(pa1);
		lv.setPrvi();
		cout << lv.getTek()->cenaDan(d1, false);
		lv.izbaciTek();
		lv.posTek();
		lv.sledeci();
		lv.dodaj(&a1);
		lv.izbaciTek();
		lv.izbaciTek();
		lv.dodaj(&a1);
		lv.dodaj(&a1);
		lv.dodaj(&a1);
		lv.setPrvi();
		lv.sledeci();
		lv.izbaciTek();
		lv.izbaciTek();
	}
	catch (gnf err) {
		cout << err << endl;
	}
	catch (gpre err) {
		cout << err << endl;
	}
	catch (gs err) {
		cout << err << endl;
	}


}
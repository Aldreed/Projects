#include "Korisnik.h"
#include "Lista.h"
#include<iostream>
#include <string>
#include"Slika.h"
#include "Evidencija.h"
using namespace std;
int main() {
	try {
		Slika slik("ora", 5, 6, 7);
		cout << slik;
		cout<<endl;
		Korisnik k("dasdasd", "Dasdasd21");
		k.getIme();
		cout << "rad";
		cout << k;
		k.promeniLozinku("Dasdasd21", "dasda12132123");
		cout << k.getLoz();
		Lista<double> l;
		double f = 150.3;
		l +=f;
		cout << l.getBroj() << l[1];
		l(1);
		
		Lista<Korisnik> kl;
		kl += k;
		kl += Korisnik("dasdadasdasda", "sifra1323145");
		
		cout<<kl[2];
		kl(2);
		kl+= Korisnik("dasdadasdasda", "sifra13231456234235346");
		Lista<Korisnik> kl2 = kl;
		cout<<kl2[2].getLoz()<<endl;
		Evidencija evi;
		evi += k;
		Korisnik k2("drugi", "mojaSifra2131");
		Korisnik k3("treci", "mojaSifra2131");
		Korisnik k4("cetvrti", "mojaSifra2131");
		Korisnik k5("peti", "mojaSifra2131");
		Korisnik k6("sesti", "mojaSifra2131");
		evi += k2;
		evi += k3;
		evi += k4;
		evi += k5; evi += k6;

		evi("drugi");
		evi.promeniSifru("treci", "mojaSifra2131", "mojanovasifra134");
		cout<<evi["treci"].getLoz();
		
	}
	catch (glozinc err) {
		cout << err;
		}
	catch (gloz err) {
		cout << err;
	}
	catch (gindex err) {
		cout << err;
	}
	catch (gnf err) {
		cout << err;
	}
	catch (gdup err) {
		cout << err;
	}
	
}
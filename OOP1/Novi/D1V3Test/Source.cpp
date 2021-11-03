#include "Polje.h"
#include "PoljeSaPutem.h"
#include "PoljeSaSumom.h"
#include "Mapa.h"
int main() {
	Polje prvo(150);
	Polje drugo(399);
	Polje trece(0);
	Polje cetvrto(1000);
	PoljeSaPutem peto( PoljeSaPutem::tip::ZEMLJANI,499);
	PoljeSaPutem sesto(PoljeSaPutem::tip::KAMENI);
	PoljeSaSumom sedmo(600, 500);
	PoljeSaSumom osmo(700, 500);
	Mapa m(3, 3);
	m += 5;
	m -= 7;
	m.zameni(0, 0, 300);
	m.zameni(1, 1, PoljeSaPutem::KAMENI);
	Mapa* temp = new Mapa(m);
	temp->zameni(0, 0, 999);
	temp->zameni(1, 1, PoljeSaPutem::ZEMLJANI);
	*temp = Mapa(Mapa(7,7));
	std::cout << m << std::endl;
	std::cout << *temp << std::endl;
	delete temp;
	sedmo--;
	osmo++;
	peto++;
	sesto--;
	trece--;
	cetvrto++;
	prvo--;
	drugo++;

	std::cout << prvo << " " << drugo << " " << trece << " " << cetvrto << " " << peto<<" "<<sesto<<" "<<sedmo<<" "<<osmo;
	int p;
	std::cin >> p;
}
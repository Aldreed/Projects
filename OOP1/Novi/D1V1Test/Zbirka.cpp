#include "Zbirka.h"
#include<time.h>
#include<cstdlib>


Zbirka::Zbirka():BrojKarata(0),first(nullptr),last(nullptr),INDEX(0)
{
}

Zbirka::Zbirka(Zbirka & const k)
{
	
	first = nullptr;
	last = nullptr;
	kopiraj(k);
}

Zbirka::Zbirka(Zbirka && k)
{
	while (first) {
		last = first;
		first = first->sledeci;
		delete last;
	}
	first = nullptr;
	last = nullptr;
	premesti(k);
}
void Zbirka::DodajKartu(ElemZbirke* premestena)
{
	BrojKarata++;
	ElemZbirke* e = premestena;
	last = ((first == nullptr) ? first : (INDEX++, last->sledeci)) = e;
}

void Zbirka::DodajKartu(Karta & novaK)
{
	BrojKarata++;
	ElemZbirke* e = new ElemZbirke(&novaK);
	last = ((first == nullptr) ? first :(INDEX++,last->sledeci) )= e;
}

int Zbirka::GetBrojKarata() const
{
	return BrojKarata;
}

 Karta & Zbirka::operator[](int i)
{
	if (i<0||i>INDEX)
	{
		exit(1);
	}
	else if (i == 0 && first == nullptr) {
		exit(1);
	}
	ElemZbirke* tek = first;
	for (int k = 0; k < i; k++) {
		tek = tek->sledeci;
	}
	return *tek->info  ;
		
}

 const Karta & Zbirka::operator[](int i) const
 {

	 if (i<0 || i>INDEX)
	 {
		 exit(1);
	 }
	 else if (i == 0 && first == nullptr) {
		 exit(1);
	 }
	 ElemZbirke* tek = first;
	 for (int k = 0; k < i; k++) {
		 tek = tek->sledeci;
	 }
	 return *tek->info;
 }

Zbirka::ElemZbirke* Zbirka::operator()(int i)
{
	if (i<0 || i>INDEX||first ==nullptr)
	{
		exit(1);
	}
	ElemZbirke* tek = first,*pret =nullptr;
	for (int k = 0; k < i; k++) {
		pret = tek;
		tek = tek->sledeci;
	}
	if (i == 0) {
		first = tek->sledeci;
	}
	else {
		pret->sledeci = tek->sledeci;
	}
	if (INDEX != 0) {
		INDEX--;
	}
	if (BrojKarata == 1) {
		last = first;
	}
	BrojKarata--;
	return tek;
}

Zbirka::ElemZbirke * Zbirka::operator~()
{
	srand(time(NULL));

	int i = (rand()%INDEX) +1;
	return this->operator()(i);
}

Zbirka & Zbirka::operator=(Zbirka & const k)
{
	if (this != &k) {
		while (first) {
			last = first;
			first = first->sledeci;
			delete last;
		}
		first = nullptr;
		last = nullptr;
		kopiraj(k);
	}
	return *this;
	
}
Zbirka & Zbirka::operator=(Zbirka && k)
{
	if (this != &k) {
		while (first) {
			last = first;
			first = first->sledeci;
			delete last;
		}
		first = nullptr;
		last = nullptr;
		premesti(k);
	}
	return *this;
}
void Zbirka::premesti(Zbirka& k) {
	first = k.first;
	last = k.last;
	k.first = nullptr;
	k.last = nullptr;
	INDEX = k.INDEX;
	BrojKarata = k.BrojKarata;
}
void Zbirka::kopiraj(Zbirka&const k) {
	
	BrojKarata = 0;
	INDEX = 0;
	if (k.first != nullptr)
	{

		for (int i = 0; i <= k.INDEX; i++) {
			DodajKartu(k[i]);
		}
	}
	else
	{
		first = last = nullptr;
	}
}

Zbirka::~Zbirka()
{
	
	
	while (first) {
		last = first;
		first = first->sledeci;
		delete last;
	}
	last = nullptr;
	first = nullptr;
}

Zbirka::ElemZbirke::ElemZbirke(Karta* novi) :info(novi->newInstance()),sledeci( nullptr){
	
}
Zbirka::ElemZbirke::~ElemZbirke()
{
	delete info;
	info = nullptr;
}
std::ostream & operator<<(std::ostream &os, Zbirka & z)
{
	Zbirka::ElemZbirke* tek = z.first;
	while (tek) {
		tek->info->ispis(os) ;
		os << std::endl;
		tek = tek->sledeci;
	}
	return os;
}

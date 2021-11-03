#pragma once
#include <string>
#include <iostream>
using std::ostream;
using std::cout;
using std::string;
class gnf {
public:
	friend ostream& operator<<(ostream& os, gnf& g) {
		os << "TEKUCI ELEMENT NE POSTOJI";
		return os;
	}
};
template <typename T>
class Lista {
	struct elem {
		T info;
		elem* sledeci;
		elem(const T & t, elem* pok = nullptr) : info(t), sledeci(pok) {

		}
	};
	elem* prvi; elem* posl;
	mutable elem* tek; mutable elem* pret;
	int brojelem;
	void kopiraj(const Lista & l);
	void premesti(const Lista& l) {
		prvi = l.prvi;
		posl = l.posl;
		tek = l.tek;
		pret = l.pret;
		l.priv = l.pret = l.posl = l.tek = nullptr;
	}
	void brisi();
public:
	
	Lista() :prvi(nullptr), posl(nullptr), tek(nullptr), pret(nullptr), brojelem(0) {

	}
	Lista(const Lista& l) { kopiraj(l); }
	Lista(Lista&& l) { premesti(l); }
	Lista& operator=(const Lista& l) {
		if (this != &l) {
			brisi(); kopiraj(l);
		}
		return *this;
	}
	Lista& operator=(Lista&& l) {
		if (this != &l) {
			brisi(); premesti(l);
		}
		return *this;
	}
	~Lista()
	{
		brisi();
	}
	void dodaj(const T & t) {
		posl = (!prvi ? prvi : posl->sledeci) = new elem(t); brojelem++;
	}
	
	void setPrvi() {
		tek = prvi;
		pret = nullptr;
	}

	void setPrvi()const {
		tek = prvi;
		pret = nullptr;
	}
	void sledeci() {
		pret = (tek->sledeci ? tek : pret);
		tek = (tek->sledeci ? tek->sledeci : tek);
		
	}
	void sledeci() const {
		tek = (tek->sledeci ? tek->sledeci : tek);
		pret = (tek->sledeci ? pret->sledeci : pret);
	}
	bool posTek() const { return tek != nullptr; }
	int getBroj() const { return brojelem; }
	T& getTek() {
		if (!posTek())throw gnf();
		return tek->info;
	}
	void izbaciTek() {
		if (!posTek())throw gnf();
		elem* stari = tek;
		prvi =( prvi == tek ? tek->sledeci : prvi);
		tek = tek->sledeci;
		if (pret) {
			pret->sledeci = tek;
		}
		stari->sledeci = nullptr;
		brojelem--;
		delete stari;
	}

};

template<typename T>
void Lista<T>::kopiraj(const Lista & l)
{
	prvi = posl = tek = pret = nullptr;
	for (elem* tek = l.prvi; tek; tek = tek->sledeci) {
		dodaj(tek->info);
}

}

template<typename T>
void Lista<T>::brisi()
{
	while (prvi) {
		posl = prvi;
		prvi = prvi->sledeci;
		delete posl;
	}
}

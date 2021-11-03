#pragma once
#include <iostream>
using std::ostream;
using std::string;
using std::cout;
using std::endl;
class gtek {
public:
	friend ostream& operator<<(ostream& os, const gtek & g) {
		os << "GRESKA NE POSTOJI TEK";
		return os;
	}
};
template <typename T>
class Lista {
	struct elem{
		T info;
		elem* sledeci;
		elem(const T & t) :info(t), sledeci(nullptr) {

		}
	};
	elem * prvi;
	elem*posl;
	mutable elem* tek;
	mutable elem* pret;
	int brojelem;
	void kopiraj(const Lista & l);
	void premesti(const Lista&  l) {
		prvi = l.prvi;
		posl = l.pret;
		brojelem = l.brojelem;
		tek = pret = l.prvi = nullptr;
	}
	void brisi();
public:
	Lista():prvi(nullptr),tek(nullptr),posl(nullptr),pret(nullptr),brojelem(0){}
	Lista(const Lista& l);
	Lista(Lista&&);
	Lista& operator=(const Lista& l);
	Lista& operator=(Lista&& l);
	int broj()const { return brojelem; }
	bool posTek() const { return tek; };
	void dodaj(const T & t);
	void setPrvi() const { tek = prvi; pret = nullptr; };
	void sledeci() const;
	void izbaci();
	T& dohvati();
	const T& dohvati()const;
	friend ostream& operator << (ostream& os, const Lista & l) {
		for (l.setPrvi();l.posTek();l.sledeci())
		{
			os << l.dohvati()<<endl;
			
		}
		return os;
	}
	
};

template<typename T>
void Lista<T>::brisi()
{
	for (setPrvi(); tek; sledeci()) {
		delete pret;
	}
	prvi = tek = posl = pret = nullptr;
	brojelem = 0;
}

template<typename T>
 Lista<T>::Lista(const Lista & l)
{
	 kopiraj(l);
}

 template<typename T>
 Lista<T>::Lista(Lista && l)
 {
	 premesti(l);
 }

 template<typename T>
 Lista<T>& Lista<T>::operator=(const Lista & l)
 {
	  if (this != &l) {
		  brisi(); kopiraj(l);
	  }
	  return *this;
 }

 template<typename T>
 Lista<T>& Lista<T>::operator=(Lista && l)
 {
	 if (this != &l) {
		 brisi();
		 premesti(l);
	 }
 }

template<typename T>
void Lista<T>::dodaj(const T & t)
{
	posl = (!prvi ? prvi : posl->sledeci) = new elem(t);
	brojelem++;
}


template<typename T>
void Lista<T>::sledeci() const
{
	if (!posTek())throw gtek();
	pret = tek;
	tek = tek->sledeci;
}

template<typename T>
void Lista<T>::izbaci()
{
	elem* stari = tek;
	tek = tek->sledeci;
	if (stari == prvi) {
		prvi = tek;
	}
	if (pret) {
		pret->sledeci = tek;
	}
	if (stari == posl) {
		posl = pret;
	}
	stari->sledeci = nullptr;
	delete stari;
	brojelem--;
}

template<typename T>
T & Lista<T>::dohvati()
{
	if (!posTek())throw gtek();
	return tek->info;
}

template<typename T>
 const T & Lista<T>::dohvati() const
{
	if (!posTek())throw gtek();
	return tek->info;
}

template<typename T>
 void Lista<T>::kopiraj(const Lista & l)
{
	 prvi = posl = tek = pret = nullptr;
	 for (l.setPrvi(); l.posTek(); l.sledeci()) {
		 dodaj(l.tek->info);
	}
}



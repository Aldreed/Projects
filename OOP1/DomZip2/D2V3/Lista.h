#pragma once
#include <iostream>
using std::string;
using std::ostream;
using std::cout;
using std::endl;
class gtek {
public:
	friend ostream& operator<<(ostream& os, gtek& g) {
		os << "NE POSTOJI TEK" << endl;
		return os;
	}
};
template<typename T>
class Lista {
	struct elem
	{
		T info;
		elem* sledeci;
		elem(const T & t, elem* sl = nullptr) :info(t), sledeci(sl) {}
	};
	elem* prvi;
	elem* posl;
	mutable elem* tek;
	mutable elem* pret;
	int brojelem;
	void kopiraj(const Lista& l);
	void premesti(Lista&& l) {
		prvi = l.prvi;
		posl = l.posl;
		l.prvi = l.posl = nullptr;
		brojelem = l.brojelem;

	};
	void brisi();
public:
	Lista() :prvi(nullptr), posl(nullptr), tek(nullptr), pret(nullptr),brojelem(0) {}
	Lista(const Lista& l) { kopiraj(l); }
	Lista(Lista&& l) {
		premesti(l);
	}
	Lista& operator=(const Lista& l) {
		if (this != &l) {
			brisi();
			kopiraj(l);
		}
		return *this;
	}
	Lista& operator=(Lista&& l) {
		if (this != &l) {
			brisi();
			premesti(l);
		}
		return *this;
	}
	void dodaj(const T& t) {
		posl = (!prvi ? prvi : posl->sledeci) = new elem(t);
		brojelem++;
	}
	void setPrvi() {
		tek = prvi;
		pret = nullptr;
	}
	void setPrvi() const {
		tek = prvi;
		pret = nullptr;
	}
	bool posTek()const {
		return tek;
	}
	void sledeci();
	void sledeci()const;
	void izbaci();
	T& dohvati();
	const T& dohvati()const;
	int getBroj()const { return brojelem; }
	friend ostream& operator<<(ostream& os, const Lista& l) {
		elem* temp = l.prvi;
		while (temp) {
			os << temp->info << endl;
			temp = temp->sledeci;
		}
		return os;
	}
	~Lista()
	{
		brisi();
	}
};
template<typename T>
 void Lista<T>::kopiraj(const Lista & l)
{
	 prvi = posl = tek = pret = nullptr; brojelem = 0;
	 for (elem* temp = l.prvi; temp; temp = temp->sledeci) {
		 dodaj(temp->info);
	 }
}

 template<typename T>
  void Lista<T>::brisi()
 {
	  while (prvi) {
		  pret = prvi;
		  prvi = prvi->sledeci;
		  delete pret;
	  }
	  prvi = posl = tek = pret = nullptr;
	  brojelem = 0;
 }

  template<typename T>
	void Lista<T>::sledeci()
  {
		if (!tek)throw gtek();
		pret = tek;
		tek = tek->sledeci;

  }

	template<typename T>
	 void Lista<T>::sledeci() const
	{
		 if (!tek)throw gtek();
		 pret = tek;
		 tek = tek->sledeci;
	}

template<typename T>
void Lista<T>::izbaci()
{
	if (!posTek())throw gtek();
	elem* stari = tek;
	tek = tek->sledeci;
	if (pret) {
		pret->sledeci = tek;
	}
	if (stari == prvi) {
		prvi = tek;
	}
	if (stari==posl)
	{
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

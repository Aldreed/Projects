#pragma once
#include "gres.h"
template<typename T>
class Lista {
	struct elem {
		elem* sledeci;
		T* info;
		~elem()
		{
			delete info;
			info == nullptr;
			delete sledeci;
			sledeci == nullptr;
		}
		elem(T t):info(new T(t)),sledeci(nullptr) {
		
		}
	};
	elem* first;
	elem* last;
	int brojelem;

public:
	Lista() :first(nullptr), last(nullptr) ,brojelem(0){
	}
	Lista(const Lista&);
	Lista(Lista&&);

	Lista& operator+=(const T& n);
	int getBroj()const {
		return brojelem;
	}
	void kopiraj(const Lista& l);
	void premesti(Lista& l) {
		first = l.first;
		last = l.last;
		brojelem = l.brojelem;
	}
	void brisi() {
		delete first;
		first = last = nullptr;
		brojelem = 0;

	}
	Lista& operator=(const Lista&);
	Lista& operator=(Lista&&);
	//indeksiranje od 1
	T operator[](int);
	const T operator[](int)const;
	void operator()(int);
	~Lista()
	{
		delete first;
		first = last = nullptr;

	}
};
template<typename T>
Lista<T> & Lista<T>::operator+=(const T & n)
{
	elem * novi = new elem(n);
	last = (!first  ? first : last->sledeci) = novi;
	brojelem++;
	return *this;
}

template<typename T>
void Lista<T>::kopiraj(const Lista & l)
{
	elem* tek = l.first;
	for (int i = 0; i < l.brojelem; i++) {
		operator+=( *tek->info);
		tek = tek->sledeci;
	}
}
template<typename T>
inline Lista<T> & Lista<T>::operator=(const Lista & t)
{
	if (this != &t) {
		brisi(); kopiraj(t);
	}
	return *this;
}
template<typename T>
inline Lista<T> & Lista<T>::operator=(Lista && t)
{
	if (this != &t) {
		brisi(); premesti(t);
	}
	return *this;
}
template<typename T>
T Lista<T>::operator[](int i)
{
	if (i<=0 || i>brojelem)throw gindex();
	else
	{
		elem* tek = first;
		elem* pret = first;
		for (int k = 0; k < i; k++)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		return *pret->info;
	}
}
template<typename T>
inline const T Lista<T>::operator[](int i) const
{

	if (i<=0 || i>brojelem)throw gindex();
	else
	{
		elem* tek = first;
		elem* pret = first;
		for (int k = 0; k < i; k++)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		return *pret->info;
	}
}
template<typename T>
void Lista<T>::operator()(int i)
{
	if (i<=0 || i>brojelem)throw gindex();
	else
	{
		elem* tek =first;
		elem* pret = first;
		for (int k = 0; k < i-1; k++)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		pret->sledeci = tek->sledeci;
		brojelem--;
		if (tek == first) {
			first = tek->sledeci;
			pret = first;
		}
		if (tek == last) {
			last = pret;
		}
		tek->sledeci = nullptr;
		delete tek;
	}
}
template<typename T>
 Lista<T>::Lista(const Lista & k)
{
	 kopiraj(k);
}
 template<typename T>
Lista<T>::Lista(Lista && t)
 {
	premesti(t);
 }


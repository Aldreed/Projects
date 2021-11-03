#pragma once
#include"Obavestenje.h"
class ListaObavestenja
{
	struct ElemO {
		Obavestenje* info;
		ElemO* sledeci;
		ElemO(Obavestenje* k);
		~ElemO();
	};
	ElemO* first;
	int neprocitana;
public:
	ListaObavestenja();
	ListaObavestenja(ListaObavestenja& const) = delete;
	ListaObavestenja(ListaObavestenja&&)=delete;
	ListaObavestenja& operator=(ListaObavestenja&&)=delete;
	ListaObavestenja& operator=(ListaObavestenja& const) = delete;
	ListaObavestenja& operator+=(const Obavestenje& );
	Obavestenje& operator[](int);
	const Obavestenje& operator[](int) const;
	friend std::ostream& operator<<(std::ostream&, ListaObavestenja&);
	int operator+();
	void operator!();
	void operator()();
	void operator~();
	~ListaObavestenja();
	

	
};


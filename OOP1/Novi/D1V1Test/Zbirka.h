#pragma once
#include "Karta.h"

class Zbirka
{
	int INDEX;
	int BrojKarata;
	struct ElemZbirke {
		Karta* info;
		ElemZbirke* sledeci;
		ElemZbirke(Karta*);
		~ElemZbirke();
		
	};
	ElemZbirke* first;
	ElemZbirke* last;
	void premesti(Zbirka&);
	void kopiraj(Zbirka& const);

public:
	Zbirka();
	Zbirka(Zbirka&const);
	Zbirka(Zbirka&&);

	void DodajKartu(ElemZbirke * premestena);
	void DodajKartu(Karta& novaK);
	int GetBrojKarata()const;

	Karta& operator[](int);
	const Karta& operator[](int)const;
	ElemZbirke* operator()(int);
	ElemZbirke* operator~();
	Zbirka& operator=(Zbirka&const);
	Zbirka& operator=(Zbirka&&);
	friend std::ostream& operator << (std::ostream&, Zbirka&);
	friend class Igrac;
	
	~Zbirka();

	
};


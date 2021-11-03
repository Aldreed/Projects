#pragma once
#include<iostream>
class Polje
{
	unsigned int neprohodnost;
	friend class Mapa;
	virtual Polje* kopija() const;
public:
	Polje(int nep =100);
	unsigned int GetNep()const;
	Polje& operator++(int);
	Polje& operator--(int);
	friend std::ostream& operator<<(std::ostream& os, Polje& p);
	virtual std::ostream& ispis(std::ostream&)const;
};


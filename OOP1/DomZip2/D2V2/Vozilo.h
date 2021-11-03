#pragma once
#include "Datum.h"
#include <iostream>
#include <string>
using std::ostream;
using std::cout;
using std::string;
using std::endl;
class Vozilo
{
	string naziv;
	Datum d;
	
public:
	Vozilo(string s,Datum dat,int cen):naziv(s),d(dat),cena(cen){}
	Vozilo(const Vozilo&) = delete;
	Vozilo(Vozilo&&) = delete;
	Vozilo& operator=(const Vozilo&) = delete;
	Vozilo& operator=(Vozilo&&) = delete;
	virtual double cenaDan(Datum d1, bool voz)const = 0;
	virtual int brojPut()const = 0;
	virtual ~Vozilo() {};
	friend ostream& operator<<(ostream& os,const Vozilo& v);
protected:
	virtual ostream& ispis(ostream& os) const=0;
	int cena;
	const Datum& getDat()const {
		return d;
	}
};


#pragma once
#include <iostream>
#include <string>
using std::ostream;
using std::string;
using std::cout;
using std::endl;

class Stajaliste
{
	int oznaka;
	string naziv;
	int zona;
public:
	Stajaliste(int oz, string ime, int zon) :oznaka(oz), naziv(ime), zona(zon) {}
	int getzona()const {
		return zona;
	}
	int getozn()const { return oznaka; }
	string getnaz() { return naziv; }
	friend bool operator==(const Stajaliste& s1, const Stajaliste&s2);
	friend ostream& operator<<(ostream& os, const Stajaliste&s);
};


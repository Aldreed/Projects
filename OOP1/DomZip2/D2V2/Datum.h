#pragma once
#include<iostream>

class gs {
public:
	friend std::ostream& operator<<(std::ostream& os, gs& g) {
		os << "POGRESAN STRING";
		return os;
	}
};
class gpre {
public:
	friend std::ostream& operator<<(std::ostream& os, gpre& g) {
		os << "PRVI DATUM JE POSLE DRUGOG";
		return os;
	}
};
static const int md[12] = { 31,28,31,30,31,30,31,31,30,31,30,31 };
class Datum
{
	
	unsigned int d;
	unsigned int m;
	unsigned int g;
	int racunaj() const;
public:
	explicit Datum(int dan = 28, int me = 12, int god = 2019) :d(dan), m(me), g(god) {
		if (dan < 0 || me < 0 || god < 0||me>12||dan>md[m-1]) {
			std::cout << "POGRESNO UNET DATUM";
			exit(1);
	 }
	};
	friend bool operator<(const Datum& d1, const Datum& d2);
	friend int operator-(const Datum& d1, const Datum& d2);
	int operator[](std::string s) const;
	friend std::ostream& operator<<(std::ostream& os,const Datum& d);
};


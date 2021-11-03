#pragma once
#include"Mesto.h"
#include<iostream>
using std::string;
using std::endl;
using std::ostream;
using std::cout;
class Deonica
{
public: enum katput{MAGISTRALNI,AUTOPUT};
		enum katvoz{LAKO,TESKO};
private:
	Mesto poc;
	Mesto kraj;
	katput mojput;
	string mojputString(int i)const;
public:
	Deonica(const Mesto& pc,const  Mesto& kr, katput kat);
	double operator~() const;
	double operator()(katvoz voz)const;
	Mesto getpoc()const;
	Mesto getkraj()const;
	friend ostream& operator<<(ostream& os, const Deonica& d);
};


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
public:
	Deonica(Mesto pc, Mesto kr, katput kat);
	double operator~() const;
	double operator()(katvoz voz);
	friend ostream& operator<<(ostream& os, const Deonica& d);
};


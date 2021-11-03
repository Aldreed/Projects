#pragma once
#include<iostream>
#include<string>
#include<cmath>
using std::ostream;
using std::string;
using std::cout;
using std::endl;

class Mesto
{
	double haversine(double duz1, double duz2, double sir1, double sir2)const;
	string naz;
	double duz;
	double sir;
	double fix(double duz) const;
	
public:
	Mesto(string ime, double duz, double sir);
	string getNaz()const { return naz; }
	double getduz()const { return duz; }
	double getsir()const { return sir; }
	friend double operator-(const Mesto& m1,const Mesto& m2);
	friend ostream& operator<<(ostream& os, const Mesto& m);
};


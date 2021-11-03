#include "Automobil.h"

string Automobil::mojtipString()const
{
	static string tipovi[2] = { "KUPA","LIMUZINA" };
	return tipovi[mojtip];
}

ostream & Automobil::ispis(ostream & os)const
{
	os << "-" << mojtipString() <<endl;
	return os;
}


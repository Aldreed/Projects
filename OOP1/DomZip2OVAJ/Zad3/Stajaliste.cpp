#include "Stajaliste.h"

bool operator==(const Stajaliste & s1, const Stajaliste & s2)
{
	if (s1.oznaka == s2.oznaka) return true;
	return false;
}

ostream & operator<<(ostream & os,const Stajaliste & s)
{
	cout << "[" << s.zona << "]" << "#" << s.oznaka << "-" << s.naziv;
	return os;
}

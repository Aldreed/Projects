#include "Datum.h"


bool operator<(const Datum & d1, const Datum & d2) 
{
	if (d1.racunaj() < d2.racunaj())return true;
	return false;
}

int operator-(const Datum & d1, const Datum & d2) 
{
	if (d1 < d2)throw gpre();
	return d1.racunaj() - d2.racunaj();
}

std::ostream & operator<<(std::ostream & os,const Datum & d)
{
	os << d["dan"] << "." << d["mesec"] << "." << d["godina"] << "."; return os;
}

int Datum::racunaj() const

{
	int n = d + g * 365;
	for (int i = m; i > 0; i--) {
		n += md[i];
	}
	int mod = 0;
	if (m <= 2)mod = -1;
	int d = (g + m) / 4 - (g + m) / 100 + (g + m) / 400;
	return n + d;
}

int Datum::operator[](std::string s) const
{
	if (s == "dan")return d;
	else if (s == "mesec")return m;
	else if (s == "godina")return g;
	else
	{
		throw gs();
	}
}

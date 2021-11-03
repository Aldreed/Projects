#include "Mesto.h"
# define M_PI           3.14159265358979323846 

double Mesto::fix(double duz) const
{
	double temp;
	if (duz < 0) {
		temp = 360 - (fmod(duz, 360));
	}
	else
	{
		temp = fmod(duz, 360);
	}
	return temp;
}

double Mesto::haversine(double duz1, double duz2,double sir1,double sir2)const
{
	double dd = (duz1 - duz2)*M_PI / 180;
	double ss = (sir1 - sir2)*M_PI / 180;
	double rs2 = sir2 * M_PI / 180;
	double rs1 = sir1 * M_PI / 180;
	double temp = pow(sin(ss / 2), 2) + cos(rs1)*cos(rs2)*pow(sin(dd / 2), 2);
	double temp2 = 6378.1 * 2 * asin(sqrt(temp));
	return temp2;
}

Mesto::Mesto(string ime, double d, double s):naz(ime),duz(d),sir(s)
{
	duz=fix(duz);
	sir=fix(sir);
	
}

double operator-(const Mesto & m1, const Mesto & m2)
{
	return m1.haversine(m1.duz, m2.duz, m1.sir, m2.sir);
}

ostream & operator<<(ostream & os,const  Mesto & m)
{
	os << m.getNaz() << "(" << m.duz << "," << m.sir << ")";
	return os;
}

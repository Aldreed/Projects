#include "Vozilo.h"

ostream & operator<<(ostream & os,const Vozilo & v)
{
	os << v.naziv << "-" << v.brojPut() << "-" << v.d;
	v.ispis(os);
	return os;
}

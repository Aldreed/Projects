#include "Multimedia.h"

ostream & operator<<(ostream & os,const Multimedia & m)
{
	os << m.ime << ":";
	m.opis(os);
	return os;
}

#include "Deonica.h"

Deonica::Deonica(Mesto pc, Mesto kr, katput kat):poc(pc),kraj(kr),mojput(kat)
{
}

double Deonica::operator~() const
{
	return poc-kraj;
}

double Deonica::operator()(katvoz voz)
{
	if (mojput == MAGISTRALNI)return 0.0;
	if (voz == LAKO) return operator~() * 11;
	return operator~() * 22;
}

ostream & operator<<(ostream & os, const Deonica & d)
{
	os << (d.mojput ? "AUTOPUT" : "MAGISTRALNI") << "[" << d.poc << "->" << d.kraj << "] -" << ~d << "km";
	return os;
}

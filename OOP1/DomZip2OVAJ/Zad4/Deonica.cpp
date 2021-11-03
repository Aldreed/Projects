#include "Deonica.h"

string Deonica::mojputString(int i) const
{
	static string s[2] = { "MAGISTRALNI","AUTOPUT" };
	return s[i];
}

Deonica::Deonica(const Mesto& pc, const Mesto& kr, katput kat):poc(pc),kraj(kr),mojput(kat)
{
}

double Deonica::operator~() const
{
	return poc-kraj;
}

double Deonica::operator()(katvoz voz)const
{
	if (mojput == MAGISTRALNI)return 0.0;
	if (voz == LAKO) return operator~() * 11;
	return operator~() * 22;
}

Mesto Deonica::getpoc() const
{
	return kraj;
}

Mesto Deonica::getkraj() const
{
	return poc;
}

ostream & operator<<(ostream & os, const Deonica & d)
{
	os << d.mojputString(d.mojput) << " [" << d.poc << " -> " << d.kraj << "] -" << ~d << " km";
	return os;
}

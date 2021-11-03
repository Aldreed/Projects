#include "PoljeSaPutem.h"

Polje * PoljeSaPutem::kopija() const
{
	return new PoljeSaPutem(*this);
}

PoljeSaPutem::PoljeSaPutem( tip t,int nep ):Polje(nep),kat(t)
{
}

std::ostream & PoljeSaPutem::ispis(std::ostream & os) const
{
	os << (kat ? "K" : "Z")<< "_" << this->GetNep();
	return os;
}

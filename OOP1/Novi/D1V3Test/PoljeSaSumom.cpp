#include "PoljeSaSumom.h"

Polje * PoljeSaSumom::kopija() const
{
	return new PoljeSaSumom(*this);
}

PoljeSaSumom::PoljeSaSumom(int gus, int nep):Polje(nep),gustina(gus)
{
	if (gus < 0) {
		exit(1);
	}
}

std::ostream & PoljeSaSumom::ispis(std::ostream & os) const
{
	os << "S" << "_" << this->GetNep()<<"("<<gustina<<")";
	return os;
}

#include "Polje.h"

Polje * Polje::kopija() const
{
	return new Polje(neprohodnost);
}

Polje::Polje(int nep) : neprohodnost(nep)
{
	if (nep < 0 || nep>1000) {
		exit(1);
	}
}

unsigned int Polje::GetNep()const
{
	return neprohodnost;
}

Polje & Polje::operator++(int)
{
	if (neprohodnost != 1000)
	{
		neprohodnost++;
	}
	return *this;
}

Polje & Polje::operator--(int)
{
	if (neprohodnost != 0)
	{
		neprohodnost--;
	}
	return *this;
}

std::ostream & Polje::ispis(std::ostream & os)const
{
	os << "P" << "_" << this->GetNep();
	return os;
}

std::ostream & operator<<(std::ostream & os, Polje & p)
{
	p.ispis(os);
	 return os;
}

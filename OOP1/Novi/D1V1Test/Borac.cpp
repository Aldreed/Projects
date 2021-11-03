#include "Borac.h"

Borac::Borac(int m, string ime, int s): Karta(m, ime), Snaga(s),Upotrebljen(false)
{
	
	kat = BORAC;
}

int Borac::GetSnaga()const
{
	return Snaga;
}

void Borac::Upotrebi(Igrac& pr, Igrac& nep)
{
	if (Upotrebljen == false) {
		Upotrebljen = true;
	}
	else
	{
		Efekat(pr,nep);
	}
}

bool Borac::operator>(Borac & k)const
{
	if (Snaga > k.GetSnaga()) {
		return true;
	}
	else return false;
}

bool Borac::operator<(Borac & k) const
{
	if (Snaga < k.GetSnaga()) {
		return true;
	}
	else return false;
}


std::ostream & Borac::ispis(std::ostream& os)const
{
	return Karta::ispis(os) << " " << "(snaga: " << GetSnaga() << ")";
	
	
}



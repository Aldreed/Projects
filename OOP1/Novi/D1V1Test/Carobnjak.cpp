#include "Carobnjak.h"

Carobnjak::Carobnjak(int i, string k, int h):Borac(i,k,h)
{
}

Karta * Carobnjak::newInstance() const
{
	Carobnjak* p = new Carobnjak(this->GetMag(), this->GetIme(), this->GetSnaga());
	p->Upotrebljen = this->Upotrebljen;
	return p;
}


void Carobnjak::Efekat(Igrac & pr, Igrac &nep)
{
	int k = nep.GetAktivirane().GetBrojKarata();
	int min = -1;
	int minSnaga=-1;
	for (int i = 0; i < k; i++) {
		if (nep.GetAktivirane()[i].GetKat() == BORAC) {
			if (dynamic_cast<Borac&>(nep.GetAktivirane()[i]) < *this ) {
				if (min != -1) {
					if (dynamic_cast<Borac&>(nep.GetAktivirane()[i]) < dynamic_cast<Borac&>(nep.GetAktivirane()[min])) {
						min = i;
					}
				}
				else
					{
						min = i;
					}
				
			}

		}
	}
	
	if (min != -1) {
		nep.GetGrav().DodajKartu(nep.GetAktivirane()(min));
		nep.SetLen( - 2 * GetSnaga());
	}
}

std::ostream & Carobnjak::ispis(std::ostream & os) const
{
	return Borac::ispis(os) << " - CAROBNJAK";
	
	
}



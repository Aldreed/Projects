#include "Mreza.h"

Mreza & Mreza::operator+=(const Gradska_linija & gl)
{
	linije.dodaj(gl);
	return *this;
}

ostream & operator<<(ostream & os,const Mreza & mr)
{
	mr.linije.setPrvi();
	while (mr.linije.posTek())
	{
		mr.linije.dohvati().getLista().setPrvi();
		cout << mr.linije.dohvati().getoznaka()<<" "<<  mr.linije.dohvati().getLista().dohvati()<<" ";
		int temp = 1;
		while (temp< mr.linije.dohvati().getLista().getBroj())
		{
			temp++;
			mr.linije.dohvati().getLista().sledeci();
		}
		cout << mr.linije.dohvati().getLista().dohvati()<<endl;
		mr.linije.sledeci();
	}
	return os;
}

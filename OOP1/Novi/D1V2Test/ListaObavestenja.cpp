#include "ListaObavestenja.h"

ListaObavestenja::ElemO::ElemO(Obavestenje*k):info(k),sledeci(nullptr)
{
}

ListaObavestenja::ElemO::~ElemO()
{
	delete info;
	info = nullptr;
	delete sledeci;
	
	sledeci = nullptr;
}

ListaObavestenja::ListaObavestenja():first(nullptr),neprocitana(0)
{

}


ListaObavestenja & ListaObavestenja::operator+=(const Obavestenje & k)
{
	ElemO* e = new ElemO(k.kopija());
	if (!e->info->GetSeen()) {
		neprocitana++;
	}
	if (first) {
		e->sledeci = first;
		first = e;
		
	}
	else
	{
		first = e;
	}
	return *this;
}

Obavestenje& ListaObavestenja::operator[](int i)
{
	for (ElemO* tek = first; tek; tek = tek->sledeci) {
		if (tek->info->GetID()== i)
		{
			return *tek->info;
		}
	}
}

const Obavestenje & ListaObavestenja::operator[](int i) const
{
	for (ElemO* tek = first; tek; tek = tek->sledeci) {
		if (tek->info->GetID() == i)
		{
			return *tek->info;
		}
	}
}

void ListaObavestenja::operator!()
{
	neprocitana = 0;
	for (ElemO* tek = first; tek; tek = tek->sledeci) {
		if (!tek->info->GetSeen())
		{
			tek->info->See();
		}
	}
}

int ListaObavestenja::operator+()
{
	if (first) {
		return neprocitana;
	}
	else
	{
		exit(1);
	}
}

void ListaObavestenja::operator()()
{
	for (ElemO* tek = first; tek; tek = tek->sledeci) {
		if (!tek->info->GetSeen())
		{
			std::cout << *(tek->info);
		}
	}
}

void ListaObavestenja::operator~()
{
	delete first;
	first = nullptr;
	neprocitana = 0;
}

ListaObavestenja::~ListaObavestenja()
{
	delete first;
	first = nullptr;
}

std::ostream & operator<<(std::ostream & os, ListaObavestenja & ls)
{
	for (ListaObavestenja::ElemO* tek = ls.first; tek; tek = tek->sledeci) {
		tek->info->ispis(os); os << std::endl;
	}
	return os;
}

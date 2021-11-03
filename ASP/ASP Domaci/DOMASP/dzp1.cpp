#include <iostream>
#include<string>
using namespace std;

class Vektor
{
	struct Sused
	{
		int cvor;
		Sused* sledeci;
		Sused(int i):cvor(i),sledeci(nullptr){}
	};
	struct elem {
		int info;
		Sused* sus;
		elem* sledeci;
		elem(int i) : info(i), sledeci(nullptr), sus(nullptr) {

		}
		~elem()
		{
			Sused* tekS = sus;
			Sused* pretS = nullptr;
			while (tekS)
			{
				pretS = tekS;
				tekS = tekS->sledeci;
				delete pretS;
			}
			sus = nullptr;
		}
		
	};
public:

	elem* first;
	Vektor();
	~Vektor();
	elem* GetNode(int i);
	void initGraf(int n);
	void DodajG(int n,int p);
	void DodajCvor(int i);
	void IzbaciCvor(int i);
	void IzbaciGranu(int n, int p);
	friend ostream& operator<<(ostream& os, elem* e);
	friend ostream& operator<<(ostream& os, Vektor& k);


private:

};
Vektor::elem * Vektor::GetNode(int i)
{
	elem* tek = first;
	while (tek && tek->info != i) {
		tek = tek->sledeci;
	}
	return tek;
}
void Vektor::initGraf(int n)
{
	this->~Vektor();
	for (int i = 1; i <= n; i++)
	{
		DodajCvor(i);
	}
}
void Vektor::DodajG(int n, int p)
{
	
	
	if ((GetNode(n) != nullptr && GetNode(p) != nullptr) && GetNode(n)!=GetNode(p)) {
		Sused* novi = new Sused(p);
		Sused* tek = GetNode(n)->sus;
		Sused* tek2 = GetNode(p)->sus;
		Sused* pret = nullptr;
		while (tek && p > tek->cvor)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		if (GetNode(n)->sus != nullptr) {
			if (tek==nullptr||tek->cvor != p) {
				if (pret == nullptr) {
					tek->sledeci = novi;
				}
				else {
					pret->sledeci = novi;
					novi->sledeci = tek;
				}
				
			}
		}
		else
		{
			GetNode(n)->sus = novi;
		}
		

		pret = nullptr;
		novi = new Sused(n);

		while (tek2 && n > tek2->cvor)
		{
			pret = tek2;
			tek2 = tek2->sledeci;
		}
		if (GetNode(p)->sus != nullptr) {
			if (tek2 == nullptr || tek2->cvor != n) {
				if (pret == nullptr) {
					tek2->sledeci = novi;
				}
				else
				{
					pret->sledeci = novi;
					novi->sledeci = tek2;
				}
			}
		}
		else
		{
			GetNode(p)->sus = novi;
		}
		
	}
	else {
		cout << "Neispravno uneti cvorovi grane"<<endl;
	}

}

void Vektor::DodajCvor(int i)
{

	if (GetNode(i) != nullptr) {
		cout << "Cvor koji ste uneli vec postoji"<<endl;
	}
	else
	{
		elem* novi = new elem(i);
		elem* tek = first;
		elem* pret = first;
		while (tek && tek->info<i)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		if (first != nullptr) {
			if (tek == nullptr) {
				pret->sledeci = novi;
				novi->sledeci = tek;
			}
			else if (tek->info == i) {
				delete novi;
			}
			else if (tek == first) {
				novi->sledeci = tek;
				first = novi;
			}
			else {
				pret->sledeci = novi;
				novi->sledeci = tek;
			}
		}
		else
		{
			first = novi;
		}
		
	}
}
void Vektor::IzbaciCvor(int i)
{
	elem* tek = first;
	elem* pret = first;
	while (tek && tek->info != i)
	{
		pret = tek;
		tek = tek->sledeci;
	}
	if (tek != nullptr) {

		
		Sused* nxt = tek->sus;
		while (nxt)
		{
			Sused* tek1 = GetNode(nxt->cvor)->sus;
			
			Sused* pret1 = nullptr;
			while (tek1 && i != tek1->cvor)
			{
				pret1 = tek1;
				tek1 = tek1->sledeci;
			}
			if (tek1 != nullptr) {
				if (pret1 == nullptr) {
					GetNode(nxt->cvor)->sus = tek1->sledeci;
					nxt= nxt->sledeci;
					delete tek1;
					tek1 = nullptr;
				}
				else
				{
					pret1->sledeci = tek1->sledeci;
					nxt=nxt->sledeci;
					delete tek1;
					tek1 = nullptr;
				}
				
			}
			else {
				cout << "Greska" << endl;
			}
			
		}
		if (pret == first) {
			first = tek->sledeci;
		}
		else
		{
			pret->sledeci = tek->sledeci;
		}
		
		delete tek;
		tek = nullptr;
	}
	else
	{
		cout << "Cvor koji se uneli ne postoji"<<endl;
	}
}

void Vektor::IzbaciGranu(int n, int p)
{	
	if ((GetNode(n) != nullptr && GetNode(n)!= nullptr) && GetNode(n) != GetNode(p))  {

		Sused* tek = GetNode(n)->sus;
		Sused* tek2 = GetNode(p)->sus;
		Sused* pret = nullptr;
		while (tek && p != tek->cvor)
		{
			pret = tek;
			tek = tek->sledeci;
		}
		if (tek != nullptr) {
			if (pret == nullptr) {
				GetNode(n)->sus = tek->sledeci;
				delete tek;
			}
			else {
				pret->sledeci = tek->sledeci;
				delete tek;
			}
			
		}
		else {
	
		}

		pret = nullptr;
		

		while (tek2 && n != tek2->cvor)
		{
			pret = tek2;
			tek2 = tek2->sledeci;
		}
		if (tek2!=nullptr) {
			if (pret == nullptr) {
				GetNode(p)->sus = tek2->sledeci;
				delete tek2;
			}
			else
			{
				pret->sledeci = tek2->sledeci;
				delete tek2;
			}
			
		}
		else
		{
			cout << "Grana koju ste uneli ne postoji" << endl;
		}
	}
	else
	{
		cout << "Neispravno uneti cvorovi grane" << endl;
	}
}



Vektor::Vektor():first(nullptr)
{
}

Vektor::~Vektor()
{
	elem* tek = first;
	elem* pret = first;
	while (tek) {
		pret = tek;
		tek = tek->sledeci;
		delete pret;
	}
	first = nullptr;
}


ostream & operator<<(ostream & os, Vektor::elem * e)
{
	Vektor::Sused* tek = e->sus;
	os << "Cvor:" << e->info << " Susedi: ";
	while (tek)
	{
		os << tek->cvor << " ";
		tek = tek->sledeci;
	}
	os << "KRAJ" << endl;
	return os;
}

ostream & operator<<(ostream & os, Vektor & k)
{
	Vektor::elem* tek = k.first;
	os << "Pisanje Vektora: " << endl;
	while (tek)
	{
		os << tek;
		tek = tek->sledeci;
	}
	os << "Kraj Pisanja"<<endl;
	return os;
}
void PozoviFunkciju(Vektor* k, int i,int& prek) {
	int p = -1;
	int temp = -1;
	switch (i)
	{
		
	case 1:
		
		cout << "Unesite broj cvorova novog grafa:" << endl;
		cin >> p;

		if (p <= 0) {
			cout << "Neispravno unet broj cvorova grafa" << endl;
			break;
		}

		k->initGraf(p);
		break;
	case 2:
		cout << "Unesite redni broj cvora koga zelite da ubacite:" << endl;
		cin >> p;

		if (p <= 0) {
			cout << "Neispravno unet broj cvora" << endl;
			break;
		}

		k->DodajCvor(p);
		break;
	case 3:
		cout << "Unesite redni broj cvora koga zelite da izbacite:" << endl;
		cin >> p;
		if (p <= 0) {
			cout << "Neispravno unet broj cvora" << endl;
			break;
		}
		k->IzbaciCvor(p);
		break;
	case 4:
		cout << "Unesite PRVI cvor grane koje zelite da ubacite:" << endl;
		cin >> p;
		cout << "Unesite DRUGI cvor grane koje zelite da ubacite:" << endl;
		cin >> temp;

		if (p <= 0||temp<=0) {
			cout << "Neispravno uneti parametri" << endl;
			break;
		}

		k->DodajG(p,temp);
		break;
	case 5:
		cout << "Unesite PRVI cvor grane koje zelite da izbacite:" << endl;
		cin >> p;
		cout << "Unesite DRUGI cvor grane koje zelite da izbacite:" << endl;
		cin >> temp;

		if (p <= 0 || temp <= 0) {
			cout << "Neispravno uneti parametri" << endl;
			break;
		}

		k->IzbaciGranu(p, temp);
		break;
	case 6:
		cout << *k;
		break;
	case 7:
		k->~Vektor();
		break;
	case 8:
		prek = 1;
	default:
		cout << "Neispravan kod funkcije" << endl;
		break;
	}
}

int main() {
	int prek = 0;
	int temp = -1;
	Vektor* v = nullptr;
	v = new Vektor();
	while (!prek) {
		cout << "Unesite kod instrukcije:" << endl;
		cout << "1. Kreiraj graf" << endl;
		cout << "2. Dodaj Cvor u graf" << endl;
		cout << "3. Ukloni cvor iz grafa" << endl;
		cout << "4. Dodaj granu u graf" << endl;
		cout << "5. Ukloni granu iz grafa" << endl;
		cout << "6. Ispis grafa" << endl;
		cout << "7. Obrisi graf" << endl;
		cout << "8. Prekid programa" << endl;
		cin >> temp;
		try {
			PozoviFunkciju(v, temp, prek);
		}
		catch (...) {
			cout << "Greska u izvrsavanju instrukcije program se gasi";
			delete v;
			exit(1);
		}
		
		
	}
	delete v;

}

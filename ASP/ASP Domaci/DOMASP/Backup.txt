#include <iostream>
#include<string>
using namespace std;

class Vektor
{
public:
	struct Sused
	{
		int cvor;
		Sused* sledeci;
		Sused(int i) :cvor(i), sledeci(nullptr) {}
	};
	struct elem {
		bool ulaz;
		bool izlaz;
		int info;
		Sused* sus;
		elem* sledeci;
		elem(int i) : info(i), sledeci(nullptr), sus(nullptr),ulaz(false),izlaz(false) {

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
	void DodajG(int n, int p);
	void DodajCvor(int i);
	void IzbaciCvor(int i);
	void IzbaciGranu(int n, int p);
	friend ostream& operator<<(ostream& os, elem* e);
	friend ostream& operator<<(ostream& os, Vektor& k);
	int SracunajPolja(int n, int m);
	int SracunajKordinate(int k,int p, int m);
	bool ProveriKoordinate(int a, int b,int n,int m);
	void PrintLav(int n, int m,elem* k);
	int ProveriBroj(elem*,int m);
	void DodajGranuLAV(int n,int m);
	elem* DodajPristupLav(int n,int m,elem* ulaz);
	void Dijkstra(int n, int m, elem* prvi);
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


	if ((GetNode(n) != nullptr && GetNode(p) != nullptr) && GetNode(n) != GetNode(p)) {
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
			if (tek == nullptr || tek->cvor != p) {
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
		cout << "Neispravno uneti cvorovi grane" << endl;
	}

}

void Vektor::DodajCvor(int i)
{

	if (GetNode(i) != nullptr) {
		cout << "Cvor koji ste uneli vec postoji" << endl;
	}
	else
	{
		elem* novi = new elem(i);
		elem* tek = first;
		elem* pret = first;
		while (tek && tek->info < i)
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
					nxt = nxt->sledeci;
					delete tek1;
					tek1 = nullptr;
				}
				else
				{
					pret1->sledeci = tek1->sledeci;
					nxt = nxt->sledeci;
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
		cout << "Cvor koji se uneli ne postoji" << endl;
	}
}

void Vektor::IzbaciGranu(int n, int p)
{
	if ((GetNode(n) != nullptr && GetNode(n) != nullptr) && GetNode(n) != GetNode(p)) {

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
			//prazno
		}

		pret = nullptr;


		while (tek2 && n != tek2->cvor)
		{
			pret = tek2;
			tek2 = tek2->sledeci;
		}
		if (tek2 != nullptr) {
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

int Vektor::SracunajPolja(int n, int m)
{
	return n*m;
}

int Vektor::SracunajKordinate(int k, int p, int m)
{
	int t = k * m + p +1;
	return t;
}

bool Vektor::ProveriKoordinate(int a, int b,int n,int m)
{
	if (a == b + 1 || a == b - 1 || a == (b - m) || a == (b + m) ){
		return true;
	}
	else
	{
		return false;
	}
	
}

void Vektor::PrintLav(int n, int m,elem* k)
{
	if (n*m<=80*50)
	{
		cout << "  ";
		elem* tek = k;
		for (int i = 1; i < m + 1; i++)
		{
			if (tek->ulaz || tek->izlaz) {
				if (tek->ulaz) {
					cout << "U ";
				}
				else if (tek->izlaz)
				{
					cout << "I ";
				}
			}
			else
			{
				cout << "_ ";
			}
			tek = tek->sledeci;
		}
		cout << endl;
		tek = k;
		elem* pret = tek;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m + 1; j++) {
				//ovde stavi if za ulaz i izlaz
				if (j == 0) {
					if (tek->ulaz || tek->izlaz) {
						if (tek->ulaz) {
							cout << "U";
						}
						else if (tek->izlaz)
						{
							cout << "I";
						}
					}
					else
					{
						cout << " ";
					}
				}
				else
				{
					int z = ProveriBroj(tek, m);
					switch (z)
					{
					case 0:
						cout << "!_";
						break;
					case 1:
						cout << "._";
						break;
					case 2:
						cout << "! ";
						break;
					case 3:
						cout << ". ";
						break;
					default:
						cout << "g";
						break;
					}
					pret = tek;
					tek = tek->sledeci;
				}
			}
			if (pret != nullptr && (pret->ulaz || pret->izlaz)) {
				if (pret->ulaz) {
					cout << "U" << endl;
				}
				else if (pret->izlaz)
				{
					cout << "I" << endl;
				}
			}
			else
			{
				cout << "|" << endl;
			}
		}
		cout << "  ";
		tek = GetNode(SracunajKordinate(n - 1, 0, m));
		for (int i = 1; i < m + 1; i++)
		{
			if (tek != nullptr && (tek->ulaz || tek->izlaz)) {
				if (tek->ulaz) {
					cout << "U ";
				}
				else if (tek->izlaz)
				{
					cout << "I ";
				}
			}
			else
			{
				cout << "  ";
			}
			tek = tek->sledeci;
		}
		cout << endl;
	}
	else {
	cout << "Prevelik lavirint za ispis"<<endl;
}
}

int Vektor::ProveriBroj(elem * k,int m)
{
	int store = 0;
	Sused* s = k->sus;
	while (s)
	{
		if ((s->cvor + 1) == k->info) {
			store = store | 1;
		}
		else if ((s->cvor - m) == k->info) {
			store = store | 2;
		}
		s = s->sledeci;
		
	}
	return store;
}

void Vektor::DodajGranuLAV(int n, int m)
{
	int p = -1;
	int d = -1;
	cout << "Unesite Koordinate prve tacke grane" << endl;
	cin >> p;
	cin >> d;
	cout << " Unesite Koordinate druge tacke grane" << endl;
	int t = -1;
	int c = -2;
	cin >> t;
	cin >> c;
	if (p<0 || t<0 || d<0 || c<0 || p>n-1 || t>n-1 || d>m-1 || c>m-1) {
		cout << "Neispravno unete koordinate" << endl;
	}
	else
	{
		 p = SracunajKordinate(p, d, m);
		 d = SracunajKordinate(t, c, m);
		if (ProveriKoordinate(p, d, n, m)) {
			DodajG(p, d);
		}
		else
		{
			cout << "Kordinate nisu susedne nista nije uradjeno" << endl;
		}
	}
}

Vektor::elem* Vektor::DodajPristupLav(int n, int m, elem* ulaz)
{
	bool done = false;
	int p = 0; int d = 0;
	while (!done) {
		cout << "Unesite koordinate ulza" << endl;
		cin >> p;
		cin >> d;
		if (p<0 || d<0 || p>n - 1 || d>m - 1) {
			cout << "Neispravno unete koordinate ulaza unesite ih ponovo" << endl;
		}
		else
		{
			elem* tek = GetNode(SracunajKordinate(p, d, m));
			if (tek != nullptr) {
				if (p == 0 || p == n - 1) {
					tek->ulaz = true;
					ulaz = tek;
					done = true;
				}
				else if (d == 0 || d == n - 1) {
					tek->ulaz = true;
					ulaz = tek;
					done = true;
				}
				else
				{
					cout << "Ne moze se na tim koordinatama staviti ulaz unesite ponovo" << endl;
				}
			}
			else
			{
				cout << "Neispravno unete koordinate ulaza unesite ih ponovo" << endl;
			}
		}
	}
	done = false;
	while (!done) {
		cout << "Unesite koordinate izlaza" << endl;
		cin >> p;
		cin >> d;
		if (p<0 || d<0 || p>n - 1 || d>m - 1) {
			cout << "Neispravno unete koordinate izlaza unesite ih ponovo" << endl;
		}
		else
		{
			elem* tek = GetNode(SracunajKordinate(p, d, m));
			if (tek != nullptr) {
				if (p == 0 || p == n - 1) {
					if (tek->ulaz != true) {
						tek->izlaz = true;
						done = true;
					}
					else
					{
						cout << "Polje ne moze da bude i ulaz i izlaz unesite ponovo" << endl;
					}
				}
				else if (d == 0 || d == n - 1) {
					if (tek->ulaz != true) {
						tek->izlaz = true;
						done = true;
					}
					else
					{
						cout << "Polje ne moze da bude i ulaz i izlaz unesite ponovo" << endl;
					}
				}
				else
				{
					cout << "Ne moze se na tim koordinatama staviti izlaz unesite ponovo" << endl;
				}
			}
			else
			{
				cout << "Neispravno unete koordinate izlaza unesite ih ponovo" << endl;
			}
		}
		if (done) {
			cout << "Da li zelite da unesete jos jedan izlaz?" << endl;
			cout << "1. Da" << endl;
			cout << "2. Ne" << endl;
			cin >> p;
			if (p == 1) {
				done = false;
			}
			else
			{
				done = true;
			}
		}
		else
		{

		}
		

	}
	return ulaz;
}

void Vektor::Dijkstra(int n, int m, elem * prvi)
{
	
	int* d = new int[n*m];
	int* t = new int[n*m];
	for (int i = 0; i < n*m; i++)
	{
		d[i] = -1;
		t[i] = 0;
	}
	Sused* tek = prvi->sus;
	while (tek) {
		d[tek->cvor-1] = 1;
		t[tek->cvor-1] = prvi->info-1;
		tek = tek->sledeci;
	}
	d[prvi->info-1]=-2;
	int memba = -3;

	for (int i = 1; i < n*m; i++) {
		int min = -1;
		int minIndex = -2;
		for (int k = 0; k < n*m; k++) {
			if (d[k] != -2 && minIndex == -2) {
				minIndex = k;
				min = d[k];
			}
			else if (d[k]<min&&d[k]!=-2&&min!=-1&&d[k]!=-1)
			{
				minIndex = k;
				min = d[k];
			}
			else if (min == -1) {
				if (d[k]>0)
				{
					minIndex = k;
					min = d[k];
				}
			}
		}
		d[minIndex] = -2;
		Sused* mojSus = GetNode(minIndex+1)->sus;
		bool susedi = false;
		for (int k = 0; k < n*m; k++)
		{
			if (d[k] > -2) {
				Sused* temp = mojSus;
				susedi = false;
				while (temp) {
					if (temp->cvor-1 == k) {
						susedi = true;
					}
					temp = temp->sledeci;
				}
				if (susedi) {
					if (min + 1 < d[k]||(d[k]==-1&&min!=-1)) {
						d[k] = min + 1;
						t[k] = minIndex;
						if (GetNode(k+1)->izlaz == true) {
							memba = k;
							i = n * m;
						}
					}
				}
			}
			else if ((k + 1) == prvi->info) {
				Sused* temp = mojSus;
				susedi = false;
				while (temp) {
					if (temp->cvor - 1 == k) {
						susedi = true;
					}
					temp = temp->sledeci;
				}
				if (susedi) {
					if (GetNode(minIndex+1)->izlaz == true) {
						memba = minIndex;
						i = n * m;
					}
				}
			}
		}
	}
	int i = 0;
	while (((memba)!=prvi->info-1)&&(memba!=-3))
	{
		
		d[i] = memba;
		memba = t[memba];
		i++;

 	}
	if (memba==-3)
	{
		cout << "nema puta" << endl;
	}
	else if (memba == prvi->info-1) {
		
		cout <<"("<< ((memba) / m) << "," << (memba) % m << ")"<<"-->";
		i--;
		while (i != -1) {
			cout << "(" << ((d[i]) / m) << "," << (d[i]) % m << ")" << "-->";
			i--;
		}
		cout << "kraj ispisa"<<endl;
	}
	delete[] d;
	delete[] t;
}

Vektor::Vektor() :first(nullptr)
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
	os << "Kraj Pisanja" << endl;
	return os;
}
void PozoviFunkciju(Vektor* k, int i, int& prek,bool &kraj,Vektor::elem* ulaz,int n,int m) {
	int p = -1;
	int temp = -1;
	switch (i)
	{
	case 1 :
		k->DodajGranuLAV(n,m);
		break;
	case 2:
		k->PrintLav(n, m, k->first);
		break;
	case 3:
		k->Dijkstra(n, m, ulaz);
		break;
	case 4:
		kraj = true;
		break;
	case 5:
		kraj = true;
		prek = 1;
		break;
	default:
		cout << "Neispravan kod operacije" << endl;
		break;
	
	}
}

int main() {
	int prek = 0;
	int temp = -1;
	int m = 0;
	int n = -1;
	Vektor::elem* ulaz = nullptr;
	Vektor* v = nullptr;
	v = new Vektor();
	while (!prek) {
		cout << "Unesite dimezije matrice:" << endl;
		cin >> n;
		cin >> m;
		if (n <= 0 || m <= 0) {
			cout << "Neispravno unete kordinate" << endl;
		}
		else if (n == 1 && m == 1) {
			cout << "Trivijalan slucaj kordinate opet" << endl;
		}
		else
		{	
			try {
				v->initGraf(v->SracunajPolja(n, m));
				ulaz = v->DodajPristupLav(n, m, ulaz);
			}
			catch (...) {
				cout << "Greska u inicijalizaciji program se gasi";
				delete v;
				exit(1);
			}
			
			bool kraj = false;
			while (!kraj)
			{
				cout << "Unesite kod operacije" << endl;
				cout << "1.Dodavanje grana" << endl;
				cout << "2.Ispis lavirinta" << endl;
				cout << "3.Resavanje lavirinta" << endl;
				cout << "4.Brisanje trenutnog i stvaranje novog lavirinta" << endl;
				cout << "5.Prekid programa" << endl;
				cin >> temp;
				try {
					PozoviFunkciju(v, temp, prek, kraj, ulaz, n, m);
				}
				catch (...) {
					cout << "Greska u izvrsavanju instrukcije program se gasi";
					kraj == true;
					prek = 1;
				}
				
			}
			delete v;
			v = new Vektor();
		}
		
	}
	
}

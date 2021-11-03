#include "Mapa.h"

Polje *** Mapa::init(const int n,const int m)
{
	
	if (n < 0 || m < 0) {
		exit(1);
	}
	Polje*** pat = new Polje**[n];
	for (int i = 0; i < n; i++)
	{
		pat[i] = new Polje*[m];
		for (int j = 0; j < m; j++)
		{
			pat[i][j] = new Polje();
		}
	}
	return pat;
}

void Mapa::kopiraj(Mapa & const k)
{
	red = k.red;
	kolone = k.kolone;
	mat = init(k.red, k.kolone);
	for (int i = 0; i < k.red; i++)
	{
		for (int j = 0; j < k.kolone; j++)
		{
			delete mat[i][j];
			mat[i][j] = k.mat[i][j]->kopija();
		}
	}
}

void Mapa::brisi()
{
	if (mat != nullptr)
	{
		for (int i = 0; i < red; i++)
		{
			for (int j = 0; j < kolone; j++)
			{
				delete mat[i][j];
				mat[i][j] = nullptr;
			}
			delete[]mat[i];
			mat[i] = nullptr;
		}
		delete[]mat;
		mat = nullptr;
	}
}

void Mapa::premesti(Mapa & const k)
{
	red = k.red;
	kolone = k.kolone;
	mat = k.mat;
	k.mat = nullptr;
}

Mapa::Mapa(int n, int m):red(n),kolone(m),mat(init(n,m))
{
}

Mapa::Mapa(Mapa & const k)
{
	kopiraj(k);
}

Mapa::Mapa(Mapa &&k)
{
	premesti(k);
}

void Mapa::zameni(int r,int k, PoljeSaPutem::tip put)
{
	PoljeSaPutem* p = new PoljeSaPutem(put, mat[r][k]->GetNep());
	delete mat[r][k];
	mat[r][k] = p;
}

void Mapa::zameni(int r, int k, int gus)
{
	PoljeSaSumom* p = new PoljeSaSumom(gus, mat[r][k]->GetNep());
	delete mat[r][k];
	mat[r][k] = p;
}

Mapa & Mapa::operator+=(int k)
{
	
	for (int i = 0; i < red; i++)
	{
		for (int j = 0; j < kolone; j++)
		{
			for (int z = 0; z < k; z++)
			{
				(*mat[i][j])++;
			}
		}
	}
	return *this;
}

Mapa & Mapa::operator-=(int k)
{
	for (int i = 0; i < red; i++)
	{
		for (int j = 0; j < kolone; j++)
		{
			for (int z = 0; z < k; z++)
			{
				(*mat[i][j])--;
			}
		}
	}
	return *this;
}

Mapa & Mapa::operator=(Mapa & const k)
{
	if (this != &k) {
		brisi();
		kopiraj(k);
	}
	return *this;
}

Mapa & Mapa::operator=(Mapa && k)
{
	if (this != &k) {
		premesti(k);
	}
	return *this;
}

Mapa::~Mapa()
{
	brisi();
}

std::ostream & operator<<(std::ostream & os, Mapa & m)
{
	for (int i = 0; i < m.red; i++)
	{
		if (i != 0) {
			os << std::endl;
		}
		for (int j = 0; j < m.kolone; j++)
		{
			if (j != 0) {
				os << "\t";
			}
			m.mat[i][j]->ispis(os);
		}
	}
	return os;
}

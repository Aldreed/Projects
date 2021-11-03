#pragma once
#include "Polje.h"
#include "PoljeSaPutem.h"
#include "PoljeSaSumom.h"
class Mapa
{
	int red;
	int kolone;
	Polje*** mat;
	Polje*** init(int n, int m);
	void kopiraj(Mapa&const k);
	void brisi();
	void premesti(Mapa&const k);
	
public:
	
	Mapa(int n, int m);
	Mapa(Mapa&const);
	Mapa(Mapa&&);
	void zameni(int r,int k,PoljeSaPutem::tip put);
	void zameni(int r, int k,int gus);
	Mapa& operator+=(int k);
	Mapa& operator-=(int k);
	Mapa& operator=(Mapa&const k);
	Mapa& operator=(Mapa&&);
	friend std::ostream& operator<<(std::ostream& os, Mapa& m);
	~Mapa();

};


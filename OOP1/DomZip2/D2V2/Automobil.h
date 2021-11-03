#pragma once
#include "Vozilo.h"
class Automobil :
	public Vozilo
{
public:enum tip{KUPA,LIMUZINA};
private:
	tip mojtip;
	double dnevnica;
	string mojtipString()const;
public:
	Automobil(string s, Datum dat, int cen, tip sta, int dnev):Vozilo(s,dat,cen),mojtip(sta),dnevnica(dnev){}
	int brojPut()const override {//broj putnika + vozac
		if (mojtip==KUPA)
		{
			return 3;
		}
		else
		{
			return 6;
		}
	}
	double cenaDan(Datum d1,bool voz)const override{
		double cd = cena;
		d1 - getDat();
		if (Datum(d1["dan"], d1["mesec"],d1["godina"]-2)<getDat()) {
			cd = 1.15f*cd;
		}
		if (voz)
		{
			cd += dnevnica;
		}
		return cd;
	}
	~Automobil()
	{

	}
protected:
	ostream& ispis(ostream& os)const override;

	
};


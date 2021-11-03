#pragma once
#include<time.h>
#include<iostream>
class Korisnik;
class Obavestenje
{ 
	friend class ListaObavestenja;
	static int idCount;
protected:
	int id;
	int timeO;
	bool seen;
	Korisnik* mojKorisnik;
	virtual Obavestenje* kopija()const = 0;
public:
	Obavestenje(Korisnik&,bool);
	bool GetSeen() const;
	int GetID()const;
	Korisnik& GetKorisnik() const;
	void See();
	friend std::ostream & operator<<(std::ostream&, Obavestenje&);
	
	virtual std::ostream& ispis( std::ostream&) const ;
	virtual ~Obavestenje() {};

};


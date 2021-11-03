#pragma once
#include <string>
using std::string;
using std::ostream;
class Multimedia
{
	string ime;
public:
	Multimedia(string im) :ime(im) {

	}
	virtual ~Multimedia()
	{

	}
	virtual unsigned int velicina() const = 0;
	virtual ostream& opis(ostream&)const = 0;
	friend ostream& operator<<(ostream& os,const Multimedia& m);
	
};


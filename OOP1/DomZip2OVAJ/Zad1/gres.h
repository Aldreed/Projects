#pragma once
#include <iostream>
class gloz {
public:
		friend std::ostream& operator<<(std::ostream& os, gloz& g) {
			os << "POGRESAN FORMAT LOZINKE";
			return os;
	}
};
class glozinc {
public:
	friend std::ostream& operator<<(std::ostream& os, glozinc& g) {
		os << "POGRESNO UNETA LOZINKA";
		return os;
	}
};
class gindex {
public:
	friend std::ostream& operator<<(std::ostream& os, gindex& g) {
		os << "POGRESNO UNET INDEX";
		return os;
	}
};
class gnf{
public:
	friend std::ostream& operator<<(std::ostream& os, gnf& g) {
		os << "UNETI KORISNIK NE POSTOJI";
		return os;
	}
};
class gdup {
public:
	friend std::ostream& operator<<(std::ostream& os, gdup& g) {
		os << "UNETI KORISNIK VEC POSTOJI";
		return os;
	}
};


#include "GEv.h"
#include "IVTEntry.h"
#include <STDIO.H>

IVTEntry* GEv::niz[256];

void GEv::add(IVTEntry* i) {
	GEv::niz[i->myNUM]=i;

}
IVTEntry* GEv::find(int i) {
		return GEv::niz[i];
}

/*
 * GEv.cpp
 *
 *  Created on: Apr 30, 2020
 *      Author: OS1
 */





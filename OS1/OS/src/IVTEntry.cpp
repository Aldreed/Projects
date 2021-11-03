/*
 * IVTEntry.cpp
 *
 *  Created on: Apr 25, 2020
 *      Author: OS1
 */
#include "IVTEntry.h"
#include <STDIO.H>
#include <dos.h>
#include <STDIO.H>
#include "KEvn.h"
#include "event.h"
#include <iostream.h>
#include "GEv.h"

pInterrupt tempOld=NULL;

IVTEntry* IVTEntry::niz[256];

IVTEntry::IVTEntry(int i, int j,pInterrupt p):myNUM(i),dold(j),myEv(NULL) {

	asm pushf;
	asm cli;
	 pInterrupt newInter = p;
	 old = getvect(i);
	 setvect(i,newInter);
	 GEv::add(this);
	 asm popf;

}

void IVTEntry::signal(int i) {

	IVTEntry* temp=GEv::find(i);

		if(temp->myEv!=NULL){
			temp->myEv->signal();
			}
		if(temp->dold==1){
			tempOld=temp->old;
			setvect(61,tempOld);
			asm int 61
		}

}

IVTEntry::~IVTEntry() {
	tempOld = old;
	setvect(myNUM,tempOld);
}

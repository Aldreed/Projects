/*
 * event.cpp
 *
 *  Created on: Apr 26, 2020
 *      Author: OS1
 */

#include "event.h"
#include "Thread.h"
#include "KEvn.h"
#include "IVTEntry.h"
#include "PCB.h"
#include <STDIO.H>
#include "SCHEDULE.H"
#include "GEv.h"
extern PCB* running;
extern int csd;
extern void interrupt timer();


#include <IOSTREAM.H>


Event::Event(IVTNo ivtNo) {
asm pushf;
asm cli;
	IVTEntry* temp = GEv::find(ivtNo);
	myImpl= new KernelEv(this);
	temp->myEv=myImpl;
	myImpl->myIVT=temp;
asm popf;
}

Event::~Event() {
	asm pushf;
	asm cli;
	delete myImpl;
	myImpl=NULL;
	asm popf;
}

void Event::wait() {
	asm pushf;
	asm cli;
	if(running->myThread==myImpl->myThread){

		if(--(myImpl->val)<0){

		this->myImpl->myPCB=running;
		csd=2;
		timer();

		}
	}
	asm popf;
}

void Event::signal() {
	if(myImpl->val<1){
		myImpl->val++;
		if(myImpl->myPCB!=NULL&&myImpl->val==0){
			Scheduler::put(myImpl->myPCB);
		}


	}
}

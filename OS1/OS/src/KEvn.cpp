#pragma once
#include "event.h"
#include "KEvn.h"
#include "PCB.h"
#include  <STDIO.H>
#include "IVTEntry.h"
extern PCB* running;



KernelEv::KernelEv( Event* const t):myIVT(NULL),val(1),myPCB(NULL),myEv(t),myThread(running->myThread){
}

void KernelEv::signal(){//globalna prakticno//ne stavljaj na stek nista
	myEv->signal();
}


KernelEv::~KernelEv() {
	myThread =NULL;
	myEv= NULL;
	myPCB = NULL;
	myIVT->myEv=NULL;
}
/*
 * KEvn.cpp
 *
 *  Created on: Apr 26, 2020
 *      Author: OS1
 */





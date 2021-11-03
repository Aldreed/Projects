#pragma once
#include "PCB.h"
#include <DOS.H>
#include <iostream.h>
#include <STDIO.H>

#include "RSig.h"
#include "SigCon.h"

extern	volatile PCB* running;
extern unsigned int IDS;
static Thread* parent=NULL;
extern volatile unsigned int Gtime;
class Consumer;
void PCB::wraper() {

running->myThread->run();
asm pushf;
asm cli;
parent =Thread::getThreadById(running->myParent);

if(parent!=NULL&&parent!=running->myThread){
	parent->myPCB->started=2;//oznaka da se poziva od sistema
	parent->signal(1);
	parent->myPCB->started=1;
}

running->started=2;
running->myThread->signal(2);
running->started=1;
running->finished=1;//osigurac da se nit ne stavi opet u scheduler
asm popf;
dispatch();
}

PCB::~PCB() {
	delete mySignalContainter;
	mySignalContainter=NULL;
	delete myQ;
	myQ=NULL;
	delete [] myBlockedSig;
	myBlockedSig=NULL;
	myParent=0;
	myThread=NULL;
	delete[] stack;
	stack=NULL;
	//cout<<"obrisan-"<<myID<<endl;
}

PCB::PCB():myID(-1),stackS(0),myBlockedSig(0),stack(NULL),sp(0),ss(0),timeslice(1),finished(0),waitRet(-3),mySignalContainter(new SigCon()),started(1),myThread(0),myParent(0),myQ(0){
	 myBlockedSig=new unsigned int[16];
		 int i;
		 for(i =0;i<16;i++){
			 myBlockedSig[i]=0;
		 }
};

PCB::PCB(unsigned long sts, unsigned int ts):waitRet(0),myThread(NULL),stackS(sts),timeslice(ts),myID(IDS++),mySignalContainter(new SigCon()),started(0),myQ(NULL) {
	asm pushf;
	asm cli;

	 unsigned* st = new unsigned[stackS];

#ifndef BCC_BLOCK_IGNORE
	 st[stackS-1]=0x200;
	 st[stackS-2] = FP_SEG(&PCB::wraper);
	 st[stackS-3] = FP_OFF(&PCB::wraper);
	 //st[1023-1016] PSW,PC,BP,DS,AX,BX,CX,DX,SI,DI
	 sp = FP_OFF(st+stackS-12);
	 ss = FP_SEG(st+stackS-12);


#endif

	 myBlockedSig=new unsigned int[16];
	 int i;
	 for(i =0;i<16;i++){
		 myBlockedSig[i]=running->myBlockedSig[i];
	 }
	 stack = st;
	 //mySignalContainter->signals=new SigCon::SigElem[15];
	 running->mySignalContainter->clone(mySignalContainter->signals);
	 myParent=running->myID;
	 //implementacija sa redom
	 myQ = new RSig();
	 asm popf;
}


#ifndef _PCB_H_
#define _PCB_H_


#include "Thread.h"

typedef unsigned int Time;
class RSig;
class SigCon;
class Semaphore;
class PCB{

	friend void obradi();
	friend class Event;
	friend class KernelEv;
	friend class KernelSem;
	friend class Semaphore;
	friend void interrupt timer();
	friend int main(int argc,char* argv[]);
	friend class UM;
	friend class Thread;
	friend void dispatch();
	friend void nasilanPrekid();

	int myParent;
	Thread* myThread;
	unsigned int ss;

	unsigned int sp ;
	unsigned int timeslice;
	 int myID;
	unsigned long stackS;
	unsigned* stack;
	volatile int waitRet;
	volatile unsigned int finished;
	int started;//nula pre starta posle moze da bude neki broj veci od nule//1 krenuo sam //2 sistemski se zovu signali //3 nasilan prekid
	static void wraper();

	PCB(unsigned long sts,unsigned int ts);
	PCB();
	unsigned int* myBlockedSig;
	SigCon* mySignalContainter;

	//implementacija sa redom
	RSig* myQ;
	~PCB();


};
#endif




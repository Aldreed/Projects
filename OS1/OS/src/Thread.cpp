
#include "Thread.h"
#include <STDIO.H>
#include <DOS.h>
#include <iostream.h>
#include "SCHEDULE.H"
#include "PCB.h"
#include "GThread.h"
#include "SigCon.h"
#include "RSig.h"
extern volatile PCB* running;
extern volatile unsigned int* GSignalBlock;//iskorisceno u global block i unblock

extern unsigned int IDS;
extern void interrupt timer();
extern int csd ;
ID Thread::getId() {
	return myPCB->myID;
}

 ID Thread::getRunningId() {
	 return running->myID;
}

void Thread::waitToComplete() {
	while(myPCB!=NULL&&myPCB->finished!=1){
  //nisam gotov cekam finished...
		dispatch();
	}
}

Thread::~Thread() {
	asm pushf;
	asm cli;

	//waitToComplete();

	GThread::deleteID(this->getId());

	delete myPCB;
	myPCB=NULL;
	asm popf;
}

Thread::Thread(StackSize stackSize, Time timeSlice) {
	myPCB = new PCB(stackSize,timeSlice);
	myPCB->myThread=this;
	if(myPCB->mySignalContainter->signals[0].fun==NULL)myPCB->myThread->registerHandler(0,&nasilanPrekid);
	GThread::add(this);
}
void Thread::start(){
	asm pushf;
	asm cli;
	if(myPCB->started==0){
		myPCB->started=1;
		myPCB->finished=0;//da se ne dogodi bug ako se nit stvori i ne pokrene da ne moze da se obrise sa delete
			Scheduler::put(myPCB);
	}
	asm popf;
}
void dispatch (){
	asm pushf;
	asm cli;
	if(csd!=3)csd=1;//zastita da se ne obrise indikator za nasilan prekid
	timer();
	asm popf;
};

//uvek uredjeno u rastucem redosledu pa je pretraga optimizovana za to
Thread* Thread::getThreadById(ID id) {
	return GThread::findID(id);
}

//SIGNALI
void Thread::registerHandler(SignalId signal, SignalHandler handler) {
	asm pushf;
	asm cli;
	myPCB->mySignalContainter->add(handler,signal);
	asm popf;
}

void Thread::unregisterAllHandlers(SignalId id) {
	asm pushf;
	asm cli;
	myPCB->mySignalContainter->delAll(id);
	if(id == 0){
		myPCB->mySignalContainter->add(&nasilanPrekid,id);
	}
	asm popf;
}

void Thread::swap(SignalId id, SignalHandler hand1, SignalHandler hand2) {
	myPCB->mySignalContainter->swap(id,hand1,hand2);
}

void Thread::blockSignal(SignalId signal) {
	myPCB->myBlockedSig[signal]=1;
}

void Thread::blockSignalGlobally(SignalId signal) {
	asm pushf;
	asm cli
	GSignalBlock[signal]=1;
	asm popf;
}

void Thread::unblockSignal(SignalId signal) {
	myPCB->myBlockedSig[signal]=0;
}

void Thread::unblockSignalGlobally(SignalId signal) {
	asm pushf;
	asm cli;
	GSignalBlock[signal]=0;
	asm popf;
}


void nasilanPrekid(){
	running->finished=1;
}

void Thread::signal(SignalId signal) {
	if(this->myPCB->started==2||(signal!=1&&signal!=2)){
		this->myPCB->myQ->addQ(signal);
	}


}

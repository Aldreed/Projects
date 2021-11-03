#pragma once
#include <STDIO.H>
#include <iostream.h>
#include "SCHEDULE.H"
#include "PCB.h"
#include  "GSem.h";
#include "KSem.h"
#include "semaphor.h"

extern PCB* running;
extern int csd;
extern void interrupt timer();
Semaphore::Semaphore(int init) :
		myImpl(new KernelSem(init)) {
 GSem::head->add(myImpl);
}

int Semaphore::wait(Time maxTimeToWait) {
	asm pushf;
	asm cli;
	if(--(myImpl->val)<0){
	myImpl->add(running,maxTimeToWait);


	csd =2;
	timer();
	asm popf;
	return running->waitRet;}
	else{
		asm popf;
		return 1;
	}
}

int Semaphore::signal(int n) {
	asm pushf;
	asm cli;
	int temp = 0;
	PCB* t=NULL;;
	if(n==0){
		if(++myImpl->val<=0){
			t = myImpl->del();
			if(t!=NULL){
				Scheduler::put(t);
			}
			asm popf;
			return temp;
		}
		asm popf;
		return temp;
	}
	else if(n>0){
		myImpl->val+=n;
		for (int i = 0;  i < n; ++i) {
			t =myImpl->del();
			if(t == NULL){
			break;
		}
		else{
			Scheduler::put(myImpl->del());
			temp++;
		}
	}
	asm popf;
	return temp;
	}
	else if(n<0){
		asm popf;
		return n;
	}
}

Semaphore::~Semaphore() {
	asm pushf
	asm cli;
	GSem::head->deleteSem(myImpl);
	delete myImpl;
	myImpl=NULL;
	asm popf;
}

int Semaphore::val() const {
	return myImpl->val;
}

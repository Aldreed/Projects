#pragma once
#include "SigCon.h"
#include <STDIO.H>
#include <IOSTREAM.H>

SigCon::SigCon() {
	asm pushf;
	asm cli;
	signals = new SigElem[16];
	asm popf;
}

SigCon::~SigCon() {
	int i;
	for(i=0;i<16;i++){
		SigElem* temp=&signals[i];
		SigElem* temp2=NULL;

		while(temp!=NULL){
			temp2=temp;
			temp=temp->next;
			temp2->fun=NULL;
			temp2->next=NULL;
			if(temp2!=&signals[i]){
				delete temp2;
			}

		}
	}
	delete [] signals;
}

SigCon::SigElem::SigElem(SignalHandler s):fun(s),next(NULL) {
}

void SigCon::add(SignalHandler s,SignalId si) {
	if(signals[si].fun!=NULL){
		SigElem* temp = new SigElem(s);
		SigElem* temp2 = &signals[si];
		while(temp2->next!=NULL){
			temp2=temp2->next;
		}
		temp2->next=temp;
		if(si==0){
			temp->fun=temp2->fun;
			temp2->fun=s;
		}
	}
	else{
		signals[si].fun =s;
	}
}

SigCon::SigElem::SigElem():fun(NULL),next(NULL) {
}

void SigCon::delAll(SignalId si) {
	asm pushf;
	asm cli;
			SigElem* temp=&signals[si];
			SigElem* temp2=NULL;

			while(temp!=NULL){
				temp2=temp;
				temp=temp->next;
				temp2->fun=NULL;
				temp2->next=NULL;
				if(temp2!=&signals[si]){
					delete temp2;
				}

			}
asm popf;
}

void SigCon::swap(SignalId i, SignalHandler s1, SignalHandler s2) {
	asm pushf
	asm cli;
	SigElem* temp1 = NULL;
	SigElem* temp2=NULL;
	SigElem* temp3 = &signals[i];
	while(temp3!=NULL&&(temp1==NULL || temp2==NULL)){
		if(temp3->fun==s1){
			temp1 =temp3;
		}
		else if(temp3->fun==s2){
			temp2=temp3;
		}
		temp3=temp3->next;
	}
	if(temp2==NULL||temp1==NULL){

	}
	else{
		SignalHandler tempSH = temp1->fun;
		temp1->fun=temp2->fun;
		temp2->fun=tempSH;
	}
	asm popf;
}

void SigCon::clone(SigElem* niz) {
	SigElem* temp1=NULL;
	SigElem* temp2=NULL;
	int i;
	for(i = 0;i<16;i++){
		temp1=&niz[i];
		temp2=&signals[i];
		while(temp2!=NULL){
			temp1->fun=temp2->fun;
			if(temp2->next!=NULL){
				temp1->next=new SigElem();
			}
			temp1=temp1->next;
			temp2=temp2->next;
		}
	}
}

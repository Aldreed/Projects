#pragma once
#include "KSem.h"
#include <STDIO.H>
#include <iostream.h>
#include "SCHEDULE.H"
#include "PCB.h"

KernelSem::KernelSem(int i):head(NULL),tail(NULL),val(i){
}
KernelSem::Elem::Elem(PCB* i, int c) :
		info(i), count(c), next(NULL) {
}

void KernelSem::add(PCB* i, int clk) {
	Elem* temp = new Elem(i, clk);
	if(head==NULL){
		head=temp;
		tail=temp;
	}
	else{
		tail->next=temp;
		tail = temp;
	}
}

void KernelSem::timePass() {
	Elem* temp1 = head;
	Elem* temp2 = NULL;
	while(temp1!=NULL){
		if (temp1->count!=0){
			--(temp1->count);

			if((temp1->count)==0){
				temp1->info->waitRet=0;

				if(temp2==NULL){
					temp2=temp1;
					head=temp1->next;
					temp1=temp1->next;
					Scheduler::put(temp2->info);
					temp2->info=NULL;
					temp2->next=NULL;
					delete temp2;
					temp2=NULL;
				}
				else{
					temp2->next=temp1->next;

					Scheduler::put(temp1->info);
					temp1->info=NULL;
					delete temp1;
					temp1 = temp2->next;
				}
				this->val--;
			}
			else{

				temp2=temp1;
				temp1= temp1->next;
			}
		}
		else{
			temp2=temp1;
			temp1= temp1->next;

		}
		if(temp1==NULL){
			tail=temp2;
		}
	}
}

PCB* KernelSem::del() {
	if(head==NULL){
		return NULL;
	}
	else{
		Elem* temp = head;
		head = head->next;
		PCB* temp2 = temp->info;
		temp->info = NULL;
		temp->next=NULL;
		delete temp;
		temp2->waitRet=1;
		return temp2;
	}
}
KernelSem::~KernelSem(){
asm pushf;
asm cli;

	tail=head;
	while(head!=NULL){
		head=head->next;
		Scheduler::put(tail->info);
		tail->info=NULL;
		delete tail;
		tail=head;
	}
asm popf;
}

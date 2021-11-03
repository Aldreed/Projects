#pragma once
#include "GSem.h"
#include "KSem.h"
#include <STDIO.H>


GSem* GSem::head = NULL;

GSem::GSem(KernelSem* k):info(k),next(NULL) {

}

int GSem::deleteSem(KernelSem* k) {
	asm pushf;
	asm cli;
	GSem* temp= GSem::head;
	GSem* temp2= NULL;
	while(temp!=NULL && temp->info!=k){
		temp2=temp;
		temp= temp->next;
	}
	if(temp==NULL){
		asm popf;
		return 0;
	}
	else if(temp2==NULL){
		if(temp->next!=NULL){
			GSem::head = temp->next;
		}
		else{
			GSem::head = NULL;
		}
		temp->info=NULL;
		temp->next=NULL;
		delete temp;
		asm popf;
		return  1;
	}
	else{
		temp2->next=temp->next;
		temp->info=NULL;
		temp->next=NULL;
		delete temp;
		asm popf;
		return 1;
	}
	asm popf;
}

void GSem::add(KernelSem* k) {
	asm pushf;
	asm cli;
	GSem* temp = new GSem(k);
	temp->next = GSem::head;
	GSem::head = temp;
	asm popf;
}

void GSem::timePass() {
	GSem* temp = GSem::head;
	while(temp!=NULL){
		temp->info->timePass();
		temp=temp->next;
	}
}

#pragma once
#include "GThread.h"
#include "Thread.h"
#include <STDIO.H>
#include <DOS.h>
#include <iostream.h>
#include "SCHEDULE.H"

extern int contextLock;

GThread::GThread(Thread* t):child(t),next(NULL){

}

 //optimizovano za strogo opadajucu
Thread* GThread::findID(int i){
	contextLock=1;
	if(i==-1){
		contextLock=0;
		return NULL;
	}
	if(GThread::head==NULL){
		contextLock=0;
		return NULL;
	}
	GThread* temp = GThread::head;
	if(temp==NULL){
		contextLock=0;
		return NULL;
	}
	while(temp!=NULL&&temp->child->getId()>i){
		temp= temp->next;
	}
	if(temp->child->getId()==i){
		contextLock=0;
		return temp->child;
	}
	contextLock=0;
	return NULL;
}
//optimizovano za strogo opadajucu listu;vraca -1 pri neuspehu
int GThread::deleteID(int i){
	if(i<0){
		return -1;
	}
	asm pushf;
	asm cli;
	GThread* temp = GThread::head;
	GThread* temp2= NULL;
	while(temp!=NULL&&temp->child->getId()>i){
		temp2=temp;
		temp=temp->next;
	}
	if(temp==NULL||temp->child->getId()!=i){
		asm popf;
		return -1;
	}
	if(temp==GThread::head){
		if(temp->next==NULL){
			GThread::head=NULL;
		}
		else{
			GThread::head= GThread::head->next;
		}
	}
	else{
		temp2->next=temp->next;
	}
	temp->next=NULL;
	temp->child=NULL;
	asm popf;
	delete temp;
	return 1;
}
int GThread::add(Thread* t){
	asm pushf;
	asm cli;
	GThread* temp = new GThread(t);//moze da se doda outofmemory error sa -1
	temp->next=GThread::head;
	GThread::head = temp;
	asm popf;
	return 1;

}

void GThread::erase() {
	asm pushf;
	asm cli;
	GThread* temp =GThread::head;
	while(GThread::head!=NULL){
		temp=GThread::head;
		GThread::head=GThread::head->next;
		temp->next=NULL;
		temp->child=NULL;
		delete temp;

	}
	asm popf;
}

GThread::~GThread() {
}

/*
 * RSig.cpp
 *
 *  Created on: May 18, 2020
 *      Author: OS1
 */

#include "RSig.h"
#include <stdio.h>
#include <IOSTREAM.H>
RSig::RSig():head(NULL),tail(NULL),count(0) {

}

void RSig::addQ(int i) {
	asm pushf;
	asm cli;
	Elem* temp = new Elem(i);
	if(head==NULL){
	head=temp;
	tail=temp;
	}
	else{
		tail->next=temp;
		tail=temp;
	}
	++count;
	asm popf;
}

int RSig::popQ() {//count se brise u tajmeru
	if(head==NULL){
		cout<<"p";
		return -1;
	}
	asm pushf;
	asm cli;
	Elem* temp =head;
	int ret = temp->info;
	head=head->next;
	if(head==NULL)tail=NULL;
	temp->next=NULL;
	delete temp;
	asm popf;
	return ret;

}

RSig::~RSig() {
	while(head!=NULL){
		tail=head;
		head=head->next;
		tail->next=NULL;
		delete tail;
	}
}

RSig::Elem::Elem(int i):next(NULL),info(i) {
}

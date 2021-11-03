/*
 * RSig.h
 *
 *  Created on: May 18, 2020
 *      Author: OS1
 */

#ifndef RSIG_H_
#define RSIG_H_

class RSig {
	struct Elem{
		int info;
		Elem* next;
		Elem(int i);
	};

	Elem* head;
	Elem* tail;
	volatile int count;

	void addQ(int i);//ubacuje na kraj reda i incrementira count
	int popQ();//izbacuje prvi u redu ali ne dekrementira count//vraca -1 ako je red prazan

	RSig();
	~RSig();
	friend void obradi();
	friend class PCB;
	friend class Thread;

};

#endif /* RSIG_H_ */

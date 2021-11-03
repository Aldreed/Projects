/*
 * IVTEntry.h
 *
 *  Created on: Apr 25, 2020
 *      Author: OS1
*/

#ifndef _IVTEntry_h_
#define _IVTEntry_h_

typedef void interrupt (*pInterrupt)(...);
#define PREPAREENTRY(x,y)	\
class IVTEntry;					\
void interrupt IR##x(...){IVTEntry::signal(x);}  \
IVTEntry I##x(x,y,IR##x); 							\

class KernelEv;
class IVTEntry{
public:
	friend class GEv;
	friend class KernelEv;
	friend class Event;
	IVTEntry(int i, int j,pInterrupt);
	static IVTEntry* niz[256];
	static void signal(int i);
	~IVTEntry();
private:
	KernelEv* myEv;
	int myNUM;
	int dold;
	pInterrupt old;

};
#endif

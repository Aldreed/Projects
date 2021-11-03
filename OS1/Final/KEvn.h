#ifndef _KEvn_h
#define _KEvn_h
class Event;
class PCB;
class IVTEntry;
class Thread;
class KernelEv{
	friend class Event;
	friend class IVTEntry;
	IVTEntry* myIVT;
	Thread* myThread;
	Event*  myEv;
	int val;
	PCB* myPCB;

	void  signal();

	KernelEv( Event* const t);
	~KernelEv();


};
#endif

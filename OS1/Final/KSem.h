#ifndef _KSem_h_
#define _KSem_h_
class PCB;
class KernelSem{
	friend class Semaphore;
	friend void interrupt timer();
	friend class GSem;
	struct Elem{
		PCB* info;
		int count;
		Elem* next;
		Elem(PCB* i,int c);
	};
	Elem* tail;
	Elem* head;
	int val;

	KernelSem(int i);
	~KernelSem();
	void add(PCB* i, int clk);
	void timePass();
	PCB* del();
};
#endif

#ifndef _GSem_h_
#define _GSem_h_
class KernelSem;
class GSem{
	static GSem* head ;
	friend class Semaphore;
	friend void interrupt timer();
	KernelSem* info;
	GSem* next;
	GSem(KernelSem* k);
	int deleteSem(KernelSem* k);
	void add(KernelSem* k);
	void timePass();



};
#endif

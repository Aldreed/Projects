#ifndef _SigCon_h_
#define _SigCon_h_



typedef void (*SignalHandler)();

typedef unsigned SignalId;

class SigCon{

		struct SigElem{
		SignalHandler fun;
		SigElem* next;
		SigElem(SignalHandler s);
		SigElem();


	};
	friend void obradi();
	friend void nasilanPrekid();
	friend class Thread;
	friend class PCB;
	SigCon();
	~SigCon();
	SigElem* signals;
	void add(SignalHandler s, SignalId si);
	void delAll(SignalId si);
	void swap(SignalId i,SignalHandler s1, SignalHandler s2);
	void clone(SigElem* niz);



};
#endif

#ifndef _GThread_h_
#define _GThread_h_
class Thread;

//Globalna lista niti
class GThread{
public:
	 static GThread* head;

	 Thread* child ;
	GThread* next ;

	GThread(Thread* t);

	static Thread* findID(int i);

	static int deleteID(int i);
	static int add(Thread* t);

	static void erase();

	~GThread();
};
#endif

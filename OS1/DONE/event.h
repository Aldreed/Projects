/*
 * event.h
 *
 *  Created on: Apr 26, 2020
 *      Author: OS1
 */
// File: event.h #ifndef _event_h_ #define _event_h_

#ifndef _event_h_
#define _event_h_



#include "IVTEntry.h"
typedef unsigned char IVTNo;
class KernelEv;

class Event {
	public:
	Event (IVTNo ivtNo);
	~Event ();
	void wait  ();

	protected:
	friend class KernelEv;
	void signal(); // can call KernelEv

private:
	KernelEv* myImpl;
};
#endif

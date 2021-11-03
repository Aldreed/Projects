/*
 * UMTh.h
 *
 *  Created on: May 9, 2020
 *      Author: OS1
 */

#ifndef UMTH_H_
#define UMTH_H_
#include "Thread.h"
class Thread;
class UM : public Thread{
public:
	UM();
	virtual void run();
	~UM();
};




#endif /* UMTH_H_ */

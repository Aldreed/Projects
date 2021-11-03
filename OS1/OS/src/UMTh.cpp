/*
 * UMTh.cpp
 *
 *  Created on: May 9, 2020
 *      Author: OS1
 */

#include "UMTh.h"
#include "PCB.h"
extern int userMain (int argc, char* argv[]);
extern int carg;
extern char** varg;
int ret;
UM::UM() {
	this->myPCB->myParent=0;
}

void UM::run() {
	ret =userMain(carg,varg);
}

UM::~UM() {
	waitToComplete();
}

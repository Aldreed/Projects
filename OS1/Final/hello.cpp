

#include "Thread.h"
#include <STDIO.H>
#include <DOS.h>
#include <iostream.h>

#include "GThread.h"
#include "PCB.h"
#include "SCHEDULE.H"

#include "KSem.h"
#include "semaphor.h"
#include "GSem.h"

#include "GEv.h"
#include "IVTEntry.h"

#include "SigCon.h"
#include "RSig.h"

#include "UMTh.h"
#include "user.h"
extern int userMain (int argc, char* argv[]);
extern void tick();

#define lock asm cli
#define unlock asm sti

 volatile unsigned int tsp;
 volatile unsigned int tss;

 volatile unsigned int IDS=0;//cemu ovo ?

 unsigned int* volatile GSignalBlock;


 PCB* running=NULL;
 volatile unsigned oldTimerOFF, oldTimerSEG;
 PCB* origin=NULL;

volatile Thread* target=NULL;
volatile static Thread* parent=NULL;
volatile unsigned int Gtime=1;
// extern void tick();
 volatile int contextLock=0;//stara infastruktura za signale ostavljeno da se nadje ako zatreba
 volatile int csd=0;

 volatile int tmp =0;//ove tri sluze za obradu signala u timer prekidnoj rutini
 volatile int tmp1 =0;
 volatile int tmp2=0;
//ovd dve ispod za iteraciju kroz signale;
static SigCon::SigElem* temp=NULL;
static SigCon::SigElem* temp2=NULL;

void obradi(){
	if(running->waitRet!=-3&&contextLock!=1){//contextLock da ne zovem sam sebe
			contextLock=1;
			asm pushf;
			asm sti;
			tmp = running->myQ->count;
			tmp1=0;
			running->myQ->count=0;
			while(tmp1<tmp)
			{
				tmp2=running->myQ->popQ();
				if(tmp2!=-1&&GSignalBlock[tmp2]!=1&&running->myBlockedSig[tmp2]!=1){
					temp = &(running->mySignalContainter->signals[tmp2]);
					temp2=temp;
					while(temp!=NULL&&temp->fun!=NULL){
						temp2=temp;
						temp=temp->next;
						temp2->fun();
						if(running->finished==1){
							break;
						}
					}
					if(running->finished==1){
						break;
					}
				}
				else{
					running->myQ->addQ(tmp2);
				}
				tmp--;
			}
			if(running->finished==1)
			{
				asm popf;
				csd=3;
				contextLock = 0;
				dispatch();
			}
			asm popf;
			contextLock=0;
		}

}
void interrupt timer(){


	if(csd==0){//okinuo se timer
		tick();
		if(GSem::head!=NULL){
			GSem::head->timePass();
		}

		if(Gtime==0){
			//nista
		}
		else{
			Gtime--;

			if(Gtime<=0&&contextLock!=1){//pre bilo Gtime==0 proveri da li promena u <=0 nesto menja
				asm{
					mov tsp,sp;
					mov tss,ss;
				}
				running->sp = tsp;
				running->ss = tss;
				if(running->finished!=1&&running->waitRet!=-3){Scheduler::put(running);}
				if(running->waitRet==-3){
					origin=running;
				}

				running = Scheduler::get();
				if(running==NULL){
					running=origin;
				}

				tsp = running->sp;
				tss = running->ss;

				asm{
					mov sp,tsp;
					mov ss,tss;
				}
				Gtime = running->timeslice;
			}
		}
	}
	else if(contextLock!=1){
		asm{
			mov tsp,sp;
			mov tss,ss;
			}


			running->sp = tsp;
			running->ss = tss;
			if(running->finished!=1 && csd !=2&&csd!=3&&running->waitRet!=-3){Scheduler::put(running);}
			if(csd==3){
				target=running->myThread;
			}
			if(running->waitRet==-3){
				origin=running;
			}

			if(csd==3){
				while(running->myThread==target){
					running = Scheduler::get();
				}
			}
			else{
				running = Scheduler::get();
			}
			if(running==NULL){
				running=origin;
			}

			tsp = running->sp;
			tss = running ->ss;
			asm{
				mov sp,tsp;
				mov ss,tss;
			}

			Gtime = running->timeslice;



			if(csd==3){//da li je usledio nasilan prekid usled signala 0

				if(target!=NULL){
					parent = Thread::getThreadById(target->myPCB->myParent);
									if(parent!=NULL){
										parent->myPCB->started=2;
										parent->signal(1);
										parent->myPCB->started=1;
									}
									target->myPCB->myThread->signal(2);
									delete target->myPCB;
									target->myPCB=NULL;
									csd=1;
				}

			}

	}
	csd = 0;
	obradi();

}



void Initialize(){
	asm{
			pushf
			cli
			push es
			push ax

			mov ax,0
			mov es,ax


			mov ax, word ptr es:0022h
			mov word ptr oldTimerSEG, ax
			mov ax, word ptr es:0020h
			mov word ptr oldTimerOFF, ax


			mov word ptr es:0022h, seg timer
			mov word ptr es:0020h, offset timer


			mov ax, oldTimerSEG
			mov word ptr es:0182h, ax
			mov ax, oldTimerOFF
			mov word ptr es:0180h, ax

			pop ax
			pop es
			popf
		}
}
void restore(){
	asm {
			pushf
			cli
			push es
			push ax

			mov ax,0
			mov es,ax

			mov ax, word ptr oldTimerSEG
			mov word ptr es:0022h, ax
			mov ax, word ptr oldTimerOFF
			mov word ptr es:0020h, ax

			pop ax
			pop es
			popf
		}
}


GThread* GThread::head = NULL;
extern int ret;

int carg=0;
char** varg;

int main(int argc,char* argv[])
{


	asm pushf;
	asm cli;
	carg=argc;
	varg=argv;

				running = new PCB();
				origin =running;
				Initialize();
				GSignalBlock = new volatile unsigned int[16];
					for(int i =0;i<16;i++){
						GSignalBlock[i]=0;
					}
					UM* um= new UM();

					asm popf;
					um->start();
					delete um;
					restore();
					delete [] GSignalBlock;
					GThread::erase();
					delete running;

					return ret;


}



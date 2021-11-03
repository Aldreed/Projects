#pragma once
#include <stdlib.h>
#include <stdio.h>
#include <Windows.h>
#include "slab.h"
#ifndef BLOCK_SIZE
#define BLOCK_SIZE (4096)
#endif // !BLOCK_SIZE
#ifndef CACHE_L1_LINE_SIZE
#define CACHE_L1_LINE_SIZE (64)
#endif // !CACHE_L1_LINE_SIZE

struct buddySpace{
	void* freeSpace;
	void* next;
};

struct Buddy
{
	int TotalSize;
/*
	struct buddySpace* free1;
	struct buddySpace* free2;
	struct buddySpace* free3;
	struct buddySpace* free4;
	struct buddySpace* free5;
	struct buddySpace* free6;
	struct buddySpace* free7;

	int SizeFree1;
	int SizeFree2;
	int SizeFree3;
	int SizeFree4;
	int SizeFree5;
	int SizeFree6;
	int SizeFree7;

*/
	struct buddySpace** freeSlots;
	int* sizes;

	int TotalLevels;

	HANDLE gMutex;
};
struct Buddy* globalBuddy;


/*TODO LIST:
	1. Implement the Gen2 in slabInit and kmem_cache_free//DONE
	2. Synchronization//DONE
	3. kmem_cache_destroy implement a doubly linked list//DONE
	4. kmem_cache_info//DONE
	5. kmem_cache_error implement internal cache error recording ?
	6. Test kmalloc and kfree//DONE
	7. Linking //Done
	8. Update the slabInit function so it supports the no offset kmalloc and kfree//DONE
	9. Add constructors and destructors//DONE 
	10. Unique names in kmem_cache_create//Needed ?
	11. Limit the object sizes in kmem_cache_create for the buffer objects//DONE
	12. Delete first item pointer in Slab//DONE
	13. Test kmem_cache_shrink//DONE
	14. Test Synchronization with multiple threads
	15. Check the strcpy_s usfulness in kmem_cache_create
	16. Redo the kmalloc and kfree //Done 10.1.2021
	17. Test kmalloc and kfree //Done 10.1.2021
	18. Test the Multiple Caches optimization on old tests
	19. Buddy Optimization ?
OPTIMIZATION LIST:
	1. Multiple Caches inside a single block of memory //Need to update kmem_cache_destroy//Done 10.1.2021
	2. LRU algorithm for kmalloc and kfree cache searches and maybe internal slab searches
	3. Maybe instead of LRU an array of all possible Buffer caches should be used
	4. Slab search in kmem_free should first go by FullSlabs then by semiFullSlabs
BUGS:
	1. The offset field in Cache not reseting properly//FIXED
	2. globalCacheBuddy not being updated in kmem_cache_create//FIXED
	3. BuddyDeallocate always moving the first pair of BuddySlots to a upper level//FIXED
	4. slabInit allowing overflow to the next block//FIXED
	5. Added input checks to kmalloc, kfree and kmem_cache_free and updated the input check in kmem_init//FIXED
	6. Made the argumetns of the ctors and dtors point to the actual object//FIXED 10.1.2021
	7. Moved the dtor before updating the freeItems list//FIXED 10.1.2021
	8. Redo the kmem_info so it accommodates small objects //Done 10.1.2021
	9. Redo the equation for the fill percentage in kmem_info//Done 10.1.2021
QUESTIONS:
	1. Da li je u redu da u prvoj ploci (objSize=60,pomeraj = 0) treci objekat bude na 
	adresi 120 ,a u drugoj ploci istog kesa(objSize=60,pomeraj=64) treci objekat bude na adresi 192? 
	
	Ako nije u redu onda samo u slabInit popravi offset i initSize 

	2. Ako ne bude prosledjen konstruktor da li treba ona dozvoliti da se alociraju objekti/buferi?

	3.Sta je argument u konstruktorima i destruktorima ? Da li treba kada se unisti kes pozvati i destruktori za svaki objekat ?
ASSUMPTIONS:
	1. Strings using 'size-' in their names will not be passed as names for caches
	*/


//OPTIMIZACIJA Da nakon uzimanja ddovlojnog prostora za metadata Buddyja preostali prostor bude stepen dvojke
//REQUIRED OPTIMIZATION//Done
void kmem_init(void* space, int block_num) {//Da li treba buddySpace da pokazuje na jedan blok ili samo na memoriju

	//Check input
	if (block_num == 1 || space==NULL) {
		return -1;
	}
	//Calculate TotalSpace Available
	int TotalSpace = (block_num) * BLOCK_SIZE;
	printf("First memSpace:%p\n TotalSize:%d\n",space, TotalSpace);
	
	//Allocate space for Buddy MetaData and save the starting postition(reworked..StartingPosition isthe same as globalBuddy pointer
	struct Buddy *my = (((struct Buddy*)space));
	void* DynamicAllocationSpace = (void*)((struct Buddy*)space + 1);
	((size_t)space)=(size_t)space+(size_t)BLOCK_SIZE;

	//Calculate number of levels
	int curLevel = block_num-1;
	int totalLevels = 0;
	/*
	if (curLevel % 2 == 1) {
		curLevel--;
	}
	*/
	for (; curLevel > 1 ; totalLevels++)
	{
		curLevel >>= 1;
	}
	my->TotalLevels = totalLevels-1;

	//Initialize the MetadataPointers
	my->freeSlots = DynamicAllocationSpace;
	DynamicAllocationSpace = (void*)((size_t)DynamicAllocationSpace + (size_t)totalLevels * sizeof(struct buddySpace*));
	my->sizes = DynamicAllocationSpace;
	DynamicAllocationSpace = (void*)((size_t)DynamicAllocationSpace + (size_t)totalLevels * sizeof(int));

	//Check if the MetaData one Block Limit has been broken
	if (DynamicAllocationSpace>=space)
	{
		return -2;
	}

	//Find Biggest
	size_t offset = 1;
	for (size_t i = 0; i < totalLevels; i++)
	{
		offset = offset << 1;
	}


	//Initialize the Zero Level space blocks in the Buddy System
	//size_t leftSpace = (TotalSpace - BLOCK_SIZE);
	size_t leftSpace = offset * BLOCK_SIZE;
	my->TotalSize = leftSpace;
	my->freeSlots[0]= (struct buddySpace*)space;
	
	struct buddySpace* temp = (struct buddySpace*)((size_t)space + leftSpace/2);
	my->freeSlots[0]->next = (void*)temp;
	temp->freeSpace = temp;
	temp->next = 0;
	my->sizes[0] = leftSpace = leftSpace/2;
	printf("Size of %d blocks is : %d \n", 0, my->sizes[0]);

	//Initialize the rest of the space blocks in the Buddy System as empyty
	for (int i = 1; i < totalLevels; i++)
	{
		my->freeSlots[i] = 0;
		if (leftSpace < 8)
		{
			my->sizes[i] = -1;
		}
		else
		{
			leftSpace /= 2;
			my->sizes[i] = leftSpace;
		}
		printf("Size of %d blocks is : %d \n", i, my->sizes[i]);
	}

	//Add the hanging parts
	if (my->TotalSize < (block_num - 1) * BLOCK_SIZE) {
		space = (size_t)space + (size_t)my->TotalSize;
		int dif = (block_num - 1) - 2*(my->sizes[0] / BLOCK_SIZE);
		if (block_num - 1 == 3) {
			dif = 1;
		}
		int i = 0;
		while (dif > 0) {
			if (dif >= my->sizes[i] / BLOCK_SIZE) {
				struct buddySpace* temp = my->freeSlots[i];
				while (temp != 0 && temp->next != 0) {
					temp = (struct buddySpace*)temp->next;
				}
				if (temp == 0)
				{
					my->freeSlots[i] = space;
					my->freeSlots[i]->freeSpace = space;
					my->freeSlots[i]->next = 0;
				}
				else
				{
					temp->next = space;
					struct buddySpace* ne = temp->next;
					ne->freeSpace = ne;
					ne->next = 0;
				}
				space = (size_t)space + (size_t)my->sizes[i];
				dif = dif - my->sizes[i] / BLOCK_SIZE;
			}
			i++;
		}
	}
	my->TotalSize = (block_num - 1) * BLOCK_SIZE;

	/*
	//Put the odd one in the lowestLevel
	if ((block_num - 1) % 2 == 1) {
		my->freeSlots[totalLevels - 1] = (struct buddySpace*)((size_t)space + (size_t)(block_num - 2) * BLOCK_SIZE);
		my->freeSlots[totalLevels - 1]->next = 0;
		my->freeSlots[totalLevels - 1]->freeSpace = (void*)my->freeSlots[totalLevels - 1];
	}
	*/
	//Set the global pointer to the BuddyMetadata
	globalBuddy = my;

	//Synchro
	globalBuddy->gMutex = CreateMutex(
		NULL,
		FALSE,
		NULL);
	//Error check
	if (globalBuddy->gMutex == NULL) {
		return -3;
	}

	//Print coordinates
	printf("Starting address: %p\n", globalBuddy);
	printf("Zero Level Zero Index Address: %p\n", my->freeSlots[0]);
	printf("Zero Level First Index Address: %p\n", my->freeSlots[0]->next);



	//huh..0x00B4B848 - 0x00B4B068
	/*
	my->free1 = (struct buddySpace*)space;
	my->free1->freeSpace = space;
	struct buddySpace* temp = my->free1->next = (struct buddySpace*)((size_t)space + (((size_t)TotalSpace-sizeof(struct Buddy))/2));

	temp->freeSpace = temp;
	temp->next = 0;

	my->free2 = my->free3 = my->free4 = 0;
	my->TotalSize = (size_t)TotalSpace - sizeof(struct Buddy);
	printf("%p\n", my->free1->next);
	printf("%p\n%p\n", space,my);
	t = my;
	*/
}

///Alocates the smallest possible space in the Level hierarchy to fit target Bytes//should i rework this so its 'target Blocks' ?
void* BuddyAllocate(size_t target) {
	//Check input
	if (target <= 0) {
		return -1;
	}

	//Find the lowest level in which the target can be allocated 
	int i = globalBuddy->TotalLevels - 1;
	for (; i > -1 ; i--)
	{
		if (target <= globalBuddy->sizes[i]) {
			break;
		}
	}
	
	//If no level was found return an error //Rewrite
	if (i == -1) {
		return -1; //TODO/Check if you have enough space if all of the free Zero Level indexes are allocated
	}

	//Check if the level has free spaces and if so retrun the allocated space
	if (globalBuddy->freeSlots[i] != 0) {
		struct buddySpace* temp = globalBuddy->freeSlots[i];
		globalBuddy->freeSlots[i] = (struct buddySpace*)globalBuddy->freeSlots[i]->next;
		return temp;
	}

	//Find the next higher level which has empty spaces
	int curLvl = i;
	while (curLvl!=-1 && globalBuddy->freeSlots[curLvl]==0)
	{
		curLvl--;
	}

	//If there is no space return error
	if (curLvl == -1) {
		return -2;
	}

	//Split the found space in two until the desired level is reached
	while (curLvl != i) {
		struct buddySpace* temp = globalBuddy->freeSlots[curLvl];
		globalBuddy->freeSlots[curLvl] = globalBuddy->freeSlots[curLvl]->next;
		struct buddySpace* tempNext = temp->next = (void*)((size_t)temp + ((size_t)(globalBuddy->sizes[curLvl]) / 2));
		tempNext->freeSpace = temp->next;
		//printf("Debug temp %p\nDebug temp-next %p\nDiff%d", temp, temp->next, (size_t)temp - (size_t)(temp->next));
		
		curLvl++;
		
		struct buddySpace* cur = globalBuddy->freeSlots[curLvl];
		struct buddySpace* prev = 0;
		while (cur!=0 &&(size_t)temp > (size_t)cur)
		{
			prev = cur;
			cur = cur->next;
		}
		
		if (prev == 0) {
			struct buddySpace* tempNext = ((struct buddySpace*)temp->next);
			(struct buddySpace*)tempNext->next = globalBuddy->freeSlots[curLvl];
			globalBuddy->freeSlots[curLvl] = temp;
		}
		else {
			((struct buddySpace*)temp->next)->next = cur;
			prev->next = temp;
		}
 	}

	//Return the space
	struct buddySpace* ret = globalBuddy->freeSlots[curLvl];
	globalBuddy->freeSlots[curLvl]= globalBuddy->freeSlots[curLvl]->next;
	return ret;
}

void BuddyDeallocate(void* target, size_t targetSize) {
	//Check input
	if (targetSize <= 0) {
		return -1;
	}
	if (target == 0) {
		return -1;
	}
	//Find the lowest level in which the target can be Placed 
	int i = globalBuddy->TotalLevels-1;
	for (; i > -1; i--)
	{
		if (targetSize <= globalBuddy->sizes[i]) {
			break;
		}
	}

	//If no level was found return an error
	if (i == -1) {
		return -1; 
	}

	//Find the targets space in the level;If it can be merged with a adjacent space merge it and move it up a level
	//Repeat until level zero is reached or no mergers can happen
	int found = 1;
	struct buddySpace* temp = (struct buddySpace*)target;
	temp->freeSpace = target;
	temp->next = 0;
	
	do
	{
		found = 0;
		//remove offset
		/*
		size_t offset = (BUDDY_DEPTH <= i) ? i - BUDDY_DEPTH + 1 : 0;*/
		size_t move = 0;
		if (i == 0) {
			move = globalBuddy->TotalSize;
		}
		else
		{
			move = globalBuddy->sizes[i - 1];
		}

		struct buddySpace* cur = globalBuddy->freeSlots[i];
		struct buddySpace* prev = 0;
		struct buddySpace* prevPrev = 0;
		while (cur != 0 && (size_t)temp > (size_t)cur)
		{
			if (prev != 0) {
				prevPrev = prev;
			}
			prev = cur;
			cur = cur->next;
		}

		if (prev == 0) {
			temp->next = globalBuddy->freeSlots[i];
			globalBuddy->freeSlots[i] = temp;
		}
		else {
			prev->next = temp;
			temp->next = cur;
		}

		if (i != 0) {
			size_t frame = (size_t)globalBuddy->freeSlots[i];
			while (frame + globalBuddy->sizes[i] <= (size_t)(temp))
			{
				frame += move;
			}

			if ((size_t)temp == frame && cur != 0) {
				if ((size_t)cur == (size_t)temp + globalBuddy->sizes[i]) {
					if (prev != 0) {
						prev->next = cur->next;
					}
					else
					{
						globalBuddy->freeSlots[i] = cur->next;
					}
				
					found = 1;
				}
			}
			else if (prev != 0 && (size_t)temp == (size_t)prev + globalBuddy->sizes[i]) {
				if (prevPrev != 0) {
					prevPrev->next = temp->next;
				}
				else
				{
					globalBuddy->freeSlots[i] = temp->next;
				}
				temp = prev;
				found = 1;
			}
			i = i - 1;
		}
	} while (found==1&&i!=-1);
	
}

struct SlabSlot {
	void* next;
};

struct Slab {
	void* next;
	size_t objSize;
	int NumOfItems;
	int NumOfBlocks;
	//struct SlabSlot* firstItem;
	struct SlabSlot* freeItem;
};
struct kmem_cache_s {
	char name[1024];
	size_t objSize;
	size_t TotalSize;

	int TotalItems;
	int TotalSlabs;
	int TotalBlocks;

	void* (*ctor)(void*);
	void* (*dtor)(void*);

	struct Slab* freeSlabs;
	struct Slab* fullSlabs;
	struct Slab* semiFullSlabs;

	/*
	struct Slab* tailFree;
	struct Slab* tailFull;
	struct Slab* tailSemi;
	*/

	int offset;
	int unusedMax;

	int errorCode;

	void* next;
	void* prev;

};

typedef struct kmem_cache_s Cache;
typedef struct kmem_cache_s kmem_cache_t;
typedef struct kmem_cache_s kmem_cache_s;
Cache* globalCacheBuddy = 0;
Cache* globalBufferBuddy = 0;
//Prva iteracija sa ulancanom listom za Items;//Druga iteracija bi bila bez te liste nego da se samo pamete slobodna mesta u listi ; kada se obrise item on se samo ubacuje u listu slobodnih; PRETPOSTAVKA da taj item zaista je bio u listi
void  slabInit(kmem_cache_s* temp) {
	size_t initSize = temp->objSize + sizeof(struct Slab) + sizeof(struct SlabSlot);//slab slot u drugoj iteraciji nije potreban
	void* firstSlab = temp->freeSlabs;
	temp->freeSlabs = BuddyAllocate(initSize);
	if (temp->freeSlabs == -1 || temp->freeSlabs == -2) {
		temp->freeSlabs = firstSlab;
		temp->errorCode = temp->errorCode | 1;
		return;
	}
	struct Slab* freeSlab = temp->freeSlabs;
	freeSlab->next = firstSlab ;
	freeSlab->objSize = temp->objSize;
	freeSlab->NumOfBlocks = (initSize < BLOCK_SIZE) ? 0 : (initSize) / BLOCK_SIZE;
	freeSlab->NumOfBlocks += ((initSize % BLOCK_SIZE) == 0) ? 0 : 1;
	freeSlab->NumOfItems = 0;

	///Initialize the lists in the Slab//Needs rework//Use Second Gen
	//freeSlab->firstItem = 0;
	//size_t offset = (CACHE_L1_LINE_SIZE <= sizeof(struct SlabSlot) || CACHE_L1_LINE_SIZE < temp->objSize) ? 0 : (CACHE_L1_LINE_SIZE + temp->offset)%(freeSlab->NumOfBlocks*BLOCK_SIZE);
	if (temp->unusedMax == -1) {
		size_t offset = (freeSlab->NumOfBlocks * BLOCK_SIZE) % temp->objSize;
		temp->unusedMax = offset;
	}
	freeSlab->freeItem = (struct SlabSlot*)((size_t)freeSlab + sizeof(struct Slab)+temp->offset);
	struct SlabSlot* cur = freeSlab->freeItem;
	struct SlabSlot* prev = 0;
	//initSize = (offset == 0 || temp->offset == 0 || strstr(temp->name, "size-") != NULL) ? temp->objSize : temp->offset;
	initSize = temp->objSize;
	if (initSize < sizeof(void*)) {
		initSize = sizeof(void*);
	}
	while (((size_t)cur + initSize) < freeSlab->NumOfBlocks * BLOCK_SIZE + (size_t)freeSlab)
	{
		prev = cur;
		cur->next = (struct SlabSlot*)((size_t)cur + initSize);///AV ako je poslednji block u alociranoj memoriji??//TODO POPRAVI
		cur = cur->next;
	}
	cur->next = 0;
	if(prev!=0)prev->next=0;
	//Update the offset for the next Free Slab
	if (strstr(temp->name, "size-") == NULL)temp->offset = (temp->offset + CACHE_L1_LINE_SIZE);
	if (temp->offset > temp->unusedMax)temp->offset = 0;

	//Update the total size of the Cache;
	temp->TotalSize += freeSlab->NumOfBlocks * BLOCK_SIZE;

	//Update the number of total Slabs
	temp->TotalSlabs++;

	//Update the number of totalBlocks
	temp->TotalBlocks += freeSlab->NumOfBlocks;
}

//Multiple points of synchronization
void* kmem_cache_alloc(kmem_cache_t* cachep) {

	//Check input
	if (cachep == NULL) {
		return -1;
	}

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}

	//If there are available SemiFull pointers
	if (cachep->semiFullSlabs != 0) {
		struct Slab* temp = cachep->semiFullSlabs;
		struct SlabSlot* cur = 0;
		/*
		//Find the Last item
		//while (cur->next != 0) {
		//	cur = cur->next;
		//}
		*/



		//Add new item and Update the pointers
		cur = temp->freeItem;
		temp->freeItem = (temp->freeItem->next == 0) ? 0 : temp->freeItem->next;//Problematicno zbog *void next
		cur->next = 0;

		//Increment the number of Items in slab and the cache
		temp->NumOfItems++;
		cachep->TotalItems++;

		//Check if the slab pointer needs to be relocated
		if (temp->freeItem == 0) {
			cachep->semiFullSlabs = temp->next;
			temp->next = cachep->fullSlabs;
			cachep->fullSlabs = temp;
		}

		//Form and return the pointer of the object
		void* ret = (void*)((size_t)cur);

		//Call ctor
		if (cachep->ctor != NULL) {
			cachep->ctor(ret);
		}


		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return -2;
		}

		return ret;

	}
	else if (cachep->freeSlabs != 0) {
		struct Slab* temp = cachep->freeSlabs;
		struct SlabSlot* cur = 0;

		//Add new item and Update the pointers
		cur = temp->freeItem;
		temp->freeItem = (temp->freeItem->next == 0) ? 0 : temp->freeItem->next;//Problematicno zbog *void next
		//temp->firstItem =cur;
		cur->next = 0;

		//Increment the number of Items in slab and cache
		temp->NumOfItems++;
		cachep->TotalItems++;

		//Check if the slab pointer needs to be relocated
		if (temp->freeItem == 0) {
			cachep->freeSlabs = temp->next;
			temp->next = cachep->fullSlabs;
			cachep->fullSlabs = temp;
		}
		else
		{
			cachep->freeSlabs = temp->next;
			temp->next = cachep->semiFullSlabs;
			cachep->semiFullSlabs = temp;
		}
		//Form and return the pointer of the object
		void* ret = (void*)((size_t)cur);

		//Call ctor
		if (cachep->ctor != NULL) {
			cachep->ctor(ret);
		}


		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return -2;
		}


		return ret;
	}


	///Alocate new Slab, Initialize it and add the item to the slab//REDO kad dodas 
	//Alocate
	/*//Allocate and Initialize the Slab//Probably needs Rework//Can be optimized for small objects ( allocate the slab inside the same block as the Cache)
	size_t initSize = cachep->objSize + sizeof(struct Slab) + sizeof(struct SlabSlot);
	cachep->freeSlabs = BuddyAllocate(initSize);
	struct Slab* freeSlab = cachep->freeSlabs;
	freeSlab->next = 0;
	freeSlab->objSize = cachep->objSize;
	freeSlab->NumOfBlocks = (initSize < BLOCK_SIZE) ? 0 : (initSize) / BLOCK_SIZE;
	freeSlab->NumOfBlocks += ((initSize % BLOCK_SIZE) == 0) ? 0 : 1;
	freeSlab->NumOfItems = 0;

	///Initialize the lists in the Slab//Needs rework
	freeSlab->firstItem = 0;
	freeSlab->freeItem = (struct SlabSlot*)((size_t)freeSlab + sizeof(struct Slab));
	struct SlabSlot* cur = freeSlab->freeItem;
	initSize = sizeof(struct SlabSlot) + cachep->objSize;
	while (((size_t)cur + initSize) < freeSlab->NumOfBlocks * BLOCK_SIZE + (size_t)freeSlab)
	{
		cur->next = (struct SlabSlot*)((size_t)cur + initSize);///AV ako je poslednji block u alociranoj memoriji??//TODO POPRAVI
		cur = cur->next;
	}
	cur->next = 0;
	*/

	slabInit(cachep);
	if ((cachep->errorCode & 1) == 1) {
		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return -2;
		}
		return 0;
	}
	//Addint the Item
	struct Slab* temp = cachep->freeSlabs;
	struct SlabSlot* cur = 0;

	//Add new item and Update the pointers
	cur = temp->freeItem;
	temp->freeItem = (temp->freeItem->next == 0) ? 0 : temp->freeItem->next;//Problematicno zbog *void next
	//temp->firstItem = cur;
	cur->next = 0;

	//Increment the number of Items in slab and cache
	temp->NumOfItems++;
	cachep->TotalItems++;

	//Check if the slab pointer needs to be relocated
	if (temp->freeItem == 0) {
		cachep->freeSlabs = temp->next;
		temp->next = cachep->fullSlabs;
		cachep->fullSlabs = temp;
	}
	else
	{
		cachep->freeSlabs = temp->next;
		temp->next = cachep->semiFullSlabs;
		cachep->semiFullSlabs = temp;
	}
	//Form and return the pointer of the object
	void* ret = (void*)((size_t)cur);

	//Call ctor
	if (cachep->ctor != NULL) {
		cachep->ctor(ret);
	}


	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}


	return ret;

}

//Bugs//Refactor//Uses: BlockSize
kmem_cache_t* kmem_cache_create(const char* name, size_t size, void(*ctor)(void*), void(*dtor)(void*)) {
	
	//Check Args
	if (name == 0 || size < 0) {
		return -1;
	}

	//Check the size limit for buffers
	if (strstr(name, "size-") != NULL && (size < 32 || size>65536)) {
		return -1;
	}

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}


	//If this is the first allocation then allocate the MasterCache
	if (globalCacheBuddy == 0) {
		//Allocate the Cache
		void* blockAllocated = BuddyAllocate(BLOCK_SIZE);
		if (blockAllocated == -1 || blockAllocated == -2 || blockAllocated == 0) {
			//Mutex release
			if (!ReleaseMutex(globalBuddy->gMutex)) {
				return 0;
			}
			return 0;
		}
		Cache* tempGlobal = blockAllocated;
		//Initialize MetaData
		char* globalName = "CacheMaster";
		strcpy_s(tempGlobal->name, 1024, globalName);
		tempGlobal->objSize = sizeof(Cache);
		tempGlobal->TotalSize = 0;
		tempGlobal->TotalItems = 0;
		tempGlobal->TotalBlocks = 0;
		tempGlobal->TotalSlabs = 0;
		tempGlobal->offset = 0;
		tempGlobal->unusedMax = -1;
		tempGlobal->ctor = NULL;
		tempGlobal->dtor = NULL;
		tempGlobal->freeSlabs = 0;
		tempGlobal->fullSlabs = 0;
		tempGlobal->semiFullSlabs = 0;
		tempGlobal->offset = 0;
		tempGlobal->errorCode = 0;
		slabInit(tempGlobal);
		tempGlobal->next = 0;
		tempGlobal->prev = 0;
		globalCacheBuddy = tempGlobal;
	}
	//Allocate the Cache
	Cache* temp = kmem_cache_alloc(globalCacheBuddy);
	if (temp == -1 || temp == -2 || temp == 0) {
		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return 0;
		}
		return 0;
	}
	//Initialize MetaData
	strcpy_s(temp->name,1024, name);
	temp->objSize = size;
	temp->TotalSize = 0;
	temp->TotalItems = 0;
	temp->TotalBlocks = 0;
	temp->TotalSlabs = 0;
	temp->offset = 0;
	temp->unusedMax = -1;
	temp->ctor = ctor;
	temp->dtor = dtor;
	temp->freeSlabs = 0;
	temp->fullSlabs = 0;
	temp->semiFullSlabs = 0;
	temp->errorCode = 0;
	/*
	//Allocate and Initialize the Slab//Probably needs Rework//Can be optimized for small objects ( allocate the slab inside the same block as the Cache)
	size_t initSize = temp->objSize + sizeof(struct Slab) + sizeof(struct SlabSlot);
	temp->freeSlabs = BuddyAllocate(initSize);
	struct Slab* freeSlab = temp->freeSlabs;
	freeSlab->next = 0;
	freeSlab->objSize = size;
	freeSlab->NumOfBlocks = (initSize< BLOCK_SIZE) ? 0 : (initSize) / BLOCK_SIZE;
	freeSlab->NumOfBlocks += ((initSize % BLOCK_SIZE) == 0) ? 0 : 1;
	freeSlab->NumOfItems = 0;

	///Initialize the lists in the Slab//Needs rework
	freeSlab->firstItem = 0;
	freeSlab->freeItem = (struct SlabSlot*)((size_t)freeSlab + sizeof(struct Slab));
	struct SlabSlot* cur = freeSlab->freeItem;
	initSize = sizeof(struct SlabSlot)+temp->objSize;
	while (((size_t)cur + initSize) < freeSlab->NumOfBlocks*BLOCK_SIZE+(size_t)freeSlab)
	{
		cur->next = (struct SlabSlot*)((size_t)cur + initSize);///AV ako je poslednji block u alociranoj memoriji??//TODO POPRAVI
		cur = cur->next;
	}
	cur->next = 0;
	*/
	slabInit(temp);
	//Add to global list
	/*
	if (globalCacheBuddy == 0) {
		globalCacheBuddy = temp;
		temp->next = 0;
		temp->prev = 0;
	}
	else {
		temp->next = globalCacheBuddy;
		globalCacheBuddy->prev = temp;
		globalCacheBuddy = temp;
		temp->prev = 0;
	}
	*/
	Cache* cur = globalCacheBuddy;
	Cache* prev = 0;
	while (cur != 0) {
		prev = cur;
		cur = cur->next;
	}
	if (prev==0)
	{
		globalCacheBuddy->next = temp;
		temp->prev = globalCacheBuddy;
	}
	else {
		prev->next = temp;
		temp->prev = prev;
	}

	temp->next = 0;

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}

	//return cache
	return temp;
}

//Test this; fix output
int kmem_cache_shrink(kmem_cache_t* cachep) {
	//Check input
	if (cachep == NULL) {
		return -1;
	}


	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}

	//Initialize iterators
	struct Slab* cur = cachep->freeSlabs;
	struct Slab* temp = NULL;
	int numberFreed = 0;
	int spaceFreed = 0;

	//Iterate and delete
	while (cur!=NULL)
	{
		temp = cur;
		cur = cur->next;
		spaceFreed += temp->NumOfBlocks * BLOCK_SIZE;
		BuddyDeallocate(temp, temp->NumOfBlocks * BLOCK_SIZE);
		numberFreed++;
	}
	

	//Upadate Cache total size
	cachep->TotalSize -= spaceFreed;

	//Update Cache total slabs
	cachep->TotalSlabs -= numberFreed;

	//Update Cache total blocks
	cachep->TotalBlocks -= spaceFreed / BLOCK_SIZE;

	//Delete the pointer
	cachep->freeSlabs = 0;
	
	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}

	//Function Success
	return numberFreed;

}
//Check
//Check
void kmem_cache_free(kmem_cache_t* cachep, void* objp) {
	
	//Check input
	if (cachep == NULL || objp == NULL) {
		return;
	}
	
	
	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return;
	}
	

	//Find items
	struct Slab* temp = cachep->semiFullSlabs;
	struct Slab* prev = 0;
	int i = 0;
	size_t local = (size_t)objp;
	
	while (temp!=0&&!((size_t)temp<=local&&local<=((size_t)(temp->NumOfBlocks * BLOCK_SIZE)+ (size_t)temp)))
	{
		prev = temp;
		temp = temp->next;
	}

	if (temp == 0) {
		temp = cachep->fullSlabs;
		prev = 0;
		while (temp != 0 && !((size_t)temp <= local && local <= ((size_t)(temp->NumOfBlocks * BLOCK_SIZE) + (size_t)temp)))
		{
			prev = temp;
			temp = temp->next;
		}
		if(temp!=0)i = 2;
	}
	else {
		i = 1;
	}

	if (temp == 0) {
		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return;
		}
		return;
	}

	/*
	struct SlabSlot* ssT = temp->firstItem;
	struct SlabSlot* ssT2 = 0;
	while (local != (size_t)ssT + sizeof(struct SlabSlot)) {
		ssT2 = ssT;
		ssT = ssT->next;
	}
	//Decrement the numeber of items in the slab and in the cache
	cachep->TotalItems--;
	temp->NumOfItems--;

	if (ssT2 != 0) {
		ssT2->next = ssT->next;
	}
	*/


	//Check if the item is really allocated
	struct SlabSlot* ssT = temp->freeItem;
	while (ssT != 0 && (size_t)ssT != local) {
		ssT = ssT->next;
	}
	//Check if the last free slot is actually the item or if the item is actually a free slot
	cachep->TotalItems--;
	temp->NumOfItems--;
	//Check if the pointers need to be updated
	if (temp->NumOfItems == 0)
	{
		if (i == 1) {
			if (prev == 0) {
				cachep->semiFullSlabs = temp->next;
			}
			else {
				prev->next = temp->next;
			}
		}
		else
		{
			if (prev == 0) {
				cachep->fullSlabs = temp->next;
			}
			else {
				prev->next = temp->next;
			}
		}

		temp->next = cachep->freeSlabs;
		cachep->freeSlabs = temp;
	}
	else if (i==2)
	{
		if (prev == 0) {
			cachep->fullSlabs = temp->next;
		}
		else {
			prev->next = temp->next;
		}
		temp->next = cachep->semiFullSlabs;
		cachep->semiFullSlabs = temp;
	}
	
	//Call dtor
	if (cachep->dtor != NULL) {
		cachep->dtor(objp);
	}

	//Update the free item list
	ssT = (struct SlabSlot*)objp;
	ssT->next= temp->freeItem;
	temp->freeItem = ssT;

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return;
	}
}
//Test
void* kmalloc(size_t size) {

	//Size Check
	if ((size < 32 || size>65536)) {
		return 0;
	}

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return 0;
	}

	//Calculate size
	size_t tSize = size;
	int exp = 0;
	while (tSize > 0)
	{
		tSize = tSize >> 1;
		exp++;
	}

	//not in bounds
	if (exp < 5 || exp>17) {
		//Mutex release
		if (!ReleaseMutex(globalBuddy->gMutex)) {
			return 0;
		}
		return 0;
	}


	//Calculate the obj size for the search
	tSize = 1;
	for (int i = exp; i > 0; i--)
	{
		tSize = tSize << 1;
	}

	//Find The cache that contains the buffers of the requested size 
	Cache* temp = globalBufferBuddy;
	while (temp != 0 && (strstr(temp->name, "size-") == NULL || temp->objSize != tSize))
	{
		temp = temp->next;
	}
	//Make a new cache if none found && Error check 
	if (temp == 0) {
		char buf[8];
		snprintf(buf, 8, "size-%d", exp);

		temp = kmem_cache_create(buf, tSize, NULL, NULL);
		if (temp == 0 || temp == -1 || temp == -2) {
			//Mutex release
			if (!ReleaseMutex(globalBuddy->gMutex)) {
				return 0;
			}
			return 0;
		}
		Cache* previo = temp->prev;
		previo->next = 0;
		if (globalBufferBuddy == 0) {
			globalBufferBuddy = temp;
			temp->next = 0;
			temp->prev = 0;
		}
		else {
			temp->next = globalBufferBuddy;
			temp->prev = 0;
			globalBufferBuddy->prev = temp;
			globalBufferBuddy = temp;
		}
	}

	//Get a newly allocated buffer
	void* ret = kmem_cache_alloc(temp);
	

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return 0;
	}

	//Check for errors
	if (ret == -1 || ret == -2 || ret == 0) {
		return 0;
	}


	//Return a newly allocated buffer
	return ret;

}                                             
//Test 
void kfree(const void* objp) {

	//Check input
	if (objp == NULL) {
		return;
	}

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return;
	}

	size_t local = (size_t)objp;
	Cache* temp = globalBufferBuddy;
	while (temp != 0)
	{
		while (temp != 0 && strstr(temp->name, "size-") == NULL)
		{
			temp = temp->next;
		}
		if (temp == 0) {
			//Mutex release
			if (!ReleaseMutex(globalBuddy->gMutex)) {
				return;
			}
			return; //error
		}
		int startingCount = temp->TotalItems;
		kmem_cache_free(temp, objp);
		if (startingCount != temp->TotalItems) {
			break;
		}
		temp = temp->next;
	}
	if (temp!=0&&temp->TotalBlocks>10)
	{
		kmem_cache_shrink(temp);
	}

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return;
	}
	return;
}
//Update when optimizing the number of caches in a block
void kmem_cache_destroy(kmem_cache_t* cachep) {
	
	//Check input
	if (cachep == 0) {
		return -1;
	}

	//Check if caches have been alocated and that the input hasn't commited an access violation
	if (globalCacheBuddy == 0 || globalCacheBuddy == cachep) {
		return-1;
	}

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}

	//Deallocate allocated slabs
	//Free slabs
	kmem_cache_shrink(cachep);

	//Semifull Slabs
	struct Slab* temp = cachep->semiFullSlabs;
	struct Slab* prev = 0;
	while (temp!=0)
	{
		prev = temp;
		temp = temp->next;
		BuddyDeallocate(prev,prev->NumOfBlocks*BLOCK_SIZE);
	}

	//Full Slabs
	temp = cachep->fullSlabs;
	while (temp != 0)
	{
		prev = temp;
		temp = temp->next;
		BuddyDeallocate(prev, prev->NumOfBlocks * BLOCK_SIZE);
	}


	//Update the pointers
	if (cachep->prev != 0) {
		cachep->prev = cachep->next;
	}
	if (cachep->next != 0) {
		((Cache*)(cachep->next))->prev = cachep->prev;
	}

	/*
	//Update the global pointer
	if (cachep == globalCacheBuddy) {
		if (cachep->prev != 0) {
			globalCacheBuddy=cachep->prev;
		}
		else if (cachep->next != 0) {
			globalCacheBuddy = cachep->next;
		}
		else
		{
			globalCacheBuddy = 0;
		}
	}
	*/

	//Dealocate the space
	/*
	BuddyDeallocate(cachep, BLOCK_SIZE);
	*/
	kmem_cache_free(globalCacheBuddy, cachep);

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}

}

void kmem_cache_info(kmem_cache_t* cachep) {
	
	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}

	int totalBlocks = cachep->TotalBlocks;
	int totalSlabs = cachep->TotalSlabs;
	int totalSpace = cachep->TotalSize - sizeof(struct Slab) * totalSlabs;
	int totalItems = cachep->TotalItems;
	
	int objSize = (cachep->objSize >= sizeof(void*)) ? cachep->objSize : sizeof(void*);
	int objPerSlab = ((totalSpace) / totalSlabs) / objSize;

	double fillPrec = (double)((double)totalItems* (double)objSize/(totalSpace*1.0)*100);

	objSize = cachep->objSize;

	printf("Name: %s\nObject Size: %d\nCache size in blocks: %d\nTotal Number of Slabs: %d\nObjects per Slab: %d\nUsed up space: %.2f%%\n", cachep->name, objSize, totalBlocks, totalSlabs, objPerSlab, fillPrec);

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}

};

void ct(void* un) {
	printf("Constructor\n");
}
void dt(void* un) {
	printf("Destructor\n");
}


int kmem_cache_error(kmem_cache_t * cachep) {

	//Synchro
	DWORD mutexWait = WaitForSingleObject(globalBuddy->gMutex, INFINITE);

	if (mutexWait == WAIT_ABANDONED) {
		return -1;
	}

	int ret = cachep->errorCode;

	//Mutex release
	if (!ReleaseMutex(globalBuddy->gMutex)) {
		return -2;
	}

	return ret;


}
//void main() {

	//printf("hi buddy size = %d \n",sizeof(struct Buddy));
	//printf("hi buddySpace size = %d \n", sizeof(struct buddySpace));
	//printf("hi slab size = %d \n", sizeof(struct Slab));
	//printf("hi cache size = %d \n", sizeof(kmem_cache_s));
	//void* tempSpace = malloc(BLOCK_SIZE*9);
	//kmem_init(tempSpace, 9);
	//void* allocated = BuddyAllocate(75);
	//printf("I allocate 75B at: %p\n",allocated); 
	//BuddyDeallocate(allocated, 75);
	//Cache* tempC = kmem_cache_create("hi", 60, &ct, &dt);
	//printf("Allocated cache at %p \n",tempC);
	//kmem_cache_info(tempC);
	//Allocation test objects
		/*
	printf("Allocation of objects without constructors\n");
	for (size_t i = 0; i < 100; i++)
	{
		printf("Allocated object at:%p\n", kmem_cache_alloc(tempC));
	}
		*/
	
	//Deletion Test
	/*
	printf("Allocation of objects without constructors\n");
	for (size_t i = 0; i < 23; i++)
	{
		printf("Allocated object at:%p\n",kmem_cache_alloc(tempC));
	}
	void* obj1 = kmem_cache_alloc(tempC);
	printf("!!Allocated object at:%p\n", obj1);
	kmem_cache_free(tempC, obj1);
	printf("Freed the obj\n");
	for (size_t i = 0; i < 40; i++)
	{
		printf("Allocated object at:%p\n", kmem_cache_alloc(tempC));
	}
	void* obj2 = kmem_cache_alloc(tempC);
	printf("!!Allocated object at:%p\n",obj2);
	kmem_cache_free(tempC, obj2);
	printf("Freed the obj\n");
	*/

	//kmalloc,kfree and kmem_cache_destroy tests
	/*
	struct Cache* tempM = kmem_cache_create("size-7", 128, NULL, NULL);
	printf("Allocated cache at %p \n", tempM);
	void** Varray = calloc(10, sizeof(void*));
	for (int  i = 0; i < 10; i++)
	{
		Varray[i] = kmalloc(128);
		printf("Allocated a buffer starting at:%p\n", Varray[i]);
	}
	for (int i = 0; i < 10; i++)
	{
		kfree(Varray[i]);
		printf("Deallocated a buffer starting at:%p\n", Varray[i]);
	}
	struct Cache* tempM2 = kmem_cache_create("size-8", 256, NULL, NULL);
	printf("Allocated cache at %p \n", tempM2);
	for (int i = 0; i < 10; i++)
	{
		Varray[i] = kmalloc(128);
		printf("Allocated a buffer starting at:%p\n", Varray[i]);
	}
	kfree(Varray[2]);
	printf("Deallocated a buffer starting at:%p\n", Varray[2]);
	for (int i = 0; i < 10; i++)
	{
		Varray[i] = kmalloc(128);
		printf("Allocated a buffer starting at:%p\n", Varray[i]);
	}
	for (int i = 0; i < 20; i++)
	{
		//Varray[i] = kmalloc(128);
		printf("Allocated a buffer starting at:%p\n", kmalloc(128));
	}
	free(Varray);
	
	//kmem_cache destroy test
	kmem_cache_destroy(tempM2);
	kmem_cache_destroy(tempM);
	*/

	//kmem_cache_info(tempC);
	//kmem_cache_destroy(tempC);
	//free(tempSpace);
	//printf("%p", globalBuddy);
//}
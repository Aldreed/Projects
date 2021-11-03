#ifndef _GEv_h_
#define _GEv_h_
class IVTEntry;
class GEv{
friend class IVTEntry;
friend class Event;


static IVTEntry* find(int i);
static void add(IVTEntry* i);

static IVTEntry* niz[256];
};
#endif

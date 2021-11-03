import Poruka from "./Poruka";

export default class Konverzacija{
    IdKon:number;
    IdNek:number;
    naziv:string;
    vlasnik:string;
    kupac:string;
    status:string;
    poslednjaPoruka:Date;
    poruke:Poruka[];
    vlasnikCur:number;
    kupacCur:number;
    Cur:number;
}
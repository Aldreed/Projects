/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

/**
 *
 * @author Bogdan
 */
class Paket {
    int Id;
    double Tezina;
    int ToXCord;
    int ToYCord;
    int IdGrad = -1;    
    
    public Paket(int i,double t,int x,int y){
        Id = i;
        Tezina = t;
        ToXCord = x;
        ToYCord = y;
    }
}

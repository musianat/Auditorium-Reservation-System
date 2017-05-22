package LinkList;

import java.util.*;

/**
 * Created by nathan on 4/15/2017.

 */
public class CustomerOrder { //holds tickets
    ArrayList<Ticket> seats;
    int aud;
    int numAdult;
    int numSenior;
    int numChild;

    double cost;
    public CustomerOrder(){
        this(0,0,0,0,null);
    }
    public CustomerOrder(int aud,int numAdult,int numSenior,int numChild,ArrayList<Ticket> seats ){
        this.aud=aud;
        this.seats=seats;
        this.numAdult=numAdult;
        this.numChild=numChild;
        this.numSenior=numSenior;


    }
    public void removeTicket(int i){
        Ticket curTicket=seats.get(i);
        if(curTicket.getType().equals("Adult")){
            numAdult--;
        }
        else if(curTicket.getType().equals("Senior")){
            numSenior--;
        }
        else if(curTicket.getType().equals("Child")){
            numChild--;
        }
        seats.remove(i);
    }

    public void addTickets(ArrayList<Ticket> newSeats){
        Ticket newTicket;
        for(int i=0;i<newSeats.size();i++){ //adds correct num of tickets
            newTicket=newSeats.get(i);
            if(newTicket.getType().equals("Adult")){
                numAdult++;
            }
            else if(newTicket.getType().equals("Senior")){
                numSenior++;
            }
            else if(newTicket.getType().equals("Child")){
                numChild++;
            }
            seats.add(newTicket);
        }
    }

    public int getNumTickets(){
    return (numAdult+numChild+numSenior);
    }


    public ArrayList<Ticket> getSeats() {
        return seats;
    }

    public void addTicket(Ticket t){

    }
    public void viewTickets(){ //generates ticket format
    for(int i=0;i<seats.size();i++){
        System.out.println((i+1)+"\t"+ seats.get(i));
    }

    }
    public double getCost(){ //dynamically calculates cost
        return ((10*numAdult) + (7.5*numSenior) + (5.25*numChild));
    }

    public int getAud() {
        return aud;
    }

    public int getNumAdult() {
        return numAdult;
    }

    public int getNumSenior() {
        return numSenior;
    }

    public int getNumChild() {
        return numChild;
    }

    public String getTickets(){ //genrates alterantive format
        String line= "Aud:"+aud+" #A:"+numAdult+" #S:"+numSenior+" #C:"+numChild+"\tSeats Reserved: ";
        for(int i=0;i<seats.size();i++){
            line+=(seats.get(i).getPlacement()+"; ");
        }
        return line;
    }

    @Override
    public String toString() { //current foramt used
        String line= "Aud:"+aud+" #A:"+numAdult+" #S:"+numSenior+" #C:"+numChild+"\tSeats Reserved: ";
        for(int i=0;i<seats.size();i++){
            line+=(seats.get(i)+"; ");
        }
        return line;
    }
}

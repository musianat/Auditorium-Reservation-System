package LinkList;

/**
 * Created by nathan on 4/15/2017.

 */
public class Ticket { //hold ticket information
    String type;
    int row;
    int seat;
    public Ticket(){
        this("",0,0);
    }
    public Ticket(String type, int row,int seat){
        this.type=type;
        this.row=row;
        this.seat=seat;
    }

    public int getSeat() {
        return seat;
    }

    public int getRow() {
        return row;
    }

    public String getType() {
        return type;
    }

    public String getPlacement(){
        return "(R:"+(row+1)+",S:"+(seat+1)+")";
    }

    @Override
    public String toString() {
        return "("+type+",R:"+(row+1)+",S:"+(seat+1)+")";
    }
}

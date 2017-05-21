package LinkList;

/**
 * Created by nathan on 3/22/2017.
 * Nathan Musial
 * ntm160030
 */
public class BaseNode {
    int row;
    int seat;
        public BaseNode(){
            this(0,0);
        }
        public BaseNode(int row, int seat){
            this.row=row;
            this.seat=seat;
        }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}

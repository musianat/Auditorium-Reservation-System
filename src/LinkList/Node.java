package LinkList;

/**
 * Created by nathan on 3/19/2017.
 * Nathan Musial
 * ntm160030
 */
//double link node
public class Node extends BaseNode {
    public Node next;
    public Node prev;


    public Node(){
        this(0,0);
    }

    public Node(int row, int seat){
        super(row,seat);
        next=null;
        prev=null;

    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


}

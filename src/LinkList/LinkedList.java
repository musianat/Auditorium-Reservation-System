package LinkList;

/**
 * Created by nathan on 3/19/2017.
 * Nathan Musial
 * ntm160030
 */
public class LinkedList {

    private Node head;
    private  Node tail;
    private int row;
    private int col;
    public LinkedList(){
        this(0,0);
    }
    public LinkedList(int row, int col){
        head=null;
        tail=null;
        this.row=row;
        this.col=col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLength(){
        Node cur=head;
        int length=1;
        while(cur.next!=null){
            cur=cur.next;
            length++;
        }
        return length;
    }
    public Node getHead(){
        return head;
    }
    public void setHead(Node head){
        this.head=head;
    }

    public void addNode(Node n){
        if(head==null){
            head=n;
        }
       else if(n.row < head.row || (n.seat < head.seat && n.row <=head.row)) {
            n.next = head;
            head.prev = n;
            head = n;
        }
        else{
            Node cur=head;
            while (cur.next != null && (cur.next.row < n.row || cur.next.seat < n.seat && cur.next.row <=n.row))
                cur=cur.next;
            n.next=cur.next;
            n.prev=cur;
            cur.next=n;
        }
    }
    public void addNode(int row,int seat){
        Node n = new Node(row,seat);
        addNode(n);
    }
    public Node deleteNode(int row, int seat){
       if(head == null){
           return head;
       }
       else if(head.row ==row && head.seat==seat){
           Node hold=head;
           head=head.next;
           head.prev=null;
           hold.next=null;
           return head;
       }
       else
       {
           Node cur =head;
           while(cur.next!=null&& cur.next.row !=row )
               cur=cur.next;
           while(cur.next!=null &&  cur.next.seat!=seat)
                cur=cur.next;
           if(cur.next == null && cur.row!=row && cur.seat!=seat)
               return null;
           else{
               Node hold=cur.next;
               cur.next=cur.next.next;
               if(cur.next!=null)
                   cur.next.prev = cur;//not sure

               hold.next=null;
               hold.prev=null;
               return hold;

           }

       }
    }

    public Node SearchForSeat(int row,int seat){
        Node cur=head;
        while(cur.next!=null){
            if(cur.row==row&&cur.seat==seat)
                return cur;
            cur=cur.next;
            if(cur.row==row&&cur.seat==seat)
                return cur;
        }
        return null;
    }

    @Override
    public String toString() {
        String result="";
        Node cur=head;
        int row=cur.row;
        result+=row+":\t";
        while(cur.next !=null){
            result+=cur.seat+"\t";
            cur=cur.next;
            if(cur.row!=row){
                result+="\n"+cur.row+":\t";
                row=cur.row;
            }
        }
        result+=cur.seat+"\n";
        return result;
    }


}

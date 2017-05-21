import LinkList.LinkedList;
import LinkList.Node;
import LinkList.*;

import java.io.*;
import java.util.*;
/**
 * Created by nathan on 4/30/2017.
 *Nathan Musial
 * ntm160030
 * project 5
 * 388-442 recursive function
 * 161-163 recursive function call
 */


public class Main {
    //global variables so that some functions can be simpler
    public static CustomerOrder orderTemp;
    public static LinkedList res1;
    public static LinkedList res2;
    public static LinkedList res3;
    public static LinkedList open1;
    public static LinkedList open2;
    public static LinkedList open3;
    public static LinkedList[] aud;
    public static HashMap userMap;

    public static void main(String[] args) throws IOException {

        //files
      //list to store auditoriums
        //change these files to change input files

        File f1 = new File("A1.txt");
        File f2 = new File("A2.txt");
        File f3 = new File("A3.txt");
        //to make sure that files are overwritten make sure the in and out file match
        File out1 = new File("A1.txt");
        File out2 = new File("A2.txt");
        File out3 = new File("A3.txt");

        //read in auditoriums
        aud = ReadInAuditorium(f1);
         res1 = aud[0]; //writes to auditorium files
         open1 = aud[1];
        aud = ReadInAuditorium(f2);
         res2 = aud[0];
         open2 = aud[1];
        aud = ReadInAuditorium(f3);
        res3 = aud[0];
         open3 = aud[1];

        File users= new File("userdb.dat");
        //HashMap userMap;
        userMap=readInCustomers(users);

        Scanner in = new Scanner(System.in); // for selecting which aud
        boolean run=true;

        Customer curCustomer=new Customer();
        while(run) {

            String username="";
            String password;
            boolean validPassword=false;

            while (!validPassword) {
                boolean validUsername = false;
                while (!validUsername) {
                    System.out.print("enter your username: ");
                    username = in.next();
                    if (userMap.get(username) != null) { //loops for valid username
                        curCustomer = (Customer) userMap.get(username);
                        validUsername = true;
                    }
                }
                for (int i = 0; i < 3; i++) { //loops for valid passowrd three times then goes back to beginning
                    System.out.print("Enter your password: ");
                    password = in.next();
                    System.out.println();
                    if (password.equals(curCustomer.getPassword())) {
                        validPassword = true;
                        break;
                    }
                }
            }
            //System.out.println(curCustomer);

            if(username.equals("admin")){ //goes to admin menu
               adminMenu(curCustomer,in);
            //   /*
                mergeList(res1,open1,out1); //recursive print function call
                mergeList(res2,open2,out2);
                mergeList(res3,open3,out3);
             //   */
               // System.out.print("implent admin menu");
                run=false;
            }
            else {
                curCustomer = customerMenu(curCustomer, in); //goes to customer menu
                userMap.remove(username);
                userMap.put(username, curCustomer);

            }
        }
    }

    public static Customer customerMenu(Customer curCustomer,Scanner in){

        boolean loggedIN = true;
        boolean valid=false;
        int input = 0;
        String s;
        while (loggedIN) {


            System.out.print("Choose an Option\n"
                    + "1 Reserve Seats\n"
                    + "2 View Orders\n"
                    + "3 Update Order\n"
                    + "4 Display Receipt\n"
                    + "5 Log Out\n");

            s = in.next();
            if (isInteger(s)) //validates input
                input = Integer.parseInt(s);
            else
                input = -1;
            switch (input) // opens auditorium user chooses or exits program
            {
                case 1: //reserve
                   reserveAuditorium(curCustomer,in);
                    break;
                case 2: // view orders
                    curCustomer.viewOrders();
                    break;
                case 3: // update orders
                    updateOrder(curCustomer,in);
                    break;
                case 4: // display receipt for orders
                    curCustomer.displayReceipt();
                    break;

                case 5: // log out user
                    loggedIN = false;
                break;
            }
        }
            return curCustomer;//returns modified customer
    }

    public static void adminMenu(Customer curCustomer,Scanner in){
        boolean loggedIN = true;
        boolean valid=false;
        int input = 0;
        String s;
        while (loggedIN) {


            System.out.print("Choose an Option\n"
                    + "1 View Auditorium\n"
                    + "2 Print Report\n"
                    + "3 Exit\n");

            s = in.next();
            if (isInteger(s)) //validates input
                input = Integer.parseInt(s);
            else
                input = -1;
            switch (input) // opens auditorium user chooses or exits program
            {
                case 1: //reserve
                    valid = false; //input validation
                    while (!valid) {
                        System.out.print("Which Auditorium\n"
                                + "1 Auditorium 1\n"
                                + "2 Auditorium 2\n"
                                + "3 Auditorium 3\n");
                        s = in.next();
                        if (isInteger(s))
                            input = Integer.parseInt(s);
                        else
                            input = -1;
                        switch (input) // opens auditorium user chooses or exits program
                        {
                            case 1:
                                // System.out.println(res1.toString() + "\n" + open1.toString());
                                printToScreen(res1, open1);
                                valid = true;
                                break;
                            case 2:
                                printToScreen(res2, open2);
                                valid = true;
                                break;
                            case 3:
                                printToScreen(res3, open3);
                                valid = true;
                                break;
                        }
                    }

                    break;


                case 2: // display report for admin
                    adminReport();
                    break;
                case 3: // logs admin out then writes to file and exits program
                    loggedIN = false;
            }
        }

       // return curCustomer;//returns modified customer
    }

    public static void adminReport(){
        int a1Res = res1.getLength(); //finds # of elements in each open and reserved aud
        int a1Open = open1.getLength();

        int a2Res = res2.getLength();
        int a2Open = open2.getLength();

        int a3Res = res3.getLength();
        int a3Open = open3.getLength();

        //finds number of each ticket in each auditorium and totals them
        int a1Adult=getNumType(1,"Adult");
        int a2Adult=getNumType(2,"Adult");
        int a3Adult=getNumType(3,"Adult");
        int totalAdult=a1Adult+a2Adult+a3Adult;

        int a1Senior=getNumType(1,"Senior");
        int a2Senior=getNumType(2,"Senior");
        int a3Senior=getNumType(3,"Senior");
        int totalSenior=a1Senior+a2Senior+a3Senior;

        int a1Child=getNumType(1,"Child");
        int a2Child=getNumType(2,"Child");
        int a3Child=getNumType(3,"Child");
        int totalChild=a1Child+a2Child+a3Child;

        //calutltes total sales in the session
        double a1TotalSales=10*a1Adult+7.5*a1Senior+5.25*a1Child;
        double a2TotalSales=10*a2Adult+7.5*a2Senior+5.25*a2Child;
        double a3TotalSales=10*a3Adult+7.5*a3Senior+5.25*a3Child;


        //prints formatted report
        System.out.println("Aud\t\t#Open\t#Res\t#Adult\t#Senior\t#child\t#Ticket Sales"); // prints out formatted report

        System.out.println("A1\t\t" + a1Open + "\t\t" + a1Res + "\t\t" + a1Adult + "\t\t" +a1Senior + "\t\t" +a1Child + "\t\t" +a1TotalSales );
        System.out.println("A2\t\t" + a2Open + "\t\t" + a2Res + "\t\t" + a2Adult + "\t\t" +a2Senior + "\t\t" +a2Child + "\t\t" +a2TotalSales);
        System.out.println("A3\t\t" + a3Open + "\t\t" + a3Res + "\t\t" + a3Adult + "\t\t" +a3Senior + "\t\t" +a3Child + "\t\t" +a3TotalSales);
        System.out.println(
                "T\t\t" + (a1Res + a2Res + a3Res) + "\t\t" + (a1Open + a2Open + a3Open) + "\t\t" + totalAdult + "\t\t" +totalSenior + "\t\t" +totalChild + "\t\t"+(a1TotalSales+a2TotalSales+a3TotalSales));

    }

    public static int getNumType(int aud,String type){
        int numTickets=0;
        int curAud;
        Set<String> keys=userMap.keySet();
        for(String key :keys){

            Customer curCutomer=(Customer)userMap.get(key); //gets list of keys
            ArrayList<CustomerOrder> orders=curCutomer.getOrders();

            for(int i=0;i<orders.size();i++){ //loops thorough every key

                CustomerOrder curOrder=orders.get(i);
                curAud=curOrder.getAud();

                if(curAud==aud){ //if matching aud

                    if(type.equals("Adult")) //if matching type
                        numTickets+=curOrder.getNumAdult(); //add total num tickets
                    else if(type.equals("Senior"))
                        numTickets+=curOrder.getNumSenior();
                    else if(type.equals("Child"))
                        numTickets+=curOrder.getNumChild();
                }
            }
        }
        return numTickets;
    }

    public static void updateOrder(Customer curCustomer, Scanner in) {

        boolean run = true;
        boolean valid = false;
        int input = 0;
        String s;
        while (run) {


            System.out.print("Choose an Option\n"
                    + "1 Add tickets to order\n"
                    + "2 Delete tickets from order\n"
                    + "3 Cancel Order\n");

            s = in.next();
            if (isInteger(s)) //validates input
                input = Integer.parseInt(s);
            else
                input = -1;
            switch (input) // opens auditorium user chooses or exits program
            {
                case 1: //Add Tickets
                    valid = false; //input validation
                    curCustomer.viewOrders();
                    while (!valid) {
                        System.out.print("which order?");

                        s = in.next();
                        if (isInteger(s)) {
                            input = Integer.parseInt(s) - 1; //for array
                            valid=true;
                        }
                        else
                            input = -1;
                        if(input!=-1 && input>=0 &&input < curCustomer.getOrders().size())
                            {
                                CustomerOrder curOrder=curCustomer.getOrders().get(input);
                                int curAud=curOrder.getAud();
                                orderTemp=null;//just to be safe

                                if(curAud==1){
                                    printToScreen(res1, open1); //prints aud 1
                                    reserveSeats(1,res1,open1,in);
                                }

                                else if(curAud==2){
                                    printToScreen(res2, open2); //prints aud 2
                                    reserveSeats(2,res2,open2,in);
                                }

                                else if(curAud==3){
                                    printToScreen(res3, open3); //prints aud 3
                                    reserveSeats(3,res3,open3,in);
                                }

                                if (orderTemp != null) { //checks if orders were added
                                    // System.out.println(orderTemp); //add order to customer
                                    curOrder.addTickets(orderTemp.getSeats());
                                }
                                orderTemp = null;
                            }
                    }
                    run = false;
                    break;

                case 2: // Delete tickets
                    valid = false; //input validation
                    curCustomer.viewOrders();
                    while (!valid) {
                        System.out.print("which order?");
                        int orderNum;
                        s = in.next();
                        if (isInteger(s)) {
                            orderNum = Integer.parseInt(s) - 1; //for array
                            valid=true;
                        }
                        else
                            orderNum = -1;

                        if(orderNum!=-1 && orderNum>=0 &&orderNum <= curCustomer.getOrders().size()) { //checks if everything is good

                            CustomerOrder curOrder = curCustomer.getOrders().get(orderNum);
                            int curAud = curOrder.getAud();

                            ArrayList<Ticket> seats = curOrder.getSeats();
                            //curCustomer.getOrders().remove(orderNum);//remove order from customer

                            int seatNum = -1;
                            int rowNum = -1;
                            boolean deleting = true;
                            int ticketNum=-1;
                            while (deleting) {
                                System.out.println("Which Ticket ot delete or Exit");
                                curOrder.viewTickets();
                                int numTickets = curOrder.getNumTickets();
                                int exitNum = numTickets+1; //not sure
                                System.out.println(exitNum + "\tExit");
                                s = in.next();
                                if (isInteger(s)) {
                                    ticketNum = Integer.parseInt(s) - 1; //for array
                                    valid = true;
                                }
                                System.out.println(numTickets);
                                if(ticketNum+1==exitNum){
                                    deleting=false;
                                }
                                else if( ticketNum >= 0 && ticketNum <= numTickets){
                                    Ticket curTicket=seats.get(ticketNum);
                                    curOrder.removeTicket(ticketNum);
                                    seatNum=curTicket.getSeat();
                                    rowNum=curTicket.getRow();

                                     deleteTicket(curAud,rowNum,seatNum);
                                    System.out.println("Ticket "+(ticketNum+1)+" Deleted");

                                    if(curOrder.getSeats().size()==0){ //if there are no tickets then delete
                                        curCustomer.getOrders().remove(orderNum);
                                        System.out.println("Order:"+(orderNum+1)+" Deleted ");
                                        deleting=false;
                                        run=false;
                                        break;

                                    }
                                }
                            }
                        }
                    }

                    run = false;
                    break;
                case 3: // Cancel order
                    valid = false; //input validation
                    curCustomer.viewOrders();
                    while (!valid) {
                        System.out.print("which order?");

                        s = in.next();
                        if (isInteger(s)) {
                            input = Integer.parseInt(s) - 1; //for array
                            valid=true;
                        }
                        else
                            input = -1;
                        if(input!=-1 && input>=0 &&input < curCustomer.getOrders().size()){
                            CustomerOrder curOrder=curCustomer.getOrders().get(input);
                            int curAud=curOrder.getAud();

                            ArrayList<Ticket> seats=curOrder.getSeats();
                            curCustomer.getOrders().remove(input);//remove order from customer

                            int seatNum=-1;
                            int rowNum=-1;
                            for(int i=0;i<seats.size();i++){ //removes tickets from auditorium
                                rowNum=seats.get(i).getRow();
                                seatNum=seats.get(i).getRow();
                                deleteTicket(curAud,rowNum,seatNum);
                            }
                            System.out.println("0rder:" +(input+1)+" Deleted");
                        }

                    }
                    run = false;
            }
        }
       // return curCustomer;
    }

    public static void deleteTicket(int aud,int rowNum,int seatNum){
        if(aud==1) {
            open1.addNode(rowNum, seatNum); // adds and deletes seats
            res1.deleteNode(rowNum, seatNum);
        }
        if(aud==2){
            open2.addNode(rowNum, seatNum); // adds and deletes seats
            res2.deleteNode(rowNum, seatNum);
        }
        if(aud==3){
            open3.addNode(rowNum, seatNum); // adds and deletes seats
            res3.deleteNode(rowNum, seatNum);
        }

    }

    public static void addTickets(int aud,int rowNum, int seatNum){

        if(aud==1) {
            res1.addNode(rowNum, seatNum); // adds and deletes seats
            open1.deleteNode(rowNum, seatNum);
        }
        if(aud==2){
            res2.addNode(rowNum, seatNum); // adds and deletes seats
            open2.deleteNode(rowNum, seatNum);
        }
        if(aud==3){
            res3.addNode(rowNum, seatNum); // adds and deletes seats
            open3.deleteNode(rowNum, seatNum);
        }
    }

    public static LinkedList[] ReadInAuditorium(File file) throws IOException{

        LinkedList[] arr = new LinkedList[2];
        int rows=0;
        int seats=0;

        Scanner in = new Scanner(file);

        String fRow= in.nextLine(); //stores first row of the auditorium
        rows++;
        seats= fRow.length(); // calculates num of seats from first row

        while(in.hasNext()){
            rows++; // counting number of rows including the first one
            in.nextLine();
        }
        in.close();

        LinkedList res= new LinkedList(rows,seats);
        LinkedList open = new LinkedList(rows,seats);

        Scanner chopper = new Scanner(file);
        String buffer;
        char c;
        Node temp;
        for(int i=0;i<rows;i++){
            buffer=chopper.nextLine(); //get line of file
            for(int j=0;j<seats;j++){
               c= buffer.charAt(j); //stores character of the file
                temp=new Node(i,j);
               if(c=='.')
                   res.addNode(temp);
               else
                   open.addNode(temp);

            }
        }
        arr[0]=res;
        arr[1]=open;
        return arr;
    }

    public static void reserveAuditorium(Customer curCustomer,Scanner in){
        boolean valid = false; //input validation
        String s;
        int input;
        while (!valid) {
            System.out.print("Which Auditorium\n"
                    + "1 Auditorium 1\n"
                    + "2 Auditorium 2\n"
                    + "3 Auditorium 3\n");
            s = in.next();
            if (isInteger(s))
                input = Integer.parseInt(s);
            else
                input = -1;
            switch (input) // opens auditorium user chooses or exits program
            {
                case 1:
                    valid = true;
                    printToScreen(res1, open1); //prints aud
                     reserveSeats(1, res1, open1, in); //reserves seat
                  //  res1 = aud[0];
                   // open1 = aud[1];

                    if (orderTemp != null) {
                        // System.out.println(orderTemp); //add order to customer
                        curCustomer.addOrder(orderTemp);
                    }
                    orderTemp = null;
                    curCustomer.viewOrders();
                    printToScreen(res1, open1); //prints aud

                    break;
                case 2:
                    valid = true;
                    printToScreen(res2, open2);
                     reserveSeats(2, res2, open2, in);
                    //res2 = aud[0];
                    //open2 = aud[1];

                    if (orderTemp != null) {
                        // System.out.println(orderTemp); //add order to customer
                        curCustomer.addOrder(orderTemp);
                    }
                    orderTemp = null;
                    curCustomer.viewOrders();

                    printToScreen(res2, open2);
                    break;
                case 3:
                    valid = true;
                    printToScreen(res3, open3);
                     reserveSeats(3, res3, open3, in);
                    //res3 = aud[0];
                    //open3 = aud[1];

                    if (orderTemp != null) {
                        // System.out.println(orderTemp); //add order to customer
                        curCustomer.addOrder(orderTemp);
                    }
                    orderTemp = null;
                    curCustomer.viewOrders();

                    printToScreen(res3, open3);
            }
        }
        //return curCustomer;
    }
    public static void reserveSeats(int aud,LinkedList res, LinkedList open, Scanner in){

        int rowNum=-1;  // row number user selects
        int seatNum=-1;  //seat number user selects

        int col= res.getCol();
        int row= res.getRow();

        ArrayList<Ticket> order= new ArrayList<Ticket>();
        int numAdult=0;
        int numSenior=0;
        int numChild=0;
        int numTickets=0;

        numAdult=getValidTickets("Adult",in); //collects ticket num of each type
        numSenior=getValidTickets("Senior",in);
        numChild=getValidTickets("Child",in);
        //gets seats from user
        getValidSeats(order,"Adult",numAdult,row,col,in); //gets valid (within auditorium) seats for each type
        getValidSeats(order,"Senior",numSenior,row,col,in);
        getValidSeats(order,"Child",numChild,row,col,in);

        numTickets=numAdult+numSenior+numChild;

        boolean openSeats=true; //searching that seats are available
            for (int i = 0; i < numTickets; i++) {
                rowNum=order.get(i).getRow();
                seatNum=order.get(i).getSeat();
                openSeats=(open.SearchForSeat(rowNum,seatNum) != null)&&openSeats; //checks if all seats are available
            }
        if(openSeats) {
            for (int i = 0; i < numTickets; i++) { //reserves seats
                rowNum=order.get(i).getRow();
                seatNum=order.get(i).getSeat();
                System.out.println(rowNum+"\t"+seatNum);
                addTickets(aud,rowNum,seatNum);
            }
            System.out.println("Seats reserved");
            orderTemp=new CustomerOrder(aud,numAdult,numSenior,numChild,order); //sends order to outside function
            return;
        }
        else{ //checks for seats
            if(findBestSeats(aud,open,res,row,col,numTickets,numAdult,numSenior,numChild,in))
                return;
            else
                System.out.println("Your selection was not available"); //if can't find best seats
                return;
            }
    }

    public static int getValidTickets(String type,Scanner in){
        int numTickets=0;
        boolean valid=false;
        while(!valid) {
            System.out.println(" Enter number of "+type+" tickets: ");
            String line = in.next();
            if(isInteger(line)) { //validates input
                numTickets = Integer.parseInt(line);
                if (numTickets >= 0)
                    valid = true;
            }
        }
        return numTickets;
    }

    public static void getValidSeats(ArrayList<Ticket> order,String type,int numTickets,int row,int col,Scanner in){
        for(int i=0;i<numTickets;i++){
            int curRow=getValidRow(row,in);
            int curSeat=getValidSeat(col,in);
            Ticket curTicket=new Ticket(type,curRow,curSeat);
            order.add(curTicket);
        }
    }

    public static boolean findBestSeats(int aud,LinkedList open,LinkedList res,int row,int col, int numTickets, int numAdult,int numSenior,int numChild,Scanner in) {

        boolean openSeats = false;
        int bestSeatNum = -1;
        int bestRowNum = -1;
        double bestSeatDistance = col; //initialize to furthest possible distance
        double middleRow = (row - 1) / 2.0;
        double middle = (col - 1) / 2.0;
        int middleSeat = 0;
        if (numTickets % 2 == 0)
            middleSeat = (numTickets - 1) / 2;
        else
            middleSeat = (numTickets / 2);
        for (int cRow = 0; cRow < row; cRow++) {
            for (int i = 0; i <= col - numTickets; i++) //moves starting point
            {
                openSeats = true;
                for (int j = 0; j < numTickets && openSeats; j++) { //checks if seats are open
                    openSeats = (open.SearchForSeat(cRow, i + j) != null) && openSeats; //checks if all seats are available
                }
                if (openSeats) {
                    double colDistance = Math.abs((middle - (i + middleSeat)));// calculates distance from mid of row to first seat
                    double rowDistance = Math.abs(cRow - middleRow);
                    double distance = Math.pow(Math.pow(colDistance, 2) + Math.pow(rowDistance, 2), .5); //calculates distance from first seat to middle of auditorium
                    if (distance < bestSeatDistance) { //checks to see if seats found are closer
                        bestSeatDistance = distance;
                        bestSeatNum = i; // stores best starting seat
                        bestRowNum = cRow; //stores row of best seats

                    }
                }
            }
        }
        String approve;
        if (bestSeatNum != -1) { //

            System.out.println(
                    "Your selection was not available" +
                            "\nthe best Seats found are on row:" + (bestRowNum + 1) + " from  seats " + (bestSeatNum + 1) + " to " + (bestSeatNum + numTickets) +
                            "\nwould you like to reserve them? " +
                            "\nenter y or n");

            approve = in.next();

            if (approve.charAt(0) == 'y' || approve.charAt(0) == 'Y') //if users accepts best seats
            {
                for (int i = 0; i < numTickets; i++) {
                    addTickets(aud, bestRowNum, bestSeatNum + i);
                    //res.addNode(bestRowNum, bestSeatNum + i); // adds and deletes seats
                    //open.deleteNode(bestRowNum, bestSeatNum + i);
                }
                ArrayList<Ticket> order = new ArrayList<Ticket>();
                Ticket curTicket = null;
                for (int i = 0; i < numAdult; i++) {
                    curTicket = new Ticket("Adult", bestRowNum, (bestSeatNum + i));
                    order.add(curTicket);
                }
                for (int i = numAdult; i < numAdult + numSenior; i++) { //not sure
                    curTicket = new Ticket("Senior", bestRowNum, (bestSeatNum + i));
                    order.add(curTicket);
                }
                for (int i = numAdult + numSenior; i < numAdult + numSenior + numChild; i++) { //not sure
                    curTicket = new Ticket("Child", bestRowNum, (bestSeatNum + i));
                    order.add(curTicket);
                }
                orderTemp = new CustomerOrder(aud, numAdult, numSenior, numChild, order);
                //arr[0]=res;
                //arr[1]=open;// updates aud lists
                System.out.println("Seats reserved");
                return true;
            }
        }
        return false;
    }


    public static int getValidSeat(int col,Scanner in){
       boolean valid=false;
       int seatNum=-1;
        while(!valid) {
            System.out.println(" Enter seat number");

            String line = in.next(); //validates input
            if(isInteger(line)) {
                seatNum = Integer.parseInt(line) - 1; //minus 1 for array

                if (seatNum >= 0 && seatNum <= col)
                    valid = true;
            }

        }
        return seatNum;
    }

    public static int getValidRow(int row,Scanner in){
        boolean valid = false;
        int rowNum=-1;
        while(!valid) {
            System.out.println("Enter row number"); // ask use to select seats
            String line = in.next();
            if(isInteger(line)) {
                rowNum = Integer.parseInt(line) - 1; // minus 1 to use in array
                if (rowNum >= 0 && rowNum <= row)
                    valid = true;
            }
        }
        return rowNum;
    }

    public static HashMap readInCustomers(File users) throws IOException {
        HashMap userMap = new HashMap(); //fills hash table with customers
        Scanner in = new Scanner(users);
        String username;
        String password = "";
        String line;
        String arr[];

        Customer temp;
        while (in.hasNextLine()) {
            line = in.nextLine();
            arr = line.split(" ");
            username = arr[0];
            if (arr.length > 0) //basic input validation
                password = arr[1];
            else
                System.out.println("error with reading users at line " + line);
            temp = new Customer(username,password);
            userMap.put(username, temp);

        }
        return userMap;
    }

    public static void printToScreen(LinkedList res, LinkedList open){

        int row=res.getRow();
        int col=res.getCol();
        System.out.print(" "); // space for formatting
        for (int i = 0; i < res.getCol(); i++) {
            System.out.print(((i+1)%10)); // prints out only first digit
        }
        System.out.println();
        for (int i = 0; i < row; i++) {
            System.out.print((i + 1)); // prints out array index+1
            for (int j = 0; j < col; j++) {

                if (open.SearchForSeat(i,j) != null)
                    System.out.print("#");
                else if(res.SearchForSeat(i,j)!=null)
                    System.out.print(".");
            }
            System.out.println();
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    public static LinkedList mergeList(LinkedList res,LinkedList open,File outFile)throws IOException{
        PrintWriter out= new PrintWriter(outFile); //write to file
        LinkedList combined=new LinkedList();
        Node head;
        int row=0;
        head=merge(res.getHead(),open.getHead(),row,out); //calls recursive print function
        combined.setHead(head);
        out.close();
        return combined; //returns merged link list

    }

    public static Node merge(Node res, Node open, int row,PrintWriter out) { //recursive print function
        Node mergedList = null;
        if(res==null &&open!=null){ //if one of the lists is empty

            if(row<open.getRow())
                out.println();
            row=open.getRow();

            out.print("#");
            merge(res,open.next,row,out);
        }
        if(res!=null &&open==null){

            if(row<res.getRow())
                out.println();
            row=res.getRow();

            out.print(".");
            merge(res.next,open,row,out);
        }
        if(res == null) {
            return open;
        }
        if(open == null) {
            return res;
        }
        if(res.getRow() < open.getRow() || res.getSeat() < open.getSeat() && res.getRow() <=open.getRow()) { //if res seat is less than open
            //point to smaller element
            if(row<res.getRow()) //checks for println
                out.println();
            row=res.getRow();

            out.print(".");

            mergedList = res;
            mergedList.next = merge(res.next, open,row,out);

        } else { //res is large, so pass h
            //point to smaller element

            if(row<open.getRow()) //checks for println
                out.println();
            row=open.getRow();
            out.print("#");

            mergedList = open;
            //open is already consider
            //now process next node of open
            mergedList.next = merge(res, open.next,row,out);

        }
        return mergedList;
    }
    }
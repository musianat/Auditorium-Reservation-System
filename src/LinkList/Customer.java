package LinkList;
import java.util.*;
import java.util.AbstractList;

/**
 * Created by nathan on 4/29/2017.
 * ntm160030
 */
public class Customer { //holds orders username and password
    String username;
    String password;
    ArrayList<CustomerOrder> orders;


    public Customer(){
        this("","",new ArrayList<CustomerOrder>());
    }
    public Customer(String username, String password){
        this(username,password,new ArrayList<CustomerOrder>());
    }
    public Customer(String username,String password,ArrayList<CustomerOrder> orders){
        this.username=username;
        this.password=password;
        this.orders=orders;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<CustomerOrder> getOrders() {
        return orders;
    }

    public void viewOrders(){
        if(orders.size()>0) {
            for (int i = 0; i < orders.size(); i++) {
                System.out.println("Order:" + (i + 1) + "\t" + orders.get(i)); //+1 so that it is more readable for user
            }
        }
        else
            System.out.println("No orders");
        //if(orders.size()>0)

    }
    public void deleteOrder(int i){
        orders.remove(i);
    }
    public void addOrder(CustomerOrder order){
        orders.add(order);

    }
    public void updateOrder(){
        System.out.println("updateOrder");

    }
    public void displayReceipt(){

        if(orders.size()>0) { //displays formatted receipt
            for (int i = 0; i < orders.size(); i++) {
                CustomerOrder curTicket = orders.get(i);
                System.out.println("Order:" + (i + 1) + "\tCost:" + curTicket.getCost() + "\t" + curTicket); //+1 so that it is more readable for user
            }

            System.out.println("Total\tCost:" + getTotalCost());
        }
        else
            System.out.println("No orders");
    }
    public double getTotalCost(){ //dynamically gets total cost
        double totalCost=0;
        for(int i=0;i<orders.size();i++){
            totalCost+=orders.get(i).getCost();
        }
        return totalCost;

    }


    @Override
    public String toString() {
        return username+ " "+ password;
    }
}

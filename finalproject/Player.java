
/*
 Player class with getters and setters
 */

package finalproject;

import java.text.DecimalFormat;

/**
 *
 * @author Cortez
 */
public class Player {

    private static DecimalFormat df = new DecimalFormat("#,###,##0.00");
    private double money;
    private String Name;
    private int Stocks;
    private double Bid;
    private double LastAcceptedBid;
    private double LastTransaction;
    private boolean flag=true;
    
    public Player(String name) {

        this.Name = name;
        this.money = 2000;
        this.Stocks = 0;

    }

    public Player(String Name, double money) {

        this.Name = Name;
        this.money = money;
        this.Stocks = 0;

    }

    public double postBid(double BidAmount) {
        
        this.Bid = BidAmount;
        //this.money=this.money-BidAmount;
        return Bid;

    }
    
    public void sellStocks(double CustomerBid){
        
        this.LastTransaction = 0.9*LastAcceptedBid;
        if(Stocks==0){
            flag=false;
            System.out.println("Player has No Stocks to Sell");
        }
        else
            if(CustomerBid>=LastTransaction){
                LastAcceptedBid=CustomerBid;
                this.Stocks=this.Stocks-1;
            }
            else{
                flag=false;
                System.out.println("REJECTING BID");}
    }
    
    public boolean SoldStock(){
       
        return flag;
        
    }
    
    public void SaveLastAcceptedBid(double amountsoldFor){
        this.LastAcceptedBid=amountsoldFor;
    }

    public double getBid() {
        return Bid;
    }

    public double getLastTransaction() {
        return LastTransaction;
    }

    public double getLastAcceptedBid() {
        
        return LastAcceptedBid;
    }
    
    public String getName() {
        return Name;
    }

    public double getMoney() {
        return money;
    }

    public int BoughtStock(){
        return Stocks++;
    }
    
    public int getStocks() {
        return Stocks;
    }
    
    public String toString(){
        String information =  "Name of Player: " + getName() + "  \nBidding Amount:" + df.format(this.Bid);
        return information;
    }
}

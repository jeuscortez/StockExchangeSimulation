
/*
 * Company Class with prooper getters and setters
 */
package finalproject;

import java.text.DecimalFormat;

/**
 *
 * @author Cortez
 */
public class Company {
    
    private static DecimalFormat df = new DecimalFormat("#,###,##0.00");
    private String Name;
    private int Stocks;
    private int Share=30;
    private double Money;
    private double BidAllowance;
    private double Bid;
    private double LastAcceptedBid;
    private double LastTransaction;
    private boolean flag =true;
    
    public Company(String name){
        
        this.Name = name;
        this.Stocks=1000;
        this.Money=(this.Stocks*Share);
        this.BidAllowance= ((this.Stocks*Share)/15);
    }
    
    public double postBid(double BidAmount){
        this.Bid = BidAmount;
        //this.Money = this.Money-BidAmount;
        return Bid;
    }
    
    public void sellStocks(double CustomerBid){
        
        LastTransaction = 0.9*LastAcceptedBid;
        if(Stocks==0){
            flag=false;
            System.out.println("Company has No Stocks to Sell");
        }
        else 
         if(CustomerBid>=LastTransaction){
                LastAcceptedBid=CustomerBid;
            this.Stocks=this.Stocks-1;
         } else{
             flag=false;
             System.out.println("REJECTING BID");}           
                
    }
     
    public boolean SoldStock(){
       
        return flag;
        
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

    public double getMoney() {
        return Money;
    }

    public double getBidAllowance() {
        return BidAllowance;
    }

    public String getName() {
        return Name;
    }
     
    public int BoughtStock(){
        return Stocks++;
    }

    public int getStocks() {
        return Stocks;
    }
    
    public String toString(){
        String information =  "Name: " + getName() + " \nBidding amount:" + df.format(Bid);
        return information;
    }
    
}

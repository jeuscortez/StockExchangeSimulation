
/*
 * Bank Class with Proper Getters and Setters
 */
package finalproject;

/**
 *
 * @author Cortez
 */
public class Bank {

    private String Name;
    private String PlayersName;
    double CustomerCash;
    int customerWealth;
    int share = 30;
    Account NewAccount;
    double interest;

    public Bank(Player player) {
        
        this.PlayersName = player.getName();
        this.Name = player.getName() + "_Bank Account";
        this.CustomerCash = player.getMoney();
        this.customerWealth = player.getStocks()*share;

    }

    public Bank(Company company) {
        
        this.PlayersName = company.getName();
        this.Name = company.getName() + "_Bank Account";
        this.CustomerCash = company.getMoney();
        this.customerWealth = company.getStocks()*share;
        

    }

    public double depositMainAccount(double deposit) {

        double Deposit = deposit;
        CustomerCash = CustomerCash + Deposit;
        return CustomerCash;

    }

    public double WithDrawMainAccount(double withdraw) {
        if(CustomerCash<0){
            System.out.println("IN DEBT!!!");
          //  return CustomerCash;
        }
        //else{
        double WithDraw = withdraw;
        CustomerCash = CustomerCash - WithDraw;
        
        return CustomerCash;
        //}
        }

    public void CollectInterest(){
        this.interest = (CustomerCash*0.05);
        CustomerCash=CustomerCash-this.interest;
    }

    public double getInterest() {
        return this.interest;
    }
    
    public double getCustomerBalance() {
        return CustomerCash;
    }

    public void OpenAccount(String NAccount, double money) {

        NewAccount = new Account(NAccount, money);

    }

    public void depositOtherAccount(double amount) {

        NewAccount.deposit(amount);

    }

    public void withdrawOtherAccount(double amount) {

        NewAccount.WithDraw(amount);
    }

    public void checkOtherAccountBalance() {

        NewAccount.getAccountBalance();

    }

    public double getCustomerCash() {
        return CustomerCash;
    }

    public String getName() {
        return Name;
    }

    public String getPlayersName() {
        return PlayersName;
    }    

    public int getCustomerWealth() {
        return customerWealth;
    }
    
    
    public String toString(){
        String info = "Name of Bank: " + getName() + "Wealth: "+  getCustomerBalance();
        return info;
    }
    
    
    
    
    
}

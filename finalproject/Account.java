/*
 * Account Class incase a player or Company wants to open another account in their Bank
 */
package finalproject;

/**
 *
 * @author Cortez
 */
class Account {

    private String nameOfAccount;
    private double MBalance;

    public Account(String Name, double Amount) {
        nameOfAccount = Name;
        MBalance = Amount;

    }

    public double deposit(double deposit) {

        double Deposit = deposit;
        MBalance = MBalance + Deposit;
        return MBalance;

    }

    public double WithDraw(double withdraw) {

        double WithDraw = withdraw;
        MBalance = MBalance - WithDraw;
        return MBalance;
    }

    public double getAccountBalance() {
        return MBalance;
    }
}


/*
@author: Jesus Cortez, Final Project , CSC 295 . Professor Liudong Zuo
* 
* Program Description: 
*       +This Program emulates a Stock Exchange.
*       +The Program runs for 30 Seconds.
*       +At the end it checks which player has the most money OR the less amount of Debt
*       +Program Creates File and writes all the Successful Transactions into a text file
*       +Program also write the name of the Winner and their Balance
*       +Name of File: "StockTransactionInfo.txt"
* 
* Note: Program write into the text file in the following Format:
*    "  Last Transaction:
*       Name of the Player that bought Share
*       Bidding Amount
*       The name of the Buyers Bank
*       The New Balance of their Bank Account after their Share Purchase "
* 
* Note: Program write the name of the Winner and their Final Balance.
* 
* Note: Winner is Selected by who has the Most Money OR who owes the less Debt
* 
* Note: Lines 353 - 359, If Line 357 is un-commented then it will run but it will reject many bids
*       and it will emulate a more realistic Stock Exchange. Meaning, less Players and Companies in Debt. 
*                       
*/
package finalproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StockExchange {
    //Global variables

    //decimal formater to keep money formated
    private static DecimalFormat df = new DecimalFormat("#,###,##0.00");
    
    //The 3 Companies that will be playing
    private static Company BankOfAmerica = new Company("Bank of America");
    private static Company Chase = new Company("Chase");
    private static Company WellsFargo = new Company("Wells Fargo");
    
    //the 5 Persons that will be player
    private static Player Joe = new Player("Joe Shark");
    private static Player Wiz = new Player("Wiz Khalifa");
    private static Player Tom = new Player("Tom Brady");
    private static Player Bill = new Player("Bill Cosby");
    private static Player Kaiba = new Player("Seito Kaiba");
    
    //The 8 banks, one for each player
    private static Bank Joes = new Bank(Joe);
    private static Bank Wizs = new Bank(Wiz);
    private static Bank Toms = new Bank(Tom);
    private static Bank Bills = new Bank(Bill);
    private static Bank Kaibas = new Bank(Kaiba);
    private static Bank Americas = new Bank(BankOfAmerica);
    private static Bank Chases = new Bank(Chase);
    private static Bank Wells = new Bank(WellsFargo);
    
    //array of classes to stores and search for info
    private static Bank Banks[] = new Bank[8];
    private static Company Companies[] = new Company[3];
    private static Player Players[] = new Player[5];
    
    //timer variables to run for 30 seconds
    private static long startTime = System.currentTimeMillis();
    private static long elapsedTime = 0L;

    
    public static void main(String[] args) {

        //inserting the companies into company[] array
        Companies[0] = BankOfAmerica;
        Companies[1] = Chase;
        Companies[2] = WellsFargo;

        //inserting the players into player[] array
        Players[0] = Joe;
        Players[1] = Wiz;
        Players[2] = Tom;
        Players[3] = Bill;
        Players[4] = Kaiba;

        //inserting Banks into Banks[] array
        Banks[0] = Joes;
        Banks[1] = Wizs;
        Banks[2] = Toms;
        Banks[3] = Bills;
        Banks[4] = Kaibas;
        Banks[5] = Americas;
        Banks[6] = Chases;
        Banks[7] = Wells;

        while (true) { //this while loop will help keep running the timer and calling game Method
            elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime <= 30 * 1000) { //keeps calling the game to play
                BeginStockGame(Companies, Players, Banks);
            } else {
                PickWinner(); //after 30 seconds are reached it call the method to see Player with most money or less Debt
                System.exit(0); //Closes the program after the winner is Shown
            }
        }

    }

    public static File OpenTransactionsInfoFile() {//ths method creates file
        try {
            File StockTransactionsInfo = new File("StockTransactionInfo.txt"); //name of the file

            if (!StockTransactionsInfo.exists()) {
                StockTransactionsInfo.createNewFile();
            }
            //try and catch blocks catch exceptions if no detected file
            return StockTransactionsInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void BeginStockGame(Company[] Companies, Player[] Players, Bank[] Banks) { //method if company is seller

        Company[] Companyselling = new Company[1]; //to store the company that is selling
        Player[] Playerselling = new Player[1];   //to store the player that is selling
        
        Random rand = new Random();//creates randoms
        
        int Crandom = rand.nextInt(3); //random array index to pick seller
        int Prandom = rand.nextInt(5); //random array index to pick seller
        int randSeller = rand.nextInt(2); //random int to select if company or player will sell

        Company[] RemainingCompaniesBiding = new Company[2]; //remaining companies that will be bidding
        Player[] AllPlayersBiding = new Player[5]; //if a company is selling then all players bid
        Company[] AllCompaniesBiding = new Company[3]; //if a player is selling then all companies bid
        Player[] RemainingPlayersBiding = new Player[4]; //remaining players that will be bidding


        if (randSeller == 0) { //if random gives a zero then company will be selling

            Companyselling[0] = Companies[Crandom]; //picking a random company as seller
            System.out.println("");
            System.out.println("Seller: " + Companies[Crandom].getName()); //prints seller

            int counter = 0;
            for (int i = 0; i < 3; i++) {

                //this for loop and if condition help move the remaining companies into another array to become bidders
                if (i != Crandom && counter < 3) {

                    RemainingCompaniesBiding[counter] = Companies[i];
                    counter++;
                }

            }

            System.arraycopy(Players, 0, AllPlayersBiding, 0, 5); //this copies all the players into another array to bid

            //this calls the method for when a company is a seller
            StockMarketExchangeCompany(Companyselling[0], RemainingCompaniesBiding, AllPlayersBiding); 


        } else { //if random gives anything but a zero then a player will be selling
            Playerselling[0] = Players[Prandom]; //picking a random player as a seller
            System.out.println("");
            System.out.println("Seller: " + Players[Prandom].getName()); //prints selected player

            int counter = 0;
            for (int i = 0; i < 5; i++) {

                //moves remaing players to another array to become bidders
                if (i != Prandom && counter < 5) {

                    RemainingPlayersBiding[counter] = Players[i];
                    counter++;

                }
            }
            
            //clones all companies into another array to be bidders
            System.arraycopy(Companies, 0, AllCompaniesBiding, 0, 3); 

            //calls the method for when a player is a seller
            StockMarketExchangePlayer(Playerselling[0], AllCompaniesBiding, RemainingPlayersBiding);
        }

    }

    public static void StockMarketExchangeCompany(Company seller, Company[] CompaniesBidding, Player[] PlayersBidding) {

        //this method is called when a Company is selling
        
        //keeps track of when to chatge interest
        if (elapsedTime == 10 * 1000 || elapsedTime == 20 * 1000 || elapsedTime == 30) { 
            System.out.println("Banks Collecting Interest");
            for (int i = 0; i < 8; i++) {
                Banks[i].CollectInterest();//bank collects interest every 10 seconds
            }
        }

        Random randomness = new Random(); //creates randoms

        System.out.println("");
        System.out.println("List of Players that will bid ..."); //prints players that are bidding
        for (int j = 0; j < 5; j++) {

            System.out.println(PlayersBidding[j].getName());
        }
        System.out.println("");
        System.out.println("List of Companies that will be bidding..."); //prints Companies that are bidding
        for (int j = 0; j < 2; j++) {

            System.out.println(CompaniesBidding[j].getName());
        }
        System.out.println("");

        //this part assigns random bids for each Company based on their bid allowance
        double CBidAmount = randomness.nextInt((int) (CompaniesBidding[0].getBidAllowance()));
        CompaniesBidding[0].postBid(CBidAmount);
        double CBidAmount2 = randomness.nextInt((int) (CompaniesBidding[1].getBidAllowance()));
        CompaniesBidding[1].postBid(CBidAmount2);

        //this part assigns random bids for each Player based on their bid allowance
        double PBidAmount = randomness.nextInt((int) (PlayersBidding[0].getMoney()));
        PlayersBidding[0].postBid(PBidAmount);
        double PBidAmount2 = randomness.nextInt((int) (PlayersBidding[1].getMoney()));
        PlayersBidding[1].postBid(PBidAmount2);
        double PBidAmount3 = randomness.nextInt((int) (PlayersBidding[2].getMoney()));
        PlayersBidding[2].postBid(PBidAmount3);
        double PBidAmount4 = randomness.nextInt((int) (PlayersBidding[3].getMoney()));
        PlayersBidding[3].postBid(PBidAmount4);
        double PBidAmount5 = randomness.nextInt((int) (PlayersBidding[4].getMoney()));
        PlayersBidding[4].postBid(PBidAmount5);

        List<Double> List = new LinkedList<>(); //inserts all the bid amounts into list
        List.add(CBidAmount);
        List.add(CBidAmount2);
        List.add(PBidAmount);
        List.add(PBidAmount2);
        List.add(PBidAmount3);
        List.add(PBidAmount4);
        List.add(PBidAmount5);
        System.out.println("Unsorted Bids"); //prints all the bids
        for (int i = 0; i < 7; i++) {

            System.out.println(List.get(i));
        }

        Collections.sort(List); //sorts the bids

        double HighestBid = List.get(6); //assigns the highest bid to variable
        System.out.println("");
        System.out.println("Highest Bid " + HighestBid); //prints highest bid

        seller.sellStocks(HighestBid);//updates the stocks


        if (seller.SoldStock() == true) { //if the seller sold stock then it continues


            for (int i = 0; i < 2; i++) { //checks if the bid belongs to a Companny
                if (HighestBid == (CompaniesBidding[i].getBid())) { 

                    //if the highest bid corresponds to Company then calls the proper method
                    printandwriteCompany(CompaniesBidding[i]);
                }
            }

            for (int j = 0; j < 5; j++) { //checks if bid belongs to a player
                if (HighestBid == (PlayersBidding[j].getBid())) {

                    //if the highest bid corresponds to Player then calls the proper method
                    printandwritePlayer(PlayersBidding[j]);
                }
            }
        } else { //if seller did not sell stock then it rejects the bid
            System.out.println("REJECTED BID");
        }

    }

    public static void StockMarketExchangePlayer(Player seller, Company[] CompaniesBidding, Player[] PlayersBidding) {

        //this method is called when a Player is selling
        
        //this tracks when to charge interest
        if (elapsedTime == 10 * 1000 || elapsedTime == 20 * 1000 || elapsedTime == 30) { 
            System.out.println("Banks Collecting Interest");
            for (int i = 0; i < 8; i++) {
                Banks[i].CollectInterest(); //bank collects interest every 10 seconds
            }
        }

        Random random = new Random();
        System.out.println("");
        System.out.println("List of Players that will bid ..."); //prints the players that will bid
        for (int j = 0; j < 4; j++) {

            System.out.println(PlayersBidding[j].getName());
        }
        System.out.println("");
        System.out.println("List of Companies that will be bidding..."); //prints the companies that will bid
        for (int j = 0; j < 3; j++) {

            System.out.println(CompaniesBidding[j].getName());
        }
        System.out.println("");

        //this part assigns random number that is in range of the Companies bid allowance
        double CBidAmount = random.nextInt((int) (CompaniesBidding[0].getBidAllowance()));
        CompaniesBidding[0].postBid(CBidAmount);
        double CBidAmount2 = random.nextInt((int) (CompaniesBidding[1].getBidAllowance()));
        CompaniesBidding[1].postBid(CBidAmount2);
        double CBidAmount3 = random.nextInt((int) (CompaniesBidding[2].getBidAllowance()));
        CompaniesBidding[2].postBid(CBidAmount3);

        //this part assigns random number that is in range of the Players bid allowance
        double PBidAmount = random.nextInt((int) (PlayersBidding[0].getMoney()));
        PlayersBidding[0].postBid(PBidAmount);
        double PBidAmount2 = random.nextInt((int) (PlayersBidding[1].getMoney()));
        PlayersBidding[1].postBid(PBidAmount2);
        double PBidAmount3 = random.nextInt((int) (PlayersBidding[2].getMoney()));
        PlayersBidding[2].postBid(PBidAmount3);
        double PBidAmount4 = random.nextInt((int) (PlayersBidding[3].getMoney()));
        PlayersBidding[3].postBid(PBidAmount4);

        List<Double> Listings = new LinkedList<>(); //list used to add bids into it
        Listings.add(CBidAmount);
        Listings.add(CBidAmount2);
        Listings.add(CBidAmount3);
        Listings.add(PBidAmount);
        Listings.add(PBidAmount2);
        Listings.add(PBidAmount3);
        Listings.add(PBidAmount4);

        System.out.println("Unsorted Bids"); //prints the bids
        for (int i = 0; i < 7; i++) {

            System.out.println(Listings.get(i));
        }

        Collections.sort(Listings); //sorts the bids
    
        double TheHighestBid = Listings.get(6); //gets the highest bid from sorted list
        
        System.out.println("");
        System.out.println("Highest Bid " + TheHighestBid);
        
        //******************************************************************************************************
        
        //If the line is uncommented the program will emulate a realistic sotck exchange where many bids are rejected 
        
        //seller.sellStocks(TheHighestBid);//updates the stocks ****************************
        
        //******************************************************************************************************
        
        if (seller.SoldStock() == true) { //if the stock was sold then it proceeds to writing the info of transaction

            for (int i = 0; i < 3; i++) {

                if (TheHighestBid == CompaniesBidding[i].getBid()) {
                    //if the highest bid came from Company it calls corresponding method
                    printandwriteCompany(CompaniesBidding[i]);

                }
            }
            for (int j = 0; j < 4; j++) {
                
                if (TheHighestBid == (PlayersBidding[j].getBid())) {
                    //if the highest bud came from a Person then it calls corresponding method 
                    printandwritePlayer(PlayersBidding[j]);
                }
            }
        } else {
            System.out.println("REJECTED BID");
        }


    }

    public static void printandwriteCompany(Company AcceptedBidder) {
       
        System.out.println("Processing Company Information....");

        System.out.println(AcceptedBidder);

        Bank BuyersBank[] = new Bank[1];

        for (int k = 0; k < 8; k++) {

            //this for loop and if condition searches for the bank of the buyer
            if (Banks[k].getName().contains(AcceptedBidder.getName())) {

                System.out.println(Banks[k].getName());
                BuyersBank[0] = Banks[k];
            }
        }
        
        BuyersBank[0].WithDrawMainAccount(AcceptedBidder.getBid());
        System.out.println("New Balance " + df.format(BuyersBank[0].getCustomerBalance()));

        try { //this write the info of the successful transaction into the file
            File file = OpenTransactionsInfoFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("Last Transaction:");
            bw.newLine();
            bw.write(AcceptedBidder.toString());
            bw.newLine();
            bw.write("Banks Name: " + BuyersBank[0].getName());
            bw.newLine();
            bw.write("Banks New Balance: " + df.format(BuyersBank[0].getCustomerBalance()));
            bw.newLine();
            bw.close();

            //to confirm that it wrote into text
            System.out.println("INFORMATION SAVED TO FILE");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void printandwritePlayer(Player AcceptedBidder) {
        
        System.out.println("Processing Player Information.....");

        System.out.println(AcceptedBidder);


        Bank BuyersBank[] = new Bank[1];

        //this for loop searches for the corresponding bank of the buyer
        for (int k = 0; k < 8; k++) {

            if (Banks[k].getName().contains(AcceptedBidder.getName())) {

                System.out.println(Banks[k].getName());
                BuyersBank[0] = Banks[k];

            }
        }
        BuyersBank[0].WithDrawMainAccount(AcceptedBidder.getBid());

        System.out.println("New Balance " + df.format(BuyersBank[0].getCustomerBalance()));

        //this write info of the last successful transaction

        try {
            File file = OpenTransactionsInfoFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("Last Transaction:");
            bw.newLine();
            bw.write(AcceptedBidder.toString());
            bw.newLine();
            bw.write("Banks Name: " + BuyersBank[0].getName());
            bw.newLine();
            bw.write("Banks New Balance: " + df.format(BuyersBank[0].getCustomerBalance()));
            bw.newLine();
            bw.close();

            //to confirm that it wrote into text
            System.out.println("INFORMATION SAVED TO FILE");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void PickWinner() {

        Bank[] RichestBank = new Bank[1];
        double PlayerHighestAmount = -999999999;

        for (int i = 0; i < 8; i++) {
            
            //this for loop helps find the person who has the most money or less Debt
            
            if (Banks[i].getCustomerBalance() > PlayerHighestAmount) {
                PlayerHighestAmount = Banks[i].getCustomerBalance();
                RichestBank[0] = Banks[i];
            }
        }
        System.out.println("");
        System.out.println("THE WINNER OF THE GAME IS: " + RichestBank[0].getPlayersName()
                + " Balance: " + df.format(RichestBank[0].getCustomerBalance()));
        
        try {  //this write the winner of the game into the txt file
            File file = OpenTransactionsInfoFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("THE WINNER OF THE GAME IS: "); 
            bw.newLine();
            bw.write("Name: " + RichestBank[0].getPlayersName()
                + "  Balance: " + df.format(RichestBank[0].getCustomerBalance()));
            bw.newLine();
            bw.close();

            //to confirm that it wrote into text
            System.out.println("INFORMATION SAVED TO FILE");

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
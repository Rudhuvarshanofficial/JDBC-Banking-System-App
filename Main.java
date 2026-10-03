import java.util.Scanner;
class Main{
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        int userOpt=0;
        int acc=0;
        System.out.print("Enter your acc number : ");
        acc = sc.nextInt();
        //System.out.println();
        Bank bank = new Bank();

        try{
        if(bank.isExits(acc)){
            System.out.print("Enter your password : ");
            int pass = sc.nextInt();
            if(bank.checkPass(pass,acc)){
                String name = bank.getName(acc);
                System.out.println();
                System.out.println("----Gus Fring Bank Prt Lmt-----");
                System.out.println();
                System.out.println("Welcome back , " + name);
                System.out.println();
                System.out.println("1. Balance ");
                System.out.println("2. Withdrawl ");
                System.out.println("3. Mini statement ");
                System.out.println("4. Account transfer  ");
                System.out.println("5. Deposit ");
                System.out.println("6. Exit ");
                System.out.println();
                System.out.print("Enter the service number : ");
                userOpt = sc.nextInt();
            }
            else System.out.println("Incorrect password");
        }
        else System.out.println("Account not found");

        
        if(userOpt==1){
            bank.displayBalance(acc);
        }
        else if(userOpt==2){
            System.out.println();
            System.out.print("Enter the amout you what to withdrawl : ");
            int amt = sc.nextInt();
            if(bank.checkWithdrawl(amt,acc)){
                bank.withdrawl(amt,acc);
            }
            else System.out.println("Insufficient balance");
        }
        else if(userOpt==3){
            TransactionsDAO.getMini(acc);
        }
        else if(userOpt==4){
            System.out.print("Acc no to transfer : ");
            int accTo = sc.nextInt();
            int amtTo=0;
            if(bank.isExits(accTo)){
                System.out.print("Enter the amt to transfer : ");
                //sc.nextInt();
                amtTo=sc.nextInt();
                if((bank.checkBalance(acc,amtTo))){
                    int balance = bank.getBalance(acc);
                    bank.accTransfer(acc,accTo,amtTo,balance);
                }
                else System.out.println("Insufficient balance ");
            }
            else System.out.println("Account not found!!");
        }
        else if(userOpt==5){
            System.out.print("Enter the amount you want to deposit : ");
            int amt = sc.nextInt();
            bank.deposit(acc,amt);
        }
        else if(userOpt==6){
            System.out.println("Transaction cancelled ");
        }
    }
    catch(Exception e){
        System.out.println("Sorry try again after some time" + e);
    }
    finally{
        sc.close();
    }
    
    }
}
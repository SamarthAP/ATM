package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.BankEmployee;
import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserOptionsMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Today's date is: "+ Main.atm.getDateTime().format(formatter) + "\n");
        System.out.println("======= User Panel ========");
        boolean isEmployee = Main.atm.getCurUser() instanceof BankEmployee;
        System.out.println("Options:");
        if (!isEmployee) {
            System.out.println("A. 'view accounts'\nB. 'withdraw money'\nC. 'deposit money' " +
                    "\nD. 'transfer money' \nE. 'pay bills' \nF. 'request a loan' \nG. 'pay back loans' \n" +
                    "H. 'get an email account summary' \nI. 'add email' \nJ. 'view investment portfolio' \nX. 'logout'");
        } else {
            System.out.println("A. 'view accounts'\nB. 'withdraw money'\nC. 'deposit money' " +
                    "\nD. 'transfer money' \nE. 'pay bills' \nF. 'request a loan' \nG. 'pay back loans' \n" +
                    "H. 'get an email account summary' \nI. 'add email' \nJ. 'view investment portfolio' \nK. 'view employee options'" +
                    "\nX. 'logout");
        }
        System.out.println("What would you like to do?");
        System.out.print(">");
        String input = in.nextLine();
        while (true) {
            switch (input.toLowerCase()) {
                case "view accounts":
                case "a":
                    return "AccountView";
                case "withdraw money":
                case "b":
                    return "WithdrawMoney";
                case "deposit money":
                case "c":
                    return "DepositMoney";
                case "transfer money":
                case "d":
                    return "TransferMoney";
                case "pay bills":
                case "e":
                    return "PayBills";
                case "request a loan":
                case "f":
                    return "RequestLoan";
                case "pay back loans":
                case "g":
                    return "PayLoans";
                case "get an email account summary":
                case "h":
                    return "SendEmailSummary";
                case "logout":
                case "x":
                    return "Logout";
                case "add email":
                case "i":
                    return "AddEmail";
                case "view investment portfolio":
                case "j":
                    return "InvestmentPortfolio";
                case "view employee options":
                case "k":
                    if (isEmployee) return "BankEmployeeOptions";
                    else return "UserOptions";
                default:
                    System.out.println("Invalid option, please try again.");
                    System.out.print(">");
                    input = in.nextLine();
            }
        }
    }
}

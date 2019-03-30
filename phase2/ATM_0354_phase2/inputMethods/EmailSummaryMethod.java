package ATM_0354_phase2.inputMethods;

import ATM_0354_phase2.InputMethod;
import ATM_0354_phase2.Main;
import ATM_0354_phase2.User;

import java.util.Scanner;

public class EmailSummaryMethod implements InputMethod {
    @Override
    public String run(Scanner in) {
        System.out.print("Type the email that you want the account summary sent to:\n>");
        String email = in.nextLine();
        System.out.print("Is " + email +" the correct email (yes/no)?\n>");
        String answer = in.nextLine();
        if (answer.equals("yes")) {
            System.out.println("Sending email to " + email);
            Main.atm.sendEmailSummary((User) Main.atm.getCurUser(), email);
            return "UserOptions";
        } else {
            System.out.println("Lets try again");
            return this.run(in);
        }
    }
}

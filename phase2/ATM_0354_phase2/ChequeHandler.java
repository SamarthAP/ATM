package ATM_0354_phase2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChequeHandler {

    String uri;

    public ChequeHandler() {
        this.uri = "http://localhost:1234/api/";
    }

    public void processCheques() {
        try {
            String response = this.sendGet();

            ArrayList<List> itemList = new ArrayList<>();

            Pattern p = Pattern.compile("\\{.*?}");
            Matcher m = p.matcher(response);
            while (m.find()) {
                String clean = m.group();
                clean = clean.replace("{", "");
                clean = clean.replace("}", "");
                itemList.add(Arrays.asList(clean.split(",")));
            }

            String id = "";
            String to = "";
            String from = "";
            double amount = 0.0;
            for (List list : itemList) {
                Pattern colonPattern = Pattern.compile(":.*");

                m = colonPattern.matcher(list.get(0).toString());
                if (m.find()) {
                    id = m.group();
                    id = id.replace(":", "");
                    id = id.replace("\"", "");
                }

                m = colonPattern.matcher(list.get(1).toString());
                if (m.find()) {
                    to = m.group();
                    to = to.replace(":", "");
                    to = to.replace("\"", "");
                }

                m = colonPattern.matcher(list.get(2).toString());
                if (m.find()) {
                    from = m.group();
                    from = from.replace(":", "");
                    from = from.replace("\"", "");
                }

                m = colonPattern.matcher(list.get(3).toString());
                if (m.find()) {
                    String amountString = m.group();
                    amountString = amountString.replace(":", "");
                    amountString = amountString.replace("\"", "");
                    amount = Double.parseDouble(amountString);
                }


                // send money to each account
                boolean bothUsersExist = Main.atm.usernameExists(from) &&  Main.atm.usernameExists(to);

                if (bothUsersExist) {
                    Account fromAccount = ((User) Main.atm.getUser(from)).getPrimaryAccount();
                    Account toAccount = ((User) Main.atm.getUser(to)).getPrimaryAccount();
                    BigDecimal chequeAmount = new BigDecimal(amount);

                    Transaction cheque = new Transfer(fromAccount, toAccount, chequeAmount);
                    cheque.process();
                } else {
                    System.out.println("One of the accounts does not exist, the cheque has been deleted");
                }

                // send a request to the database to delete the cheque
                sendDelete(id);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String sendGet() throws Exception {
        URL url = new URL(uri + "/all");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL: " + uri);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private void sendDelete(String id) throws Exception {
        URL url = new URL(uri + id + "/delete");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'DELETE' request to URL: " + uri);
        System.out.println("Response Code: " + responseCode);

    }
}

# CSC 207 ATM Project

Group 0354: Alex Shih, Sky Li, Stella Cai, Labib Zaman, Samarth Patel

*Note: The project was completed in two phases. The phase 2 folder holds the final product that we have made, and so the following information will pertain to that environment.

JDK 1.8 was used along with Java 8.

The UML Diagram is under design1.pdf and design2.pdf.

### Description:
Our program models an ATM, Automated Teller Machine. Our ATM shares most of the functionality with an ATM you would
find at a bank. Once an account is created by the bank manager, a user can view their account(s), transfer money between
accounts they own, send and receive money, pay bills. Our ATM allows users to create various types of debt and asset accounts

#### Notes:
- In order to create an account, a user must go to view accounts to request to create an account.
- When a user is created, they are automatically set up with a chequing account.
- All storable information in the ATM is stored under Files.
- Only the Bank Manager can shut off the ATM, and thus end the program; The user can only choose to log off from their account.
- CreditCardAccount can incur up to $1000 in debt, while LineOfCreditAccount can incur up to $5000 in debt.
- Some functionality requires access to the internet, so make sure Java and NodeJS can access the network on your computer

#### Bank Manager Actions:
- create users: Creates a new user with a unique username and password
- approve account creation requests: If there is a request to open an account, the Bank Manager can choose whether
or not to approve the request to open the new account
- refill cash: Increases the number of bills in the machine.
- undo recent transactions: The Bank Manager can choose whether or not to undo the most recent transaction for a
certain user's account if that transaction is not a bill.
- process email cheques ** There are SPECIFIC requirements to use this feature, mentioned below **
- logout: Logs out of the Bank Manager's account.
- shutdown: Shuts down the ATM and the program.

#### User Actions:
- view accounts: Shows the user a list of their accounts and account information. Account information includes
account ID and account balance.
- withdraw money: Allows the user to withdraw money from an account they own, unless if the account is a credit
card account.
- deposit money: Allows the user to deposit money to any account they own.
- transfer money: Allows the user to transfer money between any two accounts they own or to another user's account,
as long as the user has created an account in the ATM.
- get an email account summary
- pay bills: Allows the user to pay a bill to a non-user's account. These transactions cannot be undo'ed.
- logout: Logs out of the current user's account.

#### Usage:
1. To run the ATM, run Main.java.
2. If it is the first time this program is being run, the ATM will assume that you are the bank manager and
will make you create an account for the BankManager, with a username and a password.
This account will have privileges other accounts will not have such as creating a bank account for a user and
un-doing transactions.
3. After the creation of the Bank Manager account, you will have to sign in using the username and password you have
just created.
4. Once signed in as the Bank Manager, there will be a list of actions that the Bank Manager can perform, which are
specified above under Bank Manager actions.
5. If a user is signed in, there will be a list of actions that the user can perform, which are specified above under
User Actions.
6. Users and BankManager will be able to logout of their account with the logout action.
7. The program will end when the BankManager performs the "shutdown" action.

## ** Requirements for `process email cheques` and how to use **

### Technologies needed:
- Node.js (version 10.15.x LTS)
- npm (this will most likely be installed with node)

### Install Required Libraries
Note: these libraries must be installed in the folder phase2/ATM_0354_phase2/EmailChequeAnalyser, so make sure to
cd into the folder then run these commands to install the libraries:

```
npm install googleapis@27 --save
npm install tesseract.js --save
npm install mongoose --save
npm install express --save
npm install body-parser --save
```

### Now that all the requirements are fulfilled, this is how you enable the feature:

- You will need two terminal windows. In each terminal, cd into phase2/ATM_0354_phase2/EmailChequeAnalyser
- in one terminal, run the following command:
node server.js
- in the other terminal, run the following command:
node analyser.js
- allow the terminals to keep running in the background

### The feature is now enabled, so let's see how to use it:

- open up example_cheque.png, located in phase2/example_cheque.png
- note how our cheque is formatted. the `To` and `From` refer to usernames, and amount is the dollar value.
- using this cheque as an example, assume that there two users, one with user1 as their username, and the other
with user2 as their username. Also assume both users have one chequing account each, and each user has $1000 in their
account
- in this scenario, lets say user1 wants to give user2 $500, so user1 writes a cheque that looks like example_cheque.png
and gives it to user2
- user2 doesn't want to go to the bank to deposit the cheque, so they take a picture of it (and the picture looks like
example_cheque.png) and sends the picture to the bank's email (csc207.bank0354@gmail.com).
*Note: when sending an email of the cheque to the bank, the only requirement is having the image as an attachment to
the email
- once user2 sends the email, there is nothing they have to do anymore.
- since analyser.js is running, it will check the bank's email EVERY ONE MINUTE for cheques that have been received by the
bank and it will analyse the cheques and store the data in an online MongoDB database
- when a bank manager logs in and selects the `process email cheques` option, any information in the database will be
processed, adding and subtracting money from the respective accounts and deleting the processed information from the
database
- now user1 will see $500 in their chequing account and user2 will see $1500 in their chequing account

** End of `process email cheques` specifications **

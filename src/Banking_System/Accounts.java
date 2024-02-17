package Banking_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long open_account(String email){
        if(!account_exist(email)){
            String open_account_query = "Insert into accounts (account_number,full_name,email,balance,security_pin) values(?,?,?,?,?)";
            scanner.nextLine();
            System.out.print("Enter Full Name : ");
            String full_name = scanner.nextLine();
            System.out.print("Enter the Initial Amount : ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security Pin : ");
            String security_pin = scanner.nextLine();

            try{
                long account_number = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(open_account_query);
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,full_name);
                preparedStatement.setString(3,email);
                preparedStatement.setDouble(4,balance);
                preparedStatement.setString(5,security_pin);
                int rowsAffected = preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    return account_number;
                }else {
                    throw new RuntimeException("Account Creation Failed...");
                }
            }catch (Exception e){
                e.getStackTrace();
            }
        }
        throw new RuntimeException("Account Already Exist.....");
    }

    public long getAccount_number(String email){
        String query = "SELECT account_number from Accounts WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account number does not exist....!");
    }

    private long generateAccountNumber(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT account_number from accounts order by account_number desc limit 1");
            if(resultSet.next()){
                long last_account_number = resultSet.getLong("account_number");
                return last_account_number;
            }else{
                return 10000100;
            }
        } catch (Exception e){
                e.getStackTrace();
        }
        return 10000100;
    }

    public boolean account_exist(String email){
        String query = "Select account_number from accounts where email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else {
                return false;
            }

        }catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }
}

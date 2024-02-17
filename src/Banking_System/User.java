package Banking_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {

    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register() {

        scanner.nextLine();
        System.out.print("Full Name : ");
        String full_name = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        if(user_exist(email)){
            System.out.println("Sorry --> User Already Exist for this Email Address...");
            return;
        }
        String register_query = "INSERT INTO USER(full_name,email,password) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int affectedRow = preparedStatement.executeUpdate();
            if(affectedRow > 0 ){
                System.out.println("Registration Successfully....!");
            }
            else{
                System.out.println("Registration Failed....!");
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public String login(){
        scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        String login_query = "Select * from user where email = ? and password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return email;
            }
            else {
                return null;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }

    public boolean user_exist(String email){
        String query = "Select * from user where email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }
}

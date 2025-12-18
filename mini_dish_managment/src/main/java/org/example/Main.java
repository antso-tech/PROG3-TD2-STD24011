package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        DataRetriever data = new DataRetriever();
        dbConnection.getConnection();
        data.findDishById();
        dbConnection.closeConnection();

    }
}
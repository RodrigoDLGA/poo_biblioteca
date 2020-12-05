package com.rodrigo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainController {
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPassword;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Login(ActionEvent event) throws IOException {

        conn = mysqlconnect.ConnectDb();
        String sql = "select * from users where username = ? and password = ?";
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtUser.getText());
                pst.setString(2, txtPassword.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"¡Bienvenido! Conectándose a la BD...");
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("Sistema de Biblioteca");
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }else{
                    JOptionPane.showMessageDialog(null,"Usuario o contraseña inválido");
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        /*
        if(txtUser.getText().equals("user") && txtPassword.getText().equals("pass")){
            System.out.println("Login Success");
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Sistema de Biblioteca");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }else{
            System.out.println("Login Failed");
        }
        */
    }

}

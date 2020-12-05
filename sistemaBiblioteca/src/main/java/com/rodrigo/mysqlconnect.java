package com.rodrigo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mysqlconnect {
    Connection conn = null;

    public static Connection ConnectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root",""); //nombre de la bd
            //JOptionPane.showMessageDialog(null,"¡Conexión establecida!");
            System.out.println("Conexión establecida");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }
    }
    public static ObservableList<Libros> getDatalibros(){
        Connection conn = ConnectDb();
        ObservableList<Libros> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from libros"); // sentencia sql
            ResultSet rs = ps.executeQuery(); //Ejecuta y guarda resultado de la sentencia

            while (rs.next()){
                list.add(new Libros(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("tema"),
                        Integer.parseInt(rs.getString("anio")),
                        Integer.parseInt(rs.getString("id")))
                );
            }
        }catch (Exception e){ }
        return list;
    }
}

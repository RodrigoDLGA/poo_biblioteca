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

    public static ObservableList<Inventarios> getDatainventarios(){
        Connection conn = ConnectDb();
        ObservableList<Inventarios> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from inventario");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Inventarios(
                        rs.getString("titulo"),
                        rs.getString("estatus"),
                        Integer.parseInt(rs.getString("id")),
                        Integer.parseInt(rs.getString("cantidad")))
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    public static ObservableList<Prestamos> getDataprestamos(){
        Connection conn = ConnectDb();
        ObservableList<Prestamos> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from prestamos");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Prestamos(
                        rs.getString("nombre_alumno"),
                        Integer.parseInt(rs.getString("idLibro")),
                        Integer.parseInt(rs.getString("matricula")))
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

}

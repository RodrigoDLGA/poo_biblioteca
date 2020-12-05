package com.rodrigo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    private ComboBox<String> estatus;

    @FXML
    private TableView<Libros> tableLibros;

    @FXML
    private TableColumn<Libros, String> col_titulo;

    @FXML
    private TableColumn<Libros, String> col_autor;

    @FXML
    private TableColumn<Libros, String> col_editorial;

    @FXML
    private TableColumn<Libros, Integer> col_anio;

    @FXML
    private TableColumn<Libros, String> col_tema;

    @FXML
    private TableColumn<Libros, Integer> col_id;

    @FXML
    private TextField txt_titulo;

    @FXML
    private TextField txt_autor;

    @FXML
    private TextField txt_editorial;

    @FXML
    private TextField txt_tema;

    @FXML
    private TextField txt_anio;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField filterField;

    ObservableList<String> list = FXCollections.observableArrayList("Disponible","No disponible","Prestado");

    public void Logout(ActionEvent event) throws IOException {
        System.out.println("Logout");
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    ObservableList<Libros> listM;
    ObservableList<Libros> dataList;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_libros(){
        conn = mysqlconnect.ConnectDb();
        String sql = "insert into libros (titulo,autor,editorial,tema,anio) values (?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_titulo.getText());
            pst.setString(2,txt_autor.getText());
            pst.setString(3,txt_editorial.getText());
            pst.setString(4,txt_tema.getText());
            pst.setString(5,txt_anio.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Se añadió el libro con éxito");
            UpdateTable();
            search_libros();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    @FXML
    void getSelected (MouseEvent event){
        index = tableLibros.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        txt_titulo.setText(col_titulo.getCellData(index).toString());
        txt_autor.setText(col_autor.getCellData(index).toString());
        txt_editorial.setText(col_editorial.getCellData(index).toString());
        txt_tema.setText(col_tema.getCellData(index).toString());
        txt_anio.setText(col_anio.getCellData(index).toString());
        txt_id.setText(col_id.getCellData(index).toString());
    }

    public void Edit(){
        try{
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_titulo.getText();
            String value2 = txt_autor.getText();
            String value3 = txt_editorial.getText();
            String value4 = txt_tema.getText();
            String value5 = txt_anio.getText();
            String value6 = txt_id.getText();

            String sql = "update libros set titulo = '"+value1+"',autor= '"+value2+"',editorial= '"+value3+"',tema= '"+value4+"',anio= '"+value5+"' where id ='"+value6+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Actualización correcta");
            UpdateTable();
            search_libros();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void Delete(){
        conn = mysqlconnect.ConnectDb();
        String sql = "delete from libros where id = ?";
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1,txt_id.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null,"¡Se borró el libro!");
                UpdateTable();
                search_libros();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    }

    public void UpdateTable(){
        col_titulo.setCellValueFactory(new PropertyValueFactory<Libros, String>("titulo"));
        col_autor.setCellValueFactory(new PropertyValueFactory<Libros, String>("autor"));
        col_editorial.setCellValueFactory(new PropertyValueFactory<Libros, String>("editorial"));
        col_tema.setCellValueFactory(new PropertyValueFactory<Libros, String>("tema"));
        col_anio.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("anio"));
        col_id.setCellValueFactory(new PropertyValueFactory<Libros, Integer>("id"));

        listM = mysqlconnect.getDatalibros();
        tableLibros.setItems(listM);
    }

    @FXML
    void search_libros() {
        col_id.setCellValueFactory(new PropertyValueFactory<Libros,Integer>("id"));
        col_titulo.setCellValueFactory(new PropertyValueFactory<Libros,String>("titulo"));
        col_autor.setCellValueFactory(new PropertyValueFactory<Libros,String>("autor"));
        col_editorial.setCellValueFactory(new PropertyValueFactory<Libros,String>("editorial"));
        col_anio.setCellValueFactory(new PropertyValueFactory<Libros,Integer>("anio"));
        col_tema.setCellValueFactory(new PropertyValueFactory<Libros,String>("tema"));

        dataList = mysqlconnect.getDatalibros();
        tableLibros.setItems(dataList);
        FilteredList<Libros> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(libro -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (libro.getTitulo().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (libro.getEditorial().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (libro.getAutor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(libro.getAnio()).indexOf(lowerCaseFilter)!=-1){
                    return true;
                } else if (libro.getTema().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (String.valueOf(libro.getId()).indexOf(lowerCaseFilter) != -1)
                    return true;

                    else
                        return false; // Does not match.

            });
        });
        SortedList<Libros> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableLibros.comparatorProperty());
        tableLibros.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        estatus.setItems(list);
        txt_id.setEditable(false);
        UpdateTable();
        search_libros();
    }
}

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

    @FXML
    private TextField filterField2;

    @FXML
    private TextField filterField3;

    @FXML
    private TableView<Inventarios> tableInventario;

    @FXML
    private TableColumn<Inventarios, Integer> col_idINT;

    @FXML
    private TableColumn<Inventarios, String> col_tituloINT;

    @FXML
    private TableColumn<Inventarios, Integer> col_cantidadINT;

    @FXML
    private TableColumn<Inventarios, String> col_estatusINT;

    @FXML
    private TextField txt_idINT;

    @FXML
    private TextField txt_cantINT;

    @FXML
    private TableView<Prestamos> tablePrestamos;

    @FXML
    private TableColumn<Prestamos, Integer> col_idPRE;

    @FXML
    private TableColumn<Prestamos, String> col_NombreA;

    @FXML
    private TableColumn<Prestamos, Integer> col_Matricula;

    @FXML
    private TextField txt_idPRE;

    @FXML
    private TextField txt_NombreA;

    @FXML
    private TextField txt_Matricula;


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

    ObservableList<Inventarios> listM2;
    ObservableList<Inventarios> dataList2;

    ObservableList<Prestamos> listM3;
    ObservableList<Prestamos> dataList3;

    public void Add_libros(){
        conn = mysqlconnect.ConnectDb();
        String sql = "insert into libros (titulo,autor,editorial,tema,anio) values (?,?,?,?,?)";
        String sql2 = "insert into inventario (titulo) values (?)";
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

            pst = conn.prepareStatement(sql2);
            pst.setString(1,txt_titulo.getText());
            pst.execute();
            UpdateTableInventarios();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void Add_prestamos(){
        conn = mysqlconnect.ConnectDb();
        String sql = "insert into prestamos (idLibro,nombre_alumno,matricula) values (?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_idPRE.getText());
            pst.setString(2,txt_NombreA.getText());
            pst.setString(3,txt_Matricula.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null,"¡Se concedió el préstamo!");
            UpdateTablePrestamos();
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

    @FXML
    void getSelectedInventario (MouseEvent event){
        index = tableInventario.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_idINT.setText(col_idINT.getCellData(index).toString());
        txt_cantINT.setText(col_cantidadINT.getCellData(index).toString());
        estatus.setValue(col_estatusINT.getCellData(index).toString());
    }

    @FXML
    void getSelectedPrestamos (MouseEvent event){
        index = tablePrestamos.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_idPRE.setText(col_idPRE.getCellData(index).toString());
        txt_NombreA.setText(col_NombreA.getCellData(index).toString());
        txt_Matricula.setText(col_Matricula.getCellData(index).toString());
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

            String sql2 = "update inventario set titulo = '" + value1 + "' where titulo = (select titulo from libros where id = '" +value6+"')";
            pst = conn.prepareStatement(sql2);
            pst.execute();
            UpdateTableInventarios();

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

    public void EditarInventario(){
        try{
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_idINT.getText();
            String value2 = txt_cantINT.getText();
            String value3 = estatus.getValue();

            String sql = "update inventario set cantidad= '"+value2+"',estatus= '"+value3+"' where id ='"+value1+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"¡Inventario editado!");
            UpdateTableInventarios();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void EditarPrestamo(){
        try{
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_idPRE.getText();
            String value2 = txt_NombreA.getText();
            String value3 = txt_Matricula.getText();

            String sql = "update prestamos set idLibro= '"+value1+"',nombre_alumno= '"+value2+"',matricula= '"+value3+"' where idLibro = '" + col_idPRE.getCellData(index).toString() + "'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Actualización al préstamo correcta");
            UpdateTablePrestamos();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void Delete(){
        conn = mysqlconnect.ConnectDb();
        String sql = "delete from libros where id = ?";
        String sql2 = "delete from inventario where titulo = ?";
            try{
                pst = conn.prepareStatement(sql);
                pst.setString(1,txt_id.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null,"¡Se borró el libro!");
                UpdateTable();
                search_libros();

                pst = conn.prepareStatement(sql2);
                pst.setString(1,txt_titulo.getText());
                pst.execute();
                UpdateTableInventarios();

            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
    }

    public void DeletePrestamo(){
        conn = mysqlconnect.ConnectDb();
        String sql = " delete from prestamos where idLibro = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_idPRE.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Préstamo borrado");
            UpdateTablePrestamos();
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

    public void UpdateTableInventarios(){
        col_tituloINT.setCellValueFactory(new PropertyValueFactory<Inventarios,String>("titulo"));
        col_estatusINT.setCellValueFactory(new PropertyValueFactory<Inventarios,String>("estatus"));
        col_cantidadINT.setCellValueFactory(new PropertyValueFactory<Inventarios,Integer>("cantidad"));
        col_idINT.setCellValueFactory(new PropertyValueFactory<Inventarios,Integer>("id"));

        listM2 = mysqlconnect.getDatainventarios();
        tableInventario.setItems(listM2);
    }

    public void UpdateTablePrestamos(){
        col_idPRE.setCellValueFactory(new PropertyValueFactory<Prestamos,Integer>("idLibro"));
        col_NombreA.setCellValueFactory(new PropertyValueFactory<Prestamos,String>("nombre_alumno"));
        col_Matricula.setCellValueFactory(new PropertyValueFactory<Prestamos,Integer>("matricula"));

        listM3 = mysqlconnect.getDataprestamos();
        tablePrestamos.setItems(listM3);
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

    @FXML
    void search_inventarios() {
        col_idINT.setCellValueFactory(new PropertyValueFactory<Inventarios,Integer>("id"));
        col_tituloINT.setCellValueFactory(new PropertyValueFactory<Inventarios,String>("titulo"));
        col_cantidadINT.setCellValueFactory(new PropertyValueFactory<Inventarios,Integer>("cantidad"));
        col_estatusINT.setCellValueFactory(new PropertyValueFactory<Inventarios,String>("estatus"));

        dataList2 = mysqlconnect.getDatainventarios();
        tableInventario.setItems(dataList2);
        FilteredList<Inventarios> filteredData = new FilteredList<>(dataList2, b -> true);
        filterField2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(inventario -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (inventario.getTitulo().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (inventario.getEstatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(inventario.getId()).indexOf(lowerCaseFilter)!=-1){
                    return true;
                } else if (String.valueOf(inventario.getCantidad()).indexOf(lowerCaseFilter) != -1)
                    return true;
                    else
                        return false; // Does not match.
            });
        });
        SortedList<Inventarios> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableInventario.comparatorProperty());
        tableInventario.setItems(sortedData);
    }

    @FXML
    void search_prestamos() {
        col_idPRE.setCellValueFactory(new PropertyValueFactory<Prestamos,Integer>("idLibro"));
        col_NombreA.setCellValueFactory(new PropertyValueFactory<Prestamos,String>("nombre_alumno"));
        col_Matricula.setCellValueFactory(new PropertyValueFactory<Prestamos,Integer>("matricula"));

        dataList3 = mysqlconnect.getDataprestamos();
        tablePrestamos.setItems(dataList3);
        FilteredList<Prestamos> filteredData = new FilteredList<>(dataList3, b -> true);
        filterField3.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prestamo -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(prestamo.getIdLibro()).indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (prestamo.getNombre_alumno().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(prestamo.getMatricula()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                    else
                        return false; // Does not match.
            });
        });
        SortedList<Prestamos> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablePrestamos.comparatorProperty());
        tablePrestamos.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        estatus.setItems(list);
        txt_id.setEditable(false);
        txt_idINT.setEditable(false);
        UpdateTable();
        UpdateTableInventarios();
        UpdateTablePrestamos();
        search_libros();
        search_inventarios();
        search_prestamos();
    }
}

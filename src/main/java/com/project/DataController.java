package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataController {
    private File data;
    
    @FXML
    private TextField allinp;


    @FXML
    private TableView<List<String>> tableView;

    @FXML
    private void select() throws IOException {
        // lr_acc.set(false);
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your CSV File");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        data = fc.showOpenDialog(new Stage());

        if (data != null) {
            Path tempPath = Paths.get("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\", "temp_data.csv");
            // lr_acc.set(true);
            Files.copy(data.toPath(),tempPath,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            loadCSVdata();
        }
    }

    private void loadCSVdata() throws IOException {
        try{
            File cpy = Paths.get("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\", "temp_data.csv").toFile();
            FileReader reader = new FileReader(cpy);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            List<String> headers = csvParser.getHeaderNames();

            tableView.getColumns().clear();
            for (int i = 0; i < headers.size(); i++) {
                final int colIndex = i;
                TableColumn<List<String>, String> column = new TableColumn<>(headers.get(i));
                column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(colIndex)));
                tableView.getColumns().add(column);
            }

            ObservableList<List<String>> dataRows = FXCollections.observableArrayList();
            for (CSVRecord csvRecord : csvParser) {
                List<String> row = new ArrayList<>();
                for (String header : headers) {
                    row.add(csvRecord.get(header));
                }
                dataRows.add(row);
            }

            tableView.setItems(dataRows);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void drop_col() throws IOException{
        try {
            String scriptPath = "D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\datapre.py";
            ProcessBuilder pb = new ProcessBuilder("python",scriptPath,"drop "+allinp.getText());
            pb.redirectErrorStream(true);
            // System.out.println("drop_"+input.getText());
            Process pyth = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pyth.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exit = pyth.waitFor();
            loadCSVdata();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void dropall() throws IOException{
        try {
            String scriptPath = "D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\datapre.py";
            ProcessBuilder pb = new ProcessBuilder("python",scriptPath,"dropall");
            pb.redirectErrorStream(true);
            // System.out.println("drop_"+input.getText());
            Process pyth = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pyth.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exit = pyth.waitFor();
            loadCSVdata();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void svd() throws IOException{
        try {
            String scriptPath = "D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\datapre.py";
            ProcessBuilder pb = new ProcessBuilder("python",scriptPath,"svd");
            pb.redirectErrorStream(true);
            // System.out.println("drop_"+input.getText());
            Process pyth = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pyth.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exit = pyth.waitFor();
            loadCSVdata();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToData() throws IOException {
        App.setRoot("data");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}

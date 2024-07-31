package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SecondaryController {
    private File data;
    private DoubleProperty lr_acc = new SimpleDoubleProperty();
    private DoubleProperty ex_acc = new SimpleDoubleProperty();
    private DoubleProperty k_acc = new SimpleDoubleProperty();
    private DoubleProperty d_acc = new SimpleDoubleProperty();
    private DoubleProperty sv_acc = new SimpleDoubleProperty();
    private DoubleProperty r_acc = new SimpleDoubleProperty();

    @FXML
    private TextField lr_ac;
    @FXML
    private TextField ex_ac;
    @FXML
    private TextField k_ac;
    @FXML
    private TextField d_ac;
    @FXML
    private TextField sv_ac;
    @FXML
    private TextField r_ac;


    @FXML
    private void initialize() {
        lr_ac.textProperty().bind(lr_acc.asString());
        ex_ac.textProperty().bind(ex_acc.asString());
        k_ac.textProperty().bind(k_acc.asString());
        d_ac.textProperty().bind(d_acc.asString());
        sv_ac.textProperty().bind(sv_acc.asString());
        r_ac.textProperty().bind(r_acc.asString());
    }

    @FXML
    private void select() throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your CSV File");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        data = fc.showOpenDialog(new Stage());

        if (data != null) {
            loadCSVdata(data);
        }
    }

    @FXML
    private void select_loaded() throws IOException {
        loadCSVdata(null);
    }

    private void loadCSVdata(File data) throws IOException {
        String pythonExecutable = "python";
        String scriptPath = "D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\reg.py";
        ProcessBuilder processBuilder;
        if(data==null){
            processBuilder = new ProcessBuilder(pythonExecutable, scriptPath,"none");
        }
        else{
            processBuilder = new ProcessBuilder(pythonExecutable, scriptPath, data.getAbsolutePath());
        }
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if("lr".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    lr_acc.set(Double.valueOf(line.trim()));
                }
                else if("ex".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    ex_acc.set(Double.valueOf(line.trim()));
                }
                else if("k".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    k_acc.set(Double.valueOf(line.trim()));
                }
                else if("d".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    d_acc.set(Double.valueOf(line.trim()));
                }
                else if("sv".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    sv_acc.set(Double.valueOf(line.trim()));
                }
                else if("r".equalsIgnoreCase(line)){
                    line = reader.readLine();
                    r_acc.set(Double.valueOf(line.trim()));
                }
            }

            int exitCode = process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToData() throws IOException {
        App.setRoot("data");
    }
}



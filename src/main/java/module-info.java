module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires smile.core;
    requires smile.data;

    opens com.project to javafx.fxml;
    exports com.project;
}

module com.matteo.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.matteo.minesweeper to javafx.fxml;
    exports com.matteo.minesweeper;
}
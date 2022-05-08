package com.matteo.minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

public class Main extends Application {
    private static Cell[][] _grid;
    private static int _gridSize = 10;
    private double _bombNumber = 10;
    private double _difficulty = 5;
    private double _cellCount = 100;

    private Random _rand;
    @Override
    public void start(Stage stage) throws Exception {
        this._rand = new Random(); //instance of random class
        this._difficulty = 3;
        this._gridSize = 10;

        this._difficulty = this._difficulty >= this._gridSize ? this._gridSize:this._difficulty;

        this._cellCount = _gridSize * _gridSize;
        this._bombNumber = (this._cellCount)* (this._difficulty/10) - 1;

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setPadding(new Insets(10, 10, 10, 10));

        this._grid = new Cell[_gridSize][_gridSize];

        for (int i = 0; i < _gridSize; i++) {
            for (int j = 0; j < _gridSize; j++) {
                this._grid[i][j] = new Cell((""), i, j);
                gp.add(_grid[i][j].get_btn(), j, i);
            }
        }

        this._setBombs();
        this._printMatrix(this._grid, this._gridSize);
        Scene scene = new Scene(gp, 1000, 1000);
        stage.setResizable(false);
        stage.setTitle("MineSweeper!");
        stage.setScene(scene);
        stage.show();
    }

    private void _setBombs() {
        double bombsToPlace = this._bombNumber;
        while (bombsToPlace > 0) {
            for (int i = 0; i < this._gridSize; i++) {
                for (int j = 0; j < this._gridSize; j++) {
                    if (bombsToPlace > 0 && !_grid[i][j].is_isBomb() && this._rand.nextInt(100) > 80) {
                        _grid[i][j].set_isBomb();
                        bombsToPlace--;
                    }
                }
            }
        }
    }

    public static void lost() {
        for (int i = 0; i < _gridSize; i++) {
            for (int j = 0; j < _gridSize; j++) {
                _grid[i][j].removeListener();
            }
        }
        System.out.println("Hai perso!!");
    }

    private void _printMatrix(Cell[][] matrix, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(matrix[i][j].is_isBomb() + " ");
            }
            System.out.println();
        }
    }

    public static void cellClicked(int i, int j) {

    }

    public static void main(String[] args) {
        launch();
    }
}

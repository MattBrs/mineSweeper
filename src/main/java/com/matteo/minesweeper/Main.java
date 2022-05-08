package com.matteo.minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
        this._difficulty = 2;
        this._gridSize = 10;

        this._difficulty = this._difficulty >= this._gridSize ? this._gridSize:this._difficulty;

        this._cellCount = _gridSize * _gridSize;
        this._bombNumber = (this._cellCount)* (this._difficulty/10) - 1;
        this._cellCount = this._cellCount - this._bombNumber;
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
        //this._printMatrix(this._grid, this._gridSize);
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

        for (int i = 0; i < this._gridSize; i++) {
            for (int j = 0; j < this._gridSize; j++) {
                int countBombs = 0;

                if (!_grid[i][j].is_isBomb()) {
                    for (int k = i-1; k <= i+1; k++) {
                        for (int l = j-1; l <= j+1; l++) {
                            if (areIndexValid(k, l)) {
                                if (_grid[k][l].is_isBomb()) {
                                    countBombs++;
                                }
                            }
                        }
                    }
                } else {
                    countBombs = -1;
                }
                _grid[i][j].set_riskNumber(countBombs);
            }
        }
    }

    public static void lost() {
        for (int i = 0; i < _gridSize; i++) {
            for (int j = 0; j < _gridSize; j++) {
                _grid[i][j].removeListener();
                _grid[i][j].set_clicked();
            }
        }
        System.out.println("Hai perso!!");
    }

    private void _printMatrix(Cell[][] matrix, int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(matrix[i][j].get_riskNumber() + " ");
            }
            System.out.println();
        }
    }

    public static void cellClicked(int i, int j) {
        recursiveUncover(i, j);

        boolean bombNear = false;

        for (int c = 0; c < _gridSize; c++) {
            for (int r = 0; r < _gridSize; r++) {

                for (int k = c-1; k <= c+1; k++) {
                    for (int l = r-1; l <= r+1; l++) {
                        if (areIndexValid(k, l) && _grid[k][l].is_isBomb()) {
                            bombNear = true;
                        }
                    }
                }
                _grid[c][r].setBombNear(bombNear);
            }
        }
    }

    public static boolean recursiveUncover(int startI, int startJ) {
        if (_grid[startI][startJ].is_clicked()) {
            return false;
        }
        if (!_grid[startI][startJ].is_isBomb() && _grid[startI][startJ].get_riskNumber() > 0) {
            _grid[startI][startJ].set_clicked();
            _grid[startI][startJ].showNumber();
            return false;
        }

        _grid[startI][startJ].set_clicked();


        for (int i = startI-1; i <= startI+1; i++) {
            for (int j = startJ-1; j <= startJ+1; j++) {
                if (areIndexValid(i, j) && (i != startI || j != startJ)) {
                    recursiveUncover(i, j);
                }
            }
        }

        return true;
    }

    public static boolean areIndexValid(int startI, int startJ) {
        return !(startI < 0 || startI >= _gridSize || startJ < 0 || startJ >= _gridSize);
    }

    public static void main(String[] args) {
        launch();
    }
}

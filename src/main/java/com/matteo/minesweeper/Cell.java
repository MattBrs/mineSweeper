package com.matteo.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Cell {
    private Button _btn;
    private int _riskNumber;
    private boolean _clicked;
    private boolean bombNear = false;
    private boolean _isBomb;
    public int i;
    public int j;


    public Cell(String btnName, int iPos, int jPos) {
        this._riskNumber = 0;
        this._clicked = false;
        this.setBtn(btnName);
        this._isBomb = false;
        this.i = iPos;
        this.j = jPos;
    }

    public void onClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && !this.is_clicked()) {
            if (this._isBomb) {
                Main.lost();
            }

            Main.cellClicked(i, j);
            //do other things (uncover image, exc)
        } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY) && !this.is_clicked()){
            //set or unset flag
        }
    }

    public void setBtn(String name) {
        this._btn = new Button(name);
        this._btn.setOnMouseClicked(this::onClick);
        this._btn.setPrefSize(100, 100);
        this._btn.setStyle("-fx-background-color: #91d455; ");
    }

    public void removeListener() {
        this._btn.setOnMouseClicked(null);
    }

    public Button get_btn() {
        return _btn;
    }
    public boolean is_clicked() {
        return _clicked;
    }
    public void set_clicked() {

        if (this.is_isBomb()) {
            this._btn.setStyle("-fx-background-color: #d45555; ");
        } else {
            this._btn.setStyle("-fx-background-color: #d4b055; ");
        }
        this._clicked = true;
    }
    public boolean is_isBomb() {
        return _isBomb;
    }
    public void set_isBomb() {
        this._isBomb = true;
    }
    public int get_riskNumber() {
        return _riskNumber;
    }
    public void set_riskNumber(int _riskNumber) {
        this._riskNumber = _riskNumber;
    }

    public void setBombNear(boolean bombNear) {
        this.bombNear = bombNear;

        if (this.bombNear && this._riskNumber > 0) {
            this._btn.setText(Integer.toString(this._riskNumber));
        } else {
            this._btn.setText("");
        }
    }
}
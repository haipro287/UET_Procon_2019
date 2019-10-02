import javax.swing.*;

public class Tile extends JButton {
    private int score;
    private int rowIndex, colIndex;
    public void setScore(int score) {
        this.score = score;
        this.setText(Integer.toString(score));
    }
    public int getScore() {
        return score;
    }

    public void setPos(int rowIndex, int colIndex) {
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean checkIfClose(Tile other) {
        int differenceX = this.rowIndex - other.rowIndex;
        int differenceY = this.colIndex - other.colIndex;
        if (differenceX <= 1 && differenceX >= -1 && differenceY <= 1 && differenceY >= -1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + rowIndex + ", " + colIndex + ") - " + score;
    }
}

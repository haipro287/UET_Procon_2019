import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    private int score;
    private int rowIndex, colIndex;
    private Agent occupyingAgent = null;
    private int occupyingTeam = 0; // TODO: use constant variable
    public Tile() {
        this(0, 0, 0);
    }

    /**
     * Initialize a tile with x, y positions and score, and set the text of the tile to 'score'.
     * @param rowIndex y-position
     * @param colIndex x-position
     * @param score tile's point
     */
    public Tile(int rowIndex, int colIndex, int score) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.score = score;
        this.setText(Integer.toString(score));
        this.setFont(new Font("Arial", Font.BOLD, 12));
    }
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

    public void setOccupyingTeam(int occupyingTeam) {
        this.occupyingTeam = occupyingTeam;
    }

    public int getOccupyingTeam() {
        return occupyingTeam;
    }

    /**
     * check if the input tile is one of the 8 surrounding tile to this tile
     * @param other input tile
     * @return true if input tile is close, false if it's not
     */
    public boolean checkIfClose(Tile other) {
        int differenceX = this.rowIndex - other.rowIndex;
        int differenceY = this.colIndex - other.colIndex;
        if (differenceX <= 1 && differenceX >= -1 && differenceY <= 1 && differenceY >= -1) {
            return true;
        }
        return false;
    }

    public void setOccupyingAgent(Agent agent) {
        this.occupyingAgent = agent;
    }

    public Agent getOccupyingAgent() {
        return occupyingAgent;
    }

    @Override
    public String toString() {
        return "(" + rowIndex + ", " + colIndex + ") - " + score;
    }
}

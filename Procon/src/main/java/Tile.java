import javax.swing.*;

public class Tile extends JButton {
    private int score;
    private int rowIndex, colIndex;
    private Agent occupyingAgent = null;
    private int occupyingTeam = 0; // use teamID
    public Tile() {
        this(0, 0, 0);
    }
    public Tile(int rowIndex, int colIndex, int score) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.score = score;
        this.setText(Integer.toString(score));
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

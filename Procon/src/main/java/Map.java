import java.util.ArrayList;

public class Map {
    private int width;
    private int height;

    private int startedAtUnixTime;
    private int turn;
    private ArrayList<Team> teams;
    private ArrayList<Action> actions;
    private ArrayList<ArrayList<Tile>> tiles;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public int getStartedAtUnixTime() {
        return startedAtUnixTime;
    }

    public void setStartedAtUnixTime(int startedAtUnixTime) {
        this.startedAtUnixTime = startedAtUnixTime;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", startedAtUnixTime=" + startedAtUnixTime +
                ", turn=" + turn +
                ", teams=" + teams +
                ", actions=" + actions +
                '}';
    }
}

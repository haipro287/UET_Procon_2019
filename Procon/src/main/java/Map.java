import java.util.ArrayList;

public class Map {
    private int width;
    private int height;
    private ArrayList<ArrayList<Integer>> points;
    private int startedAtUnixTime;
    private int turn;
    private ArrayList<ArrayList<Integer>> tiled;
    private ArrayList<Team> teams;
    private ArrayList<Action> actions;

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

    public ArrayList<ArrayList<Integer>> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<ArrayList<Integer>> points) {
        this.points = points;
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

    public ArrayList<ArrayList<Integer>> getTiled() {
        return tiled;
    }

    public void setTiled(ArrayList<ArrayList<Integer>> tiled) {
        this.tiled = tiled;
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
                ", points=" + points +
                ", startedAtUnixTime=" + startedAtUnixTime +
                ", turn=" + turn +
                ", tiled=" + tiled +
                ", teams=" + teams +
                ", actions=" + actions +
                '}';
    }
}

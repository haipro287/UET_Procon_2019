import java.awt.*;
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

    public void setTilesColor() {
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                Tile tile = this.getTiles().get(i).get(j);
                if (tile.getOccupyingTeam() == 0) {
                    tile.setBackground(Color.LIGHT_GRAY);
                }
                else if (tile.getOccupyingTeam() == this.getTeams().get(Panel.MY_TEAM).getTeamID()) {
                    tile.setBackground(Color.ORANGE);
                    if (tile.getOccupyingAgent() != null) tile.setBackground(Color.RED); // TODO: change teamIndex to MY_TEAM
                }
                else if (tile.getOccupyingTeam() == this.getTeams().get(Panel.OPPONENT_TEAM).getTeamID()) {
                    tile.setBackground(Color.CYAN);
                    Color blueColor = new Color(31, 168, 198);
                    if (tile.getOccupyingAgent() != null) tile.setBackground(blueColor);
                }
                tile.setBounds(60 * j, 60 * i, 50, 50);
            }
        }
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

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Panel extends JPanel {
    public static final int TURN_PERIOD = 30000;
    Map gameMap;
    private static Tile selectingTile = null;
    static int MY_TEAM, MY_TEAMID = 2;
    static int OPPONENT_TEAM;

    public Panel() {
        gameMap = new Map();
    }

    /**
     * Get Json from Procon server and set up game map.
     * @param host server host
     * @param token server token
     * @param matchID server matchID
     * @throws IOException
     */
    private void setGameMap(String host, String token, String matchID) throws IOException {
        gameMap = ServerConnection.getJSON(matchID);
        if (gameMap.getTeams().get(0).getTeamID() == MY_TEAMID) {
            MY_TEAM = 0;
            OPPONENT_TEAM  = 1;
        }
        else {
            MY_TEAM = 1;
            OPPONENT_TEAM = 0;
        }
        // TODO: Add scoreboard for 2 team
        gameMap.setTilesColor();
        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                Tile tile = gameMap.getTiles().get(i).get(j);
                int finalI = i, finalJ = j;
                tile.addActionListener(new ActionListener() {
                    /*
                    SET ACTION FOR TILES:
                    If no tile is selected -> then the next tile clicked will be selected.
                    If a tile is selected -> check if next tile clicked is them same tile or one close to it.
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        System.out.println("You clicked tile (" + finalI + ", " + finalJ + ")");
                        if (selectingTile == null && tile.getOccupyingAgent() != null) {
                            if (tile.getOccupyingAgent().getTeamID() == MY_TEAMID) {
                                selectingTile = tile;
                                selectingTile.setForeground(selectingTile.getBackground());
                                selectingTile.setBackground(Color.YELLOW);
                            }
                        } else if (selectingTile == tile) {
                            selectingTile.setBackground(selectingTile.getForeground());
                            selectingTile.setForeground(Color.BLACK);
                            selectingTile = null;
                        } else if (selectingTile != null) {
                            if (tile.checkIfClose(selectingTile)) {
                                selectingTile.getOccupyingAgent().setAction(selectingTile, tile);
                                System.out.println(selectingTile.getOccupyingAgent().getActionString());
                            }
                        }
//                        System.out.println(gameMap.getTeams().get(MY_TEAM).getInputActionString());
                    }
                });
                this.add(tile);            }
        }
    }

    /**
     * Generate json action string and post it to the server.
     * @param matchID
     * @throws IOException
     */
    private void takeAction(String matchID) throws IOException {
        String jsonInputString = gameMap.getTeams().get(MY_TEAM).getInputActionString();
        try {
            ServerConnection.postJSON(jsonInputString, "1");
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }
    /**
     * Each turn period (5 - 10 - 15 seconds), do as follow: connect to server and get json -> remove all component on screen
     * -> redraw -> calculate next step -> human takes action -> post action to server.
     */
    public void execLoop() throws IOException {
        long lastTime = 0;
        while(true) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= TURN_PERIOD) { //After the turn period, automatically fetch new API.
                //CLEAR MAP -> SET GAME MAP -> REDRAW
                this.removeAll();
                revalidate();
                try {
                    this.setGameMap("127.0.0.1:8080", "procon30_example_token", "207");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                repaint();
//                calculateNextStep();
                takeAction("207");
                lastTime = currentTime;
            }
        }
    }
}

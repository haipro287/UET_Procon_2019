import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Panel extends JPanel {

    Scanner sc = new Scanner(System.in);
    String token = sc.nextLine();
    ArrayList<Match> matches;
    Map gameMap;

    public static int TURN_PERIOD;
    static int matchID;
    static int MY_TEAM, MY_TEAMID;
    static int OPPONENT_TEAM, OPPONENT_TEAMID;
    private static Tile selectingTile = null;

    public Panel() throws IOException {
        gameMap = new Map();
        matches = ServerConnection.getMatch(token);
        TURN_PERIOD = matches.get(0).getTurnMillis()*1000 + matches.get(0).getIntervalMillis() + 1030;
        System.out.println(TURN_PERIOD);
        matchID = matches.get(0).getId();
        MY_TEAMID = matches.get(0).getTeamID();
    }

    /**
     * 18
     * Get Json from Procon server and set up game map.
     *
     * @param host    server host
     * @param token   server token
     * @param matchID server matchID
     * @throws IOException
     */
    private void setGameMap(String host, String token, int matchID) throws IOException {
        // Reset all element
        gameMap = ServerConnection.getJSON(token, matchID);
        selectingTile = null;

        //Set myTeamIndex and myTeamID
        if (gameMap.getTeams().get(0).getTeamID() == MY_TEAMID) {
            MY_TEAM = 0;
            OPPONENT_TEAM = 1;
        } else {
            MY_TEAM = 1;
            OPPONENT_TEAM = 0;
        }
        OPPONENT_TEAMID = gameMap.getTeams().get(OPPONENT_TEAM).getTeamID();

        // TODO: Add scoreboard for 2 team
        gameMap.setTilesColor();
        // Add action to all tiles
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
//                                System.out.println(selectingTile.getOccupyingAgent().getActionString());
//                                System.out.println(gameMap.getTeams().get(MY_TEAM).getInputActionString());
                            }
                        }
                    }
                });
                this.add(tile);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 600);
    }

    /**
     * Generate json action string and post it to the server.
     *
     * @param matchID
     * @throws IOException
     */
    private void takeAction(int matchID) throws IOException {
        String jsonInputString = gameMap.getTeams().get(MY_TEAM).getInputActionString();
        ServerConnection.postJSON(jsonInputString, token, matchID);
    }

    /**
     * Each turn period (5 - 10 - 15 seconds), do as follow: connect to server and get json -> remove all component on screen
     * -> redraw -> calculate next step -> human takes action -> post action to server.
     */
    public void execLoop() throws IOException {
//        long lastTime = 0;
        try {
            this.setGameMap("127.0.0.1:8080", token, matchID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();

        while (true) {
//            System.out.println(System.currentTimeMillis());
//            System.out.println(gameMap.getStartedAtUnixTime());

            long currentTurnRemain = (System.currentTimeMillis() - Long.parseLong(gameMap.getStartedAtUnixTime())) % TURN_PERIOD;

//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastTime >= TURN_PERIOD) { //After the turn period, automatically fetch new API.

            if (currentTurnRemain == 29500) {
                takeAction(matchID);
            }
            if (currentTurnRemain % 5000 == 0) {
                System.out.println(currentTurnRemain);
//                System.out.println((System.currentTimeMillis()-Long.parseLong(gameMap.getStartedAtUnixTime()))/TURN_PERIOD);
            }
//
            if (currentTurnRemain == 1000) { //After the turn period, automatically fetch new API.

                //CLEAR MAP -> SET GAME MAP -> REDRAW
//                takeAction(matchID);
                this.removeAll();
                revalidate();
                try {
                    this.setGameMap("127.0.0.1:8080", token, matchID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                repaint();
//                calculateNextStep();
//                lastTime = currentTime;
            }
        }

    }
}

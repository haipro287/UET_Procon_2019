import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Panel extends JPanel {
    public static final int TURN_PERIOD = 30;
    Map gameMap;
//    Tile[][] tiles;
    private static Tile selectingTile = null;
    static int MY_TEAM;
    static int OPPONENT_TEAM;

    public Panel() {
        gameMap = new Map();
    }
    private void setGameMap(String host, String token, String matchID) throws IOException {
        gameMap = ServerConnection.getJSON(matchID);
        if (gameMap.getTeams().get(0).getTeamID() == 9) {
            MY_TEAM = 0;
            OPPONENT_TEAM  = 1;
        }
        else {
            MY_TEAM = 1;
            OPPONENT_TEAM = 0;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 600);
        /*
        DRAW MAP: Each tile is a button.
         */
        // FIXME: gameMap height and width is 0 for sometime, then become 10.
        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                Tile tile = gameMap.getTiles().get(i).get(j);
                if (tile.getOccupyingTeam() == 0) {
                    tile.setBackground(Color.LIGHT_GRAY);
                }
                else if (tile.getOccupyingTeam() == gameMap.getTeams().get(MY_TEAM).getTeamID()) {
                    tile.setBackground(Color.ORANGE);
                    if (checkAgentPosColor(i, j, MY_TEAM) != 0) tile.setBackground(Color.RED); // TODO: change teamIndex to MY_TEAM
                }
                else if (tile.getOccupyingTeam() == gameMap.getTeams().get(OPPONENT_TEAM).getTeamID()) {
                    tile.setBackground(Color.CYAN);
                    if (checkAgentPosColor(i, j, OPPONENT_TEAM) != 0) tile.setBackground(Color.BLUE);
                }
                tile.setFont(new Font("Arial", Font.BOLD, 15));
                tile.setBounds(60 * j, 60 * i, 50, 50);

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
                        if (selectingTile == null && checkAgentPosColor(finalI, finalJ, MY_TEAM) != 0) {
                            selectingTile = tile;
                            selectingTile.setForeground(selectingTile.getBackground());
                            selectingTile.setBackground(Color.YELLOW);
                        } else if (selectingTile == tile) {
                            selectingTile.setBackground(selectingTile.getForeground());
                            selectingTile.setForeground(Color.BLACK);
                            selectingTile = null;
                        } else if (selectingTile != null) {
                            if (tile.checkIfClose(selectingTile)) {
                                System.out.println("It is close");
                                selectingTile.getOccupyingAgent().setAction(selectingTile, tile);
                                System.out.println(selectingTile.getOccupyingAgent().getActionString());
                            }
                        }
                        System.out.println(gameMap.getTeams().get(MY_TEAM).getInputActionString());
                        try {
                            ServerConnection.postJSON(gameMap.getTeams().get(MY_TEAM).getInputActionString(), "1");
                        }
                        catch (IOException i) {
                            i.printStackTrace();
                        }
                    }
                });
                this.add(tile);            }
        }
    }

    /**
     * Check if an agent is on tile[i][j]
     * @param i y-coordinate of tile
     * @param j x-coordinate of tile
     * @param teamIndex 0 for the first team, 1 for the second
     * @return true if an agent is on tile[i][j], else return false
     */
    private int checkAgentPosColor(int i, int j, int teamIndex) {
        ArrayList<Agent> teamAgents = gameMap.getTeams().get(teamIndex).getAgents();
        for (int k = 0; k < teamAgents.size(); k++) {
            Agent currentAgent = teamAgents.get(k);
            if (currentAgent.getY() == i + 1 && currentAgent.getX() == j + 1) {
                return currentAgent.getAgentID();
            }
        }
        return 0;
    }

    private void takeAction(String host, String token, String matchID) throws IOException {
        String jsonInputString = "";
//        ServerConnection.postJSON(matchID);
    }
    /**
     * Each turn period (5 - 10 - 15 seconds), do as follow: connect to server and get json -> remove all component on screen
     * -> redraw -> calculate next step -> human takes action -> post action to server.
     */
    public void execLoop() throws IOException {
        long lastTime = 0;
        /*
        SET GAME MAP: Fetch API from the URL and set the value collected to gameMap.
         */
        try {
            this.setGameMap("127.0.0.1:8080", "procon30_example_token", "207");
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
//        while(true) {
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastTime >= TURN_PERIOD) { //After the turn period, automatically fetch new API.
//                //FETCH JSON -> READ JSON -> SET GAME MAP
//                this.removeAll();
//                //revalidate();
//                repaint();
////                calculateNextStep();
//                //takeAction()
//                //writeJSON();
//                lastTime = currentTime;
//            }
//        }
    }
}

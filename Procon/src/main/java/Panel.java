import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Panel extends JPanel {
    public static final int TURN_PERIOD = 10000;
    private Map gameMap;
    private Tile[][] tiles;
    private static Tile selectingTile = null;


    public Panel() {
        gameMap = new Map();
    }
    private void setGameMap(String host, String token, String matchID) throws IOException {
        gameMap = ServerConnection.getJSON(matchID);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 600);
        /*
        DRAW MAP: Each tile is a button.
         */
        // FIXME: gameMap height and width is 0 for sometime, then become 10.
//        System.out.println(gameMap.getHeight() + " " + gameMap.getWidth());
        tiles = new Tile[gameMap.getHeight()][gameMap.getWidth()];
        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                tiles[i][j] = new Tile();
                Tile tile = tiles[i][j];
                tile.setPos(i + 1, j + 1);
                if (gameMap.getTiled().get(i).get(j) == 0) {
                    tile.setBackground(Color.LIGHT_GRAY);
                }
                else if (gameMap.getTiled().get(i).get(j) == gameMap.getTeams().get(0).getTeamID()) {
                    tile.setBackground(Color.ORANGE);
                    if (checkAgentPosColor(i, j, 0)) tile.setBackground(Color.RED);
                }
                else if (gameMap.getTiled().get(i).get(j) == gameMap.getTeams().get(1).getTeamID()) {
                    tile.setBackground(Color.CYAN);
                    if (checkAgentPosColor(i, j, 1)) tile.setBackground(Color.BLUE);
                }
                tile.setFont(new Font("Arial", Font.BOLD, 15));
                tile.setScore(gameMap.getPoints().get(i).get(j));
                tile.setBounds(60 * j, 60 * i, 50, 50);
//                tile.setBorder(new LineBorder(Color.GREEN));
                LineBorder border = new LineBorder(Color.GREEN);

                int finalI = i, finalJ = j;
                tile.addActionListener(new ActionListener() {
                    /*
                    SET ACTION FOR TILES:
                    If no tile is selected -> then the next tile clicked will be selected.
                    If a tile is selected -> check if next tile clicked is them same tile or one close to it.
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("You clicked tile (" + finalI + ", " + finalJ + ")");
                        if (selectingTile == null) {
                            selectingTile = tile;
                            selectingTile.setForeground(selectingTile.getBackground());
                            selectingTile.setBackground(Color.YELLOW);
                            System.out.println("Selecting Tile: " + selectingTile);
                            System.out.println("This tile" + tile);
                        } else if (selectingTile == tile) {
                            selectingTile.setBackground(selectingTile.getForeground());
                            selectingTile.setForeground(Color.BLACK);
                            selectingTile = null;
                            System.out.println("Selecting Tile: " + selectingTile);
                            System.out.println("This tile" + tile);
                        } else {
                            if (tile.checkIfClose(selectingTile)) {
                                System.out.println("It is close");
                            }
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
    private boolean checkAgentPosColor(int i, int j, int teamIndex) {
        ArrayList<Agent> teamAgents = gameMap.getTeams().get(teamIndex).getAgents();
        for (int k = 0; k < teamAgents.size(); k++) {
            Agent currentAgent = teamAgents.get(k);
            if (currentAgent.getY() == i + 1 && currentAgent.getX() == j + 1) {
                return true;
            }
        }
        return false;
    }

    private void takeAction(String host, String token, String matchID) throws IOException {
        String jsonInputString = "";
        ServerConnection.postJSON(matchID);
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
//                calculateNextStep();
//                //takeAction()
//                //writeJSON();
//                lastTime = currentTime;
//            }
//        }
    }
}

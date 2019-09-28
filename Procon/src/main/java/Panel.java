import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Panel extends JPanel {
    public static final int TURN_PERIOD = 10000;
    Map gameMap;
    JButton[][] tiles;

    public Panel() {
        gameMap = new Map();
    }
    private void setGameMap(String host, String token, String matchID) throws IOException {
        gameMap = ServerConnection.GetJSON(host, token, matchID);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 600);
        /*
        DRAW MAP: Each tile is a button.
         */
        //FIXME: gameMap height and width is 0 for sometime, then become 10.
//        System.out.println(gameMap.getHeight() + " " + gameMap.getWidth());
        tiles = new JButton[gameMap.getHeight()][gameMap.getWidth()];
        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                tiles[i][j] = new JButton();
                JButton tile = tiles[i][j];
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
                tile.setText(Integer.toString(gameMap.getPoints().get(i).get(j)));
                tile.setBounds(60 * j, 60 * i, 50, 50);
                int finalI = i, finalJ = j;
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("You clicked tile (" + finalI + ", " + finalJ + ")");
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
        for (int k = 0; k < gameMap.getTeams().get(teamIndex).getAgents().size(); k++) {
            Agent currentAgent = gameMap.getTeams().get(teamIndex).getAgents().get(k);
            if (currentAgent.getY() == i + 1 && currentAgent.getX() == j + 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Each turn period (5 - 10 - 15 seconds), do as follow: connect to server and get json -> remove all component on screen
     * -> redraw -> calculate next step -> human takes action -> post action to server.
     */
    public void execLoop() {
        long lastTime = 0;
        /*
        SET GAME MAP: Fetch API from the URL and set the value collected to gameMap.
         */
        try {
            this.setGameMap("127.0.0.1:8080", "procon30_example_token", "1");
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
//                //takeAction()
//                //writeJSON();
//                lastTime = currentTime;
//            }
//        }
    }
}

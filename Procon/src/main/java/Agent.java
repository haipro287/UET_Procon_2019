public class Agent {
    private int agentID;
    private int x;
    private int y;
    private Action action;
    private int teamID;
    // TODO: Add teamID property
    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return teamID;
    }

    public Agent() {
        this(0, 0, 0, 0);
    }

    public Agent(int teamID, int agentID) {
        this(teamID, agentID, 0, 0);
    }

    public Agent(int teamID, int agentID, int x, int y) {
        this.agentID = agentID;
        this.x = x;
        this.y = y;
        this.teamID = teamID;
        action = new Action(agentID);
    }

    public void setAction(Tile start, Tile destination) {
        // TODO: if destination tile is other team's tile, perform deletion
        // TODO: if other team's agent is on destination tile, notify me.
        action.setDx(destination.getColIndex() - start.getColIndex());
        action.setDy(destination.getRowIndex() - start.getRowIndex());
        if (destination.getOccupyingTeam() == 0 || destination.getOccupyingTeam() == Panel.MY_TEAMID) {
            action.setType("move");
        }
        else if (destination.getOccupyingTeam() != Panel.MY_TEAMID) {
//            System.out.println("[generate delete tile action]");
            action.setType("remove");
        }
    }

    public String getActionString() {
        return action.toString();
    }

    @Override
    public String toString() {
        return "Agent{" +
                "teamID=" + teamID +
                ", agentID=" + agentID +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

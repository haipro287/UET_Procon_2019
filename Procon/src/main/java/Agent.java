public class Agent {
    private int agentID;
    private int x;
    private int y;
    private Action action;

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

    public Agent() {
        this(0, 0, 0);
    }

    public Agent(int agentID) {
        this(agentID, 0, 0);
    }

    public Agent(int agentID, int x, int y) {
        this.agentID = agentID;
        this.x = x;
        this.y = y;
        action = new Action(agentID);
    }

    public void setAction(Tile start, Tile destination) {
        action.setDx(destination.getColIndex() - start.getColIndex());
        action.setDy(destination.getRowIndex() - start.getRowIndex());
        action.setType("move");
    }

    public String getActionString() {
        return action.toString();
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentID=" + agentID +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
    // TODO: Add action json
    // TODO: Check if an agent is on a certain tile
}

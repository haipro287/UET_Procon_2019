public class Agent {
    private int agentID;
    private int x;
    private int y;

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
        this.agentID = 0;
        this.x = 0;
        this.y = 0;
    }

    public Agent(int agentID, int x, int y) {
        this.agentID = agentID;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentID=" + agentID +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

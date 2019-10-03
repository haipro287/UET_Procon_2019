public class Action {
    private int agentID;
    private String type;
    private int dx;
    private int dy;
    private int turn;
    private int apply;

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public Action() {
        this(0, "stay", 0, 0);
    }

    public Action(int agentID) {
        this(agentID, "stay", 0, 0);
    }

    public Action(int agentID, String type, int dx, int dy) {
        this.agentID = agentID;
        this.type = type;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String toString() {
//        return "Action{" +
//                "agentID=" + agentID +
//                ", type='" + type + '\'' +
//                ", dx=" + dx +
//                ", dy=" + dy +
//                ", turn=" + turn +
//                ", apply=" + apply +
//                '}';
        return "{\"agentID\":" + agentID + ",\"dx\":" + dx + ",\"dy\":" + dy + ",\"type\":" + type + "}";
    }
}

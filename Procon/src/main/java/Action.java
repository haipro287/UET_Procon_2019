public class Action {
    private Agent agent;
    private String type;
    private int dx;
    private int dy;
    private int turn;
    private int apply;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
        this.agent = new Agent();
        this.type = "stay";
        this.dx = 0;
        this.dy = 0;
        this.turn = 0;
        this.apply = 0;
    }

    public Action(Agent agent, String type, int dx, int dy, int turn, int apply) {
        this.agent = agent;
        this.type = type;
        this.dx = dx;
        this.dy = dy;
        this.turn = turn;
        this.apply = apply;
    }

    @Override
    public String toString() {
        return "Action{" +
                "agent=" + agent +
                ", type='" + type + '\'' +
                ", dx=" + dx +
                ", dy=" + dy +
                ", turn=" + turn +
                ", apply=" + apply +
                '}';
    }
}

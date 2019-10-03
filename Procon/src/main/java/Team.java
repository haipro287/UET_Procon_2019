import java.util.ArrayList;

public class Team {
    private int teamID;
    private ArrayList<Agent> agents;
    private int tilePoint;
    private int areaPoint;

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public int getTilePoint() {
        return tilePoint;
    }

    public void setTilePoint(int tilePoint) {
        this.tilePoint = tilePoint;
    }

    public int getAreaPoint() {
        return areaPoint;
    }

    public void setAreaPoint(int areaPoint) {
        this.areaPoint = areaPoint;
    }

    public String getInputActionString() {
        String jsonActionInputStr = "{\"actions\":[";
        for (int i = 0; i < agents.size(); i++) {
            jsonActionInputStr += agents.get(i).getActionString();
            if (i < agents.size() - 1) jsonActionInputStr += ",";
        }
        jsonActionInputStr += "]}";
        return jsonActionInputStr;
    }

    public Team() {
        this.teamID = 0;
        this.agents = new ArrayList<Agent>();
        this.areaPoint = 0;
        this.tilePoint = 0;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID=" + teamID +
                ", agents=" + agents +
                ", tilePoint=" + tilePoint +
                ", areaPoint=" + areaPoint +
                '}';
    }
}

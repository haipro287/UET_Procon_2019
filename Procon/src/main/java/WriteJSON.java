import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteJSON {
    public static String writeJSON(ArrayList<Action> actionArrayList) {
        JSONArray actions = new JSONArray();
        JSONObject agentDetails = new JSONObject();
        for (int i = 0; i < actionArrayList.size(); i++) {
            agentDetails.put("agentID", actionArrayList.get(i).getAgent().getAgentID());
            agentDetails.put("type", actionArrayList.get(i).getType());
            agentDetails.put("dx", actionArrayList.get(i).getDx());
            agentDetails.put("dy", actionArrayList.get(i).getDy());
            actions.add(agentDetails);
        }
        JSONObject result = new JSONObject();
        result.put("actions", actions);

        try (FileWriter file = new FileWriter("moveReq.json")) {

            file.write(result.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toJSONString();
    }
    public static void main(String[] args) {
        ArrayList<Action> actions = new ArrayList<Action>();
        for (int i = 0; i < 2; i++) {
            actions.add(new Action());
        }
        writeJSON(actions);
    }
}

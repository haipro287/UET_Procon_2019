import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ReadJSON {
//    private static Map map = new Map();
    public static Map readJSON(String sysRepl) {
        JSONParser jsonParser = new JSONParser();
        Map map = null;
        try (StringReader reader = new StringReader(sysRepl)) {
            Object obj = jsonParser.parse(reader);
            JSONObject result = (JSONObject) obj;

            map = parseResult(result);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }
    private static Map parseResult(JSONObject result) {
        Map map = new Map();
        //get width/height from JSON
        map.setWidth(Integer.parseInt(result.get("width").toString()));
        map.setHeight(Integer.parseInt(result.get("height").toString()));

        // get points
        JSONArray points = (JSONArray) result.get("points");
        map.setPoints(new ArrayList<ArrayList<Integer>>());
        for(int i = 0; i < map.getHeight(); i++) {
            JSONArray array = (JSONArray) points.get(i);
            map.getPoints().add(new ArrayList<Integer>());
            for (int j = 0; j < map.getWidth(); j++) {
                map.getPoints().get(i).add(Integer.parseInt(array.get(j).toString()));
            }
        }

        //get startedAtUnixTime
        map.setStartedAtUnixTime(Integer.parseInt(result.get("startedAtUnixTime").toString()));

        // get turn
        map.setTurn(Integer.parseInt(result.get("turn").toString()));

        //get tiled
        JSONArray tiled = (JSONArray) result.get("tiled");
        map.setTiled(new ArrayList<ArrayList<Integer>>());
        for (int i = 0; i < map.getHeight(); i++) {
            JSONArray array = (JSONArray) tiled.get(i);
            map.getTiled().add(new ArrayList<Integer>());
            for (int j = 0; j < map.getWidth(); j++) {
                map.getTiled().get(i).add(Integer.parseInt(array.get(j).toString()));
            }
        }

        //get teams
        JSONArray teams = (JSONArray) result.get("teams");
        map.setTeams(new ArrayList<Team>());
        for (int i = 0; i < 2; i++) {
            map.getTeams().add(new Team());
            JSONObject obj = (JSONObject) teams.get(i);

            //get teamID
            map.getTeams().get(i).setTeamID(Integer.parseInt(obj.get("teamID").toString()));

            //get agents
            JSONArray array = (JSONArray) obj.get("agents");
            map.getTeams().get(i).setAgents(new ArrayList<Agent>());
            for (JSONObject jsonObject : (Iterable<JSONObject>) array) {
                JSONObject agent = (JSONObject) jsonObject;
                map.getTeams().get(i).getAgents().add(new Agent(
                        Integer.parseInt(agent.get("agentID").toString()),
                        Integer.parseInt(agent.get("x").toString()),
                        Integer.parseInt(agent.get("y").toString())
                ));
            }

            //get team point
            map.getTeams().get(i).setTilePoint(Integer.parseInt(obj.get("tilePoint").toString()));
            map.getTeams().get(i).setAreaPoint(Integer.parseInt(obj.get("areaPoint").toString()));
        }

        //get actions
        JSONArray actions = (JSONArray) result.get("actions");
        map.setActions(new ArrayList<Action>());
        for (JSONObject jsonObject : (Iterable<JSONObject>) actions) {
            JSONObject action = (JSONObject) jsonObject;
            map.getActions().add(new Action(
                    new Agent(
                            Integer.parseInt(action.get("agentID").toString()),
                            0,
                            0
                    ),
                    action.get("type").toString(),
                    Integer.parseInt(action.get("dx").toString()),
                    Integer.parseInt(action.get("dy").toString()),
                    Integer.parseInt(action.get("turn").toString()),
                    Integer.parseInt(action.get("apply").toString())
            ));
        }

//        System.out.println(map.toString());
        return map;
    }
    public static void main(String[] args) {
//        String sysRepl = "sysRepl_turn" + Integer.toString(map.getTurn()+1) + ".json";
//        readJSON(sysRepl);
    }
}

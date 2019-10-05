import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ReadJSON {
//    private static Map map = new Map();
    //read system reply json from : /matches/{ID}
    public static Map readJSON(String sysRepl) {
        JSONParser jsonParser = new JSONParser();
        Map map = null;
        try (StringReader reader = new StringReader(sysRepl)) {
            Object obj = jsonParser.parse(reader);
            JSONObject result = (JSONObject) obj;

            map = parseResult(result);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Map parseResult(JSONObject result) {
        Map map = new Map();
        //get width/height from JSON
        map.setWidth(Integer.parseInt(result.get("width").toString()));
        map.setHeight(Integer.parseInt(result.get("height").toString()));

        // get tiles'points
        JSONArray points = (JSONArray) result.get("points");
        map.setTiles(new ArrayList<ArrayList<Tile>>());
        for(int i = 0; i < map.getHeight(); i++) {
            JSONArray array = (JSONArray) points.get(i);
            map.getTiles().add(new ArrayList<Tile>());
            for (int j = 0; j < map.getWidth(); j++) {
                map.getTiles().get(i).add(new Tile(i + 1, j + 1, Integer.parseInt(array.get(j).toString())));
            }
        }

        //get startedAtUnixTime
//        map.setStartedAtUnixTime(Integer.parseInt(result.get("startedAtUnixTime").toString()));

        // get turn
        map.setTurn(Integer.parseInt(result.get("turn").toString()));

        //get tiles'occupying team
        JSONArray tiled = (JSONArray) result.get("tiled");
        for (int i = 0; i < map.getHeight(); i++) {
            JSONArray array = (JSONArray) tiled.get(i);
            for (int j = 0; j < map.getWidth(); j++) {
                map.getTiles().get(i).get(j).setOccupyingTeam(Integer.parseInt(array.get(j).toString()));
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
                Agent newAgent = new Agent(map.getTeams().get(i).getTeamID(),
                        Integer.parseInt(agent.get("agentID").toString()),
                        Integer.parseInt(agent.get("x").toString()),
                        Integer.parseInt(agent.get("y").toString()));
                map.getTeams().get(i).getAgents().add(newAgent);
                map.getTiles().get(newAgent.getY() - 1).get(newAgent.getX() - 1).setOccupyingAgent(newAgent);
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
                    0,
                    action.get("type").toString(),
                    Integer.parseInt(action.get("dx").toString()),
                    Integer.parseInt(action.get("dy").toString())
                    ));
        }

//        System.out.println(map.toString());
        return map;
    }

    //read match data from: /matches/
    public static ArrayList<Match> readMatch(String sysRepl) {
        ArrayList<Match> matches = new ArrayList<Match>();

        JSONParser jsonParser = new JSONParser();
        try (StringReader reader = new StringReader(sysRepl)) {
            Object obj = jsonParser.parse(reader);
            JSONArray array = (JSONArray) obj;
            for (JSONObject jsonObject: (Iterable<JSONObject>) array) {
                JSONObject match = (JSONObject) jsonObject;
                matches.add(new Match(
                        Integer.parseInt(match.get("id").toString()),
                        Integer.parseInt(match.get("intervalMillis").toString()),
                        match.get("matchTo").toString(),
                        Integer.parseInt(match.get("teamID").toString()),
                        Integer.parseInt(match.get("turnMillis").toString()),
                        Integer.parseInt(match.get("turns").toString())
                ));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(matches.get(i));
        }
        return matches;
    }
    public static void main(String[] args) {
        String sysRepl = "[\n" +
                "    {\n" +
                "        \"id\": 206,\n" +
                "        \"intervalMillis\": 5,\n" +
                "        \"matchTo\": \"Go Pro  -  Go Pro_test\",\n" +
                "        \"teamID\": 244,\n" +
                "        \"turnMillis\": 30,\n" +
                "        \"turns\": 400\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 207,\n" +
                "        \"intervalMillis\": 5,\n" +
                "        \"matchTo\": \"Go Pro  -  Go Pro_test\",\n" +
                "        \"teamID\": 244,\n" +
                "        \"turnMillis\": 30,\n" +
                "        \"turns\": 400\n" +
                "    }\n" +
                "]";
        readMatch(sysRepl);
    }
}

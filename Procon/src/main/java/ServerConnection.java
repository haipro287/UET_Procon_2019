import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerConnection {

    public static void GetJSON(String host, String token, String matchID) throws IOException{
        URL url = new URL ("http://" + host + "/matches/" + matchID);

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
        System.out.println(code);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            ReadJSON.readJSON(response.toString());
        }
    }

    public static void PostJSON(String host, String token, String matchID) throws IOException{
        URL url = new URL ("http://" + host + "/matches/" + matchID + "/action");

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        String jsonInputString = "{\"actions\":[{\"agentID\":2,\"dx\":0,\"dy\":0,\"type\":\"stay\"},{\"agentID\":3,\"dx\":0,\"dy\":0,\"type\":\"stay\"}]}";

        try(OutputStream os = con.getOutputStream()){
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
        System.out.println(code);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    public static void main (String []args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter host:");
        String host = sc.nextLine();
        System.out.println("Enter token:");
        String token = sc.nextLine();
        System.out.println("Enter matchID:");
        String matchID = sc.nextLine();
        ServerConnection.GetJSON(host, token, matchID );
    }

}
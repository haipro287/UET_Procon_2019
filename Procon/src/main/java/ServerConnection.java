import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerConnection {
    private static final String host = "http://sv-procon.uet.vnu.edu.vn:3000";
    private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiVGVhbTkiLCJpYXQiOjE1NzAyNTYzMDEsImV4cCI6MTU3MDI2MzUwMX0.PaM7dw7dJ_eMY9qyDe2bjZjGOiMVMk4YbtbTwOys6_U";
    public static void getMatch() throws IOException {
        URL url = new URL(host + "/matches");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
//        System.out.println(code);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            ReadJSON.readMatch(response.toString());
        }
    }

    public static Map getJSON(String matchID) throws IOException {
        URL url = new URL(host + "/matches/" + matchID);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
//        System.out.println(code);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
//            System.out.println(ReadJSON.readJSON(response.toString()));
            return ReadJSON.readJSON(response.toString());
        }
    }

    public static void postJSON(String actionString, String matchID) throws IOException {
        URL url = new URL(host + "/matches/" + "207" + "/action");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);

        String jsonInputString = actionString;

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();
//        System.out.println(code);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
//            System.out.println(response.toString());
        }
    }

    public static void getPing() throws IOException {
        URL url = new URL(host + "/ping");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization", token);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        int code = con.getResponseCode();
//        System.out.println(code);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

        public static void main (String[]args) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter host:");
//        String host = sc.nextLine();
//        System.out.println("Enter token:");
//        String token = sc.nextLine();
//        System.out.println("Enter matchID:");
//        String matchID = sc.nextLine();
//        ServerConnection.getJSON("206");
//            postJSON("", "1");
//            getJSON("1");
            getMatch();
        }

    }
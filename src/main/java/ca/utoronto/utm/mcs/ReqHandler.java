package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import javax.inject.Inject;

public class ReqHandler implements HttpHandler {

    // Complete This Class
    public Neo4jDAO neo4jDAO;

    @Inject
    public ReqHandler(Neo4jDAO neo4j) {
        this.neo4jDAO = neo4j;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       try {
           String basePath = "/api/v1/";
           String path = exchange.getRequestURI().normalize().getPath();
           String rest = path.substring(basePath.length());

           if (rest.length() == 0){
               sendResponseCode(exchange, 404, "Not Found");
               return;
           }
           else if (rest.charAt(rest.length()-1) == '/'){
               rest = rest.substring(0, rest.length()-1);
           }

           String reqType = exchange.getRequestMethod();

           if (reqType.equals("PUT")){
               handlePutRequest(exchange, rest);
           }
           else if (reqType.equals("GET")){
                handleGetRequest(exchange, rest);
           }
           else {
               sendResponseCode(exchange, 404, "Not Found");
           }

       } catch (Exception e) {
           sendResponseCode(exchange, 500, "Internal Server Error");
       }
    }

    public void sendResponseCode(HttpExchange httpExchange, int errorCode, String response) throws IOException {
        httpExchange.sendResponseHeaders(errorCode, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public void handlePutRequest(HttpExchange exchange, String path) throws IOException {
        String body;
        JSONObject jsonObject;

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonObject = new JSONObject(body);
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        switch (path){
            case ("addActor"):
                addActor(exchange, jsonObject);
            case ("addMovie"):
                addMovie(exchange, jsonObject);
            case ("addRelationship"):
                addRelationship(exchange, jsonObject);
            default:
                sendResponseCode(exchange, 404, "Not Found");
        }
    }

    public void addActor(HttpExchange exchange, JSONObject body) throws IOException {
        String id;
        String name;

        try {
            name = body.getString("name");
            id = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        int insertResponse = this.neo4jDAO.insertActor(id, name);

        switch (insertResponse){
            case 200:
                sendResponseCode(exchange, 200, "OK");
            case 400:
                sendResponseCode(exchange, 400, "BAD REQUEST");
            case 404:
                sendResponseCode(exchange, 404, "NOT FOUND");
            case 500:
                sendResponseCode(exchange, 500, "INTERNAL SERVER ERROR");
        }
    }

    public void addMovie(HttpExchange exchange, JSONObject body) throws IOException {
        String id;
        String name;

        try {
            name = body.getString("name");
            id = body.getString("movieId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        int insertResponse = this.neo4jDAO.insertMovie(id, name);

        switch (insertResponse){
            case 200:
                sendResponseCode(exchange, 200, "OK");
            case 400:
                sendResponseCode(exchange, 400, "BAD REQUEST");
            case 404:
                sendResponseCode(exchange, 404, "NOT FOUND");
            case 500:
                sendResponseCode(exchange, 500, "INTERNAL SERVER ERROR");
        }
    }

    public void addRelationship(HttpExchange exchange, JSONObject body) throws IOException {
        String movieId;
        String actorId;

        try {
            movieId = body.getString("movieId");
            actorId = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        int insertResponse = this.neo4jDAO.insertRelationship(actorId, movieId);

        switch (insertResponse){
            case 200:
                sendResponseCode(exchange, 200, "OK");
            case 400:
                sendResponseCode(exchange, 400, "BAD REQUEST");
            case 404:
                sendResponseCode(exchange, 404, "NOT FOUND");
            case 500:
                sendResponseCode(exchange, 500, "INTERNAL SERVER ERROR");
        }

    }


    public void handleGetRequest(HttpExchange exchange, String path) throws IOException {
        String body;
        JSONObject jsonObject;

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonObject = new JSONObject(body);
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        switch (path){
            case ("getActor"):
                getActor(exchange, jsonObject);
            case ("getMovie"):
                getMovie(exchange, jsonObject);
            case ("hasRelationship"):
                hasRelationship(exchange, jsonObject);
            case ("computeBaconNumber"):
                computeBaconNumber(exchange, jsonObject);
            case ("computeBaconPath"):
                computeBaconPath(exchange, jsonObject);
            default:
                sendResponseCode(exchange, 404, "Not Found");
        }
    }


    public void getActor(HttpExchange exchange, JSONObject body) throws IOException {
        String actorId;

        try {
            actorId = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        DBResponse response = this.neo4jDAO.getActor(actorId);
        sendResponseCode(exchange, response.code, response.response);
    }

    public void getMovie(HttpExchange exchange, JSONObject body) throws IOException {
        String movieId;

        try {
            movieId = body.getString("movieId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        DBResponse response = this.neo4jDAO.getMovie(movieId);
        sendResponseCode(exchange, response.code, response.response);
    }

    public void hasRelationship(HttpExchange exchange, JSONObject body) throws IOException {
        String movieId;
        String actorId;

        try {
            movieId = body.getString("movieId");
            actorId = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        DBResponse response = this.neo4jDAO.hasRelationship(actorId, movieId);
        sendResponseCode(exchange, response.code, response.response);
    }

    public void computeBaconNumber(HttpExchange exchange, JSONObject body) throws IOException{
        String kevinActorId = "nm0000102";
        String actorId;

        try {
            actorId = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        DBResponse response = this.neo4jDAO.computeBaconNumber(actorId, kevinActorId);
        sendResponseCode(exchange, response.code, response.response);
    }

    public void computeBaconPath(HttpExchange exchange, JSONObject body) throws IOException {
        String kevinActorId = "nm0000102";
        String actorId;

        try {
            actorId = body.getString("actorId");
        }
        catch (Exception e){
            sendResponseCode(exchange, 400, "BAD REQUEST");
            return;
        }

        DBResponse response = this.neo4jDAO.computeBaconPath(actorId, kevinActorId);
        sendResponseCode(exchange, response.code, response.response);
    }

}
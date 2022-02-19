package ca.utoronto.utm.mcs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.
public class AppTest {

    final static String API_URL = "http://localhost:8080";

    private static HttpResponse<String> sendRequest(String endpoint, String method, String reqBody) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + endpoint))
                .method(method, HttpRequest.BodyPublishers.ofString(reqBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void addActor1() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100")
                .put("name", "testActorName");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());
        HttpResponse<String> confirmRes2 = sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes2.statusCode());
    }

    @Test
    public void addActor2() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void addMovie1() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100")
                .put("name", "testMovieName");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());
        HttpResponse<String> confirmRes2 = sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes2.statusCode());
    }

    @Test
    public void addMovie2() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void addRelationship1() throws JSONException, IOException, InterruptedException {

        JSONObject addActorReq = new JSONObject()
                .put("actorId", "8787").put("name", "testActor2");
        sendRequest("/api/v1/addActor", "PUT", addActorReq.toString());

        JSONObject addMovieReq = new JSONObject()
                .put("movieId", "7878").put("name", "testMovie2");
        sendRequest("/api/v1/addMovie", "PUT", addMovieReq.toString());

        JSONObject confirmReq = new JSONObject()
                .put("movieId", "7878")
                .put("actorId", "8787");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());
        HttpResponse<String> confirmRes2 = sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes2.statusCode());
    }

    @Test
    public void addRelationship2() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "384738");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }
}

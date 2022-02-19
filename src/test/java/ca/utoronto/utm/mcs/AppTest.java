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
    public void addActorPass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100")
                .put("name", "testActorName");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void addActorFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void addMoviePass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100")
                .put("name", "testMovieName");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void addMovieFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void addRelationshipPass() throws JSONException, IOException, InterruptedException {
        JSONObject addActorReq = new JSONObject()
                .put("actorId", "100").put("name", "testActor2");
        sendRequest("/api/v1/addActor", "PUT", addActorReq.toString());

        JSONObject addMovieReq = new JSONObject()
                .put("movieId", "200").put("name", "testMovie2");
        sendRequest("/api/v1/addMovie", "PUT", addMovieReq.toString());

        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100")
                .put("actorId", "200");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void addRelationshipFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void getActorPass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100")
                .put("name", "testActorName");

        sendRequest("/api/v1/addActor", "PUT", confirmReq.toString());

        confirmReq = new JSONObject().put("actorId", "100");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/getActor", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void getActorFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject().put("actrId", "100");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/getActor", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void getMoviePass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("movieId", "100")
                .put("name", "testMovieName");

        sendRequest("/api/v1/addMovie", "PUT", confirmReq.toString());

        confirmReq = new JSONObject().put("movieId", "100");
        HttpResponse<String> confirmRes = sendRequest("/api/v1/getMovie", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void getMovieFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject().put("moviieId", "100");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/getMovie", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void hasRelationshipPass() throws JSONException, IOException, InterruptedException {
        JSONObject addActorReq = new JSONObject()
                .put("actorId", "100").put("name", "testActor2");
        sendRequest("/api/v1/addActor", "PUT", addActorReq.toString());

        JSONObject addMovieReq = new JSONObject()
                .put("movieId", "200").put("name", "testMovie2");
        sendRequest("/api/v1/addMovie", "PUT", addMovieReq.toString());

        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100")
                .put("movieId", "200");

        sendRequest("/api/v1/addRelationship", "PUT", confirmReq.toString());

        HttpResponse<String> confirmRes = sendRequest("/api/v1/hasRelationship", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void hasRelationshipFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "100");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/hasRelationship", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void computeBaconNumberPass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "nm0000102");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/computeBaconNumber", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void computeBaconNumberFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorid", "nm0000102");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/computeBaconNumber", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }

    @Test
    public void computeBaconPathPass() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorId", "nm0000102");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/computeBaconPath", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_OK, confirmRes.statusCode());
    }

    @Test
    public void computeBaconPathFail() throws JSONException, IOException, InterruptedException {
        JSONObject confirmReq = new JSONObject()
                .put("actorid", "nm0000102");

        HttpResponse<String> confirmRes = sendRequest("/api/v1/computeBaconPath", "GET", confirmReq.toString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, confirmRes.statusCode());
    }



}

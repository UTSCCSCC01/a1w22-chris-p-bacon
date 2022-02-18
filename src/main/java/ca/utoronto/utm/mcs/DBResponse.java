package ca.utoronto.utm.mcs;

public class DBResponse {
    public String response;
    public int code;

    public DBResponse(String response, int code) {
        this.response = response;
        this.code = code;
    }
}

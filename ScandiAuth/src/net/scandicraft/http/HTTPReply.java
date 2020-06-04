package net.scandicraft.http;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class HTTPReply {

    private String body;
    private int statusCode;

    public HTTPReply(HttpResponse response) {
        if (response == null) {
            body = "";
            statusCode = -1;
        } else {
            statusCode = response.getStatusLine().getStatusCode();
            try {
                body = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                body = "error";
            }
        }
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

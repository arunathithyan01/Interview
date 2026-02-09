package org.example;

public class ApiException extends RuntimeException {
    private final int statusCode;
    private final String url;
    private final String responseBody;

    public ApiException(int statusCode, String url, String responseBody) {
        super("HTTP " + statusCode + " calling " + url + " body=" + responseBody);
        this.statusCode = statusCode;
        this.url = url;
        this.responseBody = responseBody;
    }

    public int statusCode() { return statusCode; }
    public String url() { return url; }
    public String responseBody() { return responseBody; }
}

    public Posts updatePostPut(int id, UpdatePostRequest payload) throws IOException, InterruptedException {
        String url = BASE_URL + "/posts/" + id;
        return sendJson("PUT", url, payload, Posts.class);
    }

    private HttpRequest.Builder baseRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10)) // per-request timeout
                .header("Accept", "application/json");
    }

    private <T> T sendJson(String method, String url, Object payload, Class<T> responseType)
            throws IOException, InterruptedException {

        String json = objectMapper.writeValueAsString(payload);

        HttpRequest request = baseRequest(url)
                .header("Content-Type", "application/json; charset=utf-8")
                .method(method, HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        return sendAndParse(request, responseType);
    }

    private <T> T sendAndParse(HttpRequest request, Class<T> responseType)
            throws IOException, InterruptedException {

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (HttpTimeoutException e) {
            // request timeout
            throw new IOException("Request timed out calling " + request.uri(), e);
        } catch (ConnectException e) {
            // DNS / refused / cannot connect
            throw new IOException("Connection failed calling " + request.uri(), e);
        }

        int status = response.statusCode();
        String body = response.body() == null ? "" : response.body();

        if (status < 200 || status >= 300) {
            throw new ApiException(status, request.uri().toString(), body);
        }

        try {
            return objectMapper.readValue(body, responseType);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to parse JSON from " + request.uri() + " body=" + body, e);
        }
    }
}

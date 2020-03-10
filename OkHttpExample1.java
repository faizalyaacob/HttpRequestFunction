package kzero.java.hibernate;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.*;

import java.io.IOException;

//SYNCHRONOUS GET REQUEST

public class OkHttpExample1 {

    // only one client, singleton, better puts it in a factory, 
	// multiple instances will create more memory.
    private final OkHttpClient httpClient = new OkHttpClient();

    public static void main(String[] args) throws IOException {
        OkHttpExample1 obj = new OkHttpExample1();
       obj.sendGETSync();
       obj.sendGETAsync();
       obj.sendPOST();
       obj.sendPUT();
        obj.sendDELETE();
    }


    //-------------START GET REQUEST : SYNCHRONOUS---------------- 
    private void sendGETSync() throws IOException {

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/article/")  // add request headers
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response headers
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            // Get response body
            System.out.println(response.body().string());
        }
    }
    //-------------END GET REQUEST : SYNCHRONOUS----------------

    //-------------START GET REQUEST : ASYNCHRONOUS----------------
    private void sendGETAsync() throws IOException {

        Request request = new Request.Builder()
                .url("https://httpbin.org/get")
                .addHeader("custom-key", "mkyong")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    // Get response headers
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    // Get response body
                    System.out.println(responseBody.string());
                }
            }
        });

    }
    //-------------END GET REQUEST : ASYNCHRONOUS----------------

    //-----------START POST REQUEST FORM PARAMETER--------------
    private void sendPOSTFORM() throws IOException {

        // form parameters
        RequestBody formBody = new FormBody.Builder()
                .add("username", "abc")
                .add("password", "123")
                .add("custom", "secret")
                .build();

        Request request = new Request.Builder()
                .url("https://httpbin.org/post")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }
    //-----------END POST REQUEST FORM PARAMETER--------------

    //---------------START POST REQUEST JSON------------------
    private void sendPOST() throws IOException {

        // json formatted data
        String json = new StringBuilder()
                .append("{")
                .append("\"title\":\"Latest Books\",")
                .append("\"author\":\"Khairi\",")
                .append("\"email\":\"Khai@gmail.com\",")
                .append("\"date\":\"\"")
                .append("}").toString();

        // json request body
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://localhost:8000/article/")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }
    //---------------END POST REQUEST JSON------------------

    //---------------START PUT REQUEST JSON------------------
    private void sendPUT() throws IOException {

        // json formatted data
        String json = new StringBuilder()
                .append("{")
                .append("\"title\":\"new update!!!!\",")
                .append("\"author\":\"PIQAS\",")
                .append("\"email\":\"PIQA@gmail.com\",")
                .append("\"date\":\"\"")
                .append("}").toString();

        // json request body
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/detail/6")
                .put(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }
    //---------------END PUT REQUEST JSON--------------------

    //---------------START DELETE REQUEST JSON------------------
    private void sendDELETE() throws IOException {

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/detail/9")
                .delete()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }

    }
    //---------------END DELETE REQUEST JSON--------------------
}
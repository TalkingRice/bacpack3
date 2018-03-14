package com.example.zilunlin.bacpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zilun Lin on 9/1/2017.
 */

public class RequestHandler {

    //An uncoupled class that allows for the dynamic usage of POST and GET functions
    //as it is not specific to any context and will adjust for different queries.


    //This method in the class allows for the POST method to be used.
    //It uses a bufferedString reader and writer to send and receive input/output streams.
    //It also uses the getPostDataString to modify the url to POST values.
    public String sendPostRequest (String requestURL,
                                   HashMap<String, String> postDataParams){
        URL url;
        StringBuilder sb = new StringBuilder();
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();

            os.close();
            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;

                while ((response = br.readLine()) != null){
                    sb.append(response);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //This method allows for the GET requests.
    public String sendGetRequest(String requestURL){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
        }
        return sb.toString();
    }

    public String sendGetRequestParam(String requestURL, String param){
        StringBuilder sb =new StringBuilder();
        try {
            URL url = new URL(requestURL+param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    //Since there is only one URL with multiple variable, this is a coupled method.
    public String sendGetRequestMultipleParam(String id, String eventid) {
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL("https://192.168.0.103/android/CRUD/user/quitEventUser.php?id="+id+"&event_id="+eventid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}

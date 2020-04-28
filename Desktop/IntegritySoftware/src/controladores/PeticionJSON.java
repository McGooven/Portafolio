package controladores;

import java.lang.Exception;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Diego
 */
public class PeticionJSON {

    JSONObject reqBody;
    public JSONArray res;
    String url;
    String method;

    public PeticionJSON(JSONObject json, String method, String url) {
        this.reqBody = json;
        this.url = url;
        this.method = method.toUpperCase();
    }

    public PeticionJSON(JSONObject json, String method, Boolean secure, String server, int port, String path) {
        this.reqBody = json;

        String http;
        if (secure) {
            http = "https://";
        } else {
            http = "http://";
        }
        this.url = http + server + ":" + String.valueOf(port);
    }
    
    private void sendPOST() throws MalformedURLException, ProtocolException, IOException {
        String charset= "UTF-8";
        URL url = new URL(this.url);
        String r;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(this.method);
        if (this.method.equals("POST")) {
            con.setDoOutput(true);
        }
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept-Charset", charset);
        
        if (this.method.equals("POST")) {
            try(OutputStream os = con.getOutputStream()){
            os.write(this.reqBody.toString().getBytes(charset));
        }
        }
        
        
        // Check the error stream first, if this is null then there have been no issues with the request
        InputStream inputStream = con.getErrorStream();
        if (inputStream == null)
            inputStream = con.getInputStream();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            r=response.toString();
        }
        JSONArray jsonArray = new JSONArray(r);
        this.res = jsonArray;
        System.out.println(this.res.toString(4));
    }
    
    private void sendGET() throws MalformedURLException, IOException {
        String charset= "UTF-8";
        URL url = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
                StringBuilder response = new StringBuilder();
                String r;
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                r=response.toString();
                JSONArray jsonArray = new JSONArray(r);
                this.res = jsonArray;
                System.out.println(this.res.toString(4));
            }
        } else {
            System.out.println("GET request not worked");
        }
    }
    
    public void connect(){
        try {
            if (this.method != null && !this.method.isEmpty()) {
                if (this.method.equals("POST")) {
                    sendPOST();
                }else{
                    sendGET();
                }
            }else{
                throw new Exception("el tipo de HTTP request para esta instancia no está definida aún");
            }
        } catch (Exception e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }
    
    public boolean comparePrimitives(JSONObject json,String key, Object segundo){
        boolean same=false;
        try{
            if (json.isNull(key) && (segundo==null)) {
                same=true;
            }
            if (json.get(key) instanceof Integer) {
                Integer p = (Integer)json.get(key);
                Integer s = (Integer)segundo;

                if (p.intValue() == s.intValue()) {
                    same=true;
                }

            }
            if (json.get(key) instanceof String) {
                String p = (String)json.get(key);
                String s = (String)segundo;

                if (p.equals(s)) {
                    same=true;
                }
        
            }
            if (json.get(key) instanceof Boolean) {
                Boolean p = (Boolean)json.get(key);
                Boolean s = (Boolean)segundo;

                if (p.booleanValue() == s.booleanValue()) {
                    same=true;
                }
            }
            if (json.get(key) instanceof Double) {
                Double p = (Double)json.get(key);
                Double s = (Double)segundo;

                if (p.doubleValue()== s.doubleValue()) {
                    same=true;
                }
            }
            /*
            if (json.get(key) instanceof JSONObject) {
                JSONObject p = (JSONObject)json.get(key);
                JSONObject s = (JSONObject)segundo;

                if (p== s.doubleValue()) {
                    same=true;
                }
            }
            */
            /*
            if (json.get(key) instanceof JSONArray) {

            }
            */
            if (json.get(key) instanceof Long) {
                Long p = (Long)json.get(key);
                Long s = (Long)segundo;

                if (p.longValue()== s.longValue()) {
                    same=true;
                }
            }
            if (json.get(key) instanceof Number) {
                Number p = (Number)json.get(key);
                Number s = (Number)segundo;

                if (p.shortValue()== s.shortValue()) {
                    same=true;
                }
            }
        }catch(Exception ex){
            //Logger.getLogger(PeticionJSON.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("el tipo a comparar no corresponde");
        }
        return same;
    }

}

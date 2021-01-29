package com.example.profile;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONParser {

	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	static public JSONObject makeRequest( String u )
	{
		HttpURLConnection conn=null;
		try {


			URL url = new URL(u);
			conn= (HttpURLConnection) url.openConnection();



		} catch (MalformedURLException e) {
			Log.e("MalformedURLException ", "" + e.toString());
		} catch (IOException e) {
			Log.e("IOOOException ", "" + e.toString());
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "iso-8859-1"), 8);


			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			json = sb.toString();

		} catch (UnsupportedEncodingException e) {
			Log.e("UnsupportedEncoding", "UnsupportedEncodingException " + e.getMessage());
		} catch (IOException e) {
			Log.d("IoException Error", "Error converting result " +e.getMessage());
		}
		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {

                Log.e("JSON Parser", "Error parsing data " + conn.toString());

        }

		// return JSON String
		return jObj;
	}


}

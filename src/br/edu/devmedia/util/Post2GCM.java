package br.edu.devmedia.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.devmedia.entity.Content;

public class Post2GCM {

	public static String post(String apiKey, Content content) {
		StringBuffer resultado = new StringBuffer();
		try {
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "key=" + apiKey);

			connection.setDoOutput(true);

			ObjectMapper mapper = new ObjectMapper();

			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

			mapper.writeValue(outputStream, content);

			outputStream.flush();

			outputStream.close();

			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				resultado.append(linha);
			}
			bufferedReader.close();

			System.out.println(resultado);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado.toString();
	}

}

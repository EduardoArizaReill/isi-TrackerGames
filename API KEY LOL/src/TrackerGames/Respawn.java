package TrackerGames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Respawn {

	public static void serverStatus(String apiKeyRespawn) {
		String apiUrl = "https://api.mozambiquehe.re/servers?auth=" + apiKeyRespawn;
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");

			if (conn.getResponseCode() != 200) {
				System.out.println("Error: " + conn.getResponseCode());
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuilder response = new StringBuilder();
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			conn.disconnect();

			System.out.println("Estado de los servidores:\n" + response.toString() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void mapRotation(String apiKey) {
	    String url = "https://api.mozambiquehe.re/maprotation?auth=" + apiKey;
	    OkHttpClient client = new OkHttpClient();

	    try {
	        String response = makeRequest(client, url);

	        if (response != null) {
	            JSONObject jsonResponse = new JSONObject(response);

	            System.out.println("\n🌍 Rotación actual del mapa:");
	            JSONObject currentMap = jsonResponse.getJSONObject("current");
	            System.out.println("🗺️ Mapa actual: " + currentMap.getString("map"));
	            System.out.println("🕒 Duración restante: " + currentMap.getString("remainingTimer"));

	            System.out.println("\n⏭️ Próximo mapa:");
	            JSONObject nextMap = jsonResponse.getJSONObject("next");
	            System.out.println("🗺️ Próximo mapa: " + nextMap.getString("map"));
	            System.out.println("🕒 Duración: " + nextMap.getDouble("DurationInMinutes") + " minutos");

	        } else {
	            System.out.println("❌ Error al obtener la rotación del mapa.");
	        }
	    } catch (IOException e) {
	        System.out.println("❌ Error en la solicitud: " + e.getMessage());
	    }
	}

	private static String makeRequest(OkHttpClient client, String url) throws IOException {
	    Request request = new Request.Builder().url(url).get().build();
	    Response response = client.newCall(request).execute();

	    if (response.isSuccessful() && response.body() != null) {
	        return response.body().string();
	    } else {
	        System.out.println("❌ Error en la solicitud: " + response.code() + " - " + response.message());
	        return null;
	    }
	}

	public static void allEvents(String apiKeyRespawn) {
		String apiUrl = "https://api.mozambiquehe.re/news?auth=" + apiKeyRespawn;
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");

			if (conn.getResponseCode() != 200) {
				System.out.println("Error: " + conn.getResponseCode() + "\n");
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuilder response = new StringBuilder();
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			conn.disconnect();

			JSONArray eventsArray = new JSONArray(response.toString());
			System.out.println("Eventos actuales:\n");
			for (int i = 0; i < eventsArray.length(); i++) {
				JSONObject event = eventsArray.getJSONObject(i);
				String title = event.optString("title", "Sin título");
				String shortDesc = event.optString("short_desc", "Sin descripción");
				System.out.println("Título: " + title);
				System.out.println("Descripción: " + shortDesc + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stalkPlayer(String apiKeyRespawn) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce nombre(Nozezi PC o goku PS4): ");
		String nombreRespawn = sc.next();

		System.out.println("Introduce Plataforma(PC, PS4, X1, SWITCH ): ");
		String plataformaRespawn = sc.next();

		String apiUrl = "https://api.mozambiquehe.re/bridge?version=5&platform=" + plataformaRespawn.toUpperCase()
				+ "&player=" + nombreRespawn + "&auth=" + apiKeyRespawn;
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");

			if (conn.getResponseCode() != 200) {
				System.out.println("Error: " + conn.getResponseCode() + "\n");
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			StringBuilder response = new StringBuilder();
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			conn.disconnect();

			JSONObject playerData = new JSONObject(response.toString());
			String playerName = playerData.optJSONObject("global").optString("name", "Desconocido");
			int level = playerData.optJSONObject("global").optInt("level", 0);
			String rank = playerData.optJSONObject("global").optJSONObject("rank").optString("rankName", "Sin rango");
			String selectedLegend = playerData.optJSONObject("realtime").optString("selectedLegend", "Desconocida");

			System.out.println("Jugador: " + playerName);
			System.out.println("Nivel: " + level);
			System.out.println("Rango: " + rank);
			System.out.println("Leyenda seleccionada: " + selectedLegend + "\n");

			JSONObject legendStats = playerData.optJSONObject("legends").optJSONObject("selected")
					.optJSONObject("data");
			if (legendStats != null) {
				System.out.println("Estadísticas de la leyenda:");
				for (String key : legendStats.keySet()) {
					JSONObject stat = legendStats.getJSONObject(key);
					String statName = stat.optString("name", "Sin nombre");
					int statValue = stat.optInt("value", 0);
					System.out.println(statName + ": " + statValue);
				}
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

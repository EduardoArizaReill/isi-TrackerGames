package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class Invocadores {

	// Método para obtener el PUUID desde el enlace generado
	public static String getPuuidFromApi(String url) throws Exception {

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful() && response.body() != null) {
			String jsonData = response.body().string();
			JSONObject jsonObject = new JSONObject(jsonData);

			// Obtener y devolver el PUUID
			return jsonObject.getString("puuid");
		} else {
			throw new IOException("Error en la solicitud: " + response.code() + " - " + response.message());
		}
	}

	public static void getMaestriaCampeon(String puuid, String championId, String region2, String apiKey) {
		String url = "https://" + region2 + ".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/"
				+ puuid + "/by-champion/" + championId + "?api_key=" + apiKey;

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).get().build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful() && response.body() != null) {
				// Procesar respuesta JSON
				String jsonData = response.body().string();
				JSONObject jsonObject = new JSONObject(jsonData);

				// Extraer datos relevantes
				int championLevel = jsonObject.getInt("championLevel");
				int championPoints = jsonObject.getInt("championPoints");
				boolean requireGradeCounts = jsonObject.has("requireGradeCounts")
						&& jsonObject.getBoolean("requireGradeCounts");

				// Mostrar la información
				System.out.println("🎖 Champion Level: " + championLevel);
				System.out.println("✨ Champion Points: " + championPoints);
				System.out.println("📊 Require Grade Counts: " + requireGradeCounts);
			} else {
				System.out.println(
						"❌ Error al obtener datos de maestría: " + response.code() + " - " + response.message());
			}
		} catch (Exception e) {
			System.out.println("❌ Error en la solicitud: " + e.getMessage());
		}
	}

	public static String getElPud(String region, String tagLine, String gameName) {
		String apiKey = "RGAPI-9ce61a2a-8635-4d87-b140-8c0210bc4b7e";
		// Construir el enlace
		String url = "https://" + region + ".api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/"
				+ tagLine + "?api_key=" + apiKey;

		return url;
	}
}

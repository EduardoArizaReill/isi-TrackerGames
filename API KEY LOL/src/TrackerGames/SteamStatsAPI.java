package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class SteamStatsAPI {

	private static final String PLAYER_STATS_URL = "https://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/";

	// Método para obtener los juegos con más jugadores conectados
	public static List<String> getTop10MostPlayedGames() throws IOException {
		OkHttpClient client = new OkHttpClient();
		List<String> topGames = new ArrayList<>();

		// Hacer solicitud a la API de estadísticas
		Request request = new Request.Builder().url(PLAYER_STATS_URL).get().build();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful() && response.body() != null) {
			String jsonData = response.body().string();
			JSONObject jsonObject = new JSONObject(jsonData).getJSONObject("response");
			JSONArray games = jsonObject.getJSONArray("games");

			// Crear una lista para almacenar los juegos con sus jugadores
			List<Map.Entry<String, Integer>> gameList = new ArrayList<>();
			for (int i = 0; i < games.length(); i++) {
				JSONObject game = games.getJSONObject(i);
				String name = game.getString("name");
				int playerCount = game.getInt("player_count");
				gameList.add(new AbstractMap.SimpleEntry<>(name, playerCount));
			}

			// Ordenar la lista por número de jugadores en orden descendente
			gameList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

			// Obtener los top 10 juegos
			for (int i = 0; i < Math.min(10, gameList.size()); i++) {
				Map.Entry<String, Integer> game = gameList.get(i);
				topGames.add(game.getKey() + " - Jugadores: " + game.getValue());
			}
		} else {
			System.out.println("❌ Error al obtener las estadísticas de los juegos.");
		}

		return topGames;
	}
}

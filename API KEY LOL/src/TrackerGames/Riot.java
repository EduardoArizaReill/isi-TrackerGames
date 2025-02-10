package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Riot {

	private static final String CHAMPIONS_JSON_URL_VERSIONS = "https://ddragon.leagueoflegends.com/api/versions.json";
	private static String lastVersion;
	private static final Map<Integer, String> CHAMPION_MAP = new HashMap<>();

	static {
		try {
			loadLastVersion();
			loadChampionsFromJson();
		} catch (IOException e) {
			System.out.println("❌ Error al inicializar Riot: " + e.getMessage());
		}
	}

	// Método para mostrar campeones gratuitos con sus IDs y los de nuevos jugadores
	public static void showFreeChamps(String apiKey) {
		OkHttpClient client = new OkHttpClient();
		try {
			String url = "https://euw1.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + apiKey;
			String response = makeRequest(client, url);

			if (response != null) {
				JSONObject rotations = new JSONObject(response);
				System.out.println("\n🎮 Campeones gratuitos en rotación:");
				rotations.getJSONArray("freeChampionIds").forEach(championId -> {
					int champId = (Integer) championId;
					System.out.println("- " + getChampionName(champId) + " (ID: " + champId + ")");
				});

				System.out.println("\n🎯 Campeones gratuitos para nuevos jugadores:");
				rotations.getJSONArray("freeChampionIdsForNewPlayers").forEach(championId -> {
					int champId = (Integer) championId;
					System.out.println("- " + getChampionName(champId) + " (ID: " + champId + ")");
				});

				int maxNewPlayerLevel = rotations.getInt("maxNewPlayerLevel");
				System.out.println("\n🔒 Nivel máximo para jugadores nuevos: " + maxNewPlayerLevel);
			}
		} catch (IOException e) {
			System.out.println("❌ Error al obtener los campeones gratuitos: " + e.getMessage());
		}
	}

	// Método para mostrar todas las versiones
	public static void showAllVersions(String apiKey) {
		try {
			System.out.println("\n📜 Todas las versiones disponibles:");
			getAllVersions().forEach(version -> System.out.println("- " + version));
			System.out.println("✔ Última versión: " + getLastVersion());
		} catch (Exception e) {
			System.out.println("❌ Error al obtener las versiones: " + e.getMessage());
		}
	}

	// Método para mostrar información del jugador y un campeón
	public static void showInfoPlayerChamp(String apiKey) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("🌍 Introduce región (ejemplo: europe): ");
		String region = scanner.nextLine();

		System.out.print("🔍 Ingresa el gameName (ejemplo: Nozezi): ");
		String gameName = scanner.nextLine();

		System.out.print("🌍 Ingresa el tagLine (ejemplo: EUW): ");
		String tagLine = scanner.nextLine();

		// Construir el enlace para obtener el puuid
		String puuidUrl = "https://" + region.toLowerCase() + ".api.riotgames.com/riot/account/v1/accounts/by-riot-id/"
				+ gameName + "/" + tagLine.toUpperCase() + "?api_key=" + apiKey;

		System.out.println(puuidUrl);

		String puuid = null;
		try {
			OkHttpClient client = new OkHttpClient();
			puuid = makeRequest(client, puuidUrl);
			if (puuid != null) {
				JSONObject puuidResponse = new JSONObject(puuid);
				puuid = puuidResponse.getString("puuid");
			} else {
				System.out.println("❌ Error al obtener el PUUID del jugador.");
				return;
			}
		} catch (IOException e) {
			System.out.println("❌ Error al obtener el PUUID: " + e.getMessage());
			return;
		}

		//System.out.println(puuid);

		System.out.print("🔍 Ingresa el nombre del campeón (ejemplo: Yasuo): ");
		String championName = scanner.nextLine();

		// Obtener el ID del campeón desde el nombre
		Integer championId = getChampionIdByName(championName);
		if (championId == null) {
			System.out.println("❌ No se encontró el campeón: " + championName);
			return;
		}

		// Construir el enlace para obtener la maestría
		String regionCorta = null;
		if (region.equals("europe")) {
			regionCorta = "euw1";
		}
		String masteryUrl = "https://" + regionCorta
				+ ".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/" + puuid + "/by-champion/"
				+ championId + "?api_key=" + apiKey;

		try {
		    OkHttpClient client = new OkHttpClient();
		    String masteryResponse = makeRequest(client, masteryUrl);

		    if (masteryResponse != null) {
		        JSONObject masteryData = new JSONObject(masteryResponse);

		        int championLevel = masteryData.getInt("championLevel");
		        int championPoints = masteryData.getInt("championPoints");

		        // Comprobar si el campo "chestGranted" existe antes de acceder a él
		        boolean chestGranted = masteryData.has("chestGranted") && masteryData.getBoolean("chestGranted");

		        System.out.println("\n📌 Información de Maestría del Campeón:");
		        System.out.println("🔹 Nombre del campeón: " + championName);
		        System.out.println("🔹 ID del campeón: " + championId);
		        System.out.println("🔹 Nivel de maestría: " + championLevel);
		        System.out.println("🔹 Puntos de maestría: " + championPoints);
		        System.out.println("🔹 Cofre obtenido: " + (chestGranted ? "Sí" : "No disponible"));
		    } else {
		        System.out.println("❌ Error al obtener la información de maestría del campeón.");
		    }
		} catch (IOException e) {
		    System.out.println("❌ Error al obtener la maestría del campeón: " + e.getMessage());
		}

	}

	// Método para realizar solicitudes HTTP
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

	// Métodos relacionados con los campeones

	private static void loadLastVersion() throws IOException {
		OkHttpClient client = new OkHttpClient();
		String response = makeRequest(client, CHAMPIONS_JSON_URL_VERSIONS);

		if (response != null) {
			JSONArray versions = new JSONArray(response);
			if (versions.length() > 0) {
				lastVersion = versions.getString(0);
			}
		}
	}

	private static void loadChampionsFromJson() throws IOException {
		OkHttpClient client = new OkHttpClient();
		String championsUrl = "https://ddragon.leagueoflegends.com/cdn/" + lastVersion + "/data/en_US/champion.json";
		String response = makeRequest(client, championsUrl);

		if (response != null) {
			JSONObject data = new JSONObject(response).getJSONObject("data");
			for (String key : data.keySet()) {
				JSONObject champion = data.getJSONObject(key);
				CHAMPION_MAP.put(champion.getInt("key"), champion.getString("name"));
			}
		}
	}

	public static String getChampionName(int id) {
		return CHAMPION_MAP.getOrDefault(id, "Desconocido");
	}

	public static Integer getChampionIdByName(String name) {
		return CHAMPION_MAP.entrySet().stream().filter(entry -> entry.getValue().equalsIgnoreCase(name))
				.map(Map.Entry::getKey).findFirst().orElse(null);
	}

	public static List<String> getAllVersions() {
		return CHAMPION_MAP.isEmpty() ? List.of() : CHAMPION_MAP.values().stream().collect(Collectors.toList());
	}

	public static String getLastVersion() {
		return lastVersion;
	}
}

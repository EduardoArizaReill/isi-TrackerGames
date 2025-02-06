package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import TrackerGames.Champions;
import TrackerGames.Invocadores;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BuscaPerfiles {
	/*Buscar jugador: https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=Nozezi&auth=5bc9a10216f18a6d3771cb04df7cfd1a
	 * https://api.mozambiquehe.re/bridge?version=5&platform=<Plataforma>&player=<Nombre>&auth=<Key>*/
	
	//Eventos: https://api.mozambiquehe.re/news?auth=5bc9a10216f18a6d3771cb04df7cfd1a
	
	//Status: servers: https://api.mozambiquehe.re/servers?auth=5bc9a10216f18a6d3771cb04df7cfd1a
	
	// apex api key 5bc9a10216f18a6d3771cb04df7cfd1a
	private static final String API_KEY = "RGAPI-f1067832-e50c-42b7-ba69-e343f7a46eec";
	private static final String API_KEY_STEAM = "3C654FF590065C6343266D006B7D71BE";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		OkHttpClient client = new OkHttpClient();

		while (true) {

			System.out.println("1. STEAM");
			System.out.println("2. RIOT GAMES");
			int compani = scanner.nextInt();
			scanner.nextLine();

			switch (compani) {
			// STEAM
			case 1 -> {

				System.out.println("Elige una opción:");
				System.out.println("1. Mostrar juegos");
				System.out.println("2. Mostrar top 10 juegos más jugados");
				System.out.println("0. Salir");
				int opcionSteam = scanner.nextInt();
				scanner.nextLine(); // Consumir el salto de línea

				switch (opcionSteam) {
				case 1 -> {
					System.out.println("🎮 Obteniendo lista de juegos de Steam...");

					try {
						List<String> gameNames = SteamAPI.getGameNames();
						System.out.println("\n📋 Lista de Juegos en Steam:");
						for (String name : gameNames) {
							System.out.println("- " + name);
						}
					} catch (IOException e) {
						System.out.println("❌ Error al obtener los nombres de los juegos: " + e.getMessage());
					}

				}
				case 2 -> {
					System.out.println("🎮 Obteniendo el Top 10 de juegos más jugados en Steam...");

					try {
						List<String> topGames = SteamStatsAPI.getTop10MostPlayedGames();
						System.out.println("\n📋 Top 10 Juegos Más Jugados en Steam:");
						for (String game : topGames) {
							System.out.println("- " + game);
						}
					} catch (IOException e) {
						System.out.println("❌ Error al obtener los juegos más jugados: " + e.getMessage());
					}

				}
				
				}
			}
			// RIOT
			case 2 -> {

				System.out.println("Elige una opción:");
				System.out.println("1. Ver mis datos");
				System.out.println("2. Ver campeones gratuitos");
				System.out.println("3. Mostrar todas las versiones");
				System.out.println("4. Ver Maestrias del jugador");
				System.out.println("0. Salir");
				int opcionRiot = scanner.nextInt();
				scanner.nextLine(); // Consumir el salto de línea

				switch (opcionRiot) {
				case 1 -> {
					System.out.print("Ingresa tu nombre de invocador: ");
					String summonerName = scanner.nextLine();
					System.out.print("Ingresa tu región (euw1, na1, kr, etc.): ");
					String region = scanner.nextLine();

					try {
						// Obtener datos del invocador
						String url = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/"
								+ summonerName.replace(" ", "%20") + "?api_key=" + API_KEY;
						String response = makeRequest(client, url);

						if (response != null) {
							JSONObject summonerData = new JSONObject(response);
							System.out.println("\n📌 Información del Invocador:");
							System.out.println("Nombre: " + summonerData.getString("name"));
							System.out.println("Nivel: " + summonerData.getInt("summonerLevel"));
							System.out.println("ID: " + summonerData.getString("id"));
							System.out.println("PUUID: " + summonerData.getString("puuid"));
						}
					} catch (IOException e) {
						System.out.println("❌ Error al obtener tus datos: " + e.getMessage());
					}
				}
				case 2 -> {
					try {
						// Obtener campeones gratuitos
						String url = "https://euw1.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=RGAPI-9ce61a2a-8635-4d87-b140-8c0210bc4b7e";
						String response = makeRequest(client, url);

						if (response != null) {
							JSONObject rotations = new JSONObject(response);
							JSONArray freeChampions = rotations.getJSONArray("freeChampionIds");
							JSONArray newPlayerChampions = rotations.getJSONArray("freeChampionIdsForNewPlayers");
							int maxNewPlayerLevel = rotations.getInt("maxNewPlayerLevel");

							System.out.println("\n🎮 Campeones en rotación gratuita:");
							for (int i = 0; i < freeChampions.length(); i++) {
								int championId = freeChampions.getInt(i);
								System.out.println(
										"- " + Champions.getChampionName(championId) + " (ID: " + championId + ")");
							}
							System.out.println("\n\n🎯 Campeones gratuitos para nuevos jugadores (Nivel máximo "
									+ maxNewPlayerLevel + "):");
							for (int i = 0; i < newPlayerChampions.length(); i++) {
								int championId = newPlayerChampions.getInt(i);
								System.out.println(
										"- " + Champions.getChampionName(championId) + " (ID: " + championId + ")");
							}
							System.out.println();
						}
					} catch (IOException e) {
						System.out.println("❌ Error al obtener los campeones gratuitos: " + e.getMessage());
					}
				}
				case 3 -> {
					System.out.println("\n📜 Todas las versiones disponibles:");
					for (String version : Champions.getAllVersions()) {
						System.out.println("- " + version);
					}
					System.out.println("\n✔ La última versión es: " + Champions.getLastVersion());
				}
				case 4 -> {
					Champions champion = new Champions();
					System.out.print("🌍 Introduce region (ejemplo: europe o asia): ");
					String region = scanner.nextLine();

					System.out.print("🌍 Ingresa el tagLine (ejemplo: EUW): ");
					String tagLine = scanner.nextLine();

					System.out.print("🔍 Ingresa el gameName (ejemplo: Nozezi): ");
					String gameName = scanner.nextLine();

					String url = Invocadores.getElPud(region, tagLine, gameName);
					// System.out.println(url);
					// Realizar la solicitud HTTP y obtener el puuid

					String puuid = null;
					try {
						puuid = Invocadores.getPuuidFromApi(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// System.out.println("🔑 Puuid obtenido: " + puuid);
					String region2;

					if (region.equals("europe")) {
						region2 = "euw1";
					} else {
						region2 = "jp1";
					}

					System.out.print("🔍 Ingresa el nombre del campeón (ejemplo: Yasuo): ");
					String championName = scanner.nextLine();

					// Buscar el ID del campeón por su nombre
					Integer championId = Champions.getChampionIdByName(championName);

					if (championId != null) {
						System.out.println("✔ ID del campeón encontrado: " + championId);

						// Obtener y mostrar maestrías del campeón
						Invocadores.getMaestriaCampeon(puuid, String.valueOf(championId), region2, API_KEY);
					} else {
						System.out.println("❌ No se encontró el campeón: " + championName);
					}

				}
				case 0 -> {
					System.out.println("¡Hasta luego!");
					scanner.close();
					return;
				}
				default -> System.out.println("Opción no válida. Intenta de nuevo.");
				}
			}
			}
		}
	}

	// Método para realizar una solicitud HTTP y devolver la respuesta como String
	public static String makeRequest(OkHttpClient client, String url) throws IOException {
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();

		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			System.out.println("❌ Error en la solicitud: " + response.code() + " - " + response.message());
			return null;
		}
	}
}

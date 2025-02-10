package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class InfoTracker {
	/*
	 * Buscar jugador:
	 * https://api.mozambiquehe.re/bridge?version=5&platform=PC&player=Nozezi&auth=
	 * 5bc9a10216f18a6d3771cb04df7cfd1a
	 * https://api.mozambiquehe.re/bridge?version=5&platform=<Plataforma>&player=<
	 * Nombre>&auth=<Key>
	 */

	// Eventos:
	// https://api.mozambiquehe.re/news?auth=5bc9a10216f18a6d3771cb04df7cfd1a

	// Status: servers:
	// https://api.mozambiquehe.re/servers?auth=5bc9a10216f18a6d3771cb04df7cfd1a

	// apex api key 5bc9a10216f18a6d3771cb04df7cfd1a
	private static final String API_KEY_RIOT = "RGAPI-b46501d5-acb2-4a32-a0e0-9ed4353b51cc";
	private static final String API_KEY_RESPAWN = "5bc9a10216f18a6d3771cb04df7cfd1a";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {

			System.out.println("1. RIOT GAMES");
			System.out.println("2. RESPAWN");
			System.out.println("0. Salir");
			int compani = scanner.nextInt();
			scanner.nextLine();

			switch (compani) {

			case 1 -> {

				System.out.println("Elige una opción:");
				System.out.println("1. Ver campeones gratuitos");
				System.out.println("2. Mostrar última versión de juego");
				System.out.println("3. Ver Maestrias del jugador");

				int opcionRiot = scanner.nextInt();
				scanner.nextLine(); // Consumir el salto de línea

				switch (opcionRiot) {

				case 1 -> {
					Riot.showFreeChamps(API_KEY_RIOT);
				}
				case 2 -> {
					Riot.showAllVersions(API_KEY_RIOT);
				}
				case 3 -> {
					Riot.showInfoPlayerChamp(API_KEY_RIOT);
				}
			}
		}

			case 2 -> {
				System.out.println("Hello");
				System.out.println("Elige una opción:");
				System.out.println("1. Mostrar Estado de servidores");
				System.out.println("2. Mostrar rotacion de mapa");
				System.out.println("3. Ver evento");
				System.out.println("4. Ver jugador");

				int opcionRespawn = scanner.nextInt();
				scanner.nextLine(); // Consumir el salto de línea

				switch (opcionRespawn) {

				case 1 -> {

					Respawn.serverStatus(API_KEY_RESPAWN);
				}
				case 2 -> {

					Respawn.mapRotation(API_KEY_RESPAWN);
				}
				case 3 -> {

					Respawn.allEvents(API_KEY_RESPAWN);
				}
				case 4 -> {

					Respawn.stalkPlayer(API_KEY_RESPAWN);
				}

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

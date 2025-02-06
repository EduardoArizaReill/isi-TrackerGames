package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Champions {
    private static final String CHAMPIONS_JSON_URL_VERSIONS = "https://ddragon.leagueoflegends.com/api/versions.json";
    private static String LastFckingVersion = ""; // Se guardará la última versión
    private static final Map<Integer, String> CHAMPION_MAP = new HashMap<>();
    private static final List<String> ALL_VERSIONS = new ArrayList<>(); // Lista para almacenar todas las versiones

    static {
        try {
            // Cargar la última versión del JSON
            loadLastVersion();

            // Construir la URL del JSON de campeones con la última versión
            String CHAMPIONS_JSON_URL = "https://ddragon.leagueoflegends.com/cdn/" + LastFckingVersion + "/data/en_US/champion.json";
            // Cargar los campeones desde el JSON
            loadChampionsFromJson(CHAMPIONS_JSON_URL);
        } catch (IOException e) {
            System.out.println("❌ Error al cargar datos: " + e.getMessage());
        }
    }

    public static Integer getChampionIdByName(String name) {
        for (Map.Entry<Integer, String> entry : CHAMPION_MAP.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getKey(); // Devolver el ID del campeón
            }
        }
        return null; // Retorna null si no se encuentra
    }
    
    // Método para cargar la última versión desde el JSON de versiones
    private static void loadLastVersion() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CHAMPIONS_JSON_URL_VERSIONS).get().build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.body() != null) {
            String jsonData = response.body().string();
            JSONArray versions = new JSONArray(jsonData);

            // Limpiar la lista de versiones y agregar todas las versiones
            ALL_VERSIONS.clear();
            for (int i = 0; i < versions.length(); i++) {
                ALL_VERSIONS.add(versions.getString(i));
            }

            // Guardar la última versión (la primera en la lista)
            if (!ALL_VERSIONS.isEmpty()) {
                LastFckingVersion = ALL_VERSIONS.get(0);
                System.out.println("✔ Última versión cargada: " + LastFckingVersion);
            }
        } else {
            System.out.println("❌ Error al obtener el JSON de versiones.");
        }
    }

    // Método para cargar campeones desde el JSON usando la URL con la versión
    private static void loadChampionsFromJson(String championsJsonUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(championsJsonUrl).get().build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.body() != null) {
            String jsonData = response.body().string();
            JSONObject data = new JSONObject(jsonData).getJSONObject("data");

            // Iterar sobre cada campeón en el JSON
            for (String championKey : data.keySet()) {
                JSONObject champion = data.getJSONObject(championKey);
                int id = champion.getInt("key"); // Obtener el ID del campeón
                String name = champion.getString("name"); // Obtener el nombre del campeón

                // Agregar al mapa
                CHAMPION_MAP.put(id, name);
            }

            System.out.println("✔ Campeones cargados correctamente desde el JSON.");
        } else {
            System.out.println("❌ Error al obtener el JSON de campeones.");
        }
    }

    // Método público para obtener el nombre del campeón a partir de su ID
    public static String getChampionName(int id) {
        return CHAMPION_MAP.getOrDefault(id, "Desconocido");
    }

    // Método público para obtener la última versión
    public static String getLastVersion() {
        return LastFckingVersion;
    }

    // Método público para obtener todas las versiones
    public static List<String> getAllVersions() {
        return ALL_VERSIONS;
    }
}

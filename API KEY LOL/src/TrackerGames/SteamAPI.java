package TrackerGames;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SteamAPI {

    private static final String BASE_URL_GAMES = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";

    // Método para obtener los nombres de los juegos
    public static List<String> getGameNames() throws IOException {
        OkHttpClient client = new OkHttpClient();
        List<String> gameNames = new ArrayList<>();

        // Realizar la solicitud para obtener la lista de juegos
        Request request = new Request.Builder().url(BASE_URL_GAMES).get().build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.body() != null) {
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);

            // Extraer los nombres de los juegos
            JSONArray games = jsonObject.getJSONObject("applist").getJSONArray("apps");
            for (int i = 0; i < games.length(); i++) {
                JSONObject game = games.getJSONObject(i);
                String name = game.getString("name");
                gameNames.add(name);
            }
        } else {
            System.out.println("❌ Error al obtener la lista de juegos.");
        }

        return gameNames;
    }
}

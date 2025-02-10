🎮 isi-TrackerGames
isi-TrackerGames es una aplicación que permite a los jugadores de League of Legends y Apex Legends obtener información en tiempo real sobre sus estadísticas, rendimiento y detalles del juego mediante APIs oficiales.

🚀 Características principales
✅ Consulta información del jugador en LoL (nivel, rango, estadísticas).
✅ Descubre los campeones gratuitos semanales en League of Legends.
✅ Obtén detalles sobre la maestría de tus campeones en LoL.
✅ Revisa la rotación de mapas en Apex Legends en tiempo real.
✅ Obtén las estadísticas de los jugadores en Apex Legends.
✅ Compatibilidad con múltiples regiones y plataformas (Europe, NA, JP, Origin, etc.).
✅ Interfaz de línea de comandos sencilla y funcional.

🔧 Requisitos
Para ejecutar el proyecto, asegúrate de contar con:

☕ Java 17 o superior.
📦 Dependencias gestionadas con Maven (OkHttp, JSON).
🔑 Claves de API válidas para Riot Games API y Apex Legends API.
🛠 Instalación
1️⃣ Clona este repositorio


🌍 APIs utilizadas
🔹 League of Legends API
📌 /lol/summoner/v4/summoners/by-name/{nombre} → Datos del jugador.
📌 /lol/platform/v3/champion-rotations → Campeones gratuitos.
📌 /lol/champion-mastery/v4/champion-masteries/by-puuid/{puuid}/by-champion/{id} → Maestría de campeón.

🔹 Apex Legends API
📌 /maprotation → Rotación de mapas en Apex Legends.
📌 /bridge?platform={platform}&player={player} → Estadísticas del jugador en Apex.

🏗 Roadmap (Mejoras futuras)
🔜 Integración con Steam API para obtener datos de otros juegos.
🔜 Soporte para Valorant API.
🔜 Interfaz gráfica con JavaFX.
🔜 Historial de partidas guardadas localmente.


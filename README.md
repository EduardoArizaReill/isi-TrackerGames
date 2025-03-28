# isi-TrackerGames

 🕹️ isi-TrackerGames
isi-TrackerGames es una plataforma web desarrollada con el objetivo de ofrecer un sistema de búsqueda de perfiles y estadísticas de jugadores para distintos videojuegos competitivos, como Apex Legends y League of Legends. Utiliza APIs oficiales para obtener información en tiempo real sobre jugadores, campeones, leyendas, rangos y estadísticas, almacenando dichos datos en una base de datos para su posterior análisis o visualización.

Este proyecto ha sido construido como una solución práctica para aprender y aplicar tecnologías modernas de desarrollo web, back-end y bases de datos relacionales.

🚀 Funcionalidades principales

🔍 Búsqueda de jugadores
Consulta perfiles introduciendo el nombre de usuario y plataforma.

Visualiza datos como rango, nivel, leyenda/campeón principal, puntos de maestría y fecha de la última partida.

🗂️ Integración con API externas
Apex Legends API (Mozambiquehe.re) para obtener perfiles, rotaciones de mapas, eventos activos y estado de servidores.

Riot Games API para recuperar información detallada de cuentas y maestría de campeones en League of Legends.

🧠 Almacenamiento en base de datos
Guarda automáticamente los datos relevantes de cada jugador consultado en una base de datos MySQL.

Tablas separadas para cada juego: respawn_players para Apex, champion_mastery para LoL.

🧑‍💻 Registro e inicio de sesión
Módulo de autenticación de usuarios con sistema de login y registro.

Gestión básica de credenciales en la tabla users.

🧰 Tecnologías utilizadas

🔧 Back-End
Java 21 con Spring Boot

Hibernate / JPA para persistencia de datos

MySQL como sistema de gestión de base de datos

🌐 Front-End
HTML5 + CSS3 para la interfaz de usuario

JavaScript (Fetch API) para llamadas asíncronas a servicios REST

Diseño responsive y estilizado con una experiencia clara y moderna

📦 Otros
Postman para pruebas de endpoints

Git y GitHub para control de versiones y colaboración

🔗 Páginas incluidas
index.html: Página principal

login.html / register.html: Autenticación

apex.html: Consulta de jugadores de Apex Legends

league.html: Consulta de maestría en League of Legends

champ.html: Campeones gratuitos de LoL

versions.html: Versiones disponibles del juego

mapas.html: Rotación de mapas en Apex

servidor.html: Estado de los servidores de Apex

trackergames.html: Menú general de la plataforma

📌 Consideraciones
Este proyecto es educativo y usa claves API públicas, por lo que su uso en producción debe incluir protección, límites de seguridad, validación, y almacenamiento seguro.

Las contraseñas actualmente no están cifradas en la base de datos (por motivos didácticos).

💬 Contribuciones
Cualquier sugerencia, mejora o reporte de error es bienvenido. Puedes abrir un issue o enviar un pull request.

📅 Estado del proyecto
En desarrollo activo. Se siguen añadiendo mejoras visuales, validación de datos y nuevas funcionalidades como dashboards de estadísticas y rankings.



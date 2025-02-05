<h1 align="center">Primer Sprint Proyecto Final</h1>
<h2 align="center">Flash Meet</h2><br>

<p align="center">  
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/Proyecto.png">  
</p>

<h3> Splash Screen <h3>
<p align="center">  
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/Splash.png">  
</p>

<hr>

<h3> Login </h3>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/login.png" align="left" style="margin-right: 10px;" height="300">
    <p>Inicio<br>
Al abrir la aplicación, aparece la pantalla de splash screen.<br>
Luego, se muestra la pantalla de Login.<br>
Si el usuario tiene cuenta, inicia sesión con su correo y contraseña. (Llamada a la API: Autenticación de usuario - POST /api/login) <br>
Si no recuerda su contraseña, puede solicitar la restauración. (Llamada a la API: Recuperación de contraseña - POST /api/password/reset) <br>
Si no tiene cuenta, elige la opción Registrar.
</p>
</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/registro.png" align="left" style="margin-right: 10px;" height="300">
    <p>Registro<br>
Se muestra la pantalla de registro donde el usuario debe completar los datos solicitados. (Llamada a la API: Registro de usuario - POST /api/register) <br>
Una vez registrado, accede a la pantalla principal de la aplicación.

</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/main.png" align="left" style="margin-right: 10px;" height="300">
    <p>Main<br>
La pantalla principal ofrece cuatro opciones: <br>
- Perfil <br>
- Amigos<br>
- Ajustes<br>
- Meet
</p>
</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/peril.png" align="left" height="300">
    <p>Perfil<br>
Al hacer clic en la foto de perfil, el usuario puede: <br>
- Cargar hasta 6 fotos. (Llamada a la API: Subida de imágenes del perfil - POST /api/profile/upload-images) <br>
- Ver sus datos personales. (Llamada a la API: Obtener datos del perfil - GET /api/profile) <br>
- Escribir una descripción para que otros lo conozcan mejor. (Llamada a la API: Actualizar información del perfil - PUT /api/profile/update)
</p>
</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/amigos.png" align="left" style="margin-right: 10px;" height="300">
    <p>Amigos<br>
- Se muestra una lista de chats con amigos. (Llamada a la API: Obtener lista de chats y amigos - GET /api/friends) <br>
- Posibilidad de reanudar conversaciones previas. (Llamada a la API: Cargar historial de chat - GET /api/chats/{friend_id})
</p>
</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/ajustes.png" align="left" style="margin-right: 10px;" height="300">
    <p>Ajustes<br>
- Modificación de preferencias de búsqueda para conocer gente. (Llamada a la API: Guardar preferencias de búsqueda - PUT /api/settings/search-preferences) <br>
- Opciones para cerrar sesión o eliminar la cuenta. (Llamada a la API: Cerrar sesión - POST /api/logout / Eliminar cuenta - DELETE /api/user/delete)
</p>
</div>

<hr>

<div align="center">
    <img src="https://github.com/kiqueee11/Proyecto-Marzo-Android/blob/master/Imagenes/chat.png" align="center style="margin-right: 10px;" height="300">
    <p>Meet<br>
- Función para conectar de manera aleatoria con otros usuarios para chatear. (Llamada a la API: Conexión aleatoria con usuario - GET /api/match)<br>
- Se inicia un chat de 5 minutos sin revelar la identidad de las personas. (Llamada a la API: Inicio de chat anónimo - POST /api/chat/start)<br>
- Al finalizar el tiempo, se pregunta si ambos desean revelar su identidad. (Llamada a la API: Confirmación de identidad mutua - POST /api/chat/reveal-identity)<br>
- Si ambos aceptan, se muestra el nombre y la foto de perfil. (Llamada a la API: Mostrar identidad de usuario - GET /api/user/profile/{user_id})<br>
En este punto, se pueden realizar las siguientes acciones:<br>
- Solicitar una videollamada. (Llamada a la API: Iniciar videollamada - POST /api/call/start)<br>
- Ver el perfil de la persona y agregarla como amigo. (Llamada a la API: Enviar solicitud de amistad - POST /api/friends/add)<br>
- Reportar a la persona o cerrar la conversación. (Llamada a la API: Reportar usuario - POST /api/report / Cerrar chat - POST /api/chat/close)<br>
- Si cualquiera de los dos elige no revelar su identidad, la conversación se cierra y el sistema conecta al usuario con otra persona aleatoriamente. (Llamada a la API: Finalizar chat y buscar nueva conexión - POST /api/chat/next-match)
</p>
</div>


// Importamos axios, la librería que usamos para hacer peticiones HTTP
import axios from "axios";

// Creamos una instancia de axios ya configurada, para no repetir código en cada petición
export const axiosClient = axios.create({
  baseURL: "http://localhost:3000",   // Dirección base del backend (todas las rutas se le agregan a esta URL)
  headers: {
    "Content-Type": "application/json",   // Le decimos al servidor que enviamos datos en formato JSON
  },
});

// (Opcional) Adjuntar token automáticamente a futuras requests
// Esto es un "interceptor": se ejecuta antes de que salga cada petición
axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken"); // verificar si el token está almacenado en localStorage
  if (token) config.headers.Authorization = `Bearer ${token}`;    // Si hay token, lo agregamos a los headers para autenticar la petición
  return config;    // Devolvemos la configuración (con o sin token) para que la petición continúe
});
// Importa el hook useState de React para gestionar estados locales
import { useState } from "react";
// Importa el cliente configurado de Axios con la URL base de la API
import { axiosClient } from "../api/axiosClient";
import "./Login.css";

// Exporta por defecto la función del componente visual Login
export default function Login() {
  // Estado para el campo de correo con un valor inicial por defecto para pruebas
  const [email, setEmail] = useState("esteban@gmail.com");
  // Estado para el campo de contraseña con un valor inicial por defecto para pruebas
  const [password, setPassword] = useState("jejeje");
  // Estado booleano para indicar si la solicitud de autenticación está en progreso
  const [loading, setLoading] = useState(false);
  // Estado para almacenar y desplegar mensajes de error surgidos durante la autenticación
  const [error, setError] = useState("");

  // Función controladora para procesar el envío del formulario de inicio de sesión
  const handleSubmit = async (e) => {
    // Evita la recarga automática del navegador al enviar el formulario
    e.preventDefault();
    // Limpia mensajes de error previos
    setError("");
    // Activa el estado de carga para bloquear el botón y mostrar el texto 'Entrando...'
    setLoading(true);

    try {
      // Envía una petición POST al endpoint /login con las credenciales ingresadas
      const res = await axiosClient.post("/login", { email, password });

      // Desestructura el token de acceso y la información del usuario recibidos de la respuesta
      const { accessToken, user } = res.data;

      // Guarda el token JWT en el almacenamiento local del navegador para futuras peticiones protegidas
      localStorage.setItem("accessToken", accessToken);
      // Serializa el objeto del usuario en formato JSON y lo almacena en localStorage
      localStorage.setItem("user", JSON.stringify(user));
      window.location.href = "/Sitios"; // Redirige al usuario a la página de Sitios tras un inicio de sesión exitoso


      // Muestra una notificación emergente confirmando el inicio de sesión exitoso
      alert(`Login OK. Bienvenido ${user.email}`);
    } catch (err) {
      // Intenta extraer el mensaje de error del backend, del estado HTTP o del cliente de red
      const msg =
        err?.response?.data?.message ||
        err?.response?.statusText ||
        err?.message ||
        "Error desconocido";
      // Asigna el texto resultante al estado 'error' para mostrarlo en pantalla
      setError(msg);
    } finally {
      // Desactiva el indicador de carga sin importar si la petición fue exitosa o falló
      setLoading(false);
    }
  };

  // Renderiza la estructura visual del componente en JSX
  return (
    // Contenedor principal de dos columnas
    <div className="login-page">
      {/* Panel izquierdo con la identidad de la marca */}
      <div className="login-brand-panel">
        <div className="login-brand-top">
          <div className="login-logo">
            <span className="login-logo-icon">D</span>
            <span className="login-logo-name">DataStock</span>
          </div>
          <h1>Gestión inteligente de inventario</h1>
          <p>
            Control centralizado de stock, remisiones, traslados y
            movimientos con trazabilidad completa.
          </p>
        </div>

        <ul className="login-features">
          <li>Bodega central + tiendas</li>
          <li>Remisiones con trazabilidad</li>
          <li>Alertas automáticas de stock</li>
          <li>Roles y permisos granulares</li>
        </ul>
      </div>

      {/* Panel derecho con el formulario */}
      <div className="login-form-panel">
        <div className="login-form-box">
          <h2>Iniciar sesión</h2>
          <p>Ingresa tus credenciales para continuar</p>

          {/* Formulario vinculado a la función handleSubmit */}
          <form onSubmit={handleSubmit}>
            <div className="login-field">
              {/* Etiqueta del campo de correo electrónico */}
              <label>Correo electrónico</label>
              {/* Input controlado para el correo */}
              <input
                type="email"
                placeholder="correo@ejemplo.com"
                value={email}
                // Actualiza el estado 'email' cada vez que el usuario escribe
                onChange={(e) => setEmail(e.target.value)}
                // Ayuda al navegador con la sugerencia de autocompletado para correos
                autoComplete="email"
              />
            </div>

            <div className="login-field">
              {/* Etiqueta del campo de la contraseña */}
              <label>Contraseña</label>
              {/* Input controlado y oculto para la contraseña */}
              <input
                type="password"
                value={password}
                // Actualiza el estado 'password' con el texto ingresado
                onChange={(e) => setPassword(e.target.value)}
                // Sugiere al gestor de contraseñas el uso de la clave actual del usuario
                autoComplete="current-password"
              />
            </div>

            {/* Renderizado condicional: muestra un mensaje en rojo si la variable 'error' tiene contenido */}
            {error ? <div className="login-error">{error}</div> : null}

            {/* Botón de envío que se deshabilita durante la petición y conmuta su texto entre 'Entrando...' y 'Entrar' */}
            <button disabled={loading} type="submit" className="login-submit">
              {loading ? "Entrando..." : "Entrar"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

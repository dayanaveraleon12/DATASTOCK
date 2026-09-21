// Importa los hooks useEffect (para efectos de ciclo de vida) y useState (para estado local) desde React
import { useEffect, useState } from "react";
// Importa la instancia de Axios configurada con la URL base y cabeceras del backend
import { axiosClient } from "../api/axiosClient";
import "./Usuarios.css";

// Declara y exporta el componente funcional Usuarios
export default function Usuarios() {
  // Estado que almacena la lista de usuarios obtenida de la API (inicia como arreglo vacío)
  const [users, setUsuarios] = useState([]);
  // Estado booleano para indicar si los datos se están cargando y mostrar el mensaje correspondiente
  const [loading, setLoading] = useState(true);
  // Estado para capturar y desplegar mensajes de error en la interfaz
  const [error, setError] = useState("");
  // Estado para el texto ingresado en el buscador por correo
  const [search, setSearch] = useState("");

  // Estado para abrir o cerrar la ventana modal del formulario
  const [showForm, setShowForm] = useState(false);
  // Estado que guarda el ID del usuario en edición (null significa que se creará un usuario nuevo)
  const [editingId, setEditingId] = useState(null);
  // Estado agrupado en un objeto para manejar los campos del formulario (correo y contraseña)
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  // Función asíncrona para consultar el listado de usuarios desde el backend
  const cargarUsuarios = async () => {
    // Activa la señal visual de carga
    setLoading(true);
    // Limpia mensajes de error previos
    setError("");
    try {
      // Petición HTTP GET al endpoint /users
      const res = await axiosClient.get("/users");
      // navigate("/dashboard"); // si usas react-router
      // Guarda la lista de usuarios devuelta por la API en el estado 'users'
      setUsuarios(res.data);
      
    } catch (err) {
      // Captura fallos y almacena el mensaje de error de la respuesta o uno por defecto
      setError(
        err?.response?.data?.message || "No se pudieron cargar los usuarios"
      );
    } finally {
      // Desactiva el estado de carga al finalizar (sea con éxito o con error)
      setLoading(false);
    }
  };


  // Hook que ejecuta cargarUsuarios una sola vez al montarse el componente en pantalla
  useEffect(() => {
    cargarUsuarios();
  }, []);

  // Filtra los usuarios en memoria comparando su email con el término de búsqueda (sin distinguir mayúsculas)
  const usuariosFiltrados = users.filter((u) =>
    u.email?.toLowerCase().includes(search.toLowerCase())
  );

  // Prepara y abre el formulario para la creación de un nuevo usuario
  const abrirNuevo = () => {
    // Reinicia el ID a null indicando que es un nuevo registro
    setEditingId(null);
    // Vacía los campos de correo y contraseña en el estado del formulario
    setForm({ email: "", password: "" });
    // Despliega la ventana modal
    setShowForm(true);
  };

  // Prepara y abre el modal en modo edición cargando los datos del usuario seleccionado
  const abrirEditar = (u) => {
    // Guarda el ID del usuario a modificar
    setEditingId(u.id);
    // Carga el correo actual y deja el campo de contraseña vacío para no sobreescribirla por accidente
    setForm({ email: u.email || "", password: "" });
    // Muestra el modal del formulario
    setShowForm(true);
  };

  // Procesa el envío del formulario al hacer clic en Guardar o Crear
  const handleSubmit = async (e) => {
    // Cancela el comportamiento nativo de recarga de página del navegador
    e.preventDefault();
    // Limpia errores previos antes de iniciar la petición
    setError("");
    try {
      // Si existe un editingId, se procede a actualizar un usuario existente
      if (editingId) {
        // Prepara el objeto a enviar únicamente con el correo modificado
        const payload = { email: form.email };
        // Solo incluye la propiedad password si el usuario ingresó un nuevo valor
        if (form.password) payload.password = form.password;

        // Petición PATCH para aplicar actualización parcial al usuario seleccionado
        await axiosClient.patch(`/users/${editingId}`, payload);
        
        
        //window.location.href = "/";
      } else {
        // Si no hay editingId, se registra un usuario nuevo con POST enviando ambos datos
        await axiosClient.post("/users", {
          email: form.email,
          password: form.password,
        });
      }
      // Oculta el modal tras completar la operación
      setShowForm(false);
      // Recarga la tabla de usuarios con la información actualizada desde la base de datos
      cargarUsuarios();
    } catch (err) {
      // Muestra en la vista el error retornado por el servidor o un texto estándar
      setError(err?.response?.data?.message || "Error al guardar el usuario");
    }
  };

  // Gestiona la eliminación de un usuario por su identificador
  const handleEliminar = async (id) => {
    // Muestra diálogo de confirmación del navegador; si el usuario cancela, interrumpe la función
    if (!confirm("¿Eliminar este usuario?")) return;
    try {
      // Petición HTTP DELETE apuntando al ID del usuario
      await axiosClient.delete(`/users/${id}`);
      // Refresca la lista de usuarios tras el borrado
      cargarUsuarios();
    } catch (err) {
      // Muestra el mensaje si ocurre un error al eliminar
      setError(err?.response?.data?.message || "Error al eliminar el usuario");
    }
  };

  // Estructura visual JSX renderizada por el componente
  return (
    <div className="usuarios-page">
      {/* Cabecera con título y botón de creación */}
      <div className="usuarios-header">
        {/* Título de la sección */}
        <h2 className="usuarios-title">Usuarios</h2>
        {/* Dispara la apertura del formulario limpio */}
        <button onClick={abrirNuevo} className="usuarios-btn-primary">
          + Nuevo Usuario
        </button>
      </div>

      {/* Input controlado conectado a 'search' para filtrar por email */}
      <input
        placeholder="Buscar por correo..."
        value={search}
        // Actualiza el estado con el texto que escribe el usuario
        onChange={(e) => setSearch(e.target.value)}
        className="usuarios-search"
      />

      {/* Renderizado condicional: muestra la barra roja de alerta si la variable error tiene contenido */}
      {error ? <div className="usuarios-error">{error}</div> : null}

      {/* Renderizado condicional: muestra el texto de carga o la tabla con los registros */}
      {loading ? (
        <p className="usuarios-loading">Cargando...</p>
      ) : (
        <div className="usuarios-table-wrap">
          <table className="usuarios-table">
            <thead>
              <tr>
                <th>Correo electrónico</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {/* Recorre la lista filtrada de usuarios para armar las filas */}
              {usuariosFiltrados.map((u) => (
                // Clave única exigida por React para reconciliación en la tabla
                <tr key={u.id}>
                  {/* Muestra el email del usuario */}
                  <td>{u.email}</td>
                  {/* Columna de acciones por usuario */}
                  <td className="usuarios-actions">
                    {/* Botón para precargar este usuario en el modal y editarlo */}
                    <button onClick={() => abrirEditar(u)} className="usuarios-btn-edit">
                      Editar
                    </button>
                    {/* Botón para confirmar y borrar el registro */}
                    <button onClick={() => handleEliminar(u.id)} className="usuarios-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {/* Mensaje mostrado si ningún usuario coincide con la búsqueda */}
              {usuariosFiltrados.length === 0 && (
                <tr>
                  <td colSpan={2} className="usuarios-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* Renderizado condicional: despliega el fondo oscuro y el modal si showForm es true */}
      {showForm && (
        <div className="usuarios-modal-overlay">
          {/* Formulario que ejecuta handleSubmit al completarse */}
          <form onSubmit={handleSubmit} className="usuarios-modal">
            {/* Título dinámico que conmuta entre edición y creación */}
            <h3>{editingId ? "Editar usuario" : "Nuevo usuario"}</h3>

            <div className="usuarios-field">
              <label>Correo electrónico</label>
              {/* Campo obligatorio de tipo email enlazado con form.email */}
              <input
                required
                type="email"
                value={form.email}
                // Conserva los demás campos del objeto 'form' y actualiza solo 'email'
                onChange={(e) =>
                  setForm({ ...form, email: e.target.value })
                }
              />
            </div>

            <div className="usuarios-field">
              {/* Etiqueta dinámica: avisa que la contraseña es opcional al editar */}
              <label>
                Contraseña {editingId ? "(dejar en blanco para no cambiarla)" : ""}
              </label>
              {/* Campo de contraseña obligatorio solo al crear (required={!editingId}) */}
              <input
                required={!editingId}
                type="password"
                value={form.password}
                // Conserva el objeto previo y actualiza el campo 'password'
                onChange={(e) => setForm({ ...form, password: e.target.value })}
              />
            </div>

            {/* Botonera de control del modal */}
            <div className="usuarios-modal-actions">
              {/* Botón tipo 'button' para cerrar sin enviar ni validar el formulario */}
              <button type="button" onClick={() => setShowForm(false)} className="usuarios-btn-cancel">
                Cancelar
              </button>
              {/* Botón tipo submit que cambia dinámicamente su texto */}
              <button type="submit" className="usuarios-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

// Importa los hooks useState (para variables de estado) y useEffect (para efectos de ciclo de vida) desde React
import { useEffect, useState } from "react";
// Importa la instancia configurada de Axios con la URL base del backend
import { axiosClient } from "../api/axiosClient";
import "./Sitios.css";

// Declara y exporta el componente principal funcional Sitios
export default function Sitios() {
  // Estado para almacenar la lista de sitios obtenida de la base de datos (inicia como array vacío)
  const [sitios, setSitios] = useState([]);
  // Estado booleano para mostrar el texto de carga mientras se completa una petición
  const [loading, setLoading] = useState(true);
  // Estado para capturar y renderizar mensajes de error en pantalla
  const [error, setError] = useState("");
  // Estado para almacenar el valor actual de la barra de búsqueda
  const [search, setSearch] = useState("");

  // Estado para controlar si la ventana modal del formulario se muestra o permanece oculta
  const [showForm, setShowForm] = useState(false);
  // Estado para guardar el ID del sitio en edición; si es null, indica que se creará uno nuevo
  const [editingId, setEditingId] = useState(null);
  // Estado para enlazar el valor del campo de texto donde se escribe el nombre del sitio
  const [nombre, setNombre] = useState("");

  // Función asíncrona para consultar el listado completo de sitios desde el servidor
  const cargarSitios = async () => {
    // Activa la pantalla/mensaje de carga
    setLoading(true);
    // Limpia mensajes de error previos
    setError("");
    try {
      // Realiza la petición HTTP GET al endpoint /sitios
      const res = await axiosClient.get("/sitios");
      // Guarda en el estado 'sitios' el arreglo retornado por la API
      setSitios(res.data);
    } catch (err) {
      // Si la API falla, captura el mensaje devuelto por el backend o asigna uno por defecto
      setError(err?.response?.data?.message || "No se pudieron cargar los sitios");
    } finally {
      // Desactiva el estado de carga al terminar el proceso, sea exitoso o con fallo
      setLoading(false);
    }
  };

  // Hook que ejecuta cargarSitios() únicamente una vez cuando el componente se monta por primera vez
  useEffect(() => {
    cargarSitios();
  }, []);

  // Filtra en memoria los sitios comparando el nombre con el texto de búsqueda (ignorando mayúsculas/minúsculas)
  const sitiosFiltrados = sitios.filter((s) =>
    s.nombre?.toLowerCase().includes(search.toLowerCase())
  );

  // Prepara el formulario para registrar un nuevo sitio
  const abrirNuevo = () => {
    // Limpia el ID en edición para marcar que es una creación
    setEditingId(null);
    // Vacía el campo de texto del nombre
    setNombre("");
    // Muestra la ventana modal del formulario
    setShowForm(true);
  };

  // Prepara el formulario para modificar un sitio existente
  const abrirEditar = (s) => {
    // Guarda el ID del elemento seleccionado
    setEditingId(s.id);
    // Precarga el nombre actual del sitio en el input
    setNombre(s.nombre || "");
    // Abre la ventana modal con los datos cargados
    setShowForm(true);
  };

  // Maneja el envío del formulario al presionar Guardar o Crear
  const handleSubmit = async (e) => {
    // Cancela el comportamiento por defecto del formulario de recargar el navegador
    e.preventDefault();
    // Limpia cualquier error anterior
    setError("");
    try {
      // Si editingId tiene un valor, actualiza el registro existente usando PATCH
      if (editingId) {
        await axiosClient.patch(`/sitios/${editingId}`, { nombre });
      } else {
        // Si editingId es null, crea un nuevo registro mediante POST
        await axiosClient.post("/sitios", { nombre });
      }
      // Oculta la ventana modal del formulario
      setShowForm(false);
      // Vuelve a consultar los sitios al servidor para refrescar la tabla
      cargarSitios();
    } catch (err) {
      // Captura y muestra un error si la solicitud falla
      setError(err?.response?.data?.message || "Error al guardar el sitio");
    }
  };

  // Maneja el proceso de borrado de un sitio según su identificador
  const handleEliminar = async (id) => {
    // Solicita confirmación al usuario en una alerta del navegador; si cancela, detiene la ejecución
    if (!confirm("¿Eliminar este sitio?")) return;
    try {
      // Realiza la petición HTTP DELETE apuntando al ID correspondiente
      await axiosClient.delete(`/sitios/${id}`);
      // Actualiza la lista consultando de nuevo los datos vigentes al backend
      cargarSitios();
    } catch (err) {
      // Captura el fallo y guarda el mensaje de error en el estado
      setError(err?.response?.data?.message || "Error al eliminar el sitio");
    }
  };

  // Retorno del componente con la estructura visual en JSX
  return (
    // Contenedor principal
    <div className="sitios-page">
      {/* Contenedor flex para la barra de título y el botón superior */}
      <div className="sitios-header">
        {/* Título de la vista */}
        <h2 className="sitios-title">Sitios</h2>
        {/* Botón para iniciar la creación de un nuevo sitio */}
        <button onClick={abrirNuevo} className="sitios-btn-primary">
          + Nuevo Sitio
        </button>
      </div>

      {/* Input de búsqueda conectado al estado 'search' */}
      <input
        placeholder="Buscar sitio..."
        value={search}
        // Actualiza el término de búsqueda con cada pulsación de tecla
        onChange={(e) => setSearch(e.target.value)}
        className="sitios-search"
      />

      {/* Renderizado condicional: muestra la alerta de error si la variable no está vacía */}
      {error ? <div className="sitios-error">{error}</div> : null}

      {/* Renderizado condicional: muestra 'Cargando...' o renderiza la tabla de datos */}
      {loading ? (
        <p className="sitios-loading">Cargando...</p>
      ) : (
        <div className="sitios-table-wrap">
          <table className="sitios-table">
            <thead>
              <tr>
                <th>Nombre</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {/* Recorre el arreglo de sitios filtrados para generar las filas */}
              {sitiosFiltrados.map((s) => (
                // Identificador único requerido por React para cada fila
                <tr key={s.id}>
                  {/* Celda con el nombre del sitio */}
                  <td>{s.nombre}</td>
                  {/* Celda con los botones de acción para cada fila */}
                  <td className="sitios-actions">
                    {/* Botón que carga los datos del sitio actual en el modal para editar */}
                    <button onClick={() => abrirEditar(s)} className="sitios-btn-edit">
                      Editar
                    </button>
                    {/* Botón que ejecuta la eliminación del sitio actual */}
                    <button onClick={() => handleEliminar(s.id)} className="sitios-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {/* Si el filtro no arroja resultados, despliega este aviso en la tabla */}
              {sitiosFiltrados.length === 0 && (
                <tr>
                  <td colSpan={2} className="sitios-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* Renderizado condicional: monta el fondo oscuro y el modal si showForm es true */}
      {showForm && (
        <div className="sitios-modal-overlay">
          {/* Formulario que activa handleSubmit al procesarse */}
          <form onSubmit={handleSubmit} className="sitios-modal">
            {/* Título dinámico según se esté creando o editando */}
            <h3>{editingId ? "Editar sitio" : "Nuevo sitio"}</h3>

            <div className="sitios-field">
              <label>Nombre</label>
              {/* Input obligatorio conectado con el estado 'nombre' */}
              <input
                required
                value={nombre}
                // Almacena el valor ingresado por el usuario en el estado
                onChange={(e) => setNombre(e.target.value)}
              />
            </div>

            {/* Grupo de botones para cancelar o procesar el formulario */}
            <div className="sitios-modal-actions">
              {/* Botón secundario para descartar cambios y cerrar el modal */}
              <button type="button" onClick={() => setShowForm(false)} className="sitios-btn-cancel">
                Cancelar
              </button>
              {/* Botón principal de submit cuyo texto cambia dinámicamente */}
              <button type="submit" className="sitios-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

// Importa los hooks useState (gestión de estado) y useEffect (ciclo de vida) desde React
import { useEffect, useState } from "react";
// Importa el módulo de servicios con métodos predefinidos para la API de tipoDocumentos
import { tipoDocumentos } from "../api/tipoDocumentos";
import "./TipoDocumentos.css";

// Declara y exporta por defecto el componente TipoDocumentos
export default function TipoDocumentos() {
  // Estado para almacenar la lista de tipos de documentos recibida de la API (inicia vacío)
  const [items, setItems] = useState([]);
  // Estado para indicar si una solicitud HTTP está en curso y mostrar el mensaje de carga
  const [loading, setLoading] = useState(true);
  // Estado para almacenar mensajes de error en caso de que alguna petición falle
  const [error, setError] = useState("");
  // Estado para controlar el texto ingresado en el buscador
  const [search, setSearch] = useState("");

  // Estado booleano para abrir o cerrar la ventana modal del formulario
  const [showForm, setShowForm] = useState(false);
  // Estado que guarda el ID del registro en edición (null significa que se creará uno nuevo)
  const [editingId, setEditingId] = useState(null);
  // Estado vinculado al campo de texto para el nombre o sigla del documento
  const [nombreDocumento, setNombreDocumento] = useState("");

  // Función asíncrona para consultar los registros desde el backend
  const cargarItems = async () => {
    // Enciende el indicador de carga
    setLoading(true);
    // Limpia mensajes de error previos
    setError("");
    try {
      // Llama al servicio tipoDocumentos.list() equivalente a GET /tipoDocumentos
      const res = await tipoDocumentos.list();
      // Guarda los datos obtenidos en el estado 'items'
      setItems(res.data);
    } catch (err) {
      // Captura y almacena el mensaje de error de la respuesta o uno genérico
      setError(
        err?.response?.data?.message || "No se pudieron cargar los tipos de documento"
      );
    } finally {
      // Apaga el indicador de carga al finalizar la operación (éxito o error)
      setLoading(false);
    }
  };

  // Hook que ejecuta cargarItems() únicamente una vez cuando el componente se monta
  useEffect(() => {
    cargarItems();
  }, []);

  // Filtra los items comparando 'nombre_documento' con el texto de búsqueda en minúsculas
  const itemsFiltrados = items.filter((it) =>
    it.nombre_documento?.toLowerCase().includes(search.toLowerCase())
  );

  // Prepara el estado para crear un nuevo registro y abre el formulario
  const abrirNuevo = () => {
    // Establece el ID en null para indicar modo creación
    setEditingId(null);
    // Limpia el campo del nombre del documento
    setNombreDocumento("");
    // Muestra el modal
    setShowForm(true);
  };

  // Carga los datos del elemento seleccionado en el formulario y lo abre en modo edición
  const abrirEditar = (it) => {
    // Almacena el ID del documento a modificar
    setEditingId(it.id);
    // Asigna el nombre existente al input del formulario
    setNombreDocumento(it.nombre_documento || "");
    // Muestra el modal
    setShowForm(true);
  };

  // Maneja el evento de envío del formulario para guardar o actualizar
  const handleSubmit = async (e) => {
    // Evita el refresco automático de la página
    e.preventDefault();
    // Limpia posibles errores previos
    setError("");
    try {
      // Si existe un ID en edición, envía una petición PUT de actualización
      if (editingId) {
        await tipoDocumentos.update(editingId, { nombre_documento: nombreDocumento });
      } else {
        // Si no hay ID, envía una petición POST de creación
        await tipoDocumentos.create({ nombre_documento: nombreDocumento });
      }
      // Cierra la ventana modal tras guardar
      setShowForm(false);
      // Vuelve a consultar la lista actualizada desde el backend
      cargarItems();
    } catch (err) {
      // Captura y guarda el mensaje si ocurre un error al procesar la solicitud
      setError(err?.response?.data?.message || "Error al guardar el tipo de documento");
    }
  };

  // Maneja el proceso de eliminación de un tipo de documento
  const handleEliminar = async (id) => {
    // Muestra un cuadro de confirmación nativo; si el usuario cancela, detiene la función
    if (!confirm("¿Eliminar este tipo de documento?")) return;
    try {
      // Ejecuta la petición DELETE correspondiente usando el ID recibido
      await tipoDocumentos.remove(id);
      // Refresca la lista para excluir el registro eliminado
      cargarItems();
    } catch (err) {
      // Muestra un error en pantalla si el borrado falla
      setError(err?.response?.data?.message || "Error al eliminar el tipo de documento");
    }
  };

  // Retorna la interfaz de usuario estructurada en JSX
  return (
    <div className="tipodoc-page">
      <div className="tipodoc-header">
        <h2 className="tipodoc-title">Tipo Documentos</h2>
        <button onClick={abrirNuevo} className="tipodoc-btn-primary">
          + Nuevo Tipo de Documento
        </button>
      </div>

      <input
        placeholder="Buscar tipo de documento..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="tipodoc-search"
      />

      {error ? <div className="tipodoc-error">{error}</div> : null}

      {loading ? (
        <p className="tipodoc-loading">Cargando...</p>
      ) : (
        <div className="tipodoc-table-wrap">
          <table className="tipodoc-table">
            <thead>
              <tr>
                <th>Nombre documento</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {itemsFiltrados.map((it) => (
                <tr key={it.id}>
                  <td>{it.nombre_documento}</td>
                  <td className="tipodoc-actions">
                    <button onClick={() => abrirEditar(it)} className="tipodoc-btn-edit">
                      Editar
                    </button>
                    <button onClick={() => handleEliminar(it.id)} className="tipodoc-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {itemsFiltrados.length === 0 && (
                <tr>
                  <td colSpan={2} className="tipodoc-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <div className="tipodoc-modal-overlay">
          <form onSubmit={handleSubmit} className="tipodoc-modal">
            <h3>{editingId ? "Editar tipo de documento" : "Nuevo tipo de documento"}</h3>

            <div className="tipodoc-field">
              <label>Nombre documento (sigla)</label>
              <input
                required
                placeholder="Ej: CC, TI, CE..."
                value={nombreDocumento}
                onChange={(e) => setNombreDocumento(e.target.value)}
              />
            </div>

            <div className="tipodoc-modal-actions">
              <button type="button" onClick={() => setShowForm(false)} className="tipodoc-btn-cancel">
                Cancelar
              </button>
              <button type="submit" className="tipodoc-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

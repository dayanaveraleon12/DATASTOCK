// Importa los hooks (función especial de React) useEffect y useState de React para manejar el estado y los efectos secundarios en el componente
import { useEffect, useState } from "react";
import { axiosClient } from "../api/axiosClient";
import "./Presentaciones.css";

// Define el componente Presentaciones que se exporta por defecto
export default function Presentaciones() { 
  const [presentaciones, setPresentaciones] = useState([]); // Estado para almacenar la lista de categorías
  const [loading, setLoading] = useState(true); // Estado para indicar si los datos están siendo cargados
  const [error, setError] = useState(""); // Estado para almacenar mensajes de error
  const [search, setSearch] = useState(""); // Estado para almacenar el término de búsqueda ingresado por el usuario

  const [showForm, setShowForm] = useState(false); // Estado para controlar la visibilidad del formulario de creación/edición de categorías
  const [editingId, setEditingId] = useState(null); // Estado para almacenar el ID de la categoría que se está editando (null si se está creando una nueva categoría)
  const [nombre, setNombre] = useState(""); // Estado para almacenar el nombre de la categoría que se está creando o editando

  const cargarPresentaciones = async () => { // Función asíncrona para cargar las categorías desde la API
    setLoading(true); // Indica que la carga de datos ha comenzado
    setError(""); // Limpia cualquier mensaje de error previo
    try {
      // GET http://localhost:3000/presentaciones
      const res = await axiosClient.get("/presentaciones"); // Realiza una solicitud GET a la API para obtener la lista de categorías
      setPresentaciones(res.data); // Actualiza el estado con la lista de categorías obtenida
    } catch (err) { // Maneja cualquier error que ocurra durante la solicitud
      setError(
        err?.response?.data?.message || "No se pudieron cargar las presentaciones"
      );
    } finally { // Se ejecuta después de intentar cargar las categorías, sin importar si tuvo éxito o falló
      setLoading(false);
    }
  };

  useEffect(() => {  // Hook de efecto que se ejecuta una vez al montar el componente
    cargarPresentaciones();  // Llama a la función para cargar las categorías desde la API
  }, []);

  // Filtra las categorías según el término de búsqueda ingresado por el usuario
  const PresentacionesFiltradas = presentaciones.filter((c) =>  
    c.nombre?.toLowerCase().includes(search.toLowerCase())
  );

  // Función para abrir el formulario de creación de una nueva categoría
  const abrirNuevo = () => { 
    setEditingId(null);
    setNombre("");
    setShowForm(true);
  };

  // Función para abrir el formulario de edición de una categoría existente
  const abrirEditar = (c) => { 
    setEditingId(c.id);
    setNombre(c.nombre || "");
    setShowForm(true);
  };

  // Función para manejar el envío del formulario de creación/edición de categorías
  const handleSubmit = async (e) => {
    e.preventDefault(); // Evita que el formulario se envíe de la manera tradicional, lo que recargaría la página
    setError("");
    try {   // Si editingId tiene un valor, significa que estamos editando una categoría existente
      if (editingId) {
        // PATCH /Presentaciones/:id
        await axiosClient.patch(`/Presentaciones/${editingId}`, { nombre });  // Realiza una solicitud PATCH a la API para actualizar la categoría con el ID especificado
      } else {
        // POST /Presentaciones
        await axiosClient.post("/Presentaciones", { nombre });
      }
      setShowForm(false);   // Cierra el formulario después de guardar los cambios
      cargarPresentaciones();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al guardar la presentación");
    }
  };

  // Función para manejar la eliminación de una categoría
  const handleEliminar = async (id) => {
    if (!confirm("¿Eliminar esta presentación?")) return;  // Muestra un cuadro de confirmación antes de eliminar la categoría
    try {   // Si el usuario confirma, realiza una solicitud DELETE a la API para eliminar la categoría con el ID especificado
      // DELETE /Presentaciones/:id
      await axiosClient.delete(`/Presentaciones/${id}`);
      cargarPresentaciones();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al eliminar la presentación");
    }
  };

  // Renderiza el componente Presentaciones 
  return (
    <div className="presentaciones-page">
      <div className="presentaciones-header">
        <h2 className="presentaciones-title">Presentaciones</h2>
        <button onClick={abrirNuevo} className="presentaciones-btn-primary">
          + Nueva Presentacion
        </button>
      </div>

      <input
        placeholder="Buscar presentación..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="presentaciones-search"
      />

      {error ? <div className="presentaciones-error">{error}</div> : null}

      {loading ? (
        <p className="presentaciones-loading">Cargando...</p>
      ) : (
        <div className="presentaciones-table-wrap">
          <table className="presentaciones-table">
            <thead>
              <tr>
                <th>Nombre</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {PresentacionesFiltradas.map((c) => (
                <tr key={c.id}>
                  <td>{c.nombre}</td>
                  <td className="presentaciones-actions">
                    <button onClick={() => abrirEditar(c)} className="presentaciones-btn-edit">
                      Editar
                    </button>
                    <button onClick={() => handleEliminar(c.id)} className="presentaciones-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {PresentacionesFiltradas.length === 0 && (
                <tr>
                  <td colSpan={2} className="presentaciones-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <div className="presentaciones-modal-overlay">
          <form onSubmit={handleSubmit} className="presentaciones-modal">
            <h3>{editingId ? "Editar presentación" : "Nueva presentación"}</h3>

            <div className="presentaciones-field">
              <label>Nombre</label>
              <input
                required
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
              />
            </div>

            <div className="presentaciones-modal-actions">
              <button type="button" onClick={() => setShowForm(false)} className="presentaciones-btn-cancel">
                Cancelar
              </button>
              <button type="submit" className="presentaciones-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

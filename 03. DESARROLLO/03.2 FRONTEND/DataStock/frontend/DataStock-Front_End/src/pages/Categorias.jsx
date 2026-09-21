// Importa los hooks (función especial de React) useEffect y useState de React para manejar el estado y los efectos secundarios en el componente
import { useEffect, useState } from "react";
import { axiosClient } from "../api/axiosClient";
import "./Categorias.css";

// Define el componente Categorias que se exporta por defecto
export default function Categorias() { 
  const [categorias, setCategorias] = useState([]); // Estado para almacenar la lista de categorías
  const [loading, setLoading] = useState(true); // Estado para indicar si los datos están siendo cargados
  const [error, setError] = useState(""); // Estado para almacenar mensajes de error
  const [search, setSearch] = useState(""); // Estado para almacenar el término de búsqueda ingresado por el usuario

  const [showForm, setShowForm] = useState(false); // Estado para controlar la visibilidad del formulario de creación/edición de categorías
  const [editingId, setEditingId] = useState(null); // Estado para almacenar el ID de la categoría que se está editando (null si se está creando una nueva categoría)
  const [nombre, setNombre] = useState(""); // Estado para almacenar el nombre de la categoría que se está creando o editando

  const cargarCategorias = async () => { // Función asíncrona para cargar las categorías desde la API
    setLoading(true); // Indica que la carga de datos ha comenzado
    setError(""); // Limpia cualquier mensaje de error previo
    try {
      // GET http://localhost:3000/categorias
      const res = await axiosClient.get("/categorias"); // Realiza una solicitud GET a la API para obtener la lista de categorías
      setCategorias(res.data); // Actualiza el estado con la lista de categorías obtenida
    } catch (err) { // Maneja cualquier error que ocurra durante la solicitud
      setError(
        err?.response?.data?.message || "No se pudieron cargar las categorías"
      );
    } finally { // Se ejecuta después de intentar cargar las categorías, sin importar si tuvo éxito o falló
      setLoading(false);
    }
  };

  useEffect(() => {  // Hook de efecto que se ejecuta una vez al montar el componente
    cargarCategorias();  // Llama a la función para cargar las categorías desde la API
  }, []);

  // Filtra las categorías según el término de búsqueda ingresado por el usuario
  const categoriasFiltradas = categorias.filter((c) =>  
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
        // PATCH /categorias/:id
        await axiosClient.patch(`/categorias/${editingId}`, { nombre });  // Realiza una solicitud PATCH a la API para actualizar la categoría con el ID especificado
      } else {
        // POST /categorias
        await axiosClient.post("/categorias", { nombre });
      }
      setShowForm(false);   // Cierra el formulario después de guardar los cambios
      cargarCategorias();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al guardar la categoría");
    }
  };

  // Función para manejar la eliminación de una categoría
  const handleEliminar = async (id) => {
    if (!confirm("¿Eliminar esta categoría?")) return;  // Muestra un cuadro de confirmación antes de eliminar la categoría
    try {   // Si el usuario confirma, realiza una solicitud DELETE a la API para eliminar la categoría con el ID especificado
      // DELETE /categorias/:id
      await axiosClient.delete(`/categorias/${id}`);
      cargarCategorias();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al eliminar la categoría");
    }
  };

  // Renderiza el componente Categorias 
  return (
    <div className="categorias-page">
      <div className="categorias-header">
        <h2 className="categorias-title">Categorías</h2>
        <button onClick={abrirNuevo} className="categorias-btn-primary">
          + Nueva Categoría
        </button>
      </div>

      <input
        placeholder="Buscar categoría..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="categorias-search"
      />

      {error ? <div className="categorias-error">{error}</div> : null}

      {loading ? (
        <p className="categorias-loading">Cargando...</p>
      ) : (
        <div className="categorias-table-wrap">
          <table className="categorias-table">
            <thead>
              <tr>
                <th>Nombre</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {categoriasFiltradas.map((c) => (
                <tr key={c.id}>
                  <td>{c.nombre}</td>
                  <td className="categorias-actions">
                    <button onClick={() => abrirEditar(c)} className="categorias-btn-edit">
                      Editar
                    </button>
                    <button onClick={() => handleEliminar(c.id)} className="categorias-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {categoriasFiltradas.length === 0 && (
                <tr>
                  <td colSpan={2} className="categorias-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <div className="categorias-modal-overlay">
          <form onSubmit={handleSubmit} className="categorias-modal">
            <h3>{editingId ? "Editar categoría" : "Nueva categoría"}</h3>

            <div className="categorias-field">
              <label>Nombre</label>
              <input
                required
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
              />
            </div>

            <div className="categorias-modal-actions">
              <button type="button" onClick={() => setShowForm(false)} className="categorias-btn-cancel">
                Cancelar
              </button>
              <button type="submit" className="categorias-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

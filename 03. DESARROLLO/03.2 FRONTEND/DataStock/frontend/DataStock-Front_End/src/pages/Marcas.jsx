// Importa los hooks (función especial de React) useEffect y useState de React para manejar el estado y los efectos secundarios en el componente
import { useEffect, useState } from "react";
import { axiosClient } from "../api/axiosClient";
import "./Marcas.css";

// Define el componente Marcas que se exporta por defecto
export default function Marcas() { 
  const [marcas, setMarcas] = useState([]); // Estado para almacenar la lista de categorías
  const [loading, setLoading] = useState(true); // Estado para indicar si los datos están siendo cargados
  const [error, setError] = useState(""); // Estado para almacenar mensajes de error
  const [search, setSearch] = useState(""); // Estado para almacenar el término de búsqueda ingresado por el usuario

  const [showForm, setShowForm] = useState(false); // Estado para controlar la visibilidad del formulario de creación/edición de categorías
  const [editingId, setEditingId] = useState(null); // Estado para almacenar el ID de la categoría que se está editando (null si se está creando una nueva categoría)
  const [nombre, setNombre] = useState(""); // Estado para almacenar el nombre de la categoría que se está creando o editando

  const cargarMarcas = async () => { // Función asíncrona para cargar las categorías desde la API
    setLoading(true); // Indica que la carga de datos ha comenzado
    setError(""); // Limpia cualquier mensaje de error previo
    try {
      // GET http://localhost:3000/marcas
      const res = await axiosClient.get("/marcas"); // Realiza una solicitud GET a la API para obtener la lista de categorías
      setMarcas(res.data); // Actualiza el estado con la lista de categorías obtenida
    } catch (err) { // Maneja cualquier error que ocurra durante la solicitud
      setError(
        err?.response?.data?.message || "No se pudieron cargar las marcas"
      );
    } finally { // Se ejecuta después de intentar cargar las categorías, sin importar si tuvo éxito o falló
      setLoading(false);
    }
  };

  useEffect(() => {  // Hook de efecto que se ejecuta una vez al montar el componente
    cargarMarcas();  // Llama a la función para cargar las categorías desde la API
  }, []);

  // Filtra las categorías según el término de búsqueda ingresado por el usuario
  const marcasFiltradas = marcas.filter((c) =>  
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
        // PATCH /marcas/:id
        await axiosClient.patch(`/marcas/${editingId}`, { nombre });  // Realiza una solicitud PATCH a la API para actualizar la categoría con el ID especificado
      } else {
        // POST /marcas
        await axiosClient.post("/marcas", { nombre });
      }
      setShowForm(false);   // Cierra el formulario después de guardar los cambios
      cargarMarcas();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al guardar la marca");
    }
  };

  // Función para manejar la eliminación de una categoría
  const handleEliminar = async (id) => {
    if (!confirm("¿Eliminar esta categoría?")) return;  // Muestra un cuadro de confirmación antes de eliminar la categoría
    try {   // Si el usuario confirma, realiza una solicitud DELETE a la API para eliminar la categoría con el ID especificado
      // DELETE /marcas/:id
      await axiosClient.delete(`/marcas/${id}`);
      cargarMarcas();
    } catch (err) {   // Maneja cualquier error que ocurra durante la solicitud
      setError(err?.response?.data?.message || "Error al eliminar la marca");
    }
  };

  // Renderiza el componente Marcas 
  return (
    <div className="marcas-page">
      <div className="marcas-header">
        <h2 className="marcas-title">Marcas</h2>
        <button onClick={abrirNuevo} className="marcas-btn-primary">
          + Nueva Marca
        </button>
      </div>

      <input
        placeholder="Buscar marca..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="marcas-search"
      />

      {error ? <div className="marcas-error">{error}</div> : null}

      {loading ? (
        <p className="marcas-loading">Cargando...</p>
      ) : (
        <div className="marcas-table-wrap">
          <table className="marcas-table">
            <thead>
              <tr>
                <th>Nombre</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {marcasFiltradas.map((c) => (
                <tr key={c.id}>
                  <td>{c.nombre}</td>
                  <td className="marcas-actions">
                    <button onClick={() => abrirEditar(c)} className="marcas-btn-edit">
                      Editar
                    </button>
                    <button onClick={() => handleEliminar(c.id)} className="marcas-btn-delete">
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
              {marcasFiltradas.length === 0 && (
                <tr>
                  <td colSpan={2} className="marcas-empty">
                    Sin resultados
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {showForm && (
        <div className="marcas-modal-overlay">
          <form onSubmit={handleSubmit} className="marcas-modal">
            <h3>{editingId ? "Editar marca" : "Nueva marca"}</h3>

            <div className="marcas-field">
              <label>Nombre</label>
              <input
                required
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
              />
            </div>

            <div className="marcas-modal-actions">
              <button type="button" onClick={() => setShowForm(false)} className="marcas-btn-cancel">
                Cancelar
              </button>
              <button type="submit" className="marcas-btn-submit">
                {editingId ? "Guardar" : "Crear"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
}

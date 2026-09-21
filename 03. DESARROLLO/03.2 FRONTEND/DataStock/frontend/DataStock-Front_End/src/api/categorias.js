// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de categorías
export const categorias = {   
  list: () => axiosClient.get("/categorias"), // Obtiene la lista de todas las categorías
  getById: (id) => axiosClient.get(`/categorias/${id}`),   // Recibe un parámetro id para consultar una categoría específica
  create: (data) => axiosClient.post("/categorias", data),    // Recibe un objeto data con la información de la nueva categoría para su creación
  update: (id, data) => axiosClient.put(`/categorias/${id}`, data), // Se usa para actualizar una categoría existente, el data contiene los nuevos valores
  remove: (id) => axiosClient.delete(`/categorias/${id}`), // Se usa para eliminar una categoría
};
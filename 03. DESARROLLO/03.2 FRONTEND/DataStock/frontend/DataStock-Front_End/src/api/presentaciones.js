// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de categorías
export const presentaciones = {   
  list: () => axiosClient.get("/presentaciones"), // Obtiene la lista de todas las presentaciones
  getById: (id) => axiosClient.get(`/presentaciones/${id}`),   // Recibe un parámetro id para consultar una presentación específica
  create: (data) => axiosClient.post("/presentaciones", data),    // Recibe un objeto data con la información de la nueva presentación para su creación
  update: (id, data) => axiosClient.put(`/presentaciones/${id}`, data), // Se usa para actualizar una presentación existente, el data contiene los nuevos valores
  remove: (id) => axiosClient.delete(`/presentaciones/${id}`), // Se usa para eliminar una presentación
};
// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de roles
export const roles = {
  list: () => axiosClient.get("/roles"),    // Obtiene la lista de todos los roles
  getById: (id) => axiosClient.get(`/roles/${id}`), // Recibe un parámetro id para consultar un rol específico
  create: (data) => axiosClient.post("/roles", data), // Recibe un objeto data con la información del nuevo rol para su creación
  update: (id, data) => axiosClient.put(`/roles/${id}`, data),  // Se usa para actualizar un rol existente, el data contiene los nuevos valores
  remove: (id) => axiosClient.delete(`/roles/${id}`), // Se usa para eliminar un rol
};
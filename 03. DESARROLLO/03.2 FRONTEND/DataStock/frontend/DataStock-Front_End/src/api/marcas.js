// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de marcas.js
export const marcas = {   
  list: () => axiosClient.get("/marcas"), // Obtiene la lista de todas las categorías
  getById: (id) => axiosClient.get(`/marcas/${id}`),   // Recibe un parámetro id para consultar una marca específica
  create: (data) => axiosClient.post("/marcas", data),    // Recibe un objeto data con la información de la nueva marca para su creación
  update: (id, data) => axiosClient.put(`/marcas/${id}`, data), // Se usa para actualizar una marca existente, el data contiene los nuevos valores
  remove: (id) => axiosClient.delete(`/marcas/${id}`), // Se usa para eliminar una marca
};
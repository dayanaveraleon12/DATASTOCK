// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de sitios
export const sitios = {
  list: () => axiosClient.get("/sitios"),
  getById: (id) => axiosClient.get(`/sitios/${id}`),
  create: (data) => axiosClient.post("/sitios", data),
  update: (id, data) => axiosClient.put(`/sitios/${id}`, data),
  remove: (id) => axiosClient.delete(`/sitios/${id}`),
};
// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de users
export const users = {
  list: () => axiosClient.get("/usuarios"),
  getById: (id) => axiosClient.get(`/usuarios/${id}`),
  create: (data) => axiosClient.post("/usuarios", data),
  update: (id, data) => axiosClient.put(`/usuarios/${id}`, data),
  remove: (id) => axiosClient.delete(`/usuarios/${id}`),
};
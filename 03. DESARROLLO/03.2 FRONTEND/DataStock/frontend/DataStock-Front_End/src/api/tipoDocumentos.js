// Importa el cliente HTTP configurado en el archivo ./axiosClient
import { axiosClient } from "./axiosClient";

// Exporta un objeto con métodos para interactuar con la API de tipoDocumentos
export const tipoDocumentos = {
  list: () => axiosClient.get("/TipoDocumentos"),
  getById: (id) => axiosClient.get(`/tipoDocumentos/${id}`),
  create: (data) => axiosClient.post("/tipoDocumentos", data),
  update: (id, data) => axiosClient.put(`/TipoDocumentos/${id}`, data),
  remove: (id) => axiosClient.delete(`/tipoDocumentos/${id}`),
};
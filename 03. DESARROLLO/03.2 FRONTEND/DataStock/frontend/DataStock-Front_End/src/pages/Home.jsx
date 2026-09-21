import { Link } from "react-router-dom";
import "./Home.css";

export default function Home() {
  // Nota: todavía no hay una página de Login que guarde algo en localStorage
  // bajo la clave "user", así que por ahora no mostramos ese bloque.
  // Cuando exista el login, guarden ahí el usuario logueado y pueden
  // volver a activar este bloque.

  return (
    <div className="home-page">
      <h1>Inicio</h1>
      <p>Bienvenido. Usa el menú superior para navegar.</p>

      <hr className="home-divider" />

      <h3>Accesos rápidos</h3>
      <ul className="home-links">
        
        <li>
          <Link to="/Usuarios">Ver usuarios o crear nuevo</Link>
        </li>

        <li>
          <Link to="/Marcas">Ir a CRUD Marcas</Link>
        </li>

        <li>
          <Link to="/Presentaciones">Ir a CRUD Presentaciones</Link>
        </li>

        <li>
          <Link to="/Sitios">Ir a CRUD Sitios</Link>
        </li>

        <li>
          <Link to="/TipoDocumentos">Ir a CRUD Tipo Documentos</Link>
        </li>
        
      </ul>
    </div>
  );
}

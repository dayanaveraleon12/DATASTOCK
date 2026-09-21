import { NavLink } from "react-router-dom";
import "./Navbar.css";

const linkClass = ({ isActive }) =>
  isActive ? "navbar-link navbar-link-active" : "navbar-link";

export default function Navbar() {
  const token = localStorage.getItem("accessToken");

  const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("user");
    window.location.href = "/"; // simple y efectivo
  };

  return (
    <nav className="navbar-sidebar">
      <div>
        <div className="navbar-brand">
          <span className="navbar-brand-icon">D</span>
          <span className="navbar-brand-name">DataStock</span>
        </div>

        <div className="navbar-links">
          {!token ? (
            <NavLink to="/Login" className={linkClass}>
              Ingreso
            </NavLink>
          ) : null}


          {token ? (
          <>
            <NavLink to="/Home" className={linkClass}>
              Home
            </NavLink>
            <NavLink to="/Usuarios" className={linkClass}>
              Usuarios
            </NavLink>
            <NavLink to="/Sitios" className={linkClass}>
              Sitios
            </NavLink>
            <NavLink to="/Presentaciones" className={linkClass}>
              Presentaciones
            </NavLink>
            <NavLink to="/Marcas" className={linkClass}>
              Marcas
            </NavLink>
            <NavLink to="/TipoDocumentos" className={linkClass}>
              Tipo Documentos
            </NavLink>
            <NavLink to="/Categorias" className={linkClass}>
              Categorias
            </NavLink>
          </>
        ) : null}
        </div>
      </div>

      <div className="navbar-footer">
        <span className="navbar-status">
          {/* Muestra un mensaje de bienvenida con el correo del usuario si hay un token válido, o un mensaje genérico si no lo hay */}
          {token ? "Bienvenido " + (JSON.parse(localStorage.getItem("user"))?.email || "Usuario") : "Bienvenido"}
        </span>
        {token ? (
          <button onClick={logout} className="navbar-logout">
            Salir
          </button>
        ) : null}
      </div>
    </nav>
  );
}

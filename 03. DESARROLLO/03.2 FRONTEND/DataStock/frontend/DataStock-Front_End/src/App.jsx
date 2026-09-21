import './App.css'

import Navbar from './component/Navbar'
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Home from'./pages/Home'
import Login from './pages/Login'
import Usuarios from'./pages/Usuarios'
import Sitios from'./pages/Sitios'
import Presentaciones from './pages/Presentaciones'
import Marcas from './pages/Marcas'
import TipoDocumentos from'./pages/TipoDocumentos'
import Categorias from'./pages/Categorias'

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        
        <Route path="/" element={<Login />} />
        <Route path="/Home" element={<Home />} />
        <Route path="/Usuarios" element={<Usuarios />} />
        <Route path="/Sitios" element={<Sitios />} />
        <Route path="/Presentaciones" element={<Presentaciones />} />
        <Route path="/Marcas" element={<Marcas />} />
        <Route path="/TipoDocumentos" element={<TipoDocumentos />} />
        <Route path="/Categorias" element={<Categorias />} />


        {/* fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
import React from 'react';

const TravelCard = ({ titulo, fechaInicio, fechaFin, action, usuario ,boton, login }) => { return (
  <div className="card mt-2 mb-2" style={{width: '18rem'}}>
    <div className="card-body">
      <h5 className="card-title">{titulo}</h5>
      <div className="card-text">Desde: <strong>{new Date(fechaInicio).toLocaleDateString()}</strong></div>
      <div className="card-text">Hasta: <strong>{new Date(fechaFin).toLocaleDateString()}</strong></div>
        <div className="card-text">Creado por: <strong>{usuario}</strong></div>
      <div className="row justify-content-end">
        {login ? <a href={action} className="btn btn-primary">{boton}</a>:
          <span className="badge badge-pill badge-danger">Logueate para ver más</span>}

      </div>

    </div>
  </div>
)}

export default TravelCard
import React from 'react';

const TravelCard = ({ titulo, fechaInicio, fechaFin, action, boton }) => { return (
  <div className="card mt-2 mb-2" style={{width: '18rem'}}>
    <img src="https://picsum.photos/200" className="card-img-top" alt="foto viaje"/>
    <div className="card-body">
      <h5 className="card-title">{titulo}</h5>
      <div className="card-text">Desde: <strong>{fechaInicio}</strong></div>
      <div className="card-text">Hasta: <strong>{fechaFin}</strong></div>
      <a href={action} className="btn btn-primary">{boton}</a>
    </div>
  </div>
)}

export default TravelCard
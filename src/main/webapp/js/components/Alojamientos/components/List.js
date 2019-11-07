import React from "react";
import { Col } from 'react-bootstrap';

const List = props => (
  <>
    {props.items && props.items.length !== 0 ? (
      <>
        {
          props.items.map((hotel, i) => {
            return (
              <Col sm={4} key={`${i}-${hotel.nombre}`}>
                <div className="card" style={{ }}>
                  <img src={"/Los_Ponys_war/images/hoteles/hotel.jpg"} width="100%" height="auto" className="card-img-top" alt={hotel.nombre} />
                  <div className="card-body">
                    <h2 className="card-title">{hotel.nombre}</h2>
                    <h3 className="card-text">{hotel.direccion}</h3>
                    <p>{hotel.descripcion}</p>
                    <br/>
                    <strong>{hotel.precio}</strong>
                    <button
                      className={"btn btn-primary"}
                      onClick={() => { return window.location.href = `${window.location.pathname}/${hotel.id}`}}>Reservar
                    </button>
                  </div>
                </div>
              </Col>
            )
          })
        }
      </>
    ) : (
      <p>No se encontraron resultados...</p>
    )}
  </>
);

export default List;
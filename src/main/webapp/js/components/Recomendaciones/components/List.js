import React from "react";
import { Col } from 'react-bootstrap';

const List = props => (
  <>
    {props.items && props.items.length !== 0 ? (
      <>
        {
          props.items.map((rec, i) => {
            return (
              <Col sm={4} key={`${i}-${rec.nombre}`}>
                <div className="card" style={{ }}>
                  <img src={`https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${rec.photoReferences}&key=AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg`} width="100%" height="auto" className="card-img-top" alt={rec.nombre} />
                  <div className="card-body">
                    <h2 className="card-title">{rec.nombre}</h2>
                    <h3 className="card-text">{rec.direccion}</h3>
                    <p>{rec.descripcion}</p>
                    <br/>
                    <button
                      className={"btn btn-primary"}
                      onClick={() => { return window.location.href = `${window.location.pathname}/${rec.id}`}}>Reservar
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
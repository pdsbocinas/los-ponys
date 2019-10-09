import React from "react";
import { Col, Row } from 'react-bootstrap';

const List = props => (
  <>
    {props.items.length && (
      <>
        <p>Mis destinos:</p>
        <Row>
        {
          props.items.map((s, i) => {
            return (
              <Col sm={4} key={`${i}-${s.name}`}>
                <div className="card" style={{ width: '18rem', maxHeight: '320px' }}>
                  <img src={s.icon} className="card-img-top" alt={s.name} />
                  <div className="card-body">
                    <h5 className="card-title">{s.name}</h5>
                    <p className="card-text">{s.formattedAddress}</p>
                    <a href="#" className="btn btn-primary">Ir al mapa</a>
                  </div>
                </div>
              </Col>
            )
          })
        }
        </Row>
      </>
    )}
  </>
);

export default List;
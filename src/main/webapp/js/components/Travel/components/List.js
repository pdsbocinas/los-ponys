import React, {useState, useCallback} from "react";
import {Col, Row} from 'react-bootstrap';

const eliminar = (id, elimina) => {
  elimina;
}

const update = (id, update) => {
  update;
}

const List = props => {
  const [fechaInicio,setFechaInicio] = useState("")
  const [fechaFin,setFechaFin] = useState("")
  const [id,setId] = useState()
  const onSelectDates = useCallback(() => {props.onSelect(fechaInicio, fechaFin, placeId)})
  return (
    <>
      {props.items.length !== 0 && (
        <>
          <p>Mis destinos:</p>
          <Row>
            {
              props.items.map((s, i) => {
                return (
                  <Col sm={4} key={`${i}-${s.name || s.nombre}`}>

                    <div className="card" style={{width: '18rem', maxHeight: '320px'}}>
                      {/*<img src={s.icon} className="card-img-top" alt={s.name || s.nombre}/>*/}
                      <div className="card-body">
                        <h5 className="card-title">{s.name || s.nombre}</h5>
                        <p className="card-text">{s.formattedAddress || s.ciudad}</p>
                        {/*<label htmlFor="fechaDesde">Desde</label>
                        <input id="fechaDesde" type="text" onChange={(e)=>{setFechaInicio(e.target.value)}}/>
                        <label htmlFor="fechaHasta">Hasta</label>
                        <input id="fechaHasta" type="text" onChange={(e)=>{setFechaFin(e.target.value)}}/>*/}

                        {/*<div>{new Date(s.fechaInicio).toDateString()}</div>*/}
                        {/*<div>{new Date(s.fechaHasta).toDateString()}</div>*/}
                        {/*<button
                          className={"btn btn-info"}
                          onClick = {()=>{setId(s.id);props.onSelect(fechaInicio,fechaFin,id)}}
                          Fecha>Elegir fechas
                          onClick={onSelectDates()}>Guardar
                        </button>*/}
                        {/*<a href={s.viaje_id+"/destino/"+s.id+"/vista"} className={"btn btn-primary"}>Elegir Fechas</a>*/}

                        <button
                          className={"btn btn-danger"}
                          onClick={() => eliminar(s.placeId, props.delete(s.placeId))}>Eliminar
                        </button>

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
  )
}

export default List;
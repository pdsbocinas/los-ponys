import React, {Component} from 'react';
import {host} from "../../../host";

const TravelCard = (props) => (


    <div className="card mt-2 mb-2" style={{width: '18rem'}}>
        <img src="https://picsum.photos/200" className="card-img-top" alt="foto viaje"/>
        <div className="card-body">
            <h5 className="card-title">{props.titulo}</h5>
            <p className="card-text">Desde: {props.fechaInicio}</p>
            <p className="card-text">Hasta: {props.fechaFin}</p>
            <a href={props.action} className="btn btn-primary">{props.boton}</a>
        </div>
    </div>


)



export default TravelCard
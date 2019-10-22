import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import List from './components/List';
import { Container, Row, Button } from 'react-bootstrap';

// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {

  }

  render () {

    return (
        <div className="container ">

          <fieldset className="border p-2">
            <legend className="w-auto">Posteo del viaje</legend>

            <div className="row">
              <div className=" col">
                <small>Titulo</small>
                <p>
                  <strong>Titulo del viaje</strong>
                </p>
              </div>
            </div>

            <div className="row">
              <div className=" col">
                <small>Desde</small>
                <p>
                  <strong>19/10/2019</strong>
                </p>
              </div>

              <div className=" col">
                <small>Hasta</small>
                <p>
                  <strong>19/12/2019</strong>
                </p>
              </div>
            </div>

            <div className="row">
              <div className="col-12">
                <a href="viajes/10">Ver recorrido del viaje</a>
              </div>
            </div>
            <div className="row">
              <div className="col-12">
                <i className="far fa-heart"></i>
                <i className="fas fa-share-alt"></i>
              </div>
            </div>
            <div className="form-group">
              <label htmlFor="">Comentarios</label>
              <textarea className="form-control" id=""></textarea>
            </div>

          </fieldset>


        </div>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
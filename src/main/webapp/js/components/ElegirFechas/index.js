import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import {Col} from "react-bootstrap";


// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {
    viaje: {
      id : viaje_id
    },
      destinos: [],
    guardarFechas: []
  }

  guardarFechas = (id, fechaInicio, fechaHasta)=>{
    this.setState({
        guardarFechas: {
          id : id,
          fechaInicio: fechaInicio,
          fechaHasta: fechaHasta
        }
    }

    )
  }

  componentDidMount() {
    axios
      .get( `${host}/api/viajes/${this.state.viaje.id}/obtener-destinos`)
      .then(   response => {
        console.log(response)
        this.setState({
          destinos: response.data
        })
      }).catch(error => {
      console.log(error)
    })
  }


  render () {

    return (
        <div className="container ">
            <div className="row">
              <div className="col-12">
                <h3>Destinos:</h3>
                <form action="guardarFechas" method={"POST"}>


                 { this.state.destinos && this.state.destinos.map(d => {
                    return(
                      <Col sm={4} key={d.id}>

                        <div className="card" style={{width: '18rem', maxHeight: '320px'}}>
                          <div className="card-body">
                            <h5 className="card-title">{d.ciudad}</h5>
                            <label htmlFor="fechaDesde">Desde</label>
                            <input name={"fechaDesde"+d.id} type="date"/>
                            <label htmlFor="fechaHasta">Hasta</label>
                            <input name={"fechaHasta"+d.id} type="date" />

                          </div>
                        </div>
                      </Col>

                    )
                  })}
                  <button className={"btn btn-primary"}> Guardar fechas!</button>
                </form>
              </div>
            </div>
        </div>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
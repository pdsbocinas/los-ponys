import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';


// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {
      usuario_email: usuario_email,
    viaje:{
      id: viaje_id,
      fechaInicio: viaje_fechaInicio,
      fechaFin: viaje_fechaFin,
      titulo: viaje_titulo
    },
    destinos:[],
      comentario_actual: '',
      comentarios:[]
  }

  onChange = (ev)=>{
    this.setState({
      [ev.target.name] : ev.target.value
    })
  }

  enviarComentario = () => {
    axios
        .post( `${host}/viajes/comentar/enviar-comentario`, {
          texto: this.state.comentario_actual,
          viaje_id: this.state.viaje.id,
            usuario_email: this.state.usuario_email
    })
        .then(   response => {
          console.log(response)
          }).catch(error => {
      console.log(error)
    })
  }

  obtenerComentarios = () => {
      axios
          .get( `${host}/api/viajes/${this.state.viaje.id}/comentarios`)
          .then(   response => {
              console.log(response)
              this.setState({
                  comentarios: response.data
              })
          }).catch(error => {
          console.log(error)
      })
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
      this.obtenerComentarios();
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
                  <strong>{this.state.viaje.titulo}</strong>
                </p>
              </div>
            </div>

            <div className="row">
              <div className=" col">
                <small>Desde</small>
                <p>
                  <strong>{this.state.viaje.fechaInicio}</strong>
                </p>
              </div>

              <div className=" col">
                <small>Hasta</small>
                <p>
                  <strong>{this.state.viaje.fechaFin}</strong>
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
                <h3>Destinos:</h3>
                {this.state.destinos && this.state.destinos.map(v => {
                  return(
                      <p>{v.nombre}</p>
                  )

                })}
              </div>
            </div>
              <i className="fas fa-heart"></i>
              <div className="row">
                  <div className="col-12">
                      <h3>Comentarios:</h3>
                      {this.state.comentarios && this.state.comentarios.map(v => {
                          return(
                              <div>
                                  <p> <strong>{v.usuario_email}</strong></p>
                                  <p>{v.texto}</p>
                                  <hr/>
                              </div>
                                  )

                      })}
                  </div>
              </div>


            <div className="row">
              <div className="col-12">
                <form action="">
                  <div className="form-group">
                    <label htmlFor="">Comentar</label>
                    <input
                        type={"text"}
                        className="form-control"
                        id="comentario_actual"
                        name={"comentario_actual"}
                        value={this.state.comentario_actual}
                        onChange={this.onChange}/>


                  </div>
                  <button
                      className={"btn btn-primary"}
                      onClick={this.enviarComentario}>Comentar
                  </button>
                </form>
              </div>
            </div>


          </fieldset>


        </div>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
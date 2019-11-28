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
          .post( `${host}/api/viajes/${this.state.viaje.id}/comentarios`)
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
            {/*<legend className="w-auto">Posteo del viaje</legend>*/}

            <div className="row">
              <div className=" col text-center bg-dark text-white">
                  <h1 className={"display-1"}>{this.state.viaje.titulo}</h1>
              </div>
            </div>
            <div className="row justify-content-center bg-dark text-white pb-3">
              <div className="col-4 text-center display-4">
                {new Date(this.state.viaje.fechaInicio).toLocaleDateString()}
              </div>
              <div className="col-1 text-center display-4">-&gt;</div>
              <div className="col-4 text-center display-4">
                {new Date(this.state.viaje.fechaFin).toLocaleDateString()}
              </div>
            </div>




            <div className="row">
              <div className="col-12">
                <h3 className={"display-3 text-center mt-2"}>Destinos</h3>
              </div>
            </div>
            <div className="row justify-content-center">

                {this.state.destinos && this.state.destinos.map(d => {
                  return(
                    <>
                      <div className="col-4 m-1">
                        <div className="card text-center" >
                          <div className="card-body">
                            <h5 className="card-title">{d.ciudad}</h5>
                            <p className="card-text"><b>{new Date(d.fechaInicio).toLocaleDateString()}</b> a <b>{new Date(d.fechaHasta).toLocaleDateString()}</b></p>
                          </div>
                          <div className="card-footer">
                            <small className="text-muted"><a href={"./destino/"+d.id+"/vista"}>Ver más!</a></small>
                          </div>
                        </div>
                      </div>
                    {/*<div className={"col-4 border border-dark"}>*/}
                    {/*  <h4 className={"display-4"}>{d.ciudad}</h4>*/}
                    {/*  <div><b>{new Date(d.fechaInicio).toLocaleDateString()}</b> a <b>{new Date(d.fechaHasta).toLocaleDateString()}</b></div>*/}
                    {/*</div>*/}
                    </>
                  )

                })}

            </div>
            <div className="row">
              <div className="col-12 text-center mt-2">
                <a className={"badge badge-info"}  href="./recorridos">Ver recorrido del viaje</a>
              </div>
            </div>
              <i className="fas fa-heart"></i>
              <div className="row mt-2">
                  <div className="col-12">
                      <h3 className={"display-4"}>Comentarios</h3>
                      {this.state.comentarios && this.state.comentarios.map(v => {
                          return(
                              <div class="border rounded-pill p-2 px-4 my-2 bg-light" >
                                <span className={"text-primary font-weight-bold mr-1"}>{v.usuario.email}</span> {v.texto}
                              </div>
                                  )

                      })}
                  </div>
              </div>


            <div className="row">
              <div className="col-12">
                <form action="">
                  <div className="form-group">
                    {/*<label htmlFor="">Comentar</label>*/}
                    <input
                        type={"text"}
                        className="form-control border rounded-pill bg-light mt-2"
                        id="comentario_actual"
                        name={"comentario_actual"}
                        value={this.state.comentario_actual}
                        placeholder={"Escribe tu comentario"}
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
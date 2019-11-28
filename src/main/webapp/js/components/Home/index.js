import React from 'react';
import ReactDOM from "react-dom";
import TravelCard from './Components/TravelCard';
import { Toast } from 'react-bootstrap';
import styled from 'styled-components';
import axios from "axios";
import { host } from '../../host.js'

const Banner = styled.div`
  position: relative;
  overflow: hidden;
  height: 365px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const CustomToast = styled(Toast)`
  position: fixed;
  bottom: 25px;
  left: 20px;
`;

class App extends React.Component{

  constructor(props) {
    super(props)
    this.state = {
      isLoginOpen: true,
      isRegisterOpen: false,
      notFound: notFound || '',
      exitoso: registroExito || '',
      session:{
        login: false,
        username: '',
        email: '',
        error:''
      },
      registro:'',
      registroUsuario:{
        mensaje:'',
        status:''
      },
      viajes:[],
      viajesPublicos: []
    }
  }

  showRegisterBox = () =>{
    this.setState({
      isRegisterOpen: true,
      isLoginOpen: false
    })

  }

  showLoginBox = () =>{
    this.setState({
      isLoginOpen: true,
      isRegisterOpen: false,
      session:''
    })

  }

  cerrarSesion = () =>{
    axios
      .post(`${host}/cerrarSesion`)
      .then(response =>{
        console.log(response)
      }).catch(error =>{
      console.log(error)
    })
    this.setState({
      session:{
        login: false,
        username: '',
        email: ''
      }
    })
  }

  obtenerMisViajes =  () => {
    if(this.state.viajes.length == 0){
      axios.get(`${host}/api/mis-viajes`)
        .then( response =>{
          console.log(this.state)
          console.log("ANTES de setState")
          console.log(response)
          let data = response.data
          this.setState({
            viajes: data
          })
          console.log("DESPUES de setState")
          console.log(this.state)
        }).catch(error =>{
        console.log(error)
      })

    }else{
      console.log('no logueado')
    }
    console.log(this.state);

  }

  // getViajes = async () => {
  //     let res = await axios.get(`${host}/api/mis-viajes`);
  //     let { data } = res.data;
  //     this.setState({ viajes: data });
  // };

  obtenerViajes = () => {
    axios
      .get(`${host}/api/viajes-publicos`)
      .then(async response => {
        const viajes = response.data
          .filter((v) => v.privacidad === 'publico')
          .map(e => { return ({ ...e, fechaFin: new Date(e.fechaFin).toDateString(), fechaInicio: new Date(e.fechaInicio).toDateString() })})

        console.log("viajes",viajes)
        await this.setState({
          viajesPublicos: viajes
        })

      }).catch(error => {
      console.log(error)
    })
    console.log(this.state);
  }

  componentDidMount() {

    if(typeof(duplicado) != "undefined"){
      if(duplicado =="duplicado"){
        this.setState({
          registro: "duplicado"
        })
      }
    }
    if(typeof(registroError) != "undefined" && registroError != ""){
      this.setState({
        registroUsuario: {
          mensaje: registroError,
          status: "danger"
        }
      })

    } else if (typeof(registroExito) != "undefined" && registroExito != ""){
      this.setState({
        registroUsuario: {
          mensaje: registroExito,
          status: "success"
        }
      })
    }

    if(typeof(errorLogin) != "undefined" && errorLogin != ""){
      this.setState({
        session: {
          login: false,
          username: '',
          email: '',
          error: errorLogin
        }
      })
    }

    if(typeof(login) != "undefined" && login == "true"){
      this.setState({
        session: {
          id: id,
          login: true,
          username: 'Un nombre',
          email: email,
          error: ''
        }
      })
      //this.obtenerMisViajes()
      // getViajes()
    }

    this.obtenerViajes()
  }

  converToDate = (time) => {
    const date = new Date(time);
    return date;
  }

  render(){
    const { notFound } = this.state
    return(
      <div className="">
        <Banner>
          <img src={`${host}/images/image-home.jpg`} width="100%" height="auto" />
          <div style={{ position: 'absolute' }}>
            <h2 className={"display-4 text-white"}>Viajá. Compartí. Recomendá</h2>
            {this.state.session.login == true ?<a href="viajes" className={"btn btn-primary"}>Creá tu viaje!</a>: <p className={"text-white"}>Para crear un Viaje debes iniciar sessión o registrarte</p>}
           {/*<a href="viajes" className={"btn btn-primary"}>Creá tu viaje!</a>*/}
          </div>
        </Banner>
        <div className={"container"}>
          <div className="row bg-light">
            {this.state.viajesPublicos.map((viaje,i) => (
              <div key={i} className="col-4">
                <TravelCard
                  titulo={viaje.titulo}
                  fechaInicio={viaje.fechaInicio}
                  fechaFin={viaje.fechaFin}
                  usuario={viaje.usuarios[0].email}
                  boton={"Ver"}
                  action ={"viajes/"+ viaje.id +"/comentar"}
                  login={this.state.session.login}
                />
              </div>
              )
            )}
          </div>
        </div>
        {errorLogin.length !== 0 || notFound.length !== 0 && (
          <CustomToast>
            <Toast.Header>
              <img src="holder.js/20x20?text=%20" className="rounded mr-2" alt="" />
              <strong className="mr-auto">Error:</strong>
            </Toast.Header>
            <Toast.Body>{errorLogin || notFound}</Toast.Body>
          </CustomToast>
        )}
        {/* {this.state.session.login==true?<h2>Bienvenido {this.state.session.email}</h2>:<h2>Por Favor inicia sesion</h2>}
                {this.state.session.login==true?
                  <button
                    className={"btn btn-danger"}
                    onClick={this.cerrarSesion}>Cerrar sesion
                  </button>
                  :
                  null*/}
        {/*<div className="root-container container bg-warning">
                    <div className=" row justify-content-center">
                        <div className="col-12 text-center">
                            <img src="/Los_Ponys_war/images/login/boy.png" alt=""/>
                        </div>
                        <div className="col-4">
                            {this.state.session.login == true?"Cerrar sesion":""}
                        </div>
                    </div>
                    <div className="box-controller row justify-content-center">
                        <div
                            className="controller col-3 bg-dark text-white"
                            onClick={this.showLoginBox}
                            style={{cursor:"pointer"}}>
                            Login
                        </div>

                        <div
                            className="controller col-3 bg-dark text-white"
                            onClick={this.showRegisterBox}
                            style={{cursor:"pointer"}}>
                            Registro
                        </div>
                    </div>

                    <div className="box-container row justify-content-center">
                        {this.state.isLoginOpen && <LoginBox/>}
                        {this.state.isRegisterOpen && <RegisterBox/>}
                    </div>
                </div>}*/}

        {/*                {this.state.registroUsuario.mensaje!=""?<div className={"alert alert-" + this.state.registroUsuario.status } role="alert">
                    {this.state.registroUsuario.mensaje}
                </div>:""}

                {this.state.session.error!=""?this.state.session.error:""}*/}

        {/*<Menu/>*/}


        {/*              {this.state.session.login && <div className={"container"}>
                    <h2 className={"display-3"}>
                        Mis Viajes!
                    </h2>
                        <div className="row bg-light">

                            {this.state.viajes.map((viaje,i) => (
                                <div key={i} className="col-4">
                                    <TravelCard
                                        id={viaje.id}
                                        titulo={viaje.titulo}
                                        fechaInicio ={viaje.fechaInicio}
                                        fechaFin ={viaje.fechaFin}
                                        boton={"Editar"}
                                        action={"viajes/"+ viaje.id}
                                    />
                                </div>
                                )

                            )}
                        </div>
                    </div>
                }*/}
      </div>

    )

  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
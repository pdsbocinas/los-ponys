import React from 'react';
import ReactDOM from "react-dom";

import LoginBox from './Components/LoginBox.js';
import RegisterBox from './Components/RegisterBox.js';
import Menu from "./Components/Menu";
import TravelCard from './Components/TravelCard';

import axios from "axios";
import { host } from '../../host.js'

class App extends React.Component{

    state={
        isLoginOpen: true,
        isRegisterOpen: false,
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

    cerrarSesion = ()=>{
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

            if (this.state.viajesPublicos.length == 0) {
                axios
                    .get( `${host}/api/viajes-publicos`)
                    .then(   response => {
                      const viajes = response.data.filter((v) => {

                            if (v.privacidad == "publico") {
                                return true;
                            }
                        })
                        this.setState({
                            viajesPublicos: viajes
                        })

                        console.log(response)
                    }).catch(error => {
                    console.log(error)
                })
            } else {

            }
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
        }else if(typeof(registroExito) != "undefined" && registroExito != ""){
            this.setState({
                registroUsuario: {
                    mensaje: registroExito,
                    status: "success"
                }
            })
            $('#element').tooltip('show')
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
            this.obtenerMisViajes()
            this.obtenerViajes()
            // getViajes()
        }


    }


    render(){

        return(
            <div className={""}>

                {this.state.session.login==true?<h2>Bienvenido {this.state.session.email}</h2>:<h2>Por Favor inicia sesion</h2>}
                {this.state.session.login==true?<button
                    className={"btn btn-danger"}
                    onClick={this.cerrarSesion}>Cerrar sesion</button>:<div className="root-container container bg-warning">
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
                </div>}

                {this.state.registroUsuario.mensaje!=""?<div className={"alert alert-" + this.state.registroUsuario.status } role="alert">
                    {this.state.registroUsuario.mensaje}
                </div>:""}

                {this.state.session.error!=""?this.state.session.error:""}

                <Menu/>

                <div>
                    <h2 className={"display-4"}>Viajá. Compartí. Recomendá</h2>
                    <a href="viajes" className={"btn btn-primary"}>Creá tu viaje!</a>
                </div>

                {this.state.session.login && <div className={"container"}>
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
                }

                {this.state.session.login && <div className={"container"}>
                    <h2 className={"display-3"}>
                        Otros Viajes!
                    </h2>
                    <div className="row bg-light">

                        {this.state.viajesPublicos.map((viaje,i) => (
                                <div key={i} className="col-4">
                                    <TravelCard
                                        titulo={viaje.titulo}
                                        fechaInicio ={viaje.fechaInicio}
                                        fechaFin ={viaje.fechaFin}
                                        boton={"Ver"}
                                        action ={"viajes/"+ viaje.id +"/comentar"}
                                    />
                                </div>
                            )

                        )}
                    </div>
                </div>}




            </div>


        )

    }
}



ReactDOM.render(<App />, document.querySelector("#root"));
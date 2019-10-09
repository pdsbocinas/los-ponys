import React from 'react';
import ReactDOM from "react-dom";

import LoginBox from './Components/LoginBox.js';
import RegisterBox from './Components/RegisterBox.js';
import Menu from "./Components/Menu";
import divWithClassName from "react-bootstrap/utils/divWithClassName";
import axios from "axios";


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

    creaViaje = ()=>{
        alert('ir a pagina creacion de viajes')
    }

    cerrarSesion = ()=>{
        axios
            .post("http://localhost:8081/Los_Ponys_war/cerrarSesion")
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

    componentDidMount() {
        // if(typeof(email) != "undefined"){
        //     if(email != ""){
        //         this.setState({
        //             session:{
        //                 login: true,
        //                 username: 'nombre',
        //                 email: email
        //             }
        //         })
        //     }
        // }else{
        //     this.setState({
        //         session:{
        //             login: false,
        //             username: '',
        //             email: ''
        //         }
        //     })
        // }

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
                    login: true,
                    username: 'Un nombre',
                    email: email,
                    error: ''
                }
            })
        }


    }


    render(){

        return(
            <div>
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
                    <h2>Si sos pura sangre, viaja con nosotros!</h2>
                    <button
                        className={"btn btn-primary"}
                        onClick ={this.creaViaje}
                    >Creá tu viaje!</button>
                </div>
            </div>


        )

    }
}



ReactDOM.render(<App />, document.querySelector("#root"));
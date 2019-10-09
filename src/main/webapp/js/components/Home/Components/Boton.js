import React from 'react';
import axios from 'axios';

class Boton extends React.Component{
    constructor(props){
        super(props);

        this.state={
            titulo: props.titulo,
            email: props.email,
            password: props.password
        }
    }

    enviar = (ev)=>{
        ev.preventDefault();

        this.setState({
            email: this.props.email,
            password: this.props.password
        })
        const data = {
            "email": this.props.email,
            "password": this.props.password
        }
        if(this.props.titulo === "R"){
            this.props.validate;
            axios
                .post("http://localhost:8081/Los_Ponys_war/guardarUsuario",data)
                .then(response =>{
                    console.log(response)
                    // return window.location.href = `/Los_Ponys_war/home`;
                    // return window.location.href = `registroOK`;
                    if(response.data == "correcto"){
                        return window.location.href = `registroOK`;
                    }else{
                        return window.location.href = `registroDuplicado`;
                    }
                }).catch(error =>{
                console.log(error)
            })
        }else{
            axios
                .post("http://localhost:8081/Los_Ponys_war/validar-login2",data)//Devuelve "correcto" o error
                .then(response =>{
                    console.log(response);
                    if(response.data == "correcto"){
                        return window.location.href = `LoginOK`;
                    }else{
                        return window.location.href = `LoginError`;
                    }


                }).catch(error =>{
                console.log(error)
            })
        }


    }

    render(){
        return(
            <button
                className={"btn btn-primary"}
                onClick={this.enviar}>
                {this.state.titulo === "R"?"Registrarse":"Iniciar Sesion"}
            </button>
        );
    }
}

export default Boton;
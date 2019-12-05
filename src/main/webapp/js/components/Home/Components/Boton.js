import React from 'react';
import axios from 'axios';
import { host } from '../../../host.js'

class Boton extends React.Component{
  constructor(props){
    super(props);

    this.state = {
      titulo: props.titulo,
      email: props.email,
      password: props.password
    }
  }


  render(){
    return(
      <button
        className={"btn btn-primary"}
        onClick={() => {}}>
        {this.state.titulo === "R" ? "Registrarse":"Iniciar Sesion"}
      </button>
    );
  }
}

export default Boton;
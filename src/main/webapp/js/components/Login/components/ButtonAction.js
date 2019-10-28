import React from 'react';
import axios from 'axios';
import { host } from '../../../host.js'

class ButtonAction extends React.Component{
  constructor(props){
    super(props);

    this.state={
      titulo: props.titulo
    }
  }

  onSubmit = (e) => {
    this.props.onSubmit();
  }

  render(){
    const { titulo } = this.state
    return(
      <button
        className={"btn btn-primary"}
        onClick={e => this.onSubmit(e)}>
        {titulo === "R" ? "Registrarse" : "Iniciar Sesion"}
      </button>
    );
  }
}

export default ButtonAction;
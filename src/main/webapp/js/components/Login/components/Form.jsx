import React from "react";
import { host } from '../../../host.js';
import FormRegistro from './FormRegistro.jsx';
import FormLogin from './FormLogin.jsx';
import axios from "axios";

class Form extends React.Component {
  state = {
    isLogin: true,
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

  componentDidMount() {
    // if (typeof(duplicado) != "undefined"){
    //   if(duplicado =="duplicado"){
    //     this.setState({
    //       registro: "duplicado"
    //     })
    //   }
    // }
    // if (typeof(registroError) != "undefined" && registroError != ""){
    //   this.setState({
    //     registroUsuario: {
    //       mensaje: registroError,
    //       status: "danger"
    //     }
    //   })
    // } else if (typeof(registroExito) != "undefined" && registroExito != ""){
    //   this.setState({
    //     registroUsuario: {
    //       mensaje: registroExito,
    //       status: "success"
    //     }
    //   })
    //   $('#element').tooltip('show')
    // }
    //
    // if (typeof(errorLogin) != "undefined" && errorLogin != ""){
    //   this.setState({
    //     session: {
    //       login: false,
    //       username: '',
    //       email: '',
    //       error: errorLogin
    //     }
    //   })
    // }
    //
    // if(typeof(login) != "undefined" && login == "true"){
    //   this.setState({
    //     session: {
    //       id: id,
    //       login: true,
    //       username: 'Un nombre',
    //       email: email,
    //       error: ''
    //     }
    //   })
    // }
  }

  isLogin = async (isLogin) => {
    await this.setState({
      isLogin: isLogin
    })
  }

  cerrarSesion = () => {
    axios.post(`${host}/cerrarSesion`)
      .then(response =>{
          window.location.href = `${host}/home`
      })
      .catch(error =>{
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

  render () {
    const { isLogin, session } = this.state
    const { usuario, errors } = this.props
    console.log("usuario", usuario.email)
    return(
      <>
        {id !== '' ? (
          <>
            <h3>Bienvenido {usuario.email}</h3>
            <button className={"btn btn-danger"} onClick={this.cerrarSesion}>
              Cerrar sesion
            </button>
          </>
        ) : (
          <>
          {isLogin ? (
              <FormLogin
                isLogin={this.isLogin}
                {...this.props}
              />
            ) : (
              <FormRegistro
                isLogin={this.isLogin}
                {...this.props}
              />
          )}
            {errors.errorLogin && (
              <p>{errors.errorLogin}</p>
            )}
            {/*{this.state.registroUsuario.mensaje!=""?<div className={"alert alert-" + this.state.registroUsuario.status } role="alert">*/}
            {/*  {this.state.registroUsuario.mensaje}*/}
            {/*</div>:""}*/}

            {/*{this.state.session.error!=""?this.state.session.error:""}*/}
          </>
        )}

      </>
    )
  }
}

export default Form;
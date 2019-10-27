import React from "react";
import { host } from '../../../host.js';
import ButtonAction from "./ButtonAction.js";

class FormRegistro extends React.Component {

  state = {
    email: '',
    password: '',
    passwordConfirm:'',
    message: '',
    isLogin: false
  }

  onChange = (ev)=>{
    this.setState({
      [ev.target.name] : ev.target.value
    })

  }

  onChangeForm = async () => {
    const { isLogin } = this.state
    await this.setState({
      isLogin: !this.state.isLogin
    })
    this.props.isLogin(isLogin)
  }

  onSubmit = async () => {
    const { email, password} = this.state
    await this.props.onSubmit("R",email, password)
  }

  render () {
    return (
      <div className="inner-container">
        <div className="box">
          {/* <div className="form-group">
                        <label htmlFor="username">UserName</label>
                        <input className="form-control" type="text" name="username" placeholder="Username"/>
                    </div> */}
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              className="form-control"
              type="text"
              name="email"
              placeholder="Ingrese su email"
              value={this.state.email}
              onChange={this.onChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Contraseña</label>
            <input
              className="form-control"
              type="password"
              name="password"
              placeholder="Password"
              value={this.state.password}
              onChange={this.onChange}
            />
          </div>
          <div className="row justify-content-center">
            {/* <button type="button" className="btn btn-primary">Registrarse</button> */}
            <ButtonAction
              titulo="R"
              onSubmit={this.onSubmit}
            />
          </div>
          <p>Ya tenes cuenta? <a onClick={this.onChangeForm}>Ingresá</a></p>
        </div>
      </div>
    )
  }
}

export default FormRegistro;
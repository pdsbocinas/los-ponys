import React from "react";
import { host } from '../../../host.js';
import ButtonAction from "./ButtonAction.js";

class FormLogin extends React.Component {
  state={
    email: '',
    password: '',
    isLogin: true
  }

  onChange = async (ev)=>{
    await this.setState({
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
    await this.props.onSubmit("login",email, password)
  }

  render(){
    return(
      <div className="box">
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            className="form-control"
            type="text"
            name="email"
            placeholder="Ingrese su email"
            value={this.state.email}
            onChange={this.onChange}/>
        </div>

        <div className="form-group">
          <label htmlFor="password">Contraseña</label>
          <input
            className="form-control"
            type="password"
            name="password"
            placeholder="Password"
            value={this.state.password}
            onChange={this.onChange}/>
        </div>
        <div className="row justify-content-center">
          {/* <button type="button" className="btn btn-primary">Login</button> */}
          <ButtonAction
            titulo="login"
            onSubmit={this.onSubmit}
          />
        </div>
        <p>No tenes cuenta? <a onClick={this.onChangeForm}>Registrate</a></p>
      </div>
    )

  }
}

export default FormLogin;
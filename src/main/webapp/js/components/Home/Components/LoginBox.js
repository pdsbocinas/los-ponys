import React from 'react';
import Boton from './Boton';

class LoginBox extends React.Component{
    state={
        email: '',
        password: ''
    }
    onChange = (ev)=>{
        this.setState({
            [ev.target.name] : ev.target.value
        })

    }
    render(){
        return(
            <div className="box col-3">
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
                    <Boton
                        titulo="login"
                        email = {this.state.email}
                        password = {this.state.password}
                    />
                </div>
            </div>
        )

    }
}

export default LoginBox;
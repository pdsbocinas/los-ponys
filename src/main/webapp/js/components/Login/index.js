import React from "react";
import ReactDOM from "react-dom";
import Button from './components/button.jsx';
import Form from './components/Form.jsx';
import styled from 'styled-components'
import axios from "axios";
import {host} from "../../host";

const Wrapper = styled.section`
  padding: 4em;
  background: papayawhip;
  position: absolute;
  top: 60px;
  right: 40px;
  z-index: 1;
`;

class App extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      active: false,
      titulo: '',
      email: '',
      password: ''
    }
  }

  enviar = async (titulo, email, password) => {
    await this.setState({
      email: email,
      password: password,
      titulo: titulo
    })

    const data = {
      "email": this.state.email,
      "password": this.state.password
    }
    console.log(data)
    if(titulo === "R"){

      this.props.validate;

      axios
        .post(`${host}/guardarUsuario`, data)
        .then(response =>{
          console.log("response registro", response);
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
    } else {
      axios
        .post(`${host}/validar-login2`, data)//Devuelve "correcto" o error
        .then(response =>{
          console.log("response login", response);
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

  onChangeActive = async (active) => {
    await this.setState({
      active: !this.state.active
    })
  }

  render () {
    const { active } = this.state;
    console.log(notFound)
    return (
      <>
        <Button
          onChangeActive={this.onChangeActive}
        />
        {active && (
          <Wrapper>
            <Form
              onSubmit={this.enviar}
            />
          </Wrapper>
        )}
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#login"));
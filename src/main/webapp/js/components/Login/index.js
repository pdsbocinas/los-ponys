import React from "react";
import ReactDOM from "react-dom";
import Button from './components/Button.jsx';
import Form from './components/Form.jsx';
import styled from 'styled-components'
import axios from "axios";
import {host} from "../../host";

const Wrapper = styled.section`
  width : 300px;
  border-bottom-right-radius: 2em;
  border-bottom-left-radius: 2em;
  padding: 4em;
  background: #c67441ad;
  position: absolute;
  top: 40px;
  right: 70px;
  z-index: 1;
  width: 310px;
  
  p {
    margin-bottom: 0px;
    color : white;
  }
  
  a {
    display: block;
    margin-bottom: 15px;
    cursor: pointer;
  }
`;

class App extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      active: false,
      titulo: '',
      email: '',
      password: '',
      reviews: [],
      errors: []
    }
  }

  componentDidMount() {
    if (id !== '') {
      axios.get(`${host}/api/viajes/comentarios/no-leido`, {
        params: {
          id: id
        }
      })
      .then( async response => {
        await this.setState({
          reviews: [...this.state.reviews].concat(response.data)
        })
      })
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

    if(titulo === "R"){

      axios
        .post(`${host}/guardarUsuario`, data)
        .then(async (response) =>{
          if (response.data === "El mail ya existe") {
            return await this.setState({
              errors: { errorLogin: response.data }
            })
          } else {
            return window.location.href = `registroOK`;
          }
        }).catch(error =>{
        console.log(error)
      })
    } else {
      axios
        .post(`${host}/validar-login2`, data)//Devuelve "correcto" o error
        .then(async (response) =>{
          const responseLogin = response.data;
          if (responseLogin.errorLogin) {
            return await this.setState({
              errors: responseLogin
            })
          } else {
            return window.location.href = 'home';
          }
        }).catch(error =>{
          console.log(error)
      })
    }
  }

  onChangeActive = async (module) => {
    await this.setState({
      active: module
    })
  }

  checkTravel = async (user_id, travel_id) => {

    axios
      .get(`${host}/viajes/ver-comentarios`, {
        params: {
          id: parseInt(user_id, 10)
        }
      })
      .then(response =>{
        console.log("response checkTravel", response);
        return window.location.href = `viajes/${travel_id}/comentar`
      }).catch(error =>{
      console.log(error)
    })
  }

  render () {
    const { active, reviews, errors } = this.state;
    console.log("user", usuario);
    console.log(errors)
    return (
      <>
        <Button
          onChangeActive={this.onChangeActive}
          cant={reviews.length}
          id={id}
          usuario={usuario}
        />
        {active === 'forms' && (
          <Wrapper>
            <Form
              usuario={usuario}
              onSubmit={this.enviar}
              errors={errors}
            />
          </Wrapper>
        )}
        {active === 'review' && usuario.email !== null && (
          <Wrapper>
            {reviews.length !== 0 && (
              <>
              {reviews.map(v => {
                return (
                  <>
                    <a onClick={() => this.checkTravel(id, v.viaje.id)}>
                      <p><strong>Comentario: </strong>{v.texto}</p>
                      <span><strong>Viaje: </strong>{v.viaje && v.viaje.titulo}</span>
                    </a>
                  </>
                  )
              })}
              </>
            )}
          </Wrapper>
        )}
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#login"));
import React from "react";
import ReactDOM from "react-dom";
import Button from './components/button.jsx';
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
      reviews: []
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
    const { active, reviews } = this.state;
    console.log('id',id)
    console.log('reviews', reviews, reviews.length)
    return (
      <>
        <Button
          onChangeActive={this.onChangeActive}
          cant={reviews.length}
          id={id}
        />
        {active === 'forms' && (
          <Wrapper>
            <Form
              onSubmit={this.enviar}
            />
          </Wrapper>
        )}
        {active === 'review' && id !== '' && (
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
import React from "react";
import ReactDOM from "react-dom";
import { Button, Modal, Form, Badge, ButtonToolbar, ToggleButtonGroup, ToggleButton } from 'react-bootstrap';
import {Container, Row, Col} from 'react-bootstrap';
import axios from "axios";
import {host} from "../../host";
import Calendar from 'react-calendar';

class App extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      show: false,
      titulo: "",
      mails: [],
      fechaInicio: new Date(),
      fechaFin: new Date(),
      usuarios: [],
      hoy: new Date (),
      privacidad: "privado"
    }
  }


  handleClose = () => this.setState({ show: false});
  handleShow = () => this.setState({ show: true});

  onChangeTitle = async (e) => {
    await this.setState({
      titulo: e.target.value
    })
  }

  onSave = () => {
    axios.post(`${host}/api/viajes`, {
      titulo: this.state.titulo,
      fechaInicio: this.state.fechaInicio,
      fechaFin: this.state.fechaFin,
      destinos: [],
      usuarios: this.state.usuarios.concat(email),
      privacidad: this.state.privacidad
    })
    .then(res => {
      const id = res.data.id;
      this.handleClose()
      // aca lo redirecciono a la url id para que empiece a seleccionar destinos
      return window.location.href = `viajes/${id}`
    })
  }

  setDate = (date) =>{
    const day = date.getDate();
    const month = date.getMonth();
    const year = date.getFullYear();
    return new Date ( year, month, day );
  }

  onChangeFrom = async (date) =>{
    const newDate = this.setDate(date);
    await this.setState({ fechaInicio : newDate })
  }

  onChangeEnd = async (date) => {
    const newDate = this.setDate(date);
    await this.setState({ fechaFin : newDate })
  }

  keyPress = async (e) => {
    if(e.keyCode == 13){
      const email =  e.target.value;
      console.log('email', e.target.value);
      return await this.setState({
        usuarios: [...this.state.usuarios].filter(v => v !== email).concat(email),
        email: ""
      })
    }
  }

  handleChangeUsers = (e) => {
    this.setState({ email: e.target.value });
  }

  delete = async (email) => {
    return await this.setState({
      usuarios: [...this.state.usuarios].filter(v => v !== email)
    })
  }

  handlePrivacity = async (e) => {
    return await this.setState({
      privacidad: e
    })
  }

  render () {
    const { show, usuarios, privacidad } = this.state
    console.log(privacidad)
    return (
      <>
        <div className="card" style={{ width: '18rem' }}>
          <div className="card-body">
            <h5 className="card-title">Crear viaje</h5>
            <p className="card-text">Crea y comparti tu experiencia de viajar.</p>
            <Button onClick={this.handleShow} variant="primary">Empezar</Button>
          </div>
        </div>
        <Modal show={show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Crea tu viaje</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group controlId="titulo">
              <Form.Label>Titulo</Form.Label>
              <Form.Control required type="text" value={this.state.titulo} onChange={e => this.onChangeTitle(e)} placeholder="Ingresa un titulo" />
            </Form.Group>
            <ButtonToolbar>
              <ToggleButtonGroup type="radio" name="options" onChange={e => this.handlePrivacity(e)} defaultValue="privado">
                <ToggleButton value="privado">Privado</ToggleButton>
                <ToggleButton value="publico">Publico</ToggleButton>
              </ToggleButtonGroup>
            </ButtonToolbar>
            <Form.Group controlId="compartir">
              <Form.Label>Comparti tu viaje con alguien mas:</Form.Label>
              <Form.Control onKeyDown={(e) => this.keyPress(e)} onChange={ e => this.handleChangeUsers(e)} type="text" placeholder="Ingresa emails:" />
              {this.state.usuarios && this.state.usuarios.map((v,i) => {
                return(
                  <div key={`${v}-${i}`}>
                  <h5>
                    <Badge variant="secondary">
                      {v}
                    </Badge>
                    <a onClick={() => this.delete(v)}>x</a>
                  </h5>
                  </div>
                )
              })}

            </Form.Group>
            <Row>
              <Col>
                <Form.Group controlId="fechaInicio">
                  <Form.Label>Inicio:</Form.Label>
                  <Form.Control
                      type="text"
                      placeholder="DD/MM/AAAA"
                      value={this.state.fechaInicio}
                  />
                </Form.Group>
              </Col>
              <Col>
                <Form.Group controlId="fechaFin">
                  <Form.Label>Fin:</Form.Label>
                  <Form.Control type="text" value={this.state.fechaFin} placeholder="DD/MM/AAAA" />
                </Form.Group>
              </Col>
            </Row>

            <Row>
              <Col>
                <Calendar
                    onChange={this.onChangeFrom}
                    value={this.state.fechaInicio}
                />
              </Col>
              <Col>
                <Calendar
                  onChange={this.onChangeEnd}
                  value={this.state.fechaFin}
                />
              </Col>
            </Row>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              Cerrar
            </Button>
            <Button variant="primary" onClick={() => this.onSave()}>
              Guardar
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
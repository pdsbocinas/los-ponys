import React from "react";
import ReactDOM from "react-dom";
import { Button, Modal, Form, Badge } from 'react-bootstrap';
import axios from "axios";
import {host} from "../../host";

class App extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      show: false,
      titulo: "",
      mails: []
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
    // solo le mando para que setee el titulo
    axios.post(`${host}/guardarViaje`, {
      titulo: this.state.titulo
    })
    .then(res => {
      const id = res.data.id;
      this.handleClose()
      // aca lo redirecciono a la url id para que empiece a seleccionar destinos
      return window.location.href = `viajes/${id}`
    })
  }

  render () {
    const { show } = this.state
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
            <Modal.Title>Modal heading</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group controlId="titulo">
              <Form.Label>Titulo</Form.Label>
              <Form.Control type="text" value={this.state.titulo} onChange={e => this.onChangeTitle(e)} placeholder="Ingresa un titulo" />
            </Form.Group>
            <Form.Group controlId="compartir">
              <Form.Label>Comparti tu viaje con alguien mas:</Form.Label>
              <Form.Control type="text" placeholder="Ingresa un titulo" />
              <h5>
                <Badge variant="secondary">pds.gomez@gmail.com</Badge>
              </h5>
            </Form.Group>
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
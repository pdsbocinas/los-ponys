import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import List from './components/List';
import { Container, Row, Button } from 'react-bootstrap';

// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {
    destination: [],
    keyword: '',
    loading: true,
    destinationSelected: []
  }

  // este metodo de ciclo de vida de React se va a ejecutar cuando el componente se termine de cargar en el DOM
  componentDidMount () {
  }



  onSearch = async (keyword) => {
    await this.setState({ keyword })
    if (keyword.length > 4) {
      axios.get(`${host}/api/destinos`, {
        params: {
          keyword: this.state.keyword
        }
      })
      .then(async res => {
        // aca res es lon que me devuelve ese endpoint. Devuelve un json con varias cosas, a nosotros nos interesa el data
        // y lo seteamos con setState en el estado del componente
        const destination = res.data;
        await this.setState({ destination });
      })
    }
  }

  onSelect = async (destin) => {
    console.log(destin)
    await this.setState({
      destinationSelected: [...this.state.destinationSelected].filter(v => v.placeId !== destin.placeId).concat(destin)
    })
  }

  onSaveDestination = () => {
    const { destinationSelected } = this.state;
    const placeIds = destinationSelected.map(v => { return (v.placeId)});
    const pageURL = window.location.href;
    const lastURLSegmentId = pageURL.substr(pageURL.lastIndexOf('/') + 1);

    axios.post(`${host}/api/viajes/${lastURLSegmentId}/destinos`, {
      destinos: placeIds
    })
    .then(res => {
      return res.data;
    })
    .catch(e => console.log(e))
  }

  render () {
    const { destination, keyword } = this.state;
    return (
      <Container>
        <Row className="justify-content-md-center">
          <form style={{
            width: '53%',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
          }}>
            <input className="form-control" style={{maxWidth: '600px', height: '45px', margin: '0px'}} type="text" value={keyword} onChange={ev => this.onSearch(ev.target.value)} placeholder='Buscar por destino'/>
            <Button style={{
              width: '230px',
              height: '45px',
              margin: '0'
            }} onClick={this.onSaveDestination} variant="primary">Guardar destinos</Button>
          </form>
        </Row>
        {keyword.length > 4 && (
          <ul style={{ maxWidth: '600px', height: '350px', margin: '15px auto 40px', overflow: 'scroll' }} className="list-group">
            {destination.map((destin, i) => (
              <li key={`${i}-${destin.name}`} className="list-group-item">
                <a style={{ cursor: 'pointer'}} onClick={() => this.onSelect(destin)}>{destin.name}</a>
              </li>
            ))}
          </ul>
        )}
        <List items={this.state.destinationSelected} />
      </Container>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
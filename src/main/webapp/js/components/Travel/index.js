import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import List from './components/List';
import { Container, Row, Button } from 'react-bootstrap';
import styled from 'styled-components'

const Title = styled.h1`
  text-align: center;
  margin-bottom: 20px;
  margin-top: 30px;
`;

const Subtitle = styled.h2`
  text-align: center;
  margin-bottom: 20px;
  margin-top: 30px;
  font-size: 24px;
`;

class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {
    destination: [],
    keyword: '',
    loading: true,
    destinationSelected: []
  }

  delete = (nombre) =>{
    const destinationSelectedCopy = Object.assign([], this.state.destinationSelected);
    destinationSelectedCopy.splice(nombre, 1);
    this.setState({destinationSelected: destinationSelectedCopy});
  }

  onChangeFrom = async (date) =>{
    const newDate = this.setDate(date);
    await this.setState({ fechaInicio : newDate })
  }

  onChangeEnd = async (date) => {
    const newDate = this.setDate(date);
    await this.setState({ fechaFin : newDate })
  }

  update = (fechaInicio, fechaFin,id) =>{
    const destino = this.state.destinationSelected.filter((d)=>{
      console.log("destino:"+ d)
        if(d.id == id){
            return true;
        }
    })
   console.log(fechaInicio)
    console.log(fechaFin)
    console.log(id)


   console.log("destino",destino)

    //const destinoBuscado = destino[0]
    //console.log("destino buscado")
    //console.log(destinoBuscado)
    return;
    const pageURL = window.location.href;
    const lastURLSegmentId = pageURL.substr(pageURL.lastIndexOf('/') + 1);
    axios.post(`${host}/api/viajes/${lastURLSegmentId}/destino/4/guardar-fecha`, {
        //destinoBuscado
        id : destinoBuscado.id,
        fechaInicio : destinoBuscado.fechaInicio,
        fechaHasta: destinoBuscado.fechaHasta
        //id: 4,
        //fechaInicio: '2019-01-01',
        //fechaHasta: '2019-12-01'

    })
    .then(res => {
        console.log(res.data)
        const destino_id = res.data
      const url =  window.location.href = `${host}/viaje/${lastURLSegmentId}destino/${destino_id}/vista`
      return url;
    })
    .catch(e => console.log(e))
  }


  // este metodo de ciclo de vida de React se va a ejecutar cuando el componente se termine de cargar en el DOM
  componentDidMount () {
    const pageURL = window.location.href;
    const lastURLSegmentId = pageURL.substr(pageURL.lastIndexOf('/') + 1);
    axios.get(`${host}/api/viajes/${lastURLSegmentId}obtener-destinos`)
      .then(async v => {
        const data = v.data
        await this.setState({
          destinationSelected: data
        })
      })
      .catch(e => {
        console.log(e)
      })
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
    console.log(destinationSelected);
    /*const destination = destinationSelected.map((d)=>{
      let obj = {}
      obj["ciudad"] = d.name
      obj["nombre"] = d.name
      obj["placeId"] = d.placeId
      obj["fechaInicio"] = ''
      obj["fechaHasta"] = ''
      obj["lat"] = d.geometry.location.lat
      obj["lng"] = d.geometry.location.lng
      return obj

    })
    console.log(destination)
    const arrayDestination = [destination]*/

    //const placeIds = destinationSelected.map(v => { return (v.placeId)});
    const pageURL = window.location.href;
    const lastURLSegmentId = pageURL.substr(pageURL.lastIndexOf('/') + 1);

    axios.post(`${host}/api/viajes/${lastURLSegmentId}/guardarDestinos`,
       destinationSelected
      //destination

    )
    .then(res => {
      const url =  window.location.href = `${host}/viajes/${lastURLSegmentId}/destino`
      return url;
    })
    .catch(e => console.log(e))
  }

  render () {
    const { destination, keyword, destinationSelected } = this.state;
    return (
      <Container>
        <Title>Agrega destinos a tu viaje</Title>
        <Row className="justify-content-md-center">
          <form style={{
            width: '53%',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            marginBottom: '55px'
          }}>
            <input className="form-control" style={{maxWidth: '600px', height: '45px', margin: '0px'}} type="text" value={keyword} onChange={ev => this.onSearch(ev.target.value)} placeholder='Buscar por destino'/>
            <Button style={{
              width: '230px',
              height: '45px',
            }} onClick={this.onSaveDestination} variant="primary">Guardar destinos</Button>
          </form>
        </Row>
        {destination.length === 0 && (
          <>
            <Subtitle>No tenés ningún destino aun, empezá a agregar</Subtitle>
          </>
        )}
        {keyword.length > 4 && (
          <ul style={{ maxWidth: '600px', height: '350px', margin: '15px auto 40px', overflow: 'scroll' }} className="list-group">
            {destination.map((destin, i) => (
              <li key={`${i}-${destin.name}`} className="list-group-item">
                <a style={{ cursor: 'pointer'}} onClick={() => this.onSelect(destin)}>{destin.name}</a>
              </li>
            ))}
          </ul>
        )}
        <List
          items={destinationSelected}
          delete={(nombre)=>this.delete(nombre)}
          onSelect={this.update}
        />
      </Container>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import { Row, Col, FormGroup, FormLabel, FormControl, FormCheck} from 'react-bootstrap';
import Calendar from 'react-calendar';

class App extends React.Component {
  constructor (props) {
    const urlParams = new URLSearchParams(window.location.search);
    super(props);
    this.state = {
      alojamientos: [],
      params: {}
    }
  }


  getAlojamientos = () => {
    const { params } = this.state
    axios.get(`${host}/api/alojamientos`,{
      params: params
    })
    .then(async v => {
      const data = v.data
      return await this.setState({
        alojamientos: data
      })
    })
    .catch(e => {
      console.log(e)
    })
  }

  componentDidMount () {
    this.getAlojamientos();
  }

  onChangeFrom = async (e) => {
    let value = e.target && e.target.value
    await this.setState(prevState => ({
      params: {
        ...prevState.params,
        precioDesde: value
      }
    }))
  }

  onChangeTo = async (e) => {
    let value = e.target && e.target.value
    await this.setState(prevState => ({
      params: {
        ...prevState.params,
        precioHasta: value
      }
    }))
  }

  onChangePrice = async () => {
    await this.getAlojamientos();
  }

  onChangeStars = async (e) => {
    let value = e.target && e.target.value
    await this.setState(prevState => ({
      params: {
        ...prevState.params,
        rating: value
      }
    }))
    await this.getAlojamientos();
  }

  onChangeDiscount = async (e) => {
    let value = e.target && e.target.value
    await this.setState(prevState => ({
      params: {
        ...prevState.params,
        descuento: value
      }
    }))
    await this.getAlojamientos();
  }

  onChangeOffers = async (e) => {
    let value = e.target && e.target.value
    console.log(value)
    await this.setState(prevState => ({
      params: {
        ...prevState.params,
        ofertas: !this.state.params.ofertas
      }
    }))
    await this.getAlojamientos();
  }

  render () {
    const { alojamientos, params } = this.state
    console.log(params)
    return (
      <>
        <Row>
          <Col sm={2}>
            <FormGroup controlId="precio">
              <FormLabel>Precio</FormLabel>
              <FormControl onChange={e => this.onChangeFrom(e)} type="text" placeholder="Desde:" />
              <FormControl onChange={e => this.onChangeTo(e)} type="text" placeholder="Hasta:" />
              <FormControl type="submit" onClick={this.onChangePrice} className="btn btn-primary" value="Buscar" placeholder="Buscar" />
            </FormGroup>
            <FormGroup controlId="rating">
              <FormLabel>Estrellas</FormLabel>
              <FormCheck onChange={e => this.onChangeStars(e)} value="1" checked={params.rating === "1"} type="checkbox" label="1 Estrellas" />
              <FormCheck onChange={e => this.onChangeStars(e)} value="2" checked={params.rating === "2"} type="checkbox" label="2 Estrellas" />
              <FormCheck onChange={e => this.onChangeStars(e)} value="3" checked={params.rating === "3"} type="checkbox" label="3 Estrellas" />
              <FormCheck onChange={e => this.onChangeStars(e)} value="4" checked={params.rating === "4"} type="checkbox" label="4 Estrellas" />
              <FormCheck onChange={e => this.onChangeStars(e)} value="5" checked={params.rating === "5"} type="checkbox" label="5 Estrellas" />
            </FormGroup>
            <FormGroup controlId="descuentos">
              <FormLabel>Descuentos</FormLabel>
              <FormCheck onChange={e => this.onChangeDiscount(e)} value="5" checked={params.descuento === "5"} type="checkbox" label="Desde 5% OFF" />
              <FormCheck onChange={e => this.onChangeDiscount(e)} value="10" checked={params.descuento === "10"} type="checkbox" label="Desde 10% OFF" />
              <FormCheck onChange={e => this.onChangeDiscount(e)} value="15" checked={params.descuento === "15"} type="checkbox" label="Desde 15% OFF" />
              <FormCheck onChange={e => this.onChangeDiscount(e)} value="20" checked={params.descuento === "20"} type="checkbox" label="Desde 20% OFF" />
            </FormGroup>
            <FormGroup controlId="ofertas">
              <FormLabel>Ofertas</FormLabel>
              <FormCheck onChange={e => this.onChangeOffers(e)} checked={params.ofertas} value={params.ofertas} type="checkbox" label="Con cancelacion gratuita" />
            </FormGroup>
          </Col>
          <Col sm={10}>
            <Row>
              <Col>
                <FormControl onChange={e => this.onChangeFrom(e)} type="text" placeholder="Desde:" />
                <Calendar />
              </Col>
              <Col>
                <FormControl onChange={e => this.onChangeTo(e)} type="text" placeholder="Hasta:" />
                <Calendar />
              </Col>
            </Row>
            <Row>
              <Col>col</Col>
              <Col>col</Col>
            </Row>
          </Col>
        </Row>
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
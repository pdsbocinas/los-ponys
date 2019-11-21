import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import {Col, Row} from "react-bootstrap";
import List from './components/List';
import Slider from "react-slick";

class App extends React.Component {

  state = {
    recomendaciones: [],
    params: {
      tipo: "museos en",
      destino: "San Luis Argentina"
    }
  }

  componentDidMount() {
    this.getRecomendaciones();
  }

  getRecomendaciones = () => {
    const { params } = this.state
    axios.get(`${host}/api/recomendaciones`,{
      params: params
    })
    .then(async v => {
      const data = v.data;
      return await this.setState({
        recomendaciones: data
      })
    })
    .catch(e => {
      console.log(e)
    })
  }

  render () {
    const { recomendaciones } = this.state
    return (
      <Row>
          <List
            items={recomendaciones}
          />
      </Row>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#puntos"));
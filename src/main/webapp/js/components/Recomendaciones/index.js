import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js';
import List from './components/List';

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
    const destinos = destinosJson.map(v => v.ciudad)

    destinos.forEach(v => {
      axios.get(`${host}/api/recomendaciones`,{
        params: {
          destino: v
        }
      })
      .then(async v => {
        const data = v.data;
        return await this.setState({
          recomendaciones: this.state.recomendaciones.concat(data)
        })
      })
      .catch(e => {
        console.log(e)
      })
    })
  }

  render () {
    const { recomendaciones } = this.state

    return (
      <>
        <h2>Museos</h2>
        <List
          items={recomendaciones && recomendaciones[0]['museos']}
        />
        <h2>Restaurantes</h2>
        <List
          items={recomendaciones && recomendaciones[0]['restaurantes']}
        />
        <h2>Monumentos</h2>
        <List
          items={recomendaciones && recomendaciones[0]['monumentos']}
        />
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#puntos"));
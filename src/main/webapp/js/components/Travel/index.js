import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';
import { host } from '../../host.js'

// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
class App extends React.Component {

  // aca inicializamos el State local del componente
  state = {
    destination: [],
    keyword: '',
    loading: true
  }

  // este metodo de ciclo de vida de React se va a ejecutar cuando el componente se termine de cargar en el DOM
  componentDidMount () {
    // AXIOS es una libreria para hacer GET y POST a un endpoint cualquiera, devuelve lo que se llama una Promesa
    // con ese .then
  }

  countriesFilter = (destination) => {
    const { keyword } = this.state
    return destination.name.indexOf(keyword) !== -1;
  }

  onSearch = async (keyword) => {
    await this.setState({ keyword })
    console.log(keyword)
    axios.get(`${host}/api/queries`, {
      params: {
        keyword: this.state.destination
      }
    })
      .then(res => {
        // aca res es lon que me devuelve ese endpoint. Devuelve un json con varias cosas, a nosotros nos interesa el data
        // y lo seteamos con setState en el estado del componente
        const destination = res.data;
        this.setState({ destination });
      })
  }

  render () {
    const { destination, keyword } = this.state;
    console.log(destination)
    return (
      <>
        <form>
          <input class="form-control" style={{ maxWidth: '600px', height: '45px', margin: '15px auto 20px' }} type="text" value={keyword} onChange={ev => this.onSearch(ev.target.value)} placeholder='Buscar por destino' />
        </form>
        <ul style={{ maxWidth: '600px', height: '350px', margin: '15px auto 40px', overflow: 'scroll' }} className="list-group">
          {destination.filter(this.countriesFilter).map((destin, i) => (
            <li key={`${i}-${destin.name}`} className="list-group-item">
              <a href={`countries/${destin.name}`}>{destin.name}</a>
            </li>
          ))}
        </ul>
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
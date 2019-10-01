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
    loading: true,
    selected: []
  }

  // este metodo de ciclo de vida de React se va a ejecutar cuando el componente se termine de cargar en el DOM
  componentDidMount () {
    // AXIOS es una libreria para hacer GET y POST a un endpoint cualquiera, devuelve lo que se llama una Promesa
    // con ese .then
  }

  onSearch = async (keyword) => {
    await this.setState({ keyword })
    if (keyword.length > 4) {
      axios.get(`${host}/api/queries`, {
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

  onSelect = (destin) => {
    this.setState({
      selected: this.state.selected.concat(destin)
    })
  }

  render () {
    const { destination, keyword, selected } = this.state;
    return (
      <>
        <form>
          <input class="form-control" style={{ maxWidth: '600px', height: '45px', margin: '15px auto 20px' }} type="text" value={keyword} onChange={ev => this.onSearch(ev.target.value)} placeholder='Buscar por destino' />
        </form>
        <ul style={{ maxWidth: '600px', height: '350px', margin: '15px auto 40px', overflow: 'scroll' }} className="list-group">
          {destination.map((destin, i) => (
            <li key={`${i}-${destin.name}`} className="list-group-item">
              <a onClick={() => this.onSelect(destin)}>{destin.name}</a>
            </li>
          ))}
        </ul>
        {selected.length !== 0 && (
          <>
          {selected.map(selected => {
            <div className="container">
              <div className="card" style={{ width: '18rem'}}>
                <img src={selected.icon} className="card-img-top" alt={selected.name} />
                <div className="card-body">
                  <h5 className="card-title">{selected.name}</h5>
                  <p className="card-text">{selected.formattedAddress}</p>
                  <a href="#" className="btn btn-primary">Go somewhere</a>
                </div>
              </div>
            </div>
          })}
          </>
        )}
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
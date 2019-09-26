import React from "react";
import ReactDOM from "react-dom";
import Button from './components/button.jsx';
import 'babel-polyfill';

class App extends React.Component {
  constructor (props) {
    super(props);
    this.state = {
      titulo: "Seteame el titulo",
      lista: ["banana","manzana", "pera"],
      listaDeObjetos: [
        {
          nombre: "Pablo",
          edad: 33
        },
        {
          nombre: "Armando",
          edad: 28
        }
      ]

    }
  }

  getDataFromChild = async (titulo) => {
    await this.setState({
      titulo
    })
  }

  render () {
    const { lista, listaDeObjetos } = this.state;
    return (
      <>
        <h1>{this.state.titulo}</h1>
        <Button
          titulo="soy el boton"
          onSendToParent={this.getDataFromChild}
        />
        <p>Lista simple</p>
        {lista.map((v) => {
          return (
            <p>{v}</p>
          )
        })}
        {listaDeObjetos.map(i => {
          return (
            <div>
              <p>{i.nombre}</p>
              <p>{i.edad}</p>
            </div>
          )
        })}
      </>
    )
  }
}

ReactDOM.render(<App />, document.querySelector("#root"));
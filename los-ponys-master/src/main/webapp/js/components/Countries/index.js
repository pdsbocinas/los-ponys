import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';

// esto es una de las formas para hacer consumo de apis via json, mirar el Controller Pais, metodo getPaisesJson
const getPaises = () => {
  let data = []
  // leer la documentacion de axios para ver que puede recibir este objeto
  const res = axios({
    url: 'http://localhost:8080/Los_Ponys_war/api/countries',
    method: 'get'
  }).then(response => { data = response.data; return data })
  .catch(err => console.log(err))
  return res
}

const App = () => {
  // y aca se le pasa la funcion y se mapea el json
  const paises = getPaises().then(v => { return v });
  console.log(paises)
  return (
    <>
      <h1>Soy los paises del Mundo!!</h1>
    </>
  )
};

ReactDOM.render(<App />, document.querySelector("#root"));
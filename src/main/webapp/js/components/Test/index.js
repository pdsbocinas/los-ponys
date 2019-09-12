import React from "react";
import ReactDOM from "react-dom";
import axios from 'axios';

const App = () => {
  console.log("soy axios", axios)
  return <div>rrrrrrReact,Webpack 4 & Babel 7!</div>;
};

ReactDOM.render(<App />, document.querySelector("#root"));
import React, { Component } from 'react';
import { render } from 'react-dom';
import { withScriptjs } from "react-google-maps";
import Map from './components/Map.js';


const App = () => {
  const MapLoader = withScriptjs(Map);

  return (
    <>
      <MapLoader
        googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg"
        loadingElement={<div style={{ height: `100%` }} />}
      />
      <button>Queres postear tu viaje???</button>
    </>
  );
};

render(<App />, document.getElementById('root'));
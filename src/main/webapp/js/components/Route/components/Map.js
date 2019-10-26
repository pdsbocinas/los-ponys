/*global google*/
import React, { Component } from "react";

import {
  withGoogleMap,
  withScriptjs,
  GoogleMap,
  DirectionsRenderer
} from "react-google-maps";
import axios from "axios";
import {host} from "../../../host";

class Map extends Component {
  state = {
    directions: null,
    destinationSelected: []
  };

  componentDidMount() {
    const pageURL = window.location.href;
    const lastURLSegmentId = pageURL.substr(pageURL.lastIndexOf('/') + 1);
    // axios.get(`${host}/api/viajes/${lastURLSegmentId}/obtener-destinos`)
    axios.get(`${host}/api/viajes/${lastURLSegmentId}/obtener-destinos`)
      .then( async v => {
        const data = v.data

        await this.setState({
          destinationSelected: data
        })

      }).catch(e => {
      console.log(e)
    })

    // const origin = {
    //   lat: this.state.destinationSelected[0].lat,
    //   lng: this.state.destinationSelected[0].lng
    // }
    // const destination ={
    //   lat: this.state.destinationSelected[this.state.destinationSelected.length-1].lat,
    //   lng: this.state.destinationSelected[this.state.destinationSelected.length-1].lng
    // }
    const directionsService = new google.maps.DirectionsService();

    // const origin = "ChIJv3lRxD4JfpYROvoO0vZ4WiA";
    // const destination = "ChIJPdeaUuQ71JURRryKvYMe7l0";
    const origin = { lat: -32.888943, lng: -68.844954 };
    const destination = { lat: -31.534986, lng: -68.535594 };

    directionsService.route(
      {
        origin: origin,
        destination: destination,
        waypoints: [{
          location: {lat: -24.186208, lng: -65.312432},
          stopover: true
        }],

        travelMode: google.maps.TravelMode.DRIVING
      },
      (result, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
          this.setState({
            directions: result
          });
        } else {
          console.error(`error fetching directions ${result}`);
        }
      }
    );
  }

  render() {
    const GoogleMapExample = withGoogleMap(props => (
      <GoogleMap

        // defaultCenter={{ lat: 40.756795, lng: -73.954298 }}
        defaultCenter={"ChIJv3lRxD4JfpYROvoO0vZ4WiA"}
        defaultZoom={13}
      >
        <DirectionsRenderer
          directions={this.state.directions}
        />
      </GoogleMap>
    ));

    return (
      <div>
        <GoogleMapExample
          containerElement={<div style={{ height: `500px`, width: "500px" }} />}
          mapElement={<div style={{ height: `100%` }} />}
        />
      </div>
    );
  }
}

export default Map;
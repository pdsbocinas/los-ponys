/*global google*/
import React, { Component } from "react";
import {
    withGoogleMap,
    withScriptjs,
    GoogleMap,
    DirectionsRenderer
} from "react-google-maps";

class Map extends Component {
    state = {
        directions: null
    };

    componentDidMount() {
        const directionsService = new google.maps.DirectionsService();

        // const origin = { lat: 40.756795, lng: -73.954298 };
        // const destination = { lat: 41.756795, lng: -78.954298 };
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

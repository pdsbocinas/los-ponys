import React, {Component} from 'react';
import OpcionMenu from './OpcionMenu'
import {Container, Row, Col} from 'reactstrap';
// import data from '../../data/menu.json';


class Menu extends Component {

    state = {
        data:[ {
            "id": 1,
            "titulo": "Hoteles",
            "icono": "hoteles.png"
        },
        {
            "id": 2,
            "titulo": "Vuelos",
            "icono": "vuelos.png"
        },
        {
            "id": 3,
            "titulo": "Autos",
            "icono": "autos.png"
        },
        {
            "id": 4,
            "titulo": "Restaurantes",
            "icono": "restaurantes.png"
        }]
    }

    ir = (lugar) =>{
        // alert("voy a ...."+lugar)
        return window.location.href = "viajes/"+lugar;
    }

    render() {

        return (
            <Container >
                <Row className="bg-secondary">
                    {this.state.data.map((menu) => {
                        return(
                            <Col key ={menu.id}>
                                <OpcionMenu
                                    nombre={menu.titulo}
                                    icono={menu.icono}
                                    redirigir={()=>this.ir(menu.titulo)}

                                />
                            </Col>
                        )
                    })}
                </Row>
            </Container>

        )
    }
}

export default Menu;
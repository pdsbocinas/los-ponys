import React from 'react';
import {Row, Col} from 'reactstrap';


const OpcionMenu = (props)=>{


    return(
        <Row
            onClick={()=>props.redirigir()}>
            <div className={"rounded-circle bg-white p-2 m-2"}>
                <Col
                    xs="12"
                    className="text-center"
                > <img src={"/Los_Ponys_war/images/menu/"+props.icono} alt=""/>

                </Col>
                <Col
                    xs="12"
                    className="text-center">{props.nombre}
                </Col>
            </div>

        </Row>
    )
}

export default OpcionMenu;
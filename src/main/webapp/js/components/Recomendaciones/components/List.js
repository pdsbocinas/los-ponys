import React from "react";
import Slider from "react-slick";
import styled from 'styled-components'

const SliderCustom = styled(Slider)`
  height: 440px;
  .slick-dots {
    bottom: 30px;
  }
`;

const Card = styled.div`
  width: 96%!important;
  margin: 0 25px;
  height: 370px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const ContentImage = styled.div`
  height: 190px;
  overflow: hidden;
  display: flex;
  align-items: center;
`;

const ContentText = styled.div`
  h2 {
    font-size: 20px;
    text-align: left;
    font-weight: 400;
  }
  
  h3 {
    font-size: 16px;
    font-weight: 200;
  }
`;

const settings = {
  dots: true,
  infinite: true,
  speed: 500,
  slidesToShow: 4,
  slidesToScroll: 1
};

const List = props => (
  <>
    {props.items && props.items.length !== 0 ? (
      <SliderCustom {...settings}>
        {
          props.items.map((rec, i) => {
            return (
              <Card key={`${i}-${rec.nombre}`}>
                <ContentImage>
                  <img src={`https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${rec.photoReferences}&key=AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg`} width="100%" height="auto" className="card-img-top" alt={rec.nombre} />
                </ContentImage>
                <ContentText className="card-body">
                  <h2>{rec.nombre}</h2>
                  <h3>{rec.direccion}</h3>
                </ContentText>
              </Card>
            )
          })
        }
      </SliderCustom>
    ) : (
      <p>Cargando resultados...</p>
    )}
  </>
);

export default List;
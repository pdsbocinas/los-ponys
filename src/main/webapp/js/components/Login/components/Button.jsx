import React from "react";
import { host } from '../../../host.js';
import styled from 'styled-components'

const Size = styled.span`
  width: 10px;
  height: 10px;
  border-radius: 50px;
  padding: 13px;
  background-color: red;
  color: #fff;
  display: block;
  position: absolute;
  top: 16px;
  left: -13px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

class Button extends React.Component {

  state = {
    active: ''
  }

  onChangeActive = async (module) => {
    await this.setState({
      active: module
    })
    await this.props.onChangeActive(module);
  }

  render() {
    const { cant, id, usuario } = this.props
    return (
      <>
        { usuario.email !== null && (
          <a style={{ position: 'relative' }} onClick={() => this.onChangeActive('review')}>
            <img src={`${host}/images/020-review.svg`} width="32" height="auto" />
            {cant !== 0 && (
              <Size>{cant}</Size>
            )}
          </a>
        )}
        <a onClick={() => this.onChangeActive('forms')}>
          <img src={`${host}/images/029-tourist.svg`} width="32" height="auto" />
        </a>
      </>
    )
  }
}

export default Button;
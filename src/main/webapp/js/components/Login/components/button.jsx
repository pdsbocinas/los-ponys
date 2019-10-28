import React from "react";
import { host } from '../../../host.js';

class Button extends React.Component {

  state = {
    active: false
  }

  onChangeActive = async () => {
    await this.setState({
      active: !this.state.active
    })
    await this.props.onChangeActive();
  }

  render() {
    return (
      <>
        <a onClick={this.onChangeActive}>
          <img src={`${host}/images/029-tourist.svg`} width="32" height="auto" />
        </a>
      </>
    )
  }
}

export default Button;
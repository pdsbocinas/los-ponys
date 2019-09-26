import React from "react";

class Button extends React.Component {

  onSendToParent = async () => {
    await this.props.onSendToParent("soy data desde el hijo")
  }

  render() {
    return (
      <>
        <button onClick={this.onSendToParent}>{this.props.titulo}</button>
      </>
    )
  }
}

export default Button;
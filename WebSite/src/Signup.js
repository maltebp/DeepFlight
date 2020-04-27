import React, { Component } from "react";
import Circle from "./images/CircleTexture2.png";

class Signup extends Component {
  render() {
    return (
      <div>
      <div className="boxwrapper">
        <div className="box"><img src={Circle} width="100%" alt="Circle"/></div>
        <div className="box"><img src={Circle} width="100%" alt="Circle"/></div>
        <div className="box"><img src={Circle} width="100%" alt="Circle"/></div>
        <div className="box"><img src={Circle} width="100%" alt="Circle"/></div>
      </div>

      </div>
    );
  }
}

export default Signup;
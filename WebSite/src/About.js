import React, { Component } from "react";
import Circle from "./images/CircleTexture2.png";
import Smar2 from "./images/smar2.png";

class About extends Component {
  render() {
    return (
      <div>
      <div className="boxwrapper">
        <img src={Smar2} width="100%" alt="deep flight screenshot"/>
      </div>
      <div className="boxwrapper">
        <div className="box, whitebg" style={{width: 100%;}}>
        <h1>Deep Flight</h1>
        <p>A classic cave flier with an infinite number of caves.</p>
      </div>
      </div>

      </div>
    );
  }
}

export default About;
import React, { Component } from "react";
import screenshot from "./images/screenshot.jpg";
import screenshot2 from "./images/screenshot2.png";
import screenshot3 from "./images/screenshot3.png";
import text from "./text/text.json";

class About extends Component {
  render() {
    return (
      <div>
        <div className="boxwrapper longread">
          <div className="box">
            <img src={screenshot} width="100%" alt="deep flight screenshot" className="headerimage" />
            <div className="imageoverlay"><h1 >{text.about.mainheader}</h1>
              <p>{text.about.tagline}</p>
            </div>
            <div className="whitebg">
              <h2>{text.about.gameheader}</h2>
              <p>{text.about.gametext}</p>
              <h2>{text.about.projectheader}</h2>
              <p>{text.about.projecttext}</p>
              <h2>Screenshots</h2>
              <img src={screenshot2} width="100%" alt="deep flight screenshot"/>
              <img src={screenshot3} width="100%" alt="deep flight screenshot"/>
            </div>
            <p>This page is made by gruppe 0, DTU software, Backend Development, may 2020. </p>
          </div>
        </div>
      </div>
    );
  }
}

export default About;
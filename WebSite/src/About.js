import React, { Component } from "react";
import screenshot from "./images/screenshot.jpg";

class About extends Component {
  render() {
    return (
      <div>
        <div className="boxwrapper longread">
          <div className="box">
            <img src={screenshot} width="100%" alt="deep flight screenshot" className="headerimage" />
            <div className="imageoverlay"><h1 >Deep Flight</h1>
              <p>Classic cave flier with an infinite number of caves</p>
            </div>
            <h2>Project</h2>
              <p>This page and the backend is made by <b>gruppe 0, DTU software, Backendudvikling, may 2020</b>. </p>
          </div>
        </div>

      </div>
    );
  }
}

export default About;
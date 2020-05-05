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
            <div className="whitebg">
            <h2>The game</h2>
            <p>The game DeepFlight was created by Malte Pedersen as part of an introductory course in C#, and is inspired by 80's 'cave flier' games. As as player you explore different caves on three planets Aerth, Turnsa and Smar. The planets have different color themes, but also different properties when it comes to the shape and difficulty. Every day, new caves are created, so if you want to keep your overall high score, there's no time to rest.</p>
            <h2>The project</h2>
            <p>This site and the game are part of separate projects at the Technical University of Denmark spring 2020. </p>


            </div>
              <p>This page is made by gruppe 0, DTU software, Backend Development, may 2020. </p>
          </div>
        </div>

      </div>
    );
  }
}

export default About;
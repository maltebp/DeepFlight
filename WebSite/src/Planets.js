import React, { Component } from "react";
import circle from "./images/CircleTexture2.png"

class Planets extends Component {
    constructor(props) {
        super(props)
        this.state = {
            planets: [
                { name: "Unknown" },
                { name: "Unknown" },
                { name: "Unknown" },
                { name: "Unknown" }
            ],
            tracks: [

            ]
        }
    }

    render() {
        return (
            <div className="boxwrapper">
                <div className="box">

                    <img src={circle} alt="planet" className="fullwidth" />
                    <h2>{this.state.planets[0].name}</h2>
                </div>
                <div className="box">
                    <img src={circle} alt="planet" className="fullwidth" />
                    <h2>{this.state.planets[1].name}</h2>
                </div>
                <div className="box">
                    <img src={circle} alt="planet" className="fullwidth" />
                    <h2>{this.state.planets[2].name}</h2>
                </div>
                <div className="box">
                    <img src={circle} alt="planet" className="fullwidth" />
                    <h2>{this.state.planets[3].name}</h2>
                </div>
            </div>
        );
    }
}

export default Planets;

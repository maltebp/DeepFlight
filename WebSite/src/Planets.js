import React, { Component } from "react";
import circle from "./images/CircleTexture2.png"
import smar from "./images/CircleSmar.png"
import aerth from "./images/CircleAerth.png"
import turnsa from "./images/CircleTurnsa.png"
import lupto from "./images/CircleLupto.png"

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

                    <img src={smar} alt="planet" className="fullwidth" />
                    <h2>smar</h2>
                </div>
                <div className="box">
                    <img src={aerth} alt="planet" className="fullwidth" />
                    <h2>aerth</h2>
                </div>
                <div className="box">
                    <img src={turnsa} alt="planet" className="fullwidth" />
                    <h2>turnsa</h2>
                </div>
                <div className="box">
                    <img src={lupto} alt="planet" className="fullwidth" />
                    <h2>lupto</h2>
                </div>
            </div>
        );
    }
}

export default Planets;

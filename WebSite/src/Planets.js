import React, { Component } from "react";
import smar from "./images/CircleSmar.png"
import aerth from "./images/CircleAerth.png"
import turnsa from "./images/CircleTurnsa.png"
import lupto from "./images/CircleLupto.png"

class Planets extends Component {

    render() {
        return (
            <div className="boxwrapper">
                <div className="box">

                    <img src={smar} alt="planet" className="fullwidth" />
                    <p>planet: smar</p>
                    
                   
                </div>
                <div className="box">
                    <img src={aerth} alt="planet" className="fullwidth" />
                    <p>planet: aerth</p>
                   

                </div>
                <div className="box">
                    <img src={turnsa} alt="planet" className="fullwidth" />
                    <p>planet: turnsa</p>
                   
                   
                </div>
                <div className="box">
                    <img src={lupto} alt="planet" className="fullwidth" />
                    <p>planet: lupto</p>
                    
                    
                </div>
            </div>
        );
    }
}

export default Planets;

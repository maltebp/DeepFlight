import React, { Component } from "react";
import smar from "./images/CircleSmar.png"
import aerth from "./images/CircleAerth.png"
import turnsa from "./images/CircleTurnsa.png"
import lupto from "./images/CircleLupto.png"

class Planets extends Component {
    constructor(props) {
        super(props)
        this.state = {
            tracks: [
                { name: "Unknown", times: [{ name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }] },
                { name: "Unknown", times: [{ name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }] },
                { name: "Unknown", times: [{ name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }] },
                { name: "Unknown", times: [{ name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }, { name: "_", time: "" }] }
            ],
        }
    }

    componentDidMount() {
        // Ask planetStatsLogic to handle state and get tracks
        //getPlanetStats(this.setState.bind(this))
    }


    /*
    
     

    */
    render() {
        console.log(this.state.tracks)
        return (
            <div className="boxwrapper">
                <div className="box">

                    <img src={smar} alt="planet" className="fullwidth" />
                    <p>planet: smar</p>
                    <p>cave: {this.state.tracks[0].name}</p>
                    <table><tbody>
                        <tr><td>1</td><td>{this.state.tracks[0].times[0].name}</td><td>{this.state.tracks[0].times[0].time}</td></tr>
                        <tr><td>2</td><td>{this.state.tracks[0].times[1].name}</td><td>{this.state.tracks[0].times[1].time}</td></tr>
                        <tr><td>3</td><td>{this.state.tracks[0].times[2].name}</td><td>{this.state.tracks[0].times[2].time}</td></tr>
                        <tr><td>4</td><td>{this.state.tracks[0].times[3].name}</td><td>{this.state.tracks[0].times[3].time}</td></tr>
                        <tr><td>5</td><td>{this.state.tracks[0].times[4].name}</td><td>{this.state.tracks[0].times[4].time}</td></tr>
                    </tbody></table>
                </div>
                <div className="box">
                    <img src={aerth} alt="planet" className="fullwidth" />
                    <p>planet: aerth</p>
                    <p>cave: {this.state.tracks[1].name}</p>
                    <table><tbody>
                        <tr><td>1</td><td>{this.state.tracks[1].times[0].name}</td><td>{this.state.tracks[1].times[0].time}</td></tr>
                        <tr><td>2</td><td>{this.state.tracks[1].times[1].name}</td><td>{this.state.tracks[1].times[1].time}</td></tr>
                        <tr><td>3</td><td>{this.state.tracks[1].times[2].name}</td><td>{this.state.tracks[1].times[2].time}</td></tr>
                        <tr><td>4</td><td>{this.state.tracks[1].times[3].name}</td><td>{this.state.tracks[1].times[3].time}</td></tr>
                        <tr><td>5</td><td>{this.state.tracks[1].times[4].name}</td><td>{this.state.tracks[1].times[4].time}</td></tr>
                    </tbody>
                    </table>
                </div>
                <div className="box">
                    <img src={turnsa} alt="planet" className="fullwidth" />
                    <p>planet: turnsa</p>
                    <p>cave: {this.state.tracks[2].name}</p>
                    <table><tbody>
                        <tr><td>1</td><td>{this.state.tracks[2].times[0].name}</td><td>{this.state.tracks[2].times[0].time}</td></tr>
                        <tr><td>2</td><td>{this.state.tracks[2].times[1].name}</td><td>{this.state.tracks[2].times[1].time}</td></tr>
                        <tr><td>3</td><td>{this.state.tracks[2].times[2].name}</td><td>{this.state.tracks[2].times[2].time}</td></tr>
                        <tr><td>4</td><td>{this.state.tracks[2].times[3].name}</td><td>{this.state.tracks[2].times[3].time}</td></tr>
                        <tr><td>5</td><td>{this.state.tracks[2].times[4].name}</td><td>{this.state.tracks[2].times[4].time}</td></tr>
                    </tbody>
                    </table>
                </div>
                <div className="box">
                    <img src={lupto} alt="planet" className="fullwidth" />
                    <p>planet: lupto</p>
                    <p>cave: {this.state.tracks[3].name}</p>
                    <table><tbody>
                        <tr><td>1</td><td>{this.state.tracks[3].times[0].name}</td><td>{this.state.tracks[3].times[0].time}</td></tr>
                        <tr><td>2</td><td>{this.state.tracks[3].times[1].name}</td><td>{this.state.tracks[3].times[1].time}</td></tr>
                        <tr><td>3</td><td>{this.state.tracks[3].times[2].name}</td><td>{this.state.tracks[3].times[2].time}</td></tr>
                        <tr><td>4</td><td>{this.state.tracks[3].times[3].name}</td><td>{this.state.tracks[3].times[3].time}</td></tr>
                        <tr><td>5</td><td>{this.state.tracks[3].times[4].name}</td><td>{this.state.tracks[3].times[4].time}</td></tr>
                    </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default Planets;

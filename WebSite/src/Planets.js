import React, { Component } from "react";
import smar from "./images/CircleSmar.png"
import aerth from "./images/CircleAerth.png"
import turnsa from "./images/CircleTurnsa.png"
import lupto from "./images/CircleLupto.png"
import axios from 'axios';
const base_url = "http://maltebp.dk:10000/gameapi/"


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

    getCurrentStats(trackIds, setState, date) {
        let tracks = [];
    
        var timeLeft = (Math.abs(new Date(date)-new Date()) / 60000);
        console.log(timeLeft + " minutes left.")
    
        for (let i in trackIds) {
            const url = base_url + "track/" + trackIds[i];
            //console.log(url);
            axios({
                method: 'get',
                url: url,
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': url,
                    'Accept': 'application/json',
                },
                withCredentials: true
            })
                .then(response => {
                    //console.log(response)
                    let times = []
                    const entries = Object.entries(response.data.times)
                    let objects = [];
                    for (let item in entries) {
                        objects[item] = { "name": entries[item][0], "time": entries[item][1] }
                    }
                    objects.sort((a, b) => a.time - b.time);
    
                    //console.log(objects)
                    for (let i = 0; i < 5; i++) {
                        if (objects[i] === null || objects[i] === undefined) {
                            times[i] = {name: "-", time: " "};           
                        } else {
                            times[i] = objects[i];
                        }
                    }
    
                    tracks[i] = { name: response.data.name, "times": times }
                    // Assert all tracks present before updating state
                    if (tracks.length === 4) {
                        this.setState({ tracks: tracks });
                        //console.log(tracks)
                    }
                    
                })
                .catch(error => {
                    console.log("error downloading track stats", error);
                });
        }
    }

    componentDidMount() {
        const url = base_url + "round/current";
    axios({
        method: 'get',
        url: url,
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': url,
            'Accept': 'application/json',
        },
        withCredentials: true
    })
        .then(response => {
            const trackIds = response.data.trackIds;
            console.log(response.data.endDate)
            this.getCurrentStats(trackIds, setState, response.data.endDate)

        })
        .catch(error => {
            console.log("error downloading current round for planet stats", error);
        });
    }

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

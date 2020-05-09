import React, { Component } from "react";
import axios from 'axios';
//import jwt from 'jsonwebtoken';

class Home extends Component {
  constructor(props) {
    super(props)
    this.state = {
      universal: [
        {score: "Loading high scores...", name: null},
        {score: null, name: null},
        {score: null, name: null},
        {score: null, name: null},
        {score: null, name: null},
      ],
      lastRound: [
              {score: "Loading high scores...", name: null},
              {score: null, name: null},
              {score: null, name: null},
              {score: null, name: null},
              {score: null, name: null},
            ]
    }
  }

  componentDidMount() {
    //const url = "http://maltebp.dk:10000/gameapi/round/current";
    const url = "http://localhost:10000/gameapi/rankings/universal"
    axios({
      method: 'get',
      url: url,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': url,
        'Accept': 'application/json',
        'Authorization': localStorage.getItem("dftoken")
      },
      withCredentials: true
    })
      .then(response => {
        console.log(response);
        // read 5 first uni scores
        const scores = response.data;
        console.log(scores);
        this.setState({
          universal: [scores[0].name, rating[1].name, scores[2].name, scores[3].name],
          lastRound: [scores[0].color, scores[1].color, scores[2].color, scores[3].color]
        });
      })
      .catch(error => {
        console.log("axios download results error", error);
      });
  }


  render() {
    return (
      <div className="boxwrapper">
        <div className="box whitebg">
          <h1>Universal ratings (top 5)</h1>
          <table>
            <tbody>
              <tr><td>{this.state.universal[0].score}</td><td>{this.state.universal[0].name}</td></tr>
              <tr><td>{this.state.universal[1].score}</td><td>{this.state.universal[1].name}</td></tr>
              <tr><td>{this.state.universal[2].score}</td><td>{this.state.universal[2].name}</td></tr>
              <tr><td>{this.state.universal[3].score}</td><td>{this.state.universal[3].name}</td></tr>
            </tbody>
          </table>
            </div>
        <div className="box whitebg">
            <h1 >Last round ratings (top 5)</h1>
            <table>
              <tbody>
                <tr><td>{this.state.lastRound[0].score}</td><td>{this.state.lastRound[0].name}</td></tr>
                <tr><td>{this.state.lastRound[1].score}</td><td>{this.state.lastRound[1].name}</td></tr>
                <tr><td>{this.state.lastRound[2].score}</td><td>{this.state.lastRound[2].name}</td></tr>
                <tr><td>{this.state.lastRound[3].score}</td><td>{this.state.lastRound[3].name}</td></tr>
              </tbody>
            </table>
          </div>
        </div>
    );
  }
}

export default Home;

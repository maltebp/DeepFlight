import React, { Component } from "react";
import axios from 'axios';
//import jwt from 'jsonwebtoken';

class Home extends Component {
  constructor(props) {
    super(props)
    this.state = {
      universal: [
        { rank: "Loading player ranking...", name: null },
        { rank: null, name: null },
        { rank: null, name: null },
        { rank: null, name: null },
        { rank: null, name: null },
      ],
      lastRound: [
        { score: "Loading high scores...", name: null },
        { score: null, name: null },
        { score: null, name: null },
        { score: null, name: null },
        { score: null, name: null },
      ]
    }
  }

  componentDidMount() {
    const BASE_URL = "http://maltebp.dk:10000/gameapi/";
    const rankUrl = BASE_URL +  "rankings/universal";
    const scoreUrl = BASE_URL + "round/previous";
    axios({
      method: 'get',
      url: rankUrl,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': rankUrl,
        'Accept': 'application/json',
        //'Authorization': localStorage.getItem("dftoken")
      },
      withCredentials: true
    })
      .then(response => {
        const ranks = response.data;
        let universal = [];
        // Show null users
        for (var i = 0; i < 5; i++ ){
          if (ranks[i] != null){
            universal.push( { rank: i + 1, name: ranks[i].username });
          } else {
            universal.push( { rank: i + 1, name: "_ _ _"});
          }
        }
        this.setState({"universal": universal});
      })
      .catch(error => {
        console.log("error downloading ranks", error);
      });

    axios({
      method: 'get',
      url: scoreUrl,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': scoreUrl,
        'Accept': 'application/json',
        //'Authorization': localStorage.getItem("dftoken")
      },
      withCredentials: true
    })
      .then(response => {
        console.log(response);
        const scores = response.data.times;
        console.log(scores);
        let lastRound = [];
        // Extract top 5
        /*
        data = [{
    "id": "105",
    "name": "FIAT",
    "active": true,
    "parentId": "1"
}, {
    "id": "106",
    "name": "AUDI",
    "active": true,
    "parentId": "1"
}, {
    "id": "107",
    "name": "BMW",
    "active": true,
    "parentId": "1"
}, {
    "id": "109",
    "name": "RENAULT",
    "active": true,
    "parentId": "1"
}];


data.sort(function(a, b) {
    return a.name > b.name;
});

console.log(data);
        */

        // Data is null
        if (i === 0){
          lastRound.push({"score": "No data from last round."});
        }

        // Show null users
        for (var i = 0; i < 5; i++ ){
          if (scores[i] != null){
            lastRound.push( { "score": "nn", "name": "nn" });
          } else {
            lastRound.push( { score: "_ _ _", name: "_ _ _"});
          }
        }
        console.log(lastRound);
        this.setState({"lastRound": lastRound});
      })
      .catch(error => {
        console.log("error downloading scores", error);
      });
  }

  render() {
    return (
      <div className="boxwrapper">
        <div className="box whitebg">
          <h1>Universal ratings (top 5)</h1>
          <table>
            <tbody>
              <tr><td>{this.state.universal[0].rank}</td><td>{this.state.universal[0].name}</td></tr>
              <tr><td>{this.state.universal[1].rank}</td><td>{this.state.universal[1].name}</td></tr>
              <tr><td>{this.state.universal[2].rank}</td><td>{this.state.universal[2].name}</td></tr>
              <tr><td>{this.state.universal[3].rank}</td><td>{this.state.universal[3].name}</td></tr>
              <tr><td>{this.state.universal[4].rank}</td><td>{this.state.universal[4].name}</td></tr>
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
              <tr><td>{this.state.lastRound[4].score}</td><td>{this.state.lastRound[4].name}</td></tr>
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default Home;

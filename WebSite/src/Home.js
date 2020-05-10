import React, { Component } from "react";
import axios from 'axios';
//import jwt from 'jsonwebtoken';

class Home extends Component {
  constructor(props) {
    super(props)
    this.state = {
      universal: [
        { rank: "Loading player ranking...", name: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
      ],
      lastRound: [
        { rank: "Loading high scores...", name: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
        { rank: null, name: null, rating: null },
      ]
    }
  }

  componentDidMount() {
    const BASE_URL = "http://maltebp.dk:10000/gameapi/";
    const rankUrl = BASE_URL + "rankings/universal";
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
        console.log(ranks)
        let universal = [];
        // Show null users
        for (var i = 0; i < 5; i++) {
          if (ranks[i] != null) {
            universal.push({ rank: i + 1, name: ranks[i].username, rating: parseFloat(ranks[i].rating).toFixed(1) });
          } else {
            universal.push({ rank: i + 1, name: "_ _ _", rating: "_ _ _" });
          }
        }
        universal.sort((a, b) => b.rating - a.rating);
        this.setState({ "universal": universal });
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
        // Sort and list objects
        const rankings = response.data.rankings;
        const entries = Object.entries(rankings)
        let objects = [];
        for (let item in entries) {
          objects[item] = { "name": entries[item][0], "rating": entries[item][1] }
        }
        objects.sort((a, b) => b.rating - a.rating);
       
        let lastRound = [];
        // Show null users
        for (var i = 0; i < 5; i++) {
          if (entries[i] != null) {
            lastRound.push({ rank: i + 1, "name": objects[i].name, "rating": parseFloat(objects[i].rating).toFixed(1) });
          } else if (i === 0) {
            lastRound.push({ rank: "No data from last round." });
          } else {
            lastRound.push({ rank: "_ _ _", name: "_ _ _", rating: "_ _ _" });
          }
        }
        this.setState({ "lastRound": lastRound });
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
              <tr><td>{this.state.universal[0].rank}</td><td>{this.state.universal[0].name}</td><td>{this.state.universal[0].rating}</td></tr>
              <tr><td>{this.state.universal[1].rank}</td><td>{this.state.universal[1].name}</td><td>{this.state.universal[1].rating}</td></tr>
              <tr><td>{this.state.universal[2].rank}</td><td>{this.state.universal[2].name}</td><td>{this.state.universal[2].rating}</td></tr>
              <tr><td>{this.state.universal[3].rank}</td><td>{this.state.universal[3].name}</td><td>{this.state.universal[3].rating}</td></tr>
              <tr><td>{this.state.universal[4].rank}</td><td>{this.state.universal[4].name}</td><td>{this.state.universal[4].rating}</td></tr>
            </tbody>
          </table>
        </div>
        <div className="box whitebg">
          <h1 >Last round ratings (top 5)</h1>
          <table>
            <tbody>
              <tr><td>{this.state.lastRound[0].rank}</td><td>{this.state.lastRound[0].name}</td><td>{this.state.lastRound[0].rating}</td></tr>
              <tr><td>{this.state.lastRound[1].rank}</td><td>{this.state.lastRound[1].name}</td><td>{this.state.lastRound[1].rating}</td></tr>
              <tr><td>{this.state.lastRound[2].rank}</td><td>{this.state.lastRound[2].name}</td><td>{this.state.lastRound[2].rating}</td></tr>
              <tr><td>{this.state.lastRound[3].rank}</td><td>{this.state.lastRound[3].name}</td><td>{this.state.lastRound[3].rating}</td></tr>
              <tr><td>{this.state.lastRound[4].rank}</td><td>{this.state.lastRound[4].name}</td><td>{this.state.lastRound[4].rating}</td></tr>
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default Home;

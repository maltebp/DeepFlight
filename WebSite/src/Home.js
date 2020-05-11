import React, { Component } from "react";
import Planets from "./Planets";
import {getRankFromServer} from "./RankingLogic"

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
    getRankFromServer(this.setState.bind(this));
  }

  render() {
    return (
      <div>
        <div className="boxwrapper">
          <div className="box">
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
          <div className="box">
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
        <div className="boxwrapper">
          <Planets/>
        </div>
      </div>
    );
  }
}

export default Home;

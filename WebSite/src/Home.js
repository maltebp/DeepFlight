import React, { Component } from "react";
import axios from 'axios';
import jwt from 'jsonwebtoken';

class Home extends Component {
  constructor(props) {
    super(props)
    this.state = {
      universal: 'Loading high scores...',
      lastRound: 'Loading high scores...'
    }
  }

  componentDidMount() {
    const url = "http://maltebp.dk:10000/gameapi/planets";
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
        const scores = response.data.planets;
        console.log(scores);
        this.setState({
          universal: scores[0].name + "\n" + scores[1].name  + "\n" + scores[2].name,
          lastRound: scores[0].color + "\n" + scores[1].color  + "\n" + scores[2].color,
        });  
      })
      .catch(error => {
        console.log("axios download results error", error);
      });
  }


  render() {
    return (
      <div>
        <div className="boxwrapper">
          <h1 className="box">Universal ratings (top 5)</h1>
          <h1 className="box">Last round ratings (top 5)</h1>
        </div>
        <div className="boxwrapper">
          <div className="box whitebg">
    <p>{this.state.universal}</p>
          </div>
          <div className="box whitebg">
    <p>{this.state.lastRound}</p>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;

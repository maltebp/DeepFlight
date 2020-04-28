import React, { Component } from "react";
import axios from 'axios';
import jwt from 'jsonwebtoken';

class Home extends Component {
  constructor(props) {
    super(props)
    this.state = {
      universal: null,
      lastRound: null
    }
    axios({

    })
  }
  getHighScores

  render() {
    return (
      <div>
        <div className="boxwrapper">
          <h1 className="box">Universal ratings (top 5)</h1>
          <h1 className="box">Last round ratings (top 5)</h1>
        </div>
        <div className="boxwrapper">
          <div className="box whitebg">
            <p>Loading high scores...</p>
          </div>
          <div className="box whitebg">
            <p>Loading high scores...</p>
          </div>
        </div>
        <div className="boxwrapper">
          <p className="box">Your rank: </p>
          <p className="box">Your rating: </p>
          <p className="box">Your rank: </p>
          <p className="box">Your rating: </p>
        </div>
      </div>
    );
  }
}

function getHighScores() {
  axios({
    method: 'post',
    url: 'http://maltebp.dk:10000/getHighScores',
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'http://maltebp.dk:10000/getHighScores',
      'Accept': 'application/json',
      'Authorization': localStorage.getItem("dftoken")
    },
    withCredentials: true
  })
    .then(response => {
      
      //this.handleState(res);
    })
    .catch(error => {
      console.log("axios download results error", error);
    });
  //event.preventDefault();
}

export default Home;

import React, { Component } from "react";
import {
  Route,
  NavLink,
  HashRouter
} from "react-router-dom";
import Home from "./Home";
import Download from "./Download";
import About from "./About";

class Main extends Component {
  render() {
    return (
      <HashRouter>
        <div>
          <h1 className="header">DEEP FLIGHT</h1>
          <ul className="header">
            <li><NavLink exact to="/">High Scores</NavLink></li>
            <li><NavLink to="/download">Download</NavLink></li>
            <li><NavLink to="/about">About the game</NavLink></li>
          </ul>
          <div className="content">
          <Route exact path="/" component={Home}/>
         <Route path="/download" component={Download}/>
         <Route path="/about" component={About}/>
          </div>
        </div>
        </HashRouter>
    );
  }
}

export default Main;

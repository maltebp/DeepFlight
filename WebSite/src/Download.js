import React, { Component } from "react";
import Login from './Login';
import jwt from 'jsonwebtoken';
import axios from 'axios';
import text from './text/text.json';
import DownloadMenu from './DownloadMenu';

class Download extends Component {
  constructor(props) {
    super(props);
    this.state = {
      token: localStorage.getItem("dftoken"),
    }
  }

  handleState() {
    this.setState({
      token: localStorage.getItem("dftoken"),
    })
  }

  render() {
    return (
      <div>
        <div className="boxwrapper longread">
          <FilterDownload token={this.state.token} handleState={this.handleState.bind(this)} />
          <div className="box whitebg">
            <h2>{text.installation.header}</h2>
            <h3>{text.installation.winheader}</h3>
            <p>{text.installation.wintext}</p>
            <h3>{text.installation.macheader}</h3>
            <p>{text.installation.mactext}</p>
            <h3>{text.installation.linheader}</h3>
            <p>{text.installation.lintext}</p>
          </div>
        </div>
      </div>
    );
  }
}

// Show the login menu of the user has no token
// Show download text box if logged in
function FilterDownload(props) {
  const token = props.token;
  const today = new Date();
  //console.log("Token in FilterDownload: " + JSON.stringify(token));
  if (token === null
    || token === undefined) {
    return (<Login handleState={props.handleState} message={text.login.standard} />); // Pass method to handle state as props
  } else if (today > new Date(jwt.decode(token).exp * 1000)) { // jwt deals in seconds, so * 1000
    return (<Login handleState={props.handleState} message={text.login.loggedout} />); // Pass method to handle state as props
  } else {
    return (<DownloadMenu />)
  }
}

export default Download;
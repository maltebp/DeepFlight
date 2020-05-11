import React, { Component } from "react";
import Login from './Login';
import jwt from 'jsonwebtoken';
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
    let installheader = text.installation.winheader;
    let installtext = text.installation.wintext;

    if (window.navigator.platform.substr(0, 5) === 'Linux') {
      installheader = text.installation.linheader;
      installtext = text.installation.lintext;

    } else if (window.navigator.platform.substr(0, 3) === 'Mac') {
      installheader = text.installation.macheader;
      installtext = text.installation.mactext;

    } else if (window.navigator.platform.substr(0, 3) !== 'Win') { // If not windows
      installheader = "The program does not run on your platform."
      installtext = "DeepFlight is a C# program, and is not tested on mobile devices and systems other than Windows 10 and Ubuntu 18."
    }
    return (
      <div>
        <div className="boxwrapper longread">
          <FilterDownload token={this.state.token} handleState={this.handleState.bind(this)} />
          <div className="box whitebg">
            <h2>{installheader}</h2>
            <p>{installtext}</p>
            <p className="footNote">{window.navigator.platform}</p>
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
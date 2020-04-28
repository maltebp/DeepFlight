import React, { Component } from "react";
import Login from './Login';

class Download extends Component {
  constructor(props){
    super(props);
    this.state = {
      token: localStorage.getItem("dftoken"),
    }
  }

  handleState(){
    this.setState({
      token: localStorage.getItem("dftoken"),
    })
    console.log("Handlin' state");
    console.log("Token in Parent: " + JSON.stringify(this.state.token))
  }

  render() {
    return (
      <div>
        <div className="boxwrapper">
          <FilterDownload token={this.state.token} handleState={this.handleState.bind(this)}/>
          <div className="box whitebg">
            <h2>Installation</h2>
            <h3>Windows</h3>
            <p>Instructions</p>
            <h3>Mac</h3>
            <p>Instructions</p>
            <h3>Linux</h3>
            <p>Instructions</p>
          </div>
        </div>
      </div>
    );
  }
}

// Show the login menu of the user has no token
// Show download text box if logged in
function FilterDownload(props) {
  console.log("Token in FilterDownload: " + JSON.stringify(props.token));
  if (props.token == 'undefined' || props.token == null) {
    return (<Login handleState={props.handleState}/>);
  } else {
    // Try to login automatically first // TODO: Show 'token expired' if old token
    return (<DownloadBox />)
  }
}

class DownloadBox extends React.Component {
  DownloadGame() {
    console.log("Starting download")
  }
  render() {
    return (
      <div className="box">
        <h2>Hello, user!</h2>
        <p>You are logged in and can download the game from here.</p>
        <p>Just a dummy file, no need to install it :-)</p>
        <a className="buttonLink" href="http://mirrors.dotsrc.org/linuxmint-cd/stable/19.3/linuxmint-19.3-cinnamon-64bit.iso">Download Deep Flight</a>
        <p>Size: 8 MB</p>
      </div>
    );
  }
}

export default Download;
import React, { Component } from "react";
import Login from './Login';
import jwt from 'jsonwebtoken';
import axios from 'axios';

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
    //console.log("Handlin' state");
    //console.log("Token in Parent: " + JSON.stringify(this.state.token))
  }

  render() {
    return (
      <div>
        <div className="boxwrapper longread">
          <FilterDownload token={this.state.token} handleState={this.handleState.bind(this)} />
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
  const token = props.token;
  const today = new Date();
  //console.log("Token in FilterDownload: " + JSON.stringify(token));
  if (token === null
    || token === undefined) {
    return (<Login handleState={props.handleState} message="Please log in to download game"/>); // Pass method to handle state as props
  } else if (today > new Date(jwt.decode(token).exp * 1000)){ // jwt deals in seconds, so * 1000
    return (<Login handleState={props.handleState} message="Session expired. Please log in again"/>); // Pass method to handle state as props
  } else {
    return (<DownloadBox />)
  }
}

class DownloadBox extends React.Component {

constructor(props) {
    super(props);
    this.state = {isToggleOn: true};
    this.handleDownload = this.handleDownload.bind(this);  }

downloadGame(){
        // source: https://medium.com/@drevets/you-cant-prompt-a-file-download-with-the-content-disposition-header-using-axios-xhr-sorry-56577aa706d6
        const url = "http://localhost:10000/gameapi/downloadgame";
        axios({
              method: 'get',
              url: url,
              headers: {
                'Content-Type': 'application/json',
                'responseType': 'blob',
                'Access-Control-Allow-Origin': url,
                'Accept': 'application',
                'Authorization': 'Bearer ' + localStorage.getItem("dftoken")
              },
              withCredentials: true
            })
              .then(response => {
                const url = window.URL.createObjectURL(new Blob([response.data]));
                  const link = document.createElement('a');
                  link.href = url;
                  link.setAttribute('download', 'DeepFlight.zip');
                  document.body.appendChild(link);
                  link.click();
              })
              .catch(error => {
                console.log("axios download results error", error);
              });
    }

    handleDownload() {
        this.setState(state => ({ isToggleOn: false }));
        //alert("Starting download");
        this.downloadGame();
        this.setState(state => ({ isToggleOn: true }));
    }

  render() {
    return (
      <div className="box">
        <h2>Hello, user!</h2>
        <p>You are logged in and can download the game from here.</p>
        <p>Just a dummy file, no need to install it :-)</p>
        <button type="button" disabled={!this.state.isToggleOn} onClick={this.handleDownload}>{this.state.isToggleOn ? 'Download' : 'Please wait...'}</button>
        <a className="buttonLink" href="http://mirrors.dotsrc.org/linuxmint-cd/stable/19.3/linuxmint-19.3-cinnamon-64bit.iso">Link as button</a>
        <p>Size: 8 MB</p>
      </div>
    );
  }
}

export default Download;
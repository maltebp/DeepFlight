import React, { Component } from "react";
import Login from './Login';
import jwt from 'jsonwebtoken';
import axios from 'axios';
import text from './text/text.json';

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
    return (<Login handleState={props.handleState} message={text.login.standard}/>); // Pass method to handle state as props
  } else if (today > new Date(jwt.decode(token).exp * 1000)){ // jwt deals in seconds, so * 1000
    return (<Login handleState={props.handleState} message={text.login.loggedout}/>); // Pass method to handle state as props
  } else {
    return (<DownloadBox />)
  }
}

class DownloadBox extends React.Component {

constructor(props) {
    super(props);
    this.state = {
    isToggleOn: true,
 message: "",
 user: "pilot"
    };
    this.handleDownload = this.handleDownload.bind(this);  }

downloadGame(){
        // source: https://medium.com/@drevets/you-cant-prompt-a-file-download-with-the-content-disposition-header-using-axios-xhr-sorry-56577aa706d6
        //const url = "http://localhost:10000/gameapi/downloadgame";
        const url = "htto://maltebp.dk:10000:10000/gameapi/downloadgame";
        axios({
              method: 'get',
              url: url,
              responseType: 'arraybuffer',
              headers: {
                'Content-Type': 'application/zip',
                'Access-Control-Allow-Origin': url,
                'Accept': 'json/application',
                'Authorization': 'Bearer ' + localStorage.getItem("dftoken")
              },
              withCredentials: true
            })
              .then(response => {
              console.log(response)
                const url = window.URL.createObjectURL(new Blob([response.data], {type: "octet/stream"}));
                  const link = document.createElement('a');
                  link.href = url;
                  link.setAttribute('download', 'DeepFlight.zip');
                  document.body.appendChild(link);
                  link.click();
              })
              .catch(error => {
                console.log("axios download results error", error);
                //TODO: Grade errors based on server reply
                this.setState(state => ({message: "Download failed."}));
                setTimeout(() => {  this.setState(state => ({ message: "" })); }, 4000);
              });
    }

    handleDownload() {
        this.setState(state => ({ isToggleOn: false }));
        this.setState(state => ({message: "Starting download."}));
        this.downloadGame();
        setTimeout(() => {  this.setState(state => ({ isToggleOn: true })); }, 1000);

    }

  render() {
    return (
      <div className="box">
        <h2>Hello, {this.state.user}!</h2>
        <p>You are logged in and can download the game from here.</p>
        <button type="button" disabled={!this.state.isToggleOn} onClick={this.handleDownload}>{this.state.isToggleOn ? 'Download' : 'Please wait...'}</button>
        <p className="alert">{this.state.message}</p>
        <p>Size: 8 MB</p>
      </div>
    );
  }
}

export default Download;
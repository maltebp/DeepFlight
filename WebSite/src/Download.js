import React, { Component } from "react";
import axios from 'axios';
import jwt from 'jsonwebtoken';

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
        <h2>DOWNLOAD THE GAME!</h2>
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
      <div className="box whitebg">
        <p>You are logged in and can download the link from here.</p>
        <button onClick="DownloadGame">DL GME</button>
      </div>
    );
  }
}

// Only show login form if user is not logged in
class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      password: "",
      loginErrors: ""
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleState = this.props.handleState;
  }

  handleChange(event) {
    this.setState({
      [event.target.name]: event.target.value
    });
  }

  handleSubmit(event) {
    const { username, password } = this.state;

    var bodyFormData = new FormData();
    bodyFormData.append('name', username);
    bodyFormData.append('password', password);

    axios({
      method: 'post',
      url: 'http://maltebp.dk:7000/login',
      //url: 'http://localhost:7000/login',
      data: bodyFormData,
      headers: {
        'Content-Type': 'multipart/form-data',
        'Access-Control-Allow-Origin': 'http://maltebp.dk:7000/login'
        //'Access-Control-Allow-Origin': 'http://localhost:7000'
      },
      withCredentials: true
    })

      .then(response => {
        // TODO remove these alerts and logs!
        //console.log(response);
        const token = response.data;
        var decoded = jwt.decode(token.jwt);
        //console.log(decoded);
        var result = JSON.stringify(response.statusText) + "\nJWT:\n" + JSON.stringify(decoded).replace(/\\/g, "");
        alert(result);
        //if (response.data.logged_in) {
        //this.props.handleSuccessfulAuth(response.data);
        //}
        localStorage.setItem("dftoken", token);
        console.log("Token in child " + token);
        console.log("Read from localStorage" + localStorage.getItem("dftoken"));
        this.handleState();
      })
      .catch(error => {
        console.log("login error", error);
      });
    event.preventDefault();
  }

  render() {
    return (
      <div className="box">
        <form onSubmit={this.handleSubmit}>
          <input
            type="text"
            name="username"
            placeholder="Username"
            defaultValue={this.state.username}
            onChange={this.handleChange}
            required
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={this.state.password}
            onChange={this.handleChange}
            required
          />
          <button type="Login">Login</button>
        </form>
      </div>
    );
  }
}

export default Download;

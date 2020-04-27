import React, { Component } from "react";
import axios from 'axios';
import jwt from 'jsonwebtoken';

class Download extends Component {
  render() {
    return (
      <div>
        <h2>LOGIN TO DOWNLOAD THE GAME!</h2>
        <div className="boxwrapper">
        <div className="box">
        <Login/>
        </div>
        </div>
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
      withCredentials: true})

      .then(response => {
        // TODO remove these alerts and logs!
        console.log(response);
        const token = response.data;
        var decoded = jwt.decode(token.jwt);
        console.log(decoded);
        var result = JSON.stringify(response.statusText) + "\nJWT:\n" + JSON.stringify(decoded).replace(/\\/g, "");
        alert(result);
        //if (response.data.logged_in) {
          //this.props.handleSuccessfulAuth(response.data);
        //}
        localStorage.setItem("dftoken", response.result);
      })
      .catch(error => {
        console.log("login error", error);
      });
    event.preventDefault();
  }

  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <input
            type="text"
            name="username"
            placeholder="Username"
            value={this.state.username}
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

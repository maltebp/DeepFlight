import React, { Component } from "react";
import axios from 'axios';

class Download extends Component {
  render() {
    return (
      <div>
        <h2>DOWNLOAD THE GAME!</h2>
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
      data: bodyFormData,
      headers: {
        'Content-Type': 'multipart/form-data',
        'Access-Control-Allow-Origin': 'http://maltebp.dk:7000/login'
      },
      withCredentials: true})

      .then(response => {
        console.log(response);
        var result = JSON.stringify(response.statusText);
        alert(result);
        //if (response.data.logged_in) {
          //this.props.handleSuccessfulAuth(response.data);
        //}
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

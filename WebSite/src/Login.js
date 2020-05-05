import React, { Component } from "react";
import axios from 'axios';
import jwt from 'jsonwebtoken';

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            password: "",
            loginErrors: "",
            toggled: false,
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleParentState = this.props.handleState; // Updates state in parent
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit(event) {
        this.setState(state => ({ toggled: true }));
        const { username, password } = this.state;
        var bodyFormData = new FormData();
        bodyFormData.append('name', username);
        bodyFormData.append('password', password);

        axios({
            method: 'post',
            //url: 'http://maltebp.dk:7000/login',
            url: 'http://localhost:7000/login',
            data: bodyFormData,
            headers: {
                'Content-Type': 'multipart/form-data',
                //'Access-Control-Allow-Origin': 'http://maltebp.dk:7000/login'
                'Access-Control-Allow-Origin': 'http://localhost:7000/login'
            },
            withCredentials: true
        })

            .then(response => {
                const token = response.data.jwt;
                var decoded = jwt.decode(token);
                console.log(decoded);
                //alert(jwt.decode(token.jwt));
                //var result = JSON.stringify(response.statusText) + "\nJWT:\n" + JSON.stringify(decoded).replace(/\\/g, "");
                //if (response.data.logged_in) {
                //this.props.handleSuccessfulAuth(response.data);
                //}
                localStorage.setItem("dftoken", token);
                //console.log("Token in child " + token);
                console.log("Read from localStorage: \n" + localStorage.getItem("dftoken"));
                this.handleParentState();
                setTimeout(() => {  this.setState(state => ({ toggled: false })); }, 1000);
            })
            .catch(error => {
                console.log("axios login error", error);
                alert("Login failed.")
                setTimeout(() => {  this.setState(state => ({ toggled: false })); }, 200);
            });

        event.preventDefault();
    }

    render() {
        return (
            <div className="box">
                <h2>Login</h2>
                <p>{this.props.message}</p>
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

                    <button ref="btn" disabled={this.state.toggled} type="Login">Login</button>
                </form>
            </div>
        );
    }
}

export default Login;

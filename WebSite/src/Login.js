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
        this.refs.btn.setAttribute("disabled", "disabled");
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
                this.refs.btn.removeAttribute("disabled");
            })
            .catch(error => {
                console.log("axios login error", error);
                if (this.refs.btn != null || this.refs.btn != undefined) {
                    this.refs.btn.removeAttribute("disabled");
                }
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

                    <button ref="btn" type="Login">Login</button>
                </form>
            </div>
        );
    }
}

export default Login;
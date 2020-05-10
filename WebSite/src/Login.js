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
            message: ""
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
        this.setState(state => ({
            toggled: true,
            message: ""
        }));
        const { username, password } = this.state;
        var bodyFormData = new FormData();
        bodyFormData.append('name', username);
        bodyFormData.append('password', password);
        const url = 'http://maltebp.dk:7000/login'
        //url: 'http://localhost:7000/login',

        axios({
            method: 'post',
            url: url,
            data: bodyFormData,
            headers: {
                'Content-Type': 'multipart/form-data',
                'Access-Control-Allow-Origin': url
            },
            withCredentials: true
        })

            .then(response => {
                const token = response.data.jwt;
                var decoded = jwt.decode(token);
                //console.log(decoded);
                localStorage.setItem("dftoken", token);
                //console.log("Read from localStorage: \n" + localStorage.getItem("dftoken"));
                this.handleParentState();
                setTimeout(() => { this.setState(state => ({ toggled: false })); }, 2000);
            })
            .catch(error => {
                console.log("axios login error", error);
                //TODO: Grade errors based on server reply

                this.setState(state => ({ toggled: false, message: "Login failed" }));
                setTimeout(() => { this.setState(state => ({ toggled: false, message: "" })); }, 3000);
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
                <p className="alert">{this.state.message}</p>
            </div>
        );
    }
}

export default Login;

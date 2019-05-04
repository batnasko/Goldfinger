import React, {Component} from 'react';
import {Button, Form, Alert} from "react-bootstrap";
import axios from "axios";
import date from "../common/date"

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showAlert: false,
            invalidLogin: false
        }
    }

    login(e) {
        this.setState({
            invalidLogin: false
        });
        e.preventDefault();
        let data = {
            "username": e.currentTarget.email.value,
            "password": e.currentTarget.password.value
        };
        let ip = this.props.user.ip;
        axios.post("http://localhost:8080/token", data).then(response => {
            axios.post("http://localhost:8000/auditability",{
                "username": data.username,
                "ip" : ip,
                "date": date(),
                "msg": "Logged in"
            });
            this.props.setToken(response.data);
            this.props.showMainPage();
        }, error => {
            axios.post("http://localhost:8000/auditability",{
                "username": data.username,
                "ip" : ip,
                "date": date(),
                "msg": "Tried to log in"
            });
            this.setState({
                invalidLogin: true
            });
        });
    }

    render() {
        return (
            <Form onSubmit={e => this.login(e)}
                    onChange={e =>{
                        this.setState({
                            invalidLogin:false
                        })
                    }}>
                {this.state.showAlert &&
                <Alert variant="danger">
                    Incorrect username or password!
                </Alert>
                }
                <Form.Group controlId="email">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control placeholder="Enter email" isInvalid={this.state.invalidLogin} required/> {/* type = "email" !!*/}
                        <Form.Control.Feedback type="invalid">Invalid username or password</Form.Control.Feedback>
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" isInvalid={this.state.invalidLogin} required/>
                </Form.Group>
                <Form.Text style={{marginBottom: 10, cursor: "pointer"}} className="text-muted"
                           onClick={this.props.showRegister}>
                    Not a member?
                </Form.Text>
                <Button variant="danger"
                        type="submit">
                    Login
                </Button>
            </Form>
        );
    }
}

export default Login;

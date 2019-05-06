import React, {Component} from 'react';
import {Button, Form, Row, Col, Alert} from "react-bootstrap";
import axios from "axios";
import date from "../common/date";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstName: {
                valid: false,
                invalid: false
            },
            lastName: {
                valid: false,
                invalid: false
            },
            email: {
                invalidMsg: "",
                valid: false,
                invalid: false
            },
            password: {
                value: "",
                valid: false,
                invalid: false
            },
            confirmPassword: {
                value: "",
                valid: false,
                invalid: false
            }
        }
    }

    register(e) {
        e.preventDefault();

        if (!this.state.firstName.valid) return;
        if (!this.state.lastName.valid) return;
        if (!this.state.email.valid) return;
        if (!this.state.password.valid) return;
        if (!this.state.confirmPassword.valid) return;
        let data = {
            "username": e.currentTarget.email.value.trim(),
            "password": e.currentTarget.password.value.trim(),
            "firstName": e.currentTarget.firstName.value.trim(),
            "lastName": e.currentTarget.lastName.value.trim()
        };
        let ip = this.props.user.ip;
        axios.post("http://localhost:8080", data).then(response => {
            axios.post("http://localhost:8000/auditability",{
                "username": data.username,
                "ip" : ip,
                "time": date(),
                "message": "Registered"
            });
            this.props.showLogin();
        }, error => {
            axios.post("http://localhost:8000/auditability",{
                "username": data.username,
                "ip" : ip,
                "date": date(),
                "msg": "Tried to register"
            });
            this.setState({
                email: {
                    invalidMsg: "Email is already used",
                    valid: false,
                    invalid: true
                }
            })
        })
    }

    handleFirstNameChange(e) {
        if (e.target.value.trim().length >= 1) {
            this.setState({
                firstName: {
                    valid: true,
                    invalid: false
                }
            })
        } else {
            this.setState({
                firstName: {
                    valid: false,
                    invalid: true
                }
            })
        }
    }

    handleLastNameChange(e) {
        if (e.target.value.trim().length >= 1) {
            this.setState({
                lastName: {
                    valid: true,
                    invalid: false
                }
            })
        } else {
            this.setState({
                lastName: {
                    valid: false,
                    invalid: true
                }
            })
        }
    }

    handleEmailChange(e) {
        if (e.target.value.trim().length > 4) {
            this.setState({
                email: {
                    invalidMsg: "Please enter valid email",
                    valid: true,
                    invalid: false
                }
            })
        } else {
            this.setState({
                email: {
                    invalidMsg: "Please enter valid email",
                    valid: false,
                    invalid: true
                }
            })
        }
    }

    handlePasswordChange() {
        if (this.state.password.value.trim().length > 4) {
            this.setState({
                password: {
                    value: this.state.password.value,
                    valid: true,
                    invalid: false
                }
            })
        } else {
            this.setState({
                password: {
                    value: this.state.password.value,
                    valid: false,
                    invalid: true
                }
            })
        }
        this.handleConfirmPasswordChange();
    }

    handleConfirmPasswordChange() {
        if (this.state.confirmPassword.value.trim().length > 4 && this.state.confirmPassword.value === this.state.password.value) {
            this.setState({
                confirmPassword: {
                    value: this.state.confirmPassword.value,
                    valid: true,
                    invalid: false
                }
            })
        } else {
            this.setState({
                confirmPassword: {
                    value: this.state.confirmPassword.value,
                    valid: false,
                    invalid: true
                }
            })
        }
    }

    render() {
        return (
            <Form
                onSubmit={e => this.register(e)
                }
            >

                <Row>
                    <Form.Group as={Col} md="6" controlId="firstName" onChange={e => this.handleFirstNameChange(e)}>
                        <Form.Label>First name</Form.Label>
                        <Form.Control type="input" placeholder="First name" required
                                      isValid={this.state.firstName.valid} isInvalid={this.state.firstName.invalid}/>
                        <Form.Control.Feedback type="invalid">First name is required</Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="lastName" onChange={e => this.handleLastNameChange(e)}>
                        <Form.Label>Last name</Form.Label>
                        <Form.Control type="input" placeholder="Last name" required isValid={this.state.lastName.valid}
                                      isInvalid={this.state.lastName.invalid}/>
                        <Form.Control.Feedback type="invalid">Last name is required</Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Form.Group controlId="email" onChange={e => this.handleEmailChange(e)}>
                    <Form.Label>Email address</Form.Label>
                    <Form.Control placeholder="Enter email" required isValid={this.state.email.valid}
                                  isInvalid={this.state.email.invalid}/>
                    <Form.Control.Feedback type="invalid">{this.state.email.invalidMsg}</Form.Control.Feedback>
                </Form.Group>
                <Form.Group controlId="password" onChange={e => {
                    this.setState({
                        password: {
                            value: e.target.value,
                            valid: this.state.password.valid,
                            invalid: this.state.password.invalid
                        },
                    }, this.handlePasswordChange);
                }}>
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required isValid={this.state.password.valid}
                                  isInvalid={this.state.password.invalid}/>
                    <Form.Control.Feedback type="invalid">Password must be 4 symbols min</Form.Control.Feedback>
                </Form.Group>


                <Form.Group controlId="repeatPassword" onChange={e => {
                    this.setState({
                        confirmPassword: {
                            value: e.target.value,
                            valid: this.state.confirmPassword.valid,
                            invalid: this.state.confirmPassword.invalid
                        },
                    }, this.handleConfirmPasswordChange);
                }}>
                    <Form.Label>Repeat password</Form.Label>
                    <Form.Control type="password" placeholder="Repeat password" required
                                  isValid={this.state.confirmPassword.valid}
                                  isInvalid={this.state.confirmPassword.invalid}/>
                    <Form.Control.Feedback type="invalid">Passwords doesn't match</Form.Control.Feedback>
                </Form.Group>
                <Row style={{paddingLeft: 15, paddingRight: 15}}>
                    <Button variant="danger"
                            type="submit">
                        Register
                    </Button>
                    <Form.Text style={{cursor: "pointer", marginLeft: "auto", marginTop: 8}} className="text-muted"
                               onClick={this.props.showLogin}>
                        Back
                    </Form.Text>
                </Row>
            </Form>
        );
    }
}

export default Register;

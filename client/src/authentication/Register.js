import React, {Component} from 'react';
import {Button, Form, Row, Col, Alert} from "react-bootstrap";
import axios from "axios";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showAlert: false
        }
    }

    register(e) {
        e.preventDefault();
        let data = {
            "username": e.currentTarget.email.value,
            "password": e.currentTarget.password.value,
            "firstName": e.currentTarget.firstName.value,
            "lastName": e.currentTarget.lastName.value
        }
        axios.post("http://localhost:8080", data).then(response => {
            this.props.showLogin();
        }, error => {
            this.setState({
                showAlert: true
            });
            setTimeout(() => {
                this.setState({
                    showAlert: false
                });
            }, 2000);
        })
    }

    render() {
        return (
            <Form
                onSubmit={e => this.register(e)}>
                {this.state.showAlert &&
                    <Alert variant="danger">
                        Email is already taken!
                    </Alert>
                }
                <Row>
                    <Form.Group as={Col} md="6" controlId="firstName">
                        <Form.Label>First name</Form.Label>
                        <Form.Control type="input" placeholder="First name" required/>
                    </Form.Group>
                    <Form.Group controlId="lastName">
                        <Form.Label>Last name</Form.Label>
                        <Form.Control type="input" placeholder="Last name" required/>
                    </Form.Group>
                </Row>
                <Form.Group controlId="email">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control placeholder="Enter email" required/>
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required/>
                </Form.Group>
                <Form.Group controlId="repeatPassword">
                    <Form.Label>Repeat password</Form.Label>
                    <Form.Control type="password" placeholder="Repeat password" required/>
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

import React, {Component} from 'react';
import {Button, Form, Row, Col} from "react-bootstrap";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <Form>
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
                    <Form.Control type="email" placeholder="Enter email" required/>
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required/>
                </Form.Group>
                <Form.Group controlId="repeatPassword">
                    <Form.Label>Repeat password</Form.Label>
                    <Form.Control type="password" placeholder="Repeat password" required/>
                </Form.Group>
                <Row style={{paddingLeft:15, paddingRight:15}}>
                    <Button variant="danger"
                            onClick={this.props.showLogin}> {/*type="submit" to submit form ?? when implement security */}
                        Register
                    </Button>
                    <Form.Text style={{cursor: "pointer", marginLeft:"auto", marginTop:8}} className="text-muted"
                               onClick={this.props.showLogin}>
                        Back
                    </Form.Text>
                </Row>
            </Form>
        );
    }
}

export default Register;
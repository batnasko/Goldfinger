import React, {Component} from 'react';
import {Button, Form} from "react-bootstrap";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control type="email" placeholder="Enter email" required/>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" required/>
                </Form.Group>
                <Form.Text style={{marginBottom:10, cursor:"pointer"}} className="text-muted" onClick={this.props.showRegister}>
                    Not a member?
                </Form.Text>
                <Button variant="danger"
                        onClick={this.props.showMainPage}> {/*ON LOGIN CLICK GO TO MAP, button type = submit??? when security is implemented*/}
                    Login
                </Button>
            </Form>
        );
    }
}

export default Login;

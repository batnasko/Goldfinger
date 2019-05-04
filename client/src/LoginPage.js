import React, {Component} from 'react';
import './LoginPage.css';
import Login from "./authentication/Login.js"
import Register from "./authentication/Register";

class LoginPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "login"
        }
    }

    showContent() {
        if (this.state.show === "login") return <Login showRegister={this.showRegister} user={this.props.user} setToken={this.props.setToken} showMainPage={this.props.showMainPage}/>;
        if (this.state.show === "register") return <Register showLogin={this.showLogin} user={this.props.user}/>;
    }

    showRegister = () => {
        this.setState({
            show: "register"
        })
    };

    showLogin = () => {
        this.setState({
            show: "login"
        })
    };


    render() {
        return (
            <div>
                <div className="background">

                </div>
                <div className="form">
                    {this.showContent()}
                </div>
            </div>
        );
    }
}

export default LoginPage;

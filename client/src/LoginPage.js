import React, {Component} from 'react';
import './LoginPage.css';
import Login from "./authentication/Login.js"
import Register from "./authentication/Register";

class LoginPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "register"
        }
    }

    showContent(){
        if (this.state.show === "login") return <Login/>
        if (this.state.show === "register") return <Register/>
    }

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

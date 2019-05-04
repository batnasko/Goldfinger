import React, {Component} from 'react';
import './App.css';
import MainPage from "./MainPage";
import LoginPage from "./LoginPage";
import axios from "axios";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "loginPage",
            user: {
                token: null,
                userDetails: {
                    username: null,
                    firstName: null,
                    lastName: null,
                    roles: null
                },
                ip: null
            }
        }
    }

    componentDidMount() {
        axios.get("https://api.ipify.org?format=json").then(response =>{
            this.setState({
                user: {
                    token: this.state.user.token,
                    userDetails: {
                        username: this.state.user.userDetails.username,
                        firstName: this.state.user.userDetails.firstName,
                        lastName: this.state.user.userDetails.lastName,
                        roles: this.state.user.userDetails.roles
                    },
                    ip: response.data.ip
                }
            })
        })

    }

    showMainPage = () => {
        this.setState({
            show: "mainPage"
        })
    };

    showLoginPage = () => {
        this.setState({
            show: "loginPage"
        })
    };

    parseJwt(token) {
        let base64Url = token.split('.')[1];
        let base64 = decodeURIComponent(atob(base64Url).split('').map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(base64);
    };

    setToken = (jwt) => {
        let decodedJwt = this.parseJwt(jwt);
        this.setState({
            user: {
                token: jwt,
                userDetails: decodedJwt,
                ip: this.state.user.ip
            }
        })
    };

    showContent() {
        if (this.state.show === "mainPage") return <MainPage showLoginPage={this.showLoginPage} user={this.state.user}/>;
        if (this.state.show === "loginPage") return <LoginPage showMainPage={this.showMainPage}
                                                               setToken={this.setToken}
                                                                user={this.state.user}/>;
    }

    render() {
        return (
            <div className="App">
                {this.showContent()}
            </div>
        );
    }
}

export default App;

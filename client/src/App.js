import React, {Component} from 'react';
import './App.css';
import MainPage from "./MainPage";
import LoginPage from "./LoginPage";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "loginPage",
            token : null,
            user:{
                username:null,
                firstName:null,
                lastName:null,
                roles:null
            }
        }
    }

    showMainPage= () =>{
        this.setState({
            show: "mainPage"
        })
    };

    showLoginPage= () =>{
        this.setState({
            show: "loginPage"
        })
    };
    parseJwt (token) {
        let base64Url = token.split('.')[1];
        let base64 = decodeURIComponent(atob(base64Url).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(base64);
    };
    setToken = (jwt) =>{
        let decodedJwt = this.parseJwt(jwt);
        this.setState({
            token:jwt,
            user:decodedJwt
        })
    };

    showContent() {
        if (this.state.show === "mainPage") return <MainPage showLoginPage={this.showLoginPage} token={this.state.token} user={this.state.user}/>;
        if (this.state.show === "loginPage") return <LoginPage showMainPage ={this.showMainPage} setToken={this.setToken}/>;
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

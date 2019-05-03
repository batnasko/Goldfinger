import React, {Component} from 'react';
import './App.css';
import MainPage from "./MainPage";
import LoginPage from "./LoginPage";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "loginPage",
            token : null
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

    setToken = (jwt) =>{
        this.setState({
            token:jwt
        })
    };

    showContent() {
        if (this.state.show === "mainPage") return <MainPage showLoginPage={this.showLoginPage}/>;
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

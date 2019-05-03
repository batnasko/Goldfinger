import React, {Component} from 'react';
import './App.css';
import MainPage from "./MainPage";
import LoginPage from "./LoginPage";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "loginPage"
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

    showContent() {
        if (this.state.show === "mainPage") return <MainPage showLoginPage={this.showLoginPage}/>;
        if (this.state.show === "loginPage") return <LoginPage showMainPage ={this.showMainPage}/>;
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

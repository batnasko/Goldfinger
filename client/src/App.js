import React, {Component} from 'react';
import './App.css';
import MainPage from "./MainPage";
import LoginPage from "./LoginPage";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "mainPage"
        }
    }


    showContent() {
        if (this.state.show === "mainPage") return <MainPage/>;
        if (this.state.show === "loginPage") return <LoginPage/>;
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

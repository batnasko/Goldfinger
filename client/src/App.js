import React, {Component} from 'react';
import './App.css';
import WorldMap from './map/WorldMap'
import {Button, Navbar} from "react-bootstrap";
import AdminPanel from "./user-admin/AdminPanel";

class App extends Component {
    constructor(props){
        super(props);
        this.state = {
            show : "map"
        }
    }

    showContent(){
        if (this.state.show === "map")return <WorldMap/>
        if (this.state.show === "adminPanel")return <AdminPanel/>
    }
    render() {
        return (
            <div className="App">
                <Navbar expand="lg" variant="light" bg="light">
                    <Navbar.Brand onClick={e => this.setState({show : "map"})}>Goldfinger</Navbar.Brand>
                    <Button variant="danger" onClick={e => this.setState({show : "adminPanel"})}>Admin Panel</Button>
                </Navbar>
                {this.showContent()}
            </div>
        );
    }
}

export default App;

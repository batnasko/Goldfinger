import React, {Component} from 'react';
import './App.css';
import WorldMap from './map/WorldMap'
import {Navbar} from "react-bootstrap";

class App extends Component {
    render() {
        return (
            <div className="App">
                <Navbar expand="lg" variant="light" bg="light">
                    <Navbar.Brand >Goldfinger</Navbar.Brand>
                </Navbar>
                <WorldMap/>
            </div>
        );
    }
}

export default App;

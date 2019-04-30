import React, {Component} from 'react';
import './MainPage.css';
import WorldMap from './map/WorldMap'
import {Button, Navbar} from "react-bootstrap";
import AdminPanel from "./user-admin/AdminPanel";

class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: "map"
        }
    }

    showMap = () => {
        this.setState({
            show: "map"
        });
    };

    showContent() {
        if (this.state.show === "map") return <WorldMap/>;
        if (this.state.show === "adminPanel") return <AdminPanel showMap={this.showMap}/>;
    }

    render() {
        return (
            <div>
                <Navbar expand="lg" variant="light" bg="light">
                    <Navbar.Brand className="goldfinger-brand" onClick={e => this.setState({show: "map"})}>
                        <img src={require("./images/logo.png")}
                             width="150"
                             height="auto"
                             className="d-inline-block align-top"
                        />
                    </Navbar.Brand>
                    <Button variant="danger" onClick={e => this.setState({show: "adminPanel"})}>Admin Panel</Button>
                </Navbar>
                {this.showContent()}
            </div>
        );
    }
}

export default MainPage;

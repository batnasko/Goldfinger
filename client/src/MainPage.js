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
        if (this.state.show === "map") return <WorldMap user={this.props.user}/>;
        if (this.state.show === "adminPanel") return <AdminPanel user={this.props.user} showMap={this.showMap}/>;
    }

    showAdminButton() {
        if (this.props.user.userDetails.roles.includes("ROLE_ADMIN")) return <Button variant="danger" style={{marginLeft: 10, marginRight: 10}} onClick={e => this.setState({show: "adminPanel"})}>Admin Panel</Button>;
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
                    <div style={{marginLeft: "auto"}}>
                        {this.showAdminButton()}
                        <Button variant="danger" style={{marginLeft: 10, marginRight: 10}}
                                onClick={this.props.showLoginPage}>Logout</Button> {/*after implementing security remove cookie*/}
                    </div>
                </Navbar>
                {this.showContent()}
            </div>
        );
    }
}

export default MainPage;

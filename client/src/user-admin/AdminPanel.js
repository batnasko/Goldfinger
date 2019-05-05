import React, {Component} from 'react';
import {Button, Navbar} from "react-bootstrap";

import './AdminPanel.css';

import Auditability from "./Auditability";
import UploadShp from "./UploadShp";
import DataOptions from "./DataOptions";


class AdminPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show : "auditability"
        }
    }

    componentDidMount() {
    }

    showContent(){
        if (this.state.show === "auditability") return <Auditability user ={this.props.user}/>
        else if(this.state.show === "uploadShp") return <UploadShp user={this.props.user} showMap={this.props.showMap}/>
        else if(this.state.show === "dataOptions") return <DataOptions user={this.props.user}/>
    }

    render() {
        let marginNavItems = {
            marginLeft: 10,
            marginRight: 10
        };
        return (
            <div className="admin-panel">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Admin Panel</Navbar.Brand>
                    <Button style={marginNavItems} variant="danger" onClick = {()=> this.setState({show : "auditability"})}>Auditability</Button>
                    <Button style={marginNavItems} variant="danger" onClick = {()=> this.setState({show : "uploadShp"})}>Upload</Button>
                    <Button style={marginNavItems} variant="danger" onClick = {()=> this.setState({show : "dataOptions"})}>Data options</Button>
                </Navbar>
                <div className="content-container">
                    {this.showContent()}
                </div>
            </div>
        );
    }
}

export default AdminPanel;
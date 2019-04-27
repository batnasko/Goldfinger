import React, {Component} from 'react';
import {Button, Navbar} from "react-bootstrap";
import './AdminPanel.css';

import Auditability from "./Auditability";
import UploadShp from "./UploadShp";


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
        if (this.state.show === "auditability") return <Auditability/>
        else if(this.state.show === "uploadShp") return <UploadShp/>
    }

    render() {
        return (
            <div className="admin-panel">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Admin Panel</Navbar.Brand>
                    <Button variant="danger" onClick = {()=> this.setState({show : "auditability"})}>Auditability</Button>
                    <Button variant="danger" onClick = {()=> this.setState({show : "uploadShp"})}>Upload</Button>
                </Navbar>
                <div className="table-container">
                    {this.showContent()}
                </div>
            </div>
        );
    }
}

export default AdminPanel;
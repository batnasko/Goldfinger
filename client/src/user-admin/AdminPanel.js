import React, {Component} from 'react';
import {Button, Table, Navbar} from "react-bootstrap";
import './AdminPanel.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';


class AdminPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            logs: []
        }
    }

    render() {
        const products = [{
            username: "Rosen",
            ip: "1533652",
            timeOfEvent: "25.36",
            message: "asdasdsadasd"
        }, {
            username: "Atanas",
            ip: "1533652",
            timeOfEvent: "25.36",
            message: "asdasdsadasd"
        }];
        const columns = [{
            dataField: 'username',
            text: 'Username',
            sort: true
        }, {
            dataField: 'ip',
            text: 'IP'
        }, {
            dataField: 'timeOfEvent',
            text: 'Time of the event'
        }, {
            dataField: 'message',
            text: 'Message'
        }];
        return (
            <div className="admin-panel">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Admin Panel</Navbar.Brand>
                    <Button variant="danger">Auditability</Button>
                </Navbar>
                <div className="table-container">
                    <BootstrapTable keyField='id' data={products} columns={columns}/>
                </div>
            </div>
        );
    }
}

export default AdminPanel;
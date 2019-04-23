import React, {Component} from 'react';
import {Button, Table, Navbar} from "react-bootstrap";
import './AdminPanel.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import axios from "axios";


class AdminPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            logs: []
        }
    }

    componentDidMount() {
        this.getLogs();
    }

    getLogs() {
        axios.post("http://localhost:8000/auditability/get", {}).then(success => {
            this.setState({
                logs: success.data
            })
        })
    }

    render() {
        const products = this.state.logs;
        const columns = [{
            dataField: 'username',
            text: 'Username',
            sort: true
        }, {
            dataField: 'ip',
            text: 'IP',
            sort: true
        }, {
            dataField: 'date',
            text: 'Time of the event',
            sort: true
        }, {
            dataField: 'msg',
            text: 'Message',
            sort: true
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
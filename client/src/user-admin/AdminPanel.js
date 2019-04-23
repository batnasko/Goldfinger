import React, {Component} from 'react';
import {Button, FormControl, Navbar, FormGroup} from "react-bootstrap";
import './AdminPanel.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';
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

    getLogs(event) {
        let body = {};
        if (event !== undefined){
            if (event.target.value.trim() !== "") {
                body = {
                    search : event.target.value.trim()
                }
            }
        } 
        axios.post("http://localhost:8000/auditability/get", body).then(success => {
            this.setState({
                logs: success.data
            })
        })
    }

    render() {
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
                    <FormControl
                        type='text'
                        name='search'
                        placeholder='Search'
                        onChange={this.getLogs.bind(this)}
                    />
                    <BootstrapTable
                        bootstrap4 keyField='id' data={this.state.logs} columns={columns}
                        pagination={paginationFactory()}/>
                </div>
            </div>
        );
    }
}

export default AdminPanel;
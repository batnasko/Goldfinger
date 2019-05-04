import React, {Component} from 'react';
import {FormControl} from "react-bootstrap";
import './AdminPanel.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import 'react-bootstrap-table2-toolkit/dist/react-bootstrap-table2-toolkit.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, {CSVExport} from 'react-bootstrap-table2-toolkit';
import axios from "axios";
import date from "../common/date";
import "./Auditability.css";

const {ExportCSVButton} = CSVExport;


class Auditability extends Component {
    constructor(props) {
        super(props);
        this.state = {
            logs: [],
            columnsToDisplay: [{
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
            }]
        }
    }

    componentDidMount() {
        this.getLogs();
    }

    getLogs(event) {
        let body = {};
        if (event !== undefined) {
            if (event.target.value.trim() !== "") {
                body = {
                    search: event.target.value.trim()
                }
            }
        }
        axios.post("http://localhost:8000/auditability/get", body,{
            headers:{
                "Authorization" : "Bearer " + this.props.user.token
            }
        }).then(success => {
            this.setState({
                logs: success.data
            })
        })
    }


    render() {
        return (
            <ToolkitProvider
                bootstrap4
                keyField="toolkit"
                data={this.state.logs}
                columns={this.state.columnsToDisplay}>
                {props => (
                    <div>
                        <ExportCSVButton className="btn btn-success" style={{marginBottom: 4}} {...props.csvProps}>Export
                            CSV</ExportCSVButton>

                        <FormControl
                            style={{marginBottom: 4}}
                            type='text'
                            name='search'
                            placeholder='Search'
                            onChange={this.getLogs.bind(this)}
                        />
                        <BootstrapTable{...props.baseProps}
                                       pagination={paginationFactory()}/>
                    </div>
                )}
            </ToolkitProvider>
        );
    }
}

export default Auditability;
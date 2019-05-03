import React, {Component} from 'react';
import {Button, Form, Col, InputGroup} from "react-bootstrap";
import axios from "axios";
import Spinner from "../common/spinner.js"


class UploadShp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false,
            isFileUploading: false
        }
    }

    uploadShp(event) {
        event.preventDefault();
        this.setState({validated: true});

        const form = event.currentTarget;
        let returnToMap = this.props.showMap;
        if (form.checkValidity() === true) {
            this.setState({
                isFileUploading: true
            });
            let token = this.props.token;
            var reader = new FileReader();
            reader.readAsDataURL(form.shapeFile.files[0]);
            reader.onload = function () {
                let data = {
                    "file": reader.result,
                    "shpFileName": form.shapeType.value,
                    "columnsToShow": form.columnsToDisplay.value,
                    "columnToColor": form.columnToColor.value
                };
                axios.post("http://localhost:9000/map/upload", data, {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                }).then(response =>
                    returnToMap()
                );
            };
        }
    }

    showContent() {
        if (!this.state.isFileUploading) {
            return <Form
                noValidate
                validated={this.state.validated}
                onSubmit={e => this.uploadShp(e)}
            >
                <Form.Row>
                    <Form.Group as={Col} md="4" controlId="shapeFile">
                        <Form.Control
                            required
                            type="file"
                            placeholder="Shape file"
                            style={{display: "none"}}
                            ref={fileInput => this.fileInput = fileInput}
                        />
                        <Button style={{display: "block"}} variant="success" onClick={() => this.fileInput.click()}>Select
                            file</Button>
                        <Form.Control.Feedback type="invalid">Upload shape file</Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} md="4" controlId="shapeType">
                        <Form.Label>Shape file name</Form.Label>
                        <Form.Control
                            required
                            type="text"
                            placeholder="Shape file type"
                        />
                        <Form.Control.Feedback type="invalid">Enter shape file type</Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Form.Row>
                    <Form.Group as={Col} md="6" controlId="columnsToDisplay">
                        <Form.Label>Columns to display</Form.Label>
                        <Form.Control type="text" placeholder="Enter columns separated by whitespaces" required/>
                        <Form.Control.Feedback type="invalid">
                            Please enter columns
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} md="3" controlId="columnToColor">
                        <Form.Label>Column to color</Form.Label>
                        <Form.Control type="text" placeholder="Column to color" required/>
                        <Form.Control.Feedback type="invalid">
                            Please enter column to color on the map
                        </Form.Control.Feedback>
                    </Form.Group>
                </Form.Row>
                <Button type="submit" variant="danger">Submit form</Button>
            </Form>
        } else {
            return <Spinner/>
        }
    }

    render() {
        return (
            <div>
                {this.showContent()}
            </div>
        );
    }
}

export default UploadShp;
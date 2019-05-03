import React, {Component} from 'react';
import {Map, TileLayer, GeoJSON, Tooltip, Popup} from "react-leaflet";
import axios from "axios"
import {Navbar, Dropdown, Button, Alert} from 'react-bootstrap';
import "./WorldMap.css"


class WorldMap extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataTypes: [],
            currentDataType: {
                id: 0,
                dataType: "Select data type",
                rowToColor: null,
                dataProperties: []
            },
            shapes: [],
            noInfoPopup: {
                latlng: {
                    lat: 0,
                    lng: 0,
                    msg: "There isn't any data for this point"
                }
            },
            alertShowAllShapes: {
                show: false,
            },
            httpHeaders: {
                headers: {
                    Authorization: "Bearer " + this.props.token
                }
            }
        };
    }

    componentDidMount() {
        axios.get("http://localhost:9000/map/datatype", this.state.httpHeaders).then(response => {
            response.data.map((dataType) => {
                axios.get("http://localhost:9000/map/datatype/" + dataType.id + "/property", this.state.httpHeaders).then(response => {
                    dataType["properties"] = response.data;

                    this.setState(prevState => ({
                        dataTypes: [...prevState.dataTypes, dataType]
                    }))
                })
            });
        });
    }

    changeDataType(dataType) {
        this.setState({
            currentDataType: {
                id: dataType.id,
                dataType: dataType.dataType,
                dataProperties: dataType.properties,
                rowToColor: dataType.rowToColor
            }
        });
        this.clearMap();
    }

    clearMap() {
        this.setState({
            shapes: []
        })
    }


    getShape(e) {
        if (this.state.currentDataType.id === 0) {
            this.setState({
                noInfoPopup: {
                    show: true,
                    latlng: e.latlng,
                    msg: "Please select data type"
                }
            });
            setTimeout(() => {
                this.setState({
                    noInfoPopup: {
                        show: false
                    }
                });
            }, 600);
        } else {
            let data = {
                latitude: e.latlng.lng,
                longitude: e.latlng.lat
            };
            axios.post("http://localhost:9000/map/shape/" + this.state.currentDataType.id, data, this.props.httpHeaders).then(response => {
                this.setState({
                    shapes: [...this.state.shapes, response.data]
                })
            }).catch((error) => {
                console.log(error);
                this.setState({
                    noInfoPopup: {
                        show: true,
                        latlng: e.latlng,
                        msg: "There isn't any data for this point"
                    }
                });
                setTimeout(() => {
                    this.setState({
                        noInfoPopup: {
                            show: false
                        }
                    });
                }, 600);
            })
        }
    }

    stringToColor(str) {
        for (var i = 0, hash = 0; i < str.length; hash = str.charCodeAt(i++) + ((hash << 5) - hash)) ;
        let color = Math.floor(Math.abs((Math.sin(hash) * 10000) % 1 * 16777216)).toString(16);
        return '#' + Array(6 - color.length + 1).join('0') + color;
    }

    getAllShapes() {
        if (this.state.currentDataType.id === 0) {
            this.setState({
                alertShowAllShapes: {
                    show: true
                }
            })
        } else {
            axios.get("http://localhost:9000/map/shape/" + this.state.currentDataType.id, this.state.httpHeaders).then(response => {
                this.setState({
                    shapes: response.data
                })
            })
        }
    }

    render() {
        let marginNavItems = {
            marginLeft: 10,
            marginRight: 10
        };
        return (
            <div className="map-container">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Map Options</Navbar.Brand>
                    <Button style={marginNavItems} variant="danger" onClick={e => this.clearMap()}>Clear Map</Button>
                    <Dropdown style={marginNavItems}>
                        <Dropdown.Toggle variant="danger" id="dropdown-basic-button">
                            {this.state.currentDataType.dataType}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {this.state.dataTypes.map((dataType) =>
                                <Dropdown.Item key={dataType.id}
                                               onClick={() => this.changeDataType(dataType)}>{dataType.dataType}</Dropdown.Item>
                            )}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Button style={marginNavItems} variant="danger" onClick={e => this.getAllShapes()}>Show All</Button>
                </Navbar>
                <Alert style={{marginBottom: 1}} show={this.state.alertShowAllShapes.show} variant="danger">
                    <p>Please, select data type, so we can show you some data!</p>
                    <div>
                        <Button onClick={e => {
                            this.setState({alertShowAllShapes: {show: false}})
                        }} variant="outline-danger">
                            Alright!
                        </Button>
                    </div>
                </Alert>
                <Map onClick={this.getShape.bind(this)} className="map"
                     center={[42.65, 23.37]}
                     zoom={6}>
                    {this.state.noInfoPopup.show &&
                    <Popup position={this.state.noInfoPopup.latlng}>{this.state.noInfoPopup.msg}</Popup>
                    }
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                    />
                    {this.state.shapes.map((shape, idx) =>
                        <GeoJSON color={this.stringToColor(shape.properties[this.state.currentDataType.rowToColor])}
                                 key={"shape-" + idx}
                                 data={shape.geometry}
                                 onMouseOver={(e) => {
                                     e.target.openPopup();
                                 }}
                                 onMouseOut={(e) => {
                                     e.target.closePopup();
                                 }}>
                            <Tooltip key={"tooltip-" + idx}>
                                {this.state.currentDataType.dataProperties.map(property =>
                                    <span> {property}: {shape.properties[property]} <br/> </span>
                                )}
                            </Tooltip>
                        </GeoJSON>
                    )}
                </Map>
            </div>
        );
    }
}

export default WorldMap;

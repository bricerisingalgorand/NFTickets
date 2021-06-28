import React, {useState, useEffect} from 'react';
import {
  Container, Col,
  Button, Form, Row
} from "react-bootstrap";
import {useParams, withRouter} from "react-router-dom";
const eventService = require('../services/eventService.js')

export default function Event(props) {
  const { eventId } = useParams();

  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [venue, setVenue] = useState("");
  const [time, setTime] = useState("");
  const [zones, setZones] = useState([]);

  function onSubmit(e) {
    e.preventDefault();
    console.log(name, desc, time) // use these as fields
  }

  useEffect(() => {
    // call api to fetch details
    // eventId is the eventId
    eventService.getEvent(eventId, (data, err) => {
      if (err) {
        console.log(err)
      } else {
        console.log(data)
        setName(data['performance.name'])
        setDesc(data['performance.description'])
        const epoch = data.startTime
        const d = new Date(0); // The 0 there is the key, which sets the date to the epoch
        d.setUTCSeconds(epoch);
        setTime(d)
        setVenue(data['venue.name'])
        setZones(data['zones'])
      }
    })
  }, []);

  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Event Details
      </h1>
      <Row>
        <Col>
            <p>{`Event: ${name}`}</p>
        </Col>
        <Col>
            <p>{`Date: ${time}`}</p>
        </Col>
      </Row>
      <Row>
        <Col>
            <p>{`Location: ${venue}`}</p>
        </Col>
        <Col>
            <p>{`Time: ${time}`}</p>
        </Col>
      </Row>

      <h3>Description</h3>
      <p>{desc}</p>
      <Form onSubmit={onSubmit}>
        <Form.Label>Seating Details</Form.Label>
        <Form.Row className="mb-3">
            <Form.Group as={Col} controlId="formGridEventName">
                <Form.Control as="select" defaultValue="Select Zone...">
                    {Array.from(zones).map((zone) => {
                        return <option>{zone.name}</option>
                    })}
                </Form.Control>
            </Form.Group>
            <Form.Group as={Col} controlId="formGridLocation">
                <Form.Control type="location" placeholder="Available seats" />
            </Form.Group>
            <Form.Group as={Col} controlId="formGridLocation">
                <Form.Control type="number" placeholder="Price (ALGO)" />
            </Form.Group>
        </Form.Row>
        <Button variant="primary" type="submit">
          Create Event
        </Button>
      </Form>
    </Container>
  )
}
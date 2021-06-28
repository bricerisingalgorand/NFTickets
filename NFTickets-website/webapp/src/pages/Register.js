import React, {useState} from 'react';
import {
  Container, Col,
  Button, Form
} from "react-bootstrap";
const fundService = require('../services/fundService.js')

export default function Register(props) {
  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [goal, setGoal] = useState("");
  // eslint-disable-next-line
  const [curr, setCurr] = useState("");
  const [acct, setAcct] = useState("");
  const [time, setTime] = useState("");

  function onSubmit(e) {
    e.preventDefault();
    console.log(name, desc, goal, acct, time) // use these as fields
    const fund = fundService.fundBuilder(name, desc, parseInt(time), parseInt(goal), acct);
    fundService.createFund(fund, ()=>{});
  }

  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Register an Event
      </h1>
      <Form onSubmit={onSubmit}>
        <Form.Row className="mb-3">
          <Form.Group as={Col} controlId="formGridEventName">
            <Form.Label>Event Name</Form.Label>
            <Form.Control type="name" placeholder="Enter event name" />
          </Form.Group>
          <Form.Group as={Col} controlId="formGridLocation">
            <Form.Label>Event Location</Form.Label>
            <Form.Control type="location" placeholder="Enter event location" />
          </Form.Group>
        </Form.Row>
        <Form.Group controlId="desc">
          <Form.Label>Description</Form.Label>
          <Form.Control as="textarea" rows="3" onChange={(e) => setDesc(e.target.value)}/>
        </Form.Group>
        <Form.Row className="mb-3">
          <Col>
            <Form.Label>Event Start Date</Form.Label>
            <Form.Control type="date" />
          </Col>
          <Col>
            <Form.Label>Event End Date</Form.Label>
            <Form.Control type="date" />
          </Col>
          <Col>
            <Form.Label>Event Start Time</Form.Label>
            <Form.Control type="time" />
          </Col>
          <Col>
            <Form.Label>Event End Time</Form.Label>
            <Form.Control type="time" />
          </Col>
        </Form.Row>
        <Form.Row className="mb-3">
          <Form.Group as={Col} controlId="formGridEventName">
            <Form.Label>Ticket Details</Form.Label>
            <Form.Control type="number" placeholder="Enter quantity" />
          </Form.Group>
          <Form.Group as={Col} controlId="formGridLocation">
            <Form.Label>Ticket Limit</Form.Label>
            <Form.Control type="number" placeholder="Enter limit" />
          </Form.Group>
        </Form.Row>
        <Form.Label>Seating Details</Form.Label>
        <Form.Row className="mb-3">
          <Form.Group as={Col} controlId="formGridEventName">
            <Form.Control type="string" placeholder="Enter Zone" />
          </Form.Group>
          <Form.Group as={Col} controlId="formGridLocation">
            <Form.Control type="number" placeholder="Price (ALGO)" />
          </Form.Group>
        </Form.Row>
        <Form.Group controlId="desc">
          <Form.Label>Seat Mapping</Form.Label>
          <Form.Control as="textarea" rows="3" onChange={(e) => setDesc(e.target.value)}/>
        </Form.Group>
        <Button variant="primary" type="submit">
          Create Event
        </Button>
      </Form>
    </Container>
  )
}
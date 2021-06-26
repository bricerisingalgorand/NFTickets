import React, {useState} from 'react';
import {
  Container, Col,
  Button, Form, Row, InputGroup, FormControl
} from "react-bootstrap";
const fundService = require('../services/fundService.js')

export default function About(props) {
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
        Event Details
      </h1>
      <Row>
        <Col>
            <p>Event: Harry Styles: Love on Tour</p>
        </Col>
        <Col>
            <p>Date: Mon, Oct 23</p>
        </Col>
      </Row>
      <Row>
        <Col>
            <p>Location: TD Garden - Boston, MA</p>
        </Col>
        <Col>
            <p>Time: 8:00pm - 1:00pm</p>
        </Col>
      </Row>

      <h3>Description</h3>
      <p>Lorem ipsum odor amet, consectetuer adipiscing elit. Malesuada fames proin venenatis tristique dictum. Maximus scelerisque conubia turpis amet curae taciti fames felis. Duis malesuada commodo curae conubia eleifend; convallis pellentesque nam. Fusce porta adipiscing, aliquam etiam fames malesuada vitae. Laoreet in integer mi montes fermentum dignissim pellentesque. Pharetra ante non ultrices vivamus, vehicula magna porta. Dictum massa nisi mus, aenean adipiscing nascetur. Id id etiam placerat cubilia suspendisse porta imperdiet.</p>
      <Form onSubmit={onSubmit}>
        <Form.Label>Seating Details</Form.Label>
        <Form.Row className="mb-3">
            <Form.Group as={Col} controlId="formGridEventName">
                <Form.Control type="number" placeholder="Enter row/zone" />
            </Form.Group>
            <Form.Group as={Col} controlId="formGridLocation">
                <Form.Control type="location" placeholder="Available seats" />
            </Form.Group>
            <Form.Group as={Col} controlId="formGridLocation">
                <Form.Control type="location" placeholder="Price (ALGO)" />
            </Form.Group>
            <InputGroup>
                <InputGroup.Text>ALGO</InputGroup.Text>
                <FormControl id="inlineFormInputGroupUsername" placeholder="Username" />
            </InputGroup>
        </Form.Row>
        <Button variant="primary" type="submit">
          Create Event
        </Button>
      </Form>
    </Container>
  )
}
import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from "react-router-dom";
import {
  Navbar,
  Nav,
  Form,
  FormControl,
  Button
} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import About from './pages/About'
import Browse from './pages/Browse'
import Register from './pages/Register'
import Event from './pages/Event'
import Checkout from './pages/Checkout'

function App(props) {
  return (
    <Router>
      <Navbar bg="light" expand="lg">
        <Navbar.Brand href="/">NFTickets</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">
            <Nav.Link href="/register">Register Events</Nav.Link>
            <Nav.Link href="/browse">Browse</Nav.Link>
            <Nav.Link href="/Event">Event</Nav.Link>
            <Nav.Link href="/Checkout">Checkout</Nav.Link>
          </Nav>
          <Form inline>
            <FormControl type="text" placeholder="Search" className="mr-sm-2" />
            <Button variant="outline-success">Search</Button>
          </Form>
        </Navbar.Collapse>
      </Navbar>
      <Switch>
        <Route exact path="/">
          <About />
        </Route>
        <Route path="/register">
          <Register />
        </Route>
        <Route path="/browse">
          <Browse />
        </Route>
        <Route path="/event">
          <Event />
        </Route>
        <Route path="/checkout">
          <Checkout />
        </Route>
        {/* <Route exact path="/project/:projectId">
          <Project />
        </Route> */}
      </Switch>
    </Router>
  );
}

export default App;

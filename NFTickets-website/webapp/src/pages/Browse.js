import React, {useEffect, useState} from 'react';
import {
  Container,
  Table,
  Button
} from "react-bootstrap";
const eventService = require('../services/eventService.js')
const dateFormat = require('dateformat');

export default function Browse(props) {

  const [events, setEvents] = useState([]);

  useEffect(() => {
    // call api to fetch details
    // projectId is the projectId
    eventService.getEvents((data, err) => {
      if (err) {
        console.log(err)
      } else {
        console.log(data.contents)
        setEvents(data.contents)
      }
    })
  }, []);


  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Available Events
      </h1>
      <Table responsive>
        <thead>
        <tr>
          <th>Event</th>
          <th>Location</th>
          <th>Date/Time</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        {Array.from(events).map((event) => {
          return (
            <tr>
              <td>{event['performance.name']}</td>
              <td>{event['venue.name']}</td>
              <td>{dateFormat(new Date(event.startTime), "yyyy-mm-dd h:MM:ss")}</td>
              <td>
                <Button href={`/browse/${event.id}`}>
                  Event Info
                </Button>
              </td>
            </tr>
           )
        })}
        </tbody>
      </Table>
    </Container>
  )
}
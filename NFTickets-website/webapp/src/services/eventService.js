const axios = require('axios')
const APP_SERVICE_URL = 'http://localhost:8080'

const getEvent = function (eventId, callback) {
  axios.get(`${APP_SERVICE_URL}/event/${eventId}`).then(
    (result) => {
      callback(result.data, null);
    })
    .catch((error) => {
      callback(null, error);
    });
}

const getEvents = function (callback) {
  axios.get(`${APP_SERVICE_URL}/event`).then(
    (result) => {
      callback(result.data, null);
    })
    .catch((error) => {
      callback(null, error);
    });
}

/* Example Event
{
  "startTime": "2021-06-27T18:59:29.837Z",
  "endTime": "2021-06-27T18:59:29.837Z",
  "performance": {
    "name": "string",
    "description": "string"
  },
  "venue": {
    "name": "string",
    "description": "string"
  },
  "zones": [
    {
      "name": "string",
      "seats": 0,
      "price": 0
    }
  ]
}
 */

const createEvent = function (event, callback) {
  axios.post(`${APP_SERVICE_URL}/event`, fund, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then((result) => {
      callback(result.data, null);
    })
    .catch((error) => {
      callback(null, error);
    });
}

module.exports = {
  getEvent,
  getEvents,
  createEvent,
}

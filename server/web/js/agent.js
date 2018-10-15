'use strict';

(function () {
  const CONNECTED_INTERLOCUTOR = `You have been connected with client ${name}`;
  const REGISTERED_MESSAGE = 'You are registered in the system. Wait questions from clients.';

  const WEBSOCKET_URL = 'ws://188.225.56.206:80/support/agent'

  window.user = {
    CONNECTED_INTERLOCUTOR: CONNECTED_INTERLOCUTOR,
    REGISTERED_MESSAGE: REGISTERED_MESSAGE,
    WEBSOCKET_URL: WEBSOCKET_URL
  }
})();
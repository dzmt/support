'use strict';

(function () {
  const CONNECTED_INTERLOCUTOR = `You have been connected with agent ${name}`;
  const REGISTERED_MESSAGE = 'You are registered in the system. You can ask your questions now.';
  const INTERLOCUTOR_LEAVE = 'Agent has left chat. If you need help, ask a question.'
  const LEAVE_CHAT_WITH_AGENT = 'YOu have left chat with agent.  If you need help, ask a question.'

  const WEBSOCKET_URL = 'ws://localhost:8080/support/client';

  window.user = {
    CONNECTED_INTERLOCUTOR: CONNECTED_INTERLOCUTOR,
    REGISTERED_MESSAGE: REGISTERED_MESSAGE,
    WEBSOCKET_URL: WEBSOCKET_URL,
    INTERLOCUTOR_LEAVE: INTERLOCUTOR_LEAVE,
    LEAVE_CHAT_WITH_AGENT: LEAVE_CHAT_WITH_AGENT
  }
})();
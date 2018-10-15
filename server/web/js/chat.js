'use strict';

(function () {
  let util = window.util;
  let getDialogId = window.util.getDialogId;

  const PARTNER_LIST_SELECTOR = '.partner__list';
  const ACTIVE_PARTNER_SELECTOR = '.partner__item-active';
  const ACTIVE_PARTNER_CLASS = 'partner__item-active';
  const UNREAD_PARTNER_CLASS = 'partner__item-unread';

  const DIALOGS_SELECTOR = '.dialog__list';
  const ACTIVE_DIALOG_SELECTOR = '.dialog__item-active';
  const ACTIVE_DIALOG_CLASS = 'dialog__item-active'

  let INPUT_SELECTOR = '.input-message';
  let SEND_SELECTOR = '.btn-send';
  let LEAVE_SELECTOR = '.btn-leave';

  
  const SERVER_ID = 'server';

  const tabs = document.querySelector(PARTNER_LIST_SELECTOR);
  const dialogs = document.querySelector(DIALOGS_SELECTOR);
  const serverTab = document.all[SERVER_ID];
  const serverDialog = document.all[getDialogId(SERVER_ID)];

  const input = document.querySelector(INPUT_SELECTOR);
  const send = document.querySelector(SEND_SELECTOR);
  const leaveExit = document.querySelector(LEAVE_SELECTOR);

  window.activeTab = serverTab;
  window.activeDialog = serverDialog;

  let disabledServerTab = function () {
    if (window.activeTab === serverTab) {
      input.hidden = true;
      send.hidden = true;
      leaveExit.textContent = 'Exit';
    } else {
      input.hidden = false;
      send.hidden = false;

      leaveExit.textContent = 'Leave';
    }
  }

  disabledServerTab();

  let toggleActiveDialog = function(tabId) {
    let dialog = document.all[getDialogId(tabId)];
    if (!dialog.classList.contains(ACTIVE_DIALOG_CLASS)) {
      window.activeDialog.classList.remove(ACTIVE_DIALOG_CLASS);
      dialog.classList.add(ACTIVE_DIALOG_CLASS);
      window.activeDialog = dialog;
    }
    
  };

  let toggleActiveTab = function(tab) {
    if (tab !== window.activeTab) {
      window.activeTab.classList.remove(ACTIVE_PARTNER_CLASS);
      tab.classList.add(ACTIVE_PARTNER_CLASS);

      if (tab.classList.contains(UNREAD_PARTNER_CLASS)) {
        tab.classList.remove(UNREAD_PARTNER_CLASS);
      }
    }
    
    window.activeTab = tab;
    toggleActiveDialog(tab.id);
  }

  let clickTabItemHandler = evt => {
    let tab = evt.target;
    toggleActiveTab(tab);
    disabledServerTab();
  }

  tabs.addEventListener('click', clickTabItemHandler);

  let showRemoteMessage = function (id, text) {
    let tab = document.getElementById(id);
    let dialog = document.all[getDialogId(id)];

    if (!tab.classList.contains(UNREAD_PARTNER_CLASS) && !tab.classList.contains(ACTIVE_PARTNER_CLASS)) {
      tab.classList.add(UNREAD_PARTNER_CLASS);
    }
    let p = util.createMessage('remote', text);
    dialog.appendChild(p);
  }

  let createContentMessage = function (from, to, text) {
    return {
      type: 'CONTENT', 
      from: from, 
      to: to, 
      text: text};
  }

  let createCurrentMessage = function (from, text) {
    let to = window.activeTab.id;
    let p = util.createMessage('current', text);
    window.activeDialog.appendChild(p);
    return createContentMessage(from, to, text);
  }

  let createInterlocutor = function (id, name) {
    let tab = util.createTab(id, name);
    let dialog = util.createDialog(id);
    let text = window.user.CONNECTED_INTERLOCUTOR;

    showRemoteMessage(SERVER_ID, text);

    dialogs.appendChild(dialog);
    tabs.appendChild(tab);
  }

  let removeTab = function (id) {
    let tab = document.getElementById(id);
    let dialog = document.all[getDialogId(id)];
    let name = tab.textContent;
    
    if (tab.classList.contains(ACTIVE_PARTNER_CLASS)) {
      toggleActiveTab(serverTab);
      disabledServerTab();
    }
    tabs.removeChild(tab);
    dialogs.removeChild(dialog);
  }
  
  window.chat = {
    serverTab: serverTab,
    serverDialog: serverDialog,
    // activeTab: activeTab,
    // activeDialog: activeDialog,
    showRemoteMessage: showRemoteMessage,
    createInterlocutor: createInterlocutor,
    removeTab: removeTab,
    createCurrentMessage: createCurrentMessage
  }
})();
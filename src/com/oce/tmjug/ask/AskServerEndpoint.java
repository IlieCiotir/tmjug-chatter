/**
 * 
 */
package com.oce.tmjug.ask;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Sile
 * 
 */
@ServerEndpoint(value = "/ask")
public class AskServerEndpoint {
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {

	}

	@OnMessage
	public void onMessage(Session session, final String message) {
		for (Session t : session.getOpenSessions()) {

			if (t.isOpen() && !session.equals(t)) {
				t.getAsyncRemote().sendText(message);
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {

	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {

	}
}

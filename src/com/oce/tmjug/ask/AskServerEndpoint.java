/**
 * 
 */
package com.oce.tmjug.ask;

import java.util.UUID;

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
		session.getUserProperties().put(session.getId(), UUID.randomUUID());
		System.out.println(session.getUserProperties().get(session.getId()));
		session.getAsyncRemote().sendText(
				"Welcome, your id is "
						+ session.getUserProperties().get(session.getId())
								.toString());
		for (Session t : session.getOpenSessions()) {
			t.getAsyncRemote().sendText(
					session.getUserProperties().get(session.getId()).toString()
							+ " joined.");
		}

	}

	@OnMessage
	public void onMessage(Session session, final String message) {
		for (Session t : session.getOpenSessions()) {

			t.getAsyncRemote().sendText(
					session.getUserProperties().get(session.getId()).toString()
							+ " said " + message);

		}
	}

	@OnError
	public void onError(Session session, Throwable error) {

	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		for (Session s : session.getOpenSessions()) {
			if (s.isOpen() && !session.equals(s)) {
				s.getAsyncRemote().sendText(
						session.getUserProperties().get(session.getId())
								.toString()
								+ " left.");
			}
		}
	}
}

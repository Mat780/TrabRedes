package main;

public enum Protocolos {
	LOGIN, 				// Somente Cliente/Servidor
	REGISTER, 			// Somente Cliente/Servidor
	DISCONNECT, 		// Somente Cliente/Servidor
	LIST_USER_ON_LINE,  // Somente Cliente/Servidor
	LIST_USER_PLAYING,  // Somente Cliente/Servidor
	UPDATE_PORT, 		// Somente Cliente/Servidor
	GAME_HUB,			// Somente Cliente/Servidor
	GAME_INI, 			// Somente Peer to peer
	GAME_ACK, 			// Somente peer to peer
	GAME_NEG, 			// Somente peer to peer
	GAME_OVER, 			// Cliente/Servidor e Peer to peer
	GAME_START,			// Cliente/Servidor e Peer to peer
	STAY_ALIVE	 		// Cliente/Servidor e Peer to peer
}
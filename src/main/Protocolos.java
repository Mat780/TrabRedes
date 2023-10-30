package main;

// Enum dos protocolos utilizados durante as diferentes
// arquiteturas de aplicacao no pacote main.
public enum Protocolos {
	LOGIN, 				// Somente Cliente/Servidor
	REGISTER, 			// Somente Cliente/Servidor
	DISCONNECT, 		// Somente Cliente/Servidor
	LIST_USER_ON_LINE,  // Somente Cliente/Servidor
	LIST_USER_PLAYING,  // Somente Cliente/Servidor
	UPDATE_IP,			// Somente Cliente/Servidor
	UPDATE_PORT, 		// Somente Cliente/Servidor
	GAME_HUB,			// Somente Cliente/Servidor
	GET_QTD_CADASTRADOS,// Somente Cliente/Servidor
	GET_QTD_ONLINE,		// Somente Cliente/Servidor
	GET_QTD_JOGANDO, 	// Somente Cliente/Servidor
	VOLTAR_LIST_ONLINE, // Somente Cliente/Servidor
	GAME_INI, 			// Somente Peer to peer
	GAME_ENTER,			// Somente Peer to peer
	GAME_ACK, 			// Somente peer to peer
	GAME_NEG, 			// Somente peer to peer
	UPDATE_PECA,		// Somente peer to peer
	UPDATE_PRONTO, 		// Somente peer to peer
	OK,					// Somente peer to peer
	GAME_START_PECA,	// Somente peer to peer
	JOGADA,				// Somente peer to peer
	GAME_HUB_ENTER, 	// Cliente/Servidor e Peer to peer
	GAME_OVER, 			// Cliente/Servidor e Peer to peer
	GAME_START,			// Cliente/Servidor e Peer to peer
	STAY_ALIVE,	 		// Cliente/Servidor e Peer to peer
}

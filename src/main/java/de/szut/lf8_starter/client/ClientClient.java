package de.szut.lf8_starter.client;

import org.springframework.stereotype.Service;

@Service
public class ClientClient {

    /**
     * Checks if a client exists (Dummy implementation for Issue #9)
     * @param clientId the client ID
     * @return true if client exists, false otherwise
     */
    public boolean clientExists(Long clientId) {
        // Dummy validation - returns true for all positive IDs
        return clientId != null && clientId > 0;
    }
}
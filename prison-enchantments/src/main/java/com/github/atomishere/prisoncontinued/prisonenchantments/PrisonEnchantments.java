package com.github.atomishere.prisoncontinued.prisonenchantments;

import com.github.atomishere.prisoncontinued.prisonenchantments.tokens.TokenManager;
import tech.mcprison.prison.PrisonAPI;
import tech.mcprison.prison.modules.Module;
import tech.mcprison.prison.output.Output;
import tech.mcprison.prison.store.Collection;
import tech.mcprison.prison.store.Database;

import java.io.IOException;
import java.util.Optional;

public class PrisonEnchantments extends Module {

    private Database database;

    /**
     * Initialize the module.
     * @param version The version of the module.
     */
    public PrisonEnchantments(String version) {
        super("Enchantments", version, 3);
    }

    @Override
    public void enable() {
        Optional<Database> databaseOptional = PrisonAPI.getStorage().getDatabase("tokensDb");
        if(!databaseOptional.isPresent()) {
            PrisonAPI.getStorage().createDatabase("tokens");
            databaseOptional = PrisonAPI.getStorage().getDatabase("tokens");
        }
        this.database = databaseOptional.get();

        //Load token manager

        TokenManager tokenManager = new TokenManager(initCollection("tokens"));
        try {
            tokenManager.loadPlayers();
        } catch(IOException ex) {
            Output.get().logError("A player file failed to load.", ex);
        }
    }

    private Collection initCollection(String collName) {
        Optional<Collection> collectionOptional = database.getCollection(collName);
        if(!collectionOptional.isPresent()) {
            database.createCollection(collName);
            collectionOptional = database.getCollection(collName);
        }

        return collectionOptional.orElseThrow(RuntimeException::new);
    }
}

package com.github.atomishere.prisoncontinued.prisonenchantments.tokens;

import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import tech.mcprison.prison.Prison;
import tech.mcprison.prison.internal.events.player.PlayerJoinEvent;
import tech.mcprison.prison.output.Output;
import tech.mcprison.prison.store.Collection;
import tech.mcprison.prison.store.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TokenManager {

    private Collection collection;
    @Getter
    private List<TokenPlayer> players;

    public TokenManager(Collection collection) {
        this.collection = collection;
        this.players = new ArrayList<>();

        Prison.get().getEventBus().register(this);
    }

    public void loadPlayer(String playerFile) throws IOException {
        Document document = collection.get(playerFile).orElseThrow(IOException::new);
        players.add(new TokenPlayer(document));
    }

    public void loadPlayers() throws IOException {
        List<Document> players = collection.getAll();
        players.forEach(document -> this.players.add(new TokenPlayer(document)));
    }

    public void savePlayer(TokenPlayer player, String playerFile) throws IOException {
        collection.insert(playerFile, player.toDocument());
    }

    public void savePlayer(TokenPlayer player) throws IOException {
        this.savePlayer(player, "tplayer_" + player.uid.getLeastSignificantBits());
    }

    public void savePlayers() throws IOException {
        for(TokenPlayer player : players) {
            savePlayer(player);
        }
    }

    public Optional<TokenPlayer> getPlayer(UUID uid) {
        return players.stream().filter(player -> player.uid.equals(uid)).findFirst();
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!getPlayer(event.getPlayer().getUUID()).isPresent()) {
            TokenPlayer newPlayer = new TokenPlayer();
            newPlayer.uid = event.getPlayer().getUUID();
            newPlayer.tokens = 0;

            players.add(newPlayer);

            try {
                savePlayer(newPlayer);
            } catch(IOException ex) {
                Output.get().logError(
                        "Failed to create new player data file for player " + event.getPlayer()
                                .getName(), ex);
            }
        }
    }
}

package com.github.atomishere.prisoncontinued.prisonenchantments.tokens;

import lombok.Getter;
import lombok.Setter;
import tech.mcprison.prison.store.Document;

import java.util.UUID;

public class TokenPlayer {
    public UUID uid;
    @Getter
    @Setter
    public int tokens;

    public TokenPlayer() {
    }

    public TokenPlayer(Document document) {
        this.uid = UUID.fromString((String) document.get("uid"));
        this.tokens = (Integer) document.get("tokens");
    }

    public Document toDocument() {
        Document ret = new Document();
        ret.put("uid", this.uid);
        ret.put("tokens", tokens);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenPlayer)) {
            return false;
        }

        TokenPlayer that = (TokenPlayer) o;

        return uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}

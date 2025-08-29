package me.bhop.lanbroadcaster.common;

import java.util.concurrent.CompletableFuture;

public interface MOTDProvider {
    CompletableFuture<String> provideMOTD();
}

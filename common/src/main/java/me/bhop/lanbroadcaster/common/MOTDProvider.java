package me.bhop.lanbroadcaster.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface MOTDProvider {
    CompletableFuture<String> provideMOTD(ExecutorService executor);
}
